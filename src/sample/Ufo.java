package sample;

/**
 * Created by Gyenes on 2017.04.30..
 */
public class Ufo {
    private double x;
    private int y;
    private int dx;
    private int HP;
    private static int MOVEPERIOD = 30;
    private static int MAXSREENWITH = 320;
    private int periodCounter;
    private int dy;

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Ufo() {
        this.x = Math.random() * MAXSREENWITH;
        this.y = -270;
        periodCounter = 0;
        dx = 10;
        dy = 10;
        HP = 4;

    }

    public double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }


    public void move() {

        if (periodCounter < 15) {
            x += dx;
        } else {
            x -= dx;
        }
        y += dy;
        periodCounter++;
        if (periodCounter == MOVEPERIOD) {
            periodCounter = 0;
        }
    }

}

