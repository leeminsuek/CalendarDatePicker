package com.devs.shoki.calendarpicker;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.devs.shoki.calendarpicker.calendar.cell.CalendarCellView;
import com.devs.shoki.calendarpicker.calendar.cell.CalendarWeekView;
import com.devs.shoki.calendarpicker.calendar.param.CalendarCellParams;
import com.devs.shoki.calendarpicker.calendar.param.CalendarDayParams;
import com.devs.shoki.calendarpicker.calendar.param.CalendarPickerParams;
import com.devs.shoki.calendarpicker.constants.Config;
import com.devs.shoki.calendarpicker.constants.Week;
import com.devs.shoki.calendarpicker.listener.IDayClickListener;
import com.devs.shoki.calendarpicker.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by shoki on 2016-03-18.
 */
public class CalendarGridAdapter extends RecyclerView.Adapter<CalendarGridAdapter.CalendarViewHolder> {

    private final int HEADER_CELL_CNT = 7;

    public static final int VIEW_HEADER = 1;
    public static final int VIEW_NORMAL = 2;

    private List<Week> weekList = new ArrayList<>();
    private List<CalendarCellParams> paramsList = new ArrayList<>();
    private Map<String, CalendarDayParams> selectParamsMap;
    private IDayClickListener iDayClickListener;
    private CalendarPickerParams calendarPickerParams;

    private boolean drag = false;
    private RecyclerView recyclerView;

    public CalendarGridAdapter(List<CalendarCellParams> cellParamses, Map<String, CalendarDayParams> selectDayMap, CalendarPickerParams pickerParams) {
        for (Week week : Week.values()) {
            weekList.add(week);
        }
        calendarPickerParams = pickerParams;
        selectParamsMap = selectDayMap;
        paramsList = cellParamses;
        setSelectedItem();
    }

