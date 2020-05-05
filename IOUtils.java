import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class IOUtils
{

	public static Date getCurrentDateTime()
	{
		return null;
	}

	public static int compareDateTime(Date date1, Date date2)
	{
		return 0;
	}

	public static Date diffDateTime(Date date1, Date date2)
	{
		return date2;
	}

	public static boolean createTimeInstance(String date)
	{
		return false;
	}

	public static boolean createTimeInstance(String date, String time)
	{
		return false;
	}

	public static boolean validateUsername(String username)
	{
		return false;
	}

	public static boolean validatePassword(String password)
	{
		return false;
	}

	public static boolean validateEmail(String email)
	{
		return false;
	}

	public static boolean validateInteger(String text)
	{
		return false;
	}

	public static void main(String args[])
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String nowString = dtf.format(now);
		System.out.println(now);
		System.out.println(now.getClass());
		System.out.println(nowString);
		System.out.println(nowString.getClass());
		System.out.println(nowString.charAt(0));
		System.out.println(nowString.split("/"));
	}
}