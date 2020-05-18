import java.util.ArrayList;

public class Category {
	private static ArrayList<Category> categoryList = new ArrayList<Category>();
	
	private String category;
	
	public static Category findCategory(String categoryString)
	{
		Category retCategory = null;
		for(Category category: categoryList)
		{
			if(category.getCategoryString().equals(categoryString))
			{
				retCategory = category;
				break;
			}
		}
		if(retCategory == null)
			retCategory = Category.findCategory("Other");
		return retCategory;
	}
	
	public static ArrayList<String> getAllCategoryString()
	{
		ArrayList<String> retList = new ArrayList<String>();
		for(Category category: categoryList)
		{
			retList.add(category.getCategoryString());
		}
		return retList;
	}
	
	public String getCategoryString()
	{
		return category;
	}
	
	public int equals(String categoryString)
	{
		if(category == categoryString)
			return 0;
		else
			return 1;
	}
}