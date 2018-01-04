package Logic;

import fontyspublisher.IRemotePublisherForDomain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxhe on 13-12-2017.
 */
public class User implements Serializable
{
    private String username;
    private int ID;
    private List<Invitation> invitations;

    public User(String username, int id) throws RemoteException{
        this.username = username;
        this.ID = id;
        invitations = new ArrayList<>();
    }

    public String getUsername(){return username;}

    public int getID(){return ID;}

    public void placeShip(){

    }


    public boolean guessShip(){
        return false;
    }



    @Override
    public String toString(){
        return username;
    }

}
