package Administration;

import GameServer.RMIGameClient;
import Interfaces.IGame;
import Logic.Game;
import Logic.Invitation;
import Logic.Move;
import Logic.Tile;
import fontyspublisher.IRemotePropertyListener;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by maxhe on 5-1-2018.
 */
public class GameAdmin extends UnicastRemoteObject implements IRemotePropertyListener
{

    private RMIGameClient client;

    public GameAdmin(RMIGameClient client)throws RemoteException{
        this.client = client;
        client.getPublisherGame().subscribeRemoteListener(this,"game");
    }

    /**
     * Inform listener about change of a property in the domain. On the basis
     * of the data provided by the instance of PropertyChangeEvent the observer
     * is synchronized with respect to the remote domain.
     *
     * @param evt PropertyChangeEvent @see java.beans.PropertyChangeEvent
     * @throws RemoteException
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
                    case "game": startNewGame(evt);
                        return;
                    case "launch": launch(evt);
                }
            }
        });
    }

    private void startNewGame(PropertyChangeEvent evt){
        client.gameSetup();
    }
    private void launch(PropertyChangeEvent evt){
        Move move = (Move)evt.getNewValue();
        client.setTiles();
    }
}
