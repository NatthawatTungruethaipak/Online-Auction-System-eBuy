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
     * Constructor of Category. Set the string and add the Category to list
     * 
     * @param categoryStr String of category
     */
    public Category(String categoryStr)
    {
        this.categoryStr = categoryStr;
        categoryList.add(this);
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
        {
            new Category(str);
        }
    }

    /**
     * Find the category instance from all category
     * 
     * @param categoryFind String of category that want to get
     * @return Category instance that finding from string. If not found, Category of
     *         "Other".
     */
    public static Category findCategory(String categoryFind)
    {
        Category retCategory = null;
        for (Category category : categoryList)
        {
            if (category.equals(categoryFind))
            {
                retCategory = category;
                break;
            }
        }
        if (retCategory == null)
            retCategory = Category.findCategory("Other");
        return retCategory;
    }

    /**
     * Get the category string list
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
     * Get category string of current instance.
     * 
     * @return Category string inside.
     */
    public String getCategoryStr()
    {
        return this.categoryStr;
    }

    /**
     * Override the equals method to compare from string
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

    /**
     * Check that category contain in list of category or not.
     * @param category to be check
     * @return True when category contain in the list of category. Otherwise false.
     */
    public boolean contains(String category)
    {
        boolean contain = false;
        for (Category eachCategory : categoryList)
        {
            if (eachCategory.equals(category))
            {
                contain = true;
                break;
            }
        }
        return contain;
    }
}