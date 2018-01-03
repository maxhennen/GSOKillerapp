package Interfaces;

import GameServer.RMIGameClient;
import Logic.User;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by maxhe on 20-12-2017.
 */
public interface ILobby extends Remote
{
    ObservableList<User> getPlayers() throws RemoteException;
}
