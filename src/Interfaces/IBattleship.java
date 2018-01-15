package Interfaces;

import Logic.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Created by maxhe on 14-1-2018.
 */
public interface IBattleship extends Remote
{
    /**
     * Register the new user
     * @param username the username that will be inserted in the database
     * @param email the email that will be inserted in the database
     * @param password the password that will be inserted in the database
     * @throws RemoteException if there's a problem with the connection
     */
    void register(String username, String email, String password)throws RemoteException, SQLException;

    /**
     * Check if the user is in the database
     * @param email the email of the user that wants to sign in
     * @param password the password of the user that wants to sign in
     * @return the user with the email and password in the parameters
     * @throws RemoteException if there's a problem with the connection
     */
    User login(String email, String password)throws RemoteException;

    /**
     * Make a connection with a gameserver
     * @param user the user that wants to connect with the gameserver
     * @throws RemoteException if there's a problem with the connection
     */
    void connectWithGameserver(User user) throws RemoteException;
}
