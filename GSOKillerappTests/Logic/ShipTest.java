package Logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by maxhe on 14-1-2018.
 */
public class ShipTest
{
    private Ship ship = null;

    @Before
    public void before(){
        ship = new Ship("Carrier",5);
    }

    @Test
    public void getName() throws Exception
    {
        assertEquals(ship.getName(),"Carrier");
    }

    @Test
    public void getSize() throws Exception
    {
        assertEquals(ship.getSize(),5);
    }

}