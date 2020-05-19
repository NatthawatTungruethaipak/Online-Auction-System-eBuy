import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Manage the Auction file
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
    { "USERNAME", "PASSWORD", "NAME", "SURNAME", "BIRTH", "ADDRESS", "EMAIL", "BALANCE" };

    /** Auction tag to indicate data type **/
    final private String[] tagAuction =
    { "ITEM", "CATEGORY", "PICTURE", "SELLER", "DATESTART", "DATEEND", "STAGE", "MINBID" };

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
        String surname = null;
        Date birth = null;
        String address = null;
        String email = null;
        int balance = 0;

        /** Loop through each data and validate tag **/
        for (int i = 0; i < tagUser.length; i++)
        {
            String[] fields = parse[i].split(" ", 2);
            /** Check username **/
            if (fields[0].equals(tagUser[0]) && IOUtils.validateUsername(fields[1]))
                username = fields[1];
            /** Check password **/
            else if (fields[0].equals(tagUser[1]) && IOUtils.validatePassword(fields[1]))
                password = fields[1];
            /** Check name **/
            else if (fields[0].equals(tagUser[2]) && IOUtils.isNullStr(fields[1]) == false)
                name = fields[1];
            /** Check surname **/
            else if (fields[0].equals(tagUser[3]) && IOUtils.isNullStr(fields[1]) == false)
                surname = fields[1];
            /** Check birth date **/
            else if (fields[0].equals(tagUser[4]) && IOUtils.validateDate(fields[1]))
                birth = IOUtils.strToDate(fields[1]);
            /** Check address **/
            else if (fields[0].equals(tagUser[5]))
                address = fields[1];
            /** Check email **/
            else if (fields[0].equals(tagUser[6]) && IOUtils.validateEmail(fields[1]))
                email = fields[1];
            /** Check balance account **/
            else if (fields[0].equals(tagUser[7]) && IOUtils.validateInteger(fields[1]))
                balance = Integer.parseInt(fields[1]);
            else
                return null;
        }
        User user = new User(username, password, name, surname, birth, address, email);
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
            /** Check item name **/
            if (fields[0].equals(tagAuction[0]) && IOUtils.isNullStr(fields[1]) != true)
                item = fields[1];
            /** Check category **/
            else if (fields[0].equals(tagAuction[1]) && IOUtils.isNullStr(fields[1]) != true)
                category = fields[1];
            /** Check picture **/
            else if (fields[0].equals(tagAuction[2]) && IOUtils.isNullStr(fields[1]) != true)
                picture = fields[1];
            /** Check seller user **/
            else if (fields[0].equals(tagAuction[3]))
            {
                seller = userManager.findUserByUsername(fields[1]);
                if (seller == null)
                    return null;
            }
            /** Check start date **/
            else if (fields[0].equals(tagAuction[4]) && IOUtils.validateDateTime(fields[1]))
                dateStart = IOUtils.strToDateTime(fields[1]);
            /** Check close date **/
            else if (fields[0].equals(tagAuction[5]) && IOUtils.validateDateTime(fields[1]))
                dateEnd = IOUtils.strToDateTime(fields[1]);
            /** Check stage **/
            else if (fields[0].equals(tagAuction[6]) && IOUtils.validateInteger(fields[1]))
            {
                stage = Integer.parseInt(fields[1]);
                if (stage < 0 || stage > 2)
                    return null;
            }
            /** Check minimum bid money **/
            else if (fields[0].equals(tagAuction[7]) && IOUtils.validateInteger(fields[1]))
            {
                minBid = Integer.parseInt(fields[1]);
                if (minBid < 0)
                    return null;
            }
            else
                return null;
        }
        /** Check that start date after end date or not **/
        if (dateStart.after(dateEnd))
            return null;
        Auction auction = new Auction(seller, item, category, dateStart, dateEnd, minBid, picture);

        /** Update stage **/
        if (stage == 1)
            auction.openAuction();
        else if (stage == 2)
        {
            auction.openAuction();
            auction.closeAuction();
        }
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
        for (int i = 0; i < tagBid.length; i++)
        {
            String[] fields = parse[i].split(" ", 2);
            /** Get bidder **/
            if (fields[0].equals(tagBid[0]))
            {
                bidder = userManager.findUserByUsername(fields[1]);
                if (bidder == null)
                {
                    bError = true;
                    break;
                }
            }

            /** Check winner bidder. */
            else if (fields[0].equals(tagWinner))
            {
                bWinner = true;
                bidder = userManager.findUserByUsername(fields[1]);
                if (bidder == null)
                {
                    bError = true;
                    break;
                }
            }

            /** Check bid money **/
            else if (fields[0].equals(tagBid[1]) && IOUtils.validateInteger(fields[1]))
            {
                money = Integer.parseInt(fields[1]);
                if (money < 0)
                    bError = true;
            }

            /** Check bid date **/
            else if (fields[0].equals(tagBid[2]) && IOUtils.validateDateTime(fields[1]))
                dateBid = IOUtils.strToDateTime(fields[1]);
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
        for (int i = 0; i < count && bNotNull; i++)
        {
            tag = new String[8];
            for (int j = 0; j < tagUser.length && bNotNull; j++)
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
        boolean bNotNull = true;
        int countAuction = 0;
        int countBid = 0;
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
        for (int i = 0; i < countAuction && bNotNull; i++)
        {
            /* Get Auction */
            tag = new String[8];
            for (int j = 0; j < tagAuction.length && bNotNull; j++)
            {
                tag[j] = reader.readLine();
                if (tag[j] == null)
                    bNotNull = false;
            }
            Auction auction = parseAuction(tag);
            if (auction != null) /* If data is correct, add to user list */
                auctionList.add(auction);

            /* Get Bid */
            line = reader.readLine();
            try
            {
                countBid = Integer.parseInt(line);
            }
            catch (Exception e)
            {
                reader.close();
                return auctionList;
            }

            for (int j = 0; j < countBid; j++)
            {
                tag = new String[3];
                for (int k = 0; k < tagBid.length && bNotNull; k++)
                {
                    tag[k] = reader.readLine();
                    if (tag[k] == null)
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

        /** Write each user **/
        for (User user : userList)
        {
            writer.writeLine(tagUser[0] + " " + user.getUsername() + "\n");
            writer.writeLine(tagUser[1] + " " + user.getPassword() + "\n");
            writer.writeLine(tagUser[2] + " " + user.getName() + "\n");
            writer.writeLine(tagUser[3] + " " + user.getSurname() + "\n");
            writer.writeLine(tagUser[4] + " " + IOUtils.dateToStr(user.getBirth()) + "\n");
            writer.writeLine(tagUser[5] + " " + user.getAddress() + "\n");
            writer.writeLine(tagUser[6] + " " + user.getEmail() + "\n");
            writer.writeLine(tagUser[7] + " " + user.getBalance() + "\n");
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

        /** Write each auction **/
        for (Auction auction : auctionList)
        {
            writer.writeLine(tagAuction[0] + " " + auction.getItem() + "\n");
            writer.writeLine(tagAuction[1] + " " + auction.getCategoryStr() + "\n");
            writer.writeLine(tagAuction[2] + " " + auction.getPicture() + "\n");
            writer.writeLine(tagAuction[3] + " " + auction.getSeller().getUsername() + "\n");
            writer.writeLine(tagAuction[4] + " " + IOUtils.dateTimeToStr(auction.getDateStart()) + "\n");
            writer.writeLine(tagAuction[5] + " " + IOUtils.dateTimeToStr(auction.getDateEnd()) + "\n");
            writer.writeLine(tagAuction[6] + " " + auction.getStage() + "\n");
            writer.writeLine(tagAuction[7] + " " + auction.getMinBidMoney() + "\n");

            /** Write each bid **/
            Iterator<Bid> bids = auction.getBidIterator();
            while (bids.hasNext())
            {
                Bid bid = bids.next();
                User bidder = bid.getBidder();
                if (auction.getWinner() == bid)
                    writer.writeLine(tagWinner + " " + bidder.getUsername() + "\n");
                else
                    writer.writeLine(tagBid[0] + " " + bidder.getUsername() + "\n");
                writer.writeLine(tagBid[1] + " " + bid.getMoney() + "\n");
                writer.writeLine(tagBid[2] + " "
                        + IOUtils.dateTimeToStr(bid.getDateTime()) + "\n");
            }
        }

        writer.close();
        return true;
    }

}