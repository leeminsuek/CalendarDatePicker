package com.devs.shoki.calendarpicker.calendar.cell;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devs.shoki.calendarpicker.R;
import com.devs.shoki.calendarpicker.constants.Week;

/**
 * Created by shoki on 2016-03-18.
 */
public class CalendarWeekView extends RelativeLayout {

    public CalendarWeekView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.picker_week_cell, this);
    }

    public void setData(Week week) {
        TextView titleTxtv = (TextView) findViewById(R.id.picker_week_cell_title_txtv);

        titleTxtv.setText(week.week);

        if(week.isWeekend) {
            titleTxtv.setTextColor(getContext().getResources().getColor(R.color.weekendTextColor));
        }
        else {
            titleTxtv.setTextColor(getContext().getResources().getColor(R.color.nowTextColor));
        }
    }
}
