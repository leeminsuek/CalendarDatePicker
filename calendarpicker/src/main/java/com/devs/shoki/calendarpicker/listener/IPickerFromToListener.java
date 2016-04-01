package com.devs.shoki.calendarpicker.listener;

import com.devs.shoki.calendarpicker.CalendarPickerDialog;
import com.devs.shoki.calendarpicker.calendar.param.CalendarDayParams;

/**
 * Created by shoki on 2016-03-21.
 */
public interface IPickerFromToListener {
    public void onPickerFromToListener(CalendarPickerDialog dialog, CalendarDayParams from, CalendarDayParams to);
}
