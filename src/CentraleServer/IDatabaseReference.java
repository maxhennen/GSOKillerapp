package CentraleServer;

import Logic.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created by maxhe on 6-12-2017.
 */
public interface IDatabaseReference extends Remote
{
    User login(String email, String password)throws RemoteException;
    void register(String username, String email, String password)throws RemoteException;
}
