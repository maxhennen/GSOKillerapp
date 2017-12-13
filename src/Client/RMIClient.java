package Client;

import CentraleServer.DatabaseRepository;
import Interfaces.IData;
import Interfaces.IDatabaseReference;
import Logic.Battleship;
import Logic.User;
import com.sun.mail.iap.ConnectionException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Created by maxhe on 11-10-2017.
 */
public class RMIClient extends Application
{

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 300;

    private AnchorPane anchorPane;
    private TextField tfLoginName;
    private PasswordField tfLoginPassword;
    private Button btnLogin;

    private TextField tfRegisterName;
    private TextField tfRegisterEmail;
    private PasswordField tfRegisterPassword;
    private PasswordField tfRegisterConfirm;
    private Button btnRegister;

    private static final String bindingName = "Data";

    private Registry registry = null;
    private IData data = null;
    private Battleship battleship = null;


    private void CreateRMIClient(Properties properties){
        try {
            String ip = properties.getProperty("ipAddress");
            int portNr = Integer.parseInt(properties.getProperty("port"));

            registry = LocateRegistry.getRegistry(ip, portNr);
        } catch (RemoteException ex) {
            System.out.println("Client: Can't find registry " + ex.getMessage());
            registry = null;
        }

        if (registry != null){
            try{
                data = (IData) registry.lookup(bindingName);
                System.out.println("Client: lookup");
                battleship = new Battleship(this);
            }
            catch (RemoteException ex){
                System.out.println("Client: RemoteException: " + ex.getMessage());
            }
            catch (NotBoundException ex){
                System.out.println("Client: No beurs binded to registry." + ex.getMessage());
            }
        }
    }

    public IData getData(){return data;}


    @Override
    public void start(Stage primaryStage) throws RemoteException {
        // Welcome message
        System.out.println("CLIENT USING REGISTRY");

        // Create client
        CreateRMIClient(getConnectionProperties());

        setScene();
        Scene scene = new Scene(anchorPane, WIDTH, HEIGHT);

        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.toFront();
    }

    private void setScene(){
        anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(WIDTH);
        anchorPane.setPrefHeight(HEIGHT);

        tfLoginName = new TextField();
        tfLoginName.setLayoutX(50);
        tfLoginName.setLayoutY(50);
        tfLoginName.setPrefHeight(27);
        tfLoginName.setPrefWidth(200);
        tfLoginName.setPromptText("Email");
        anchorPane.getChildren().add(tfLoginName);

        tfLoginPassword = new PasswordField();
        tfLoginPassword.setLayoutY(90);
        tfLoginPassword.setLayoutX(50);
        tfLoginPassword.setPrefWidth(200);
        tfLoginPassword.setPrefHeight(27);
        tfLoginPassword.setPromptText("Password");
        anchorPane.getChildren().add(tfLoginPassword);

        btnLogin = new Button();
        btnLogin.setLayoutX(50);
        btnLogin.setLayoutY(130);
        btnLogin.setPrefWidth(200);
        btnLogin.setPrefHeight(27);
        btnLogin.setText("Login");
        btnLogin.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                try
                {
                    battleship.login(tfLoginName.getText(), tfLoginPassword.getText());
                } catch (ConnectionException e)
                {
                    System.out.println("Client: ConnectionException: " + e.getMessage());
                    JOptionPane.showMessageDialog(null,"Connection lost with server!");
                }
            }
        });
        anchorPane.getChildren().add(btnLogin);

        tfRegisterName = new TextField();
        tfRegisterName.setLayoutX(500);
        tfRegisterName.setLayoutY(50);
        tfRegisterName.setPrefWidth(200);
        tfRegisterName.setPrefHeight(27);
        tfRegisterName.setPromptText("Username");
        anchorPane.getChildren().add(tfRegisterName);

        tfRegisterEmail = new TextField();
        tfRegisterEmail.setLayoutX(500);
        tfRegisterEmail.setLayoutY(90);
        tfRegisterEmail.setPrefWidth(200);
        tfRegisterEmail.setPrefHeight(27);
        tfRegisterEmail.setPromptText("Email");
        anchorPane.getChildren().add(tfRegisterEmail);

        tfRegisterPassword = new PasswordField();
        tfRegisterPassword.setLayoutY(130);
        tfRegisterPassword.setLayoutX(500);
        tfRegisterPassword.setPrefHeight(27);
        tfRegisterPassword.setPrefWidth(200);
        tfRegisterPassword.setPromptText("Password");
        anchorPane.getChildren().add(tfRegisterPassword);

        tfRegisterConfirm = new PasswordField();
        tfRegisterConfirm.setLayoutX(500);
        tfRegisterConfirm.setLayoutY(170);
        tfRegisterConfirm.setPrefWidth(200);
        tfRegisterConfirm.setPrefHeight(27);
        tfRegisterConfirm.setPromptText("Password Confirmation");
        anchorPane.getChildren().add(tfRegisterConfirm);

        btnRegister = new Button();
        btnRegister.setLayoutX(500);
        btnRegister.setLayoutY(210);
        btnRegister.setPrefWidth(100);
        btnRegister.setPrefHeight(27);
        btnRegister.setText("Register");
        btnRegister.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                try
                {
                    battleship.register(tfRegisterName.getText(),tfRegisterEmail.getText(),tfRegisterPassword.getText(),tfRegisterConfirm.getText());
                } catch (ConnectionException e)
                {
                    System.out.println("Client: ConnectionException: " + e.getMessage());
                    JOptionPane.showMessageDialog(null,"Connection lost with server!");
                }
            }
        });
        anchorPane.getChildren().add(btnRegister);
    }


    public static Properties getConnectionProperties()
    {
        Properties properties = new Properties();

        File file = new File("properties/centraleserverprops.properties");
        try (InputStream inputStream = new FileInputStream(file))
        {
            properties.load(inputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return properties;
    }

    @Override
    public void stop() throws RemoteException {

    }
}

