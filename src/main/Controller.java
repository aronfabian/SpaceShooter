package main;

import gameelement.*;
import gui.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller implements KeyListener {
    /*
        ufo width: 104.39999389648438
        ufo height: 63.0
        bullet width: 15.0
        bullet height: 14.699951171875
        asteroid width: 53.70001220703125
        asteroid height: 57.0
        craft width: 72.0
        craft height: 71.29998779296875
     */
    private static final int WINDOWBOTTOM = 800;
    private static final int WINDOWTOP = 0;
    private static final int BULLETOFFSET = 30;
    List<Craft> crafts = new ArrayList<>();
    List<Bullet> bullets = new ArrayList<>();
    List<Ufo> ufos = new ArrayList<>();
    List<Asteroid> asteroids = new ArrayList<>();
    List<Gift> gifts = new ArrayList<>();

    Stage ps;
    boolean addBullet = false;
    boolean addUfoBullet = false;

    public Controller(Stage ps) {
        this.ps = ps;
    }

    public void start() {
        GameView gameView = new GameView();
        gameView.setKeyListener(this);

        crafts.add(new Craft());

        try {
            gameView.build(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(80), ev -> {
            elementCollision();
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


        //15 másodpercenként megjelenik egy ajándék
        Timeline timeline5 = new Timeline(new KeyFrame(Duration.seconds(15), ev -> {
            if((Math.random()>0.5)) {
                gifts.add(new HpGift());
            }
            else
            {
                gifts.add(new WeaponGift());
            }

        }));
        timeline5.setCycleCount(Animation.INDEFINITE);
        timeline5.play();

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
            gameView.drawCraft(craft.getX(), craft.getY());

            if (addBullet == true) {
                //x+310, y+280: crat-bullet correction
                bullets.add(new Bullet(craft.getX() + 310, craft.getY() + 280, true, 1, false));
            }
            craft.move();
            gameView.drawHp(craft.getHp());
            gameView.drawScore(craft.getScore());
            gameView.drawCraft(craft.getX(), craft.getY());
        }
    }



    private void updateAsteroid(GameView gameView) {

        AsteroidOutOfFrame();
        //remove asteroids
        Iterator<Asteroid> iter1 = asteroids.iterator();
        while (iter1.hasNext()) {

            Asteroid asteroid = iter1.next();
            if (asteroid.getHp() == 0) {
                iter1.remove();
            }
        }
        //move asteroids
        for (Asteroid asteroid : asteroids) {
            asteroid.move();
        }
        gameView.drawAsteroids(asteroids);
    }

    private void updateUfo(GameView gameView) {

        UfoOutOfFrame();
        //remove asteroids
        Iterator<Ufo> iter = ufos.iterator();
        while (iter.hasNext()) {
            Ufo ufo = iter.next();
            if (ufo.getHp() == 0) {
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
                //x+120, y+95 : ufo-bullets corretction
                bullets.add(new Bullet((int) ufo.getX() + 120, (int) ufo.getY() + 95, false, 1, false));
            }
        }
        addUfoBullet = false;

        gameView.drawUfos(ufos);
    }

    private void updateBullet(GameView gameView) {

        bulletOutOfFrame();

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

    //outOfFrame functions
    private void bulletOutOfFrame() {
        for (Bullet bullet : bullets) {
            if (bullet.getY() < WINDOWTOP - BULLETOFFSET) {
                bullet.setDestroyBullet(true);
            }
            if (bullet.getY() > WINDOWBOTTOM) {
                bullet.setDestroyBullet(true);
            }
        }
    }
    private void AsteroidOutOfFrame() {
        for (Asteroid asteroid : asteroids) {
            if (asteroid.getY() > WINDOWBOTTOM) {
                asteroid.setHp(0);
            }
        }
    }

    private void UfoOutOfFrame() {
        for (Ufo ufo : ufos) {
            if (ufo.getY() > WINDOWBOTTOM) {
                ufo.setHp(0);
            }
        }
    }

    //collision functions
    private void elementCollision() {
        for (Bullet bullet : bullets) {
            if (bullet.getIsCraftBullet() == true)  //if bullet was shot by Craft
            {
                for (Ufo ufo : ufos) {
                    if (Math.sqrt(Math.pow(bullet.getCenterX() - ufo.getCenterX(), 2) + Math.pow(bullet.getCenterY() - ufo.getCenterY(), 2)) < ufo.getRadius()+bullet.getRadius())
                    //if bullet is close to Ufo
                    {
                        ufo.setHp(ufo.getHp() - bullet.getBulletPow()); //decrease Ufo HP
                        bullet.setDestroyBullet(true); //destroy bullet flag=1
                        crafts.get(0).setScore(crafts.get(0).getScore() + 20); //increase player's score
                    }
                }
                for (Asteroid asteroid : asteroids) {
                    if (Math.sqrt(Math.pow(bullet.getCenterX() - asteroid.getCenterX(), 2) + Math.pow(bullet.getCenterY() - asteroid.getCenterY(), 2)) < asteroid.getRadius()+bullet.getRadius())
                    //if bullet is close to Ufo
                    {
                        asteroid.setHp(asteroid.getHp() - bullet.getBulletPow()); //decrease Asteroid HP
                        bullet.setDestroyBullet(true); //destroy bullet flag=1
                        crafts.get(0).setScore(crafts.get(0).getScore() + 10); //increase player's score
                    }
                }
            } else //if bullet was shot by one of the Ufo
            {
                for (Craft craft : crafts) {
                    if (Math.sqrt(Math.pow(bullet.getX() - craft.getX(), 2) + Math.pow(bullet.getY() - craft.getY(), 2)) < craft.getRadius()+bullet.getRadius()) {
                        craft.setHp(craft.getHp() - bullet.getBulletPow()); //decrease Asteroid HP
                        bullet.setDestroyBullet(true); //destroy bullet flag=1
                    }
                }
            }
        }

        for (Craft craft : crafts) {
            for (Ufo ufo : ufos) {
                if (Math.sqrt(Math.pow(craft.getCenterX() - ufo.getCenterX(), 2) + Math.pow(craft.getCenterY() - ufo.getCenterY(), 2)) < craft.getRadius()+ufo.getRadius()) {
                    ufo.setHp(0); //Ufo destroyed
                    craft.setHp(craft.getHp() - 1); //decrease UFO HP
                }
            }
            for (Asteroid asteroid : asteroids) {
                if (Math.sqrt(Math.pow(craft.getCenterX() - asteroid.getCenterX(), 2) + Math.pow(craft.getCenterY() - asteroid.getCenterY(), 2)) < craft.getRadius()+asteroid.getRadius()) {
                    asteroid.setHp(0);//asteroid destroyed
                    craft.setHp(craft.getHp() - 1); //decrease UFO HP
                }
            }
        }
    }

}
