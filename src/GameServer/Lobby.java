package GameServer;

import Interfaces.IGame;
import Interfaces.ILobby;
import Interfaces.IServerReference;
import Logic.Invitation;
import Logic.User;
import fontyspublisher.IRemotePublisherForDomain;

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
    private final List<User> users;
    private final List<Invitation> invitations;
    private IRemotePublisherForDomain publisherLobby = null;
    private IRemotePublisherForDomain publisherGame = null;

    public Lobby(IRemotePublisherForDomain publisherLobby,IRemotePublisherForDomain publisherGame) throws RemoteException{
        users = new ArrayList<>();
        invitations = new ArrayList<>();
        this.publisherLobby = publisherLobby;
        this.publisherGame = publisherGame;

        this.publisherLobby.registerProperty("lobby");
        this.publisherLobby.registerProperty("invitation");
        this.publisherGame.registerProperty("game");
    }

    public void sendInvitation(Invitation invitation) throws ConcurrentModificationException, RemoteException{
        if(invitations.size() != 0)
        {
            Iterator<Invitation> i = invitations.iterator();
            while (i.hasNext()){
                if(!(i.next().toString().equals(invitation.toString()))){
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
        Iterator<Invitation> i = invitations.iterator();
        while (i.hasNext()){
            if(i.next().toString().equals(invitation.toString())){
                i.remove();
            }
        }
        publisherLobby.inform("invitation",null,invitations);
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

        Iterator<User> u = users.iterator();
        while (u.hasNext()){
            User user = u.next();
            if(user.getUsername().equals(invitation.getReceiver().getUsername()) || user.getUsername().equals(invitation.getSender().getUsername())){
                u.remove();
            }
        }

        publisherLobby.inform("lobby",null,users);
    }

    @Override
    public void goBackToLobbyAfterGame(User user) throws RemoteException
    {
        users.add(user);
        publisherLobby.inform("lobby",null,users);
    }

    /**
     * Removes user that logs out from list with users
     *
     * @param user the user that wants to logout
     * @throws RemoteException if there's a problem with the connection
     */
    @Override
    public void logout(User user) throws RemoteException
    {
        Iterator<User> usersIterator = users.iterator();
        while (usersIterator.hasNext()){
            User userIterator = usersIterator.next();
            if(userIterator.getUsername().equals(user.getUsername())){
                usersIterator.remove();
            }
        }
        publisherLobby.inform("lobby",null,users);
    }


    @Override
    public void connectWithGameserver(User user) throws RemoteException
    {
        users.add(user);
        publisherLobby.inform("lobby",null,users);
    }
}
