package Logic;

import Interfaces.IGameListener;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Created by maxhe on 13-12-2017.
 */
public class User implements Serializable, IGameListener
{
    private String username;

    public User(String username){
        this.username = username;
    }

    public String getUsername(){return username;}

    public void sendInvitation(){

    };

    public void acceptInvitation(){

    }

    public void declineInvitation(){

    }

    public void placeShip(){

    }


    public boolean guessShip(){
        return false;
    }

    public void receiveInvitation(Invitation invitation){

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

    }
}
