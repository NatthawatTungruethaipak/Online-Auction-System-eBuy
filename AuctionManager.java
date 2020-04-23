import java.util.ArrayList;
import java.util.Date;
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
    public boolean createAuction(User seller, String item, String category,
            String picture, int minBid, Date dateStart, Date dateEnd)
    {
        Auction createdAuction = new Auction(seller, item, category, dateStart, dateEnd);
        
        /** Add auction to stage list **/
        if(createdAuction.getStage() == 0)
            waitedAuction.add(createdAuction);
        else if(createdAuction.getStage() == 1)
            openedAuction.add(createdAuction);
        else
            return false;
        
        /** Add auction to hash map of category **/
        ArrayList<Auction> auctionCategoryList = auctionMapCategory.get(Category.findCategory(category));
        if(auctionCategoryList != null)
            auctionCategoryList.add(createdAuction);
        else
            return false;      
               
        /** Add auction to hash map of item **/
        ArrayList<Auction> auctionItemList = auctionMapItem.get(item);
        if(auctionItemList != null)
            auctionItemList.add(createdAuction);
        else /* If don't have a list in hash map, create new one */
        {
            auctionItemList = new ArrayList<Auction>();
            auctionItemList.add(createdAuction);
            auctionMapItem.put(item, auctionItemList);
        }
        
        /** Add auction to hash map of seller **/
        ArrayList<Auction> auctionSellerList = auctionMapSeller.get(seller);
        if(auctionSellerList != null)
            auctionSellerList.add(createdAuction);
        else /* If don't have a list in hash map, create new one */
        {
            auctionSellerList = new ArrayList<Auction>();
            auctionSellerList.add(createdAuction);
            auctionMapSeller.put(seller, auctionSellerList);
        }
        return true;
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
    public boolean createAuction(User seller, String item, String category, String picture, int minBid, Date dateEnd)
    {
        Auction createdAuction = new Auction(seller, item, category, dateEnd);
        
        /** Add auction to stage list **/
        if(createdAuction.getStage() == 0)
            waitedAuction.add(createdAuction);
        else if(createdAuction.getStage() == 1)
            openedAuction.add(createdAuction);
        else
            return false;
        
        /** Add auction to hash map of category **/
        ArrayList<Auction> auctionCategoryList = auctionMapCategory.get(Category.findCategory(category));
        if(auctionCategoryList != null)
            auctionCategoryList.add(createdAuction);
        else
            return false;      
               
        /** Add auction to hash map of item **/
        ArrayList<Auction> auctionItemList = auctionMapItem.get(item);
        if(auctionItemList != null)
            auctionItemList.add(createdAuction);
        else /* If don't have a list in hash map, create new one */
        {
            auctionItemList = new ArrayList<Auction>();
            auctionItemList.add(createdAuction);
            auctionMapItem.put(item, auctionItemList);
        }
        
        /** Add auction to hash map of seller **/
        ArrayList<Auction> auctionSellerList = auctionMapSeller.get(seller);
        if(auctionSellerList != null)
            auctionSellerList.add(createdAuction);
        else /* If don't have a list in hash map, create new one */
        {
            auctionSellerList = new ArrayList<Auction>();
            auctionSellerList.add(createdAuction);
            auctionMapSeller.put(seller, auctionSellerList);
        }
        return true;
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
        auctionMapCategory.get(Category.findCategory(category));
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