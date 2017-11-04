
package org.homelinux.client;

import java.util.Date;

/**
 *Provides standardization methods for the module.  Standardizes cookie expiration, time offset.
 * Also standardizes a method for saving date information as a short integer date code
 * @author nelson
 */
public class Defaults {


/**
 * Gets the default expiration.  This is meant for use as as the default expiration for cookies.
 * @return A Date object set to 7 days before the current date.
 */
    public static Date getExpiration()
    {
        Date now = new Date();
        long nowLong = now.getTime();
        nowLong = nowLong + (1000 * 60 * 60 * 24 * 7);//seven days
        now.setTime(nowLong);
        return now;
    }

/**
 * Gets the current Date offset by 7 hours to match Coordinated Universal Time
 * @return the current date + 7 hours.
 */
    public static Date getNow()
    {
        long currentTime = System.currentTimeMillis();
        long offset = 1000 * 60 * 60 * 8;
        Date now = new Date(currentTime + offset);
        return now;
    }

    /**
     * Translates a date input in the format YYYY-MM-DD into an integer representing the number of days since Jan 1, 2000.
     * The datecode is an easy way to determine the dates of videos stored in cookes to differentiate which video a user
     * has watched in the last 7 days.
     * @param a date in the format YYYY-MM-DD
     * @return an integer value representing the number of days since Jan 1, 2000
     */

    public static int dateCode(String date)
    {
        int[] months = {
            31, //January
            28, //February
            31, //March
            30, //April
            31, //May
            30, //June
            31, //July
            31, //August
            30, //September
            31, //October
            30, //November
            31 //December
        };

        //get the date on which the video was published and put it into three separate int variables for month,day, and year
        String[] d =
                (date.split(" "))[0].split("-");
        int year = Integer.parseInt(d[0]);
        int month = Integer.parseInt(d[1]);
        int day = Integer.parseInt(d[2]);

        //totalDays keeps track of the total number of days since january 1st
        int totalDays = 0;

        //System.out.println("year: "+ year + "\nmonth: "+month+ "\nday: "+day +"\ntotalDays: "+totalDays);

        if (year % 400 == 0) {
            months[2] = 29;
        } else if (year % 100 == 0) {
            //skip
        } else if (year % 4 == 0) {
            months[2] = 29;
        }

        for (int i = 1; i < month; i++) {
            totalDays += months[i - 1];
        }

        int y = (year - 2000);
        y *= 365; // days since the year 2000
        totalDays += day + y;
        // System.out.println("\n\n\nyear: " + year + "\nmonth: " + month + "\nday: " + day + "\ntotalDays: " + totalDays);

        return totalDays;
    }
}
