package com.devs.shoki.calendarpicker.listener;

import com.devs.shoki.calendarpicker.CalendarPickerDialog;
import com.devs.shoki.calendarpicker.calendar.param.CalendarDayParams;

/**
 * Created by shoki on 2016-03-18.
 */
public interface IPickerListener {

    public void onPickerListener(CalendarPickerDialog dialog, CalendarDayParams day);
}
