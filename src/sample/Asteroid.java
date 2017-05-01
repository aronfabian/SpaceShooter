package sample;

public class Asteroid extends Enemy {

    private int dy;
    private static int MAXSREENWITH = 600;

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

    public void move() {
        this.y += dy;
    }

}


