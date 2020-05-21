import java.util.Date;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * Represent the auction object in auction program. Contain the auction item, bidder,
 * and seller.
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
    private TreeSet<Bid> bidSet = new TreeSet<Bid>();

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
    public Auction(User seller, String item, String category, Date dateStart,
            Date dateEnd, int minBid, String picture)
    {
        this.item = item;
        this.category = Category.findCategory(category);
        this.seller = seller;
        this.dateEnd = dateEnd;
        seller.addSelling(this);
        this.dateStart = dateStart;
        this.minBid = minBid;
        this.picture = picture;
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
     * Get url picture of item.
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
     * Get the category of item
     * 
     * @return Category
     */
    public String getCategoryStr()
    {
        return category.getCategoryStr();
    }

    /**
     * Get the stage of an auction
     * 
     * @return Stage
     */
    public int getStage()
    {
        return this.stage;
    }

    /**
     * Get current price of this auction
     * 
     * @return current bid price
     */
    public int getCurrentBidMoney()
    {
        if (bidSet.last() != null)
            return bidSet.last().getMoney();
        else 
            return minBid;
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
     * Number of bid in the auction.
     * 
     * @return number of bid
     */
    public int getNumberOfBid()
    {
        return bidSet.size();
    }

    /**
     * Iterator of bid
     * 
     * @return iterator of bid
     */
    public Iterator<Bid> getBidIterator()
    {
        return this.bidSet.iterator();
    }

    /**
     * Check the auction is it has a bid.
     * 
     * @return true when this auction has someone bid on it.
     */
    public boolean isBid()
    {
        if (bidSet.isEmpty())
            return false;
        else
            return true;
    }

    /**
     * Set winner of an auction (Used in read file). Also check that the bid is in
     * the auction before set.
     * 
     * @param winner The bid that win the auction
     * @return Return true if can set and found this bid in auction. Otherwise,
     *         false.
     */
    public boolean setWinner(Bid winner)
    {
        boolean bCheck = false;
        if (winner != null)
        {
            if (bidSet.contains(winner) == true)
            {
                this.winner = winner;
                bCheck = true;
            }
        }
        return bCheck;
    }

    /**
     * Set stage of auction
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
            Iterator<Bid> bids = bidSet.descendingIterator();
            while (bids.hasNext())
            {
                Bid bid = bids.next();
                User bidder = bid.getBidder();
                int bidMoney = bid.getMoney();
                if (bidder.deductMoney(bidMoney))
                {
                    winner = bid;
                    break;
                }
            }
            stage = 2;
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
            if (bidSet.last() != null)
                startBidPrice = bidSet.last().getMoney();

            if (user != null && money > startBidPrice)
            {
                Bid createBid = new Bid(user, money);
                /* Add bid to auction and user */
                if (bidSet.add(createBid) && user.addBid(this))
                    bCheck = true;
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
            bidSet.add(bid);
    }
}