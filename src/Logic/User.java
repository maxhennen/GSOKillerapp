package Logic;

import fontyspublisher.IRemotePropertyListener;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by maxhe on 13-12-2017.
 */
public class User implements Serializable
{
    private String username;

    public User(String username){
        this.username = username;
    }

    public String getUsername(){return username;}

    public void sendInvitation(){

    };

    public void acceptInvitation(){

    }

    public void declineInvitation(){

    }

    public void placeShip(){

    }


    public boolean guessShip(){
        return false;
    }

    public void receiveInvitation(Invitation invitation){

    }

    @Override
    public String toString(){
        return username;
    }
}
