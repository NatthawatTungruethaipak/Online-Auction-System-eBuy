import java.util.ArrayList;
import java.util.Date;

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
        User userLogin = AuctionProgram.getSingletonInstance().getLogin();
        clearScreen();
        System.out.println("=========================================================");
        System.out.println("===================== Auction Program ===================");
        System.out.println("=========================================================");
        if(userLogin != null)
            System.out.println("Welcome \"" + userLogin.getName() +"\" to the auction program");
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
        System.out.println("==================== See All Command ====================");
        System.out.println("=========================================================");
        System.out.println("/help - See all command.");
        System.out.println("/home - Home page of auction program.");
        System.out.println("/next - Go to next page of auction list.");
        System.out.println("/prev - Go back to previous page of auction list.");
        System.out.println("/first - Go to first page of auction list.");
        System.out.println("/search - Search the auction.");
        System.out.println("/auction - Select the auction to display or bid.");
        System.out.println("/register - Register to be new user.");
        System.out.println("/login - Login to the system.");
        System.out.println("/logout - Logout from the system.");
        System.out.println("/profile - View the profile info.");
        System.out.println("/makeauction - Make the auction.");
        System.out.println("/aboutus - See about us.");
        System.out.println("/exit - Exit from the program.");
        System.out.println("\nThe auction program will use auction no. to be key");
        System.out.println("\nThe auction list that display will not update\n until search or go to home page again");        
    }

    public void resetAuctionDisplay(ArrayList<Auction> auctionList)
    {
        if(auctionList == null)
            auctionDisplay = AuctionProgram.getSingletonInstance().searchAuction(1, null, 0);
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
        System.out.println("Select type that want to search");
        System.out.println("1 - Auction in opened stage");
        System.out.println("2 - Auction in closed stage");
        System.out.println("3 - Item");
        System.out.println("4 - Category");
        System.out.println("5 - Seller");
        System.out.println("6 - Auction that has price lower");
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
            keyInt = IOUtils.getInteger("Input lower price that want to find", 0);
        auctionDisplay = AuctionProgram.getSingletonInstance().searchAuction(type, keyStr, keyInt);
    }

    public void displaySelectAuction()
    {
        int node = IOUtils.getInteger("Select auction no.: ", 1, auctionDisplay.size()) - 1;
        Auction auction = auctionDisplay.get(node);
        displayAuction(auction, true);
        System.out.println("=========================================================");
        if(IOUtils.getConfirm("Do you want to make a bid?"))
            displayMakeBid(auction, auction.getStartBidPrice());
        IOUtils.getString("Press enter to continue..");
        clearScreen();
        displayAuctionList();
            
    }
    
    private void displayAuction(Auction auction, boolean bFull)
    {
        System.out.println("Item: " + auction.getItem());
        System.out.println("Starting bid price: " + auction.getStartBidPrice() +
                "("+ auction.getNumberOfBid() +" bid)");
        Date dateEnd = auction.getDateEnd();
        if(auction.getStage() == 2)
            System.out.println("Closed auction");
        else
        {
            int[] diff = IOUtils.diffCurrentDateTime(auction.getDateEnd());
            if(diff[0] != 0)
                System.out.println(diff[0]+" Days "+diff[1]+" Hours "+diff[2]+" Minutes");
            else if(diff[0] == 0)
                System.out.println(diff[1]+" Hours "+diff[2]+" Minutes "+diff[3]+" Seconds");
            else if(diff[1] == 0)
                System.out.println(diff[2] + " Minutes " + diff[3] + " Seconds");
        }
        /** See full detail of auction **/
        if (bFull)
        {
            Bid winBid = auction.getWinner();
            if (auction.getStage() == 2)
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
    
    private void displayMakeBid(Auction auction, int minPrice)
    {
        System.out.println("Minimum price to bid: " + minPrice+1);
        IOUtils.getInteger("Price that want to bid: ");
        System.out.println("=========================================================");
        
    }
    
    public void displayRegister()
    {
        // TODO Auto-generated method stub

    }

    public void displayLogin()
    {
        // TODO Auto-generated method stub

    }

    public void displayProfile()
    {
        // TODO Auto-generated method stub

    }

    public void displayEditProfile()
    {
        // TODO Auto-generated method stub

    }

    public void displayMakeAuction()
    {
        // TODO Auto-generated method stub

    }

    public void displayEnding()
    {
        // TODO Auto-generated method stub

    }

    private void clearScreen()
    {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    public void displayGetHelp()
    {
        // TODO Auto-generated method stub

    }

    public int displayGetAuctionNode()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public void displayLogout()
    {
        // TODO Auto-generated method stub

    }

    public void displayAboutUs()
    {
        // TODO Auto-generated method stub
        
    }

}
