package com.applandeo.materialcalendarview.listeners;

import android.graphics.Typeface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.applandeo.materialcalendarview.R;
import com.applandeo.materialcalendarview.adapters.CalendarPageAdapter;
import com.applandeo.materialcalendarview.utils.CalendarProperties;
import com.applandeo.materialcalendarview.utils.DateUtils;
import com.applandeo.materialcalendarview.utils.DayColorsUtils;
import com.applandeo.materialcalendarview.utils.FocusedDay;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DayRowSelectListener implements AdapterView.OnItemSelectedListener{

    private CalendarPageAdapter mCalendarPageAdapter;

    private CalendarProperties mCalendarProperties;
    private int mPageMonth;

    public DayRowSelectListener(CalendarPageAdapter calendarPageAdapter, CalendarProperties calendarProperties, int pageMonth) {
        mCalendarPageAdapter = calendarPageAdapter;
        mCalendarProperties = calendarProperties;
        mPageMonth = pageMonth < 0 ? 11 : pageMonth;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Calendar day = new GregorianCalendar();
        day.setTime((Date) adapterView.getItemAtPosition(position));
        focusDay(view, day);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void focusDay(View view, Calendar day) {
        TextView dayLabel = view.findViewById(R.id.dayLabel);

        FocusedDay previousFocusedDay = mCalendarPageAdapter.getFocusedDay();
        if (isAnotherDayFocused(previousFocusedDay, day)) {
            reverseUnfocusedColor(previousFocusedDay);
        }

        mCalendarPageAdapter.setFocusedDay(new FocusedDay(dayLabel, day));
        DayColorsUtils.setFocusedDayColors(dayLabel, mCalendarProperties);
    }

    private void reverseUnfocusedColor(FocusedDay focusedDay) {
        TextView dayLabel = (TextView) focusedDay.getView();

        if (!isCurrentMonthDay(focusedDay.getCalendar())) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getAnotherMonthsDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
        } else if (!isActiveDay(focusedDay.getCalendar())) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getDisabledDaysLabelsColor(),
                    Typeface.NORMAL, R.drawable.background_transparent);
        } else if (isSelectedDay(focusedDay.getCalendar())) {
            DayColorsUtils.setSelectedDayColors(dayLabel, mCalendarProperties);
        } else {
            DayColorsUtils.setCurrentMonthDayColors(focusedDay.getCalendar(),
                    DateUtils.getCalendar(), dayLabel, mCalendarProperties);
        }
    }

    private boolean isSelectedDay(Calendar day) {
        return Stream.of(mCalendarPageAdapter.getSelectedDays())
                .filter(selectedDay -> selectedDay.getCalendar().equals(day))
                .findFirst().isPresent();
    }

    private boolean isCurrentMonthDay(Calendar day) {
        return day.get(Calendar.MONTH) == mPageMonth && isBetweenMinAndMax(day);
    }

    private boolean isActiveDay(Calendar day) {
        return !mCalendarProperties.getDisabledDays().contains(day);
    }

    private boolean isBetweenMinAndMax(Calendar day) {
        return !((mCalendarProperties.getMinimumDate() != null && day.before(mCalendarProperties.getMinimumDate()))
                || (mCalendarProperties.getMaximumDate() != null && day.after(mCalendarProperties.getMaximumDate())));
    }

    private boolean isAnotherDayFocused(FocusedDay focusedDay, Calendar day) {
        return focusedDay != null && !day.equals(focusedDay.getCalendar());
    }
}
