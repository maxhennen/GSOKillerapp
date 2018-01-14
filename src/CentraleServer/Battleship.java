package CentraleServer;

import Interfaces.IBattleship;
import Logic.User;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Battleship extends UnicastRemoteObject implements IBattleship, Serializable

{
    private IDatabaseReference context;
    private CentraleRMIServer server;

    public Battleship(IDatabaseReference context, CentraleRMIServer server) throws RemoteException{
        this.context = context;
        this.server = server;
    }

    public void register(String username, String email, String password) throws RemoteException
    {
        try
        {
            context.register(username,email,password);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Something went wrong. Try again later");
        }
    }

    public User login(String email, String password)throws RemoteException{
        try
        {
            return context.login(email,password);
        }
        catch (RemoteException e){
            System.out.println("Client: RemoteException: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void connectWithGameserver(User user) throws RemoteException
    {
        server.connectWithGameserver(user);
    }

}
