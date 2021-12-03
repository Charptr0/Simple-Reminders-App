package com.charptr0.simplereminders;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 *
 * @author Chenhao Li
 * @version 0.5
 */
public class CalenderActivity extends AppCompatActivity
{
    private CalendarView calendarView;
    private TextView dateAndTimeText;

    private String dateAsString = "";
    private String timeAsString = "12:00";

    private final int DEFAULT_HR = 12;
    private final int DEFAULT_MIN = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();

        super.onCreate(savedInstanceState);

        //upon creating intent, set view to be at calender_layout.fxml
        setContentView(R.layout.calender_layout);

        //set default date to be today
        dateAsString = CurrentDateAndTime.getCurrentDateAndTimeFormatted();

        //add reference for calender and text for date and time
        calendarView = findViewById(R.id.calender);
        dateAndTimeText = findViewById(R.id.dateAndTimeText);

        //upon creating this intent, set the text to be the current date and time at 12:00pm
        dateAndTimeText.setText((dateAsString));

        //for the calender view, add a listener for changing dates
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //update variable
                dateAsString = String.valueOf(month + 1) + "/" + dayOfMonth + "/" + year;
                String dateAndTimeTogether = dateAsString + " at " + timeAsString;

                //display the new date to user
                dateAndTimeText.setText(dateAndTimeTogether);
            }
        });
    }

    public void timePickerHandler(View view)
    {
        //create a new time picker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(CalenderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            //add a listener for every time set
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //update variable
                timeAsString = hourOfDay + ":" + minute;

                //display the new time to user
                dateAndTimeText.setText(String.format("%s at %s", dateAsString, timeAsString));
            }

        }, DEFAULT_HR, DEFAULT_MIN, false);

        timePickerDialog.show(); //show the dialog
    }

    public void onConfirmHandler(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("date_and_time", (dateAsString + " at " + timeAsString));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
