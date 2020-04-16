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

	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public String getName()
	{
		return name;
	}

	public String getSurname()
	{
		return surname;
	}

	public Date getBirth()
	{
		return birth;
	}

	public String getAddress()
	{
		return address;
	}

	public String getEmail()
	{
		return email;
	}

	public ArrayList<Auction> getSellingList()
	{
		return sellingList;
	}

	public ArrayList<Auction> getBidList()
	{
		return bidList;
	}

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

	public boolean setName(String name)
	{
		this.name = name;
		return true;
	}

	public boolean setSurname(String surname)
	{
		this.surname = surname;
		return true;
	}

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

	public boolean setAddress(String address)
	{
		this.address = address;
		return true;
	}

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

	public boolean addSelling(Auction auction)
	{
		this.sellingList.add(auction);
		return true;
	}

	public boolean addBid(Auction auction)
	{
		this.bidList.add(auction);
		return true;
	}

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

	public int getBalance()
	{
		return this.balance;
	}

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

	public boolean addMoney(int money)
	{
		this.balance = this.balance + money;
		return true;
	}
}