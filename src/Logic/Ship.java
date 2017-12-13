package Logic;

import java.util.List;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Ship
{
    private String name;
    private int size;
    private List<Tile> tiles;

    public Ship(String name, int size){
        this.name = name;
        this.size = size;
    }

    public String getName(){return name;}
    public int getSize(){return size;}
}
