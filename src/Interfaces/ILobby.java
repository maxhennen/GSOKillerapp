package Interfaces;

import GameServer.RMIGameClient;
import Logic.Invitation;
import Logic.User;
import com.sun.org.apache.regexp.internal.RE;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by maxhe on 20-12-2017.
 */
public interface ILobby extends Remote
{
    void sendInvitation(Invitation invitation)throws RemoteException;
    void declineInvitation(Invitation invitation) throws RemoteException;
}
