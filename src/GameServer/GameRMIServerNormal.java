/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameServer;

import CentraleServer.DatabasePersistentie;
import CentraleServer.DatabaseRepository;
import Interfaces.IData;
import Interfaces.IServerReference;
import Logic.Lobby;
import com.sun.jmx.remote.internal.RMIExporter;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.Publisher;
import fontyspublisher.RemotePublisher;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Example of RMI using Registry
 *
 * @author Nico Kuijpers
 */
public class GameRMIServerNormal extends Application{

    // Set port number
    private static final int portNumberReference = 1100;
    private static final int portNumberLobby = 1101;

    // Set binding name for reference with central server
    private static final String bindingNameReference = "reference";

    // References to registry and central server
    private Registry registryReference = null;
    private Lobby lobby = null;

    //Reference to registry and game client
    private Registry registryLobby = null;
    private static final String bindingNameLobby = "lobby";
    private static final String bindingNamePublisher = "RemotePublisher";
    private RemotePublisher publisher = null;


    // Constructor
    public GameRMIServerNormal() throws RemoteException {

        try
        {
            publisher = new RemotePublisher();
            lobby = new Lobby(publisher);
            System.out.println("lobby created");
            publisher.registerProperty("lobby");
        } catch (RemoteException e)
        {
            System.out.println("GameServer: Cannot create lobby");
            System.out.println("GameServer: RemoteException " + e.getMessage());
        }

        // Create registry at port number
        try {
            registryReference = LocateRegistry.createRegistry(portNumberReference);
            System.out.println("Server: Registry created on port number " + portNumberReference);
            System.out.println();
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registryReference = null;
        }

        // Bind beurs using registry
        try {
            registryReference.rebind(bindingNameReference, (IServerReference) lobby);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind data");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }

        try
        {
            registryLobby = LocateRegistry.createRegistry(portNumberLobby);
            System.out.println("GameServer: Registry created on portnumber " + portNumberLobby);
        }
        catch (RemoteException e){
            System.out.println("GameServer: Cannot create registry");
            System.out.println("GameServer: RemoteException: " + e.getMessage());
        }

        try
        {
            registryLobby.rebind(bindingNamePublisher,publisher);
        }
        catch (RemoteException e){
            System.out.println("GameServer: Cannot bind publisher");
            System.out.println("GameServer: RemoteException: " + e.getMessage());
        }

        try
        {
            registryLobby.rebind(bindingNameLobby,lobby);
        }
        catch (RemoteException e){
            System.out.println("GameServer: Cannot bind lobby");
            System.out.println("GameServer: RemoteException: " + e.getMessage());
        }

        printIPAddresses();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {

    }

    private static void printIPAddresses()
    {
        try
        {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Gameserver: IP Address: " + localhost.getHostAddress());
        }
        catch (java.net.UnknownHostException ex)
        {
            System.out.println("Gameserver: Cannot get IP address of local host");
            System.out.println("Gameserver: UnknownHostException: " + ex.getMessage());
        }
    }
}
