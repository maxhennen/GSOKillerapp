package GameServer;

import Administration.GameAdmin;
import Administration.LobbyAdmin;
import Client.RMIClient;
import Enums.TileStatus;
import Interfaces.IGame;
import Interfaces.ILobby;
import Logic.*;
import fontyspublisher.IRemotePublisherForListener;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Created by maxhe on 20-12-2017.
 */
public class RMIGameClient
{
    private final User user;
    private LobbyAdmin lobbyAdmin;
    private GameAdmin gameAdmin;
    // Create bindingnames
    private static final String bindingNameLobby = "lobby";
    private static final String bindingNameGame = "game";
    private static final String bindingNamePublisherGame = "RemotePublisherGame";
    private static final String bindingNamePublisherLobby = "RemotePublisherLobby";

    private Registry registryLobby = null;
    private Registry registryGame = null;
    private IGame game = null;
    private ILobby lobby = null;
    private IRemotePublisherForListener publisherLobby;
    private IRemotePublisherForListener publisherGame;

    // Nodes lobbyscreen
    private AnchorPane lobbySreen;
    private Stage lobbyStage;
    private Scene lobbyScene;
    private Label lblPlayers;
    private ListView lstPlayers;
    private Button btnSend;
    private Label lblInvitations;
    private ListView lstInvitations;
    private Button btnAccept;
    private Button btnDecline;
    private Button btnLogout;

    // Nodes gamescreen
    private AnchorPane gameScreen;
    private Stage gameStage;
    private Scene gameScene;
    private Label lblPlayerOne;
    private Label lblPlayerTwo;
    private TextField tfTileCodeX;
    private TextField tfTileCodeY;
    private Button btnLaunch;
    private Label lblTimer;
    private ArrayList<javafx.scene.image.ImageView> imageViewsPlayerOne;
    private ArrayList<javafx.scene.image.ImageView> imageViewsPlayerTwo;

    public RMIGameClient(String ipAddress, int portNrLobby,int portNrGame, User user)throws RemoteException{
        this.user = user;
        setUpControls();
        createGameClientLobby(ipAddress,portNrLobby);
        createGameClientGame(ipAddress,portNrGame);
        lobbyAdmin = new LobbyAdmin(this);
        gameAdmin = new GameAdmin(this);

    }

    public IRemotePublisherForListener getPublisherLobby(){return publisherLobby;}
    public IRemotePublisherForListener getPublisherGame(){return publisherGame;}

    private void createGameClientGame(String ipAdress, int portNrGame){
        // Print ip and port
        System.out.println("GameClient: IP: " + ipAdress);
        System.out.println("GameClient: Port Game: " + portNrGame);


        //Locate registry at ip and port
        try
        {
            registryGame = LocateRegistry.getRegistry(ipAdress,portNrGame);
            System.out.println("GameClient: Registry is located");
        }
        catch (RemoteException e){
            System.out.println("GameClient: RemoteException: " + e.getMessage());
        }

        if(registryGame != null){
            try{
                game = (IGame) registryGame.lookup(bindingNameGame);
                publisherGame = (IRemotePublisherForListener) registryGame.lookup(bindingNamePublisherGame);
            }
            catch (RemoteException e){
                System.out.println("GameClient: Cannot bind game");
                System.out.println("GameClient: RemoteException: " + e.getMessage());
                game = null;
            }
            catch (NotBoundException e){
                System.out.println("GameClient: Cannot bind game");
                System.out.println("GameClient: NotBoundException: " + e.getMessage());
                game = null;
            }
            catch (NullPointerException e){
                System.out.println("GameClient: Cannot bind game");
                System.out.println("GameClient: NullpointerException:" + e.getMessage());
            }
        }
    }

    private void createGameClientLobby(String ipAddress, int portNrLobby){


        // Print ip and port
        System.out.println("GameClient: IP: " + ipAddress);
        System.out.println("GameClient: Port Lobby: " + portNrLobby);


        //Locate registry at ip and port
        try
        {
            registryLobby = LocateRegistry.getRegistry(ipAddress,portNrLobby);
            System.out.println("GameClient: Registry is located");
        }
        catch (RemoteException e){
            System.out.println("GameClient: Cannot locate registry");
            System.out.println("GameClient: RemoteException: " + e.getMessage());
        }

        if(registryLobby != null){
            try{
                lobby = (ILobby) registryLobby.lookup(bindingNameLobby);
                publisherLobby = (IRemotePublisherForListener) registryLobby.lookup(bindingNamePublisherLobby);
            }
            catch (RemoteException e){
                System.out.println("GameClient: Cannot bind lobby");
                System.out.println("GameClient: RemoteException: " + e.getMessage());
                lobby = null;
            }
            catch (NotBoundException e){
                System.out.println("GameClient: Cannot bind lobby");
                System.out.println("GameClient: NotBoundException: " + e.getMessage());
                lobby = null;
            }
            catch (NullPointerException e){
                System.out.println("GameClient: Cannot bind lobby");
                System.out.println("GameClient: NullpointerException:" + e.getMessage());
            }
        }
    }

