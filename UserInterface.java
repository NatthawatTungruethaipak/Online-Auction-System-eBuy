
public class UserInterface
{

    private static UserInterface userInterface = new UserInterface();
    
    private UserInterface()
    {
    }
    
    public static UserInterface getSingletonInstance()
    {
        return userInterface;
    }
    
    public void displayIntroduction()
    {
        
    }

}
