package com.core.app.JavaWebApp.Utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /** The Constant FORMAT_DEFAULT. */
    private static final String FORMAT_DEFAULT = "dd/MM/yyyy";

    /** The Constant FORMAT_DD_MM_YYYY. */
    public static final String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";

    /** The Constant FORMAT_YYYY_MM_dd. */
    public static final String FORMAT_YYYY_MM_dd = "yyyy/MM/dd";

    /** The Constant FORMAT_DD_MM_YY. */
    public static final String FORMAT_DD_MM_YY = "dd/MM/yy";

    /** The Constant FORMAT_DD_DOT_MM_DOT_YYYY_HH_MM. */
    public static final String FORMAT_DD_DOT_MM_DOT_YYYY_HH_MM = "dd.MM.yyyy HH:mm";

    /** The Constant FORMAT_HH_MM_A. */
    public static final String FORMAT_HH_MM_A = "h:mm a";

    /** The Constant FORMAT_DD_MM_YYYY_HH_MM_SS. */
    public static final String FORMAT_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

    /** The Constant FORMAT_DD_MM_YYYY_HH_MM_SS_A. */
    public static final String FORMAT_DD_MM_YYYY_HH_MM_SS_A = "dd/MM/yyyy h:mm:ss a";

    /** The Constant FORMAT_YYYYMMDDHHMMSS. */
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /** The Constant FORMAT_YYYYMMDD. */
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";

    /** The Constant FORMAT_DDMMYYYY. */
    public static final String FORMAT_DDMMYYYY = "ddMMyyyy";

    /** The Constant FORMAT_YYYY. */
    public static final String FORMAT_YYYY = "yyyy";

    /** The Constant FORMAT_DD_MM_YYYY_HH_MM. */
    public static final String FORMAT_DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";

    /** The Constant FORMAT_YYYY_MM_DD_HH_MM_SS_SSS. */
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy/MM/dd HH:mm:ss,SSS";

    /** The Constant FORMAT_DDMMYYYYHHMMSS. */
    public static final String FORMAT_DDMMYYYYHHMMSS = "ddMMyyyyHHmmss";

    /** The Constant FORMAT_DDMMYYHHMMSS. */
    public static final String FORMAT_DDMMYYHHMMSS = "ddMMyyHHmmss";

    /** The Constant FORMAT_TIMESTAMP. */
    public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

    /** The Constant FORMAT_DD_MM_YYYY_HH24_MM_SS. */
    public static final String FORMAT_DD_MM_YYYY_HH24_MM_SS = "dd-MM-yyyy HH:mm:ss";

    /** The Constant FORMAT_DD_DOT_MM_DOT_YYYY_HH_MM_SS. */
    public static final String FORMAT_DD_DOT_MM_DOT_YYYY_HH_MM_SS = "dd.MM.yyyy HH:mm:ss";

    /** The Constant FORMAT_DD MMM YYYY. */
    public static final String FORMAT_DD_MMM_YYYY = "dd MMM yyyy";
    /** The Constant FORMAT_DD MMM YYYY. */
    public static final String FORMAT_DDdashMMMdashYYYY = "dd-MMM-yyyy";



    /** The Constant FORMAT_DD-MM-YYYY : eg.26-03-1887 */
    public static final String FORMAT_DDdashMMdashYYYY = "dd-MM-yyyy";

    /**
     * Description: This method parsed java.util.Date type to java.lang.String
     * by default string format "dd/MM/yyyy"
     *
     * @param date the date
     * @return the string
     */
    public static String parseDate(java.util.Date date) {

        String s = null;

        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(FORMAT_DEFAULT);
            s = df.format(date);
        }

        return s;
    }

    /**
     * Description: This method parsed java.util.Date type to java.lang.String
     * by specified string format
     *
     * @param date the date
     * @param format the format
     * @return the string
     */
    public static String parseDate(java.util.Date date, String format) {

        String s = null;

        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            s = df.format(date);
        }

        return s;
    }

    /**
     * Description: This method parsed java.sql.Date type to java.lang.String by
     * default string format "dd/MM/yyyy"
     *
     * @param date the date
     * @return the string
     */
    public static String parseDate(java.sql.Date date) {

        String s = null;

        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(FORMAT_DEFAULT);
            s = df.format(date);
        }

        return s;
    }

    /**
     * Description: This method parsed java.sql.Date type to java.lang.String by
     * specified string format
     *
     * @param date the date
     * @param format the format
     * @return the string
     */
    public static String parseDate(java.sql.Date date, String format) {

        String s = null;

        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            s = df.format(date);
        }

        return s;
    }

    /**
     * Description: This method parsed java.sql.Timestamp type to
     * java.lang.String by default string format "dd/MM/yyyy"
     *
     * @param t the t
     * @param format the format
     * @return the string
     */
    public static String parseTime(java.sql.Timestamp t, String format) {

        String s = null;

        if (t != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            s = df.format(t);
        }

        return s;
    }

    /**
     * Description: This method parsed java.sql.Time type to java.lang.String by
     * specified string format
     *
     * @param t the t
     * @param format the format
     * @return the string
     */
    public static String parseTime(java.sql.Time t, String format) {

        String s = null;

        if (t != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            s = df.format(t);
        }

        return s;
    }

    /**
     * Description: This method get current system date.
     *
     * @return the system date
     */
    public static Date getSystemDate() {

        return new Date(System.currentTimeMillis());
    }

    /**
     * Description: This method get current system time stamp.
     *
     * @return the system timestamp
     */
    public static Timestamp getSystemTimestamp() {

        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * Description: This method get current system time.
     *
     * @return the system time
     */
    public static Time getSystemTime() {

        return new Time(System.currentTimeMillis());
    }

    /**
     * Description: This method parsed java.lang.String type to java.util.Date
     * by specified string format
     *
     * @param s the s
     * @param format the format
     * @return the java.util. date
     */
    public static java.util.Date strToDate(String s, String format) {

        java.util.Date d = null;

        if (s != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            try {
                d = df.parse(s);
            } catch (ParseException e) {
                // log.error(e);
                d = null;
            }
        }

        return d;
    }

    /**
     * Description: This method will parse java.lang.String type to java.sql.Timestamp by
     * specified string format
     *
     * @param strDate the str date
     * @param format the format
     * @return the java.sql. timestamp
     */
    public static java.sql.Timestamp strToTimestamp(String strDate, String format) {
        Date date = strToDate(strDate, format);
        if (date != null){
            return new java.sql.Timestamp(date.getTime());
        }
        else{
            return null;
        }
    }

    /**
     * Description: This method parsed java.lang.String type to java.sql.Date by
     * specified string format
     *
     * @param s the s
     * @param format the format
     * @return the java.sql. date
     */
    public static java.sql.Date strToSqlDate(String s, String format) {

        java.util.Date d = null;

        if (s != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            try {
                d = df.parse(s);
            } catch (ParseException e) {
                // log.error(e);
                d = null;
            }
        }

        if (d != null)
            return new java.sql.Date(d.getTime());
        else
            return null;

    }

    /**
     * Return the date after addition of days
     * Supports subtraction by passing in negative parameter.
     *
     * @param date Date
     * @param days int
     * @return int
     */
    public static Date addDays(Date date, int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * Return the date after addition of hours
     * Supports subtraction by passing in negative parameter.
     *
     * @param date the date
     * @param hours the hours
     * @return the date
     */
    public static Date addHours(Date date, int hours)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * Return the date after addition of months
     * Supports subtraction by passing in negative parameter.
     *
     * @param date Date
     * @param months int
     * @return int
     */
    public static Date addMonths(Date date, int months)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * Return the day difference of 2 dates.
     *
     * @param startDate Date
     * @param endDate Date
     * @return int
     */
    public static int dateDiff(Date startDate, Date endDate)
    {
        return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * Return the time difference in minutes between 2 dates.
     *
     * @param startDate Date
     * @param endDate Date
     * @return int
     */
    public static long timeDiff(Date startDate, Date endDate) {
        return ((endDate.getTime() - startDate.getTime()) / (1000 * 60));
    }

    /**
     * Convert a string in DD/MM/YYYY format to date object.
     *
     * @param sDate String
     * @return Date
     */
    public static Date toDate(String sDate) {
        Date date = null;

        if ((sDate != null) && (sDate.length() == 10)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(sDate.substring(6)),
                    Integer.parseInt(sDate.substring(3, 5)) - 1,
                    Integer.parseInt(sDate.substring(0, 2)));
            date = calendar.getTime();
        }
        return date;
    }

    /**
     * Convert a string in DD/MM/YYYY format to date object
     * Also allows to specify hour, minutes, seconds, milliSeconds.
     *
     * @param sDate String
     * @param hour the hour
     * @param minutes the minutes
     * @param seconds the seconds
     * @param milliSeconds the milli seconds
     * @return Date
     */
    public static Date toDate(String sDate, int hour, int minutes, int seconds, int milliSeconds ) {
        Date date = null;

        if ((sDate != null) && (sDate.length() == 10)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(sDate.substring(6)),
                    Integer.parseInt(sDate.substring(3, 5)) - 1,
                    Integer.parseInt(sDate.substring(0, 2)));
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, seconds);
            calendar.set(Calendar.MILLISECOND, milliSeconds);

            date = calendar.getTime();
        }
        return date;
    }

    /**
     * Check if the first date is after the second date ignoring time.
     *
     * @param date1 Date
     * @param date2 Date
     * @return boolean
     */
    public static boolean isAfterDate(Date date1, Date date2) {
        return date1.after(date2);
    }


    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {

        DateUtil util = new DateUtil();
    }


    // newly added
    /**
     * Convert partial date format from YYYYMMDD to DD/MM/YYYY format.
     *
     * @param partialDate String
     * @return String
     */
    public static String formatPartialDate(String partialDate) {
        String date = null;

        if (partialDate != null) {
            if (partialDate.length() == 8) {
                date = partialDate.substring(6) + "/" +
                        partialDate.substring(4, 6) + "/" +
                        partialDate.substring(0, 4);
            }
            else if (partialDate.length() == 6) {
                date = partialDate.substring(4) + "/" +
                        partialDate.substring(0, 4);
            }
            else if (partialDate.length() == 4) {
                date = partialDate;
            }
        }

        return date;
    }

    /**
     * Convert partial date format from YYYYMMDD to DD/MM/YYYY format
     * if date is already in DD/MM/YYYY format it will return itself as normal.
     * @param partialDate String
     * @return String
     */
    public static String formatForCalendarDisplay(String partialDate) {
        String date = null;

        if (partialDate != null) {
            if (partialDate.length() == 8) {
                date = partialDate.substring(6) + "/"
                        + partialDate.substring(4, 6) + "/"
                        + partialDate.substring(0, 4);
            } if(partialDate.length() == 10){
                if(partialDate.charAt(2)==('/')&& partialDate.charAt(5)==('/')){
                    date = partialDate;
                }
            }
        }
        return date;
    }

    /**
     * Convert Date to DD/MM/YYYY format.
     *
     * @param partialDate Date
     * @return String
     */
    public static String formatForCalendarDisplay(Date partialDate) {
        SimpleDateFormat sdf =new SimpleDateFormat(FORMAT_DD_MM_YYYY);
        return sdf.format(partialDate);
    }

    /**
     * Convert two Strings first is (date) to be validate and second is the format.
     *
     * @param date the date
     * @param format the format
     * @return boolean
     */

    public static boolean isDateValid(String date, String format){
        try {
            Date dateSimple = new SimpleDateFormat(format).parse(date);

            Format formatter = new SimpleDateFormat(format);

            if (!date.equals(formatter.format(dateSimple))) {
                return false;
            }

            return true;
        }
        catch (ParseException e) {
            return false;
        }
    }


    /**
     * To check if date1 is earlier than date2.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return true, if is earlier day
     */
    public static boolean isEarlierDay(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date2);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);
        return date1.before(calendar.getTime());
    }

    /**
     * Gets the elapsed time.
     *
     * @param milisDiff the milis diff
     * @return the elapsed time
     */
    public static String getElapsedTime(long milisDiff) {
        if(milisDiff<1000){ return "0 second";}
        String formattedTime = "";
        long secondInMillis = 1000;
        long minuteInMillis = secondInMillis * 60;
        long hourInMillis = minuteInMillis * 60;
        long dayInMillis = hourInMillis * 24;
        int timeElapsed[] = new int[4];
        // Define time units - plural cases are handled inside loop
        String timeElapsedText[] = {"second", "minute", "hour", "day"};
        timeElapsed[3] = (int) (milisDiff / dayInMillis); // days
        milisDiff = milisDiff % dayInMillis;
        timeElapsed[2] = (int) (milisDiff / hourInMillis); // hours
        milisDiff = milisDiff % hourInMillis;
        timeElapsed[1] = (int) (milisDiff / minuteInMillis); // minutes
        milisDiff = milisDiff % minuteInMillis;
        timeElapsed[0] = (int) (milisDiff / secondInMillis); // seconds
        // Only adds 3 significant high valued units
        for(int i=(timeElapsed.length-1), j=0; i>=0 && j<3; i--){         // loop from high to low time unit
            if(timeElapsed[i] > 0){
                formattedTime += ((j>0)? ", " :"")
                        + timeElapsed[i]
                        + " " + timeElapsedText[i]
                        + ( (timeElapsed[i]>1)? "s" : "" );
                ++j;
            }
        } // end for - build string
        return formattedTime;
    }
}