package GameServer;

import Logic.User;
import fontyspublisher.RemotePublisher;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 14-1-2018.
 */
public class GameTest
{
    private Game game;
    @Before
    public void doThisBeforetest()
    {
        try
        {
            RemotePublisher gamePublisher = new RemotePublisher();
            game = new Game(gamePublisher);
            game.createGame(new User("playerone"),new User("playertwo"));

        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getPlayerOne() throws Exception
    {
        game.createGame(new User("playerone"),new User("playertwo"));
        assertEquals(game.getPlayerOne().getUsername(),"playerone");
    }

    @Test
    public void getPlayerTwo() throws Exception
    {
        assertEquals(game.getPlayerTwo().getUsername(),"playertwo");
    }

    @Test
    public void getTilesPlayerOne() throws Exception
    {
        assertEquals(100,game.getTilesPlayerOne().size());
    }

    @Test
    public void getTilesPlayerTwo() throws Exception
    {
        assertEquals(100,game.getTilesPlayerTwo().size());
    }

    @Test
    public void createGame() throws Exception
    {
        User playerone = new User("playerone");
        User playertwo = new User("playertwo");

        game.createGame(playerone,playertwo);
        assertEquals(game.getPlayerOne(),playerone);
        assertEquals(game.getPlayerTwo(),playertwo);
    }

    @Test
    public void launch() throws Exception
    {
        setPlayerTurn();

        for(int x = 1; x < 100; x++){
            for(int y = 1; y < 100; y++){
                game.launch(x,y);
            }
        }
    }

    @Test
    public void checkEndGame() throws Exception
    {
    }

    @Test
    public void setPlayerTurn() throws Exception
    {
    }

    @Test
    public void getPlayerTurn() throws Exception
    {
    }

}