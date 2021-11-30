package com.charptr0.simplereminders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        dialog.setSingleChoiceItems(preSetTimes, checkedTime, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(CreationActivity.this, "1 minutes from now", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(CreationActivity.this, "5 minutes from now", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(CreationActivity.this, "10 minutes from now", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(CreationActivity.this, "15 minutes from now", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(CreationActivity.this, "30 minutes from now", Toast.LENGTH_SHORT).show();
                        break;

                    case 5:
                        Toast.makeText(CreationActivity.this, "1 hour from now", Toast.LENGTH_SHORT).show();
                        break;

                    case 6:
                        Toast.makeText(CreationActivity.this, "8 hours from now", Toast.LENGTH_SHORT).show();
                        break;

                    case 7:
                        Toast.makeText(CreationActivity.this, "12 hours from now", Toast.LENGTH_SHORT).show();
                        break;

                    case 8:
                        Toast.makeText(CreationActivity.this, "1 day from now", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }

                //update the date and time text
                dateAndTimeText.setText((CurrentDate.getDateFormatted() + " at " + CurrentTime.getCurrentTime()));
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

    /**
     * Handles the events when the confirm button is pressed
     *
     * Save the reminder name and priority level that the user entered and pass it back to the main activity
     */
    public void confirmButtonHandler(View view)
    {
        //get attributes from user inputs
        String reminderName = getUserEnteredReminderName();
        String priorityLevel = getUserEnteredPriorityLevel();

        //pass the information back to the main activity
        Intent intent = new Intent();

        //create a new reminder with the user inputs
        intent.putExtra("reminder", new Reminder(reminderName, priorityLevel, ""));
        setResult(RESULT_OK, intent);

        //destroy the current intent
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