    public void setOnDragListener(RecyclerView view, boolean drag) {
        this.drag = drag;
        recyclerView = view;
        if(drag) {
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i("touchEvent", "================EVNET================");
                    Log.i("touchEvent", "action : " + event.getAction());
                    if(event.getAction() == MotionEvent.ACTION_UP ||
                            event.getAction() == MotionEvent.ACTION_MOVE) {
                        View item = recyclerView.findChildViewUnder(event.getX(), event.getY());
                        if(item != null) {
                            item.performClick();
                        }
                    }

                    return false;
                }
            });
        }
    }

    public void setOnDayClickListener(IDayClickListener listener) {
        iDayClickListener = listener;
    }

    public void setData(List<CalendarCellParams> cellParamses) {
        paramsList = cellParamses;
        setSelectedItem();
    }

    private void setSelectedItem() {
        if (selectParamsMap.containsKey(Config.SELECT_LAST_DATE_KEY)) {
            CalendarDayParams startDay = selectParamsMap.get(Config.SELECT_FIRST_DATE_KEY);
            CalendarDayParams endDay = selectParamsMap.get(Config.SELECT_LAST_DATE_KEY);
            for (int i = 0; i < paramsList.size(); i++) {
                int startDiff = DateUtil.isDifferenceOfDay(startDay, paramsList.get(i).getDayParams());
                int endDiff = DateUtil.isDifferenceOfDay(endDay, paramsList.get(i).getDayParams());
                if (startDiff == 0 || endDiff == 0 || (startDiff == 1 && endDiff == -1)) {
                    if (startDiff == 0 && endDiff == 0) {
                        paramsList.get(i).setSelectedState(Config.SELECTED_ONE_DATE);
                    } else if (startDiff == 0) {
                        paramsList.get(i).setSelectedState(Config.SELECTED_FIRST_DATE);
                    } else if (endDiff == 0) {
                        paramsList.get(i).setSelectedState(Config.SELECTED_LAST_DATE);
                    } else {
                        paramsList.get(i).setSelectedState(Config.SELECTED_BETWEEN_DATE);
                    }
                    paramsList.get(i).setSelected(true);
                } else {
                    paramsList.get(i).setSelectedState(Config.SELECTED_NONE_DATE);
                    paramsList.get(i).setSelected(false);
                }
            }
        } else if (selectParamsMap.containsKey(Config.SELECT_FIRST_DATE_KEY)) {
            CalendarDayParams startDay = selectParamsMap.get(Config.SELECT_FIRST_DATE_KEY);
            for (int i = 0; i < paramsList.size(); i++) {
                int startDiff = DateUtil.isDifferenceOfDay(startDay, paramsList.get(i).getDayParams());
                if (startDiff == 0 || startDiff == 1) {
                    if (startDiff == 0) {
                        paramsList.get(i).setSelectedState(Config.SELECTED_ONE_DATE);
                    } else {
                        paramsList.get(i).setSelectedState(Config.SELECTED_NONE_DATE);
                    }
                    paramsList.get(i).setSelected(true);
                } else {
                    paramsList.get(i).setSelectedState(Config.SELECTED_NONE_DATE);
                    paramsList.get(i).setSelected(false);
                }
            }
        } else if (selectParamsMap.containsKey(Config.SELECT_DATE_KEY)) {
            for (int i = 0; i < paramsList.size(); i++) {
                int diff = DateUtil.isDifferenceOfDay(selectParamsMap.get(Config.SELECT_DATE_KEY), paramsList.get(i).getDayParams());
                if (diff == 0) {
                    paramsList.get(i).setSelectedState(Config.SELECTED_ONE_DATE);
                    paramsList.get(i).setSelected(true);
                } else {
                    paramsList.get(i).setSelectedState(Config.SELECTED_NONE_DATE);
                    paramsList.get(i).setSelected(false);
                }
            }
        }
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int size = (int) parent.getContext().getResources().getDimension(R.dimen.calendar_size);

        if (viewType == VIEW_HEADER) {
            CalendarWeekView cell = new CalendarWeekView(parent.getContext());
            CalendarWeekViewHolder calendarWeekViewHolder = new CalendarWeekViewHolder(cell);
            calendarWeekViewHolder.calendarWeekView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return calendarWeekViewHolder;
        } else {
            CalendarCellView cell = new CalendarCellView(parent.getContext());
            final CalendarCellViewHolder calendarCellViewHolder = new CalendarCellViewHolder(cell);
            calendarCellViewHolder.calendarCellView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size));
            return calendarCellViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, final int position) {

        if (position >= 0 && position < HEADER_CELL_CNT) {
            CalendarWeekViewHolder calendarWeekViewHolder = (CalendarWeekViewHolder) holder;
            calendarWeekViewHolder.calendarWeekView.setData(weekList.get(position));
        } else {
            CalendarCellParams params = paramsList.get(position - HEADER_CELL_CNT);
            CalendarCellViewHolder calendarCellViewHolder = (CalendarCellViewHolder) holder;
            calendarCellViewHolder.calendarCellView.setParams(params, calendarPickerParams);
        }
    }

    @Override
    public int getItemCount() {
        return paramsList.size() + HEADER_CELL_CNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 0 && position < HEADER_CELL_CNT) {
            return VIEW_HEADER;
        }
        return VIEW_NORMAL;
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public CalendarViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 요일표기 셀
     */
    public class CalendarWeekViewHolder extends CalendarViewHolder {

        public CalendarWeekView calendarWeekView;

        public CalendarWeekViewHolder(View itemView) {
            super(itemView);

            calendarWeekView = (CalendarWeekView) itemView;
        }
    }

    /**
     * 날짜표기 셀
     */
    public class CalendarCellViewHolder extends CalendarViewHolder {

        public CalendarCellView calendarCellView;

        public CalendarCellViewHolder(final View itemView) {
            super(itemView);

            calendarCellView = (CalendarCellView) itemView;

            if(drag && recyclerView != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        v.performClick();
                        return false;
                    }
                });
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iDayClickListener != null) {
                        iDayClickListener.onDayClickListener(calendarCellView.getParams().getDayParams(), getAdapterPosition());
                    }
                }
            });
        }
    }
}
