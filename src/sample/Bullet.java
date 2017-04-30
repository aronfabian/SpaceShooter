package sample;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Bullet {
    private int x;
    private int y;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y -= 20;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
