package sample;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Craft {
    private static final int LEFTSIDE = -320;
    private static final int RIGHTSIDE = 400;
    private int dx;
    private int x;
    private int y;
    private int score;
    private int HP;

    public Craft(int x, int y, int score, int HP) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.HP = HP;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void move() {
        if (x == LEFTSIDE && dx <= 0) {
            return;
        }
        if (x == RIGHTSIDE && dx >= 0) {
            return;
        }
        x += dx;
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

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}
