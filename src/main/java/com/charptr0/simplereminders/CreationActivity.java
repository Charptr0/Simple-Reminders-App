package com.charptr0.simplereminders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreationActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_layout);
    }

    public void advanceOptionsBtnHandler(View view)
    {
        Intent intent = new Intent(CreationActivity.this, CalenderActivity.class);
        startActivity(intent);
    }

    public void quickSetOpenDialog(View view)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CreationActivity.this);
        dialog.setTitle("Choose a time slot");
        dialog.setPositiveButton("Done", null);

        String currentDate = CurrentDate.getDateFormatted();

        String[] preSetTimes = {"1 minute from now","5 minutes from now","10 minutes from now",
                "15 minutes from now","30 minutes from now", "1 hour from now",
                "8 hours from now", "12 hours from now", "1 day from now"};

        int checkedTime = 5;

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

            }
        });

        AlertDialog alert = dialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }
}