    private void setUpControls(){
        lobbyStage = new Stage();

        // initialize nodes lobbyscreen
        lobbySreen = new AnchorPane();

        lblPlayers = new Label();
        lblPlayers.setLayoutX(14);
        lblPlayers.setLayoutY(14);
        lblPlayers.setPrefWidth(78);
        lblPlayers.setPrefHeight(21);
        lblPlayers.setText("Players:");

        lstPlayers = new ListView();
        lstPlayers.setLayoutY(42);
        lstPlayers.setLayoutX(14);
        lstPlayers.setPrefWidth(200);
        lstPlayers.setPrefHeight(349);

        btnSend = new Button();
        btnSend.setLayoutX(240);
        btnSend.setLayoutY(42);
        btnSend.setPrefWidth(132);
        btnSend.setPrefHeight(31);
        btnSend.setOnAction(event -> sendInvitation((User)lstPlayers.getSelectionModel().getSelectedItem()));
        btnSend.setText("Send Invitation");

        lblInvitations = new Label();
        lblInvitations.setLayoutY(121);
        lblInvitations.setLayoutX(242);
        lblInvitations.setPrefHeight(21);
        lblInvitations.setPrefWidth(242);
        lblInvitations.setText("Invitations: ");

        lstInvitations = new ListView();
        lstInvitations.setLayoutX(242);
        lstInvitations.setLayoutY(150);
        lstInvitations.setPrefHeight(114);
        lstInvitations.setPrefWidth(341);

        btnAccept = new Button();
        btnAccept.setLayoutY(287);
        btnAccept.setLayoutX(242);
        btnAccept.setPrefHeight(31);
        btnAccept.setPrefWidth(132);
        btnAccept.setOnAction(event -> acceptInvitation((Invitation)lstInvitations.getSelectionModel().getSelectedItem()));
        btnAccept.setText("Accept Invitation");

        btnDecline = new Button();
        btnDecline.setLayoutX(394);
        btnDecline.setLayoutY(287);
        btnDecline.setPrefWidth(136);
        btnDecline.setPrefHeight(31);
        btnDecline.setOnAction(event -> declineInvitation((Invitation) lstInvitations.getSelectionModel().getSelectedItem()));
        btnDecline.setText("Decline Invitation");

        btnLogout = new Button();
        btnLogout.setLayoutX(350);
        btnLogout.setLayoutY(20);
        btnLogout.setPrefWidth(200);
        btnLogout.setPrefHeight(31);
        btnLogout.setText("Logout");
        btnLogout.setOnAction(event -> logout());

        lobbySreen.getChildren().addAll(lblPlayers,lblInvitations,lstInvitations,lstPlayers,btnAccept,btnDecline,btnSend,btnLogout);

        lobbyScene = new Scene(lobbySreen,600,400);
        lobbyStage.setTitle("Lobby");
        lobbyStage.setScene(lobbyScene);
        lobbyStage.show();
        lobbyStage.toFront();

        //initialize nodes gamescreen

        gameScreen = new AnchorPane();
        gameScreen.setPrefHeight(609);
        gameScreen.setPrefWidth(953);

        tfTileCodeX = new TextField();
        tfTileCodeX.setLayoutY(22);
        tfTileCodeX.setLayoutX(14);
        tfTileCodeX.setPrefWidth(35);
        tfTileCodeX.setPrefHeight(31);

        tfTileCodeY = new TextField();
        tfTileCodeY.setLayoutY(22);
        tfTileCodeY.setLayoutX(60);
        tfTileCodeY.setPrefWidth(35);
        tfTileCodeY.setPrefHeight(31);

        btnLaunch = new Button();
        btnLaunch.setLayoutY(22);
        btnLaunch.setLayoutX(209);
        btnLaunch.setPrefHeight(31);
        btnLaunch.setPrefWidth(65);
        btnLaunch.setOnAction(event -> launch());
        btnLaunch.setText("Launch rocket");

        lblPlayerOne = new Label();
        lblPlayerOne.setLayoutX(14);
        lblPlayerOne.setLayoutY(177);
        lblPlayerOne.setPrefWidth(200);
        lblPlayerOne.setPrefHeight(21);

        lblPlayerTwo = new Label();
        lblPlayerTwo.setLayoutX(484);
        lblPlayerTwo.setLayoutY(177);
        lblPlayerTwo.setPrefHeight(21);
        lblPlayerTwo.setPrefWidth(200);

        lblTimer = new Label();
        lblTimer.setLayoutX(484);
        lblTimer.setLayoutY(20);
        lblTimer.setPrefWidth(200);
        lblTimer.setPrefHeight(21);

        gameScreen.getChildren().addAll(tfTileCodeX,tfTileCodeY,btnLaunch,lblPlayerOne,lblPlayerTwo,lblTimer);

        gameScene = new Scene(gameScreen);
        gameStage = new Stage();
        gameStage.setTitle(this.user.getUsername());
        gameStage.setScene(gameScene);
    }

