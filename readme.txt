### Online Auction Program: eBuy ###
  An online auction system: eBuy is a program that lets people buy and sell items via auction.
This application will act as a market including many functions which are authentication, create
the auction, bid the item, sell the item, deposit money, and withdraw money. In addition, a
person who won the auction will automatically deduct the money from an account.
  This program develop through Java language version 8 and execute base on terminal. The user
need to use command that we provide to use the program such as '/help' or '/login', etc.


## Software Requirement ##
    - Terminal with graphic display.
    - Java 8


## Files ##

  # Java file (14 Files)
    1. AuctionProgram.java 
        Facade of auction program. Contain main method in this class.
    2. AuctionFileHandler.java
        Handle user file data and auction file data.
    3. AuctionManager.java
        Manage, control, and keep all auction.
    4. AuctionTrigger.java
        Observe and manage the auction to update stage on time.
    5. Auction.java
        Represent the auction object in auction program. 
        Contain the info of auction item, bidder, and seller.
        In addition manage the bid too.
    6. Bid.java
        Represent the bid object in auction program.
        Contain the bidder, money, and bid date.
    7. Category.java
        Category for item in auction.
    8. DateUtils.java
        Utility method about date in the auction program.
    9. IOUtils.java
        Utility method about input and output in the auction program.
    10. TextFileReader.java
        Read file control. Can open, close, and read line from file.
    11. TextFileWriter.java
        Write file control. Can open, close, and write line to file.
    12. User.java
        Represent the user object in auction program. Contain the user information.
    13. UserInterface.java
        User Interface of online auction program. Base on terminal.
    14. UserManager.java
        Manage, control, and keep all user.

  # Data file (2 Files, 1 Folder)
    1. userData.txt
        Keep user data.
    2. auctionData.txt
        Keep auction data.
    3. imgFolder
        Folder that keeping image of an item.
    if in the directory doesn't have these things, the program will auto generate later.
  *Note* The program will not save until close program successfully.


## How to run? ##

  1) Go to 'src' folder.
    Go to src directory that contains java file, data text file, and picture folder
       
  2) Uses command 'javac *.java'
    Execute from .java to .class

  3) Uses command 'java AuctionProgram'
    Run the eBuy


## How to use? ##

      When start the eBuy, it will display the home page first. In the
    home page will display some of auction from auction list. You can
    go to next page (/next) or previous page (/prev) to see all auction
    along the list.
      Auction list will not update until you search, or go to home page.
    
      From the auction that display in a list, there is a no. on each
    auction. You can specific auction from the list by using no. of
    auction (Use '/auction' and then input no. that want to see). And
    when seeing each auction, you can decide to make a bid but you
    need to login (/login) first. If you don't have any account,
    you can register to the eBuy ('/register').

      You can see your information, balance account, withdraw, deposit,
    history bid/selling through the profile(/profile).

      After seeing the auction list from eBuy, you could want to sell
    something on eBuy. So, you can go to make auction page (/makeauction)
    and input item information, start date, end date, and upload the
    picture.
      Have a good luck with eBuy.


## All command ##

# /help
  See all command.

# /home
  Home page of auction program.

# /next
  Go to next page of auction list.

# /prev
  Go back to previous page of auction list.

# /first
  Go to first page of auction list.

# /search
  Search the auction.

# /auction
  Select the auction to display or bid. Uses auction no. that display to be key

# /register
  Register to be new user.

# /login
  Login to the system.

# /logout
  Logout from the system.

# /profile
  View the profile, account, and history bid/selling

# /makeauction
  Make the auction.

# /aboutus
  See about us.
  
# /exit
  Exit from the program.


## Features (or problems..?) in program ##
  - You can bid on your auction (and can bid repeatly as you want).
  - You can win your auciton.
  - The terminal needs to have a graphic display. Because there are some part to upload and display picture.
If the terminal doesn't have graphic display, it will occur the throw exception X10 (Cause from
don't have a gui from system).

## Creator ##
Created by Kla & Tong group
# Group Member #
  1. Nathaphop Sundarabhogin  60070503420
  2. Natthawat Tungruethaipak 60070503426
