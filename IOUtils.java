import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Represent the utility method about input and output in the auction program.
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
        File file = new File(getImgDir());
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
     * one number, one lower character, one upper character, one special that is [ @
     * # $ % ! . _ -].
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
        String passwordPattern = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!_-]).{8,40})";
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

    /**
     * Print the wording and get string from user.
     * 
     * @param print is wording for print out.
     * @return String from user input.
     */
    public static String getString(String print)
    {
        System.out.print(print);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (isNullStr(input))
            return "";
        else
            return input.trim();
    }

    /**
     * Print the wording and get integer from user.
     * 
     * @param print is wording for print out.
     * @return Integer from user input.
     */
    public static int getInteger(String print)
    {
        System.out.print(print);
        boolean bOk = false;
        int input = 0;
        while (!bOk)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                bOk = true;
            }
            catch (Exception e)
            {
                System.out.println("Format mismatch");
            }

        }
        return input;
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
        System.out.print(print);
        boolean bOk = false;
        int input = 0;
        while (!bOk)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                if (input > min)
                    bOk = true;
                else
                    System.out.println("Number out of range");
            }
            catch (Exception e)
            {
                System.out.println("Format mismatch");
            }

        }
        return input;
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
        System.out.print(print);
        boolean bOk = false;
        int input = 0;
        while (!bOk)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
                if ((input > min) && (input < max))
                    bOk = true;
                else
                    System.out.println("Number out of range");

            }
            catch (Exception e)
            {
                System.out.println("Format mismatch");
            }

        }
        return input;
    }

    /**
     * Print the wording and get boolean from user.
     * 
     * @param print is wording for print out.
     * @return boolean from user input.
     */
    public static boolean getConfirm(String print)
    {
        System.out.print(print);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no")))
        {
            System.out.println("Format mismatch");
            scanner = new Scanner(System.in);
            input = scanner.nextLine();
        }
        if (input.equalsIgnoreCase("yes"))
            return true;
        else if (input.equalsIgnoreCase("no"))
            return false;
        return false;
    }

    /**
     * Print the wording and get date from user.
     * 
     * @param print   is wording for print out.
     * @param dateCpr is date that going to be compare.
     * @param command is select before or after compare operation. For command = 1 is
     *                before, command = 2 is after. Otherwise will not check.
     * @return Date from user input.
     */
    public static Date getDate(String print, Date dateCpr, int command)
    {
        boolean bOk = false;
        Date dateInput = null;
        while (!bOk)
        {
            System.out.print(print);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            while (DateUtils.validateDateStr(input) == false)
            {
                {
                    System.out.println("Format mismatch");
                    scanner = new Scanner(System.in);
                    input = scanner.nextLine();
                }
            }
            dateInput = DateUtils.strToDate(input);
            if (dateCpr == null)
                dateCpr = new Date();
            if (command == 1)
            {
                if (dateInput.before(dateCpr))
                    bOk = true;
            }
            else if (command == 2)
            {
                if (dateInput.after(dateCpr))
                    bOk = true;
            }
            else
                bOk = true;
        }
        return dateInput;
    }

    /**
     * Print the wording and get date with time from user.
     * 
     * @param print   is wording for print out.
     * @param dateCpr is date that going to be compare.
     * @param command is select before or after compare operation. For command = 1 is
     *                before, command = 2 is after. Otherwise will not check.
     * @return Date with time from user input.
     */
    public static Date getDateTime(String print, Date dateCpr, int command)
    {
        boolean bOk = false;
        Date dateInput = null;
        while (!bOk)
        {
            System.out.print(print);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            while (DateUtils.validateDateTimeStr(input) == false)
            {
                {
                    System.out.println("Format mismatch");
                    scanner = new Scanner(System.in);
                    input = scanner.nextLine();
                }
            }
            dateInput = DateUtils.strToDateTime(input);
            if (dateCpr == null)
                dateCpr = new Date();
            if (command == 1)
            {
                if (dateInput.before(dateCpr))
                    bOk = true;
            }
            else if (command == 2)
            {
                if (dateInput.after(dateCpr))
                    bOk = true;
            }
            else
                bOk = true;
        }
        return dateInput;
    }

    /**
     * Print the wording and get command from user.
     * 
     * @param print is wording for print out.
     * @return command number.
     */
    public static int getCommand(String print)
    {
        boolean bOk = false;
        int commandValue = 0;
        while (!bOk)
        {
            String input = getString(print);
            if (input.equals("/home"))
            {
                commandValue = 1;
                bOk = true;
            }
            else if (input.equals("/help"))
            {
                commandValue = 2;
                bOk = true;
            }
            else if (input.equals("/next"))
            {
                commandValue = 3;
                bOk = true;
            }
            else if (input.equals("/prev"))
            {
                commandValue = 4;
                bOk = true;
            }
            else if (input.equals("/first"))
            {
                commandValue = 5;
                bOk = true;
            }
            else if (input.equals("/search"))
            {
                commandValue = 6;
                bOk = true;
            }
            else if (input.equals("/auction"))
            {
                commandValue = 7;
                bOk = true;
            }
            else if (input.equals("/register"))
            {
                commandValue = 8;
                bOk = true;
            }
            else if (input.equals("/login"))
            {
                commandValue = 9;
                bOk = true;
            }
            else if (input.equals("/logout"))
            {
                commandValue = 10;
                bOk = true;
            }
            else if (input.equals("/profile"))
            {
                commandValue = 11;
                bOk = true;
            }
            else if (input.equals("/makeauction"))
            {
                commandValue = 12;
                bOk = true;
            }
            else if (input.equals("/aboutus"))
            {
                commandValue = 13;
                bOk = true;
            }
            else if (input.equals("/exit"))
            {
                commandValue = 14;
                bOk = true;
            }
            else
            {
                bOk = false;
            }
        }
        return commandValue;
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
        while (!bOk)
        {
            userInput = getString(print);
            if (validateUsername(userInput))
                bOk = true;
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
        }
        return userInput;
    }

    /**
     * Get image directory.
     * 
     * @return image directory in the system.
     */
    public static String getImgDir()
    {
        return System.getProperty("user.dir") + "\\" + imgDirectory + "\\";
    }

    /**
     * Upload the image to the system.
     * 
     * @return filename of the image.
     */
    public static String uploadImage()
    {
        int count = 0;
        /** Upload image **/
        JFileChooser frameChooseFile = new JFileChooser();
        frameChooseFile.setDialogTitle("Select an image");
        frameChooseFile.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "jpg or png images", "png", "jpg");
        frameChooseFile.addChoosableFileFilter(filter);

        /** If user doesn't upload image, reset to default **/
        if (frameChooseFile.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
            return imgDefault;

        /** Split file name into suffix and prefix and prepare directory path **/
        String fileName = frameChooseFile.getSelectedFile().getName();
        String directory = getImgDir();
        String[] fileSplit = fileName.split("\\.(?=[^\\.]+$)");

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

        /** Copy file to the image directory of online auction program **/
        File src = frameChooseFile.getSelectedFile();
        File dest = new File(directory + fileName);
        try
        {
            Files.copy(src.toPath(), dest.toPath());
        }
        catch (IOException e)
        {
            System.out.println("Error occur, can't upload");
            return null;
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