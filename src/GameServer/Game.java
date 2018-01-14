package GameServer;

import Enums.TileStatus;
import Interfaces.IGame;
import Logic.*;
import fontyspublisher.IRemotePublisherForDomain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Game extends UnicastRemoteObject implements IGame, Serializable
{
    private Random random;

    private User playerOne;
    private User playerTwo;
    private User playerTurn = null;

    private ArrayList<Ship> shipsPlayerOne;
    private ArrayList<Ship> shipsPlayerTwo;
    private ArrayList<Tile> tilesPlayerOne;
    private ArrayList<Tile> tilesPlayerTwo;

    private ArrayList<Move> moves;

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
    public void createGame(User playerOne, User playerTwo) throws RemoteException
    {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;

        setPlayerTurn();

        setTilesPlayerOne();
        setTilesPlayerTwo();

        shipsPlayerOne = new ArrayList<>();
        shipsPlayerTwo = new ArrayList<>();

        givePlayersShips(shipsPlayerOne,tilesPlayerOne);
        givePlayersShips(shipsPlayerTwo,tilesPlayerTwo);

    }

    @Override
    public void launch(int x, int y) throws RemoteException
    {
        moves = new ArrayList<>();
        if(playerTurn.getUsername().equals(playerOne.getUsername())){

            if (checkTilesLaunch(x,y,tilesPlayerTwo))
            {
                moves.add(new Move(x,y,true, TileStatus.HIT));
            }
            else {
                moves.add(new Move(x,y,true,TileStatus.MIS));
            }
        }
        else {
            if(checkTilesLaunch(x,y,tilesPlayerOne)){
                moves.add(new Move(x,y,false,TileStatus.HIT));
            }
            else {
                moves.add(new Move(x,y,false,TileStatus.MIS));
            }
        }
        publisherGame.inform("launch",null,moves);
    }

    @Override
    public boolean checkEndGame() throws RemoteException
    {
        int counter = 0;
        if(playerTurn.getUsername().equals(playerOne.getUsername())){
            for(Tile tile : tilesPlayerTwo){
                if(tile.getStatus() == TileStatus.SHIP){
                    counter++;
                }
            }
        }
        else{
            for(Tile tile : tilesPlayerOne){
                if(tile.getStatus() == TileStatus.SHIP){
                    counter++;
                }
            }
        }
        System.out.println(counter);

        if(counter == 0){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkTilesLaunch(int x, int y, ArrayList<Tile> tiles){

        boolean check = false;

        for(Tile tile : tiles){

            if(tile.getX() == x && tile.getY() == y){
                if (tile.getStatus() == TileStatus.SHIP)
                {
                    tile.setStatus(TileStatus.HIT);
                    check = true;
                }
                else if(tile.getStatus() == TileStatus.WATER) {
                    tile.setStatus(TileStatus.MIS);
                    try
                    {
                        setPlayerTurn();
                    } catch (RemoteException e)
                    {
                        System.out.println("GameServer: RemoteException: " + e.getMessage());
                    }
                }
            }
        }

        return check;
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
    }

    private void setTilesPlayerOne() throws RemoteException{
        tilesPlayerOne = new ArrayList<>();
        int x = 50;
        int y = 270;
        setTiles(tilesPlayerOne,x,y,true);
    }

    private void setTilesPlayerTwo() throws RemoteException{
        tilesPlayerTwo = new ArrayList<>();
        int x = 510;
        int y = 270;
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
                    x = 50;
                    y = y + 32;
                    counterX = 0;
                    counterY = counterY + 1;
                }
            }
            else {
                if(counterX == 10)
                {
                    x = 510;
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
            else{
                playerTurn = playerTwo;
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
