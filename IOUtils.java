import java.util.Date;
import java.util.Scanner;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represent the utility method in the auction program. Contain the helper method.
 * 
 * Created by Kla & Tong 18 May 2020
 */
public class IOUtils
{
    /**
     * Date format for date
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd-MM-yyyy");

    /**
     * DateTime format for date and time
     */
    private static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(
            "dd-MM-yyyy-HH:mm:ss");

    /**
     * Check the string is null or not.
     * 
     * @param string that will be check.
     * @return true, when the string is null. false, when the string is not null.
     */
    public static boolean isNullStr(String string)
    {
        if (string.trim().equals(""))
            return true;
        else
            return false;
    }

    /**
     * Check the date input that is after current date or not
     * 
     * @param date to be check with current date.
     * @return true when date is after current date. Otherwise false.
     */
    public static boolean isAfterCurrentDateTime(Date date)
    {
        Date dateNow = new Date();
        if (date.after(dateNow))
            return true;
        else
            return false;
    }

    /**
     * Find the difference in date and time from current time with date and time from
     * user.
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param dateCpr is date to be compare.
     * @return difference of date and time in term of integer array. Index 0 is days,
     *         index 1 is hours, index 2 is minutes, index 3 is seconds
     */
    public static int[] diffCurrentDateTime(Date dateCpr)
    {
        Date dateNow = new Date();
        int diffDateTime[] = new int[4];

        try
        {
            // in milliseconds
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
     * Change from string format to date
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param date String date format that want to create date instance
     * @return Date instance. If can't convert, null.
     */
    public static Date strToDate(String dateStr)
    {
        if (isNullStr(dateStr))
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
     * Change from string format to date time
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param date and time that going to create
     * @return Date instance. If can't convert, null.
     */
    public static Date strToDateTime(String dateTimeStr)
    {
        if (isNullStr(dateTimeStr))
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
     * Change from string format to date
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param date String date format that want to create date instance
     * @return Date instance. If can't convert, null.
     */
    public static String dateToStr(Date date)
    {
        simpleDateTimeFormat.setLenient(false);
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }

    /**
     * Change from string format to date time
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param date and time that going to create
     * @return Date instance. If can't convert, null.
     */
    public static String dateTimeToStr(Date dateTime)
    {
        simpleDateTimeFormat.setLenient(false);
        String strDate = simpleDateTimeFormat.format(dateTime);
        return strDate;
    }

    /**
     * Validate the username in pattern of 6-30 characters with consist of alphabet,
     * number and underscore. First character can't be number.
     * 
     * Reference from
     * https://www.geeksforgeeks.org/how-to-validate-a-username-using-regular-expressions-in-java/
     * 
     * @param username is username to be validate
     * @return True, when username is in correct pattern. False, when username is not
     *         in correct pattern.
     */
    public static boolean validateUsername(String username)
    {
        if (isNullStr(username))
            return false;
        String usernamePattern = "^[aA-zZ]\\w{5,29}$";
        if (username.matches(usernamePattern))
            return true;
        else
            return false;
    }

    /**
     * Validate the password in pattern of 8-40 characters with consist of at least
     * one number, one lower character, one upper character, one special that is [ @
     * # $ % ! . ].
     * 
     * Reference from
     * https://examples.javacodegeeks.com/core-java/util/regex/matcher/validate-password-with-java-regular-expression-example/
     * 
     * @param password is password to be validate
     * @return @return True, when password is in correct pattern. False, when
     *         password is not in correct pattern.
     */
    public static boolean validatePassword(String password)
    {
        if (isNullStr(password))
            return false;
        String passwordPattern = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
        if (password.matches(passwordPattern))
            return true;
        else
            return false;
    }

    /**
     * Validate the email in pattern of email
     * 
     * Reference from https://www.tutorialspoint.com/validate-email-address-in-java
     * 
     * @param email is email to be validate
     * @return True, when email is in correct pattern. False, when email is not in
     *         correct pattern.
     */
    public static boolean validateEmail(String email)
    {
        if (isNullStr(email))
            return false;
        String emailPattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.matches(emailPattern))
            return true;
        else
            return false;
    }

    /**
     * Validate date to check pattern of date in format dd-MM-yyyy
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param date that going to be validate
     * @return True, when date is correct pattern. False, when date is incorrect
     *         pattern.
     */
    public static boolean validateDateStr(String date)
    {
        if (isNullStr(date))
            return false;
        try
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
     * Validate date and time to check pattern of date and time in format
     * dd-MM-yyyy-HH:mm:ss
     * 
     * Reference from
     * https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/
     * 
     * @param dateTime that going to be validate
     * @return True, when date and time is correct pattern. False, when date and time
     *         is incorrect pattern.
     */
    public static boolean validateDateTimeStr(String dateTime)
    {
        if (isNullStr(dateTime))
            return false;
        try
        {
            simpleDateFormat.setLenient(false);
            if (simpleDateFormat.parse(dateTime) != null)
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
     * Validate integer the string parameter must contain only integer in format of
     * string
     * 
     * @param string is string to be validate
     * @return true, when string is integer. False, when string is not integer.
     */
    public static boolean validateInteger(String string)
    {
        if (isNullStr(string))
            return false;
        try
        {
            Integer.parseInt(string);
            return true;
        }
        catch (final NumberFormatException e)
        {
            return false;
        }
    }

    /**
     * Print the wording and get string from user.
     * 
     * @param print is wording for print out.
     * @return String from user input.
     */
    public static String getString(String print)
    {
        System.out.print(print);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (isNullStr(input))
            return "";
        else
            return input.trim();
    }

    /**
     * Print the wording and get integer from user.
     * 
     * @param print is wording for print out.
     * @return Integer from user input.
     */
    public static int getInteger(String print)
    {
        System.out.print(print);
        boolean bOk = false;
        int input = 0;
        while (!bOk)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                bOk = true;
            }
            catch (Exception e)
            {
                System.out.println("Format mismatch");
            }

        }
        return input;
    }

    /**
     * Print the wording and get integer from user.
     * 
     * @param print is wording for print out.
     * @param min   is minimum value of number from user input.
     * @return Integer from user input.
     */
    public static int getInteger(String print, int min)
    {
        System.out.print(print);
        boolean bOk = false;
        int input = 0;
        while (!bOk)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                if (input > min)
                    bOk = true;
                else
                    System.out.println("Number out of range");
            }
            catch (Exception e)
            {
                System.out.println("Format mismatch");
            }

        }
        return input;
    }

    /**
     * Print the wording and get integer from user with the range of number.
     * 
     * @param print is wording for print out.
     * @param min   is minimum value of number from user input.
     * @param max   is maximum value of number from user input.
     * @return Integer from user input.
     */
    public static int getInteger(String print, int min, int max)
    {
        System.out.print(print);
        boolean bOk = false;
        int input = 0;
        while (!bOk)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                if ((input > min) && (input < max))
                    bOk = true;
                else
                    System.out.println("Number out of range");

            }
            catch (Exception e)
            {
                System.out.println("Format mismatch");
            }

        }
        return input;
    }

