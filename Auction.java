import java.util.Date;
import java.util.TreeSet;
import java.util.Iterator;

/**
 * Represent the auction object in auction program.
 * Contain the auction item, bidder, and seller.
 * 
 * Created by Kla & Tong
 * 14 April 2020
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
     * Constructor of Auction for opening auction later.
     * @param dateEnd2 
     * @param dateStart2 
     * @param category2 
     * @param item2 
     * @param seller2 
     * @param item Item name
     * @param category Category of an auction
     * @param seller The seller or user that create a new auction
     * @param dateStart Date that want to start an auction
     * @param dateEnd Date that want to close an auction
     */
    public Auction(User seller, String item, String category,
    		Date dateStart, Date dateEnd, int minBid, String picture)
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
     * @return Item name
     */
    public String getItem()
    {
        return this.item;
    }

    /**
     * Get url picture of item.
     * @return URL picture
     */
    public String getPicture()
    {
        return this.picture;
    }
    
    /**
     * Get the seller of auction
     * @return The  seller user
     */
    public User getSeller()
    {
        return this.seller;
    }
    
    /**
     * The winner for the closed auction
     * @return The winner user of auction
     */
    public Bid getWinner()
    {
        return this.winner;
    }
    
    /**
     * Get the opened date of an Auction
     * @return Opened date of an auction
     */
    public Date getDateStart()
    {
        return this.dateStart;
    }
    
    /**
     * Get the closed date of an Auction
     * @return Closed date of an auction
     */
    public Date getDateEnd()
    {
        return this.dateEnd;
    }
    
    /**
     * Get the current max bid of auction
     * @return Max bid of auction
     */
    public Bid getMaxBid()
    {
        return bidSet.last();
    }
    
    /**
     * Get the category of item
     * @return Category
     */
    public String getCategory()
    {
        return category.getCategoryStr();
    }
    
    /**
     * Get the stage of an auction
     * @return Stage
     */
    public int getStage()
    {
        return this.stage;
    }
    
    /**
     * Get the Minimum money to bid of an auction
     * @return Stage
     */
    public int getMinBidMoney()
    {
        return this.minBid;
    }
    
    public boolean setWinner(Bid winner)
    {
    	boolean bCheck = false;
    	if(winner != null)
    	{
    		this.winner = winner;
    		bCheck = true;
    	}
    	return bCheck;
    }
    
    /**
     * Open the auction. The current stage must be waited auction.
     * @return True if can open. Otherwise, false.
     */
    public boolean openAuction()
    {
        boolean bCheck = false;
        if(stage == 0)
        {
            stage = 1;
            bCheck = true;
        }
        return bCheck;
    }
    
    /**
     * Close the auction. The current stage must be closed auction
     * Find out the winner and deduct money from account.
     * @return True if can close. Otherwise, false.
     */
    public boolean closeAuction()
    {
        boolean bCheck = false;
        if(stage == 1)
        {
            Iterator<Bid> bids = bidSet.descendingIterator();
            while (bids.hasNext())
            {
            	Bid bid = bids.next();
            	User bidder = bid.getBidder();
            	int bidMoney = bid.getMoney();
            	if(bidder.deductMoney(bidMoney))
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
     * Make a bid to the auction. It'll error if don't have any user or money
     * that want to bid doesn't more than minimum money and maximum bid.
     * @param user User that want to bid
     * @param money Amount of money that want to bid
     * @return True, if can bid. Otherwise , false.
     */
    public boolean makeBid(User user, int money)
    {
        boolean bCheck = false;
        int minMoney = minBid;
        
        /* Check stage is open or not */
        if(stage == 1)
        {
            /* Check that have anyone bid or not.
             * If have, set minimum money to maximum bid from bid set instead */
            if(bidSet.last() != null)
                minMoney = bidSet.last().getMoney();
            
            if(user != null && money > minMoney)
            {
                Bid createBid = new Bid(user, money);
                /* Add bid to auction and user */
                if(bidSet.add(createBid) && user.addBid(this))
                    bCheck = true;
            }
        }
        return bCheck;
    }
}