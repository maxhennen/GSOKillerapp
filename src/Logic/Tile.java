package Logic;


import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.Serializable;

/**
 * Created by maxhe on 13-12-2017.
 */
public class Tile implements Serializable
{
    private int layoutX;
    private int layoutY;
    private int X;
    private int Y;
    private TileStatus status = TileStatus.WATER;

    public Tile(int layoutX, int layoutY){
        this.layoutX = layoutX;
        this.layoutY = layoutY;
    }

    public void setStatus(TileStatus status){this.status = status;}
    public TileStatus getStatus(){return status;}

    public Integer getX(){return X;}
    public Integer getY(){return Y;}
    public void setX(int x){this.X = x;}
    public void setY(int y){this.Y = y;}
    public Integer getLayoutX(){return layoutX;}
    public Integer getLayoutY() {return layoutY;}
}
