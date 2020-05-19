import java.io.FileWriter;
import org.json.simple.JSONArray;

/**
 * Write file control. Can open, close, and write json to file.
 * This class uses the external class, JSON-SIMPLE.
 * Reference: https://code.google.com/archive/p/json-simple/
 * 
 * Created by Kla & Tong
 * 19 May 2020
 */
public class JSONFileWriter
{
	/** File name to open **/
	String fileName = null;
	
	/** Write file **/
	FileWriter fileWriter;
	
	/**
	 * Constructor of TextFileWriter. Set the file name
	 * @param fileName
	 */
	public JSONFileWriter(String fileName)
	{
		this.fileName = fileName; 
	}
	
	/**
	 * Open JSON file
	 * @return Return true if can open. Otherwise, false.
	 */
	private boolean open()
	{
		boolean bCheck = false;
		try
		{
			if(fileWriter != null)
			{
				fileWriter.close();
				fileWriter = null;
			}
			fileWriter = new FileWriter(fileName);
			bCheck = true;
		}
		catch(Exception e)
		{
			System.out.println("System: Error, Cannot open the write file.");
			fileWriter = null;
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
			if(fileWriter != null)
			{
				fileWriter.close();
				fileWriter = null;
			}
			bCheck = true;
		}
		catch(Exception e)
		{
			System.out.println("System: Error, Cannot close the write file.");
			fileWriter = null;
		}
		return bCheck;
	}
	
	/**
	 * Write JSON array to JSON file
	 * @return jsonArray Json Array that want to write to JSON file
	 */
	public boolean writeJSON(JSONArray jsonArray)
	{
		boolean bCheck = false;
		if(jsonArray != null && open() == true)
		{
			try
			{
				fileWriter.write(jsonArray.toJSONString());
			}
			catch (Exception e)
			{
				System.out.println("System: Error, cannot write file.");
			}
			close();
		}
		return bCheck;
	}
}