    public void gameSetup(){

        try
        {
            if(this.user.getUsername().equals(game.getPlayerOne().getUsername()) || this.user.getUsername().equals(game.getPlayerTwo().getUsername())){
                try
                {
                    lblPlayerOne.setText(game.getPlayerOne().getUsername());
                    lblPlayerTwo.setText(game.getPlayerTwo().getUsername());

                    imageViewsPlayerOne = new ArrayList<>();
                    imageViewsPlayerTwo = new ArrayList<>();

                    setTiles(game.getTilesPlayerOne(),game.getPlayerOne().getUsername(),true);
                    setTiles(game.getTilesPlayerTwo(),game.getPlayerTwo().getUsername(),false);

                    setLabelsGame();

                    lobbyStage.close();
                    gameStage.show();
                    gameStage.toFront();
                } catch (RemoteException e)
                {
                    System.out.println("GameClient: RemoteException: " + e.getMessage());
                }
            }
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    private void setLabelsGame(){

        for (int i = 1; i < 11; i++){
            //labels x- as player one
            int playerOneX = 30 +(32 *i);
            Label labelX1 = new Label();
            labelX1.setLayoutY(233);
            labelX1.setLayoutX(playerOneX);
            labelX1.setPrefWidth(20);
            labelX1.setPrefHeight(20);
            labelX1.setText(String.valueOf(i));

            //labels y- as player one
            int playerOneY = 233 +(32 *i);
            Label labelY1 = new Label();
            labelY1.setLayoutY(playerOneY);
            labelY1.setLayoutX(14);
            labelY1.setPrefWidth(20);
            labelY1.setPrefHeight(20);
            labelY1.setText(String.valueOf(i));

            //labels x- as player two
            int playerTwoX = 484 +(32 *i);
            Label labelX2 = new Label();
            labelX2.setLayoutY(233);
            labelX2.setLayoutX(playerTwoX);
            labelX2.setPrefWidth(20);
            labelX2.setPrefHeight(20);
            labelX2.setText(String.valueOf(i));

            //labels y- as player two
            int playerTwoY = 233 +(32 *i);
            Label labelY2 = new Label();
            labelY2.setLayoutY(playerTwoY);
            labelY2.setLayoutX(484);
            labelY2.setPrefWidth(20);
            labelY2.setPrefHeight(20);
            labelY2.setText(String.valueOf(i));

            gameScreen.getChildren().addAll(labelX1,labelX2,labelY1,labelY2);
        }

    }

    private void setTiles(ArrayList<Tile> tiles, String username, boolean playerOne){

        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {

                for(Tile tile: tiles){
                    javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
                    imageView.setFitWidth(30);
                    imageView.setFitHeight(30);
                    imageView.setLayoutX(tile.getLayoutX());
                    imageView.setLayoutY(tile.getLayoutY());
                    imageView.setId(tile.getX() + ";" + tile.getY());
                    if (playerOne)
                    {
                        imageViewsPlayerOne.add(imageView);
                    }

                    else {
                        imageViewsPlayerTwo.add(imageView);
                    }

                    if(tile.getStatus() == TileStatus.SHIP){
                        if(user.getUsername().equals(username))
                        {
                            imageView.setImage(new Image("assets/ship.jpg"));
                        }
                        else {
                            imageView.setImage(new Image("assets/water.jpg"));
                        }
                    }

                    else {
                        imageView.setImage(new Image("assets/water.jpg"));
                    }

                    setBtnLaunch();

                    gameScreen.getChildren().add(imageView);
                }
            }
        });
    }

