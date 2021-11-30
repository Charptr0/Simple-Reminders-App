package com.charptr0.simplereminders;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Reminder.java - Hold all the information needed to create a reminder tab
 *
 * @author Chenhao Li
 * @version 1.0
 */
public class Reminder implements Parcelable {

    /**
     * Stores the reminder's name
     */
    private final String NAME;

    /**
     * Stores the reminder's priority level
     */
    private final String PRIORITY_LEVEL;

    /**
     * Stores the reminder's time
     */
    private final String TIME;

    /**
     * Default constructor (FOR TESTING)
     */
    public Reminder() {
        this.NAME = "Untitled Reminder";
        this.PRIORITY_LEVEL = "Medium";
        this.TIME = "";
    }

    public Reminder(String name, String priority_level, String time)
    {
        this.NAME = name;
        this.PRIORITY_LEVEL = priority_level;
        this.TIME = time;
    }

    protected Reminder(Parcel in) {
        NAME = in.readString();
        PRIORITY_LEVEL = in.readString();
        TIME = in.readString();
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    public String getName() {
        return NAME;
    }

    public String getPriorityLevel() {
        return PRIORITY_LEVEL;
    }

    @Override
    public String toString() {
        return String.format("Reminder Name: %s\nPriority Level: %s\nTime: %s", this.NAME, this.PRIORITY_LEVEL, this.TIME);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NAME);
        dest.writeString(PRIORITY_LEVEL);
        dest.writeString(TIME);
    }
}
