package CentraleServer;

import Interfaces.IData;
import Interfaces.IDatabaseReference;
import Logic.User;
import fontyspublisher.IRemotePublisherForDomain;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by maxhe on 6-12-2017.
 */
public class DatabaseRepository extends UnicastRemoteObject implements IData
{
    //private static final long serialVersionUID = 1L;
    private IDatabaseReference context;

    public DatabaseRepository(IDatabaseReference context) throws RemoteException
    {
        this.context = context;
    }

    @Override
    public void register(String username, String email, String password)throws RemoteException
    {
        context.register(username,email,password);
    }

    @Override
    public User login(String email, String password)throws RemoteException
    {
        return context.login(email,password);
    }
}
