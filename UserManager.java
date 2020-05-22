import java.util.ArrayList;
import java.util.Date;

/**
 * Manage, control, and keep all user.
 * 
 * Created by Kla & Tong 23 April 2020
 */
public class UserManager
{
    /**
     * UserManager that will be Singleton to control user
     */
    private static UserManager userManager = new UserManager();;

    /**
     * List of user
     */
    private ArrayList<User> userList = null;

    /**
     * Constructor of UserManager class for future add-on Make it private to prevent
     * to implement singleton.
     */
    private UserManager()
    {
    }

    /**
     * Get instance of UserManager. Implement Singleton
     * 
     * @return Instance of UserManager
     */
    public static UserManager getSingletonInstance()
    {
        return userManager;
    }

    /**
     * Create the ArrayList for userList
     * 
     * @param userList Initialize user
     */
    public void initialUser(ArrayList<User> userList)
    {
        if(userList != null)
            this.userList = userList;
        else
            this.userList = new ArrayList<User>();
    }

    /**
     * Find the user using username
     * 
     * @param username Username that want to find
     * @return User if found. Null if not.
     */
    public User findUserByUsername(String username)
    {
        User findUser = null;
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
     * 
     * @param name Name that want to find
     * @return User that has name that finding
     */
    public User findUserByName(String name)
    {
        User findUser = null;
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
     * Find username and then check the password.
     * 
     * @param username Username that want to find
     * @param password Password that want to check
     * @return User if username and password are same. Otherwise, null.
     */
    public User checkLogin(String username, String password)
    {
        User findUser = null;
        for (User user : userList)
        {
            if (user.getUsername().equals(username))
            {
                if (user.checkPassword(password))
                    findUser = user;
                break;
            }
        }
        return findUser;
    }

    /**
     * Create new user and add to user list
     * 
     * @param username Username of user
     * @param password Password of user
     * @param name     Name of user
     * @param birth    Birth date of user
     * @param address  Address of user
     * @param email    Email of user
     * @return True, if can create a new user. Otherwise, false.
     */
    public boolean createUser(String username, String password, String name,
            Date birth, String address, String email)
    {
        User newUser = validateUser(username, password, name, birth, address, email);
        if (newUser != null && userList.add(newUser))
            return true;
        else
            return false;
    }

    /**
     * Get all user list. (Used in write file)
     * 
     * @return User list
     */
    public ArrayList<User> getAllUser()
    {
        return userList;
    }

    /**
     * Validate user data and return in User instance back.
     * 
     * @param username Username to validate
     * @param password Password user to validate
     * @param name     Name of user to validate
     * @param birth    birth date of user
     * @param address  Address of user
     * @param email    Email of user
     * @return New user if the data is valid.
     */
    private User validateUser(String username, String password, String name,
            Date birth, String address, String email)
    {
        if (IOUtils.validateUsername(username) == false)
            return null;
        if (IOUtils.validatePassword(password) == false)
            return null;
        if (DateUtils.isAfterCurrentDateTime(birth))
            return null;
        if (IOUtils.validateEmail(email) == false)
            return null;
    
        /* Create new user */
        if (findUserByUsername(username) == null)
            return new User(username, password, name, birth, address, email);
        else
            return null;
    }
}