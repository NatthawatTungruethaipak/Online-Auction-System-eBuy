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
	private int balance = 0;

	/**
	 * User's bidList
	 */
	private ArrayList<Auction> bidList = new ArrayList<Auction>();

	/**
	 * User's sellingList
	 */
	private ArrayList<Auction> sellingList = new ArrayList<Auction>();

	/**
	 * Constructor for user class that set the username and password of the user
	 * 
	 * @param username
	 * @param password
	 */
	public User(String username, String password, String name, String surname, Date birth, String address, String email)
	{
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.birth = birth;
		this.address = address;
		this.email = email;
	}

	/**
	 * Get username of user
	 * 
	 * @return User's username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Get password of user
	 * 
	 * @return User's password
	 */
	public String getPassword()
	{
		return password;
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
	 * Editing the profile. Set new password, name, surname, birth, address, email	
	 * @param password
	 * @param name
	 * @param surname
	 * @param birth
	 * @param address
	 * @param email
	 * @return
	 */
	public boolean editProfile(String password, String name, String surname, Date birth,
			String address, String email)
	{
		if (IOUtils.validatePassword(password) == false)
			return false;
		if(IOUtils.validateEmail(email) == false)
			return false;
		if (IOUtils.isNullStr(name) == true)
			return false;
		if (IOUtils.isNullStr(surname) == true)
			return false;
		if(IOUtils.isNullStr(address) == true)
			return false;
		Date currentDate = IOUtils.getCurrentDateTime();
		if(birth == null || birth.after(currentDate))
			return false;
		
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.birth = birth;
		this.address = address;
		this.email = email;
		return true;
	}


	/**
	 * Add selling thing of user
	 * 
	 * @param auction The auction that user sell
	 * @return true
	 */
	public boolean addSelling(Auction auction)
	{
		if(auction == null)
			return false;
		if (this.sellingList.add(auction))
			return true;
		else
			return false;
	}

	/**
	 * Add buying thing that user bid
	 * 
	 * @param auction The auction that use bid
	 * @return true
	 */
	public boolean addBid(Auction auction)
	{
		if(this.bidList.add(auction))
			return true;	
		else
			return false;
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
		}
		else
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
		}
		else
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