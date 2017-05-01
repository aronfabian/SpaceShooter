package sample;

/**
 * Created by Gyenes on 2017.04.30..
 */
public class Asteroid {
    private double x;
    private int y;
    private int dy;
    private int HP;
    private static int MAXSREENWITH = 320;


    public Asteroid() {
        x = Math.random() * MAXSREENWITH;
        y = -270;
        dy = 10;
        HP = 2;


    }

    public double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDy() {
        return dy;

    }


    public void setX(double x) {
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