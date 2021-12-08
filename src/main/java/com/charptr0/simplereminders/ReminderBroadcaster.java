package com.charptr0.simplereminders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

/**
 * Handle schedule notifications
 */
public class ReminderBroadcaster extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //get the name of the reminder
        String reminderName = intent.getStringExtra("reminder_name");

        //build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Main Channel");
        builder.setContentTitle("Simple Reminders");
        builder.setContentText(reminderName);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);

        //send to user
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(generateRandomID(), builder.build());
    }

    /**
     * Generate a random int to represent the notification iD
     * All notification id must be different inorder for all notification to show up
     * @return a integer
     */
    private int generateRandomID() {
        return new Random().nextInt();
    }
}
