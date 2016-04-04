package com.devs.shoki.calendarpicker.calendar.cell;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devs.shoki.calendarpicker.R;
import com.devs.shoki.calendarpicker.calendar.param.CalendarCellParams;
import com.devs.shoki.calendarpicker.calendar.param.CalendarPickerParams;
import com.devs.shoki.calendarpicker.constants.CalendarMode;
import com.devs.shoki.calendarpicker.constants.Config;
import com.devs.shoki.calendarpicker.constants.MonthState;

/**
 * Created by shoki on 2016-03-18.
 */
public class CalendarCellView extends RelativeLayout {

    private CalendarCellParams params;
    private CalendarPickerParams pickerParams;
    private View backgroundView, backgroundView2;


    public CalendarCellView(Context context) {
        super(context);

        init();
    }

    public CalendarCellParams getParams() {
        return params;
    }

    private void init() {
        View.inflate(getContext(), R.layout.picker_cell, this);

        backgroundView = findViewById(R.id.picker_cell_background);
        backgroundView2 = findViewById(R.id.picker_cell_full_background);
    }

    public void setTitle(String day) {
        TextView titleTxtv = (TextView) findViewById(R.id.picker_cell_title_txtv);

        titleTxtv.setText(day);
    }

    public void setTextColor(int state) {
        TextView titleTxtv = (TextView) findViewById(R.id.picker_cell_title_txtv);

        if(state == MonthState.PREV) {
            titleTxtv.setTextColor(ContextCompat.getColor(getContext(), R.color.prevTextColor));
        }
        else if(state == MonthState.NOW) {
            titleTxtv.setTextColor(ContextCompat.getColor(getContext(), R.color.nowTextColor));
        }
        else {
            titleTxtv.setTextColor(ContextCompat.getColor(getContext(), R.color.nextTextColor));
        }
    }

    public void setParams(CalendarCellParams params, CalendarPickerParams pickerParams) {
        this.params = params;
        this.pickerParams = pickerParams;
        setTitle(String.valueOf(params.getDayParams().getDay()));
        setTextColor(params.getMonthState());
        setBackgroundColor();
    }

    public void setBackgroundColor() {
        setBackgroundImage(backgroundView, null);
        setBackgroundImage(backgroundView2, null);
        if(params.isSelected()) {
            if(pickerParams.getMode().equals(CalendarMode.SELECT)) {
                setBackgroundImage(backgroundView, pickerParams.getSelectedDrawable());
                LayoutParams params = (LayoutParams) backgroundView.getLayoutParams();
                params.addRule(CENTER_IN_PARENT, RelativeLayout.TRUE);
            }
            else if(pickerParams.getMode().equals(CalendarMode.FROM_TO) || pickerParams.getMode().equals(CalendarMode.DRAG_SELECT)) {
                if(params.getSelectedState() == Config.SELECTED_FIRST_DATE) {
                    setBackgroundImage(backgroundView, pickerParams.getSelectedFirstDrawable());
                    LayoutParams params = (LayoutParams) backgroundView.getLayoutParams();
                    params.addRule(ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                }
                else if(params.getSelectedState() == Config.SELECTED_LAST_DATE) {
                    setBackgroundImage(backgroundView, pickerParams.getSelectedLastDrawable());
                    LayoutParams params = (LayoutParams) backgroundView.getLayoutParams();
                    params.addRule(ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                }
                else if(params.getSelectedState() == Config.SELECTED_BETWEEN_DATE) {
                    setBackgroundImage(backgroundView2, pickerParams.getSelectedBetweenDrawable());
                }
                else {
                    setBackgroundImage(backgroundView, pickerParams.getSelectedDrawable());
                    LayoutParams params = (LayoutParams) backgroundView.getLayoutParams();
                    params.addRule(CENTER_IN_PARENT, RelativeLayout.TRUE);
                }
            }
        }
        else {
            setBackgroundImage(backgroundView2, ContextCompat.getDrawable(getContext(), R.drawable.calendar_date_default));
        }
    }

    private void setBackgroundImage(View view, Drawable drawable) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }
}
