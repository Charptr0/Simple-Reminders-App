package com.charptr0.simplereminders;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CalenderActivity extends AppCompatActivity
{
    private CalendarView calendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender_layout);
        calendarView = findViewById(R.id.calender);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                System.out.printf("%d/%d/%d\n", month + 1, dayOfMonth, year);
            }
        });

    }

    public void timePickerHandler(View view)
    {
        int defaultHour = 10;
        int defaultMinute = 20;

        TimePickerDialog timePickerDialog = new TimePickerDialog(CalenderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                System.out.println(hourOfDay);
                System.out.println(minute);
            }
        }, defaultHour, defaultMinute, false);

        timePickerDialog.show();


    }
}
