package gameelement;

public class Asteroid extends Enemy {

    private int dy;

    public static final int WIDTH = 53;
    public static final int HEIGHT = 56;

    public Asteroid() {
        x = (int) (Math.random() * (MAXSREENWITH - WIDTH));
        y = -60;
        dy = 10;
        hp = 1;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
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
        this.y += dy;
    }

}


