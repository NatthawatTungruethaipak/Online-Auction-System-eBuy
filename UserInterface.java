import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserInterface
{

    private static UserInterface userInterface = new UserInterface();
    
    private ArrayList<Auction> auctionDisplay = new ArrayList<Auction>();
    
    private int page = 0;
    
    /** Max number of auction in one page **/
    private final int maxPage = 5; 
 
    private UserInterface()
    {
    }

    public static UserInterface getSingletonInstance()
    {
        return userInterface;
    }

    public void displayHomePage()
    {
        clearScreen();
        System.out.println("=========================================================");
        System.out.println("=                         eBuy                          =");
        System.out.println("=========================================================");
        if(AuctionProgram.isLogin())
        {
            User user = AuctionProgram.getLogin();
            System.out.println("Welcome \"" + user.getName() +"\" to the auction program");
        }
        else
        {
            System.out.println("Welcome to the auction program.");
            System.out.println("Uses '/login' to login to the system.");
        }
        
        resetAuctionDisplay(null);
        displayAuctionList();
        try
        {
            Thread.sleep(300000);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void displayHelp()
    {
        clearScreen();
        System.out.println("=========================================================");
        System.out.println("=                    See All Command                    =");
        System.out.println("=========================================================");
        System.out.println("/help - See all command.\n");
        System.out.println("/home - Home page of auction program.\n");
        System.out.println("/next - Go to next page of auction list.\n");
        System.out.println("/prev - Go back to previous page of auction list.\n");
        System.out.println("/first - Go to first page of auction list.\n");
        System.out.println("/search - Search the auction.\n");
        System.out.println("/auction - Select the auction to display or bid.");
        System.out.println("The auction program use auction no. to be key\n");
        System.out.println("/register - Register to be new user.\n");
        System.out.println("/login - Login to the system.\n");
        System.out.println("/logout - Logout from the system.\n");
        System.out.println("/profile - View the profile info.\n");
        System.out.println("/makeauction - Make the auction.\n");
        System.out.println("/aboutus - See about us.\n");
        System.out.println("/exit - Exit from the program.\n");
        
        System.out.println("\nThe auction list that display will not update\n until search or go to home page again");        
    }

    private void resetAuctionDisplay(ArrayList<Auction> auctionList)
    {
        if(auctionList == null)
            auctionDisplay = AuctionProgram.searchAuction(1, null, 0);
        else
            auctionDisplay = auctionList;
        page = 0;

    }

    public void displayAuctionList()
    {
        System.out.println("=========================================================");
        if(auctionDisplay == null || auctionDisplay.size() == 0)
            System.out.println("\n\t\t- Do not found any auction -\n");
        else
        {
            
            for(int i = 0+(page*maxPage); i < maxPage; i++)
            {
                Auction auction = auctionDisplay.get(i);
                if(auction != null)
                {
                    System.out.println("\tNo. " + i+1);
                    displayAuction(auction, false);
                    System.out.println();
                }
            }
        }
        System.out.println("======================== Page "+ (page+1) +" =========================");

    }

    public void displayNextPage()
    {
        
        if(auctionDisplay == null || auctionDisplay.size() == 0)
        {
            System.out.println("=========================================================");
            System.out.println("\n\t\t- Do not found any auction -\n");
            System.out.println("=========================================================");
        }
        else if((maxPage * (page+1)) > auctionDisplay.size())
        {
            System.out.println("=========================================================");
            System.out.println("\n\t\t- This is the last page -\n");
            System.out.println("=========================================================");
        }
        else
        {
            page++;
            displayAuctionList();
        }
        
    }

    public void displayPrevPage()
    {
        if(auctionDisplay == null || auctionDisplay.size() == 0)
        {
            System.out.println("=========================================================");
            System.out.println("\n\t\t- Do not found any auction -\n");
            System.out.println("=========================================================");
        }
        else if(page-1 < 0)
        {
            System.out.println("=========================================================");
            System.out.println("\n\t\t- This is the first page -\n");
            System.out.println("=========================================================");
        }
        else
        {
            page--;
            displayAuctionList();
        }

    }

    public void displayFirstPage()
    {
        page = 0;
        displayAuctionList();
    }

    public void searchAuction()
    {
        int type = 0;
        int keyInt = 0;
        String keyStr = null;
        
        System.out.println("=========================================================");
        System.out.println("=                     Search Auction                    =");
        System.out.println("=========================================================");
        System.out.println("Select type that want to search");
        System.out.println("1 - Auction in opened stage");
        System.out.println("2 - Auction in closed stage");
        System.out.println("3 - Item");
        System.out.println("4 - Category");
        System.out.println("5 - Seller");
        System.out.println("6 - Lower start price to bid");
        System.out.println("=========================================================");
        
        type = IOUtils.getInteger("Select type number: ", 1, 6);
        if (type == 3)
            keyStr = IOUtils.getString("Search item name: ");
        else if (type == 4)
        {
            System.out.println("Select category that want to search")
            ArrayList<String> categoryList = Category.getAllCategoryStr();
            for(int i = 0; i < categoryList.size(); i++)
                System.out.println((i+1) +  " - " + categoryList.get(i));
            int node = IOUtils.getInteger("Select category number: ",1 , categoryList.size());
            keyStr = categoryList.get(node-1);
        }
        else if (type == 5)
            keyStr = IOUtils.getString("Search seller name: ");
        else if (type == 6)
            keyInt = IOUtils.getInteger("Start price (Baht): ", 0);
        resetAuctionDisplay(AuctionProgram.searchAuction(type, keyStr, keyInt));
        displayAuctionList();
    }

    public void displaySelectAuction()
    {
        int node = IOUtils.getInteger("Select auction no.: ", 1, auctionDisplay.size()) - 1;
        Auction auction = auctionDisplay.get(node);
        displayAuction(auction, true);
        System.out.println("=========================================================");
        if(AuctionProgram.isLogin())
        {
            if(IOUtils.getConfirm("Do you want to make a bid?"))
                displayMakeBid(auction);
        }
        else
        {
            System.out.println("Please login to system to bid to the auction.");
        }
        refresh();
    }
    
    private void displayAuction(Auction auction, boolean bFull)
    {
        System.out.println("Item: " + auction.getItem());
        if(auction.isBid) /** Check that have anyone bid or not, display different text **/
            System.out.print("Current bid: " + auction.getCurrentBidMoney() + " Baht");
        else
            System.out.print("Starting bid price: " + auction.getMinBid() + " Baht");        
        System.out.println(" ("+ auction.getNumberOfBid() +" bid)");
        if(auction.getStage() == 2)  /** Print time left before close auction **/
            System.out.println("Closed auction");
        else
        {
            int[] diff = IOUtils.diffCurrentDateTime(auction.getDateEnd());
            if(diff[0] != 0) /** Displaying time **/
                System.out.println(diff[0]+" Days "+diff[1]+" Hours "+diff[2]+" Minutes");
            else if(diff[0] == 0)
                System.out.println(diff[1]+" Hours "+diff[2]+" Minutes "+diff[3]+" Seconds");
            else if(diff[1] == 0)
                System.out.println(diff[2] + " Minutes " + diff[3] + " Seconds");
        }
        
        /** Print full detail of auction **/
        if (bFull)
        {
            Bid winBid = auction.getWinner();
            if (auction.getStage() == 2) /** Print winner **/
                if(winBid == null)
                    System.out.println("Winner: Don't have winner");
                else
                {
                    User bidder = winBid.getBidder();
                    System.out.println("Winner: " + bidder.getName() + " " + bidder.getSurname());
                }
            System.out.println("Category: " + auction.getCategoryStr());
            Date startDate = auction.getDateStart();
            System.out.println("Started: " + IOUtils.dateToStr(startDate));
            Date endDate = auction.getDateEnd();
            System.out.println("Ended: " + IOUtils.dateToStr(endDate));
            User seller = auction.getSeller();
            System.out.println("Seller: " + seller.getUsername() + " " + seller.getSurname());
        }
    }
    
    private void displayMakeBid(Auction auction)
    {
        int minPrice;
        if(auction.isBid())
        {
            minPrice = auction.getMaxBidMoney();
            System.out.println("Current bid: " + minPrice  + " Baht");
        }
        else
        {
            minPrice = auction.getMinBid() - 1;
            System.out.println("Starting bid: " + auction.getMinBid()  + " Baht");
        }
        
        int money = 0;
        boolean bLoop = true;
        do {
            money = IOUtils.getInteger("Price that want to bid: ");
            if(money < minPrice)
                System.out.println("You need to input price more than " + minPrice + " Baht");
            else
                bLoop = false;
            } while(bLoop);
        if(AuctionProgram.makeBid(auction, money))
        {
            System.out.println("=========================================================\n");
            System.out.println("                   - Make bid success -                   ");
            System.out.println("\n=========================================================");
        }
        else
        {
            System.out.println("=========================================================\n");
            System.out.println("     - Cannot make bid, please check auction stage -     ");
            System.out.println("\n=========================================================");
        }
    }
    
    public void displayRegister()
    {
        /** This part is just some prototype **/
        UserManager userManager = UserManager.getSingletonInstance();
        String username = null;
        String password = IOUtils.getString("Password: ");
        boolean bLoop = true;
        do
        {
            username = IOUtils.getString("Username: ");
            if(AuctionProgram.searchUser(username,false) != null)
                System.out.println("Username has already been taken");
            else
                bLoop = false;
        }while(bLoop);
        
        bLoop = true;
        do
        {
            password = IOUtils.getString("Password: ");
            if(IOUtils.validatePassword(password) == false)
                System.out.println("Password is not correct");
            else
                bLoop = false;
        }while(bLoop);
        
        boolean bCheck = AuctionProgram.login(username,password);
        if(bCheck)
        {
            System.out.println("=========================================================\n");
            System.out.println("                    - Login success -                    ");
            System.out.println("\n=========================================================");
        }
        else
        {
            
        }
        /*****************************************/
    }

    public void displayLogin()
    {
        String username = IOUtils.getString("Username: ");
        String password = IOUtils.getString("Password: ");

        if(AuctionProgram.login(username,password))
        {
            System.out.println("=========================================================\n");
            System.out.println("                    - Login success -                    ");
            System.out.println("\n=========================================================");
        }
        else
        {
            System.out.println("=========================================================\n");
            System.out.println("         - Username or password is not correct -         ");
            System.out.println("\n=========================================================");
        }
        refresh();        
    }
    
    
    private void displayProfile(User user)
    {
        clearScreen();
        System.out.println("=========================================================");
        System.out.println("=                        Profile                        =");
        System.out.println("=========================================================");
        System.out.println("Username: "+user.getUsername());
        System.out.println("Name: "+user.getName());
        System.out.println("Surname: "+user.getSurname());
        System.out.println("Birth: " + IOUtils.dateToStr(user.getBirth()));
        System.out.println("Address: "+user.getAddress());
        System.out.println("Email: "+user.getEmail());
        System.out.println("=========================================================");
    }
    
    
    public void displayManageProfile()
    {
        if(AuctionProgram.isLogin())
        {
            System.out.println("=========================================================\n");
            System.out.println("                   - Please login first -                ");
            System.out.println("\n=========================================================");
            refresh();
            return;
        }
        
        User user = AuctionProgram.getLogin();
        clearScreen();
        displayProfile(user);
        int menu = 0;
        do {
            System.out.println("Select menu");
            System.out.println("0 - Go back to home page");
            System.out.println("1 - Edit profile");
            System.out.println("2 - See balance");
            System.out.println("3 - Deposit");
            System.out.println("4 - Withdraw");
            System.out.println("5 - Bids/Selling History");
            menu = IOUtils.getInteger("Select menu: ", 1, 5);
            switch(menu)
            {
                case 1:
                    displayEditProfile(user);
                    break;
                case 2:
                    displayBalance(user);
                    break;
                case 3:
                    displayDeposit(user);
                    break;
                case 4:
                    displayWithdraw(user);
                    break;
                case 3:
                    displayHistoryBidSelling(user);
                    break;
            }
        } while(menu != 4);
        refresh();
    }

    private void displayEditProfile(User user)
    {
        

    }

    private void displayBalance(User user)
    {
        int balance = user.getBalance();
        System.out.println("=========================================================");
        System.out.println("\tBalance: " + balance);
        System.out.println("=========================================================");
        
    }
    
    private void displayDeposit(User user)
    {
        int balance = user.getBalance();
        displayBalance(user);
        int money = IOUtils.getInteger("Withdraw: ", 0, balance);
        if(AuctionProgram.deposit(money))
            System.out.println("Money has been deposit to the accuont.");
        else
            System.out.println("Problem occur, cannot deposit money to the account.");
    }
    
    private void displayWithdraw(User user)
    {
        int balance = user.getBalance();
        displayBalance(user);
        int money = IOUtils.getInteger("Withdraw: ", 0);
        if(AuctionProgram.withdraw(money))
            System.out.println("Money has been withdrawn from the accuont.");
        else
            System.out.println("Problem occur, cannot withdraw money from the account.");
    }
    
    private void displayHistoryBidSelling(User user)
    {
        System.out.println("=========================================================");
        System.out.println("=                     Bid History                       =");
        System.out.println("=========================================================");
        ArrayList<Auction> bidList = user.getBidList();
        System.out.println("- Bidding");
        for(Auction auction: bidList)
            if(auction.getStage() == 1)
                displayAuction(auction, false);

        System.out.println("- Offers");
        for(Auction auction: bidList)
            if(auction.getWinner().getBidder() == user)
                displayAuction(auction, false);
        
        System.out.println("- Didn't Win");
        for(Auction auction: bidList)
            if(auction.getWinner().getBidder() != user)
                displayAuction(auction, false);

        System.out.println("=========================================================");
        System.out.println("=                 Selling History                       =");
        System.out.println("=========================================================");
        ArrayList<Auction> sellList = user.getAuctionList();
        System.out.println("- Waiting");
        for(Auction auction: sellList)
            if(auction.getStage() == 0)
                displayAuction(auction, false);
        
        System.out.println("- Active");
        for(Auction auction: sellList)
            if(auction.getStage() == 1)
                displayAuction(auction, false);

        System.out.println("- Sold");
        for(Auction auction: sellList)
            if(auction.getStage() == 2 && auction.getWinner() != null)
                displayAuction(auction, false);
        
        System.out.println("- Unsold");
        for(Auction auction: sellList)
            if(auction.getStage() == 2 && auction.getWinner() == null)
                displayAuction(auction, false);
        
    }

    public void displayMakeAuction()
    {
        if(AuctionProgram.isLogin())
        {
            System.out.println("=========================================================\n");
            System.out.println("                   - Please login first -                ");
            System.out.println("\n=========================================================");
            refresh();
            return;
        }

    }

    public boolean displayEnding()
    {
        if(IOUtils.getConfirm("Are you sure you want to exit?"))
        {
            System.out.println("=========================================================\n");
            System.out.println("                        - Good bye -                     ");
            System.out.println("\n=========================================================");
            return false; /** Return false to get out from loop **/
        }
        else
        {
            displayHomePage();
            return true; /** Return true to still loop **/
        }

    }

    public void displayGetHelp()
    {
        System.out.println("=========================================================\n");
        System.out.println("- Command is not correct. Uses '/help' to see all command -");
        System.out.println("\n=========================================================");

    }

    public void displayLogout()
    {
        if(AuctionProgram.isLogin())
        {
            System.out.println("=========================================================\n");
            System.out.println("                   - Please login first -                ");
            System.out.println("\n=========================================================");
            refresh();
            return;
        }
        else if(AuctionProgram.logout())
        {
            System.out.println("=========================================================\n");
            System.out.println("                     - Logout success -                  ");
            System.out.println("\n=========================================================");
            refresh();
            return;
        }
        else
        {
            System.out.println("=========================================================\n");
            System.out.println("       - Logout occurs problem, please try again -       ");
            System.out.println("\n=========================================================");
            refresh();
            return;
        }

    }

    public void displayAboutUs()
    {
        clearScreen();
        System.out.println("=========================================================");
        System.out.println("=                        About us                       =");
        System.out.println("=========================================================");
        System.out.println("    An online auction system: eBuy is a program that lets");
        System.out.println("people buy and sell items via auction. This application");
        System.out.println("will act as a market including many functions which are");
        System.out.println("authentication, create the auction, bid the item, sell the");
        System.out.println(" item, deposit money, and withdraw money.");
        System.out.println("    In addition, a person who won the auction will automatically");
        System.out.println("deduct the money from an account.\n");
        System.out.println("===== This program was created by Kla & Tong group =====");
        System.out.println("Group Member: ");
        System.out.println("1) Nathaphop Sundarabhogin  60070503420");
        System.out.println("2) Natthawat Tungruethaipak 60070503426");
        System.out.println("=========================================================");
        refresh();
    }
    
    public static void displayImage(String imageDir)
    {
        JFrame f = new JFrame("image");
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File(imageDir));
        }
        catch (IOException e)
        {
            System.out.println("Cannot load the image");
            return;
        }
        int width = img.getWidth();
        int height = img.getHeight();
        
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBounds(0, 0, width, height);
        imageLabel.setVisible(true);
        
        f.add(imageLabel);
        f.setBounds(0, 0, width, height);
        f.setVisible(true);
    }
    
    private void refresh()
    {
        IOUtils.getString("Press enter to continue..");
        clearScreen();
        displayHomePage();
    }
    
    private void clearScreen()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

}
