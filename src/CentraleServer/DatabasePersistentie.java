package CentraleServer;

import Logic.User;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

/**
 * Created by maxhe on 6-12-2017.
 */
public class DatabasePersistentie extends UnicastRemoteObject implements IDatabaseReference
{
    private static final long serialVersionUID = 1L;
    private Connection conn;
    private PreparedStatement prep;

    public DatabasePersistentie() throws RemoteException{

    }

    private void getConnection(){
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://Max;databaseName=GSOKillerapp;integratedSecurity=true");
        } catch (ClassNotFoundException e)
        {
            System.out.println("Server: cannot connect with database");
            System.out.println("Server: ClassFoundException: " + e.getMessage());
        }
        catch (SQLException e)
        {
            System.out.println("Server: cannot connect with database");
            System.out.println("Server: SQLException: " + e.getMessage());
        }
    }

    @Override
    public User login(String email, String password)throws RemoteException
    {
        System.out.println(email + "--" + password);
        User user = null;
        try
        {
            getConnection();
            String query = "SELECT id, name FROM Users WHERE email = ? AND password = ?;";
            prep = conn.prepareStatement(query);
            prep.setString(1,email);
            prep.setString(2,password);
            ResultSet results = prep.executeQuery();

            while (results.next()){
                user = new User(results.getString("name"));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Server: SQLException: " + e.getMessage());
        }
        finally
        {
            try
            {
                conn.close();
            } catch (SQLException e)
            {
                System.out.println("Server: SQLException: " + e.getMessage());
            }
        }
        return user;
    }

    @Override
    public void register(String username, String email, String password)throws RemoteException
    {
        try{
            getConnection();
            String query = "INSERT INTO Users(name,email,password)VALUES(?,?,?);";
            prep = conn.prepareStatement(query);
            prep.setString(1,username);
            prep.setString(2,email);
            prep.setString(3,password);
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null,"You're registrated. You can now login");
        }
        catch (SQLException e){
            System.out.println("Server: SQLException: " + e.getMessage());
            JOptionPane.showMessageDialog(null,"Email is already in use");
        }
        finally
        {
            try
            {
                conn.close();
            } catch (SQLException e)
            {
                System.out.println("Server: SQLException: " + e.getMessage());
            }
        }
    }
}
