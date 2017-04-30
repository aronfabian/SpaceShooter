package sample;

/**
 * Created by Gyenes on 2017.04.30..
 */
public class Ufo {
    private int x;
    private int y;
    private int dx;
    private static int MOVEPERIOD = 30;
    private int periodCounter;
    private int dy;

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Ufo(int x, int y) {
        this.x = x;
        this.y = y;
        periodCounter = 0;
        dx = 10;
        dy = 10;

    }

    public int getX() {
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

