import java.util.*;

/**
 * Represent the user object in auction program. Contain the user detail.
 * 
 * Created by Kla & Tong 14 April 2020
 */
public class User
{
	/**
	 * User's username
	 */

	private String username;

	/**
	 * User's password
	 */
	private String password;

	/**
	 * User's name
	 */
	private String name;

	/**
	 * User's surname
	 */
	private String surname;

	/**
	 * User's birth
	 */
	private Date birth;

	/**
	 * User's address
	 */
	private String address;

	/**
	 * User's email
	 */
	private String email;

	/**
	 * User's balance
	 */
	private int balance;

	/**
	 * User's bidList
	 */
	private ArrayList<Auction> bidList;

	/**
	 * User's sellingList
	 */
	private ArrayList<Auction> sellingList;

	/**
	 * Constructor for user class that set the username and password of the user
	 * 
	 * @param username
	 * @param password
	 */
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	/**
	 * Get name of user
	 * 
	 * @return User's name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Get surname of user
	 * 
	 * @return User's surname
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * Get birth of user
	 * 
	 * @return User's birth
	 */
	public Date getBirth()
	{
		return birth;
	}

	/**
	 * Get address of user
	 * 
	 * @return User's address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * Get email of user
	 * 
	 * @return User's email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Get sellingList of user
	 * 
	 * @return User's sellingList
	 */
	public ArrayList<Auction> getSellingList()
	{
		return sellingList;
	}

	/**
	 * Get bidList of user
	 * 
	 * @return User's bidList
	 */
	public ArrayList<Auction> getBidList()
	{
		return bidList;
	}

	/**
	 * Set new password to for user if new password is correct format
	 * 
	 * @param newPassword will be set as new password
	 * @return boolean of setting new password if the new password can set will
	 *         return true
	 */
	public boolean setPassword(String newPassword)
	{
		if (IOUtils.validatePassword(newPassword))
		{
			this.password = newPassword;
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * Set name of user
	 * 
	 * @param name that will be name of user
	 * @return true
	 */
	public boolean setName(String name)
	{
		this.name = name;
		return true;
	}

	/**
	 * Set new surname of user
	 * 
	 * @param surname that will be surname of user
	 * @return true
	 */
	public boolean setSurname(String surname)
	{
		this.surname = surname;
		return true;
	}

	/**
	 * Set birth of user if birth is in correct format
	 * 
	 * @param birth that will be birth of user
	 * @return true if birth is valid
	 */
	public boolean setBirth(Date birth)
	{
		if (IOUtils.validateDateTime(birth))
		{
			this.birth = birth;
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * Set the address of the user
	 * 
	 * @param address that will be address of user
	 * @return true
	 */
	public boolean setAddress(String address)
	{
		this.address = address;
		return true;
	}

	/**
	 * Set email of user if email is in correct format
	 * 
	 * @param email that will be email of user
	 * @return if email is valid
	 */
	public boolean setEmail(String email)
	{
		if (IOUtils.validateEmail(email))
		{
			this.email = email;
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * Add selling thing of user
	 * 
	 * @param auction The auction that user sell
	 * @return true
	 */
	public boolean addSelling(Auction auction)
	{
		this.sellingList.add(auction);
		return true;
	}

	/**
	 * Add buying thing that user bid
	 * 
	 * @param auction The auction that use bid
	 * @return true
	 */
	public boolean addBid(Auction auction)
	{
		this.bidList.add(auction);
		return true;
	}

	/**
	 * Check password for the login
	 * 
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String password)
	{
		if (this.password.equals(password))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * Get balance of user
	 * 
	 * @return User's balance
	 */
	public int getBalance()
	{
		return this.balance;
	}

	/**
	 * Deduct money from balance
	 * 
	 * @param money amount of money to deduct from the balance
	 * @return if deduct the money from the balance and the balance has more than or
	 *         equal to zero will return true
	 */
	public boolean deductMoney(int money)
	{
		if ((this.balance - money) < 0)
		{
			return false;
		} else
		{
			this.balance = this.balance - money;
			return true;
		}
	}

	/**
	 * Add money to balance
	 * 
	 * @param money amount of money to add in balance
	 * @return true
	 */
	public boolean addMoney(int money)
	{
		this.balance = this.balance + money;
		return true;
	}
}