package Administration;

import GameServer.RMIGameClient;
import Logic.Invitation;
import Logic.User;
import fontyspublisher.IRemotePropertyListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by maxhe on 5-1-2018.
 */
public class LobbyAdmin extends UnicastRemoteObject implements IRemotePropertyListener
{
    private final RMIGameClient client;

    public LobbyAdmin(RMIGameClient client) throws RemoteException{
        this.client = client;
        client.getPublisherLobby().subscribeRemoteListener(this,"lobby");
        client.getPublisherLobby().subscribeRemoteListener(this,"invitation");
    }

    /**
     * Inform listener about change of a property in the domain. On the basis
     * of the data provided by the instance of PropertyChangeEvent the observer
     * is synchronized with respect to the remote domain.
     *
     * @param evt PropertyChangeEvent @see java.beans.PropertyChangeEvent
     * @throws RemoteException if there's a connection error
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                switch (evt.getPropertyName()){
                    case "lobby":
                        usersPropertyChange(evt);
                        return;
                    case "invitation":
                        invitationsPropertyChange(evt);
                        return;
                }
            }
        });
    }

    private void invitationsPropertyChange(PropertyChangeEvent evt){
        client.setLstInvitations(FXCollections.observableList((List<Invitation>)evt.getNewValue()));
    }

    private void usersPropertyChange(PropertyChangeEvent evt){
        client.setLstPlayers(FXCollections.observableList((List<User>) evt.getNewValue()));
    }
}
