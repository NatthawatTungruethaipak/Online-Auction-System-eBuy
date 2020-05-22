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

    /** Auction file handler instance instance **/
    private static AuctionFileHandler fileHandler = AuctionFileHandler
            .getSingletonInstance();

    /** User login **/
    private static User userLogin = null;

    /**
     * Constructor of auction program. Make it private to prevent to implement
     * singleton.
     */
    private AuctionProgram()
    {
    }

    /**
     * Main function.
     * Get the command from user and display interface follow the command.
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
                case 1: /** main page command **/                    
                    UserInterface.displayHomePage();
                    break;
                case 2: /** help command **/
                    UserInterface.displayHelp();
                    break;
                case 3: /** next page command **/
                    UserInterface.displayNextPage();
                    break;
                case 4: /** previous page command **/
                    UserInterface.displayPrevPage();
                    break;
                case 5: /** first page command **/
                    UserInterface.displayFirstPage();
                    break;
                case 6: /** search auction command **/
                    UserInterface.displaySearchAuction();
                    break;
                case 7: /** display auction information **/
                    UserInterface.displaySelectAuction();
                    break;
                case 8: /** display register **/
                    UserInterface.displayRegister();
                    break;
                case 9: /** display login **/
                    UserInterface.displayLogin();
                    break;
                case 10: /** display logout **/
                    UserInterface.displayLogout();
                    break;
                case 11: /** display user profile **/
                    UserInterface.displayManageProfile();
                    break;
                case 12: /** make auction **/
                    UserInterface.displayMakeAuction();
                    break;
                case 13: /** about us **/
                    UserInterface.displayAboutUs();
                    break;
                case 14: /** Exit **/
                    bLoop = UserInterface.displayEnding();
                    break;
                default: /** Error command **/
                    UserInterface.displayGetHelp();
                    break;
            }
        }
        endProgram();
        System.exit(0);
    }

    /**
     * Get the user that currently login
     * 
     * @return user
     */
    public static User getLogin()
    {
        return userLogin;
    }

    /**
     * Check that user is login or not
     * 
     * @return True, if user is login. False, if doesn't have any user login.
     */
    public static boolean isLogin()
    {
        if (userLogin != null)
            return true;
        else
            return false;
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
        User user = userManager.getLogin(username, password);
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
     * @return true if can logout. Otherwise, user haven't login.
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
     * Register the account
     * 
     * @param username Username of user
     * @param password Password of user
     * @param name     Name of user
     * @param birth    Birth date of user
     * @param address  Address of user.
     * @param email    Email of user
     * @return True if be able to register to the system. Otherwise, false.
     */
    public static boolean register(String username, String password, String name,
            Date birth, String address, String email)
    {
        return userManager.createUser(username, password, name, birth, address,
                email);
    }

    /**
     * Edit profile of user that currently login
     * 
     * @param password Password of user 
     * @param name     Name of user
     * @param birth    Birth date of user
     * @param address  Address of user
     * @param email    Email of user
     * @return True if can edit profile. Otherwise, false.
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
     * Deposit money to the balance
     * 
     * @param money Amount of money to deposit
     * @return true when can deposit the money to the account.
     *         Otherwise, false.
     */
    public static boolean deposit(int money)
    {
        boolean bCheck = false;
        if (userLogin != null)
        {
            if (userLogin.addMoney(money))
                bCheck = true;
        }
        return bCheck;
    }

    /**
     * Withdraw money from the balance
     * 
     * @param money Amount of money to withdraw
     * @return true when be able to withdraw the money from the account.
     *          Otherwise, false.
     */
    public static boolean withdraw(int money)
    {
        boolean bCheck = false;
        if (userLogin != null)
        {
            if (userLogin.deductMoney(money))
                bCheck = true;
        }
        return bCheck;
    }

    /**
     * Create auction for sell item
     * 
     * @param item      Item name that want to sell
     * @param category  Category that of item
     * @param picture   Picture of item
     * @param minBid    Minimum money to bid
     * @param dateStart Date start of the auction
     * @param dateEnd   Date end or close date of auction
     * @return true if can create auction. Otherwise false.
     */
    public static boolean makeAuction(String item, String category, String picture,
            int minBid, Date dateStart, Date dateEnd)
    {
        if(userLogin != null)
            return auctionManager.createAuction(userLogin, item, category, picture,
                    minBid, dateStart, dateEnd);
        else
            return false;
    }

    /**
     * Make bid to the auction
     * 
     * @param auction Auction that user want to bid
     * @param money Money that user money want to bid
     * @return True if user can make bid. Otherwise, false.
     */
    public static boolean makeBid(Auction auction, int money)
    {
        if (userLogin != null)
            return auction.makeBid(userLogin, money);
        else
            return false;
    }

    /**
     * Search auction list from stage, category, item, seller, lower price.
     * 
     * @param type   Type that want to search
     * (1-open, 2-close, 3-item, 4- cat, 5-name, 6-lower price)
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
                retUserList = auctionManager.searchAuctionByCategory(keyStr);
                break;
            case 5:/* Search auction by name */
                retUserList = auctionManager.searchAuctionBySellerName(keyStr);
                break;
            case 6: /* Search auction by lower price */
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
     * Initialise the program.
     */
    public static void initialProgram()
    {
        /* Create img directory */
        IOUtils.initial();

        /* Initial category */
        Category.initial();

        /* Read user from file */
        ArrayList<User> userList = fileHandler.readUsers();
        userManager.initialUser(userList);

        /* Read auction from file */
        ArrayList<Auction> auctionList = fileHandler.readAuctions();
        auctionManager.initialAuction(auctionList);

        /* Run auction trigger */
        AuctionTrigger auctionTrigger = AuctionTrigger.getSingleInstance();
        auctionTrigger.start();
    }

    /**
     * End the program
     */
    public static void endProgram()
    {
        /* Interrupt thread to stop loop */
        AuctionTrigger auctionTrigger = AuctionTrigger.getSingleInstance();
        auctionTrigger.interrupt();

        /* Save user to file */
        ArrayList<User> userList = userManager.getAllUser();
        fileHandler.writeUsers(userList);

        /* Save auction to file */
        ArrayList<Auction> auctionList = auctionManager.getAllAuction();
        fileHandler.writeAuctions(auctionList);

    }
}