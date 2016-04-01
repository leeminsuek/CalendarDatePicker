package com.devs.shoki.calendarpicker.constants;

/**
 * Created by shoki on 2016-03-18.
 */
public enum Week {

    SUN("SUN", 0, true),
    MON("MON", 1, false),
    TUE("TUE", 2, false),
    WED("WED", 3, false),
    THU("THU", 4, false),
    FRI("FIR", 5, false),
    SAT("SAT", 6, true)
    ;

    public String week;
    public int index;
    public boolean isWeekend;
    Week(String week, int index, boolean isWeekend) {
        this.week = week;
        this.index = index;
        this.isWeekend = isWeekend;
    }
}
