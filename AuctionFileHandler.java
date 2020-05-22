import java.util.ArrayList;
import java.util.Date;

/**
 * Handle user file data and auction file data.
 * 
 * Created by Kla & Tong 19 May 2020
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
    final private String[] tagUser =
    { "USERNAME", "PASSWORD", "NAME", "BIRTH", "ADDRESS", "EMAIL", "BALANCE" };

    /** Auction tag to indicate data type **/
    final private String[] tagAuction =
    { "ITEM", "CATEGORY", "PICTURE", "SELLER", "DATESTART", "DATEEND", "STAGE",
            "MINBID" };

    /** Bid tag to indicate data type **/
    final private String[] tagBid =
    { "BIDDER", "MONEY", "DATE" };

    /** Winner tag to indicate the winner of auction **/
    final private String tagWinner = "WINNERBIDDER";

    /**
     * Constructor of AuctionFileHandler. Prevent another class can create the
     * instance. Implement for singleton
     */
    private AuctionFileHandler()
    {
    }

    /**
     * Get instance of AuctionFileHandler. Implement singleton
     * 
     * @return
     */
    public static AuctionFileHandler getSingletonInstance()
    {
        return auctionFileHandler;
    }

    /**
     * Get the parse of user, validate, and create user.
     * 
     * @param parse Parse of command the want to check
     * @return User if the data is valid. Otherwise, null.
     */
    private User parseUser(String parse[])
    {
        String username = null;
        String password = null;
        String name = null;
        Date birth = null;
        String address = null;
        String email = null;
        int balance = 0;

        /** Loop through each data and validate tag **/
        for (int i = 0; i < tagUser.length; i++)
        {
            String[] fields = parse[i].split(" ", 2);
            String tag = fields[0].trim();
            String text = fields[1].trim();
            /** Check username **/
            if (tag.equals(tagUser[0]) && IOUtils.validateUsername(text))
                username = text;
            /** Check password **/
            else if (tag.equals(tagUser[1])
                    && IOUtils.validatePassword(text))
                password = text;
            /** Check name **/
            else if (tag.equals(tagUser[2])
                    && IOUtils.isNullStr(text) == false)
                name = text;
            /** Check birth date **/
            else if (tag.equals(tagUser[3])
                    && DateUtils.validateDateStr(text))
                birth = DateUtils.strToDate(text);
            /** Check address **/
            else if (tag.equals(tagUser[4]))
                address = text;
            /** Check email **/
            else if (tag.equals(tagUser[5])
                    && IOUtils.validateEmail(text))
                email = text;
            /** Check balance account **/
            else if (tag.equals(tagUser[6])
                    && IOUtils.validateInteger(text))
                balance = Integer.parseInt(text);
            else
                return null;
        }
        User user = new User(username, password, name, birth, address, email);
        user.addMoney(balance);
        return user;
    }

    /**
     * Get the parse of auction, validate, and create auction.
     * 
     * @param parse Parse of command the want to check
     * @return Auction if the data is valid. Otherwise, null.
     */
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
        /** Loop through each data and validate tag **/
        for (int i = 0; i < tagAuction.length; i++)
        {
            String[] fields = parse[i].split(" ", 2);
            String tag = fields[0].trim();
            String text = fields[1].trim();
            /** Check item name **/
            if (tag.equals(tagAuction[0])
                    && IOUtils.isNullStr(text) != true)
                item = text;
            /** Check category **/
            else if (tag.equals(tagAuction[1])
                    && IOUtils.isNullStr(text) != true)
                category = text;
            /** Check picture **/
            else if (tag.equals(tagAuction[2])
                    && IOUtils.isNullStr(text) != true)
                picture = text;
            /** Check seller user **/
            else if (tag.equals(tagAuction[3]))
            {
                seller = userManager.findUserByUsername(text);
                if (seller == null)
                    return null;
            }
            /** Check start date **/
            else if (tag.equals(tagAuction[4])
                    && DateUtils.validateDateTimeStr(text))
                dateStart = DateUtils.strToDateTime(text);
            /** Check close date **/
            else if (tag.equals(tagAuction[5])
                    && DateUtils.validateDateTimeStr(text))
                dateEnd = DateUtils.strToDateTime(text);
            /** Check stage **/
            else if (tag.equals(tagAuction[6])
                    && IOUtils.validateInteger(text))
            {
                stage = Integer.parseInt(text);
                if (stage < 0 || stage > 2)
                    return null;
            }
            /** Check minimum bid money **/
            else if (tag.equals(tagAuction[7])
                    && IOUtils.validateInteger(text))
            {
                minBid = Integer.parseInt(text);
                if (minBid < 0)
                    return null;
            }
            else
                return null;
        }
        /** Check that start date after end date or not **/
        if (dateStart.after(dateEnd))
            return null;
        Auction auction = new Auction(seller, item, category, dateStart, dateEnd,
                minBid, picture);

        /** Update stage **/
        if (auction.setStage(stage) == false)
            return null;
        return auction;
    }

    /**
     * Get the parse of bid, validate, create bid, and add to auction
     * 
     * @param parse   Parse of command that want to check
     * @param auction Auction that going to add bid
     */
    private void parseBid(String parse[], Auction auction)
    {
        boolean bWinner = false;
        boolean bError = false;
        User bidder = null;
        int money = 0;
        Date dateBid = null;

        UserManager userManager = UserManager.getSingletonInstance();
        /** Loop through each tag and then get the data **/
        for (int i = 0; i < tagBid.length && !bError; i++)
        {
            String[] fields = parse[i].split(" ", 2);
            String tag = fields[0].trim();
            String text = fields[1].trim();
            /** Get bidder **/
            if (tag.equals(tagBid[0]) || tag.equals(tagWinner))
            {
                bidder = userManager.findUserByUsername(text);
                if (bidder == null)
                {
                    bError = true;
                    break;
                }
                if (tag.equals(tagWinner))
                    bWinner = true;
            }

            /** Check bid money **/
            else if (tag.equals(tagBid[1])
                    && IOUtils.validateInteger(text))
            {
                money = Integer.parseInt(text);
                if (money < 0)
                {
                    bError = true;
                    break;
                }
            }

            /** Check bid date **/
            else if (tag.equals(tagBid[2])
                    && DateUtils.validateDateTimeStr(text))
                dateBid = DateUtils.strToDateTime(text);
            else
            {
                bError = true;
                break;
            }
        }
        if (bError == false) /** If don't have any error occur **/
        {
            Bid bid = new Bid(bidder, money);
            bid.setDate(dateBid);
            auction.addBid(bid);
            if (bWinner == true)
                auction.setWinner(bid);
            bid.getBidder().addBid(auction);
        }
        return;
    }

    /**
     * Read user file and return list of user back.
     * 
     * @return List of user
     */
    public ArrayList<User> readUsers()
    {
        String tag[];
        boolean bNotNull = true;
        int count = 0;
        ArrayList<User> userList = new ArrayList<User>();

        /** Open file. If cannot open, return null; **/
        TextFileReader reader = new TextFileReader(userFileName);
        if (reader.open() == false)
            return null;

        /** Read number of data **/
        String line;
        line = reader.readLine();
        try
        {
            count = Integer.parseInt(line);
        }
        catch (Exception e)
        {
            return null;
        }

        /** Loop get user and add to list **/
        int numTag = tagUser.length;
        for (int i = 0; i < count && bNotNull; i++)
        {
            tag = new String[numTag];
            for (int j = 0; j < numTag && bNotNull; j++)
            {
                tag[j] = reader.readLine();
                if (tag[j] == null)
                    bNotNull = false;
            }
            User user = parseUser(tag);
            if (user != null) /* If data is correct, add to user list */
                userList.add(user);
        }
        reader.close();
        return userList;
    }

    /**
     * Read auction file and return list of auction back.
     * 
     * @return List of auction
     */
    public ArrayList<Auction> readAuctions()
    {
        String tag[];
        int countAuction = 0;
        boolean bNotNull = true;
        
        ArrayList<Auction> auctionList = new ArrayList<Auction>();

        /** Open file. If cannot open, return null; **/
        TextFileReader reader = new TextFileReader(auctionFileName);
        if (reader.open() == false)
            return null;

        /** Read number of data **/
        String line;
        line = reader.readLine();
        try
        {
            countAuction = Integer.parseInt(line);
        }
        catch (Exception e)
        {
            return null;
        }

        /** Loop get auction, loop get bid, and add to list **/
        int numTag = tagAuction.length;
        for (int i = 0; i < countAuction && bNotNull; i++)
        {
            /* Get Auction */
            tag = new String[numTag];
            for (int j = 0; j < numTag && bNotNull; j++)
            {
                tag[j] = reader.readLine();
                if (tag[j] == null)
                    bNotNull = false;
            }
            Auction auction = parseAuction(tag);
            if (auction != null) /* If data is correct, add to user list */
            {
                auctionList.add(auction);
                readBids(reader, auction);
            }
        }
        reader.close();
        return auctionList;
    }
    
    private void readBids(TextFileReader reader, Auction auction)
    {
        String tag[];
        int countBid = 0;
        boolean bNotNull = true;
        
        /** Get No. of Bid **/
        String line = reader.readLine();
        try
        {
            countBid = Integer.parseInt(line);
        }
        catch (Exception e)
        {
            return;
        }
        
        /** Read all bid in auction **/
        int numTag = tagBid.length;
        for (int i = 0; i < countBid && bNotNull; i++)
        {
            tag = new String[numTag];
            for (int j = 0; j < numTag && bNotNull; j++)
            {
                tag[j] = reader.readLine();
                if (tag[j] == null)
                    bNotNull = false;
            }
            parseBid(tag, auction);
        }
    }

    /**
     * Write users data to user text file.
     * 
     * @param users Users that going to write
     * @return True if don't have any problem.
     */
    public boolean writeUsers(ArrayList<User> userList)
    {
        if (userList == null)
            return false;
        /** Open file **/
        TextFileWriter writer = new TextFileWriter(userFileName);
        if (writer.open() == false)
            return false;

        writer.writeLine(userList.size() + "\n");
        /** Write each user **/
        for (User user : userList)
        {
            writer.writeLine(tagUser[0] + " " + user.getUsername() + "\n");
            writer.writeLine(tagUser[1] + " " + user.getPassword() + "\n");
            writer.writeLine(tagUser[2] + " " + user.getName() + "\n");
            writer.writeLine(
                    tagUser[3] + " " + DateUtils.dateToStr(user.getBirth()) + "\n");
            writer.writeLine(tagUser[4] + " " + user.getAddress() + "\n");
            writer.writeLine(tagUser[5] + " " + user.getEmail() + "\n");
            writer.writeLine(tagUser[6] + " " + user.getBalance() + "\n");
        }
        writer.close();
        return true;
    }

    /**
     * Write auction data to auction text file
     * 
     * @param auctions Auction list that going to write
     * @return True if don't have any problem.
     */
    public boolean writeAuctions(ArrayList<Auction> auctionList)
    {
        if (auctionList == null)
            return false;

        /** Open file **/
        TextFileWriter writer = new TextFileWriter(auctionFileName);
        if (writer.open() == false)
            return false;
        writer.writeLine(auctionList.size() + "\n");
        /** Write each auction **/
        for (Auction auction : auctionList)
        {
            writer.writeLine(tagAuction[0] + " " + auction.getItem() + "\n");
            writer.writeLine(tagAuction[1] + " " + auction.getCategoryStr() + "\n");
            writer.writeLine(tagAuction[2] + " " + auction.getPicture() + "\n");
            writer.writeLine(
                    tagAuction[3] + " " + auction.getSeller().getUsername() + "\n");
            writer.writeLine(tagAuction[4] + " "
                    + DateUtils.dateTimeToStr(auction.getDateStart()) + "\n");
            writer.writeLine(tagAuction[5] + " "
                    + DateUtils.dateTimeToStr(auction.getDateEnd()) + "\n");
            writer.writeLine(tagAuction[6] + " " + auction.getStage() + "\n");
            writer.writeLine(tagAuction[7] + " " + auction.getMinBid() + "\n");

            /** Write each bid **/
            ArrayList<Bid> bidList = auction.getBidList();
            writer.writeLine(auction.getNumberOfBid() + "\n");
            for(Bid bid: bidList)
            {
                User bidder = bid.getBidder();
                if (auction.getWinner() == bid)
                    writer.writeLine(tagWinner + " " + bidder.getUsername() + "\n");
                else
                    writer.writeLine(tagBid[0] + " " + bidder.getUsername() + "\n");
                writer.writeLine(tagBid[1] + " " + bid.getMoney() + "\n");
                writer.writeLine(tagBid[2] + " "
                        + DateUtils.dateTimeToStr(bid.getDateTime()) + "\n");
            }
        }

        writer.close();
        return true;
    }

}