package com.charptr0.simplereminders;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Main Channel";
    private int notification_id = 1;
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
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Main Notification", NotificationManager.IMPORTANCE_DEFAULT);
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

            databaseHelper.addReminder(r);
            createNotification(r.getName());
        }

        if(!listOfReminders.isEmpty()) noReminderText.setVisibility(View.INVISIBLE);
    }


    private void createNotification(String reminderName)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Simple Reminders");
        builder.setContentText(reminderName);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);

        NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
        manager.notify(1, builder.build());
    }
}