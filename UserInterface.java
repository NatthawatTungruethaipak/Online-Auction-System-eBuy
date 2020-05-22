import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * User Interface of online auction program. Base on terminal.
 * 
 * Created by Kla & Tong 18 May 2020
 */
public class UserInterface
{

    /** Auction list to display **/
    private static ArrayList<Auction> auctionDisplay = new ArrayList<Auction>();

    /** Current page **/
    private static int page = 0;

    /** Max number of auction in one page **/
    private static final int maxPage = 5;

    /**
     * Clear screen and go to home page.
     * 
     * @param bHome Indicate to go to home page or not
     */
    private static void refresh(boolean bHome)
    {
        System.out.println("Press enter to continue..");
        IOUtils.getEnter();
        clearScreen();
        if (bHome)
            displayHomePage();
    }

    /**
     * Clear screen. Reference:
     * https://stackoverflow.com/questions/2979383/java-clear-the-console
     */
    private static void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Reset the auction list.
     * 
     * @param auctionList Set auction list to display and reset. if null, set auction
     *                    list to opened auction.
     */
    private static void resetAuctionDisplay(ArrayList<Auction> auctionList)
    {
        if (auctionList == null)
            auctionDisplay = AuctionProgram.searchAuction(1, null, 0);
        else
            auctionDisplay = auctionList;
        page = 0;

    }

    /**
     * Display auction info.
     * 
     * @param auction Auction that want to display
     * @param bFull   To select that see full information or not
     */
    private static void displayAuction(Auction auction, boolean bFull)
    {
        System.out.println("Item: " + auction.getItem());
        /** Check that have anyone bid or not, display different text **/
        if (auction.isBid())
            System.out
                    .print("Current bid: " + auction.getCurrentBidMoney() + " Baht");
        else
            System.out.print("Starting bid price: " + auction.getMinBid() + " Baht");
        System.out.println(" (" + auction.getNumberOfBid() + " bid)");
        if (auction.getStage() == 0) /** Auction is waiting to open **/
            System.out.println("Waiting to open");
        else if (auction.getStage() == 2) /** Auction is close **/
            System.out.println("Closed auction");
        else /** Displaying time **/
        {
            int[] diff = DateUtils.diffCurrentDateTime(auction.getDateEnd());
            if (diff[0] != 0)
                System.out.println("Remaining: " + diff[0] + " Days " + diff[1]
                        + " Hours " + diff[2] + " Minutes");
            else if (diff[0] == 0 && diff[1] != 0)
                System.out.println("Remaining: " + diff[1] + " Hours " + diff[2]
                        + " Minutes " + diff[3] + " Seconds");
            else
                System.out.println("Remaining: " + diff[2] + " Minutes " + diff[3]
                        + " Seconds");
        }
        Date startDate = auction.getDateStart();
        System.out.print("Started: " + DateUtils.dateTimeToStr(startDate));
        Date endDate = auction.getDateEnd();
        System.out.println("\tEnded: " + DateUtils.dateTimeToStr(endDate));
        
        /** Print full detail of auction **/
        if (bFull)
        {
            Bid winBid = auction.getWinner();
            if (auction.getStage() == 2) /** Print winner **/
            {
                if (winBid == null)
                    System.out.println("Winner: Don't have winner");
                else
                {
                    User bidder = winBid.getBidder();
                    System.out.println("Winner: " + bidder.getName());
                    System.out.println("Win price: " + winBid.getMoney());
                    System.out.println("Bid date: " + DateUtils.dateTimeToStr(winBid.getDateTime()));
                }
            }
            System.out.println("Category: " + auction.getCategoryStr());
            User seller = auction.getSeller();
            System.out.println("Seller: " + seller.getName());
        }
    }

