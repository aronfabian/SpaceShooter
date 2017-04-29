package sample;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Craft {
    private int dx;
    private int x;

    public Craft(int x) {
        this.x = x;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void move() {
        this.x += dx;
    }

    public int getDx() {
        return dx;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
