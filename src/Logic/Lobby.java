package Logic;

import GameServer.RMIGameClient;
import Interfaces.IGame;
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
    private List<Game> games;
    private IRemotePublisherForDomain publisherLobby = null;
    private IRemotePublisherForDomain publisherGame = null;

    public Lobby(IRemotePublisherForDomain publisherLobby,IRemotePublisherForDomain publisherGame) throws RemoteException{
        users = new ArrayList<>();
        invitations = new ArrayList<>();
        games = new ArrayList<>();
        this.publisherLobby = publisherLobby;
        this.publisherGame = publisherGame;

        this.publisherLobby.registerProperty("lobby");
        this.publisherLobby.registerProperty("invitation");
        this.publisherGame.registerProperty("game");
    }

    public void sendInvitation(Invitation invitation) throws ConcurrentModificationException, RemoteException{
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

        publisherLobby.inform("invitation", invitations, invitations);
    }

    public void declineInvitation(Invitation invitation) throws RemoteException
    {
        publisherLobby.inform("invitation",invitations,invitations);
        System.out.println("invitation removed");
    }

    @Override
    public void acceptInvitation(Invitation invitation, IGame game) throws RemoteException
    {
        try
        {
            game.createGame(invitation.getReceiver(),invitation.getSender());
            publisherGame.inform("game",null,game);
        } catch (NullPointerException e)
        {
            System.out.println("GameServer: NullpointerException " + e.getMessage());
        }
    }

    @Override
    public void connectWithGameserver(User user) throws RemoteException
    {
        users.add(user);
        publisherLobby.inform("lobby",null,users);
    }
}
