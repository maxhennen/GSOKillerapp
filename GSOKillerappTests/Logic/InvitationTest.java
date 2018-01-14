package Logic;

import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 14-1-2018.
 */
public class InvitationTest
{

    Invitation invitation = null;

    @Before
    public void before(){
        try
        {
            invitation = new Invitation(new User("sender"),new User("receiver"));
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getSender() throws Exception
    {
        assertEquals("sender",invitation.getSender().getUsername());
    }

    @Test
    public void getReceiver() throws Exception
    {
        assertEquals("receiver",invitation.getReceiver().getUsername());
    }

    @Test
    public void stringTo() throws Exception{
        assertEquals("sender wants to play a game with you!",invitation.toString());
    }

}