import java.util.ArrayList;
import java.util.Date;

/**
 * Observer of auction. Observe an auction to update the auction stage.
 * 
 * Created by Kla & Tong 14 April 2020
 */
public class TimeManager extends Thread
{
    private static ArrayList<Auction> managedAuction = new ArrayList<Auction>();

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
        while (true)
        {
            for (Auction auction : managedAuction)
            {
                int stage = auction.getStage();
                Date currentDate = DateUtils.getCurrentDateTime();
                /* For Testing */
                /* currentDate = new Date(2020, 1, 1); */
                /* Thread.sleep(30000); /* 30000 = 30 Secs */
                if (stage == 0)
                {
                    Date startDate = auction.getDateStart();
                    if (startDate.after(currentDate))
                    {
                        auctionManager.updateAuctionStage(auction);
                    }
                }
                else if (stage == 1)
                {
                    Date endDate = auction.getDateEnd();
                    if (endDate.after(currentDate))
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

}
