package com.charptr0.simplereminders;

import java.util.Calendar;
import java.util.Date;

public class CurrentTime
{
    private CurrentTime() {}

    public static String getCurrentTime() {
        Date time = Calendar.getInstance().getTime();

        String[] line = time.toString().split(" ");

        String[] raw_times = line[3].split(":");

        return raw_times[0] + ":" + raw_times[1];
    }
}
