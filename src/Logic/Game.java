package Logic;

import Interfaces.IGamePublisher;
import fontyspublisher.IRemotePropertyListener;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Timer;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Game extends UnicastRemoteObject implements IGamePublisher
{
    private int ID;
    private Timer timer;
    private User playerOne;
    private User playerTwo;
    private List<Ship> ships;
    private List<Tile> tiles;

    public Game(User playerOne, User playerTwo) throws RemoteException{
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public void setup(){

    }

    public boolean checkTime(){
        return false;
    }

    public boolean checkIfTilesHasShip(){
        return false;
    }

    /**
     * Subscribe remote property listener. Remote listener will be subscribed
     * to given property. In case given property is the null-String, the listener
     * will be subscribed to all properties.
     *
     * @param listener remote property listener to be subscribed
     * @param property null-String allowed
     * @throws RemoteException
     */
    @Override
    public void subscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException
    {

    }

    /**
     * Unsubscribe remote property listener. Remote listener will be unsubscribed
     * from given property. In case given property is the null-string, the listener
     * will be unsubscribed from all properties.
     *
     * @param listener property listener to be unsubscribed
     * @param property null-String allowed
     * @throws RemoteException
     */
    @Override
    public void unsubscribeRemoteListener(IRemotePropertyListener listener, String property) throws RemoteException
    {

    }
}
