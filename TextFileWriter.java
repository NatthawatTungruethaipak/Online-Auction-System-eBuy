import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Write file control. Can open, close, and write line to file.
 * 
 * Created by Kla & Tong
 * 19 May 2020
 */
public class TextFileWriter
{
	/** File name to open **/
	String fileName = null;
	
	/** Buffer to write from file **/
	BufferedWriter writer;
	
	/**
	 * Constructor of TextFileWriter. Set the file name
	 * @param fileName File name to open/write
	 */
	public TextFileWriter(String fileName)
	{
		this.fileName = fileName; 
	}
	
	/**
	 * Open file
	 * @return Return true if can open. Otherwise, false.
	 */
	public boolean openFile()
	{
		boolean bCheck = false;
		try
		{
			if(writer != null)
			{
				writer.close();
				writer = null;
			}
			writer = new BufferedWriter(new FileWriter(fileName));
			bCheck = true;
		}
		catch(Exception e)
		{
			System.out.println("System: Cannot open the write file");
			writer = null;
		}
		return bCheck;
	}
	
	/**
	 * Close file
	 * @return Return true if can close. If there are error occur, false
	 */
	public boolean closeFile()
	{
		boolean bCheck = false;
		try
		{
			if(writer != null)
			{
				writer.close();
				writer = null;
			}
			bCheck = true;
		}
		catch(Exception e)
		{
			System.out.println("System: Cannot close the write file, some error occur.");
			writer = null;
		}
		return bCheck;
	}
	
	/**
	 * Write line to text file
	 * @param line Line string that want to write to text file
	 * @return True if writting success. Otherwise, false.
	 */
	public boolean writerLine(String line)
	{
		boolean bCheck = false;
		try
		{
			writer.write(line);
		}
		catch (IOException e)
		{
			System.out.println("System: Error, cannot write file.");
			closeFile();
		}
		return bCheck;
	}
}
