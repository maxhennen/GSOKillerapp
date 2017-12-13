/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CentraleServer;

import Interfaces.IData;
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
public class CentraleRMIServer extends Application{

    // Set port number
    private static final int portNumber = 1099;

    // Set binding name for student administration
    private static final String bindingName = "Data";

    // References to registry and student administration
    private Registry registry = null;
    private DatabaseRepository beurs = null;

    // Constructor
    public CentraleRMIServer() throws RemoteException {

        beurs = new DatabaseRepository(new DatabasePersistentie());

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
            registry.rebind(bindingName, (IData) beurs);
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
            System.out.println("Server: IP Address: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1)
            {
                System.out.println("Server: Full list of IP addresses:");

                for (InetAddress allMyIp : allMyIps)
                {
                    System.out.println("    " + allMyIp);
                }
            }
        }
        catch (java.net.UnknownHostException ex)
        {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }
    }
}
