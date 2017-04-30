package sample;

/**
 * Created by Gyenes on 2017.04.30..
 */
public class Asteroid {
    private int x;
    private int y;
    private int dy;
    private int HP;

    public Asteroid(int x, int y) {
        this.x = x;
        this.y = y;
        this.dy = 10;
        HP = 2;


    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDy() {
        return dy;

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void move() {
        this.y += dy;
    }
}