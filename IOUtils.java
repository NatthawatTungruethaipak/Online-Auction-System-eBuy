import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represent the user object in auction program. Contain the user detail.
 * 
 * Created by Kla & Tong 18 May 2020
 */
public class IOUtils
{

	/**
	 * Check the string is null or not.
	 * 
	 * @param string that will be check.
	 * @return true, when the string is null. false, when the string is not null.
	 */
	public static boolean isNullStr(String string)
	{
		if (string.trim().equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Get current date and time.
	 * 
	 * @return current data and time in term of Date class.
	 */
	public static Date getCurrentDateTime()
	{
		Date dateNow = new Date();
		return dateNow;
	}

	/**
	 * Find the difference in date and time from couple of date and time.
	 * 
	 * @param date1 is first date to be compare.
	 * @param date2 is second date to be compare.
	 * @return difference of date and time in term of integer array. Index 0 is
	 *         days, index 1 is hours, index 2 is minutes, index 3 is seconds
	 */
	public static ArrayList<Integer> diffDateTime(Date date1, Date date2)
	{
		ArrayList<Integer> values = new ArrayList<>();
		try
		{
			// in milliseconds
			long diff = date2.getTime() - date1.getTime();

			long diffSeconds = (diff / 1000) % 60;
			long diffMinutes = (diff / (60 * 1000)) % 60;
			long diffHours = (diff / (60 * 60 * 1000)) % 24;
			long diffDays = (diff / (24 * 60 * 60 * 1000));

			values.add((int) diffDays);
			values.add((int) diffHours);
			values.add((int) diffMinutes);
			values.add((int) diffSeconds);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return values;
	}

	/**
	 * Create date instance
	 * 
	 * @param date that going to create
	 * @return create data in term of Date class.
	 */
	public static Date createTimeInstance(String date)
	{
		if (isNullStr(date))
		{
			return null;
		}
		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
			simpleDateFormat.setLenient(false);
			Date javaDate = simpleDateFormat.parse(date);
			return javaDate;
		} catch (ParseException e)
		{
			System.out.println(date + " is invalid date format");
			return null;
		}
	}

	/**
	 * Create date with time instance
	 * 
	 * @param date that going to create
	 * @param time that going to create
	 * @return create data and time in term of Date class.
	 */
	public static Date createTimeInstance(String date, String time)
	{
		if (isNullStr(date))
		{
			return null;
		}
		try
		{
			String dateTime = date + "-" + time;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy-HH:mm:ss");
			simpleDateFormat.setLenient(false);
			Date javaDate = simpleDateFormat.parse(dateTime);
			return javaDate;

		} catch (ParseException e)
		{
			System.out.println(date + " is invalid date and time format");
			return null;
		}
	}

	/**
	 * validate the username in pattern of 6-30 characters with consist of alphabet,
	 * number and underscore. First character can't be number.
	 * 
	 * reference from
	 * https://www.geeksforgeeks.org/how-to-validate-a-username-using-regular-expressions-in-java/
	 * 
	 * @param username is username to be validate
	 * @return True, when username is in correct pattern. False, when username is
	 *         not in correct pattern.
	 */
	public static boolean validateUsername(String username)
	{
		String usernamePattern = "^[aA-zZ]\\w{5,29}$";
		if (username.matches(usernamePattern))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * validate the password in pattern of 8-40 characters with consist of at least
	 * one number, one lower character, one upper character, one special that is [ @
	 * # $ % ! . ].
	 * 
	 * reference from
	 * https://examples.javacodegeeks.com/core-java/util/regex/matcher/validate-password-with-java-regular-expression-example/
	 * 
	 * @param password is password to be validate
	 * @return @return True, when password is in correct pattern. False, when
	 *         password is not in correct pattern.
	 */
	public static boolean validatePassword(String password)
	{
		String passwordPattern = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
		if (password.matches(passwordPattern))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * validate the email in pattern of email
	 * 
	 * reference from https://www.tutorialspoint.com/validate-email-address-in-java
	 * 
	 * @param email is email to be validate
	 * @return True, when email is in correct pattern. False, when email is not in
	 *         correct pattern.
	 */
	public static boolean validateEmail(String email)
	{
		String emailPattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (email.matches(emailPattern))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * validate integer the string parameter must contain only integer in format of string
	 * 
	 * @param string is string to be validate
	 * @return true, when string is integer. False, when string is not integer.
	 */
	public static boolean validateInteger(String string)
	{
		try
		{
			Integer.parseInt(string);
			return true;
		} catch (final NumberFormatException e)
		{
			return false;
		}
	}

	public static void main(String args[])
	{
		System.out.println("isNullStr " + isNullStr(""));
		System.out.println("isNullStr " + isNullStr("tong"));
		System.out.println("now is " + getCurrentDateTime());
		System.out.println(createTimeInstance("12-29-2016"));
		System.out.println(createTimeInstance("12-29-2016", "23:33:17"));
		Date d1 = createTimeInstance("12-29-2016", "23:33:27");
		Date d2 = createTimeInstance("12-30-2016", "22:33:17");
		diffDateTime(d1, d2);
		System.out.println("validateEmail " + validateEmail("natthawat.tungruethaipak@mail.kmutt.ac.th"));
		System.out.println("validateEmail " + validateEmail("natthawat.tungruethaipakmail.kmutt.ac.th"));
		System.out.println("validatePassword " + validatePassword("n!k@sn1Kos"));
		System.out.println("validatePassword " + validatePassword("nsn1Kos"));
		// Test Case: 1
		String str1 = "Geeksforgeeks";
		System.out.println("validateUsername1 " + validateUsername(str1));

		// Test Case: 2
		String str2 = "1Geeksforgeeks";
		System.out.println("validateUsername2 " + validateUsername(str2));

		// Test Case: 3
		String str3 = "Ge";
		System.out.println("validateUsername3 " + validateUsername(str3));

		// Test Case: 4
		String str4 = "e22vvb";
		System.out.println("validateUsername4 " + validateUsername(str4));
		
		System.out.println(validateInteger(""));
		System.out.println(validateInteger("tong"));
		System.out.println(validateInteger("tong12"));
		System.out.println(validateInteger("1234"));
	}
}