import java.util.*;

/**
 * Represent the user manager object in auction program. Contain the user manager detail that control user.
 * 
 * Created by Kla & Tong 23 April 2020
 */
public class UserManager
{
	/**
	 * UserManager that will be Singleton to control user
	 */
	private static UserManager userManager = null;

	/**
	 * List of user
	 */
	private ArrayList<User> userList;

	/**
	 * Index of userList
	 */
	private int count;
	
	/**
	 * User that going to find
	 */
	private User findUser;
	
	/**
	 * Constructor of UserManager class for future add-on
	 */
	public UserManager()
	{

	}

	/**
	 * Static method to create instance of UserManager class
	 */
	public static UserManager getSingletonInstance()
	{
		if (userManager == null)
			userManager = new UserManager();
		return userManager;
	}

	/**
	 * Create the ArrayList for userList
	 */
	public void initialUser(ArrayList<User> userList)
	{
		userList = new ArrayList<User>();
	}

	/**
	 * Find the user using username
	 */
	public User findUserByUsername(String username)
	{
		count = 0;
		findUser = null;
		while (userList.size() > count)
		{
			findUser = userList.get(count);
			if (findUser.getUsername().equals(username))
			{
				return findUser;
			}
			count++;
		}
		return findUser;
	}

	/**
	 * Find the user using name
	 */
	public User findUserByName(String name)
	{
		count = 0;
		findUser = null;
		while (userList.size() > count)
		{
			findUser = userList.get(count);
			if (findUser.getName().equals(name))
			{
				return findUser;
			}
			count++;
		}
		return findUser;
	}

	/**
	 * Add user to the list of userList
	 */
	public boolean addUser(User user)
	{
		userList.add(user);
		return true;
	}
}