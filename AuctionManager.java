import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Manage, control, and keep all auction.
 * This is test Git 1.
 * This is test Git 2.
 * 
 * Created by Kla & Tong 14 April 2020
 */
public class AuctionManager
{
    /** Keep auctionManager instance, used for singleton */
    private static AuctionManager auctionManager = new AuctionManager();

    /** Auction list that in wait stage */
    private ArrayList<Auction> waitedAuction = new ArrayList<Auction>();

    /** Auction list that in open stage */
    private ArrayList<Auction> openedAuction = new ArrayList<Auction>();

    /** Auction list that in close stage */
    private ArrayList<Auction> closedAuction = new ArrayList<Auction>();

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
     * Get instance of auction manager. Implement the singleton.
     * 
     * @return AuctionManager instance of AuctionManager
     */
    public static AuctionManager getSingletonInstance()
    {
        return auctionManager;
    }
    
    /**
     * Initialize auction list for AuctionManager and hash map.
     * Can set the auctionList (Used for read file).
     * 
     * @param auctionList List of auction that want to set. Can be null.
     */
    public void initialAuction(ArrayList<Auction> auctionList)
    {
        ArrayList<Category> categoryList = Category.getAllCategory();
        for(Category category: categoryList)
            auctionMapCategory.put(category, new ArrayList<Auction>());
        
        if (auctionList == null)
            return;
        for (Auction auction : auctionList)
            storeAuction(auction);
    }

    /**
     * Create the auction and keep in list.
     * 
     * @param item      Item name
     * @param category  Category of item
     * @param picture   Picture file name
     * @param minBid    Minimum money of bid
     * @param dateStart Start date of an auction
     * @param dateEnd   End date of an auction
     * @return True if can create the auction. Otherwise, false
     */
    public boolean createAuction(User seller, String item, String categoryStr,
            String picture, int minBid, Date dateStart, Date dateEnd)
    {
        boolean bCheck = false;
        Auction auction = null;
        Category category = Category.findCategory(categoryStr);
        if(IOUtils.validateAuction(seller, item, category, dateStart, dateEnd, minBid, picture))
        {
            auction = new Auction(seller, item, category, dateStart, dateEnd, minBid, picture);
            storeAuction(auction);
            bCheck = true;
        }
        return bCheck;
    }

    /**
     * Get the auction list from stage of an auction
     * 
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
     * 
     * @param category Category of auction
     * @return Array List of auction from requiring category.
     */
    public ArrayList<Auction> searchAuctionByCategory(String category)
    {
        ArrayList<Auction> auctionList = auctionMapCategory.get(Category.findCategory(category));
        return auctionList;
    }

    /**
     * Get the auction list from item name of an auction
     * 
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
     * 
     * @param sellerName seller name of auction
     * @return Array List of auction from requiring seller name.
     */
    public ArrayList<Auction> searchAuctionBySellerName(String sellerName)
    {
        UserManager userManager = UserManager.getSingletonInstance();
        User seller = userManager.findUserByName(sellerName);
        ArrayList<Auction> auctionLists = auctionMapSeller.get(seller);
        return auctionLists;
    }

    /**
     * Get the list of auction that lower than price.
     * 
     * @param price Min bid money or current highest bid to filter the lower
     * @return List of auction from requiring the lower price.
     */
    public ArrayList<Auction> searchAuctionByLowerPrice(int price)
    {
        ArrayList<Auction> retLists = new ArrayList<Auction>();
        for (Auction auction : openedAuction)
            if (auction.getCurrentBidMoney() <= price)
                retLists.add(auction);
        return retLists;
    }

    /**
     * Update the stage of an auction.
     * 
     * @param auction Auction that want to update
     * @return True if can update. Otherwise, false.
     */
    public boolean updateAuctionStage(Auction auction)
    {
        if (auction == null)
            return false;
        int stage = auction.getStage();
        boolean bCheck = false;
        if (stage == 0)
        {
            if (auction.openAuction())
            {
                waitedAuction.remove(auction);
                openedAuction.add(auction);
                bCheck = true;
            }
        }
        else if (stage == 1)
        {

            if (auction.closeAuction())
            {
                openedAuction.remove(auction);
                closedAuction.add(auction);
                bCheck = true;
            }
        }
        return bCheck;
    }

    /**
     * Get all auction (Used to save file).
     * 
     * @return All auction in a ArrayList
     */
    public ArrayList<Auction> getAllAuction()
    {
        ArrayList<Auction> allAuction = new ArrayList<Auction>();
        allAuction.addAll(waitedAuction);
        allAuction.addAll(closedAuction);
        allAuction.addAll(openedAuction);

        return allAuction;
    }

    

    /**
     * Store the auction to list and hashmap. Also, put the auction to observer.
     * 
     * @param auction Auction that want to keep
     */
    private void storeAuction(Auction auction)
    {
        /* Add auction to stage list */
        if (auction.getStage() == 0)
            waitedAuction.add(auction);
        else if (auction.getStage() == 1)
            openedAuction.add(auction);
        else if (auction.getStage() == 2)
            closedAuction.add(auction);
    
        /* Add auction to hash map of category */
        Category category = Category.findCategory(auction.getCategoryStr());
        ArrayList<Auction> auctionCategoryList = auctionMapCategory.get(category);
        if (auctionCategoryList != null)
            auctionCategoryList.add(auction);
    
        /* Add auction to hash map of item */
        ArrayList<Auction> auctionItemList = auctionMapItem.get(auction.getItem());
        if (auctionItemList != null)
            auctionItemList.add(auction);
        else /* If don't have a list in hash map, create new one */
        {
            auctionItemList = new ArrayList<Auction>();
            auctionItemList.add(auction);
            auctionMapItem.put(auction.getItem(), auctionItemList);
        }
    
        /* Add auction to hash map of seller */
        ArrayList<Auction> auctionSellerList = auctionMapSeller.get(auction.getSeller());
        if (auctionSellerList != null)
            auctionSellerList.add(auction);
        else /* If don't have a list in hash map, create new one */
        {
            auctionSellerList = new ArrayList<Auction>();
            auctionSellerList.add(auction);
            auctionMapSeller.put(auction.getSeller(), auctionSellerList);
        }
    
        /* Add auction to AuctionTrigger */
        if (auction.getStage() < 2)
            AuctionTrigger.getSingleInstance().addAuction(auction);
    }
}