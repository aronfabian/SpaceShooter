package gameelement;

/**
 * Created by Gyenes on 2017.05.01..
 */
public class WeaponGift extends Gift {
    private int weaponPow;
    public static final int HEIGHT = 55;

    public WeaponGift() {
        super();
        this.weaponPow = (int) ((Math.random() * 4) + 1); // az ajándék fegyvererősség 1-5 között
    }
}
