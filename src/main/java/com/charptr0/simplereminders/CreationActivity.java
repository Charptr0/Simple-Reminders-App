package com.charptr0.simplereminders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CreationActivity extends AppCompatActivity
{
    private TextView dateAndTimeText;
    private AutoCompleteTextView reminderNameView;
    private RadioGroup selectedPriority;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_layout);

        dateAndTimeText = findViewById(R.id.dateAndTimeText_main);

        reminderNameView = findViewById(R.id.reminderNameTextArea);

        selectedPriority = findViewById(R.id.priorityLevels);

    }

    /**
     * onClick handler on the "advance options" button
     *
     * Start a new activity for result to grab the user's custom time options
     *
     */
    public void advanceOptionsBtnHandler(View view)
    {
        Intent intent = new Intent(CreationActivity.this, CalenderActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * Handle and parse the data that was send back from the calender activity
     *
     * The data that is send back is the date and time in "MM/DD/YY at HH:MM" format. After getting the data,
     * it will update the dateAndTimeText
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            String text = data.getStringExtra("date_and_time");
            dateAndTimeText.setText(text);
        }
    }

    /**
     * Opens a dialog for quick set options when the "quickest" button is clicked
     */
    public void quickSetOpenDialog(View view)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CreationActivity.this);
        dialog.setTitle("Choose a time slot");
        dialog.setPositiveButton("Done", null);

        String[] preSetTimes = {
                "1 minute from now",
                "5 minutes from now",
                "10 minutes from now",
                "15 minutes from now",
                "30 minutes from now",
                "1 hour from now",
                "8 hours from now",
                "12 hours from now",
                "1 day from now"};

        //default option to be option 5 (1 hr)
        int checkedTime = 5;

        //dialog button listener
        dialog.setSingleChoiceItems(preSetTimes, 10, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String dateAndTime = CurrentDateAndTime.getCurrentDateAndTimeFormatted();

                switch (which) {
                    case 0:
                        dateAndTime = FutureDateAndTime.getFutureTime(60);
                        break;
                    case 1:
                        dateAndTime = FutureDateAndTime.getFutureTime(300);
                        break;
                    case 2:
                        dateAndTime = FutureDateAndTime.getFutureTime(600);
                        break;
                    case 3:
                        dateAndTime = FutureDateAndTime.getFutureTime(900);
                        break;
                    case 4:
                        dateAndTime = FutureDateAndTime.getFutureTime(1800);
                        break;

                    case 5:
                        dateAndTime = FutureDateAndTime.getFutureTime(3600);
                        break;

                    case 6:
                        dateAndTime = FutureDateAndTime.getFutureTime(28800);
                        break;

                    case 7:
                        dateAndTime = FutureDateAndTime.getFutureTime(43200);
                        break;

                    case 8:
                        dateAndTime = FutureDateAndTime.getFutureTime(86400);
                        break;

                    default:
                        break;
                }

                //update the date and time text
                dateAndTimeText.setText(dateAndTime);
            }
        });

        //display the dialog
        AlertDialog alert = dialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    /**
     * Get the user input from the textview as a string
     *
     * @return the reminder name that the user entered
     */
    private String getUserEnteredReminderName()
    {
        //get the string from the text view
        String reminderName = reminderNameView.getText().toString();

        //if the text view is empty, then change to a default value
        if(reminderName.isEmpty()) return "Untitled Reminder";

        //if not empty, return the string that is inside the text view
        return reminderName;
    }

    /**
     * Get the string from the pressed radio button
     *
     * @return the text from the pressed radio button
     */
    private String getUserEnteredPriorityLevel()
    {
        //grab the id of pressed radio button
        int id = selectedPriority.getCheckedRadioButtonId();

        //match the radio button object to id
        RadioButton buttonChecked = findViewById(id);

        //return its text
        return buttonChecked.getText().toString();
    }

    private String getUserEnteredTime()
    {
        return dateAndTimeText.getText().toString();
    }

    private void showInvalidTimeErrDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("Your input time is not valid")
                .setMessage("Please choose a appropriate time")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Handles the events when the confirm button is pressed
     *
     * Save the reminder name and priority level that the user entered and pass it back to the main activity
     */
    public void confirmButtonHandler(View view) {
        //get attributes from user inputs
        String reminderName = getUserEnteredReminderName();
        String priorityLevel = getUserEnteredPriorityLevel();
        String reminderTime = getUserEnteredTime();

        if(reminderTime.equals(""))
        {
            showInvalidTimeErrDialog();
            return;
        }

        //pass the information back to the main activity
        Intent intent = new Intent();

        //create a new reminder with the user inputs
        intent.putExtra("reminder", new Reminder(reminderName, priorityLevel, reminderTime, String.valueOf(CurrentDateAndTime.getUnixTime())));
        setResult(RESULT_OK, intent);

        //destroy the current intent
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
