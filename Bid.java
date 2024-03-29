import java.util.Date;

/**
 * Represent the bid object in auction program. Contain the bidder, money, and
 * bid date.
 * 
 * Created by Kla & Tong 14 April 2020
 */
public class Bid  implements Comparable<Bid>
{
    /** Bidder of the bid */
    private User bidder;

    /** Amount of money that bid */
    private int money;

    /** Date that make the bid */
    private Date dateBid;

    /**
     * Constructor of bid for a new bid
     * 
     * @param user  User that bid
     * @param money Amount of money to bid
     */
    public Bid(User user, int money)
    {
        this.bidder = user;
        this.money = money;
        this.dateBid = new Date();
    }

    /**
     * Get the user who make a bid
     * 
     * @return Owner of bid.
     */
    public User getBidder()
    {
        return this.bidder;
    }

    /**
     * Get number of money that bid
     * 
     * @return Bid price
     */
    public int getMoney()
    {
        return this.money;
    }

    /**
     * Get the date that create the bid
     * 
     * @return Created date of bid
     */
    public Date getDateTime()
    {
        return this.dateBid;
    }

    /**
     * Set date of bid. (Used in read file)
     * 
     * @param date Date that want to set
     * @return Return true if can set date. Otherwise, false.
     */
    public boolean setDate(Date date)
    {
        boolean bCheck = false;
        if (date != null)
        {
            this.dateBid = date;
            bCheck = true;
        }
        return bCheck;
    }

    /**
     * CompareTo function for using in bidSet.
     * 
     * @param bid Bid that want to compare
     * @return Positive value, if money of this instance is more than.
     *         Negative value, if money of this instance is less than.
     *         Zero, if both of them are equal.
     */
    public int compareTo(Bid bid)
    {
        return this.money - bid.getMoney();
    }

}