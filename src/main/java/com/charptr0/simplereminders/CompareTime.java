package com.charptr0.simplereminders;

/**
 * Compare the user input time and date with the current time and date
 *
 * @author Chenhao Li
 * @version 1.0
 */
public class CompareTime
{
    private CompareTime() {}

    //indices
    private static final int MONTH = 0;
    private static final int DAYS = 1;
    private static final int YEAR = 2;
    private static final int TIME = 4;
    private static final int HOUR = 0;
    private static final int MINUTE = 1;

    /**
     * Change month from a string to its corresponding int
     * @param month Jan - Dec
     * @return integer representation of the month
     */
    private static int parseMonths(String month)
    {
        switch (month)
        {
            case "Jan": return 1;
            case "Feb": return 2;
            case "Mar": return 3;
            case "Apr": return 4;
            case "May": return 5;
            case "Jun": return 6;
            case "Jul": return 7;
            case "Aug": return 8;
            case "Sep": return 9;
            case "Oct": return 10;
            case "Nov": return 11;
            case "Dec": return 12;
            default: break;
        }

        return 0;
    }

    /**
     * Check to make sure the user input date and time are valid
     * The input date and time MUST be in the future, or greater than the current date or time
     *
     * @param dateAndTime user inputed date and time information in "MM DD YY at HH:MM" format
     * @return the user input date and time is valid or not
     */
    public static boolean isValidDateTime(String dateAndTime)
    {
        //get the user input for month, day, year, hour, and minute
        String[] userInput = dateAndTime.split(" ");
        String[] userInputTime = userInput[TIME].split(":");
        int inputMonth = parseMonths(userInput[MONTH]);
        int inputDay = Integer.parseInt(userInput[DAYS]);
        int inputYear = Integer.parseInt(userInput[YEAR]);
        int inputHour = Integer.parseInt(userInputTime[HOUR]);
        int inputMinute = Integer.parseInt(userInputTime[MINUTE]);

        //get the current month, day, year, hour, and minute
        String[] currentDate = CurrentDateAndTime.getCurrentDate().split(" ");
        String[] currentTime = CurrentDateAndTime.getCurrentTime().split(":");
        int currentMonth = parseMonths(currentDate[MONTH]);
        int currentDay = Integer.parseInt(currentDate[DAYS]);
        int currentYear = Integer.parseInt(currentDate[YEAR]);
        int currentHour = Integer.parseInt(currentTime[HOUR]);
        int currentMinute = Integer.parseInt(currentTime[MINUTE]);

        //the comparison between the two dates

        //input year is BIGGER than the current e.g 2022 > 2021
        if(inputYear > currentYear) return true;

        //input year is the same AND the input month is BIGGER than the current e.g 12/22/21 > 11/22/21
        if(inputYear == currentYear && inputMonth > currentMonth) return true;

        //input month is the same AND the input day is BIGGER than the current e.g 12/20/21 > 12/10/21
        else if(inputMonth == currentMonth && inputDay > currentDay) return true;

        //input day is the same AND the input hr is BIGGER than the current e.g 12:00 > 17:00
        else if(inputDay == currentDay && inputHour > currentHour) return true;

        //input hour is the same AND the input mim is BIGGER than the current e.g 12:14 > 12:22
        else if(inputHour == currentHour && inputMinute > currentMinute) return true;

        //else, the input date is not valid
        return false;
    }
}
