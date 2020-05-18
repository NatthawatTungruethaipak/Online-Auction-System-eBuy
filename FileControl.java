import java.io.*;

/**
 * Basic File Controller. Open file, read file, write file, etc.
 * 
 * Created by Kla & Tong
 * 18 May 2020
 */
public class FileControl
{
	/** File name that going to read/write **/
	private String fileName;
	
	/** Buffer to read **/
	private BufferedReader reader = null;
	
	/** Buffer to write **/
	private BufferedWriter writer = null;
	
	public FileControl(String fileName)
	{
		this.fileName = fileName;
		/** Initial write buffer **/
		FileReader fileWriter = null;
		try
		{
			fileWriter = new FileWriter(fileName);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("System: Do not found read file");
			return;
		}
		reader = new BufferedReader(fileReader);
		return;
		
		/** Initial read buffer **/
		FileReader fileReader = null;
		try
		{
			fileReader = new FileReader(fileName);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("System: Do not found read file");
			return;
		}
		reader = new BufferedReader(fileReader);
		return;
	}
	
	public boolean openReadFile()
	{
		
	}
	
	public boolean closeReadFile()
	{
		
	}
	
	public boolean openWriteFile()
	{
		
	}
	
	public boolean closeWriteFile()
	{
		
	}
	
	public String readLine()
	{
		
	}
	
	public boolean writeLine(String line)
	{
		
	}
}