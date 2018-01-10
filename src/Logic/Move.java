package Logic;

/**
 * Created by maxhe on 10-1-2018.
 */
public class Move
{
    private User user;
    private int X;
    private int Y;

    public Move(User user, int X, int Y){
        this.user = user;
        this.X = X;
        this.Y = Y;
    }

    public User getUser(){return user;}

    public int getX()
    {
        return X;
    }

    public int getY()
    {
        return Y;
    }
}
