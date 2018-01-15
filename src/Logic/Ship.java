package Logic;

import java.io.Serializable;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Ship implements Serializable
{
    private final String name;
    private final int size;

    public Ship(String name, int size){
        this.name = name;
        this.size = size;
    }

    public String getName(){return name;}
    public int getSize(){return size;}
}
