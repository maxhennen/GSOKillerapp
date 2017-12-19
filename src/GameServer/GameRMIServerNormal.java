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
    private static final int portNumber = 1100;

    // Set binding name for student administration
    private static final String bindingName = "reference";

    // References to registry and student administration
    private Registry registry = null;
    private Lobby lobby = null;
    private RemotePublisher publisher = null;

    // Constructor
    public GameRMIServerNormal() throws RemoteException {

        lobby = new Lobby();

        // Create registry at port number
        try {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on port number " + portNumber);
            System.out.println();
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Bind beurs using registry
        try {
            registry.rebind(bindingName, (IServerReference) lobby);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind data");
            System.out.println("Server: RemoteException: " + ex.getMessage());
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
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1)
            {
                System.out.println("Gameserver: Full list of IP addresses:");

                for (InetAddress allMyIp : allMyIps)
                {
                    System.out.println("    " + allMyIp);
                }
            }
        }
        catch (java.net.UnknownHostException ex)
        {
            System.out.println("Gameserver: Cannot get IP address of local host");
            System.out.println("Gameserver: UnknownHostException: " + ex.getMessage());
        }
    }
}