    /**
     * Print the wording and get boolean from user.
     * 
     * @param print is wording for print out.
     * @return boolean from user input.
     */
    public static boolean getConfirm(String print)
    {
        System.out.print(print);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no")))
        {
            System.out.println("Format mismatch");
            scanner = new Scanner(System.in);
            input = scanner.nextLine();
        }
        if (input.equalsIgnoreCase("yes"))
            return true;
        else if (input.equalsIgnoreCase("no"))
            return false;
        return false;
    }

    /**
     * Print the wording and get date from user.
     * 
     * @param print   is wording for print out.
     * @param dateCpr is date that going to be compare.
     * @param after   is boolean to select before or after.
     * @return Date from user input.
     */
    public static Date getDate(String print, Date dateCpr, boolean after)
    {
        boolean bOk = false;
        Date dateInput = null;
        while (!bOk)
        {
            System.out.print(print);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            while (validateDateStr(input) == false)
            {
                {
                    System.out.println("Format mismatch");
                    scanner = new Scanner(System.in);
                    input = scanner.nextLine();
                }
            }
            dateInput = strToDate(input);
            if (after)
            {
                if (dateInput.after(dateCpr))
                {
                    bOk = true;
                }
                else
                {
                    continue;
                }
            }
            else
            {
                if (dateInput.before(dateCpr))
                {
                    bOk = true;
                }
                else
                {
                    continue;
                }
            }
        }
        return dateInput;
    }

    /**
     * Print the wording and get date with time from user.
     * 
     * @param print   is wording for print out.
     * @param dateCpr is date with time that going to be compare.
     * @param after   is boolean to select before or after.
     * @return Date with time from user input.
     */
    public static Date getDateTime(String print, Date dateCpr, boolean after)
    {
        boolean bOk = false;
        Date dateInput = null;
        while (!bOk)
        {
            System.out.print(print);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            while (validateDateTimeStr(input) == false)
            {
                {
                    System.out.println("Format mismatch");
                    scanner = new Scanner(System.in);
                    input = scanner.nextLine();
                }
            }
            dateInput = strToDateTime(input);
            if (after)
            {
                if (dateInput.after(dateCpr))
                {
                    bOk = true;
                }
                else
                {
                    continue;
                }
            }
            else
            {
                if (dateInput.before(dateCpr))
                {
                    bOk = true;
                }
                else
                {
                    continue;
                }
            }
        }
        return dateInput;
    }

    /**
     * Print the wording and get command from user.
     * 
     * @param print is wording for print out.
     * @return command number.
     */
    public static int getCommand(String print)
    {
        boolean bOk = false;
        int commandValue = 0;
        while (!bOk)
        {
            String input = getString(print);
            if (input.equals("/home"))
            {
                commandValue = 1;
                bOk = true;
            }
            else if (input.equals("/help"))
            {
                commandValue = 2;
                bOk = true;
            }
            else if (input.equals("/next"))
            {
                commandValue = 3;
                bOk = true;
            }
            else if (input.equals("/prev"))
            {
                commandValue = 4;
                bOk = true;
            }
            else if (input.equals("/first"))
            {
                commandValue = 5;
                bOk = true;
            }
            else if (input.equals("/search"))
            {
                commandValue = 6;
                bOk = true;
            }
            else if (input.equals("/auction"))
            {
                commandValue = 7;
                bOk = true;
            }
            else if (input.equals("/register"))
            {
                commandValue = 8;
                bOk = true;
            }
            else if (input.equals("/login"))
            {
                commandValue = 9;
                bOk = true;
            }
            else if (input.equals("/logout"))
            {
                commandValue = 10;
                bOk = true;
            }
            else if (input.equals("/profile"))
            {
                commandValue = 11;
                bOk = true;
            }
            else if (input.equals("/makeauction"))
            {
                commandValue = 12;
                bOk = true;
            }
            else if (input.equals("/aboutus"))
            {
                commandValue = 13;
                bOk = true;
            }
            else if (input.equals("/exit"))
            {
                commandValue = 14;
                bOk = true;
            }
            else
            {
                bOk = false;
            }
        }
        return commandValue;
    }
}