    /**
     * Display make bid page to the auction and send the information to make bid
     * 
     * @param auction Auction that want to make bid
     */
    private static void displayMakeBid(Auction auction)
    {
        int minPrice;
        if (auction.isBid())
        {
            minPrice = auction.getCurrentBidMoney();
            System.out.println("Current bid: " + minPrice + " Baht");
        }
        else
        {
            minPrice = auction.getMinBid() - 1;
            System.out.println("Starting bid: " + auction.getMinBid() + " Baht");
        }

        int money = 0;
        boolean bLoop = true;
        do
        {
            money = IOUtils.getInteger("Price that want to bid (Baht): ", 0);
            if (money < minPrice)
                System.out.println(
                        "You need to input price more than " + minPrice + " Baht");
            else
                bLoop = false;
        } while (bLoop);
        if (AuctionProgram.makeBid(auction, money))
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                   - Make bid success -                   ");
            System.out.println(
                    "\n=========================================================");
        }
        else
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                     - Make bid fail -                   ");
            System.out.println(
                    "\n=========================================================");
        }
    }

    /**
     * Display info of user (hide password).
     */
    private static void displayProfile(User user)
    {
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Name: " + user.getName());
        System.out.println("Birth: " + DateUtils.dateToStr(user.getBirth()));
        System.out.println("Address: " + user.getAddress());
        System.out.println("Email: " + user.getEmail());
    }

    /**
     * Display info of user. (Used after register/edit profile)
     * 
     * @param username Username of user
     * @param password password of user
     * @param name     name of user
     * @param birth    birth date
     * @param address  address of user
     * @param email    email of user
     */
    private static void displayProfile(String username, String password, String name,
            Date birth, String address, String email)
    {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Name: " + name);
        System.out.println("Birth: " + DateUtils.dateToStr(birth));
        System.out.println("Address: " + address);
        System.out.println("Email: " + email);
    }

    /**
     * Display edit page of user and send data to edit.
     * 
     * @param user User that want to edit
     */
    private static void displayEditProfile(User user)
    {

        if (!AuctionProgram.isLogin())
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                   - Please login first -                ");
            System.out.println(
                    "\n=========================================================");
            refresh(true);
            return;
        }

        clearScreen();
        System.out.println(
                "=========================================================");
        System.out.println(
                "=                      Edit profile                     =");
        System.out.println(
                "=========================================================");
        displayProfile(user);
        System.out.println(
                "=========================================================\n");
        String password = user.getPassword();
        String name = user.getName();
        Date birth = user.getBirth();
        String address = user.getAddress();
        String email = user.getEmail();
        /** Ask user want to edit each data */
        if (IOUtils.getConfirm("Do you want to edit password (yes/no)?: "))
            password = IOUtils.getPassword("Edit Password: ");
        if (IOUtils.getConfirm("Do you want to edit name (yes/no)?: "))
            name = IOUtils.getString("Edit Name: ");
        if (IOUtils.getConfirm("Do you want to edit birth date (yes/no)?: "))
            birth = IOUtils.getDate("Edit Birth (dd-mm-yyyy): ", null, false);
        if (IOUtils.getConfirm("Do you want to edit address (yes/no)?: "))
            address = IOUtils.getString("Edit Address: ");
        if (IOUtils.getConfirm("Do you want to edit email (yes/no)?: "))
            email = IOUtils.getEmail("Edit Email: ");
        System.out.println(
                "=========================================================");
        displayProfile(user.getUsername(), password, name, birth, address, email);
        System.out.println(
                "=========================================================\n");
        if (IOUtils.getConfirm("Confirm to edit (yes/no): "))
        {
            if (AuctionProgram.editProfile(password, name, birth, address, email))
            {
                System.out.println(
                        "=========================================================\n");
                System.out.println(
                        "                      - Edit success -                   ");
                System.out.println(
                        "\n=========================================================");
            }
            else
            {
                System.out.println(
                        "=========================================================\n");
                System.out.println(
                        "                       - Edit failure -                   ");
                System.out.println(
                        "\n=========================================================");
            }
        }
        refresh(true);
    }

    /**
     * Display balance of user
     * 
     * @param user User that want to display balance
     */
    private static void displayBalance(User user)
    {
        int balance = user.getBalance();
        clearScreen();
        System.out.println(
                "=========================================================");
        System.out.println("\tBalance: " + balance + " Baht");
        System.out.println(
                "=========================================================");
    }

    /**
     * Display deposit page and send data to deposit
     * 
     * @param user User that want to deposit
     */
    private static void displayDeposit(User user)
    {
        clearScreen();
        displayBalance(user);
        int money = IOUtils.getInteger("Deposit (Baht): ", 0);
        boolean ret = AuctionProgram.deposit(money);
        clearScreen();
        displayBalance(user);
        if (ret)
            System.out.println("Money has been deposit to the account.");
        else
            System.out
                    .println("Problem occur, cannot deposit money to the account.");
        refresh(false);
    }

    /**
     * Display withdraw page and send data to withdraw
     * 
     * @param user User that want to deposit
     */
    private static void displayWithdraw(User user)
    {
        clearScreen();
        int balance = user.getBalance();
        displayBalance(user);
        int money = IOUtils.getInteger("Withdraw (Baht): ", 0, balance);
        boolean ret = AuctionProgram.withdraw(money);
        clearScreen();
        displayBalance(user);
        if (ret)
            System.out.println("Money has been withdrawn from the account.");
        else
            System.out.println(
                    "Problem occur, cannot withdraw money from the account.");
        refresh(false);
    }

    /**
     * Display history bid/selling of user
     * 
     * @param user User that want to see history
     */
    private static void displayHistoryBidSelling(User user)
    {
        System.out.println(
                "=========================================================");
        System.out.println(
                "=                     Bid History                       =");
        System.out.println(
                "=========================================================\n");
        ArrayList<Auction> bidList = user.getBidList();
        System.out.println(
                "---------------------------------------------------------");
        System.out.println("\t\tBidding");
        System.out.println(
                "---------------------------------------------------------");

        for (Auction auction : bidList)
            if (auction.getStage() == 1)
            {
                displayAuction(auction, false);
                System.out.println();
            }

        System.out.println();
        System.out.println(
                "---------------------------------------------------------");
        System.out.println("\t\tOffers");
        System.out.println(
                "---------------------------------------------------------");
        for (Auction auction : bidList)
            if (auction.getWinner().getBidder() == user)
            {
                displayAuction(auction, false);
                System.out.println();
            }

        System.out.println();
        System.out.println(
                "---------------------------------------------------------");
        System.out.println("\t\tDidn't Win");
        System.out.println(
                "---------------------------------------------------------");
        for (Auction auction : bidList)
            if (auction.getWinner().getBidder() != user)
            {
                displayAuction(auction, false);
                System.out.println();
            }

        System.out.println(
                "\n=========================================================");
        System.out.println(
                "=                 Selling History                       =");
        System.out.println(
                "=========================================================\n");
        ArrayList<Auction> sellList = user.getSellingList();
        System.out.println(
                "---------------------------------------------------------");
        System.out.println("\t\tWaiting");
        System.out.println(
                "---------------------------------------------------------");
        for (Auction auction : sellList)
            if (auction.getStage() == 0)
            {
                displayAuction(auction, false);
                System.out.println();
            }

        System.out.println();
        System.out.println(
                "---------------------------------------------------------");

        System.out.println("\t\tActive");
        System.out.println(
                "---------------------------------------------------------");
        for (Auction auction : sellList)
            if (auction.getStage() == 1)
            {
                displayAuction(auction, false);
                System.out.println();
            }

        System.out.println();
        System.out.println(
                "---------------------------------------------------------");
        System.out.println("\t\tSold");
        System.out.println(
                "---------------------------------------------------------");
        for (Auction auction : sellList)
            if (auction.getStage() == 2 && auction.getWinner() != null)
            {
                displayAuction(auction, false);
                System.out.println();
            }

        System.out.println();
        System.out.println(
                "---------------------------------------------------------");
        System.out.println("\t\tUnsold");
        System.out.println(
                "---------------------------------------------------------");
        for (Auction auction : sellList)
            if (auction.getStage() == 2 && auction.getWinner() == null)
            {
                displayAuction(auction, false);
                System.out.println();
            }
        System.out.println(
                "\n=========================================================");
        refresh(false);
    }

    /**
     * Home page of auction program.
     */
    public static void displayHomePage()
    {
        clearScreen();
        System.out.println(
                "=========================================================");
        System.out.println(
                "=             Online Auction Program: eBuy              =");
        System.out.println(
                "=========================================================");
        System.out.println("");
        if (AuctionProgram.isLogin())
        {
            User user = AuctionProgram.getLogin();
            System.out.println(
                    "Welcome \"" + user.getName() + "\" to the auction program");
        }
        else
        {
            System.out.println("Welcome to the auction program.");
            System.out.println("Uses '/login' to login to the system.");
        }
        System.out.println("");
        resetAuctionDisplay(null);
        displayAuctionList();
    }

    /**
     * Displaying help command
     */
    public static void displayHelp()
    {

        clearScreen();
        System.out.println(
                "=========================================================");
        System.out.println(
                "=                       How to use                      =");
        System.out.println(
                "=========================================================");
        System.out.println("");
        /* First paragraph */
        System.out.print("  When start the eBuy, it will display the home page ");
        System.out.print("first. In the\nhome page will display some of auction ");
        System.out.print("from auction list. You can\ngo to next page (/next) ");
        System.out.print("or previous page (/prev) to see all auction\nalong the ");
        System.out.print("list.\nAuction list will not update until you search, ");
        System.out.print("or go to home page.\n\n");

        /* Second paragraph */
        System.out.print("  From the auction that display in a list, there is ");
        System.out.print("a no. on each\nauction. You can specific auction from ");
        System.out.print("the list by using no. of\nauction (Use '/auction' and ");
        System.out.print("then input no. that want to see). And\nwhen seeing each ");
        System.out.print("auction, you can decide to make a bid but you\nneed to ");
        System.out.print("login (/login) first. If you don't have any account,\n");
        System.out.print("you can register to the eBuy ('/register').\n\n");

        /* Third paragraph */
        System.out
                .print("  You can see your information, balance account, withdraw,");
        System.out.print(" deposit,\nhistory bid/selling through the profile ");
        System.out.print("(/profile).\n\n");

        /* Fourth paragraph */
        System.out
                .print("  After seeing the auction list from eBuy, you could want ");
        System.out.print(
                "to sell\nsomething on eBuy. So, you can go to make auction ");
        System.out.print(
                "page (/makeauction)\nand input item information, start date");
        System.out.print(", end date, and upload the\npicture.\n\n");

        /* Ending */
        System.out.println(
                "               Have a good luck with eBuy.               ");
        System.out.println("");
        System.out.println(
                "=========================================================");
        System.out.println(
                "=                       All Command                     =");
        System.out.println(
                "=========================================================");
        System.out.println("");
        System.out.println("/help - See all command.");
        System.out.println("/home - Home page of auction program.");
        System.out.println("/next - Go to next page of auction list.");
        System.out.println("/prev - Go back to previous page of auction list.");
        System.out.println("/first - Go to first page of auction list.");
        System.out.println("/search - Search the auction.");
        System.out.println("/auction - Select the auction to display or bid.");
        System.out.println("           Uses auction no. that display to be key");
        System.out.println("/register - Register to be new user.");
        System.out.println("/login - Login to the system.");
        System.out.println("/logout - Logout from the system.");
        System.out.println(
                "/profile - View the profile, account, and history bid/selling.");
        System.out.println("/makeauction - Make the auction.");
        System.out.println("/aboutus - See about us.");
        System.out.println("/exit - Exit from the program.\n");
        System.out.println(
                "=========================================================");

    }

    /**
     * Displaying when a command is wrong.
     */
    public static void displayGetHelp()
    {
        System.out.println(
                "=========================================================\n");
        System.out.println(
                "-     Wrong command. Uses '/help' to see all command    -");
        System.out.println(
                "\n=========================================================");

    }

    /**
     * Let a user confirm to exit or not. Then display ending program.
     * 
     * @return Return false, if user want to exit. Otherwise, true.
     */
    public static boolean displayEnding()
    {
        if (IOUtils.getConfirm("Are you sure you want to exit? (yes/no): "))
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                        - Good bye -                     ");
            System.out.println(
                    "\n=========================================================");
            return false; /** Return false to get out from loop **/
        }
        else
        {
            displayHomePage();
            return true; /** Return true to still loop **/
        }

    }

    /**
     * Display login UI and send username and password to login.
     */
    public static void displayLogin()
    {
        if (AuctionProgram.isLogin())
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "               - Have been login already -               ");
            System.out.println(
                    "\n=========================================================");
            return;
        }
        clearScreen();
        System.out.println(
                "=========================================================");
        System.out.println(
                "=                         Login                         =");
        System.out.println(
                "=========================================================");
        String username = IOUtils.getString("Username: ");
        String password = IOUtils.getString("Password: ");
        if (AuctionProgram.login(username, password))
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                    - Login success -                    ");
            System.out.println(
                    "\n=========================================================");
        }
        else
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "         - Username or password is not correct -         ");
            System.out.println(
                    "\n=========================================================");
        }
        refresh(true);
    }

    /**
     * Display logout
     */
    public static void displayLogout()
    {
        if (!AuctionProgram.isLogin())
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                   - Please login first -                ");
            System.out.println(
                    "\n=========================================================");
            refresh(true);
            return;
        }
        else if (AuctionProgram.logout())
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                     - Logout success -                  ");
            System.out.println(
                    "\n=========================================================");
            refresh(true);
            return;
        }
        else
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "       - Logout occurs problem, please try again -       ");
            System.out.println(
                    "\n=========================================================");
            refresh(true);
            return;
        }

    }

    /**
     * Display about us information.
     */
    public static void displayAboutUs()
    {
        clearScreen();
        System.out.println(
                "=========================================================");
        System.out.println(
                "=                        About us                       =");
        System.out.println(
                "=========================================================");
        System.out.println(
                "\n    An online auction system: eBuy is a program that lets");
        System.out
                .println("people buy and sell items via auction. This application");
        System.out
                .println("will act as a market including many functions which are");
        System.out.println("authentication, create the auction, bid the item, sell");
        System.out.println(
                "the item, deposit money, and withdraw money. In addition,");
        System.out.println("a person who won the auction will automatically");
        System.out.println("deduct the money from an account.\n");
        System.out
                .println("      This program was created by Kla & Tong group      ");
        System.out.println("Group Member: ");
        System.out.println("1) Nathaphop Sundarabhogin  60070503420");
        System.out.println("2) Natthawat Tungruethaipak 60070503426");
        System.out.println(
                "\n=========================================================");
        refresh(true);
    }

    /**
     * Display auction in a list. In one page will display an auction follow number
     * of maxPage.
     */
    public static void displayAuctionList()
    {
        System.out.println(
                "=========================================================\n");
        if (auctionDisplay == null
                || auctionDisplay.size() == 0) /** Don't have any auction **/
            System.out.println(
                    "                 - Do not found any auction -            ");
        else
        {

            for (int i = 0 + (page * maxPage), count = 0; i < auctionDisplay.size()
                    && count < maxPage; i++, count++)
            {
                Auction auction = auctionDisplay.get(i);
                if (auction != null)
                {
                    System.out.println("\tNo. " + i + 1);
                    displayAuction(auction, false);
                    System.out.println();
                }
            }
        }
        System.out.println("\n======================== Page " + (page + 1)
                + " =========================");
    }

    /**
     * Display next page of auction list.
     */
    public static void displayNextPage()
    {

        if (auctionDisplay == null
                || auctionDisplay.size() == 0) /** Don't have any auction **/
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                 - Do not found any auction -            ");
            System.out.println(
                    "\n=========================================================");
        }
        else if ((maxPage * (page + 1)) > auctionDisplay.size())
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                  - This is the last page -              ");
            System.out.println(
                    "\n=========================================================");
        }
        else
        {
            page++;
            displayAuctionList();
        }

    }

    /**
     * Display previous page of auction list
     */
    public static void displayPrevPage()
    {
        if (auctionDisplay == null
                || auctionDisplay.size() == 0) /** Don't have any auction **/
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                 - Do not found any auction -            ");
            System.out.println(
                    "\n=========================================================");
        }
        else if (page - 1 < 0)
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                 - This is the first page -              ");
            System.out.println(
                    "\n=========================================================");
        }
        else
        {
            page--;
            displayAuctionList();
        }

    }

    /** Display the first page of auction list **/
    public static void displayFirstPage()
    {
        page = 0;
        clearScreen();
        displayAuctionList();
    }

    /**
     * Display make auction and send data to make auction
     */
    public static void displayMakeAuction()
    {
        if (!AuctionProgram.isLogin())
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                   - Please login first -                ");
            System.out.println(
                    "\n=========================================================");
            refresh(true);
            return;
        }
        clearScreen();
        System.out.println(
                "=========================================================");
        System.out.println(
                "=                   Make Auction                        =");
        System.out.println(
                "=========================================================");
        String item = IOUtils.getString("Item name: ");
        String category = IOUtils.getCategory("Select category of item");
        int minBid = IOUtils.getInteger("Minimum bid money (Baht): ", 0);
        Date dateStart = IOUtils.getDateTime("Start date (dd-mm-yyyy-hh:mm): ", null,
                true);
        Date dateEnd = IOUtils.getDateTime("End date (dd-mm-yyyy-hh:mm): ",
                dateStart, true);
        String picture = IOUtils.uploadImage();
        System.out.println(
                "=========================================================");
        System.out.println("Item: " + item);
        System.out.println("Category: " + category);
        System.out.println("Minimum bid money: " + minBid);
        System.out.println("Start date: " + DateUtils.dateTimeToStr(dateStart));
        System.out.println("End date: " + DateUtils.dateTimeToStr(dateEnd));
        displayImage("picture");
        System.out.println(
                "=========================================================");
        if (IOUtils.getConfirm("Confirm create auction (yes/no): "))
        {
            if (AuctionProgram.makeAuction(item, category, picture, minBid,
                    dateStart, dateEnd))
            {
                System.out.println(
                        "=========================================================\n");
                System.out.println(
                        "                 - Create auction success -               ");
                System.out.println(
                        "\n=========================================================");
            }
            else
            {
                System.out.println(
                        "=========================================================\n");
                System.out.println(
                        "                - Create auction failure -                 ");
                System.out.println(
                        "\n=========================================================");
            }
        }
        refresh(true);
    }

    /**
     * Search auction UI. Let user to select type and input key.
     **/
    public static void displaySearchAuction()
    {
        int type = 0;
        int keyInt = 0;
        String keyStr = null;
        clearScreen();
        System.out.println(
                "=========================================================");
        System.out.println(
                "=                     Search Auction                    =");
        System.out.println(
                "=========================================================");
        System.out.println("Select type that want to search");
        System.out.println("0 - Go back home page");
        System.out.println("1 - Auction in opened stage");
        System.out.println("2 - Auction in closed stage");
        System.out.println("3 - Item");
        System.out.println("4 - Category");
        System.out.println("5 - Seller");
        System.out.println("6 - Lower start price to bid");
        System.out.println(
                "=========================================================");

        type = IOUtils.getInteger("Select type number: ", 0, 6);
        if (type == 0)
        {
            displayHomePage();
            return;
        }
        else if (type == 3)
            keyStr = IOUtils.getString("Search item name: ");
        else if (type == 4)
            keyStr = IOUtils.getCategory("Select category that want to search");
        else if (type == 5)
            keyStr = IOUtils.getString("Search seller name: ");
        else if (type == 6)
            keyInt = IOUtils.getInteger("Start price (Baht): ", 0);
        resetAuctionDisplay(AuctionProgram.searchAuction(type, keyStr, keyInt));
        clearScreen();
        displayAuctionList();
    }

    /**
     * Display all info of an auction and let user to select the auction that want to
     * see or bid.
     */
    public static void displaySelectAuction()
    {
        if (auctionDisplay == null || auctionDisplay.size() == 0)
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                 - Do not found any auction -            ");
            System.out.println(
                    "\n=========================================================");
            refresh(true);
            return;
        }
        int node = IOUtils.getInteger("Select auction no.: ", 1,
                auctionDisplay.size()) - 1;
        Auction auction = auctionDisplay.get(node);
        System.out.println(
                "=========================================================");
        displayAuction(auction, true);
        displayImage(auction.getPicture());
        System.out.println(
                "=========================================================");
        if (AuctionProgram.isLogin())
        {
            if (IOUtils.getConfirm("Do you want to make a bid? (yes/no): "))
                displayMakeBid(auction);
        }
        else
        {
            System.out.println("Please login to system to bid to the auction.");
        }
        refresh(true);
    }

    /**
     * Display the register page and send the information to register
     */
    public static void displayRegister()
    {
        clearScreen();
        System.out.println(
                "=========================================================");
        System.out.println(
                "=                        Register                       =");
        System.out.println(
                "=========================================================");
        String username = IOUtils.getUsername("Username: ");
        String password = IOUtils.getPassword("Password: ");
        String name = IOUtils.getString("Name: ");
        Date birth = IOUtils.getDate("Birth date (dd-mm-yyyy): ", null, false);
        String address = IOUtils.getString("Address: ");
        String email = IOUtils.getEmail("Email: ");
        System.out.println(
                "=========================================================");
        displayProfile(username, password, name, birth, address, email);
        System.out.println(
                "=========================================================\n");
        if (IOUtils.getConfirm("Confirm register (yes/no): "))
        {
            boolean bCheck = AuctionProgram.register(username, password, name, birth,
                    address, email);
            if (bCheck)
            {
                System.out.println(
                        "=========================================================\n");
                System.out.println(
                        "                   - Register success -                  ");
                System.out.println(
                        "\n=========================================================");
            }
            else
            {
                System.out.println(
                        "=========================================================\n");
                System.out.println(
                        "                   - Register failure -                  ");
                System.out.println(
                        "\n=========================================================");
            }
        }
        refresh(true);
    }

    /**
     * Display and have the mini menu of user to manage
     */
    public static void displayManageProfile()
    {
        if (!AuctionProgram.isLogin())
        {
            System.out.println(
                    "=========================================================\n");
            System.out.println(
                    "                   - Please login first -                ");
            System.out.println(
                    "\n=========================================================");
            refresh(true);
            return;
        }

        int menu = 0;
        do
        {
            clearScreen();
            User user = AuctionProgram.getLogin();
            System.out.println(
                    "=========================================================");
            System.out.println(
                    "=                        Profile                        =");
            System.out.println(
                    "=========================================================");
            displayProfile(user);
            System.out.println(
                    "=========================================================");

            System.out.println("Select menu");
            System.out.println("0 - Go back to home page");
            System.out.println("1 - Edit profile");
            System.out.println("2 - See balance");
            System.out.println("3 - Deposit");
            System.out.println("4 - Withdraw");
            System.out.println("5 - Bids/Selling History");
            menu = IOUtils.getInteger("Select menu: ", 0, 5);
            switch (menu) {
                case 1:
                    /** Edit profile **/
                    displayEditProfile(user);
                    break;
                case 2:
                    /** See balance **/
                    displayBalance(user);
                    refresh(false);
                    break;
                case 3:
                    /** Deposit **/
                    displayDeposit(user);
                    break;
                case 4:
                    /** Withdraw **/
                    displayWithdraw(user);
                    break;
                case 5:
                    /** Display history bid/selling **/
                    displayHistoryBidSelling(user);
                    break;
            }
        } while (menu != 0);
        displayHomePage();
    }

    /**
     * Display image
     * 
     * @param imgFileName Image file name that want to display.
     */
    public static void displayImage(String imgFileName)
    {
        JFrame f = new JFrame("image");
        String imgDir = IOUtils.getImageDir() + imgFileName;
        BufferedImage img = null;
        System.out.print("Loading image to display...  ");
        try
        {
            img = ImageIO.read(new File(imgDir));
        }
        catch (IOException e)
        {
            System.out.println("Fail...");
            System.out.println("Cannot load image to display");
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
        System.out.println("Success!");
        return;
    }

}
