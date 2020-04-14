import java.util.Date;

/**
 * Represent the auction object in auction program.
 * Contain the auction item, bidder, and seller.
 * 
 * Created by Kla & Tong
 * 14 April 2020
 */
public class Auction
{
	/** Item name **/
	private String item;
	
	/** Category of item **/
	private Category category;
	
	/** Picture url of item */
	private String picture;
	
	/** Seller of an auction **/
	private User seller;
	
	/** The Bid that won the auction **/
	private Bid winner;
	
	/** Start date to open an auction **/
	private Date dateStart;
	
	/** End date to close an auction **/
	private Date dateEnd;
	
	/** Stage of an auction (wait:0, open:1, closed:2) **/
	private int stage;
	
	/** Minimum bid of auction **/
	private int minBid = 0;
	
	
	
	
	
	
}