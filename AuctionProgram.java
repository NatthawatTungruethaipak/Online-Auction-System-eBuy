import java.util.ArrayList;
import java.util.Date;

/**
 * Represent the user object in auction program. Contain the user detail.
 * 
 * Created by Kla & Tong 18 May 2020
 */

public class AuctionProgram
{
    /** Keep auctionProgram instance, used for singleton **/
    private static AuctionProgram auctionProgram = new AuctionProgram();

    private static AuctionManager auctionManager = AuctionManager.getSingletonInstance();

    private static UserManager userManager = UserManager.getSingletonInstance();
    
    private static UserInterface userInterface = UserInterface.getSingletonInstance();

//	private GraphicUI gui = GraphicUI

    private User userLogin;

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
        int command[] = new int[]{0,0};
        auctionProgram.initial();
        userInterface.displayIntroduction();
        while(command[0] != 10)
        {
            command[0] = IOUtils.getCommand("command: ");
            /** help command **/
            if(command[0] == 1)
                userInterface.displayHelp();
            /** main page command **/
            else if(command[0] == 2)
            {
                userInterface.resetAuctionDisplay();
                userInterface.displayAuction();
            }
            /** next page command **/
            else if(command[0] == 3)
                userInterface.displayNextPage();
            /** prev page command **/
            else if(command[0] == 4)
                userInterface.displayPrevPage();
            /** first page command **/
            else if(command[0] == 5)
                userInterface.displayFirstPage();
            /** search auction command **/
            else if(command[0] == 6)
                userInterface.searchAuction();
            /** display auction information **/
            else if(command[0] == 7)
                userInterface.displayAuction(command[1]);
            /** display register **/
            else if(command[0] == 7)
                userInterface.displayRegister();
            /** display login **/
            else if(command[0] == 9)
                userInterface.displayLogin();
            /** display user profile **/
            else if(command[0] == 10)
                userInterface.displayProfile();
            /** edit user profile **/
            else if(command[0] == 11)
                userInterface.displayEditProfile();
            /** make auction **/
            else if(command[0] == 12)
                userInterface.displayMakeAuction();
            /** make bid **/
            else if(command[0] == 13)
                userInterface.displayMakeBid();
            
            
        }
        userInterface.displayEnding();
        AuctionProgram.getSingletonInstance().end();
    }

    /**
     * Static method to create instance of AuctionProgram class
     * 
     * @return auctionProgram
     */
    public static AuctionProgram getSingletonInstance()
    {
        return auctionProgram;
    }

    /**
     * Get login user
     * 
     * @return user
     */
    public User getLogin()
    {
        return userLogin;
    }

    /**
     * Register the account
     * 
     * @param username of registering account
     * @param password of registering account
     * @param name     of registering account
     * @param surname  of registering account
     * @param birth    of registering account
     * @param address  of registering account
     * @param email    of registering account
     * @return True, when can register the account. Otherwise, false.
     */
    public boolean register(String username, String password, String name,
            String surname, Date birth, String address, String email)
    {
        if (userManager.createUser(username, password, name, surname, birth, address,
                email))
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
    public boolean login(String username, String password)
    {
        User user = userManager.checkLogin(username, password);
        if (user != null)
        {
            this.userLogin = user;
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
    public boolean logout()
    {
        if (userLogin != null)
        {
            userLogin = null;
            return true;
        }
        else
        {
            return false;
        }

    }

    /**
     * Deposit money from the balance
     * 
     * @param money is amount of money to deposit
     * @return true when can deposit the money to the account. Otherwise false.
     */
    public boolean deposit(int money)
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
    public boolean withdraw(int money)
    {
        if (userLogin.deductMoney(money))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Make bid from user
     * 
     * @param auction that user want to make bid
     * @param money   amount of money in that bid
     * @return true when can make bid. Otherwise false.
     */
    public boolean makeBid(Auction auction, int money)
    {
        if (auction.makeBid(userLogin, money))
        {
            return true;
        }
        else
        {
            return false;
        }

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
    public boolean makeAuction(String item, String category, String picture,
            int minBid, Date dateStart, Date dateEnd)
    {
        if (auctionManager.createAuction(userLogin, item, category, picture, minBid,
                dateStart, dateEnd))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * View profile of user.
     */
    public void viewProfile()
    {
        System.out.println("Name: " + userLogin.getName());
        System.out.println("Surname: " + userLogin.getUsername());
        System.out.println("Birth: " + userLogin.getBirth());
        System.out.println("Address: " + userLogin.getAddress());
        System.out.println("Email: " + userLogin.getEmail());
        System.out.println("Balance: " + userLogin.getBalance());
    }

    /**
     * Edit profile of user
     * 
     * @param password of user that going to be
     * @param name     of user that going to be
     * @param surname  of user that going to be
     * @param birth    of user that going to be
     * @param address  of user that going to be
     * @param email    of user that going to be
     * @return true edit profile successful. Otherwise false.
     */
    public boolean editProfile(String password, String name, String surname,
            Date birth, String address, String email)
    {
        if (userLogin.editProfile(password, name, surname, birth, address, email))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 
     */
    public void readData()
    {

    }

    /**
     * 
     */
    public void saveData()
    {

    }

    /**
     * 
     */
    public void initial()
    {

    }

    /**
     * 
     */
    public void end()
    {

    }
}