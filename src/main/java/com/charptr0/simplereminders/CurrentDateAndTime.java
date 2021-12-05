package com.charptr0.simplereminders;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Get the current date and time in "MM DD YY at HH:MM" format
 *
 * @author Chenhao Li
 * @version 1.0
 */
public class CurrentDateAndTime
{
    private CurrentDateAndTime() {}

    public static long getUnixTime()
    {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * Get the current time
     * @return HH:MM format
     */
    public static String getCurrentTime() {

        //get calender
        Calendar calendar = Calendar.getInstance();

        //get the timezone in where the program is currently running
        calendar.setTimeZone(TimeZone.getDefault());

        Date time = calendar.getTime();

        //formatting the time to be in HH:MM format
        String[] line = time.toString().split(" ");

        String[] raw_times = line[3].split(":");

        return raw_times[0] + ":" + raw_times[1];
    }

    /**
     * Get the current date
     * @return MM DD YY format
     */
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());

        //format the date to be in MM DD YY format
        String rawDate = calendar.getTime().toString();
        String[] line = rawDate.split(" ");

        return String.format("%s %s %s", line[1], line[2], line[5]);
    }

    public static String getCurrentDateAndTimeFormatted()
    {
        return String.format("%s at %s", getCurrentDate(), getCurrentTime());
    }
}
