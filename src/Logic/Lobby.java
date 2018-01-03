package Logic;

import GameServer.RMIGameClient;
import Interfaces.ILobby;
import Interfaces.IServerReference;
import fontyspublisher.RemotePublisher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Lobby extends UnicastRemoteObject implements IServerReference, ILobby, Serializable
{
    private List<User> users;
    private RemotePublisher publisher = null;

    public Lobby(RemotePublisher publisher) throws RemoteException{
        users = new ArrayList<>();
        this.publisher = publisher;
        publisher.registerProperty("lobby");
    }

    public void sendInvitation(){

    }

    public void acceptInvitation(){

    }

    public void declineInvitation(){

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
            System.out.println("user added");
        }
        catch (RemoteException e){
            System.out.println("GameClient: RemoteException: " + e.getMessage());
        }
    }

    /**
     * Gets a list of all players in this lobby
     * @return a list of users containing individual players
     */
    public ObservableList<User> getPlayers() throws RemoteException
    {
        return FXCollections.unmodifiableObservableList(FXCollections.observableList(users));
    }

}
