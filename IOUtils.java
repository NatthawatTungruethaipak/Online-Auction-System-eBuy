import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * Utility method about input and output in the auction program.
 * 
 * Created by Kla & Tong 18 May 2020
 */
public class IOUtils
{
    /** Directory of image */
    final static String imgDirectory = "imgFolder";

    /** Default image name */
    final static String imgDefault = "default.png";

    /**
     * Initial the directory folder for image.
     */
    public static void initial()
    {
        /* Create image directory */
        File file = new File(getImageDir());
        if (!file.exists())
            file.mkdir();
    }

    /**
     * Check the string is null or not.
     * 
     * @param string String that will be check.
     * @return true if the string is null. Otherwise, false.
     */
    public static boolean isNullStr(String string)
    {
        boolean bCheck = false;
        if (string.trim().equals(""))
            bCheck = true;
        return bCheck;
    }

    /**
     * Validate the username in pattern of 6-30 characters with consist of alphabet,
     * number and underscore. First character can't be number.
     * 
     * Reference from
     * https://www.geeksforgeeks.org/how-to-validate-a-username-using-regular-expressions-in-java/
     * 
     * @param username Username that want to validate
     * @return True if username is in correct pattern. Otherwise, false.
     */
    public static boolean validateUsername(String username)
    {
        if (isNullStr(username))
            return false;
        String usernamePattern = "^[aA-zZ]\\w{5,29}$";
        if (username.matches(usernamePattern))
            return true;
        else
            return false;
    }

