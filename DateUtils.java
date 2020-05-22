import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility method about date and time in the auction program.
 * 
 * Created by Kla & Tong 18 May 2020
 */
public class DateUtils
{
    /**
     * Date format for date
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * DateTime format for date and time
     */
    private static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm");

    /**
     * Check the date input that is after current date or not
     * 
     * @param date Date that want to check with current date.
     * @return true when date is after current date. Otherwise false.
     */
    public static boolean isAfterCurrentDateTime(Date date)
    {
        Date dateNow = new Date();
        if (date != null && date.after(dateNow))
            return true;
        else
            return false;
    }

    /**
     * Check the date input that is before current date or not
     * 
     * @param date Date that want to check with current date.
     * @return true when date is before current date. Otherwise false.
     */
    public static boolean isBeforeCurrentDateTime(Date date)
    {
        Date dateNow = new Date();          /* Current date time */
        if (date != null && date.before(dateNow))
            return true;
        else
            return false;
    }

    /**
     * Find the difference of date and time with the current time.
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param dateCpr is date to be compare.
     * @return Difference of date and time in term of integer array.
     *         Index 0 is days, index 1 is hours, index 2 is minutes, index 3 is seconds
     */
    public static int[] diffCurrentDateTime(Date dateCpr)
    {
        Date dateNow = new Date();          /* Current date time */
        int diffDateTime[] = new int[4];    /* Used to return date time */

        try
        {
            /* In millisecs */
            long diff = Math.abs(dateNow.getTime() - dateCpr.getTime());

            long diffSeconds = (diff / 1000) % 60;
            long diffMinutes = (diff / (60 * 1000)) % 60;
            long diffHours = (diff / (60 * 60 * 1000)) % 24;
            long diffDays = (diff / (24 * 60 * 60 * 1000));

            diffDateTime[0] = (int) diffDays;
            diffDateTime[1] = (int) diffHours;
            diffDateTime[2] = (int) diffMinutes;
            diffDateTime[3] = (int) diffSeconds;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return diffDateTime;
    }

    /**
     * Convert from string format to Date type.
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param dateStr String of date format
     * @return Date instance. But if dateStr is wrong format, null.
     */
    public static Date strToDate(String dateStr)
    {
        if (IOUtils.isNullStr(dateStr))
            return null;
        try
        {
            simpleDateFormat.setLenient(false);
            Date javaDate = simpleDateFormat.parse(dateStr);
            return javaDate;
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * Convert from string format to DateTime type.
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param dateTimeStr String of date format
     * @return DateTime instance. But if dateStr is wrong format, null.
     */
    public static Date strToDateTime(String dateTimeStr)
    {
        if (IOUtils.isNullStr(dateTimeStr))
            return null;
        try
        {
            simpleDateTimeFormat.setLenient(false);
            Date javaDate = simpleDateTimeFormat.parse(dateTimeStr);
            return javaDate;
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * Convert from Date type to string format
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param date Date type that want to convert to string of date format
     * @returnDate string format. If can't convert, null.
     */
    public static String dateToStr(Date date)
    {
        simpleDateTimeFormat.setLenient(false);
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }

    /**
     * Convert from DateTime type to string format
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param dateTime DateTime type that want to convert to string of date format
     * @return Date string format. If can't convert, null.
     */
    public static String dateTimeToStr(Date dateTime)
    {
        simpleDateTimeFormat.setLenient(false);
        String strDate = simpleDateTimeFormat.format(dateTime);
        return strDate;
    }

    /**
     * Validate string of date to check pattern of date in format dd-MM-yyyy
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param date Date in string that want to validate
     * @return True if date is correct pattern.
     *         False if date is incorrect pattern.
     */
    public static boolean validateDateStr(String date)
    {
        if (IOUtils.isNullStr(date)) /* Check null */
            return false;
        try /* Check format */
        {
            simpleDateFormat.setLenient(false);
            if (simpleDateFormat.parse(date) != null)
                return true;
            else
                return false;
        }
        catch (ParseException e)
        {
            return false;
        }
    }

    /**
     * Validate string of date and time.
     * Check pattern of date and time in format dd-MM-yyyy-HH:mm:ss
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param dateTime Date time in string that want to validate
     * @return True if date and time is correct pattern.
     *         False if date and time is incorrect pattern.
     */
    public static boolean validateDateTimeStr(String dateTime)
    {
        if (IOUtils.isNullStr(dateTime))
            return false;
        try
        {
            simpleDateTimeFormat.setLenient(false);
            if (simpleDateTimeFormat.parse(dateTime) != null)
                return true;
            else
                return false;
        }
        catch (ParseException e)
        {
            return false;
        }
    }

}
