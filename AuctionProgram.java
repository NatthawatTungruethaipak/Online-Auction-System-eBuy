import java.util.ArrayList;
import java.util.Date;

/**
 * Facade of auction program. Contain main method in this class.
 * 
 * Created by Kla & Tong 18 May 2020
 */

public class AuctionProgram
{
    /** Auction manager instance **/
    private static AuctionManager auctionManager = AuctionManager
            .getSingletonInstance();

    /** User manager instance **/
    private static UserManager userManager = UserManager.getSingletonInstance();

    /** User manager instance **/
    private static AuctionFileHandler fileHandler = AuctionFileHandler
            .getSingletonInstance();

    /** User login **/
    private static User userLogin;

    /**
     * Constructor of auction program. Make it private to prevent to implement
     * singleton.
     */
    private AuctionProgram()
    {
    }

    /**
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        boolean bLoop = true;
        initialProgram();
        UserInterface.displayHomePage();
        while (bLoop)
        {
            System.out.println("\nUse command '/help' to see all command");
            int command = IOUtils.getCommand("command: ");
            switch (command) {
                case 1:
                    /** main page command **/
                    UserInterface.displayHomePage();
                    break;
                case 2:
                    /** help command **/
                    UserInterface.displayHelp();
                    break;
                case 3:
                    /** next page command **/
                    UserInterface.displayNextPage();
                    break;
                case 4:
                    /** previous page command **/
                    UserInterface.displayPrevPage();
                    break;
                case 5:
                    /** first page command **/
                    UserInterface.displayFirstPage();
                    break;
                case 6:
                    /** search auction command **/
                    UserInterface.searchAuction();
                    break;
                case 7:
                    /** display auction information **/
                    UserInterface.displaySelectAuction();
                    break;
                case 8:
                    /** display register **/
                    UserInterface.displayRegister();
                    break;
                case 9:
                    /** display login **/
                    UserInterface.displayLogin();
                    break;
                case 10:
                    /** display logout **/
                    UserInterface.displayLogout();
                    break;
                case 11:
                    /** display user profile **/
                    UserInterface.displayManageProfile();
                    break;
                case 12:
                    /** make auction **/
                    UserInterface.displayMakeAuction();
                    break;
                case 13:
                    /** about us **/
                    UserInterface.displayAboutUs();
                    break;
                case 14:
                    /** Exit **/
                    bLoop = UserInterface.displayEnding();
                    break;
                default:
                    /** Error command **/
                    UserInterface.displayGetHelp();
                    break;
            }
        }
        endProgram();
    }

    /**
     * Get login user
     * 
     * @return user
     */
    public static User getLogin()
    {
        return userLogin;
    }

    /**
     * Get login user
     * 
     * @return user
     */
    public static boolean isLogin()
    {
        if (userLogin != null)
            return true;
        else
            return false;
    }

    /**
     * Register the account
     * 
     * @param username of registering account
     * @param password of registering account
     * @param name     of registering account
     * @param birth    of registering account
     * @param address  of registering account
     * @param email    of registering account
     * @return True, when can register the account. Otherwise, false.
     */
    public static boolean register(String username, String password, String name,
            Date birth, String address, String email)
    {
        return userManager.createUser(username, password, name, birth, address,
                email);
    }

    /**
     * Login to the system
     * 
     * @param username of user
     * @param password of user
     * @return True if can login. Otherwise, false.
     */
    public static boolean login(String username, String password)
    {
        User user = userManager.checkLogin(username, password);
        if (user != null)
        {
            userLogin = user;
            return true;
        }
        else
            return false;

    }

    /**
     * Logout of the system
     * 
     * @return true when user logged in, false when no user logged in.
     */
    public static boolean logout()
    {
        if (userLogin != null)
        {
            userLogin = null;
            return true;
        }
        else
            return false;
    }

    /**
     * Deposit money from the balance
     * 
     * @param money is amount of money to deposit
     * @return true when can deposit the money to the account. Otherwise false.
     */
    public static boolean deposit(int money)
    {
        if (userLogin.addMoney(money))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Withdraw money from the balance
     * 
     * @param money is amount of money to deposit
     * @return true when can withdraw the money from the account. Otherwise false.
     */
    public static boolean withdraw(int money)
    {
        if (userLogin != null)
        {
            if (userLogin.deductMoney(money))
                return true;
            else
                return false;
        }
        else
            return false;
    }

    /**
     * Make bid from user
     * 
     * @param auction that user want to make bid
     * @param money   amount of money in that bid
     * @return true when can make bid. Otherwise false.
     */
    public static boolean makeBid(Auction auction, int money)
    {
        if (userLogin != null)
            return auction.makeBid(userLogin, money);
        else
            return false;

    }

    /**
     * Create auction for sell
     * 
     * @param item      of user that want to sell
     * @param category  of user that want to sell
     * @param picture   of user that want to sell
     * @param minBid    of user that want to sell
     * @param dateStart of user that want to sell
     * @param dateEnd   of user that want to sell
     * @return true when auction is created successful. Otherwise false.
     */
    public static boolean makeAuction(String item, String category, String picture,
            int minBid, Date dateStart, Date dateEnd)
    {
        return auctionManager.createAuction(userLogin, item, category, picture,
                minBid, dateStart, dateEnd);
    }

    /**
     * Search auction list from stage, category, item, seller, lower price.
     * 
     * @param type   Type that want to search
     * @param keyStr Key that used to search.
     * @param keyInt Integer value that want to search
     * @return Return user list from search
     */
    public static ArrayList<Auction> searchAuction(int type, String keyStr,
            int keyInt)
    {
        ArrayList<Auction> retUserList = null;
        switch (type) {
            case 1: /* Search opened auction */
                retUserList = auctionManager.searchAuctionByStage(1);
                break;
            case 2: /* Search closed auction */
                retUserList = auctionManager.searchAuctionByStage(2);
                break;
            case 3: /* Search auction by item name */
                retUserList = auctionManager.searchAuctionByItem(keyStr);
                break;
            case 4:/* Search auction by category */
                retUserList = auctionManager.searchAuctionByCat(keyStr);
                break;
            case 5: /* Search auction by lower price */
                retUserList = auctionManager.searchAuctionByLowerPrice(keyInt);
                break;
        }
        return retUserList;
    }

    /**
     * Search user from username or name.
     * 
     * @param key     String name or username to find.
     * @param bSelect Select to search by name(true) or username(false)
     * @return User that finding. Return null if can't find.
     */
    public static User searchUser(String key, boolean bSelect)
    {
        if (bSelect)
            return userManager.findUserByName(key);
        else
            return userManager.findUserByUsername(key);

    }

    /**
     * Edit profile of user
     * 
     * @param password of user that going to be
     * @param name     of user that going to be
     * @param birth    of user that going to be
     * @param address  of user that going to be
     * @param email    of user that going to be
     * @return true edit profile successful. Otherwise false.
     */
    public static boolean editProfile(String password, String name, Date birth,
            String address, String email)
    {
        if (userLogin != null)
            return userLogin.editProfile(password, name, birth, address, email);
        else
            return false;
    }

    /**
     * Initialise the program.
     */
    public static void initialProgram()
    {
        /* Create img directory */
        IOUtils.initial();

        /* Read user from file */
        ArrayList<User> userList = fileHandler.readUsers();
        userManager.initialUser(userList);

        /* Read auction from file */
        ArrayList<Auction> auctionList = fileHandler.readAuctions();
        auctionManager.initialAuction(auctionList);
    }

    /**
     * End the program
     */
    public static void endProgram()
    {
        /* Save user to file */
        ArrayList<User> userList = userManager.getAllUser();
        fileHandler.writeUsers(userList);
        
        /* Save auction to file */
        ArrayList<Auction> auctionList = auctionManager.getAllAuction();
        fileHandler.writeAuctions(auctionList);
        
    }
}