    /**
     * Validate the password in pattern of 8-40 characters to consist of at least
     * one number, one lower character, one upper character.
     * 
     * Reference from
     * https://examples.javacodegeeks.com/core-java/util/regex/matcher/validate-password-with-java-regular-expression-example/
     * 
     * @param password Password string that want to validate
     * @return @return True, when password is in correct pattern. Otherwise, false.
     */
    public static boolean validatePassword(String password)
    {
        if (isNullStr(password))
            return false;
        String passwordPattern = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{8,40})";
        if (password.matches(passwordPattern))
            return true;
        else
            return false;
    }

    /**
     * Validate the email in pattern of email
     * 
     * Reference from https://www.tutorialspoint.com/validate-email-address-in-java
     * 
     * @param email Email string that want to validate
     * @return True, when email is in correct pattern. False, when email is not in
     *         correct pattern.
     */
    public static boolean validateEmail(String email)
    {
        if (isNullStr(email))
            return false;
        String emailPattern = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.matches(emailPattern))
            return true;
        else
            return false;
    }

    /**
     * Validate integer. The string parameter must contain only integer in format of string
     * 
     * @param string String that want to validate is integer or not.
     * @return true, when string can be in integer. False, when string is not integer.
     */
    public static boolean validateInteger(String string)
    {
        try
        {
            Integer.parseInt(string);
            return true;
        }
        catch (final NumberFormatException e)
        {
            return false;
        }
    }

    /**
     * Validate user data.
     * Return true if valid.
     * 
     * @param username Username to validate
     * @param password Password user to validate
     * @param name     Name of user to validate
     * @param birth    birth date of user
     * @param address  Address of user
     * @param email    Email of user
     * @return Return false if the data is invalid. Otherwise, true.
     */
    public static boolean validateUser(String username, String password, String name,
            Date birth, String address, String email)
    {
        if (IOUtils.validateUsername(username) == false)
            return false;
        else if (IOUtils.validatePassword(password) == false)
            return false;
        else if (IOUtils.isNullStr(name) == true)
            return false;
        else if (birth == null || DateUtils.isAfterCurrentDateTime(birth))
            return false;
        else if (IOUtils.isNullStr(address) == true)
            return false;
        else if (IOUtils.validateEmail(email) == false)
            return false;
        else
            return true;
    }
    
    /**
     * Validate auction data.
     * Return true if valid.
     * 
     * @param seller    Seller of auction
     * @param item      Item name
     * @param category  Category of item
     * @param picture   Picture of item
     * @param minBid    Minimum bid money
     * @param dateStart Start date of auction
     * @param dateEnd   End date of auction
     * @return  Return false if the data is invalid. Otherwise, true.
     */
    public static boolean validateAuction(User seller, String item, Category category,
            Date dateStart, Date dateEnd, int minBid, String picture)
    {
        if (seller == null)
            return false;
        else if (IOUtils.isNullStr(item))
            return false;
        else if (category == null)
            return false;
        else if (dateStart == null || dateEnd == null)
            return false;
        else if(dateStart.after(dateEnd))
            return false;
        else if (IOUtils.isNullStr(picture))
            return false;
        else if (minBid < 0)
            return false;
        else
            return true;
    }
    
    /**
     * Get enter key from user.
     */
    public static void getEnter()
    {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    /**
     * Loop ask until get text from user.
     * 
     * @param message Message asking user to get input
     * @return Text from user input.
     */
    public static String getText(String message)
    {
        String retString = ""; /* Return text */
        boolean bOk = false;

        while (!bOk)
        {
            System.out.print(message);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (isNullStr(input))
                System.out.println("Input can't be null");
            else
            {
                bOk = true;
                retString = input.trim();
            }
        }
        return retString;
    }

    /**
     * Loop ask until get integer from user. Can set the minimum value.
     * 
     * @param message Message asking to get input
     * @param min     Minimum value that let user input.
     * @return Integer from user input.
     */
    public static int getInteger(String message, int min)
    {
        boolean bOk = false;
        int returnInt = 0;  /* Integer that going to return */
        while (!bOk)
        {
            System.out.print(message);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try
            {
                if (isNullStr(input))
                    System.out.println ("Input can't be null");
                else
                {
                    returnInt = Integer.parseInt(input);
                    if (returnInt >= min)
                        bOk = true;
                    else
                        System.out.println("Input must be number greater than or equal to " + min);
                }

            }
            catch (Exception e)
            {
                System.out.println("Input must be number");
            }

        }
        return returnInt;
    }

    /**
     * Loop ask until get integer from user. Can set the minimum and maximum value.
     * 
     * @param message Message asking to get input.
     * @param min     Minimum value that let user input.
     * @param max     Maximum value that let user input.
     * @return Integer from user input.
     */
    public static int getInteger(String message, int min, int max)
    {

        boolean bOk = false;
        int returnInt = 0;     /* Integer that going to return */
        if(max < min) /* Swap value */
        {
            int temp = max;
            max = min;
            min = temp;
        }
            
        while (!bOk) /* Loop until get corret input format from user */
        {
            System.out.print(message);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try
            {
                if (isNullStr(input))
                    System.out.println("Input can't be null");
                else
                {
                    returnInt = Integer.parseInt(input);
                    if ((returnInt >= min) && (returnInt <= max))
                        bOk = true;
                    else
                        System.out.println("Input must be number between " + min + " and " + max);
                }
            }
            catch (Exception e)
            {
                System.out.println("Input must be number.");
            }

        }
        return returnInt;
    }

    /**
     * Ask command from user.
     * 
     * @param message Message asking to get command.
     * @return command number.
     */
    public static int getCommand(String print)
    {
        final String[] command = { "/home", "/help", "/next", "/prev", "/first",
                "/search", "/auction", "/register", "/login", "/logout", "/profile",
                "/makeauction", "/aboutus", "/exit" };
        int commandValue = 0;
        String input = getText(print);
        /* Loop check the command and get pos of that command before return */
        for (int i = 0; i < command.length; i++)
            if (input.equalsIgnoreCase(command[i]))
                commandValue = i + 1;
        return commandValue;
    }

    /**
     * Loop ask user until user input yes or no
     * 
     * @param message Message asking to get yes/no.
     * @return True if user say yes. False if user say no.
     */
    public static boolean getConfirm(String print)
    {
        boolean bOk = false;
        boolean returnConfirm = false;  /* Boolean to return the answer */
        String input = "";
        System.out.print(print);
        while (!bOk)
        {
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("yes"))
            {
                returnConfirm = true;
                bOk = true;
            }
            else if (input.equalsIgnoreCase("no"))
            {
                returnConfirm = false;
                bOk = true;
            }
            else
                System.out.println("Input must be yes or no.");
        }
        return returnConfirm;
    }

    /**
     * Loop get date until user input correct format of date.
     * 
     * @param message Message asking to get date
     * @param dateCpr Date to compare before or after.
     *                Can be null to set to current date.
     * @param bAfter  if true, date input must after dateCpr.
     *                Otherwise, date input must before dateCpr.
     * @return Date from user input.
     */
    public static Date getDate(String print, Date dateCpr, boolean bAfter)
    {
        boolean bOk = false;
        Date dateInput = null;  /* Date to compare */
        String input = null;    /* Input line from user */
        while (!bOk) /* Loop until date is valid */
        {
            System.out.print(print);
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            if (DateUtils.validateDateStr(input) == false)
                System.out.println("Date format is dd-mm-yyyy. Example: 26-10-1998");
            else
            {
                dateInput = DateUtils.strToDate(input);
                if (dateCpr == null)
                    dateCpr = new Date();
                if (bAfter)
                {
                    if (dateInput.after(dateCpr))
                        bOk = true;
                    else
                        System.out.println("Input date must after " + DateUtils.dateToStr(dateCpr) + " .");
                }
                else
                {
                    if (dateInput.before(dateCpr))
                        bOk = true;
                    else
                        System.out.println("Input date must before " + DateUtils.dateToStr(dateCpr) + " .");
                }
            }
        }
        return dateInput;
    }

    /**
     * Loop get date until user input correct format of date and time.
     * 
     * @param message Message asking to get date
     * @param dateCpr Date to compare before or after
     * @param bAfter  if true, date time input must after dateCpr.
     *                Otherwise, date time input must before dateCpr.
     * @return Date time from user input.
     */
    public static Date getDateTime(String message, Date dateCpr, boolean bAfter)
    {
        boolean bOk = false;
        Date dateInput = null;  /* Date to compare */
        String input = null;    /* Input line from user */
        while (!bOk) /* Loop until date is valid */
        {
            System.out.print(message);
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();

            if (DateUtils.validateDateTimeStr(input) == false)
                System.out.println("Date with time format is dd-mm-yyyy-hh:mm. Example: 26-10-1998-13:39");
            else
            {
                dateInput = DateUtils.strToDateTime(input);
                if (dateCpr == null)
                    dateCpr = new Date();
                if (bAfter)
                {
                    if (dateInput.after(dateCpr))
                        bOk = true;
                    else
                        System.out.println("Input date must after " + DateUtils.dateTimeToStr(dateCpr) + " .");
                }
                else
                {
                    if (dateInput.before(dateCpr))
                        bOk = true;
                    else
                        System.out.println("Input date must before " + DateUtils.dateTimeToStr(dateCpr) + " .");

                }
            }
        }
        return dateInput;
    }

    /**
     * Loop get the username from user and check that username is not duplicate in the system.
     * 
     * @param message Message asking to get input
     * @return Username from user.
     */
    public static String getUsername(String message)
    {
        boolean bOk = false;
        String userInput = "";  /* Text line input */
        UserManager userManager = UserManager.getSingletonInstance();
        while (!bOk)
        {
            userInput = getText(message);
            if (validateUsername(userInput))
            {
                if (userManager.findUserByUsername(userInput) != null)
                    System.out.println("The username is already in use.");
                else
                    bOk = true;
            }
            else
                System.out.println("Username must between 6-30 characters and first " + 
            "character must be alphabet and otherwise can be alphbet, number and underscore.");
        }
        return userInput;
    }

    /**
     * Loop ask user to input the password
     * 
     * @param message Message asking to get input
     * @return Password from user.
     */
    public static String getPassword(String print)
    {
        boolean bOk = false;
        String userInput = "";  /* Text line input */
        while (!bOk)
        {
            userInput = getText(print);
            if (validatePassword(userInput))
                bOk = true;
            else
                System.out.println("Password must between 8-40 characters. Password must " + 
            "contain at least one lower alphbet, one upper and one number.");
        }
        return userInput;
    }

    /**
     * Loop ask until get the correct email format.
     * 
     * @param message Message asking to get input
     * @return String that will be the email of the user.
     */
    public static String getEmail(String print)
    {
        boolean bOk = false;
        String userInput = "";
        while (!bOk)
        {
            userInput = getText(print);
            if (validateEmail(userInput))
                bOk = true;
            else
                System.out.println("Wrong email format");
        }
        return userInput;
    }

    /**
     * Select category from category list.
     * 
     * @param message Message asking to get input
     * @return category string that user selected
     */
    public static String getCategory(String message)
    {
        System.out.println(message);
        ArrayList<String> categoryList = Category.getAllCategoryStr();
        /* Display all category */
        for (int i = 0; i < categoryList.size(); i++)
            System.out.println((i + 1) + " - " + categoryList.get(i));
        
        /* Let user select */
        int node = IOUtils.getInteger("Select category number: ", 1,
                categoryList.size());
        return categoryList.get(node - 1);
    }

    /**
     * Get image directory of auction program.
     * 
     * @return image directory in the system.
     */
    public static String getImageDir()
    {
        return System.getProperty("user.dir") + "/" + imgDirectory + "/";
    }

    /**
     * Upload the image to the system.
     * 
     * @return filename of the image. If error occur, return default image.
     */
    public static String uploadImage()
    {
        
        /* Upload image */
        JFileChooser frameChooseFile = new JFileChooser(
                FileSystemView.getFileSystemView());
        frameChooseFile.setDialogTitle("Select an image");
        frameChooseFile.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "jpg/png images", "png", "jpg");
        frameChooseFile.addChoosableFileFilter(filter);
        
        System.out.print("Selecting image... ");
        
        /* If user doesn't upload image, reset to default */
        int ret = JFileChooser.CANCEL_OPTION;
        ret = frameChooseFile.showOpenDialog(null);
        if (ret != JFileChooser.APPROVE_OPTION)
        {
            System.out.println("No image uploaded");
            return imgDefault;
        }
        else
            System.out.println("Image uploaded");
        
        /* Prepare file name and directory */
        String uploadedFileName = frameChooseFile.getSelectedFile().getName();
        String fileName = getAvailableFileName(uploadedFileName);
        String directory = getImageDir();
            
        /* Copy file to the image directory of online auction program */
        try
        {
            File src = frameChooseFile.getSelectedFile();
            File dest = new File(directory + fileName);
            Files.copy(src.toPath(), dest.toPath());
        }
        catch (IOException e)
        {
            System.out.println("Error occur, can't upload");
            return imgDefault;
        }
    
        return fileName;
    }

    /**
     * Find image file name that doesn't exist in image directory.
     * 
     * @param uploadedFileName File name that going to upload in system
     * @return Available file name that doesn't exist in image directory.
     */
    private static String getAvailableFileName(String uploadedFileName)
    {
        int count = 0;
        
        /* Split file name into suffix and prefix and prepare directory path */
        String[] fileSplit = uploadedFileName.split("\\.(?=[^\\.]+$)");
        String directory = getImageDir();
        String fileName = uploadedFileName;
    
        /* Check that file exists or not, If exists, change file name */
        File temp = null;
        boolean bLoop = true;
        do
        {
            temp = new File(directory + fileName);
            if (!temp.exists())
                bLoop = false;
            else
            {
                count++;
                fileName = fileSplit[0] + "-" + count + "." + fileSplit[1];
            }
        } while (bLoop);
        return fileName;
    }
}