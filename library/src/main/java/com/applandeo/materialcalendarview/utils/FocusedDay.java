package com.applandeo.materialcalendarview.utils;

import android.view.View;

import java.util.Calendar;

/**
 * This helper class represent when a day has focus on calendar.
 * <p>
 * Created by Paulo Henrique Carvalho de Morais on 21.06.2019.
 */


public class FocusedDay {
    private View mView;
    private Calendar mCalendar;

    public FocusedDay(Calendar calendar) {
        mCalendar = calendar;
    }

    /**
     * @param view     View representing focused calendar cell
     * @param calendar Calendar instance representing focused cell date
     */
    public FocusedDay(View view, Calendar calendar) {
        mView = view;
        mCalendar = calendar;
    }

    /**
     * @return View representing focused calendar cell
     */
    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }

    /**
     * @return Calendar instance representing focused cell date
     */
    public Calendar getCalendar() {
        return mCalendar;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SelectedDay) {
            return getCalendar().equals(((SelectedDay) obj).getCalendar());
        }

        if(obj instanceof Calendar){
            return getCalendar().equals(obj);
        }

        return super.equals(obj);
    }
}

