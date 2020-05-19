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
	String fileName = null;
	
	BufferedWriter writer;
	
	
	public TextFileWriter(String fileName)
	{
		this.fileName = fileName; 
	}
	
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
