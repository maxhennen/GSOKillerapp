package Logic;

import Enums.TileStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 14-1-2018.
 */
public class TileTest
{
    private Tile tile = null;

    @Before
    public void before(){
        tile = new Tile(50,270);
    }

    @Test
    public void setStatus() throws Exception
    {
        tile.setStatus(TileStatus.MIS);
        assertEquals(TileStatus.MIS,tile.getStatus());
    }

    @Test
    public void getStatus() throws Exception
    {
        tile.setStatus(TileStatus.HIT);
        assertEquals(TileStatus.HIT,tile.getStatus());
    }

    @Test
    public void getX() throws Exception
    {
        tile.setX(1);
        assertEquals(new Long(1),new Long(tile.getX()));
    }

    @Test
    public void getY() throws Exception
    {
        tile.setY(1);
        assertEquals(new Long(1),new Long(tile.getY()));
    }

    @Test
    public void setX() throws Exception
    {
        tile.setX(1);
        assertEquals(new Long(1),new Long(tile.getX()));
    }

    @Test
    public void setY() throws Exception
    {
        tile.setY(1);
        assertEquals(new Long(1),new Long(tile.getY()));
    }

    @Test
    public void getLayoutX() throws Exception
    {
        assertEquals(new Long(50),new Long(tile.getLayoutX()));
    }

    @Test
    public void getLayoutY() throws Exception
    {
        assertEquals(new Long(270),new Long(tile.getLayoutY()));
    }

}