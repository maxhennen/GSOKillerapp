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
    void connectWithGameserver(User user) throws RemoteException;
}
