package gameelement;

/**
 * Created by Gyenes on 2017.05.01..
 */
public class WeaponGift extends Gift {
    private int weaponPow;


    public WeaponGift() {

        this.weaponPow = (int) ((Math.random() * 4) + 1); // az ajándék fegyvererősség 1-5 között
    }
}
