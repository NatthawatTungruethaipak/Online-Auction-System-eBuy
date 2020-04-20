import java.util.ArrayList;

/**
 * Manage, control, and keep all auction.
 * 
 * Created by Kla & Tong
 * 14 April 2020
 */
public class AuctionManager
{
    private static AuctionManager auctionManager = new AuctionManager();
    
    ArrayList <Auction> waitedAuction = new ArrayList<Auction>();
    
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
    
    public void initialAuction(ArrayList<Auction> auctionList)
    {
        
    }
    
    public boolean createAuction(String item, String category, String picture, String category, int minBid, String dateStart, String dateEnd)
    {
        
    }
    
    public boolean createAuction(String item, String category, String picture, String category, int minBid, String dateEnd)
    {
        
    }
    
    public ArrayList<Auction> searchAuctionByStage(int stage)
    {
        
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