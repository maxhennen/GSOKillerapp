package Interfaces;

import Client.RMIClient;
import Logic.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by maxhe on 13-12-2017.
 */
public interface IServerReference extends Remote
{
    /**
     * Connects a user with a gameserver
     * @param user the user that will be connected with the gameserver
     * @throws RemoteException if there's a problem with the connection
     */
    void connectWithGameserver(User user) throws RemoteException;
}
