package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller implements KeyListener {
    List<Craft> crafts = new ArrayList<>();
    List<Bullet> bullets = new ArrayList<>();
    List<Ufo> ufos = new ArrayList<>();
    List<Asteroid> asteroids = new ArrayList<>();

    Stage ps;
    boolean addBullet = false;
    boolean addUfoBullet = false;

    public Controller(Stage ps) {
        this.ps = ps;
    }

    public void start() {
        GameView gameView = new GameView();
        gameView.setKeyListener(this);
      
        crafts.add(new Craft(50, 300, 0));

        try {
            gameView.build(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), ev -> {
            updateCraft(gameView);
            updateBullet(gameView);
            updateAsteroid(gameView);
            updateUfo(gameView);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //4 másodpercenként megjelenik egy ufo (időzítő)
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(4), ev -> {
            ufos.add(new Ufo());
        }));
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();

        //2 másodpercenként megjelenik egy aszteroida (időzítő)
        Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
            asteroids.add(new Asteroid());
        }));
        timeline3.setCycleCount(Animation.INDEFINITE);
        timeline3.play();

        //2 másodpercenként lőnek az asztroidák (időzítő)
        Timeline timeline4 = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
            addUfoBullet = true;
        }));
        timeline4.setCycleCount(Animation.INDEFINITE);
        timeline4.play();




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

            gameView.drawCraft(craft.getX(), 300);
            if (addBullet == true) {
                bullets.add(new Bullet(craft.getX(), craft.getY(), true, 1, false));
            }
        }
    }

    private void AsteroidOutOfFrame(int edgeDistBottom) {
        for (Asteroid asteroid : asteroids) {
            if (asteroid.getY() > edgeDistBottom) {
                asteroid.setHP(0);
            }
        }
    }

    private void UfoOutOfFrame(int edgeDistBottom) {
        for (Ufo ufo : ufos) {
            if (ufo.getY() > edgeDistBottom) {
                ufo.setHP(0);
            }
        }
    }

    private void bulletOutOfFrame(int maximumDist) {
        for (Bullet bullet : bullets) {
            if (bullet.getY() < maximumDist) {
                bullet.setDestroyBullet(true);
            }
        }
    }

    private void updateAsteroid(GameView gameView) {
        gameView.clearAsteroids();
        AsteroidOutOfFrame(320);
        //remove asteroids
        Iterator<Asteroid> iter1 = asteroids.iterator();
        while (iter1.hasNext()) {
            Asteroid asteroid = iter1.next();
            if (asteroid.getHP() == 0) {
                iter1.remove();
            }

        }
        //move asteroids
        for (Asteroid asteroid : asteroids) {
            asteroid.move();
        }

        // ezt kell még megírni gameView.drawAsteroids(asteroids);
    }

    private void updateUfo(GameView gameView) {
        gameView.clearUfos();
        UfoOutOfFrame(320);
        //remove asteroids
        Iterator<Ufo> iter = ufos.iterator();
        while (iter.hasNext()) {
            Ufo ufo = iter.next();
            if (ufo.getHP() == 0) {
                iter.remove();
            }

        }
        //move ufos
        for (Ufo ufo : ufos) {
            ufo.move();
        }

        if (addUfoBullet == true) {
            Iterator<Ufo> iter2 = ufos.iterator();
            while (iter2.hasNext()) {
                Ufo ufo = iter2.next();

                bullets.add(new Bullet((int) ufo.getX(), (int) ufo.getY(), false, 1, false));
            }
        }
        addUfoBullet = false;
// ezt kell még megírni gameView.drawUfos(ufos);
        // ezt kell még megírni gameView.drawAsteroids(asteroids);
    }
    private void updateBullet(GameView gameView) {
        gameView.clearBullets();
        bulletOutOfFrame(-270);

        //remove bullets
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if (bullet.getDestroyBullet() == true) {
                iter.remove();
            }

        }

        //move bullets
        for (Bullet bullet : bullets) {
            bullet.move();
        }

        gameView.drawBullets(bullets);
    }

    private void bulletCollision(double threshDist) {
        for (Bullet bullet : bullets) {
            if (bullet.getisCraftBullet() == true)  //if bullet was shot by Craft
            {
                for (Ufo ufo : ufos) {
                    if (Math.sqrt(Math.pow(bullet.getX() - ufo.getX(), 2) + Math.pow(bullet.getY() - ufo.getX(), 2)) < threshDist)
                    //if bullet is close to Ufo
                    {
                        ufo.setHP(ufo.getHP() - 1); //decrease Ufo HP
                        bullet.setDestroyBullet(true); //destroy bullet flag=1
                        crafts.get(0).setScore(crafts.get(0).getScore() + 1); //increase player's score
                    }
                }

            } else //if bullet was shot by one of the Ufo
            {
                for (Asteroid asteroid : asteroids) {
                    if (Math.sqrt(Math.pow(bullet.getX() - asteroid.getX(), 2) + Math.pow(bullet.getY() - asteroid.getX(), 2)) < threshDist)
                    //if bullet is close to Ufo
                    {
                        asteroid.setHP(asteroid.getHP() - 1); //decrease Asteroid HP
                        bullet.setDestroyBullet(true); //destroy bullet flag=1
                        crafts.get(0).setScore(crafts.get(0).getScore() + 1); //increase player's score
                    }
                }
            }
        }
    }

    private void elementCollision() {

    }

}
