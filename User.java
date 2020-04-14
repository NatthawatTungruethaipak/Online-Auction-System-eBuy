import java.util.*;
public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private Date birth;
    private String address;
    private String email;
    private int balance;
    private ArrayList<Auction> bidList;
    private ArrayList<Auction> sellingList;
    
    public User(String username, String password) {
        if(validateUsername(username))
        this.username = username;
        this.password = password;
    }
    
    public String getName(){
        return name;
    }
    
    public String getSurname(){
        return surname;
    }
    
    public Date getBirth(){
        return birth;
    }

    public String getAddress(){
        return address;
    }
    
    public String getEmail(){
        return email;
    }

    public ArrayList<Auction> getSellingList(){
        return sellingList;
    }

    public ArrayList<Auction> getBidList(){
        return bidList;
    }

    public boolean setPassword(String newPassword){
        if(IOUtils.validatePassword(newPassword))
        {
            this.password = newPassword;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setName(String name){
        this.name = name;
        return true;
    }

    public boolean setSurname(String surname){
        this.surname = surname;
        return true;
    }

    public boolean setBirth(Date birth){
        if(validateDateTime(birth)){
            this.birth = birth;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setAddress(String address){
        this.address = address;
        return true;
    }

    public boolean setEmail(String email){
        if(validateEmail(email)){
            this.email = email;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean addSelling(Auction auction){
        this.sellingList.add(auction);
    }
}