package Interfaces;

import Logic.Tile;
import Logic.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by maxhe on 5-1-2018.
 */
public interface IGame extends Remote
{
    /**
     * Create a game between two players
     * @param playerOne the receiver who accepted the invitation
     * @param playerTwo the sender of the invitation
     * @throws RemoteException if there's a problem with the connection
     */
    void createGame(User playerOne, User playerTwo) throws RemoteException;

    /**
     * Gives player one
     * @return player one
     * @throws RemoteException if there's a problem with the connection
     */
    User getPlayerOne() throws RemoteException;

    /**
     * Gives player two
     * @return player two
     * @throws RemoteException if there's a connection error
     */
    User getPlayerTwo() throws RemoteException;

    /**
     * Gives the tiles of player one back
     * @return a list of tiles
     * @throws RemoteException if there's a problem with the connection
     */
    ArrayList<Tile> getTilesPlayerOne() throws RemoteException;

    /**
     * Gives the tiles of player two back
     * @return a list of tiles
     * @throws RemoteException if there's a problem with the connection
     */
    ArrayList<Tile> getTilesPlayerTwo() throws RemoteException;

    /**
     * Initialize randomly which player can start and change the players turn after every move
     * @throws RemoteException if there's a problem with the connection
     */
    void setPlayerTurn() throws RemoteException;

    /**
     * Gives back the player which turn it is
     * @return player one or player two depends on which one has the turn
     * @throws RemoteException if there's a problem with the connection
     */
    User getPlayerTurn() throws RemoteException;

    /**
     * Checks if there's a hit
     * @param x the x coordinate where the missile will land
     * @param y the y coordinate where the missile will land
     * @throws RemoteException if there's a problem with the connection
     */
    void launch(int x, int y) throws RemoteException;

    /**
     * Checks if the game is ended
     * @return true if a player hit every ship of the other player. Else it returns false
     * @throws RemoteException if there's a problem with the connection
     */
    boolean checkEndGame() throws RemoteException;
}
