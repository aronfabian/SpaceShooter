package sample;

import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements KeyListener {
    List<Craft> crafts = new ArrayList<>();
    List<Bullet> bullets = new ArrayList<>();
    Stage ps;

    public Controller(Stage ps) {
        this.ps = ps;
    }

    public void start(){
        GameView gameView = new GameView();
        gameView.setKeyListener(this);
        crafts.add(new Craft(50));
        try {
            gameView.build(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TimerTask processTask = new TimerTask() {
            @Override
            public void run() {
                for (Craft craft : crafts) {
                    craft.move();
                    System.out.println(craft.getX());
                    gameView.setCraft(craft.getX(),300);
                }
                gameView.clearBullets();
                for (Bullet bullet : bullets) {
                    bullet.move();
                    gameView.setBullets(bullet.getX(),bullet.getY());
                }

            }

        };
        Timer timer = new Timer();
        timer.schedule(processTask, 0, 50);
    }

    @Override
    public void spacePressed() {
        bullets.add(new Bullet());
    }

    @Override
    public void spaceReleased() {
        bullets.add(new Bullet());
    }

    @Override
    public void rightPressed() {
        crafts.get(0).setDx(10);
    }

    @Override
    public void leftPressed() {
        crafts.get(0).setDx(-10);
    }

    @Override
    public void rightReleased() {
        crafts.get(0).setDx(0);
    }

    @Override
    public void leftReleased() {
        crafts.get(0).setDx(0);
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}
