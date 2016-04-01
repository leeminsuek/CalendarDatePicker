package com.devs.shoki.calendarpicker.constants;

/**
 * Created by shoki on 2016-03-21.
 */
public enum CalendarMode {
    DEFAULT(0),
    SELECT(1),
    FROM_TO(2),
    FROM_TO_DRAG(3)
    ;
    public int mode;
    CalendarMode(int mode) {
        this.mode = mode;
    }
}
