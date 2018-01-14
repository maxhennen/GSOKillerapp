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

    public User(String username) throws RemoteException{
        this.username = username;
    }

    public String getUsername(){return username;}

    @Override
    public String toString(){
        return username;
    }

}
