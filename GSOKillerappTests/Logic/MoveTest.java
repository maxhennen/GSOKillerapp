package Logic;

import Enums.TileStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 14-1-2018.
 */
public class MoveTest
{
    private Move move = null;

    @Before
    public void before(){
        move = new Move(4,2,true, TileStatus.SHIP);
    }

    @Test
    public void getTileID() throws Exception
    {
        assertEquals("4;2",move.getTileID());
    }

    @Test
    public void getPlayerOne() throws Exception
    {
        assertEquals(true,move.getPlayerOne());
    }

    @Test
    public void getStatus() throws Exception
    {
        assertEquals(TileStatus.SHIP,move.getStatus());
    }

}