package gameelement;

import java.io.Serializable;

/**
 * Created by arons on 2017. 05. 01..
 */
public class GameElement implements Serializable {
    public static final int MAXSREENWITH = 800;

    protected int x;
    protected int y;


    public GameElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GameElement() {
        x = 10;
        y = 10;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
