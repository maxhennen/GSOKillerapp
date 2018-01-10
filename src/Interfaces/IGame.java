package Interfaces;

import Logic.Game;
import Logic.Ship;
import Logic.Tile;
import Logic.User;
import com.sun.org.apache.regexp.internal.RE;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by maxhe on 5-1-2018.
 */
public interface IGame extends Remote
{
    void createGame(User playerOne, User playerTwo) throws RemoteException;
    User getPlayerOne() throws RemoteException;
    User getPlayerTwo() throws RemoteException;
    ArrayList<Tile> getTilesPlayerOne() throws RemoteException;
    ArrayList<Tile> getTilesPlayerTwo() throws RemoteException;
    ArrayList<Ship> getShipsPlayerOne() throws RemoteException;
    ArrayList<Ship> getShipsPlayerTwo() throws RemoteException;
    void setPlayerTurn() throws RemoteException;
    User getPlayerTurn() throws RemoteException;
    int getTimer()throws RemoteException;
    void launch(int x, int y) throws RemoteException;
}