    private void setBtnLaunch(){
        try
        {
            if(!this.user.getUsername().equals(game.getPlayerTurn().getUsername())){
                btnLaunch.setDisable(true);
            }
            else {
                btnLaunch.setDisable(false);
            }
        } catch (RemoteException e)
        {
            System.out.println("GameClient: RemoteException: " + e.getMessage() );
        }
    }

    public void setTileHitMis(Move move){

        if(move.getPlayerOne()){
            loopImageViews(imageViewsPlayerTwo,move);
        }
        else{
            loopImageViews(imageViewsPlayerOne,move);
        }

        setBtnLaunch();
        endGame();
    }

    private void endGame(){
        try
        {
            if(game.checkEndGame()){
                if(game.getPlayerTurn().getUsername().equals(this.user.getUsername())){
                    JOptionPane.showMessageDialog(null,"Congratulations u have won the game!");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Unfortunately u have lost the game!");
                }

                lobby.goBackToLobbyAfterGame(this.user);
                gameStage.close();
                lobbyStage.show();
                lobbyStage.toFront();
            }


        } catch (RemoteException e)
        {
            System.out.println("GameClient: RemoteException: " + e.getMessage());
        }
    }

    private void loopImageViews(ArrayList<javafx.scene.image.ImageView> views, Move move){

        for(javafx.scene.image.ImageView imageView : views){
            if(imageView.getId().equals(move.getTileID())){
                if(move.getStatus() == TileStatus.HIT){
                    imageView.setImage(new Image("assets/hit.jpg"));
                }
                else{
                    imageView.setImage(new Image("assets/mis.jpg"));
                }
            }
        }
    }

    private void acceptInvitation(Invitation invitation){

        try
        {
            lobby.acceptInvitation(invitation, game);

        } catch (RemoteException e)
        {
            System.out.println("GameClient: RemoteException: " + e.getMessage());
        }
    }

    private void sendInvitation(User receiver){
        try
        {
            if(!this.user.getUsername().equals(receiver.getUsername()))
            {
                Invitation invitation = new Invitation(this.user, receiver);
                lobby.sendInvitation(invitation);
            }
            else {
                JOptionPane.showMessageDialog(null,"You can't send an invitation to yourself");
            }
        } catch (RemoteException e)
        {
            System.out.println("GameClient: RemoteException: " + e.getMessage());
        }
        catch (NullPointerException e){
            JOptionPane.showMessageDialog(null,"You didn't select a player");
        }
    }

    private void declineInvitation(Invitation invitation){
        try
        {
            lobby.declineInvitation(invitation);
        } catch (RemoteException e)
        {
            System.out.println("GameClient: RemoteException: " + e.getMessage());
        }
    }

    public void setLstInvitations(ObservableList<Invitation> invitations){
        lstInvitations.getItems().clear();

        for (Invitation i:invitations)
        {
                if(this.user.getUsername().equals(i.getReceiver().getUsername())){
                    lstInvitations.getItems().add(i);
                }
        }
    }

    private void launch(){

        int x = 0;
        int y = 0;

        try
        {
            x = Integer.valueOf(tfTileCodeX.getText());
            y = Integer.valueOf(tfTileCodeY.getText()) - 1;
        } catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null,"Only insert numbers, please!");
            System.out.println("GameClient: NumberFormatException: " + e.getMessage());
        }

        try
        {
            if(x > 0 && 10 >= x && y > 0 && 10 >= y){
                game.launch(x,y);
            }
            else{
                JOptionPane.showMessageDialog(null,"x and y values must between 1 and 10");
            }
        }

        catch (RemoteException e)
        {
            System.out.println("GameClient: RemoteException: " + e.getMessage());
        }
    }

    private void logout(){
        try
        {
            lobby.logout(this.user);
        } catch (RemoteException e)
        {
            System.out.println("GameClient: RemoteException: " + e.getMessage());
        }
        RMIClient client = new RMIClient();
        try
        {
            lobbyStage.close();
            client.start(new Stage());
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void setLstPlayers(ObservableList<User> users){
        lstPlayers.setItems(users);
    }
}
