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
	 * Date format for date
	 */
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

	/**
	 * DateTime format for date and time
	 */
	private static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");

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
	public static int[] diffDateTime(Date date1, Date date2)
	{
		int diffDateTime[] = new int[4];
		try
		{
			// in milliseconds
			long diff = date2.getTime() - date1.getTime();

			long diffSeconds = (diff / 1000) % 60;
			long diffMinutes = (diff / (60 * 1000)) % 60;
			long diffHours = (diff / (60 * 60 * 1000)) % 24;
			long diffDays = (diff / (24 * 60 * 60 * 1000));

			diffDateTime[0] = (int) diffDays;
			diffDateTime[1] = (int) diffHours;
			diffDateTime[2] = (int) diffMinutes;
			diffDateTime[3] = (int) diffSeconds;

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return diffDateTime;
	}

	/**
	 * Create date instance
	 * 
	 * @param date that going to create
	 * @return create data in term of Date class.
	 */
	public static Date createDateInstance(String date)
	{
		if (isNullStr(date))
		{
			return null;
		}
		try
		{
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
	 * @param date and time that going to create
	 * @return create data and time in term of Date class.
	 */
	public static Date createDateTimeInstance(String dateTime)
	{
		if (isNullStr(dateTime))
		{
			return null;
		}
		try
		{
			simpleDateTimeFormat.setLenient(false);
			Date javaDate = simpleDateTimeFormat.parse(dateTime);
			return javaDate;

		} catch (ParseException e)
		{
			System.out.println(dateTime + " is invalid date and time format");
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
		if (isNullStr(username))
		{
			return false;
		}
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
		if (isNullStr(password))
		{
			return false;
		}
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
		if (isNullStr(email))
		{
			return false;
		}
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
	 * Validate date to check pattern of date in format dd-MM-yyyy
	 * 
	 * @param date that going to be validate
	 * @return True, when date is correct pattern. False, when date is incorrect
	 *         pattern.
	 */
	public static boolean validateDate(String date)
	{
		if (isNullStr(date))
		{
			return false;
		}
		try
		{
			simpleDateFormat.setLenient(false);
			if (simpleDateFormat.parse(date) != null)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (ParseException e)
		{
			System.out.println(date + " is invalid date format");
			return false;
		}
	}

	/**
	 * Validate date and time to check pattern of date and time in format
	 * dd-MM-yyyy-HH:mm:ss
	 * 
	 * @param dateTime that going to be validate
	 * @return True, when date and time is correct pattern. False, when date and
	 *         time is incorrect pattern.
	 */
	public static boolean validateDateTime(String dateTime)
	{
		if (isNullStr(dateTime))
		{
			return false;
		}
		try
		{
			simpleDateFormat.setLenient(false);
			if (simpleDateFormat.parse(dateTime) != null)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (ParseException e)
		{
			System.out.println(dateTime + " is invalid date format");
			return false;
		}
	}
	
	/**
	 * validate integer the string parameter must contain only integer in format of
	 * string
	 * 
	 * @param string is string to be validate
	 * @return true, when string is integer. False, when string is not integer.
	 */
	public static boolean validateInteger(String string)
	{
		if (isNullStr(string))
		{
			return false;
		}
		try
		{
			Integer.parseInt(string);
			return true;
		} catch (final NumberFormatException e)
		{
			return false;
		}
	}
}