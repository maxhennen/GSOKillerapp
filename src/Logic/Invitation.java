package Logic;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Invitation implements Serializable
{
    private User sender;
    private User receiver;
    private String message;

    public Invitation(User sender, User receiver){
        this.sender = sender;
        this.receiver = receiver;
        this.message = sender.getUsername() + " wants to play a game with you!";
    }

    public String toString() {
        return message;
    }

    public User getSender()
    {
        return sender;
    }

    public User getReceiver()
    {
        return receiver;
    }
}
