import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manage, control, and keep all auction.
 * 
 * Created by Kla & Tong
 * 14 April 2020
 */
public class AuctionManager
{
    /** Keep auctionManager instance, used for singleton **/
    private static AuctionManager auctionManager = new AuctionManager();
    
    /** Auction list that in wait stage **/
    private ArrayList <Auction> waitedAuction = new ArrayList<Auction>();
    
    /** Auction list that in open stage **/
    private ArrayList <Auction> openedAuction = new ArrayList<Auction>();
    
    /** Auction list that in close stage **/
    private ArrayList <Auction> closedAuction = new ArrayList<Auction>();
    
    /** HashMap of auction list that have a key is category */
    private HashMap<Category, ArrayList<Auction>> auctionMapCategory = new HashMap<Category, ArrayList<Auction>>();
    
    /** HashMap of auction list that have a key is item */
    private HashMap<String, ArrayList<Auction>> auctionMapItem = new HashMap<String, ArrayList<Auction>>();
    
    /** HashMap of auction list that have a key is seller */
    private HashMap<User, ArrayList<Auction>> auctionMapSeller = new HashMap<User, ArrayList<Auction>>();
    
    /**
     * Constructor of auction manager.
     * Make it private to prevent to implement singleton.
     */
    private AuctionManager()
    {
    }
    
    /**
     * Get instance of auction manager.
     * Implement singleton
     * @return AuctionManager instance
     */
    public static AuctionManager getSingletonInstance()
    {
        return auctionManager;
    }
    
    /**
     * Initialize for AuctionManager
     * @param auctionList
     */
    public void initialAuction(ArrayList<Auction> auctionList)
    {
        
    }
    
    /**
     * Create the auction and add to auction manager
     * @param item Item name
     * @param category Category of item
     * @param picture Picture url
     * @param minBid Minimum money of bid
     * @param dateStart Start date of an auction
     * @param dateEnd End date of an auction
     * @return True if can create the auction. Otherwise, false
     */
    public boolean createAuction(String item, String category, String picture,
            int minBid, String dateStart, String dateEnd)
    {
        boolean bCheck = false;
        
        return bCheck;
    }
    
    /**
     * Create the auction and add to auction manager
     * @param item Item name
     * @param category Category of item
     * @param picture picture url
     * @param minBid Minimum money of bid
     * @param dateEnd End date of an auction
     * @return True if can create the auction. Otherwise, false
     */
    public boolean createAuction(String item, String category, String picture, int minBid, String dateEnd)
    {
        boolean bCheck = false;
        
        return bCheck;
    }
    
    /**
     * Get the auction list from stage of an auction
     * @param stage Stage of auction (0-waited, 1-opened, 2-closed)
     * @return Array List of auction from requiring stage.
     */
    public ArrayList<Auction> searchAuctionByStage(int stage)
    {
        ArrayList<Auction> auctions = null;

        if (stage == 0)
            auctions = waitedAuction;
        else if (stage == 1)
            auctions = openedAuction;
        else if (stage == 2)
            auctions = closedAuction;
        return auctions;
    }
    
    public ArrayList<Auction> searchAuctionByCat(String category)
    {
    }
    
    public ArrayList<Auction> searchAuctionByItem(String item)
    {
        
    }
    
    public ArrayList<Auction> searchAuctionBySeller(String seller)
    {
        
    }
    
    public ArrayList <Auction> searchAuctionByMinPrice(int money)
    {
        
    }
    
    public ArrayList<Auction> sortAuction(ArrayList auctions)
    {
        
    }
    
    public boolean updateAuctionStage(Auction auction)
    {
        
    }
}