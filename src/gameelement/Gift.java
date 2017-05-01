package gameelement;

/**
 * Created by Gyenes on 2017.05.01..
 */


public class Gift extends GameElement {

    protected int dy;


    public Gift(int x, int y, int dy) {
        super(x, y);
        this.dy = dy;
    }

    public Gift() {
        super(0, 0);
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void move() {
        y += dy;
    }
}
