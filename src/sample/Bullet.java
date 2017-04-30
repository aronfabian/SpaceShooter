package sample;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Bullet {
    private int x;
    private int y;
    private boolean isCraftBullet;

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param isCraftBullet =true if it belongs to a Craft
     */
    public Bullet(int x, int y, boolean isCraftBullet) {
        this.x = x;
        this.y = y;
        this.isCraftBullet = isCraftBullet;
    }

    public void move() {
        if (isCraftBullet == true) {
            y -= 20;
        } else {
            y += 20;
        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
