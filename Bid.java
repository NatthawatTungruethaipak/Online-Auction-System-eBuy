import java.util.Date;

/**
 * Represent the bid object in auction program.
 * Contain the bidder, money, and bid date.
 * 
 * Created by Kla & Tong
 * 14 April 2020
 */
public class Bid
{
    /** Bidder of the bid */
    User bidder;
    
    /** Amount of money that bid **/
    int money;
    
    /** Date that bid **/
    Date dateBid;
    
    /** 
     * Constructor of bid for a new bid
     * @param user
     * @param money
     */
    public Bid(User user, int money)
    {
        this.bidder = user;
        this.money = money;
        this.dateBid = IOUtils.getCurrentDateTime();
    }
    
    public User getBidder()
    {
        return this.bidder;
    }
    
    public Date getDateTime()
    {
        return this.dateBid;
    }
    
    public int getMoney()
    {
        return this.money;
    }
    
    public int compareTo(Bid bid)
    {
        return this.money - bid.getMoney();
    }

}