package com.charptr0.simplereminders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Reminder>listOfReminders = new ArrayList<>();
    private TextView noReminderText;
    private RecyclerView recyclerView;
    private ReminderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(BuildConfig.DEBUG)
            StrictMode.enableDefaults();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noReminderText = findViewById(R.id.noReminderText);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReminderAdapter(this, listOfReminders);

        recyclerView.setAdapter(adapter);

        if(!listOfReminders.isEmpty()) noReminderText.setVisibility(View.INVISIBLE);
    }

    public void creationButtonHandler(View view){
        Intent intent = new Intent(MainActivity.this, CreationActivity.class);
        startActivityForResult(intent, 1);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            assert data != null;

            Reminder r = data.getParcelableExtra("reminder");
            listOfReminders.add(r);

            adapter.notifyDataSetChanged();

        }

        if(!listOfReminders.isEmpty()) noReminderText.setVisibility(View.INVISIBLE);

    }
}