package gameelement;

/**
 * Created by Gyenes on 2017.05.01..
 */
public class WeaponGift extends Gift {
    private int weaponPow;
    private static int MAXSREENWITH = 400;

    public WeaponGift() {
        dy = 10;
        this.x = (int) (Math.random() * MAXSREENWITH);
        this.y = -200;
        this.weaponPow = (int) ((Math.random() * 4) + 1); // az ajándék fegyvererősség 1-5 között
    }
}
