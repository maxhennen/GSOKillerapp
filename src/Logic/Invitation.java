package Logic;

import java.io.Serializable;

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

    public User getSender(){return sender;}
    public User getReceiver(){return receiver;}

    public String toString(){
        return message;
    }
}
