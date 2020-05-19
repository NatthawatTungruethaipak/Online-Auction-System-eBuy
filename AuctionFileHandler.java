import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
    final private String userFileName = "userData.txt";
    
    /** Auction file name **/
    final private String auctionFileName = "auctionData.txt";
    
    /** User tag to indicate data type **/
    final private String[] tagUser = {"USERNAME","PASSWORD","NAME","SURNAME","BIRTH","ADDRESS","EMAIL","BALANCE"};

    /** Auction tag to indicate data type **/
    final private String[] tagAuction = {"ITEM","CATEGORY","PICTURE","SELLER","DATESTART","DATEEND","STAGE","MINBID"};

    /** Bid tag to indicate data type **/
    final private String[] tagBid = {"BIDDER","MONEY","DATE"};

    /** Winner tag to indicate the winner of auction **/
    final private String tagWinner = "WINNERBIDDER";

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
        for(int i = 0; i < paraUser; i++)
        {
             String fields[] = parse[i].split(" ", 2);
             if(parse[0].equals(tagUser[0]) && IOUtils.validateUsername(parse[1]))
                username = parse[1];
             else if(parse[0].equals(tagUser[1]) && IOUtils.validatePassword(parse[1]))
                password = parse[1];
             else if(parse[0].equals(tagUser[2]) && IOUtils.isNullStr(parse[1]) != true)
                name = parse[1];
             else if(parse[0].equals(tagUser[3]) && IOUtils.isNullStr(parse[1]) != true)
                 surname = parse[1];
             else if(parse[0].equals(tagUser[4]) && IOUtils.validateDate(parse[1]))
                 birth = IOUtils.createTimeInstance(parse[1]);
             else if(parse[0].equals(tagUser[5]))
                 address = parse[1];
             else if(parse[0].equals(tagUser[6]) && IOUtils.validateEmail(parse[1]))
                 email = parse[1];
             else if(parse[0].equals(tagUser[7]) && IOUtils.validateInteger(parse[1]))
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
        for(int i = 0; i < paraAuction; i++)
        {
             String fields[] = parse[i].split(" ", 2);
             if(parse[0].equals(tagAuction[0]) && IOUtils.isNullStr(parse[1]) != true)
                item = parse[1];
             else if(parse[0].equals(tagAuction[1]) && IOUtils.isNullStr(parse[1]) != true)
                category = parse[1];
             else if(parse[0].equals(tagAuction[2]) && IOUtils.isNullStr(parse[1]) != true)
                picture = parse[1];
             else if(parse[0].equals(tagAuction[3]))
             {
                 seller = userManager.findUserByUsername(parse[1]);
                 if(seller == null)
                     return null;
             }
             else if(parse[0].equals(tagAuction[4]) && IOUtils.validateDateTime(parse[1]))
                  dateStart = IOUtils.createDateTimeInstance(parse[1]);
             else if(parse[0].equals(tagAuction[5]) && IOUtils.validateDateTime(parse[1]))
            	 dateEnd = IOUtils.createDateTimeInstance(parse[1]);
             else if(parse[0].equals(tagAuction[6]) && IOUtils.validateInteger(parse[1]))
                 stage = Integer.parseInt(parse[1]);
             else if(parse[0].equals(tagAuction[7]) && IOUtils.validateInteger(parse[1]))
             {
            	 minBid = Integer.parseInt(parse[1]);
            	 if(minBid < 0)
            		 return null;
             }
             else
                 return null;
        }
        if(dateStart.after(dateEnd))
    		return null;
        Auction auction = new Auction(seller, item, category, dateStart, dateEnd, minBid, picture);
        if(stage == 1)
        	auction.openAuction();
        else if(stage == 2)
        {
        	auction.openAuction();
        	auction.closeAuction();
        }
        return auction;
    }
    
    private void parseBid(String parse[], Auction auction)
    {
    	boolean bWinner = false;
    	boolean bError = false;
    	User bidder;
        int money;
        Date dateBid;
        
        UserManager userManager = UserManager.getSingletonInstance();
        for(int i = 0; i < paraBid; i++)
        {
             String fields[] = parse[i].split(" ", 2);
             if(parse[0].equals(tagBid[0]))
             {
                 bidder = userManager.findUserByUsername(parse[1]);
                 if(bidder == null)
                 {
                	 bError = true;
                     break;
                 }
             }
             else if(parse[0].equals(tagWinner))
             {
            	 bWinner = true;
                 bidder = userManager.findUserByUsername(parse[1]);
                 if(bidder == null)
                 {
                	 bError = true;
                     break;
                 }
             }
             else if(parse[0].equals(tagBid[1]) && IOUtils.validateInteger(parse[1]))
             {
            	 money = Integer.parseInt(parse[1]);
            	 if(money < 0)
            		 bError = true;
             }
             else if(parse[0].equals(tagBid[2]) && IOUtils.validateDateTime(parse[1]))
                  dateBid = IOUtils.createDateTimeInstance(parse[1]);
             else
             {
            	 bError = true;
                 break;
             }
        }
        if(bError == false)
        {
        	Bid bid = new Bid(bidder, money);
        	bid.setDate(dateBid);
        	auction.addBid(bid);
        	if(bWinner == true)
        		auction.setWinner(bid);
        }
        return auction;
    }
    
    /**
     * Read user file and add to system.
     */
    public ArrayList<User> readUsers()
    {
        String tag[];
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
            tag = new String[8];
            for(int j = 0; j < tagUser.length && bNotNull; j++)
            {
                tag[j] = reader.readLine();
                if(tag[j] == null)
                    bNotNull = false;
            }
            User user = parseUser(tag);
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
        String tag[];
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
            tag = new String[8];
            for(int j = 0; j < tagAuction.length && bNotNull; j++)
            {
                tag[j] = reader.readLine();
                if(tag[j] == null)
                    bNotNull = false;
            }
            Auction auction = parseAuction(tag);
            if(auction != null) /* If data is correct, add to user list */  
                auctionList.add(auction);
            
            /* Get Bid */
            line = reader.readLine();
            try
            {
                countBid = Integer.parseInt(line);
            }
            catch(Exception e)
            {
            	reader.close();
            	return auctionList;
            }
            
            for(int j = 0; j < countBid; j++)
            {
            	tag = new String[3];
            	for(int k = 0; k < tagBid.length && bNotNull; k++)
            	{
            		tag[k] = reader.readLine();
            		if(tag[k] == null)
            			bNotNull = false;
            	}
            }
            parseBid(tag, auction);
        }
        reader.close();
        return auctionList;
    }
    
    /**
     * Write users data to user text file.
     * @param users Users that going to write
     * @return True if don't have any problem.
     */
    public boolean writeUsers(ArrayList<User> userList)
    {
        if(userList == null)
        	return false;
        TextFileWriter writer = new TextFileWriter(userFileName);
        String line;
        if(writer.open() == false)
        	return false;
        for(User user: userList)
        {
        	writer.writeLine(tagUser[0] + " " + user.getUsername() + "\n");
        	writer.writeLine(tagUser[1] + " " + user.getPassword() + "\n");
        	writer.writeLine(tagUser[2] + " " + user.getName() + "\n");
        	writer.writeLine(tagUser[3] + " " + user.getSurname() + "\n");
        	writer.writeLine(tagUser[4] + " " + IOUtils.dateToString(user.getBirth()) + "\n");
        	writer.writeLine(tagUser[5] + " " + user.getAddress() + "\n");
        	writer.writeLine(tagUser[6] + " " + user.getEmail() + "\n");
        	writer.writeLine(tagUser[7] + " " + user.getBalance() + "\n");
        }
        writer.close();
        return true;
    }
    
    /**
     * Write auction data to auction text file
     * @param auctions Auctions that going to write
     * @return True if don't have any problem.
     */
    public boolean writeAuctions(ArrayList<Auction> auctionList)
    {
    	if(auctionList == null)
        	return false;
        TextFileWriter writer = new TextFileWriter(auctionFileName);
        String line;
        if(writer.open() == false)
        	return false;
        for(Auction auction: auctionList)
        {
        	writer.writeLine(tagAuction[0] + " " + auction.getItem() + "\n");
        	writer.writeLine(tagAuction[1] + " " + auction.getCategoryStr() + "\n");
        	writer.writeLine(tagAuction[2] + " " + auction.getPicture() + "\n");
        	writer.writeLine(tagAuction[3] + " " + auction.getSeller().getUsername() + "\n");
        	writer.writeLine(tagAuction[4] + " " + IOUtils.dateTimeToString(auction.getDateStart()) + "\n");
        	writer.writeLine(tagAuction[5] + " " + IOUtils.dateTimeToString(auction.getDateEnd()) + "\n");
        	writer.writeLine(tagAuction[6] + " " + auction.getStage() + "\n");
        	writer.writeLine(tagAuction[7] + " " + auction.getMinBidMoney() + "\n");
        	Iterator<Bid> bids = auction.getBidIterator();
        	while (bids.hasNext())
            {
            	Bid bid = bids.next();
            	User bidder = bid.getBidder();
            	if(auction.getWinner() == bid)
            		writer.writeLine(tagWinner + " " + bid.getBidder().getUsername() + "\n");
            	else
            		writer.writeLine(tagBid[0] + " " + bid.getBidder().getUsername() + "\n");
            	writer.writeLine(tagBid[1] + " " + bid.getMoney() + "\n");
            	writer.writeLine(tagBid[2] + " " + IOUtils.dateTimeToString(bid.getDateTime()) + "\n");
            }
        }
        writer.close();
        return true;
    }
    
}