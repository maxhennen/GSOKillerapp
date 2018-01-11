package Logic;

import java.io.Serializable;

/**
 * Created by maxhe on 11-1-2018.
 */
public class Move implements Serializable
{
    private String tileID;
    private boolean playerOne;
    private TileStatus status;

    public Move(int x, int y, boolean playerOne, TileStatus status){
        this.tileID = x + ";" + y;
        this.playerOne = playerOne;
        this.status = status;
    }

    public String getTileID()
    {
        return tileID;
    }

    public boolean getPlayerOne()
    {
        return playerOne;
    }

    public TileStatus getStatus()
    {
        return status;
    }
}
