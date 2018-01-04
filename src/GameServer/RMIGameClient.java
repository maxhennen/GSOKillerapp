package GameServer;

import Interfaces.ILobby;
import Logic.Battleship;
import Logic.Invitation;
import Logic.User;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.IRemotePublisherForListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by maxhe on 20-12-2017.
 */
public class RMIGameClient extends UnicastRemoteObject implements IRemotePropertyListener
{
    private User user;
    // Create bindingnames
    private static final String bindingNameLobby = "lobby";
    private static final String bindingNameGame = "game";
    private static final String bindingNamePublisher = "RemotePublisher";

    private Registry registry = null;
    private ILobby lobby = null;
    private IRemotePublisherForListener publisher;

    // Nodes
    private AnchorPane lobbySreen;
    private Stage stage;
    private Scene lobbyScene;
    private Label lblPlayers;
    private ListView lstPlayers;
    private Button btnSend;
    private Label lblInvitations;
    private ListView lstInvitations;
    private Button btnAccept;
    private Button btnDecline;

    public RMIGameClient(String ipAddress, int portNr, Battleship battleship)throws RemoteException{
        setUpControls();
        createGameClient(ipAddress,portNr, battleship);
    }

    public IRemotePublisherForListener getPublisher(){return publisher;}

    private void createGameClient(String ipAddress, int portNr, Battleship battleship){
        this.user = battleship.getUser();

        // Print ip and port
        System.out.println("GameClient: IP: " + ipAddress);
        System.out.println("GameClient: Port: " + portNr);

        //Locate registry at ip and port
        try
        {
            registry = LocateRegistry.getRegistry(ipAddress,portNr);
            System.out.println("GameClient: Registry is located");
        }
        catch (RemoteException e){
            System.out.println("GameClient: Cannot locate registry");
            System.out.println("GameClient: RemoteException: " + e.getMessage());
        }

        if(registry != null){
            try{
                lobby = (ILobby) registry.lookup(bindingNameLobby);
                publisher = (IRemotePublisherForListener) registry.lookup(bindingNamePublisher);
                publisher.subscribeRemoteListener(this,"lobby");
                publisher.subscribeRemoteListener(this,"invitation");
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
        stage = new Stage();
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
        btnAccept.setText("Accept Invitation");

        btnDecline = new Button();
        btnDecline.setLayoutX(394);
        btnDecline.setLayoutY(287);
        btnDecline.setPrefWidth(136);
        btnDecline.setPrefHeight(31);
        btnDecline.setOnAction(event -> declineInvitation((Invitation) lstInvitations.getSelectionModel().getSelectedItem()));
        btnDecline.setText("Decline Invitation");

        lobbySreen.getChildren().addAll(lblPlayers,lblInvitations,lstInvitations,lstPlayers,btnAccept,btnDecline,btnSend);

        lobbyScene = new Scene(lobbySreen,600,400);
        stage.setTitle("Lobby");
        stage.setScene(lobbyScene);
        stage.show();
        stage.toFront();
    }


    /**
     * Inform listener about change of a property in the domain. On the basis
     * of the data provided by the instance of PropertyChangeEvent the observer
     * is synchronized with respect to the remote domain.
     *
     * @param evt PropertyChangeEvent @see java.beans.PropertyChangeEvent
     * @throws RemoteException
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                switch (evt.getPropertyName()){
                    case "lobby":
                        usersPropertyChange(evt);
                        return;
                    case "invitation":
                        invitationsPropertyChange(evt);
                        return;
                }
            }
        });
    }

    public void invitationsPropertyChange(PropertyChangeEvent evt){
        lstInvitations.getItems().clear();
        ObservableList<Invitation> invitations = FXCollections.observableList((List<Invitation>)evt.getNewValue());

        for (Invitation i:invitations)
        {
            if(this.user.getUsername().equals(i.getReceiver().getUsername())){
                lstInvitations.getItems().add(i);
            }
        }
    }

    public void usersPropertyChange(PropertyChangeEvent evt){
        lstPlayers.setItems(FXCollections.observableList((List<User>) evt.getNewValue()));
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
}
