import java.util.ArrayList;
import java.util.Date;

/**
 * Observe and manage the auction to update stage on time.
 * 
 * Created by Kla & Tong 14 April 2020
 */
public class AuctionTrigger extends Thread
{
    /** AuctionTrigger instance. Implement the singleton. */
    private static AuctionTrigger auctionTrigger = new AuctionTrigger();

    /** Auction list that AuctionTrigger observe and update the stage of auction. */
    private ArrayList<Auction> managedAuction = new ArrayList<Auction>();

    /** Used to make infinity loop in thread and can stop the loop from interrupt. */
    private boolean bLoop = true;

    /**
     * Private constructor. Implement the singleton
     */
    private AuctionTrigger()
    {

    }

    /**
     * Get instance of AuctionTrigger.
     * Implement the singleton
     * @return Instance of AuctionTrigger
     */
    public static AuctionTrigger getSingleInstance()
    {
        return auctionTrigger;
    }

    /**
     * Add auction to managed list to observe and update the stage.
     * 
     * @param auction Auction that want to observe.
     * @return Return true if can add to Arraylist for checking. Otherwise, false.
     */
    public boolean addAuction(Auction auction)
    {
        boolean bCheck = false;
        synchronized (managedAuction) /* Synchronized with loop in thread */
        {
            if (auction != null)
            {
                if (managedAuction.add(auction))
                    bCheck = true;
            }
        }
        return bCheck;
    }

    /**
     * Implement run method of thread. Observe, manage, and update all auction.
     */
    public void run()
    {
        AuctionManager auctionManager = AuctionManager.getSingletonInstance();
        while (bLoop)
        {
            /* Synchronized the data when add to list */
            synchronized (managedAuction)
            {
                for (int i = 0; i < managedAuction.size(); i++)
                {
                    Auction auction = managedAuction.get(i);
                    int stage = auction.getStage();

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

         /* Sleep thread for a while to let another thread can add data from synchronized */
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.out.println("Can't sleep thread");
            }
        }
    }

    /**
     * Interrupt method to stop the loop of thread
     */
    public void interrupt()
    {
        /* Stop loop */
        bLoop = false;
    }
}
