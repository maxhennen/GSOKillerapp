package Logic;

import Interfaces.IGame;
import Interfaces.ILobby;
import Interfaces.IServerReference;
import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.IRemotePublisherForListener;
import fontyspublisher.Publisher;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Game extends UnicastRemoteObject implements IGame, Serializable
{
    private int ID;

    private Timer timer;
    private Random random;

    private User playerOne;
    private User playerTwo;
    private User playerTurn;

    private ArrayList<Ship> shipsPlayerOne;
    private ArrayList<Ship> shipsPlayerTwo;
    private ArrayList<Tile> tilesPlayerOne;
    private ArrayList<Tile> tilesPlayerTwo;

    private int secondsTimer;

    private IRemotePublisherForDomain publisherGame = null;

    public Game(IRemotePublisherForDomain publisher) throws RemoteException{
        this.publisherGame = publisher;
        this.publisherGame.registerProperty("game");
        this.publisherGame.registerProperty("launch");
    }

    public User getPlayerOne() throws RemoteException
    {
        return playerOne;
    }

    public User getPlayerTwo() throws RemoteException
    {
        return playerTwo;
    }

    @Override
    public ArrayList<Tile> getTilesPlayerOne() throws RemoteException
    {
        return tilesPlayerOne;
    }

    @Override
    public ArrayList<Tile> getTilesPlayerTwo() throws RemoteException{
        return tilesPlayerTwo;
    }

    @Override
    public ArrayList<Ship> getShipsPlayerOne() throws RemoteException
    {
        return shipsPlayerOne;
    }

    @Override
    public ArrayList<Ship> getShipsPlayerTwo() throws RemoteException
    {
        return shipsPlayerTwo;
    }


    public boolean checkTime(){
        return false;
    }

    public boolean checkIfTilesHasShip(){
        return false;
    }

    @Override
    public void createGame(User playerOne, User playerTwo) throws RemoteException
    {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        setTilesPlayerOne();
        setTilesPlayerTwo();

        shipsPlayerOne = new ArrayList<>();
        shipsPlayerTwo = new ArrayList<>();

        givePlayersShips(shipsPlayerOne,tilesPlayerOne);
        givePlayersShips(shipsPlayerTwo,tilesPlayerTwo);


        timer = new Timer();
        timer();
    }

    public int timer(){
        secondsTimer = 30;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                secondsTimer = secondsTimer - 1;
            }
        }, 0, 1000);
        return secondsTimer;
    }

    public int getTimer()throws RemoteException{return secondsTimer;}

    @Override
    public void launch(int x, int y) throws RemoteException
    {
        Move move = new Move(playerTurn,x,y);
        if(playerTurn.getUsername().equals(playerOne.getUsername())){
            checkTilesLaunch(x,y,tilesPlayerTwo);
            publisherGame.inform("launch",null,move);
        }
        else {
            checkTilesLaunch(x,y,tilesPlayerOne);
            publisherGame.inform("launch",null,move);
        }
    }

    private void checkTilesLaunch(int x, int y, ArrayList<Tile> tiles){
        for(Tile tile : tiles){
            if(tile.getX() == x && tile.getY() == y){
                tile.setStatus(TileStatus.HIT);
            }
            else {
                tile.setStatus(TileStatus.MIS);
            }
        }
    }

    private void givePlayersShips(ArrayList<Ship> ships, ArrayList<Tile> tiles){
        ships.add(new Ship("Carrier",5));
        ships.add(new Ship("Battleship",4));
        ships.add(new Ship("Cruiser",3));
        ships.add(new Ship("Submarine",3));
        ships.add(new Ship("Destroyer",2));

        for(Ship ship: ships){
            random = new Random();
            int x = randomInt(ship.getSize());
            int y = randomInt(ship.getSize());
            boolean randomXofY = random.nextBoolean();


            while(true){
                if(randomXofY){
                    if(checkFreeTilesX(x,y,tiles,ship.getSize())){
                        giveShipTile(ship,randomXofY,x,y,tiles);
                        break;
                    }
                    else {
                        x = randomInt(ship.getSize());
                    }
                }

                else {
                    if(checkFreeTilesY(x,y,tiles,ship.getSize())){
                        giveShipTile(ship,randomXofY,x,y,tiles);
                        break;
                    }
                    else {
                        y = randomInt(ship.getSize());
                    }
                }
            }

        }
    }

    private boolean checkFreeTilesY(int x, int y, ArrayList<Tile> tiles, int size){
        for (Tile tile : tiles){
            for(int i = 0; i < size; i++){

                if(tile.getY() + i == y && tile.getX() == x && tile.getY() + i <= 9){
                    if (tile.getStatus() == TileStatus.SHIP)
                    {
                        return false;
                    }
                }

                else if(tile.getY() + i == y && tile.getX() == x){
                    if (tile.getStatus() == TileStatus.SHIP)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkFreeTilesX(int x, int y, ArrayList<Tile> tiles, int size){

        boolean check = true;


        for(int i = 0; i < size; i++) {
            for(Tile tile : tiles){
                if (tile.getX() + i == x && tile.getY() == y && tile.getX() + i <= 9){
                    if(tile.getStatus() == TileStatus.SHIP){
                        check = false;
                    }
                }

                else if(tile.getX() + i == x && tile.getY() == y ){
                    if(tile.getStatus() == TileStatus.SHIP){
                        check = false;
                    }
                }
            }
        }
        return check;
    }

    private int randomInt(int size){
        int randomInt = random.nextInt(10 - size - 1) + 1;
        return randomInt;
    }

    private void giveShipTile(Ship ship, boolean randomXofY, int x, int y, ArrayList<Tile> tiles){
        for(int i = 0; i < ship.getSize(); i++){

            if(randomXofY){
                x = x + 1;
            }

            else {
                y = y + 1;
            }

            for(Tile tile : tiles){
                if(tile.getY() == y && tile.getX() == x){
                    tile.setStatus(TileStatus.SHIP);
                }
            }
        }
        System.out.println(ship.getName() + x + y);
    }

    private void setTilesPlayerOne() throws RemoteException{
        tilesPlayerOne = new ArrayList<>();
        int x = 14;
        int y = 233;
        setTiles(tilesPlayerOne,x,y,true);
    }

    private void setTilesPlayerTwo() throws RemoteException{
        tilesPlayerTwo = new ArrayList<>();
        int x = 484;
        int y = 233;
        setTiles(tilesPlayerTwo,x,y,false);
    }

    private void setTiles(ArrayList<Tile> tiles, int x, int y,boolean playerOne) throws RemoteException{
        int counterX = 0;
        int counterY = 0;
        for(int i = 0; i < 100; i++){
            Tile tile = new Tile(x,y);

            x = x + 32;
            counterX++;
            if(playerOne)
            {
                if(counterX == 10)
                {
                    x = 14;
                    y = y + 32;
                    counterX = 0;
                    counterY = counterY + 1;
                }
            }
            else {
                if(counterX == 10)
                {
                    x = 484;
                    y = y + 32;
                    counterX = 0;
                    counterY = counterY + 1;
                }

            }
            tile.setX(counterX);
            tile.setY(counterY);
            tiles.add(tile);
        }
    }

    public void setPlayerTurn() throws RemoteException{
        if(playerTurn == null){
            random = new Random();
            boolean next = random.nextBoolean();
            if(next){
                playerTurn = playerOne;
            }
        }
        else{
            if(playerTurn == playerOne){
                playerTurn = playerTwo;
            }
            else {
                playerTurn = playerOne;
            }
        }
    }

    public User getPlayerTurn() throws RemoteException{return playerTurn;}
}
