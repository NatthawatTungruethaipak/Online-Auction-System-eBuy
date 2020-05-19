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
    /** Instance of AuctionFileHandler. Implement singleton **/
    private static AuctionFileHandler auctionFileHandler = new AuctionFileHandler();
    
    /** User file name **/
    final String userFileName = "userData.txt";
    
    /** Auction file name **/
    final String auctionFileName = "auctionData.txt";
    
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

    private User parseUser(String parse[])
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
    
    private Auction parseAuction(String parse[])
    {
        String item = null;
        String category = null;
        String picture = null;
        User seller = null;
        Date dateStart = null;
        Date dateEnd = null;
        int stage = 0;
        int minBid = 0;
        
        UserManager userManager = UserManager.getSingletonInstance();
        for(int i = 0; i < 8; i++)
        {
             String fields[] = parse[i].split(" ", 2);
             if(parse[0].equals("ITEM") && IOUtils.isNullStr(parse[1]) != true)
                item = parse[1];
             else if(parse[0].equals("CATEGORY") && IOUtils.isNullStr(parse[1]) != true)
                category = parse[1];
             else if(parse[0].equals("PICTURE") && IOUtils.isNullStr(parse[1]) != true)
                picture = parse[1];
             else if(parse[0].equals("SELLER"))
             {
                 seller = userManager.findUserByUsername(parse[1]);
                 if(seller == null)
                     return null;
             }
             else if(parse[0].equals("DATESTART") && IOUtils.validateDateTime(parse[1]))
                  dateStart = IOUtils.createDateTimeInstance(parse[1]);
             else if(parse[0].equals("DATEEND") && IOUtils.validateDateTime(parse[1]))
            	 dateEnd = IOUtils.createDateTimeInstance(parse[1]);
             else if(parse[0].equals("STAGE") && IOUtils.validateInteger(parse[1]))
                 stage = Integer.parseInt(parse[1]);
             else if(parse[0].equals("MINBID") && IOUtils.validateInteger(parse[1]))
            	 minBid = Integer.parseInt(parse[1]);
             else
                 return null;
        }
        Auction auction = new Auction(seller, item, category, dateStart, dateEnd);
        if(auction.setMinBid(minBid) == false)
        	return null;
        if(auction.setPicture(picture) == false)
        	return null;
        if(stage == 1)
        	auction.openAuction();
        else if(stage == 2)
        {
        	auction.openAuction();
        	auction.closeAuction();
        }
        return auction;
    }
    
    private Bid parseBid(String parse[])
    {
        String username = null;
        String password = null;
        String name = null;
        String surname = null;
        Date birth = null;
        String address = null;
        String email = null;
        int balance = 0;
        for(int i = 0; i < 9; i++)
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
             else if(parse[0].equals("BIRTH") && IOUtils.    validateDate(parse[1]))
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
        boolean bNotNull = true;
        int count = 0;
        ArrayList<User> userList = new ArrayList<User>();
        
        /** Open file. If cannot open, return null; **/
        TextFileReader reader = new TextFileReader(userFileName);
        if(reader.open() == false)
            return null;
        
        /** Read number of data **/
        String line;
        line = reader.readLine();
        try
        {
        	count = Integer.parseInt(line);
        }
        catch(Exception e)
        {
        	return null;
        }
        
        /** Loop get user and add to list**/
        for(int i = 0; i < count && bNotNull; i++)
        {
            parameter = new String[8];
            for(int j = 0; i < 8 && bNotNull; i++)
            {
                parameter[i] = reader.readLine();
                if(parameter[i] == null)
                    bNotNull = false;
            }
            User user = parseUser(parameter);
            if(user != null) /* If data is correct, add to user list */  
                userList.add(user);
        }
        reader.close();
        return userList;
    }

    
    /**
     * Read auction file and add to system.
     */
    public ArrayList<Auction> readAuctions()
    {
        String parameter[];
        boolean bNotNull = true;
        int countAuction = 0;
        int countBid = 0;
        ArrayList<Auction> auctionList = new ArrayList<Auction>();
        
        /** Open file. If cannot open, return null; **/
        TextFileReader reader = new TextFileReader(auctionFileName);
        if(reader.open() == false)
            return null;
        
        /** Read number of data **/
        String line;
        line = reader.readLine();
        try
        {
            countAuction = Integer.parseInt(line);
        }
        catch(Exception e)
        {
        	return null;
        }
        
        /** Loop get auction, loop get bid, and add to list**/
        for(int i = 0; i < countAuction && bNotNull; i++)
        {
        	/* Get Auction */
            parameter = new String[8];
            for(int j = 0; i < 8 && bNotNull; i++)
            {
                parameter[i] = reader.readLine();
                if(parameter[i] == null)
                    bNotNull = false;
            }
            Auction auction = parseAuction(parameter);
            if(auction != null) /* If data is correct, add to user list */  
                auctionList.add(auction);
            
            /* Get Bid */
        }
        reader.close();
        return auctionList;
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