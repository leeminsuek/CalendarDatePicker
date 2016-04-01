package com.devs.shoki.calendarpicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.devs.shoki.calendarpicker.calendar.CalendarPickerView;
import com.devs.shoki.calendarpicker.calendar.param.CalendarDayParams;
import com.devs.shoki.calendarpicker.calendar.param.CalendarPickerParams;
import com.devs.shoki.calendarpicker.constants.CalendarMode;
import com.devs.shoki.calendarpicker.listener.IPickerFromToListener;
import com.devs.shoki.calendarpicker.listener.IPickerListener;

/**
 * Created by shoki on 2016-03-18.
 */
public class CalendarPickerDialog extends AlertDialog {

    private CalendarPickerView calendarPickerView;

    protected CalendarPickerDialog(Context context, CalendarPickerParams params) {
        super(context);

        calendarPickerView = new CalendarPickerView(context, params, this);
        setView(calendarPickerView);
    }

    public CalendarPickerView getCalendarPickerView() {
        return calendarPickerView;
    }

    public static class Builder {
        private CalendarPickerDialog calendarPickerDialog;
        private CalendarPickerParams calendarPickerParams;

        private Context context;

        public Builder(Activity context) {
            this.context = context;
            this.calendarPickerParams = new CalendarPickerParams();
        }

        public Builder setOnPickerListener(IPickerListener pickerListener) {
            calendarPickerParams.setPickerListener(pickerListener);
            return this;
        }

        public Builder setOnPickerFromToListener(IPickerFromToListener pickerFromToListener) {
            calendarPickerParams.setPickerFromToListener(pickerFromToListener);
            return this;
        }

        public Builder setSelectedMode(CalendarMode mode) {
            calendarPickerParams.setMode(mode);
            return this;
        }

        public Builder setFirstDate(int year, int month, int day) {
            CalendarDayParams dayParams = new CalendarDayParams();
            dayParams.setYear(year);
            dayParams.setMonth(month);
            dayParams.setDay(day);
            calendarPickerParams.setFirstDate(dayParams);
            return this;
        }

        /**
         * 테스트 한글
         * @param year 년
         * @param month 월
         * @param day 일
         * @return 빌드
         */
        public Builder setLastDate(int year, int month, int day) {
            CalendarDayParams dayParams = new CalendarDayParams();
            dayParams.setYear(year);
            dayParams.setMonth(month);
            dayParams.setDay(day);
            calendarPickerParams.setLastDate(dayParams);
            return this;
        }

        public Builder setSelectedDrawable(@DrawableRes int drawable) {
            calendarPickerParams.setSelectedDrawable(ContextCompat.getDrawable(context, drawable));
            return this;
        }

        public Builder setSelectedDrawable(Drawable drawable) {
            calendarPickerParams.setSelectedDrawable(drawable);
            return this;
        }

        public Builder setSelectedFirstDateDrawable(@DrawableRes int drawable) {
            calendarPickerParams.setSelectedFirstDrawable(ContextCompat.getDrawable(context, drawable));
            return this;
        }

        public Builder setSelectedFirstDateDrawable(Drawable drawable) {
            calendarPickerParams.setSelectedFirstDrawable(drawable);
            return this;
        }

        public Builder setSelectedLastDateDrawable(@DrawableRes int drawable) {
            calendarPickerParams.setSelectedLastDrawable(ContextCompat.getDrawable(context, drawable));
            return this;
        }

        public Builder setSelectedLastDateDrawable(Drawable drawable) {
            calendarPickerParams.setSelectedLastDrawable(drawable);
            return this;
        }

        public Builder setSelectedBetweenDateDrawable(@DrawableRes int drawable) {
            calendarPickerParams.setSelectedBetweenDrawable(ContextCompat.getDrawable(context, drawable));
            return this;
        }

        public Builder setSelectedBetweenDateDrawable(Drawable drawable) {
            calendarPickerParams.setSelectedBetweenDrawable(drawable);
            return this;
        }

        public Builder setStartDate(int year, int month, int day) {
            CalendarDayParams dayParams = new CalendarDayParams();
            dayParams.setYear(year);
            dayParams.setMonth(month);
            dayParams.setDay(day);
            calendarPickerParams.setStartDate(dayParams);
            return this;
        }

        /**
         * 디폴트 설정 후 생성
         * @return 빌드
         */
        public CalendarPickerDialog create() {
            if(calendarPickerParams.getSelectedDrawable() == null) {
                calendarPickerParams.setSelectedDrawable(ContextCompat.getDrawable(context, R.drawable.calendar_date_selected));
            }

            if(calendarPickerParams.getSelectedFirstDrawable() == null) {
                calendarPickerParams.setSelectedFirstDrawable(ContextCompat.getDrawable(context, R.drawable.calendar_date_selected_first));
            }

            if(calendarPickerParams.getSelectedLastDrawable() == null) {
                calendarPickerParams.setSelectedLastDrawable(ContextCompat.getDrawable(context, R.drawable.calendar_date_selecte_last));
            }

            if(calendarPickerParams.getSelectedBetweenDrawable() == null) {
                calendarPickerParams.setSelectedBetweenDrawable(ContextCompat.getDrawable(context, R.drawable.calendar_date_selected_item));
            }

            if(calendarPickerDialog == null) {
                calendarPickerDialog = new CalendarPickerDialog(context, calendarPickerParams);
            }

            return calendarPickerDialog;
        }
    }
}
