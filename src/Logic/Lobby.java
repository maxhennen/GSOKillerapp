package Logic;

import Interfaces.IServerReference;
import Interfaces.IUserPublisher;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Lobby implements IUserPublisher, IServerReference
{
    /**
     * Register property. Register property at this publisher. From now on
     * listeners can subscribe to this property. Nothing changes in case given
     * property was already registered.
     *
     * @param property empty string not allowed
     * @throws RemoteException
     */
    @Override
    public void registerProperty(String property) throws RemoteException
    {

    }

    /**
     * Unregister property. Unregister property at this publisher. From now on
     * listeners subscribed to this property will not be informed on changes.
     * In case given property is null-String, all properties (except null) will
     * be unregistered.
     *
     * @param property registered property at this publisher
     * @throws RemoteException
     */
    @Override
    public void unregisterProperty(String property) throws RemoteException
    {

    }

    /**
     * Inform all listeners subscribed to property. All listeners subscribed
     * to given property as well as all listeners subscribed to null-String
     * are informed of a change of given property through a (remote) method
     * invocation of propertyChange(). In case given property is the null-String
     * all subscribed listeners are informed.
     *
     * @param property property is either null-String or is registered
     * @param oldValue original value of property at domain (null is allowed)
     * @param newValue new value of property at domain
     * @throws RemoteException
     */
    @Override
    public void inform(String property, Object oldValue, Object newValue) throws RemoteException
    {

    }

    /**
     * Obtain all registered properties. An unmodifiable list all properties
     * including the null property is returned.
     *
     * @return list of registered properties including null
     * @throws RemoteException
     */
    @Override
    public List<String> getProperties() throws RemoteException
    {
        return null;
    }

    public void sendInvitation(){

    }

    public void acceptInvitation(){

    }

    public void declineInvitation(){

    }

    public void receiveInvitation(Invitation invitation){

    }

    @Override
    public void addPlayer(User player)
    {

    }
}
