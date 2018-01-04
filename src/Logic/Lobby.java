package Logic;

import GameServer.RMIGameClient;
import Interfaces.ILobby;
import Interfaces.IServerReference;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.IRemotePublisherForListener;
import fontyspublisher.RemotePublisher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Lobby extends UnicastRemoteObject implements IServerReference, ILobby, Serializable
{
    private List<User> users;
    private List<Invitation> invitations;
    private IRemotePublisherForDomain publisher = null;

    public Lobby(IRemotePublisherForDomain publisher) throws RemoteException{
        users = new ArrayList<>();
        invitations = new ArrayList<>();
        this.publisher = publisher;
        publisher.registerProperty("lobby");
        publisher.registerProperty("invitation");
    }

    public void sendInvitation(Invitation invitation) throws ConcurrentModificationException{
        try
        {
            if(invitations.size() != 0)
            {
                for (Invitation i : invitations)
                {
                    if (!i.toString().equals(invitation.toString()))
                    {
                        invitations.add(invitation);
                    }
                }
            }
            else {
                invitations.add(invitation);
            }
        } catch (ConcurrentModificationException e)
        {
            System.out.println("GameServer: ConcurrentModificationException: " + e.getMessage());
        }

        try
        {
            publisher.inform("invitation", invitations, invitations);
        } catch (RemoteException e)
        {
            System.out.println("GameServer: RemoteException: " + e.getMessage());
        }
    }

    public void acceptInvitation(){

    }

    public void declineInvitation(Invitation invitation)
    {
        try{
            for (Iterator<Invitation> i = invitations.iterator(); i.hasNext();)
            {
                Invitation in = i.next();
                if(in.toString().equals(invitation.toString())){
                    i.remove();
                }
            }
            publisher.inform("invitation",invitations,invitations);
            System.out.println("invitation removed");
        }
        catch (RemoteException e){
            System.out.println("GameServer: RemoteException: " + e.getMessage());
        }
        catch (ConcurrentModificationException e){
            System.out.println("GameServer: ConcurrentModificationException: " + e.getMessage());
            declineInvitation(invitation);
        }
    }

    public void receiveInvitation(Invitation invitation){

    }

    @Override
    public void connectWithGameserver(User user) throws RemoteException
    {
        try
        {
            users.add(user);
            publisher.inform("lobby",null,users);
        }
        catch (RemoteException e){
            System.out.println("GameServer: RemoteException: " + e.getMessage());
        }
    }
}
