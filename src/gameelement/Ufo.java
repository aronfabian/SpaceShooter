package gameelement;

/**
 * Created by Gyenes on 2017.04.30..
 */
public class Ufo extends Enemy {

    private int dx;

    private static final int MOVEPERIOD = 30;
    public static final int WIDTH = 104;
    public static final int HEIGHT = 63;
    private int periodCounter;
    private int dy;


    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Ufo() {
        periodCounter = 0;
        dx = 5;
        dy = 5;
        hp = 4;
        this.x = (int) (Math.random() * (MAXSREENWITH - WIDTH - MOVEPERIOD * dx));
        this.y = -70;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }



    public int getCenterX()
    {
        return getX() + WIDTH / 2;
    }
    public int getCenterY()
    {
        return getY() + HEIGHT / 2;
    }
    public double getRadius() {
        return (Math.sqrt(Math.pow(WIDTH / 2, 2) + Math.pow(HEIGHT / 2, 2)));
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

