import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Represent the auction object in auction program. Contain the info of auction item,
 * bid, and seller. In addition manage the bid too.
 * 
 * Created by Kla & Tong 14 April 2020
 */
public class Auction
{
    /** Item name **/
    private String item;

    /** Category of item **/
    private Category category;

    /** Picture url of item */
    private String picture = null;

    /** Seller of an auction **/
    private User seller;

    /** The Bid that won the auction **/
    private Bid winner = null;

    /** Start date to open an auction **/
    private Date dateStart;

    /** End date to close an auction **/
    private Date dateEnd;

    /** Stage of an auction (wait:0, open:1, closed:2) **/
    private int stage = 0;

    /** Minimum bid of auction **/
    private int minBid = 0;

    /** List of bid **/
    private ArrayList<Bid> bidList = new ArrayList<Bid>();

    /**
     * Constructor of Auction.
     * 
     * @param seller    Seller who open an auction
     * @param item      Item name
     * @param category  Category of an auction
     * @param dateStart Start date of an auction
     * @param dateEnd   End date of an auction
     * @param minBid    Minimum money to bid
     * @param picture   Picture of item
     */
    public Auction(User seller, String item, Category category, Date dateStart,
            Date dateEnd, int minBid, String picture)
    {
        this.item = item;
        this.category = category;
        this.seller = seller;
        this.dateEnd = dateEnd;
        this.dateStart = dateStart;
        this.minBid = minBid;
        this.picture = picture;
        seller.addSelling(this);
    }

    /**
     * Get item name that sell in auction
     * 
     * @return Item name
     */
    public String getItem()
    {
        return this.item;
    }

    /**
     * Get picture file name of item.
     * 
     * @return URL picture
     */
    public String getPicture()
    {
        return this.picture;
    }

    /**
     * Get the seller of auction
     * 
     * @return The seller user
     */
    public User getSeller()
    {
        return this.seller;
    }

    /**
     * The winner for the closed auction
     * 
     * @return The winner user of auction
     */
    public Bid getWinner()
    {
        return this.winner;
    }

    /**
     * Get the opened date of an Auction
     * 
     * @return Opened date of an auction
     */
    public Date getDateStart()
    {
        return this.dateStart;
    }

    /**
     * Get the closed date of an Auction
     * 
     * @return Closed date of an auction
     */
    public Date getDateEnd()
    {
        return this.dateEnd;
    }

    /**
     * Get the category in string
     * 
     * @return Category string
     */
    public String getCategoryStr()
    {
        return category.getCategoryStr();
    }

    /**
     * Get the stage of an auction
     * 
     * @return Stage of auction. 0 - wait, 1 - open, 2- close
     */
    public int getStage()
    {
        return this.stage;
    }

    /**
     * Minimum bid at start
     * 
     * @return minimum bid of the auction at the start of the auction.
     */
    public int getMinBid()
    {
        return this.minBid;
    }

    /**
     * Get current price of this auction
     * 
     * @return current bid price
     */
    public int getCurrentBidMoney()
    {
        if (!bidList.isEmpty())
            return bidList.get(bidList.size() - 1).getMoney();
        else
            return minBid;
    }

    /**
     * Number of bid in the auction.
     * 
     * @return number of bid
     */
    public int getNumberOfBid()
    {
        return bidList.size();
    }

    /**
     * Iterator of bid
     * 
     * @return iterator of bid
     */
    public ArrayList<Bid> getBidList()
    {
        return this.bidList;
    }

    /**
     * Get bid of user
     * 
     * @return Latest Bid that user bid to auction
     */
    public Bid getBidByUser(User user)
    {
        Bid retBid = null;
        /* Loop from the end (the highest money) */
        for (int i = bidList.size() - 1; i >= 0; i--)
        {
            Bid bid = bidList.get(i);
            if (bid.getBidder() == user)
            {
                retBid = bid;
                break;
            }
        }
        return retBid;
    }

    /**
     * Check the auction is it has a bid.
     * 
     * @return true when this auction has someone bid on it.
     */
    public boolean isBid()
    {
        if (bidList.isEmpty())
            return false;
        else
            return true;
    }

    /**
     * Set winner of an auction (Used in read file). Also check that the bid is in
     * the auction or not before set.
     * 
     * @param winner The bid that win the auction
     * @return Return true if can set and found this bid in auction. Otherwise,
     *         false.
     */
    public boolean setWinner(Bid winner)
    {
        boolean bCheck = false;
        if (winner != null && bidList.contains(winner) == true)
        {
            this.winner = winner;
            bCheck = true;
        }
        return bCheck;
    }

    /**
     * Set stage of auction. (Used in read file).
     * 
     * @param stage that will be set to the auction
     * @return true when stage is between 0-2. Otherwise is false.
     */
    public boolean setStage(int stage)
    {
        if ((stage >= 0) && (stage <= 2))
        {
            this.stage = stage;
            return true;
        }
        else
            return false;
    }

    /**
     * Open the auction. The current stage must be waited auction.
     * 
     * @return True if can open. Otherwise, false.
     */
    public boolean openAuction()
    {
        boolean bCheck = false;
        if (stage == 0)
        {
            stage = 1;
            bCheck = true;
        }
        return bCheck;
    }

    /**
     * Close the auction. The current stage must be closed auction Find out the
     * winner and deduct money from account.
     * 
     * @return True if can close. Otherwise, false.
     */
    public boolean closeAuction()
    {
        boolean bCheck = false;
        if (stage == 1)
        {
            stage = 2;
            
            /* Find winner, deduct money from bidder and add money to seller */
            for (int i = bidList.size() - 1; i >= 0; i--)
            {
                Bid bid = bidList.get(i);
                User bidder = bid.getBidder();
                int bidMoney = bid.getMoney();
                if (bidder.deductMoney(bidMoney))
                {
                    seller.addMoney(bidMoney);
                    winner = bid;
                    break;
                }
            }
            bCheck = true;
        }
        return bCheck;
    }

    /**
     * Make a bid to the auction. It'll error if don't have any user or money that
     * want to bid doesn't more than minimum money and maximum bid.
     * 
     * @param user  User that want to bid
     * @param money Amount of money that want to bid
     * @return True, if can bid. Otherwise , false.
     */
    public boolean makeBid(User user, int money)
    {
        boolean bCheck = false;
        int startBidPrice = minBid - 1;

        /* Check stage is open or not */
        if (stage == 1)
        {
            /*
             * Check that have anyone bid or not. If have, set minimum money to
             * maximum bid from bid set instead
             */
            if (!bidList.isEmpty())
                startBidPrice = bidList.get(bidList.size() - 1).getMoney();

            if (user != null && money > startBidPrice)
            {
                Bid createBid = new Bid(user, money);
                /* Add bid to auction and user */
                if (bidList.add(createBid) && user.addBid(this))
                    bCheck = true;
                Collections.sort(bidList);
            }
        }
        return bCheck;
    }

    /**
     * Add bid to auction. Used for read from file
     * 
     * @param bid Bid that want to add
     */
    public void addBid(Bid bid)
    {
        if (bid != null)
            bidList.add(bid);
        Collections.sort(bidList);
    }
}