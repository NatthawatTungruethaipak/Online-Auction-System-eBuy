import java.util.ArrayList;

/**
 * Manage the Auction file
 * 
 * Created by Kla & Tong
 * 19 May 2020
 */
public class AuctionFileHandler
{
	/** User file name **/
	final String userFileName = "userData.txt";
	
	/** Auction file name **/
	final String auctionFileName = "auctionData.txt";
	
	/** Instance of AuctionFileHandler. Implement singleton **/
	private AuctionFileHandler auctionFileHandler = new AuctionFileHandler();
	
	/**
	 * Constructor of AuctionFileHandler.
	 * Prevent another class can create the instance. Implement for singleton
	 */
	private AuctionFileHandler()
	{
	}
	
	/**
	 * Get instance of AuctionFileHandler. Implement singleton
	 * @return
	 */
	public static AuctionFileHandler getSingletonInstance()
	{
		
	}
	
	/**
	 * Read user file and add to system.
	 */
	private void readUsers()
	{
		
	}
	
	/**
	 * Read auction file and add to system.
	 */
	private void readAuctions()
	{
		
	}
	
	/**
	 * Write users data to user text file.
	 * @param users Users that going to write
	 * @return True if don't have any problem.
	 */
	public boolean writeUsers(ArrayList<User> users)
	{
		
	}
	
	/**
	 * Write auction data to auction text file
	 * @param auctions Auctions that going to write
	 * @return True if don't have any problem.
	 */
	public boolean writeAuctions(ArrayList<Auction> auctions)
	{
		
	}
}