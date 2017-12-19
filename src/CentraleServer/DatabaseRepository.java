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
    private CentraleRMIServer server;

    public DatabaseRepository(IDatabaseReference context, CentraleRMIServer server) throws RemoteException
    {
        this.context = context;
        this.server = server;
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

    @Override
    public boolean connectWithGameserver(User user) throws RemoteException
    {
        return server.connectWithGameserver(user);
    }
}
