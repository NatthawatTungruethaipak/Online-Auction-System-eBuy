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
        Auction newAuction = new Auction(seller, item, category, dateStart, dateEnd);
        
        /* Set minimum bid of the auction */
        if(newAuction.setMinBid(minBid) == false)
        	return false;
        
        /* Set picture */
        if(newAuction.setPicture(picture) == false)
        	return false;
        
        /* Check date start and date end */
        /* Note to myself in the future, change from create auction at first to check all variable and then create *.
        
        /** Add auction to stage list **/
        if(newAuction.getStage() == 0)
            waitedAuction.add(newAuction);
        else if(newAuction.getStage() == 1)
            openedAuction.add(newAuction);
        else
            return false;
        
        /** Add auction to hash map of category **/
        ArrayList<Auction> auctionCategoryList = auctionMapCategory.get(Category.findCategory(category));
        if(auctionCategoryList != null)
            auctionCategoryList.add(newAuction);
        else
            return false;      
               
        /** Add auction to hash map of item **/
        ArrayList<Auction> auctionItemList = auctionMapItem.get(item);
        if(auctionItemList != null)
            auctionItemList.add(newAuction);
        else /* If don't have a list in hash map, create new one */
        {
            auctionItemList = new ArrayList<Auction>();
            auctionItemList.add(newAuction);
            auctionMapItem.put(item, auctionItemList);
        }
        
        /** Add auction to hash map of seller **/
        ArrayList<Auction> auctionSellerList = auctionMapSeller.get(seller);
        if(auctionSellerList != null)
            auctionSellerList.add(newAuction);
        else /* If don't have a list in hash map, create new one */
        {
            auctionSellerList = new ArrayList<Auction>();
            auctionSellerList.add(newAuction);
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
    
    /**
     * Get the auction list from category of an auction
     * @param category Category of auction
     * @return Array List of auction from requiring category.
     */
    public ArrayList<Auction> searchAuctionByCat(String category)
    {
        ArrayList <Auction>auctionList = auctionMapCategory.get(Category.findCategory(category));
        return auctionList;
    }
    
    /**
     * Get the auction list from item name of an auction
     * @param item Item name of auction
     * @return Array List of auction from requiring item name.
     */
    public ArrayList<Auction> searchAuctionByItem(String item)
    {
    	ArrayList<Auction> auctionLists = auctionMapItem.get(item);
        return auctionLists;
    }
    
    /**
     * Get the auction list from seller name of an auction
     * @param sellerName seller name of auction
     * @return Array List of auction from requiring seller name.
     */
    public ArrayList<Auction> searchAuctionBySeller(String sellerName)
    {
    	UserManager userManager = UserManager.getSingletonInstance();
    	User seller = userManager.findUserByName(sellerName);
    	ArrayList<Auction> auctionLists = auctionMapItem.get(seller);
        return auctionLists;
    }
    
    /**
     * Get the auction list from lower price to bid of an auction
     * @param money Min bid money or current highest bid to filter the lower
     * @return Array List of auction from requiring the lower price.
     */
    public ArrayList <Auction> searchAuctionByLowerPrice(int money)
    {
        ArrayList<Auction> auctionLists = new ArrayList<Auction>();
    	for (Auction auction: openedAuction)
    	{            
    	    Bid maxBid = auction.getMaxBid();
            if(maxBid == null)
            {
                if(auction.getMinBidMoney() < money)
                    auctionLists.add(auction);
            }
            else
            {
                if(maxBid.getMoney() < money)
                    auctionLists.add(auction);
            }
        }
        return auctionLists;
    }

    /**
     * Update the stage of an auction.
     * @param auction Auction that want to update
     * @return True if can update. Otherwise, false.
     */
    public boolean updateAuctionStage(Auction auction)
    {
        if(auction == null)
            return false;
        int stage = auction.getStage();
        boolean bCheck = false;
        if(stage == 0)
        {
            if(auction.openAuction() == true)
            {
                waitedAuction.remove(auction);
                openedAuction.add(auction);
                bCheck = true;
            }
        }
        else if(stage == 1)
        {
            
            if(auction.closeAuction() == true)
            {
                openedAuction.remove(auction);
                closedAuction.add(auction);
                bCheck = true;
            }
        }
        return bCheck;
    }
}