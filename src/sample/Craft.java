package sample;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Craft {
    private int dx;
    private int x;
    private int y;
    private int score;

    public Craft(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
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

    public int getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
