import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * Utility method about input and output in the auction program.
 * 
 * Created by Kla & Tong 18 May 2020
 */
public class IOUtils
{
    /** Directory of image **/
    final static String imgDirectory = "imgFolder";

    /** Image name **/
    final static String imgDefault = "default.png";

    /**
     * Initial the directory folder for image.
     */
    public static void initial()
    {
        /** Create image directory **/
        File file = new File(getImageDir());
        if (!file.exists())
            file.mkdir();
    }

    /**
     * Check the string is null or not.
     * 
     * @param string that will be check.
     * @return true, when the string is null. false, when the string is not null.
     */
    public static boolean isNullStr(String string)
    {
        if (string.trim().equals(""))
            return true;
        else
            return false;
    }

    /**
     * Validate the username in pattern of 6-30 characters with consist of alphabet,
     * number and underscore. First character can't be number.
     * 
     * Reference from
     * https://www.geeksforgeeks.org/how-to-validate-a-username-using-regular-expressions-in-java/
     * 
     * @param username is username to be validate
     * @return True, when username is in correct pattern. False, when username is not
     *         in correct pattern.
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
     * Validate the password in pattern of 8-40 characters with consist of at least
     * one number, one lower character, one upper character.
     * 
     * Reference from
     * https://examples.javacodegeeks.com/core-java/util/regex/matcher/validate-password-with-java-regular-expression-example/
     * 
     * @param password is password to be validate
     * @return @return True, when password is in correct pattern. False, when
     *         password is not in correct pattern.
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
     * @param email is email to be validate
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
     * Validate integer the string parameter must contain only integer in format of
     * string
     * 
     * @param string is string to be validate
     * @return true, when string is integer. False, when string is not integer.
     */
    public static boolean validateInteger(String string)
    {
        if (isNullStr(string))
            return false;
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

    public static void getEnter()
    {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    /**
     * Print the wording and get string from user.
     * 
     * @param print is wording for print out.
     * @return String from user input.
     */
    public static String getString(String print)
    {
        String returnString = "";
        boolean bOk = false;

        while (!bOk)
        {
            System.out.print(print);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (isNullStr(input))
            {
                System.out.println("Input can't be null");
            }
            else
            {
                bOk = true;
                returnString = input.trim();
            }
        }
        return returnString;
    }

    /**
     * Print the wording and get integer from user.
     * 
     * @param print is wording for print out.
     * @param min   is minimum value of number from user input.
     * @return Integer from user input.
     */
    public static int getInteger(String print, int min)
    {
        boolean bOk = false;
        int returnInt = 0;
        while (!bOk)
        {
            try
            {
                System.out.print(print);
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if (isNullStr(input))
                {
                    System.out.println("Input can't be null");
                }
                else
                {
                    returnInt = Integer.parseInt(input);
                    if (returnInt >= min)
                        bOk = true;
                    else
                        System.out.println(
                                "Input must be number greater than or equal to "
                                        + min);
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
     * Print the wording and get integer from user with the range of number.
     * 
     * @param print is wording for print out.
     * @param min   is minimum value of number from user input.
     * @param max   is maximum value of number from user input.
     * @return Integer from user input.
     */
    public static int getInteger(String print, int min, int max)
    {

        boolean bOk = false;
        int returnInt = 0;
        while (!bOk)
        {
            try
            {
                System.out.print(print);
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if (isNullStr(input))
                {
                    System.out.println("Input can't be null");
                }
                else
                {
                    returnInt = Integer.parseInt(input);
                    if ((returnInt >= min) && (returnInt <= max))
                        bOk = true;
                    else
                        System.out.println("Input must be number between " + min
                                + " and " + max);

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
     * Print the wording and get command from user.
     * 
     * @param print is wording for print out.
     * @return command number.
     */
    public static int getCommand(String print)
    {
        final String[] command =
        { "/home", "/help", "/next", "/prev", "/first", "/search", "/auction",
                "/register", "/login", "/logout", "/profile", "/makeauction",
                "/aboutus", "/exit" };
        int commandValue = 0;
        String input = getString(print);
        for (int i = 0; i < command.length; i++)
        {
            if (input.equalsIgnoreCase(command[i]))
                commandValue = i + 1;
        }
        return commandValue;
    }

    /**
     * Print the wording and get boolean from user.
     * 
     * @param print is wording for print out.
     * @return boolean from user input.
     */
    public static boolean getConfirm(String print)
    {
        boolean bOk = false;
        boolean returnConfirm = false;
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
                System.out.println("Input must be yes or no");
        }
        return returnConfirm;
    }

    /**
     * Print the wording and get date from user.
     * 
     * @param print   is wording for print out.
     * @param dateCpr is date that going to be compare.
     * @param bAfter  if true, date input must after dateCpr. Otherwise, date input
     *                must before dateCpr.
     * @return Date from user input.
     */
    public static Date getDate(String print, Date dateCpr, boolean bAfter)
    {
        boolean bOk = false;
        Date dateInput = null;
        String input = null;
        while (!bOk)
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
                        System.out.println("Input date must after "
                                + DateUtils.dateToStr(dateCpr) + " .");
                }
                else
                {
                    if (dateInput.before(dateCpr))
                        bOk = true;
                    else
                        System.out.println("Input date must before "
                                + DateUtils.dateToStr(dateCpr) + " .");
                }
            }
        }
        return dateInput;
    }

    /**
     * Print the wording and get date with time from user.
     * 
     * @param print   is wording for print out.
     * @param dateCpr is date that going to be compare.
     * @param bAfter  if true, date input must after dateCpr. Otherwise, date input
     *                must before dateCpr.
     * @return Date with time from user input.
     */
    public static Date getDateTime(String print, Date dateCpr, boolean bAfter)
    {
        boolean bOk = false;
        Date dateInput = null;
        String input = null;
        while (!bOk) /** Loop until date is valid **/
        {
            System.out.print(print);
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();

            if (DateUtils.validateDateTimeStr(input) == false)
            {
                System.out.println(
                        "Date with time format is dd-mm-yyyy-hh:mm. Example: 26-10-1998-13:39");
            }
            else
            {
                dateInput = DateUtils.strToDateTime(input);
                System.out.println(dateInput);
                if (dateCpr == null)
                    dateCpr = new Date();
                if (bAfter)
                {
                    if (dateInput.after(dateCpr))
                        bOk = true;
                    else
                        System.out.println("Input date must after "
                                + DateUtils.dateTimeToStr(dateCpr) + " .");
                }
                else
                {
                    if (dateInput.before(dateCpr))
                        bOk = true;
                    else
                        System.out.println("Input date must before "
                                + DateUtils.dateTimeToStr(dateCpr) + " .");

                }
            }
        }
        return dateInput;
    }

    /**
     * Get the username from user as an input and validate the username.
     * 
     * @param print is wording for print out.
     * @return String that will be the username of the user.
     */
    public static String getUsername(String print)
    {
        boolean bOk = false;
        String userInput = "";
        UserManager userManager = UserManager.getSingletonInstance();
        while (!bOk)
        {
            userInput = getString(print);
            if (validateUsername(userInput))
            {
                if (userManager.findUserByUsername(userInput) != null)
                {
                    System.out.println("The username is already in use.");
                    bOk = false;
                }
                else
                {
                    bOk = true;
                }
            }
            else
                System.out.println(
                        "Username must between 6-30 characters and first character must be alphabet and otherwise can be alphbet, number and underscore.");
        }
        return userInput;
    }

    /**
     * Get the password from user as an input and validate the password.
     * 
     * @param print is wording for print out.
     * @return String that will be the password of the user.
     */
    public static String getPassword(String print)
    {
        boolean bOk = false;
        String userInput = "";
        while (!bOk)
        {
            userInput = getString(print);
            if (validatePassword(userInput))
                bOk = true;
            else
                System.out.println(
                        "Password must between 8-40 characters. Password must contain at least one lower alphbet, one upper and one number.");
        }
        return userInput;
    }

    /**
     * Get the password from user as an input and validate the email.
     * 
     * @param print is wording for print out.
     * @return String that will be the email of the user.
     */
    public static String getEmail(String print)
    {
        boolean bOk = false;
        String userInput = "";
        while (!bOk)
        {
            userInput = getString(print);
            if (validateEmail(userInput))
                bOk = true;
            else
                System.out.print("Wrong email format");
        }
        return userInput;
    }

    /**
     * Get image directory.
     * 
     * @return image directory in the system.
     */
    public static String getImageDir()
    {
        return System.getProperty("user.dir") + "/" + imgDirectory + "/";
    }

    private static String getAvailableFileName(String uploadedFileName)
    {
        int count = 0;
        
        /** Split file name into suffix and prefix and prepare directory path **/
        String[] fileSplit = uploadedFileName.split("\\.(?=[^\\.]+$)");
        String directory = getImageDir();
        String fileName = uploadedFileName;

        /** Check that file exists or not, If exists, change file name **/
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
    /**
     * Upload the image to the system.
     * 
     * @return filename of the image. If error occur, return default image.
     */
    public static String uploadImage()
    {
        
        /** Upload image **/
        JFileChooser frameChooseFile = new JFileChooser(
                FileSystemView.getFileSystemView());
        frameChooseFile.setDialogTitle("Select an image");
        frameChooseFile.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "jpg/png images", "png", "jpg");
        frameChooseFile.addChoosableFileFilter(filter);
        
        System.out.print("Selecting image... ");
        /** If user doesn't upload image, reset to default **/
        int ret = JFileChooser.CANCEL_OPTION;
        ret = frameChooseFile.showOpenDialog(null);
        if (ret != JFileChooser.APPROVE_OPTION)
        {
            System.out.println("No image uploaded");
            return imgDefault;
        }
        else
            System.out.println("Image uploaded");
        
        /** Prepare file name and directory **/
        String uploadedFileName = frameChooseFile.getSelectedFile().getName();
        String fileName = getAvailableFileName(uploadedFileName);
        String directory = getImageDir();
            
        /** Copy file to the image directory of online auction program **/
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
     * Select category from category list
     * 
     * @param print is wording for print out.
     * @return category that user selected
     */
    public static String getCategory(String print)
    {
        System.out.println(print);
        ArrayList<String> categoryList = Category.getAllCategoryStr();
        for (int i = 0; i < categoryList.size(); i++)
            System.out.println((i + 1) + " - " + categoryList.get(i));
        int node = IOUtils.getInteger("Select category number: ", 1,
                categoryList.size());
        return categoryList.get(node - 1);
    }

}