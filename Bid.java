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
    
    /**
     * Get the user who make a bid
     * @return User that make a bid
     */
    public User getBidder()
    {
        return this.bidder;
    }
    
    /**
     * Get date that this bid was created
     * @return
     */
    public Date getDateTime()
    {
        return this.dateBid;
    }
    
    /**
     * Get money that bid
     * @return
     */
    public int getMoney()
    {
        return this.money;
    }
    
    /**
     * CompareTo function for using in bidSet.
     * @param bid Bid that want to compare
     * @return Return positive value, if money of this instance is more than.
     *         Return negative value, if money of this instance is less than.
     *         Return 0, if both of them are equal.
     */
    public int compareTo(Bid bid)
    {
        return this.money - bid.getMoney();
    }

}