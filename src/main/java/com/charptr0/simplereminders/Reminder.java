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
     * Raw unix time will be used to represent the reminder
     */
    private final long ID;

    /**
     * Time in seconds needed to wait for the notification to trigger
     *
     * Time to notify = ID + this
     */
    private final int WAIT_TIME_SECONDS;

    public Reminder(String name, String priority_level, String time, String id, String waitTimeSeconds)
    {
        this.NAME = name;
        this.PRIORITY_LEVEL = priority_level;
        this.TIME = time;
        this.ID = Long.parseLong(id);
        this.WAIT_TIME_SECONDS = Integer.parseInt(waitTimeSeconds);
    }

    protected Reminder(Parcel in) {
        NAME = in.readString();
        PRIORITY_LEVEL = in.readString();
        TIME = in.readString();
        ID = in.readLong();
        WAIT_TIME_SECONDS = in.readInt();
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

    /**
     * Get the name of the reminder
     * @return the name of the reminder
     */
    public String getName() {
        return NAME;
    }

    /**
     * Get the priority level of the reminder
     * @return "Low", "Medium" or "High"
     */
    public String getPriorityLevel() {
        return PRIORITY_LEVEL;
    }

    /**
     * Get the time of the reminder
     * @return the time in MM/DD/YY at HH::MM format
     */
    public String getTime() {
        return TIME;
    }

    /**
     * @return get the wait time
     */
    public int getWaitTimeSeconds() {
        return WAIT_TIME_SECONDS;
    }

    public int getPriorityId()
    {
        switch (this.PRIORITY_LEVEL)
        {
            case "Low" : return 1;
            case "Medium" : return 2;
            case "High" : return 3;
        }

        return 0;
    }

    /**
     * Get the id of the reminder
     * @return the id
     */
    public long getId() {
        return ID;
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
        dest.writeLong(ID);
        dest.writeInt(WAIT_TIME_SECONDS);
    }
}
