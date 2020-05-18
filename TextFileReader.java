import java.io.*;

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
		try
		{
			if(reader != null)
			{
				reader.close();
				reader = null;
			}
			reader = new BufferedReader(new FileReader(fileName));
		}
		catch(Exception e)
		{
			System.out.println("System: Cannot open the read file");
			reader = null;
			return false;
		}
		return true;
	}
	
	public boolean closeFile()
	{
		try
		{
			if(reader != null)
			{
				reader.close();
				reader = null;
			}
		}
		catch(Exception e)
		{
			System.out.println("System: Cannot close read file, some error occur.");
			reader = null;
			return false;
		}
		return true;
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
