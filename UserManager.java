import java.util.*;

/**
 * Represent the user manager object in auction program. Contain the user
 * manager detail that control user.
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
	private ArrayList<User> userList = new ArrayList<User>();

	/**
	 * User that going to find
	 */
	private User findUser = null;

	/**
	 * Constructor of UserManager class for future add-on Make it private to prevent
	 * to implement singleton.
	 */
	private UserManager()
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
	 * Validate data of new user
	 * 
	 * @param username of new user
	 * @param password of new user
	 * @param name of new user
	 * @param surname of new user
	 * @param birth of new user
	 * @param address of new user
	 * @param email of new user
	 * @return new user with validated
	 */
	private User validateUser(String username, String password, String name, String surname, String birth,
			String address, String email)
	{
		if (IOUtils.validateUsername(username) && IOUtils.validatePassword(password) && IOUtils.validateDate(birth)
				&& IOUtils.validateEmail(email))
		{
			if (findUserByUsername(username) == null)
			{
				Date validatedUserBirth = IOUtils.createDateInstance(birth);
				User validatedUser = new User(username, password, name, surname, validatedUserBirth, address, email);
				return validatedUser;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * Find the user using username
	 */
	public User findUserByUsername(String username)
	{
		findUser = null;
		for (User user : userList)
		{
			if (user.getUsername().equals(username))
			{
				findUser = user;
				break;
			}
		}
		return findUser;
	}

	/**
	 * Find the user using name
	 */
	public User findUserByName(String name)
	{
		findUser = null;
		for (User user : userList)
		{
			if (user.getName().equals(name))
			{
				findUser = user;
				break;
			}
		}
		return findUser;
	}

	/**
	 * Add user to the list of userList
	 */
	public User checkLogin(String username, String password)
	{
		findUser = null;
		for (User user : userList)
		{
			if (user.getUsername().equals(username))
			{
				if (user.checkPassword(password))
				{
					findUser = user;
					break;
				}
				else
				{
					break;
				}
			}
		}
		return findUser;
	}

	public boolean createUser(String username, String password, String name, String surname, String birth,
			String address, String email)
	{
		User newUser = validateUser(username, password, name, surname, birth, address, email);
		if(userList.add(newUser))
		{
			return true;
		}
		else {
			return false;
		}
	}
}