package com.charptr0.simplereminders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.charptr0.simplereminders.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    public void customOptionsBtnHandler(View view)
    {
        Intent intent = new Intent(MainActivity.this, CalenderActivity.class);
        startActivity(intent);
    }

    public void quickSetOpenDialog(View view)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Choose a time slot");
        dialog.setPositiveButton("Done", null);

        String[] preSetTimes = {"1 minute from now","5 minutes from now","10 minutes from now",
                "15 minutes from now","30 minutes from now", "1 hour from now",
                "8 hours from now", "12 hours from now", "1 day from now"};

        int checkedTime = 5;

        dialog.setSingleChoiceItems(preSetTimes, checkedTime, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(MainActivity.this, "1 minutes from now", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "5 minutes from now", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "10 minutes from now", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "15 minutes from now", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                    Toast.makeText(MainActivity.this, "30 minutes from now", Toast.LENGTH_SHORT).show();
                        break;

                    case 5:
                        Toast.makeText(MainActivity.this, "1 hour from now", Toast.LENGTH_SHORT).show();
                        break;

                    case 6:
                        Toast.makeText(MainActivity.this, "8 hours from now", Toast.LENGTH_SHORT).show();
                        break;

                    case 7:
                        Toast.makeText(MainActivity.this, "12 hours from now", Toast.LENGTH_SHORT).show();
                        break;

                    case 8:
                        Toast.makeText(MainActivity.this, "1 day from now", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }
            }
        });

        AlertDialog alert = dialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}