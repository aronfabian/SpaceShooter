package gameelement;

/**
 * Created by Gyenes on 2017.05.01..
 */


public class Gift extends GameElement {
    protected int dy;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;

    public Gift(int x, int y, int dy) {
        super(x, y);
        this.dy = dy;
    }

    public Gift() {
        x = (int) (Math.random() * (MAXSREENWITH - WIDTH));
        y = -200;
        dy = 10;
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
