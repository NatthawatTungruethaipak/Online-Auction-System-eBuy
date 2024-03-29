import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Read file control. Can open, close, and read line from file.
 * 
 * Created by Kla & Tong 19 May 2020
 */
public class TextFileReader
{
    /** File name to open */
    private String fileName = null;

    /** Read buffer from file */
    private BufferedReader reader;

    /**
     * Constructor of TextFileReader. Set the file name to read.
     * 
     * @param fileName File name that going to open.
     */
    public TextFileReader(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * Open file and setting buffer.
     * 
     * @return Return true if can open. Otherwise, false.
     */
    public boolean open()
    {
        boolean bCheck = false;
        try
        {
            /* Setting read buffer */
            if (reader != null)
            {
                reader.close();
                reader = null;
            }
            reader = new BufferedReader(new FileReader(fileName));
            bCheck = true;
        }
        catch (Exception e)
        {
            System.out.println("System: Cannot open the read file");
            reader = null;
        }
        return bCheck;
    }

    /**
     * Close read buffer
     * 
     * @return Return true if can close. If there are error occur, false
     */
    public boolean close()
    {
        boolean bCheck = false;
        try
        {
            if (reader != null)
            {
                reader.close();
                reader = null;
            }
            bCheck = true;
        }
        catch (Exception e)
        {
            System.out.println("System: Cannot close the read file, some error occur.");
            reader = null;
        }
        return bCheck;
    }

    /**
     * Read line from text file
     * 
     * @return Line read from text file
     */
    public String readLine()
    {
        if (reader == null) /* Don't have read buffer */
            return null;
        String line;
        /* Read line */
        try
        {
            line = reader.readLine();
        }
        catch (IOException e)
        {
            System.out.println("System: Error from reading file.");
            close();
            return null;
        }
        return line;
    }
}
