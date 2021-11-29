package com.charptr0.simplereminders;

import java.util.Calendar;

/**
 * CurrentDate - Gets the current date in MM/DD/YY format
 *
 * @author Chenhao Li
 * @version 1.0
 */
public class CurrentDate
{
    private CurrentDate() {}

    private static int getDayOfMonth(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private static int getMonth(){
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    private static int getYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String getDateFormatted() {
        return getMonth() + "/" + getDayOfMonth() + "/" + getYear();
    }

}
