package Logic;


/**
 * Created by maxhe on 13-12-2017.
 */
public class Tile
{
    private int X;
    private int Y;
    private String code;

    public Tile(int X, int Y){
        this.X = X;
        this.Y = Y;
        code = (Integer.toString(X)) + (Integer.toString(Y));
    }

    public String getCode(){return code;}
}
