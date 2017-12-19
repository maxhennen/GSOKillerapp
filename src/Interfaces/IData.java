package Interfaces;

import Logic.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by maxhe on 6-12-2017.
 */
public interface IData extends Remote
{
    void register(String username, String email, String password)throws RemoteException;
    User login(String email, String password)throws RemoteException;
    boolean connectWithGameserver(User user) throws RemoteException;
}
