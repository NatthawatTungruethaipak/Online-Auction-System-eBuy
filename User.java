import java.util.ArrayList;
import java.util.Date;

/**
 * Represent the user object in auction program. Contain the user information.
 * 
 * Created by Kla & Tong 14 April 2020
 */
public class User
{
    /** Username of user */
    private String username;

    /** Password of user */
    private String password;

    /** Name of user */
    private String name;

    /** Birth date of user */
    private Date birth;

    /** Address of user */
    private String address;

    /** Email of user */
    private String email;

    /** Balance account of user */
    private int balance = 0;

    /** Auction list that user bid */
    private ArrayList<Auction> bidList = new ArrayList<Auction>();

    /** Auction list that user sell */
    private ArrayList<Auction> sellingList = new ArrayList<Auction>();

    /**
     * Constructor for user class
     * 
     * @param username Username of user
     * @param password Password of user
     * @param name     Name of user
     * @param birth    Birth of user
     * @param address  Address of user
     * @param email    mail of user
     */
    public User(String username, String password, String name, Date birth,
            String address, String email)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.address = address;
        this.email = email;
    }

    /**
     * Get username of user
     * 
     * @return Username of user.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Get password of user
     * 
     * @return Password of user
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Get name of user
     * 
     * @return Name of user
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get birth date of user
     * 
     * @return Birth date of user.
     */
    public Date getBirth()
    {
        return birth;
    }

    /**
     * Get address of user
     * 
     * @return Address of user
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Get email of user
     * 
     * @return Email of user.
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Get sell auction list of user
     * 
     * @return Auction list that user sell
     */
    public ArrayList<Auction> getSellingList()
    {
        return sellingList;
    }

    /**
     * Get auction list that user bid
     * 
     * @return Auction list that user bid
     */
    public ArrayList<Auction> getBidList()
    {
        return bidList;
    }

    /**
     * Get balance money in account of user
     * 
     * @return Money in account
     */
    public int getBalance()
    {
        return this.balance;
    }

    /**
     * Editing the profile. Set new password, name, birth, address, email
     * 
     * @param password New password that want to set
     * @param name     New name that want to set
     * @param birth    New birth that want to set
     * @param address  New address that want to set
     * @param email    New email that want to set
     * @return Return true.
     */
    public boolean editProfile(String password, String name, Date birth,
            String address, String email)
    {
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.address = address;
        this.email = email;
        return true;
    }

    /**
     * Add auction that user selling
     * 
     * @param auction The auction that user sell
     * @return true if can add the auction to list. Otherwise, false.
     */
    public boolean addSelling(Auction auction)
    {
        boolean bCheck = false;
        if(auction != null )
        {
            if (sellingList.contains(auction))
                bCheck = true;
            else if (sellingList.add(auction))
                bCheck = true;
        }
        return bCheck;
    }

    /**
     * Add auction that user bid to the bidList of user.
     * 
     * @param auction The auction that user bid
     * @return true if can add auction that user bid to list. Otherwise, false.
     */
    public boolean addBid(Auction auction)
    {
        boolean bCheck = false;
        if(auction != null)
        {
            if (bidList.contains(auction))
                bCheck = true;
            else if (bidList.add(auction))
                bCheck = true;
        }
        return bCheck;
    }

    /**
     * Compare other password with password of user that it's same or not. 
     * 
     * @param password Password that want to check.
     * @return True if password and user password are the same.
     */
    public boolean checkPassword(String password)
    {
        if (this.password.equals(password))
            return true;
        else
            return false;
    }

    /**
     * Deduct money from balance account
     * 
     * @param money Amount of money to deduct from the balance
     * @return if deduct the money from the balance and the balance has more than or
     *         equal to zero will return true
     */
    public boolean deductMoney(int money)
    {
        if ((this.balance - money) < 0)        
            return false;
        else
        {
            this.balance = this.balance - money;
            return true;
        }
    }

    /**
     * Add money to balance account
     * 
     * @param money amount of money to add in balance.
     * @return true if can add money to the account. Otherwise, false.
     */
    public boolean addMoney(int money)
    {
        this.balance = this.balance + money;
        return true;
    }
}