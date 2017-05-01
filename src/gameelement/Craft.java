package gameelement;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Craft extends GameElement {
    private static final int LEFTSIDE = -320;
    private static final int RIGHTSIDE = 400;
    private int dx;
    private int score;
    private int hp;

    public Craft(int x, int y, int score, int hp) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.hp = hp;
    }

    public Craft() {
        x = 50;
        y = 450;
        score = 0;
        hp = 3;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void move() {
        if (x == LEFTSIDE && dx <= 0) {
            return;
        }
        if (x == RIGHTSIDE && dx >= 0) {
            return;
        }
        x += dx;
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
}
