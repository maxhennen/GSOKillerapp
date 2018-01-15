package CentraleServer;

import Interfaces.IBattleship;
import Logic.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Battleship extends UnicastRemoteObject implements IBattleship, Serializable

{
    private final IDatabaseReference context;
    private final CentralRMIServer server;
    private final HashMap<String,User> sessions;
    private static final int SESSIONIDLENGTH = 10;


    public Battleship(IDatabaseReference context, CentralRMIServer server) throws RemoteException{
        this.context = context;
        this.server = server;
        sessions = new HashMap<>();
    }

    public void register(String username, String email, String password) throws RemoteException, SQLException
    {
        try
        {
            context.register(username,email,password);
        }
        catch (RemoteException e)
        {
            System.out.println("Server: RemoteException: " + e.getMessage());
        }
    }

    public User login(String email, String password)throws RemoteException{
        try
        {
            User user = context.login(email,password);
            String sessionID = randomString(SESSIONIDLENGTH);
            sessions.put(sessionID,user);
            return user;

        }
        catch (RemoteException e){
            System.out.println("Client: RemoteException: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void connectWithGameserver(User user) throws RemoteException
    {
        server.connectWithGameserver(user);
    }


    private static String randomString(final int length) {

        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {

            int randomNumber = random.nextInt(8);

            char c = (char) (6 + randomNumber);

            sb.append(c);

        }

        return sb.toString();

    }
}
