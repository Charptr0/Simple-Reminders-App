package com.charptr0.simplereminders;

import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

/**
 * Get and calculate the future time in "MM DD YY at HH:MM" format
 *
 * @author Chenhao Li
 * @version 1.0
 */
public class FutureDateAndTime
{
    private FutureDateAndTime() {}

    /**
     * Calculate the future time
     * @param seconds second after the current time
     * @return "MM DD YY at HH:MM" format
     */
    public static String getFutureTime(final int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());

        calendar.add(Calendar.SECOND, seconds);

        return getFutureTimeFormatted(calendar.getTime().toString());
    }

    /**
     * Calculate the time in seconds needed to wait for a notification
     * @return seconds needed to wait in order to send a notification
     */
    public static long getTimeInBetween(int year, int month, int day, int hr, int min)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hr, min);

        long waitTimeInSeconds = calendar.getTimeInMillis() / 1000L;

        return waitTimeInSeconds - CurrentDateAndTime.getUnixTime();
    }

    private static String getFutureTimeFormatted(String rawTimeAndDate)
    {
        String[] line = rawTimeAndDate.split(" ");

        String[] raw_times = line[3].split(":");

        String formattedTime = raw_times[0] + ":" + raw_times[1];

        String formattedDate = String.format("%s %s %s", line[1], line[2], line[5]);

        return String.format("%s at %s", formattedDate, formattedTime);
    }

}
