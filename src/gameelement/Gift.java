package gameelement;

/**
 * Created by Gyenes on 2017.05.01..
 */


public class Gift extends GameElement {

    protected int dy;
    private static int MAXSREENWITH = 400;

    public Gift(int x, int y, int dy) {
        super(x, y);
        this.dy = dy;
    }

    public Gift() {
        x = (int) (Math.random() * MAXSREENWITH);
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
