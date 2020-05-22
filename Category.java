import java.util.ArrayList;

/**
 * Category for item in auction.
 * 
 * Created by Kla & Tong 18 May 2020
 */
public class Category
{
    /** All category **/
    private static ArrayList<Category> categoryList = new ArrayList<Category>();

    /** Category string **/
    private String categoryStr;

    /**
     * Constructor of Category. Set the category string.
     * 
     * @param categoryStr String of category
     */
    public Category(String categoryStr)
    {
        this.categoryStr = categoryStr;
    }

    /**
     * Initialise the Category when starting the program
     */
    public static void initial()
    {
        String[] strArray =
        { "Electronics", "Collecibles & Art", "Fashion", "Home & Garden",
                "Sporting Goods", "Health & Beauty", "Toys & Hobbies", "Books",
                "Movies", "Music", "Business & Industrial", "Vehicle", "Other" };
        
        for (String str : strArray)
            categoryList.add(new Category(str));
    }

    /**
     * Find the category instance from category list
     * 
     * @param categoryFind String of category that want to get
     * @return Category instance that finding from string. If not found, Category of
     *         "Other".
     */
    public static Category findCategory(String categoryFind)
    {
        Category retCategory = null;    /* return category */
        for (Category category : categoryList)
        {
            String categoryStr = category.getCategoryStr();
            if (categoryStr.equals(categoryFind))
            {
                retCategory = category;
                break;
            }
        }
        
        /* If it's null set to 'Other' */
        if (retCategory == null)
            retCategory = Category.findCategory("Other");
        return retCategory;
    }

    /**
     * Get the array list of category string.
     * 
     * @return Array list of category in string format.
     */
    public static ArrayList<String> getAllCategoryStr()
    {
        ArrayList<String> retList = new ArrayList<String>();
        for (Category category : categoryList)
        {
            retList.add(category.getCategoryStr());
        }
        return retList;
    }

    /**
     * Get the category list.
     * 
     * @return Array list of category.
     */
    public static ArrayList<Category> getAllCategory()
    {
        return categoryList;
    }

    /**
     * Get the string of category.
     * 
     * @return Category word.
     */
    public String getCategoryStr()
    {
        return this.categoryStr;
    }

    /**
     * Override the equals method to compare the string between category.
     * 
     * @param categoryStr Category string that want to compare
     * @return True if string inside category and string that want to compare is the
     *         same. Otherwise, False.
     */
    public boolean equals(String categoryStr)
    {
        if (this.categoryStr == categoryStr)
            return true;
        else
            return false;
    }
}