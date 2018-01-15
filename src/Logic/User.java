package Logic;


import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by maxhe on 13-12-2017.
 */
public class User implements Serializable
{
    private final String username;
    private String sessionID;

    public User(String username) throws RemoteException{
        this.username = username;
    }

    public String getUsername(){return username;}
    public void setSessionID(String sessionID){this.sessionID = sessionID;}
    public String getSessionID(){return sessionID;}

    @Override
    public String toString(){
        return username;
    }

}
