import java.util.Date;

/**
 * Represent the user object in auction program. Contain the user detail.
 * 
 * Created by Kla & Tong 18 May 2020
 */

public class AuctionProgram
{
	/** Keep auctionProgram instance, used for singleton **/
	private static AuctionProgram auctionMain = new AuctionProgram();

	private AuctionManager auctionManager = AuctionManager.getSingletonInstance();

	private UserManager userManager = UserManager.getSingletonInstance();

//	private GraphicUI gui = GraphicUI

	private User user;

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

	}

	/**
	 * Static method to create instance of AuctionProgram class
	 * 
	 * @return auctionMain
	 */
	public static AuctionProgram getSingletonInstance()
	{
		return auctionMain;
	}

	/**
	 * Get login user
	 * 
	 * @return user
	 */
	public User getLogin()
	{
		return user;
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
			String surname, String birth, String address, String email)
	{
		if (userManager.createUser(username, password, name, surname, birth, address,
				email))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Login to the system
	 * 
	 * @param username of user
	 * @param password of user
	 * @return user when can login. Otherwise false.
	 */
	public User login(String username, String password)
	{
		User loginUser = userManager.checkLogin(username, password);
		if (loginUser != null)
		{
			return user;
		}
		else
		{
			return null;
		}

	}

	/**
	 * Logout of the system
	 * 
	 * @return true when user logged in, false when no user logged in.
	 */
	public boolean logout()
	{
		if (user != null)
		{
			user = null;
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
		if (user.addMoney(money))
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
		if (user.deductMoney(money))
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
		if (auction.makeBid(user, money))
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
		if (auctionManager.createAuction(user, item, category, picture, minBid,
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
		System.out.println("Name: " + user.getName());
		System.out.println("Surname: " + user.getUsername());
		System.out.println("Birth: " + user.getBirth());
		System.out.println("Address: " + user.getAddress());
		System.out.println("Email: " + user.getEmail());
		System.out.println("Balance: " + user.getBalance());
	}

	/**
	 * Edit profile of user
	 * 
	 * @param password of user that going to be
	 * @param name of user that going to be
	 * @param surname of user that going to be
	 * @param birth of user that going to be
	 * @param address of user that going to be
	 * @param email of user that going to be
	 * @return true edit profile successful. Otherwise false.
	 */
	public boolean editProfile(String password, String name, String surname,
			Date birth, String address, String email)
	{
		if (user.editProfile(password, name, surname, birth, address, email))
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