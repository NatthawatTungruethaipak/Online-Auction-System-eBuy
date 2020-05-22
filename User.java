import java.util.ArrayList;
import java.util.Date;

/**
 * Represent the user object in auction program. Contain the user information.
 * 
 * Created by Kla & Tong 14 April 2020
 */
public class User
{
    /** User's username **/
    private String username;

    /** User's password **/
    private String password;

    /** User's name **/
    private String name;

    /** User's birth **/
    private Date birth;

    /** User's address **/
    private String address;

    /** User's email **/
    private String email;

    /** User's balance **/
    private int balance = 0;

    /** User's bidList **/
    private ArrayList<Auction> bidList = new ArrayList<Auction>();

    /** User's sellingList **/
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
     * Get balance of user
     * 
     * @return User's balance
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
     * @return
     */
    public boolean editProfile(String password, String name, Date birth,
            String address, String email)
    {
        /** Validate **/
        if (IOUtils.validatePassword(password) == false)
            return false;
        if (IOUtils.validateEmail(email) == false)
            return false;
        if (IOUtils.isNullStr(name) == true)
            return false;
        if (IOUtils.isNullStr(address) == true)
            return false;
        if (birth == null || DateUtils.isAfterCurrentDateTime(birth))
            return false;

        /** Set to user **/
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
     * Add auction that user bid
     * 
     * @param auction The auction that user bid
     * @return true if can add auction that user bid to list. Otherwise, false.
     */
    public boolean addBid(Auction auction)
    {
        boolean bCheck = false;
        if(auction != null )
        {
            if (bidList.contains(auction))
                bCheck = true;
            else if (bidList.add(auction))
                bCheck = true;
        }
        return bCheck;
    }

    /**
     * Check password for the login
     * 
     * @param password
     * @return True if password match with user password
     */
    public boolean checkPassword(String password)
    {
        if (this.password.equals(password))
            return true;
        else
            return false;
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
            return false;
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
     * @return true if can add money. Otherwise, false.
     */
    public boolean addMoney(int money)
    {
        this.balance = this.balance + money;
        return true;
    }
}