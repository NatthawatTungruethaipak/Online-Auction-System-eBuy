import java.util.ArrayList;

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
        System.out.println("/auction - Select the auction to see or bid.");
        System.out.println("/register - Register to be new user.");
        System.out.println("/login - Login to the system.");
        System.out.println("/logout - Logout from the system.");
        System.out.println("/profile - View the profile info.");
        System.out.println("/makeauction - Make the auction.");
        System.out.println("/aboutus - See about us.");
        System.out.println("/exit - Exit from the program.");
        
    }

    public void resetAuctionDisplay(ArrayList<Auction> auctionList)
    {
        if(auctionList == null)
            auctionDisplay = AuctionProgram.getSingletonInstance().sear;
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
                    System.out.println("\tItem: " + auction.getItem());
                    System.out.println("\tBy: " + auction.getSeller().getName());
                    System.out.println("Minimum Bid Money: " + auction.getMinBidMoney());
                    System.out.println("Current Highest Bid: " + auction.getMaxBid());
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
        // TODO Auto-generated method stub

    }

    public void displayAuction()
    {
        // TODO Auto-generated method stub

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

    public void displayMakeBid()
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
