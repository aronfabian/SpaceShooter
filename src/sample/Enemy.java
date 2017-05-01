package sample;

/**
 * Created by arons on 2017. 05. 01..
 */
public class Enemy extends GameElement {
    protected int hp;

    public Enemy(int x, int y, int hp) {
        super(x, y);
        this.hp = hp;
    }

    public Enemy() {
        super(0, 0);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
