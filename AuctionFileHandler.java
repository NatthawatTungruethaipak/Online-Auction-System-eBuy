import java.util.ArrayList;
import java.util.Date;

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

	public User parseUser(String parse[])
	{
		String username = null;
		String password = null;
		String name = null;
		String surname = null;
		Date birth = null;
		String address = null;
		String email = null;
		int balance = 0;
		for(int i = 0; i < 8; i++)
		{
			 String fields[] = parse[i].split(" ", 2);
			 if(parse[0].equals("USERNAME") && IOUtils.validateUsername(parse[1]))
				username = parse[1];
			 else if(parse[0].equals("PASSWORD") && IOUtils.validatePassword(parse[1]))
				password = parse[1];
			 else if(parse[0].equals("NAME") && IOUtils.isNullStr(parse[1]) != true)
				name = parse[1];
			 else if(parse[0].equals("SURNAME") && IOUtils.isNullStr(parse[1]) != true)
				 surname = parse[1];
			 else if(parse[0].equals("BIRTH") && IOUtils.validateDate(parse[1]))
				 birth = IOUtils.createTimeInstance(parse[1]);
			 else if(parse[0].equals("ADDRESS"))
				 address = parse[1];
			 else if(parse[0].equals("EMAIL") && IOUtils.validateEmail(parse[1]))
				 email = parse[1];
			 else if(parse[0].equals("BALANCE") && IOUtils.validateInteger(parse[1]))
				 balance = Integer.parseInt(parse[1]);
			 else
				 return null;
		}
		User user = new User(username, password, name, surname, birth, address, email);
		user.addMoney(balance);
		return user;
	}
	
	/**
	 * Read user file and add to system.
	 */
	public ArrayList<User> readUsers()
	{
		String parameter[];
		int count = 0;
		ArrayList<User> userList = new ArrayList<User>();
		
		/** Open file. If cannot open, return null; **/
		TextFileReader reader = new TextFileReader(userFileName);
		if(reader.open() == false)
			return null;
		
		/** Read number of data **/
		String line;
		line = reader.readLine();
		count = Integer.parseInt(line);
		
		/** Loop get user and add to list**/
		for(int i = 0; i < count; i++)
		{
			parameter = new String[8];
			for(int j = 0; i < 8;i++)
		    	parameter[i] = reader.readLine();
			User user = parseUser(parameter);
			if(user != null) /* If data is correct, add to user list */  
				userList.add(user);
		}
		return userList;
		
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
	
	private User
}