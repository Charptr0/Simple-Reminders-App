package com.charptr0.simplereminders;

import android.app.AlertDialog;
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

import java.util.Calendar;

/**
 * The activity that handle for manual input of date and time
 *
 * @author Chenhao Li
 * @version 1.0
 */
public class CalenderActivity extends AppCompatActivity
{
    private CalendarView calendarView;
    private TextView dateAndTimeText;

    private String dateAsString = CurrentDateAndTime.getCurrentDate();
    private String timeAsString = CurrentDateAndTime.getCurrentTime();

    private final int DEFAULT_HR = 12;
    private final int DEFAULT_MIN = 0;

    private int selectedYear = Calendar.getInstance().get(Calendar.YEAR);
    private int selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int selectedDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private int selectedHour = DEFAULT_HR;
    private int selectedMinute = DEFAULT_MIN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //upon creating intent, set view to be at calender_layout.fxml
        setContentView(R.layout.calender_layout);

        //add reference for calender and text for date and time
        calendarView = findViewById(R.id.calender);
        dateAndTimeText = findViewById(R.id.dateAndTimeText);

        //for the calender view, add a listener for changing dates
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //update variable
                dateAsString = String.format("%s %s %s", parseMonth(month + 1), parseDatOfMonth(dayOfMonth), year);
                String dateAndTimeTogether = dateAsString + " at " + timeAsString;

                //display the new date to user
                dateAndTimeText.setText(dateAndTimeTogether);

                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth;
            }
        });
    }

    /**
     * Change month from a integer representation to string representation
     *
     * 1 -> Jan
     * 2 -> Feb
     * ...
     *
     * @param month the month as an integer
     * @return the month as a string
     */
    private String parseMonth(int month)
    {
        switch (month)
        {
            case 1: return "Jan";
            case 2: return "Feb";
            case 3: return "Mar";
            case 4: return "Apr";
            case 5: return "May";
            case 6: return "Jun";
            case 7: return "Jul";
            case 8: return "Aug";
            case 9: return "Sep";
            case 10: return "Oct";
            case 11: return "Nov";
            case 12: return "Dec";
            default: break;
        }

        return "";
    }

    /**
     * Change the dayOfMonth integer representation to a string representation
     *
     * @param dayOfMonth as an integer
     * @return day of the month as a string, if its < 10, it will be begin with a 0
     */
    private String parseDatOfMonth(int dayOfMonth)
    {
        if(dayOfMonth < 10) return "0" + String.valueOf(dayOfMonth);

        return String.valueOf(dayOfMonth);
    }

    /**
     * Display an error dialog when the time entered has already passed
     */
    private void showInvalidTimeErrDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("Your input time is not valid")
                .setMessage("Please choose a appropriate time")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void timePickerHandler(View view)
    {
        //create a new time picker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(CalenderActivity.this, new TimePickerDialog.OnTimeSetListener() {
            //add a listener for every time set
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //update variable
                if(minute == 0)
                    timeAsString = hourOfDay + ":" + minute + "0";

                else timeAsString = hourOfDay + ":" + minute;

                //display the new time to user
                dateAndTimeText.setText(String.format("%s at %s", dateAsString, timeAsString));

                selectedHour = hourOfDay;
                selectedMinute = minute;
            }

        }, DEFAULT_HR, DEFAULT_MIN, false);

        timePickerDialog.show(); //show the dialog
    }

    /**
     * Confirm button handler
     */
    public void onConfirmHandler(View view)
    {
        //if the date and time is missing, then the user hasnt entered anything
        if(dateAndTimeText.getText().equals(""))
        {
            //show err
            showInvalidTimeErrDialog();
            return;
        }

        //if time entered has already passed
        if(!CompareTime.isValidDateTime(dateAndTimeText.getText().toString()))
        {
            //show err
            showInvalidTimeErrDialog();
            return;
        }

        //calculate the wait time in seconds
        long waitTimeInSeconds = FutureDateAndTime.getTimeInBetween(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute);

        //pass data back to creation activity
        Intent intent = new Intent();
        intent.putExtra("date_and_time", (dateAsString + " at " + timeAsString));
        intent.putExtra("wait_time", waitTimeInSeconds);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
