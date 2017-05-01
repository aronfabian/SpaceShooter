package gameelement;

/**
 * Created by Gyenes on 2017.05.01..
 */
public class HpGift extends Gift {
    private static int MAXSREENWITH = 400;


    // itt mindig 1 lesz a az ajándék élet
    public HpGift() {
        dy = 10;
        this.x = (int) (Math.random() * MAXSREENWITH);
        this.y = -200;

    }
}
