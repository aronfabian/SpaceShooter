package sample;

/**
 * Created by arons on 2017. 04. 29..
 */
public class Bullet {
    private int x;
    private int y;
    private int bulletPow;
    private boolean isCraftBullet;
    private boolean destroyBullet;

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

    public void setCraftBullet(boolean craftBullet) {
        isCraftBullet = craftBullet;
    }

    public boolean getIsDestroyBullet() {
        return destroyBullet;
    }

    public void setDestroyBullet(boolean destroyBullet) {
        this.destroyBullet = destroyBullet;
    }

    public boolean getisCraftBullet() {
        return isCraftBullet;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }


}
