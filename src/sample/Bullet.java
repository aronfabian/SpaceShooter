package sample;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Bullet {
    private int x;
    private int y;
    private boolean isCraft;

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param isCraft =true if it belongs to a Craft
     */
    public Bullet(int x, int y, boolean isCraft) {
        this.x = x;
        this.y = y;
        this.isCraft = isCraft;
    }

    public void move() {
        if (isCraft == true) {
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
