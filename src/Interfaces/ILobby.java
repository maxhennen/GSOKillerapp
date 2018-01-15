package Interfaces;

import Logic.Invitation;
import Logic.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by maxhe on 20-12-2017.
 */
public interface ILobby extends Remote
{
    /**
     * Make a sends the invitation to the receiver and updates the invitation listview
     * @param invitation the invitation that will be send to the receiver
     * @throws RemoteException if there's a problem with the connection
     */
    void sendInvitation(Invitation invitation)throws RemoteException;

    /**
     * Removes the invitation from the invitation listview
     * @param invitation the invitation that will be declined
     * @throws RemoteException if there's a problem with the connection
     */
    void declineInvitation(Invitation invitation) throws RemoteException;

    /**
     * Accepts the invitation and creates a new game
     * @param invitation the invitation that will be accepted
     * @param game the interface from game where we can create a game
     * @throws RemoteException if there's a connection error
     */
    void acceptInvitation(Invitation invitation, IGame game) throws RemoteException;

    /**
     * Updates the users list after they got back from a game
     * @param user the user that will be added to the users listview
     * @throws RemoteException if there's a problem with the connection
     */
    void goBackToLobbyAfterGame(User user) throws RemoteException;

    /**
     * Removes user that logs out from list with users
     * @param user the user that wants to logout
     * @throws RemoteException if there's a problem with the connection
     */
    void logout(User user) throws RemoteException;

}
