package gameelement;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Bullet extends GameElement {
    private int bulletPow;
    private boolean isCraftBullet;
    private boolean destroyBullet;
    private static final int WIDTH=15;
    private static final int HEIGHT=15;

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param isCraftBullet =true if it belongs to a Craft
     */
    public Bullet(int x, int y, boolean isCraftBullet, int bulletPow, boolean destroyBullet) {
        this.x = x;
        this.y = y;
        this.isCraftBullet = isCraftBullet;
        this.bulletPow = bulletPow;
        this.destroyBullet = destroyBullet;
    }

    public void move() {
        if (isCraftBullet == true) {
            y -= 20;
        } else {
            y += 20;
        }
    }

    public int getBulletPow() {
        return bulletPow;
    }

    public void setBulletPow(int bulletPow) {
        this.bulletPow = bulletPow;
    }

    public boolean getIsCraftBullet() {
        return isCraftBullet;
    }

    public void setCraftBullet(boolean isCraftBullet) {
        this.isCraftBullet = isCraftBullet;
    }

    public boolean getDestroyBullet() {
        return destroyBullet;
    }

    public void setDestroyBullet(boolean destroyBullet) {
        this.destroyBullet = destroyBullet;
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
}
