package gameelement;

public class Asteroid extends Enemy {

    private int dy;
    private static int MAXSREENWITH = 600;
    private static final int WIDTH=54;
    private static final int HEIGHT=57;

    public Asteroid() {
        x = (int) (Math.random() * MAXSREENWITH);
        y = -270;
        dy = 10;
        hp = 2;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
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
        this.y += dy;
    }

}


