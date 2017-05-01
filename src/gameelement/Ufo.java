package gameelement;

/**
 * Created by Gyenes on 2017.04.30..
 */
public class Ufo extends Enemy {

    private int dx;

    private static int MOVEPERIOD = 30;
    private static int MAXSREENWITH = 400;
    private static final int WIDTH=105;
    private static final int HEIGHT=63;
    private int periodCounter;
    private int dy;


    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Ufo() {
        this.x = (int) (Math.random() * MAXSREENWITH);
        this.y = -200;
        periodCounter = 0;
        dx = 10;
        dy = 10;
        hp = 1;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public int getCenterX()
    {
        return getX()+getWIDTH()/2;
    }
    public int getCenterY()
    {
        return getY()+getHEIGHT()/2;
    }
    public double getRadius() {
        return (Math.sqrt(Math.pow(getWIDTH() / 2, 2) + Math.pow(getHEIGHT() / 2, 2)));
    }

    public void move() {
        //TODO mozgás közben ne tudjon oldalra kimenni a képernyőről
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

