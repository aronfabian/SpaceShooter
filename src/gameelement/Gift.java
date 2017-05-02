package gameelement;

/**
 * Created by Gyenes on 2017.05.01..
 */


public class Gift extends GameElement {
    protected int dy;

    private static int MAXSREENWITH = 400;
    private boolean isExist;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;



    public Gift(int x, int y, int dy, boolean isExist) {
        super(x, y);
        this.dy = dy;
        this.isExist = isExist;
    }

    public Gift() {
        x = (int) (Math.random() * (MAXSREENWITH - WIDTH));
        y = -200;
        dy = 10;
        isExist = true;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
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


    public int getCenterX() {
        return getX() + WIDTH / 2;
    }

    public int getCenterY() {
        return getY() + HEIGHT / 2;
    }

    public double getRadius() {
        return (Math.sqrt(Math.pow(WIDTH / 2, 2) + Math.pow(HEIGHT / 2, 2)));
    }
}
