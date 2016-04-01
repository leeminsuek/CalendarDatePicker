package com.devs.shoki.calendarpicker.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.devs.shoki.calendarpicker.R;

public class DragSelectRecyclerView extends RecyclerView {

    public interface FingerListener {
        void onDragSelectFingerAction(boolean fingerDown);
    }

    private static final boolean LOGGING = false;
    private static final int AUTO_SCROLL_DELAY = 25;

    public DragSelectRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public DragSelectRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DragSelectRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private static void LOG(String message, Object... args) {
        //noinspection PointlessBooleanExpression
        if (!LOGGING) return;
        if (args != null) {
            Log.d("DragSelectRecyclerView", String.format(message, args));
        } else {
            Log.d("DragSelectRecyclerView", message);
        }
    }

    private int lastDraggedIndex = -1;
    private DragSelectRecyclerViewAdapter<?> adapter;
    private int initialSelection;
    private boolean dragSelectActive;
    private int minReached;
    private int maxReached;

    private int hotspotHeight;
    private int hotspotTopBound;
    private int hotspotBottomBound;
    private int autoScrollVelocity;

    private FingerListener fingerListener;

    private void init(Context context, AttributeSet attrs) {
        autoScrollHandler = new Handler();
        final int defaultHotspotHeight = context.getResources().getDimensionPixelSize(R.dimen.dsrv_defaultHotspotHeight);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DragSelectRecyclerView, 0, 0);
            try {
                boolean autoScrollEnabled = a.getBoolean(R.styleable.DragSelectRecyclerView_dsrv_autoScrollEnabled, true);
                if (!autoScrollEnabled) {
                    hotspotHeight = -1;
                    LOG("Auto-scroll disabled");
                } else {
                    hotspotHeight = a.getDimensionPixelSize(
                            R.styleable.DragSelectRecyclerView_dsrv_autoScrollHotspotHeight, defaultHotspotHeight);
                    LOG("Hotspot height = %d", hotspotHeight);
                }
            } finally {
                a.recycle();
            }
        } else {
            hotspotHeight = defaultHotspotHeight;
            LOG("Hotspot height = %d", hotspotHeight);
        }
    }

    public void setFingerListener(@Nullable FingerListener listener) {
        this.fingerListener = listener;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (hotspotHeight > -1) {
            hotspotTopBound = hotspotHeight;
            hotspotBottomBound = getMeasuredHeight() - hotspotHeight;
            LOG("RecyclerView height = %d", getMeasuredHeight());
            LOG("Hotspot top bound = %d", hotspotTopBound);
            LOG("Hotspot bottom bound = %d", hotspotBottomBound);
        }
    }

    public boolean setDragSelectActive(boolean active, int initialSelection) {
        if (active && dragSelectActive) {
            LOG("Drag selection is already active.");
            return false;
        }
        lastDraggedIndex = -1;
        minReached = -1;
        maxReached = -1;
        if (!adapter.isIndexSelectable(initialSelection)) {
            dragSelectActive = false;
            this.initialSelection = -1;
            lastDraggedIndex = -1;
            LOG("Index %d is not selectable.", initialSelection);
            return false;
        }
        adapter.setSelected(initialSelection, true);
        dragSelectActive = active;
        this.initialSelection = initialSelection;
        lastDraggedIndex = initialSelection;
        if (fingerListener != null)
            fingerListener.onDragSelectFingerAction(true);
        LOG("Drag selection initialized, starting at index %d.", initialSelection);
        return true;
    }

    /**
     * Use {@link #setAdapter(DragSelectRecyclerViewAdapter)} instead.
     */
    @Override
    @Deprecated
    public void setAdapter(Adapter adapter) {
        if (!(adapter instanceof DragSelectRecyclerViewAdapter<?>))
            throw new IllegalArgumentException("Adapter must be a DragSelectRecyclerViewAdapter.");
        setAdapter((DragSelectRecyclerViewAdapter<?>) adapter);
    }

    public void setAdapter(DragSelectRecyclerViewAdapter<?> adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
    }

    private boolean inTopHotspot;
    private boolean inBottomHotspot;

    private Handler autoScrollHandler;
    private Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (autoScrollHandler == null)
                return;
            if (inTopHotspot) {
                scrollBy(0, -autoScrollVelocity);
                autoScrollHandler.postDelayed(this, AUTO_SCROLL_DELAY);
            } else if (inBottomHotspot) {
                scrollBy(0, autoScrollVelocity);
                autoScrollHandler.postDelayed(this, AUTO_SCROLL_DELAY);
            }
        }
    };

    private int getItemPosition(MotionEvent e) {
        final View v = findChildViewUnder(e.getX(), e.getY());
        if (v == null) return -2;
        if (v.getTag() == null || !(v.getTag() instanceof ViewHolder))
            throw new IllegalStateException("Make sure your adapter makes a call to super.onBindViewHolder(), and doesn't override itemView tags.");
        final ViewHolder holder = (ViewHolder) v.getTag();
        return holder.getAdapterPosition();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (dragSelectActive) {
            final int itemPosition = getItemPosition(e);
            if (e.getAction() == MotionEvent.ACTION_UP) {
                dragSelectActive = false;
                inTopHotspot = false;
                inBottomHotspot = false;
                autoScrollHandler.removeCallbacks(autoScrollRunnable);
                if (fingerListener != null)
                    fingerListener.onDragSelectFingerAction(false);
                return true;
            } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
                // Check for auto-scroll hotspot
                if (hotspotHeight > -1) {
                    if (e.getY() <= hotspotTopBound) {
                        inBottomHotspot = false;
                        if (!inTopHotspot) {
                            inTopHotspot = true;
                            LOG("Now in TOP hotspot");
                            autoScrollHandler.removeCallbacks(autoScrollRunnable);
                            autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_DELAY);
                        }
                        autoScrollVelocity = (int) (hotspotTopBound - e.getY()) / 2;
                        LOG("Auto scroll velocity = %d", autoScrollVelocity);
                    } else if (e.getY() >= hotspotBottomBound) {
                        inTopHotspot = false;
                        if (!inBottomHotspot) {
                            inBottomHotspot = true;
                            LOG("Now in BOTTOM hotspot");
                            autoScrollHandler.removeCallbacks(autoScrollRunnable);
                            autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_DELAY);
                        }
                        autoScrollVelocity = (int) (e.getY() - hotspotBottomBound) / 2;
                        LOG("Auto scroll velocity = %d", autoScrollVelocity);
                    } else if (inTopHotspot || inBottomHotspot) {
                        LOG("Left the hotspot");
                        autoScrollHandler.removeCallbacks(autoScrollRunnable);
                        inTopHotspot = false;
                        inBottomHotspot = false;
                    }
                }

                // Drag selection logic
                if (itemPosition != -2 && lastDraggedIndex != itemPosition) {
                    lastDraggedIndex = itemPosition;
                    if (minReached == -1) minReached = lastDraggedIndex;
                    if (maxReached == -1) maxReached = lastDraggedIndex;
                    if (lastDraggedIndex > maxReached)
                        maxReached = lastDraggedIndex;
                    if (lastDraggedIndex < minReached)
                        minReached = lastDraggedIndex;
                    if (adapter != null)
                        adapter.selectRange(initialSelection, lastDraggedIndex, minReached, maxReached);
                    if (initialSelection == lastDraggedIndex) {
                        minReached = lastDraggedIndex;
                        maxReached = lastDraggedIndex;
                    }
                }
                return true;
            }
        }
        return super.dispatchTouchEvent(e);
    }
}