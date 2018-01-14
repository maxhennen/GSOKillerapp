package Client;

import GameServer.RMIGameClient;
import Interfaces.IBattleship;
import Logic.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Stage stage;

    private User user;

    //Panes
    private AnchorPane loginScreen;
    private AnchorPane chooseModeScreen;
    private AnchorPane lobbyScreen;

    //Scenes
    private Scene loginScene;
    private Scene chooseModeScene;
    private Scene lobbyScene;

    // Nodes loginscreen
    private TextField tfLoginName;
    private PasswordField tfLoginPassword;
    private Button btnLogin;
    private TextField tfRegisterName;
    private TextField tfRegisterEmail;
    private PasswordField tfRegisterPassword;
    private PasswordField tfRegisterConfirm;
    private Button btnRegister;

    //Nodes chooosemodescreen
    private Button btnnormalModus;
    private Button btnbigModus;
    private Button btnsmallModus;
    private Label lblChooseModus;

    //RMICentraleServer
    private static final String bindingNameCentraleserver = "data";

    private Registry registry = null;
    private IBattleship battleship = null;
    private RMIGameClient gameClient = null;


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
                battleship = (IBattleship) registry.lookup(bindingNameCentraleserver);
                System.out.println("Client: lookup");
            }
            catch (RemoteException ex){
                System.out.println("Client: RemoteException: " + ex.getMessage());
            }
            catch (NotBoundException ex){
                System.out.println("Client: No beurs binded to registry." + ex.getMessage());
            }
        }
    }

    public IBattleship getData(){return battleship;}


    @Override
    public void start(Stage primaryStage) throws RemoteException {
        // Welcome message
        System.out.println("CLIENT USING REGISTRY");

        // Create client
        CreateRMIClient(getProperties());

        if(battleship != null)
        {

            stage = primaryStage;

            //initialize screens
            loginScreen = new AnchorPane();
            chooseModeScreen = new AnchorPane();
            lobbyScreen = new AnchorPane();

            setupControls();

            loginScene = new Scene(loginScreen, 1000, 300);
            chooseModeScene = new Scene(chooseModeScreen, 1000, 300);
            lobbyScene = new Scene(lobbyScreen, 1000, 300);

            primaryStage.setTitle("Battleship");
            primaryStage.setScene(loginScene);
            primaryStage.show();
            primaryStage.toFront();
        }
        else {
            JOptionPane.showMessageDialog(null,"Cannot connect with server");
        }
    }

    private void setupControls(){
        // set loginscreen
        tfLoginName = new TextField();
        tfLoginName.setLayoutX(50);
        tfLoginName.setLayoutY(50);
        tfLoginName.setPrefHeight(27);
        tfLoginName.setPrefWidth(200);
        tfLoginName.setPromptText("Email");

        tfLoginPassword = new PasswordField();
        tfLoginPassword.setLayoutY(90);
        tfLoginPassword.setLayoutX(50);
        tfLoginPassword.setPrefWidth(200);
        tfLoginPassword.setPrefHeight(27);
        tfLoginPassword.setPromptText("Password");

        btnLogin = new Button();
        btnLogin.setLayoutX(50);
        btnLogin.setLayoutY(130);
        btnLogin.setPrefWidth(200);
        btnLogin.setPrefHeight(27);
        btnLogin.setText("Login");
        btnLogin.setOnAction(event -> login());

        tfRegisterName = new TextField();
        tfRegisterName.setLayoutX(500);
        tfRegisterName.setLayoutY(50);
        tfRegisterName.setPrefWidth(200);
        tfRegisterName.setPrefHeight(27);
        tfRegisterName.setPromptText("Username");

        tfRegisterEmail = new TextField();
        tfRegisterEmail.setLayoutX(500);
        tfRegisterEmail.setLayoutY(90);
        tfRegisterEmail.setPrefWidth(200);
        tfRegisterEmail.setPrefHeight(27);
        tfRegisterEmail.setPromptText("Email");

        tfRegisterPassword = new PasswordField();
        tfRegisterPassword.setLayoutY(130);
        tfRegisterPassword.setLayoutX(500);
        tfRegisterPassword.setPrefHeight(27);
        tfRegisterPassword.setPrefWidth(200);
        tfRegisterPassword.setPromptText("Password");

        tfRegisterConfirm = new PasswordField();
        tfRegisterConfirm.setLayoutX(500);
        tfRegisterConfirm.setLayoutY(170);
        tfRegisterConfirm.setPrefWidth(200);
        tfRegisterConfirm.setPrefHeight(27);
        tfRegisterConfirm.setPromptText("Password Confirmation");

        btnRegister = new Button();
        btnRegister.setLayoutX(500);
        btnRegister.setLayoutY(210);
        btnRegister.setPrefWidth(100);
        btnRegister.setPrefHeight(27);
        btnRegister.setText("Register");
        btnRegister.setOnAction(event -> register());

        loginScreen.getChildren().addAll(tfLoginName, tfLoginPassword, btnLogin, tfRegisterName, tfRegisterEmail, tfRegisterPassword, tfRegisterConfirm, btnRegister);
        //end set loginscreen

        // set choosemodescreen
        btnsmallModus = new Button();
        btnsmallModus.setLayoutY(128);
        btnsmallModus.setLayoutX(75);
        btnsmallModus.setPrefHeight(115);
        btnsmallModus.setPrefWidth(108);
        btnsmallModus.setText("Small\n 8x8");

        btnnormalModus = new Button();
        btnnormalModus.setLayoutY(128);
        btnnormalModus.setLayoutX(225);
        btnnormalModus.setPrefWidth(108);
        btnnormalModus.setPrefHeight(115);
        btnnormalModus.setText("Normal\n 10x10");
        btnnormalModus.setOnAction(event -> connectWithGameServer());

        btnbigModus = new Button();
        btnbigModus.setLayoutY(128);
        btnbigModus.setLayoutX(375);
        btnbigModus.setPrefHeight(115);
        btnbigModus.setPrefWidth(108);
        btnbigModus.setText("Big\n12x12");

        lblChooseModus = new Label();
        lblChooseModus.setLayoutY(51);
        lblChooseModus.setLayoutX(75);
        lblChooseModus.setPrefHeight(57);
        lblChooseModus.setPrefWidth(259);
        lblChooseModus.setText("Choose gamemodus");
        lblChooseModus.setStyle("-fx-font-size: 28");

        chooseModeScreen.getChildren().addAll(btnsmallModus,btnnormalModus,btnbigModus, lblChooseModus);
        //end set choosemodusscreen
    }

    private void login(){
        try
        {
            this.user = battleship.login(MD5Hash(tfLoginName.getText()), MD5Hash(tfLoginPassword.getText()));
        } catch (RemoteException e)
        {
            System.out.println("Client: RemoteException: " + e.getMessage());
        }

        if(this.user != null){
            stage.setTitle("Choose gamemodus");
            stage.setScene(chooseModeScene);
        }
        else{
            JOptionPane.showMessageDialog(null,"Email/ password are not correct. Try again.");
        }
    }

    private void register(){
        try
        {
            if (tfRegisterPassword.getText().length() >= 6)
            {
                if (tfRegisterPassword.getText().equals(tfRegisterConfirm.getText()))
                {
                    battleship.register(tfRegisterName.getText(),MD5Hash(tfRegisterEmail.getText()),MD5Hash(tfRegisterPassword.getText()));
                }
                else {
                    JOptionPane.showMessageDialog(null,"Passwordfields don't match!");
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"Password is to short!");
            }
        } catch (RemoteException e)
        {
            System.out.println("Client: RemoteException: " + e.getMessage());
            JOptionPane.showMessageDialog(null,"Connection lost with server!");
        }
    }

    private void connectWithGameServer(){
        try
        {
            Properties properties = getProperties();
            String ip = properties.getProperty("ipAddress");
            stage.close();
            gameClient = new RMIGameClient(ip,1101,1102, this.user);
            battleship.connectWithGameserver(this.user);
            System.out.println("Client: Connected with gameserver");
        }
        catch (RemoteException e){
            System.out.println("Client: RemoteException: " + e.getMessage());
        }
    }

    public static Properties getProperties()
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

    public String MD5Hash(String hash){
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(hash.getBytes(), 0, hash.length());
            return new BigInteger(1,m.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }
}
