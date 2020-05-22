import java.util.ArrayList;
import java.util.Date;

/**
 * Manage, control, and keep all user.
 * 
 * Created by Kla & Tong 23 April 2020
 */
public class UserManager
{
    /** UserManager that will be Singleton to control user */
    private static UserManager userManager = new UserManager();

    /** List of user */
    private ArrayList<User> userList = null;

    /**
     * Constructor of UserManager class.
     * Make it private to prevent to implement singleton.
     */
    private UserManager()
    {
    }

    /**
     * Get instance of UserManager. Implement singleton.
     * 
     * @return Instance of UserManager
     */
    public static UserManager getSingletonInstance()
    {
        return userManager;
    }

    /**
     * Initialize user list for UserManager.
     * Can set the userList (Used for read file).
     * 
     * @param userList User list that want to set in UserManager
     */
    public void initialUser(ArrayList<User> userList)
    {
        if(userList != null)
            this.userList = userList;
        else
            this.userList = new ArrayList<User>();
    }

    /**
     * Find the user using username.
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
     * Find the user using name.
     * If there are duplicate name, it will return the first user that found.
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
     * Find user from username and check that password is match or not.
     * 
     * @param username Username that want to find
     * @param password Password that want to check
     * @return User if username and password are correct. Otherwise, null.
     */
    public User getLogin(String username, String password)
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
        User newUser = null;
        
        /* Validate user data and don't have username in system. */
        if(IOUtils.validateUser(username, password, name, birth, address, email) &&
           findUserByUsername(username) == null)
        {
            newUser = new User(username, password, name, birth, address, email);
            if (userList.add(newUser))
                return true;
        }
        return false;
    }
    
    /**
     * Edit profile data of user.
     * 
     * @param user     User that want to edit
     * @param password Password of user
     * @param name     Name of user
     * @param birth    Birth date of user
     * @param address  Address of user
     * @param email    Email of user
     * @return True, if can create a new user. Otherwise, false.
     */
    public boolean editProfile(User user, String password,String name, Date birth, String address, String email)
    {
        /* Validate user data and don't have username in system. */
        if(IOUtils.validateUser(user.getUsername(), password, name, birth, address, email))
        {
            user.editProfile(password, name, birth, address, email);
            return true;
        }
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
}