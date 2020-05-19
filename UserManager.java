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
    private static UserManager userManager = new UserManager();;

    /**
     * List of user
     */
    private ArrayList<User> userList = new ArrayList<User>();

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

    }

    /**
     * Validate data of new user
     * 
     * @param username of new user
     * @param password of new user
     * @param name     of new user
     * @param surname  of new user
     * @param birth    of new user
     * @param address  of new user
     * @param email    of new user
     * @return new user with validated
     */
    private User validateUser(String username, String password, String name,
            String surname, Date birth, String address, String email)
    {
        if (IOUtils.validateUsername(username) == false)
            return null;
        if (IOUtils.validatePassword(password) == false)
            return null;
        if (birth.after(IOUtils.getCurrentDateTime()))
            return null;
        if (IOUtils.validateEmail(email) == false)
            return null;
        if (findUserByUsername(username) == null)
        {
            User user = new User(username, password, name, surname, birth, address,
                    email);
            return user;
        }
        else
        {
            return null;
        }
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

    /**
     * Create new user and add to user list
     * 
     * @param username Username of user
     * @param password Password of user
     * @param name     Name of user
     * @param surname  Surname of user
     * @param birth    Birth date of user
     * @param address  Address of user
     * @param email    Email of user
     * @return
     */
    public boolean createUser(String username, String password, String name,
            String surname, Date birth, String address, String email)
    {
        User newUser = validateUser(username, password, name, surname, birth,
                address, email);
        if(newUser != null && userList.add(newUser))
            return false;
        else
            return false;
    }

    /**
     * Get all user list. (Used in write file)
     * 
     * @return All user list.
     */
    public ArrayList<User> getAllUser()
    {
        return userList;
    }
}