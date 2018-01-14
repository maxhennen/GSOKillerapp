package Logic;

import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 14-1-2018.
 */
public class UserTest
{
    private User user = null;

    @Before
    public void before(){
        try
        {
            user = new User("player");
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getUsername() throws Exception
    {
        assertEquals("player",user.getUsername());
    }

    @Test
    public void stringTo() throws Exception
    {
        assertEquals("player",user.toString());
    }

}