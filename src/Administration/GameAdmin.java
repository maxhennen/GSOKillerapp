package Administration;

import GameServer.RMIGameClient;
import Logic.Move;
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

    private final RMIGameClient client;

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
        client.setTileHitMis(moves.get(0));
    }
}
