package com.devs.shoki.calendarpicker.calendar.param;

/**
 * Created by shoki on 2016-03-21.
 */
public class CalendarDayParams {
    private int day;
    private int month;
    private int year;
    /**
     * 공휴일/주말 구분
     */
    private boolean week = false;

    public boolean isWeek() {
        return week;
    }

    public void setWeek(boolean week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
