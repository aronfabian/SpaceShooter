package gameelement;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Craft extends GameElement {
    private int dx;
    private int score;
    private int hp;
    public static final int WIDTH = 72;
    public static final int HEIGHT = 71;

    public Craft(int x, int y, int score, int hp) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.hp = hp;
    }

    public Craft() {
        x = MAXSREENWITH / 2;
        y = 770;
        score = 0;
        hp = 3;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDx() {
        return dx;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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
        if (x < 0 && dx <= 0) {
            return;
        }
        if (x > MAXSREENWITH - WIDTH && dx >= 0) {
            return;
        }
        x += dx;
    }
}
