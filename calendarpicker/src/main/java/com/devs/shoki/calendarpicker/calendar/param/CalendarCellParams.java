package com.devs.shoki.calendarpicker.calendar.param;

public class CalendarCellParams {


    public CalendarCellParams() {
    }

    /**
     * 한페이지 이전달 날짜인지 다음달 날짜인지 해당달 날짜인지 구분
     */
    private int monthState;
    /**
     * 날짜정보
     */
    private CalendarDayParams dayParams;
    /**
     * 선택한 날짜인지
     */
    private boolean selected = false;
    /**
     * 선택한날짜중 시작,끝,중간 구분
     */
    private int selectedState;

    public int getSelectedState() {
        return selectedState;
    }

    public void setSelectedState(int selectedState) {
        this.selectedState = selectedState;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public CalendarDayParams getDayParams() {
        return dayParams;
    }

    public void setDayParams(CalendarDayParams dayParams) {
        this.dayParams = dayParams;
    }

    public int getMonthState() {
        return monthState;
    }

    public void setMonthState(int monthState) {
        this.monthState = monthState;
    }
}