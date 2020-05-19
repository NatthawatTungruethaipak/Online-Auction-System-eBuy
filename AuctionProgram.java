/**
 * Represent the user object in auction program. Contain the user detail.
 * 
 * Created by Kla & Tong 18 May 2020
 */

public class AuctionProgram
{
	/** Keep auctionProgram instance, used for singleton **/
	private static AuctionProgram auctionMain = null;

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

	public static void main(String[] args)
	{

	}

	public static AuctionProgram getSingletonInstance()
	{
		if (auctionMain == null)
			auctionMain = new AuctionProgram();
		return auctionMain;
	}

	public User getLogin()
	{
		return user;

	}

	public boolean register(String[] userInfo)
	{
		return false;
	}

	public User login(String username, String password)
	{
		return user;
	}
	
	public boolean logout() {
		
	}
}