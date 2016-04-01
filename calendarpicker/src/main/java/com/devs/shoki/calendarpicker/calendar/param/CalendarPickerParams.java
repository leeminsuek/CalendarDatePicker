package com.devs.shoki.calendarpicker.calendar.param;

import android.graphics.drawable.Drawable;

import com.devs.shoki.calendarpicker.constants.CalendarMode;
import com.devs.shoki.calendarpicker.listener.IPickerFromToListener;
import com.devs.shoki.calendarpicker.listener.IPickerListener;

/**
 * Created by shoki on 2016-03-18.
 */
public class CalendarPickerParams {

    /**
     * 단일선택 콜백
     */
    private IPickerListener pickerListener;
    /**
     * 기간선택 콜백
     */
    private IPickerFromToListener pickerFromToListener;
    /**
     * 기간선택 선택된 날짜 (시작)
     */
    private CalendarDayParams firstDate;
    /**
     * 기간선택 선택된 날짜 (마지막)
     */
    private CalendarDayParams lastDate;
    /**
     * 시작날짜
     */
    private CalendarDayParams startDate;

    /**
     * 모드 SELECTE / FROM_TO
     */
    private CalendarMode mode = CalendarMode.DEFAULT;
    /**
     * 선택했을때 보여줄 이미지들
     */
    private Drawable selectedDrawable;
    private Drawable selectedFirstDrawable;
    private Drawable selectedLastDrawable;
    private Drawable selectedBetweenDrawable;

    public Drawable getSelectedFirstDrawable() {
        return selectedFirstDrawable;
    }

    public void setSelectedFirstDrawable(Drawable selectedFirstDrawable) {
        this.selectedFirstDrawable = selectedFirstDrawable;
    }

    public Drawable getSelectedLastDrawable() {
        return selectedLastDrawable;
    }

    public void setSelectedLastDrawable(Drawable selectedLastDrawable) {
        this.selectedLastDrawable = selectedLastDrawable;
    }

    public Drawable getSelectedBetweenDrawable() {
        return selectedBetweenDrawable;
    }

    public void setSelectedBetweenDrawable(Drawable selectedBetweenDrawable) {
        this.selectedBetweenDrawable = selectedBetweenDrawable;
    }

    public Drawable getSelectedDrawable() {
        return selectedDrawable;
    }

    public void setSelectedDrawable(Drawable selectedDrawable) {
        this.selectedDrawable = selectedDrawable;
    }

    public CalendarMode getMode() {
        return mode;
    }

    public void setMode(CalendarMode mode) {
        this.mode = mode;
    }

    public IPickerFromToListener getPickerFromToListener() {
        return pickerFromToListener;
    }

    public void setPickerFromToListener(IPickerFromToListener pickerFromToListener) {
        this.pickerFromToListener = pickerFromToListener;
    }

    public IPickerListener getPickerListener() {
        return pickerListener;
    }

    public void setPickerListener(IPickerListener pickerListener) {
        this.pickerListener = pickerListener;
    }

    public CalendarDayParams getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(CalendarDayParams firstDate) {
        this.firstDate = firstDate;
    }

    public CalendarDayParams getLastDate() {
        return lastDate;
    }

    public void setLastDate(CalendarDayParams lastDate) {
        this.lastDate = lastDate;
    }

    public CalendarDayParams getStartDate() {
        return startDate;
    }

    public void setStartDate(CalendarDayParams startDate) {
        this.startDate = startDate;
    }
}
