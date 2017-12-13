package Logic;

import java.io.Serializable;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Invitation implements Serializable
{
    private int senderID;
    private int receiverID;
    private String message;

    public Invitation(int senderID, int receiverID, String usernameSender){
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = usernameSender + " want play a game with you!";
    }

    public int getSenderID(){return senderID;}
    public int getReceiverID(){return receiverID;}
    public String getMessag(){return message;}
}
