package com.charptr0.simplereminders;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class for the recycler view
 *
 * IDK WHAT IS HAPPENING HERE BUT IT WORKS (DONT TOUCH)
 *
 * @author Chenhao Li
 * @version 1.0
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderHolder>
{
    private Context context;
    private ArrayList<Reminder>reminders;

    public ReminderAdapter(Context context, ArrayList<Reminder>reminders)
    {
        this.context = context;
        this.reminders = reminders;
    }

    @NonNull
    @Override
    public ReminderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reminder_box_layout, parent, false);

        return new ReminderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderHolder holder, int position) {
        Reminder r = reminders.get(position);

        holder.setDetails(r);
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    class ReminderHolder extends RecyclerView.ViewHolder {
        private TextView reminderNameTxt;
        private TextView reminderPriorityLevelTxt;
        private TextView reminderTimeTxt;
        private LinearLayout linearLayout;

        public ReminderHolder(View view)
        {
            super(view);

            reminderNameTxt = view.findViewById(R.id.reminderNameText);
            reminderPriorityLevelTxt = view.findViewById(R.id.reminderPriorityText);
            reminderTimeTxt = view.findViewById(R.id.reminderTimeText);
            linearLayout = view.findViewById(R.id.linearLayout);
        }

        private void updateLayoutColor(String reminder_priority)
        {
            if(reminder_priority.equals("Medium")) return;

            switch (reminder_priority)
            {
                case "Low":
                    linearLayout.setBackgroundColor(Color.GREEN);
                    break;

                case "High":
                    linearLayout.setBackgroundColor(Color.RED);
                    break;

                default: break;
            }

        }

        public void setDetails(Reminder reminder)
        {
            reminderNameTxt.setText(reminder.getName());
            reminderPriorityLevelTxt.setText(("Priority: " + reminder.getPriorityLevel()));
            reminderTimeTxt.setText(reminder.getTime());

            updateLayoutColor(reminder.getPriorityLevel());
        }
    }
}
