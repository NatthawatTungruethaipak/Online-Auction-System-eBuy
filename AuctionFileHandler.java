import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	private static AuctionFileHandler auctionFileHandler = new AuctionFileHandler();
	
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
		return auctionFileHandler;
	}
	
	/**
	 * Read user file and add to system.
	 */
	public ArrayList<User> readUsers()
	{
		JSONFileReader userReader = new JSONFileReader(userFileName);
		JSONArray userJSON = userReader.readJSON();
		return ;		
	}
	
	private User parseUserObject(JSONObject userObj)
	{
		String username = (String) userObj.get("username");
		String password = (String) userObj.get("password");
		String name = (String) userObj.get("name");
		String surname = (String) userObj.get(surname);
		Date birth = (Date) userObj.get();
		String address;
		String email;
		int balance;
	}
	
	/**
	 * Read auction file and add to system.
	 */
	public ArrayList<Auction> readAuctions()
	{
		
	}
	
	/**
	 * Write users data to user text file.
	 * @param users Users that going to write
	 * @return True if don't have any problem.
	 */
	public boolean writeUsers(ArrayList<User> users)
	{
		if(users != null)
		{
			
		}
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