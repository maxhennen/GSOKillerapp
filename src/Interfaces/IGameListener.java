package Interfaces;

import fontyspublisher.IRemotePropertyListener;

import java.rmi.Remote;

/**
 * Created by maxhe on 13-12-2017.
 */
public interface IGameListener extends IRemotePropertyListener, Remote
{
    /**
     *
     * @return false
     */
    boolean guessShip();

    /**
     *
     */
    void placeShip();
}
