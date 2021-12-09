package com.charptr0.simplereminders;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Reminder>listOfReminders = new ArrayList<>();

    private TextView noReminderText;
    private RecyclerView recyclerView;
    private ReminderAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init notifications for Android Oreo or higher
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("Main Channel", "Main Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        noReminderText = findViewById(R.id.noReminderText);

        //recycler view and adapter init
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReminderAdapter(this, listOfReminders);
        recyclerView.setAdapter(adapter);

        //add a reference to the database
        databaseHelper = new DatabaseHelper(MainActivity.this);
        getRemindersFromDatabase(); //get all previous entries from the database

        //gesture handler
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();

                //if a right swipe gesture is made, delete the current reminder
                if(direction == ItemTouchHelper.RIGHT)
                {
                    databaseHelper.deleteEntry(listOfReminders.get(position).getId());
                    listOfReminders.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(MainActivity.this, "The task has been successfully deleted", Toast.LENGTH_SHORT).show();
                }

                //if the list is empty, notify the user there are no reminder
                if(listOfReminders.isEmpty()) noReminderText.setVisibility(View.VISIBLE);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * Get all entries that are inside of the database and display it to the user
     */
    @SuppressLint("NotifyDataSetChanged")
    private void getRemindersFromDatabase()
    {
        //grab all entries
        ArrayList<Reminder>temp = databaseHelper.getAll();

        //entries are not empty
        if(!temp.isEmpty())
        {
            noReminderText.setVisibility(View.INVISIBLE);

            //add every single entry into the main list and update the adapter
            for(Reminder r : temp)
            {
                this.listOfReminders.add(r);
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * onClick handler for the creation button
     *
     * Opens a new intent for the creation of a new reminder
     */
    public void creationButtonHandler(View view){
        Intent intent = new Intent(MainActivity.this, CreationActivity.class);
        startActivityForResult(intent, 1);
    }


    /**
     * Get the data from the creationActivity
     * All reminders send back are valid reminder, therefore is added to the array list and database
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            //make sure data is not null
            assert data != null;

            Reminder r = data.getParcelableExtra("reminder");
            listOfReminders.add(r);

            //update the adapter and recycler view
            adapter.notifyDataSetChanged();

            setAlarm(r.getName(), r.getId() * 1000L, r.getWaitTimeSeconds());

            //add the reminder to database
            databaseHelper.addReminder(r);
        }

        if(!listOfReminders.isEmpty()) noReminderText.setVisibility(View.INVISIBLE);
    }

    /**
     * Generate a random integer to represent the pending intent ID
     * @return a integer
     */
    private int generateRandomID() {
        return new Random().nextInt();
    }

    /**
     * Set schedule notifications
     * @param reminderName the reminder created time in seconds
     * @param waitTimeSeconds how many seconds to wait from the current time
     */
    private void setAlarm(String reminderName, long createdTime, int waitTimeSeconds)
    {
        Intent intent = new Intent(this, ReminderBroadcaster.class);
        intent.putExtra("reminder_name", reminderName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, generateRandomID(), intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long waitTime = 1000L * waitTimeSeconds;

        // allow the notification to be more precise
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, createdTime + waitTime, pendingIntent);
        }

        //if the phone has Android version less than M, set a regular schedule notification
        //these are not very accurate, but still works to an extend
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, createdTime + waitTime, pendingIntent);
        }
    }
}