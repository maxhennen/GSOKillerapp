package Administration;

import GameServer.RMIGameClient;
import Logic.Move;
import Logic.Tile;
import Logic.User;
import fontyspublisher.IRemotePropertyListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by maxhe on 5-1-2018.
 */
public class GameAdmin extends UnicastRemoteObject implements IRemotePropertyListener
{

    private RMIGameClient client;

    public GameAdmin(RMIGameClient client)throws RemoteException{
        this.client = client;
        client.getPublisherGame().subscribeRemoteListener(this,"game");
        client.getPublisherGame().subscribeRemoteListener(this,"launch");
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
                        return;
                }
            }
        });
    }

    private void startNewGame(PropertyChangeEvent evt){
        client.gameSetup();
    }

    private void launch(PropertyChangeEvent evt){
        ArrayList<Move> moves = (ArrayList<Move>) evt.getNewValue();
        System.out.println("checkkkk");
        client.setTileHitMis(moves.get(0));
    }
}
