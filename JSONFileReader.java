import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 * Read file control. Can open, close, and read json from file.
 * This class uses the external class, JSON-SIMPLE.
 * Reference: https://code.google.com/archive/p/json-simple/
 * 
 * Created by Kla & Tong
 * 19 May 2020
 */
public class JSONFileReader
{
	/** File name to open **/
	String fileName = null;
	
	/** File reader **/
	FileReader fileReader;
	
	/**
	 * Constructor of TextFileReader. Set the file name
	 * @param fileName
	 */
	public JSONFileReader(String fileName)
	{
		this.fileName = fileName; 
	}
	
	/**
	 * Open JSON file.
	 * @return Return true if can open. Otherwise, false.
	 */
	private boolean open()
	{
		boolean bCheck = false;
		try
		{
			if(fileReader != null)
			{
				fileReader.close();
				fileReader = null;
			}
			fileReader = new FileReader(fileName);
			bCheck = true;
		}
		catch(Exception e)
		{
			System.out.println("System: Error, Cannot open the read file");
			fileReader = null;
		}
		return bCheck;
	}
	
	/**
	 * Close file
	 * @return Return true if can close. If there are error occur, false
	 */
	private boolean close()
	{
		boolean bCheck = false;
		try
		{
			if(fileReader != null)
			{
				fileReader.close();
				fileReader = null;
			}
			bCheck = true;
		}
		catch(Exception e)
		{
			System.out.println("System: Error, cannot close the read file.");
			fileReader = null;
		}
		return bCheck;
	}
	
	/**
	 * Read line from text file
	 * @return Line read from text file
	 */
	public JSONArray readJSON()
	{
		if(open() == false)
			return null;
		JSONParser jsonParser = new JSONParser();
		Object obj = null;
		try
		{
			obj = jsonParser.parse(fileReader);
		}
		catch (Exception e)
		{
			System.out.println("System: Error, cannot reading file.");
			close();
			return null;
		}
		close();
		return (JSONArray) obj;
	}
}
