package gameelement;

/**
 * Created by Gyenes on 2017.05.01..
 */
public class WeaponGift extends Gift {
    private int weaponPow;


    public WeaponGift() {
        super();
        this.weaponPow = (int) ((Math.random() * 4) + 1); // az ajándék fegyvererősség 1-5 között
    }

    public int getWeaponPow() {
        return weaponPow;
    }

    public void setWeaponPow(int weaponPow) {
        this.weaponPow = weaponPow;
    }
}
