package com.charptr0.simplereminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "reminders.db";
    private static final String TABLE_NAME = "reminders";
    private static final String COLUMN_1 = "name";
    private static final String COLUMN_2 = "priority_level";
    private static final String COLUMN_3 = "time";
    private static final String COLUMN_4 = "priority_id";
    private static final String COLUMN_5 = "time_id";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_1 + " TEXT, " +
                COLUMN_2 + " TEXT, " +
                COLUMN_3 + " TEXT," +
                COLUMN_4 + " INT, " +
                COLUMN_5 + " BIGINT)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addReminder(Reminder reminder)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //reminder's name
        cv.put(COLUMN_1, reminder.getName());

        //reminder's priority
        cv.put(COLUMN_2, reminder.getPriorityLevel());

        //reminder's time
        cv.put(COLUMN_3, reminder.getTime());

        //raw priority id: 0 -> no priority | 1 -> low | 2 -> medium | 3 -> high
        cv.put(COLUMN_4, reminder.getPriorityId());

        //raw time (unix time)
        cv.put(COLUMN_5, CurrentDateAndTime.getUnixTime());

        long status = db.insert(TABLE_NAME, null, cv);

        db.close();

        return status != 1;
    }
}
