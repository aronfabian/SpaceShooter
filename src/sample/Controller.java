package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Controller implements KeyListener {
    List<Craft> crafts = new ArrayList<>();
    List<Bullet> bullets = new ArrayList<>();
    Stage ps;
    boolean addBullet = false;

    public Controller(Stage ps) {
        this.ps = ps;
    }

    public void start() {
        GameView gameView = new GameView();
        gameView.setKeyListener(this);
        crafts.add(new Craft(50, 400));
        try {
            gameView.build(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(80), ev -> {
            updateCraft(gameView);
            updateBullet(gameView);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    @Override
    public void spacePressed() {
        addBullet = true;
    }

    @Override
    public void spaceReleased() {
        addBullet = false;
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

    //functions


    private void updateCraft(GameView gameView) {
        for (Craft craft : crafts) {
            craft.move();

            gameView.drawCraft(craft.getX(), craft.getY());
            if (addBullet == true) {
                bullets.add(new Bullet(craft.getX(), craft.getY(), true));
            }
        }
    }

    private void updateBullet(GameView gameView) {
        boolean[] bulletOutOfFrame = new boolean[bullets.size()];
        int i = 0;
        gameView.clearBullets();
        for (Bullet bullet : bullets) {
            bullet.move();
            if (bullet.getY() < -270) {
                bulletOutOfFrame[i] = true;
            }
            i++;
            System.out.println(bullet.getY());

        }
        for (int j = 0; j < bullets.size(); j++) {
            if (bullets.size() > 0) {
                if (bulletOutOfFrame[j] == true) {
                    bullets.remove(j);
                }
            }
        }
        gameView.drawBullets(bullets);
    }


}
