import java.util.ArrayList;
import java.util.Date;

/**
 * Observe and manage the auction to update stage on time.
 * 
 * Created by Kla & Tong 14 April 2020
 */
public class AuctionTrigger extends Thread
{
    private static AuctionTrigger auctionTrigger = new AuctionTrigger();
    
    private ArrayList<Auction> managedAuction = new ArrayList<Auction>();
    
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
    public boolean addAuction(Auction auction)
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
            for (int i = 0; i < managedAuction.size(); i ++)
            {
                Auction auction = managedAuction.get(i);
                int stage = auction.getStage();
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
                    if (DateUtils.isBeforeCurrentDateTime(startDate))
                        auctionManager.updateAuctionStage(auction);
                }
                else if (stage == 1)
                {
                    Date endDate = auction.getDateEnd();
                    if (DateUtils.isBeforeCurrentDateTime(endDate))
                        auctionManager.updateAuctionStage(auction);
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
