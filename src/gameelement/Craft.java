package gameelement;

import java.io.Serializable;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Craft extends GameElement {

    private int dx;
    private int score;
    private int hp;
    private int bulletPower;
    public static final int WIDTH = 72;
    public static final int HEIGHT = 71;
    public static final int MAXHP = 3;


    public Craft(int x, int y, int score, int hp, int bulletPower) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.hp = hp;
        this.bulletPower = bulletPower;
    }

    public Craft() {
        x = MAXSREENWITH / 2;
        y = 770;
        score = 0;
        hp = 3;
        bulletPower = 1;
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
        if (hp <= MAXHP) {
            this.hp = hp;
        }
    }


    public int getBulletPower() {
        return bulletPower;
    }


    public int getCenterX() {
        return getX() + WIDTH / 2;
    }

    public int getCenterY() {
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

    public void setBulletPower(int bulletPower) {
        this.bulletPower = bulletPower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Craft craft = (Craft) o;

        if (bulletPower != craft.bulletPower) return false;
        if (dx != craft.dx) return false;
        if (score != craft.score) return false;
        return hp == craft.hp;
    }

    @Override
    public int hashCode() {
        int result = bulletPower;
        result = 31 * result + dx;
        result = 31 * result + score;
        result = 31 * result + hp;
        return result;
    }

    @Override
    public String toString() {
        return "Craft{" +
                "bulletPower=" + bulletPower +
                ", dx=" + dx +
                ", score=" + score +
                ", hp=" + hp +
                ", x=" + x +
                '}';
    }
}
