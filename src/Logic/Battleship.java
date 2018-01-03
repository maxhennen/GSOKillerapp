package Logic;

import Client.RMIClient;
import com.sun.mail.iap.ConnectionException;
import fontyspublisher.IRemotePropertyListener;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Battleship extends UnicastRemoteObject implements Remote

{
    private RMIClient rmiClient;
    private User user;

    public Battleship(RMIClient rmiClient) throws RemoteException{
        this.rmiClient = rmiClient;
    }

    public User getUser(){return user;}

    public void stopSession(User user){

    }

    public void register(String username, String email, String password, String passwordConfirm) throws ConnectionException
    {
        try
        {
            if(password.length() >= 6)
            {
                if(password.equals(passwordConfirm))
                {
                    String emailMD5 = MD5Hash(email.toLowerCase());
                    String passwordMD5 = MD5Hash(password);
                    rmiClient.getData().register(username, emailMD5, passwordMD5);
                }
                else {
                    JOptionPane.showMessageDialog(null,"Passwordfields do not match!");
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"Passwordlength must be longer than 6 characters");
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Something went wrong. Try again later");
        }
    }

    public void login(String email, String password)throws ConnectionException{
        try
        {
            String emailMD5 = MD5Hash(email.toLowerCase());
            String passwordMD5 = MD5Hash(password);
            user = rmiClient.getData().login(emailMD5, passwordMD5);
            if(user != null){

            }
            else {
                JOptionPane.showMessageDialog(null,"Email/ password are not correct. Try again.");
            }
        }
        catch (RemoteException e){
            System.out.println("Client: RemoteException: " + e.getMessage());
        }
    }

    public void startGame(User playerOne, User playerTwo){

    }


    public String MD5Hash(String hash){
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(hash.getBytes(), 0, hash.length());
            return new BigInteger(1,m.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }
}
