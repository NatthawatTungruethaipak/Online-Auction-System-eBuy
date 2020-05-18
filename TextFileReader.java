import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Read file control. Can open, close, and read line from file.
 * 
 * Created by Kla & Tong
 * 19 May 2020
 */
public class TextFileReader
{
	String fileName = null;
	
	BufferedReader reader;
	
	public TextFileReader(String fileName)
	{
		this.fileName = fileName; 
	}
	
	public boolean openFile()
	{
		boolean bCheck = false;
		try
		{
			if(reader != null)
			{
				reader.close();
				reader = null;
			}
			reader = new BufferedReader(new FileReader(fileName));
			bCheck = true;
		}
		catch(Exception e)
		{
			System.out.println("System: Cannot open the read file");
			reader = null;
		}
		return bCheck;
	}
	
	public boolean closeFile()
	{
		boolean bCheck = false;
		try
		{
			if(reader != null)
			{
				reader.close();
				reader = null;
			}
			bCheck = true;
		}
		catch(Exception e)
		{
			System.out.println("System: Cannot close the read file, some error occur.");
			reader = null;
		}
		return bCheck;
	}
	
	public String readLine()
	{
		if(reader == null)
			return null;
		String line;
		try
		{
			line = reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("System: Error from reading file.");
			closeFile();
			return null;
		}
		return line;
	}
}
