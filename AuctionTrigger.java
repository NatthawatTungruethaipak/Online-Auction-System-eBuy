import java.util.ArrayList;
import java.util.Date;

/**
 * Observe and manage the auction to update stage on time.
 * 
 * Created by Kla & Tong 14 April 2020
 */
public class AuctionTrigger extends Thread
{
    private static ArrayList<Auction> managedAuction = new ArrayList<Auction>();
    
    private static AuctionTrigger auctionTrigger = new AuctionTrigger();
    
    private boolean bLoop = true; 
    
    private AuctionTrigger()
    {
    }
    
    public static AuctionTrigger getSingleInstance()
    {
        return auctionTrigger;
    }

    /**
     * Add auction to managed list to observe and update stage.
     * 
     * @param auction Auction that want to observe.
     * @return Return true if can add to arraylist for checking. Otherwise, false.
     */
    public static boolean addAuction(Auction auction)
    {
        boolean bCheck = false;
        if (auction != null)
        {
            if (managedAuction.add(auction))
                bCheck = true;
        }
        return bCheck;
    }

    /**
     * Implement run method of thread. Observe all managed and update its stage.
     */
    public void run()
    {
        AuctionManager auctionManager = AuctionManager.getSingletonInstance();
        while (bLoop)
        {
            for (Auction auction : managedAuction)
            {
                int stage = auction.getStage();
                Date currentDate = DateUtils.getCurrentDateTime();
                /**************** For Testing ****************/
//                 currentDate = DateUtils.strToDate("1-1-1998"); 
//                 try
//                {
//                    Thread.sleep(30000);/* 30000 = 30 Secs */
//                }
//                catch (InterruptedException e)
//                {
//                    System.out.println("Thread error problem");
//                } 
                /********************************************/ 
                 
                if (stage == 0)
                {
                    Date startDate = auction.getDateStart();
                    if (startDate.before(currentDate))
                        auctionManager.updateAuctionStage(auction);
                }
                else if (stage == 1)
                {
                    Date endDate = auction.getDateEnd();
                    if (endDate.before(currentDate))
                    {
                        auctionManager.updateAuctionStage(auction);
                        managedAuction.remove(auction);
                    }
                }
                else
                {
                    managedAuction.remove(auction);
                }
            }
        }
    }
    
    public void interrupt()
    {
        /* Stop loop */
        bLoop = false;
    }
}
