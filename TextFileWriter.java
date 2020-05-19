import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write file control. Can open, close, and write line to file.
 * 
 * Created by Kla & Tong 19 May 2020
 */
public class TextFileWriter
{
    /** File name to open */
    private String fileName = null;

    /** Write buffer from file **/
    private BufferedWriter writer;

    /**
     * Constructor of TextFileWriter. Set the file name
     * 
     * @param fileName
     */
    public TextFileWriter(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * Open file and setting buffer
     * 
     * @return Return true if can open. Otherwise, false.
     */
    public boolean open()
    {
        boolean bCheck = false;
        try
        {
            if (writer != null)
            {
                writer.close();
                writer = null;
            }
            writer = new BufferedWriter(new FileWriter(fileName));
            bCheck = true;
        }
        catch (Exception e)
        {
            System.out.println("System: Cannot open the write file");
            writer = null;
        }
        return bCheck;
    }

    /**
     * Close buffer
     * 
     * @return Return true if can close. If there are error occur, false
     */
    public boolean close()
    {
        boolean bCheck = false;
        try
        {
            if (writer != null)
            {
                writer.close();
                writer = null;
            }
            bCheck = true;
        }
        catch (Exception e)
        {
            System.out.println(
                    "System: Cannot close the write file, some error occur.");
            writer = null;
        }
        return bCheck;
    }

    /**
     * Write line text to file
     * 
     * @return Line read from text file
     */
    public boolean writeLine(String line)
    {
        boolean bCheck = false;
        try
        {
            writer.write(line);
        }
        catch (IOException e)
        {
            System.out.println("System: Error, cannot write file.");
            close();
        }
        return bCheck;
    }
}
