package main;

import gameelement.*;
import gui.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import network.ClientState;
import network.Network;
import network.NetworkClient;
import network.NetworkServer;

import java.io.*;
import java.util.*;

public class Controller implements KeyListener {

    private static final int WINDOWBOTTOM = 900;
    private static final int WINDOWTOP = 0;
    private static final int BULLETOFFSET = 30;
    private final List<Craft> crafts = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Ufo> ufos = new ArrayList<>();
    private final List<Asteroid> asteroids = new ArrayList<>();
    private final List<Gift> gifts = new ArrayList<>();

    private final Stage ps;
    private boolean addBullet = false;
    private boolean addUfoBullet = false;
    private boolean rightPressed;
    private boolean leftPressed;

    private Timeline timeline;
    private Timeline ufoTimer;
    private Timeline asteroidTimer;
    private Timeline ufoShootTimer;
    private Timeline giftTimer;
    private GameView gameView;
    private GameType gameType;
    private Network network;
    private ClientState clientState;

    public Controller(Stage ps) {
        this.ps = ps;
        gameType = GameType.SINGLEPLAYER;
        network = null;
        clientState = new ClientState();
    }

    public Controller(Stage ps, GameType gameType, String ip) {
        this.ps = ps;
        this.gameType = gameType;
        switch (gameType) {
            case SERVER:
                network = new NetworkServer(this);
                clientState = new ClientState();
                break;
            case CLIENT:
                network = new NetworkClient(this, ip);
                clientState = new ClientState();
                break;
            case SINGLEPLAYER:
                network = null;
                clientState = new ClientState();
                break;
        }
    }

    public List<Craft> getCrafts() {
        return crafts;
    }

    public void setCrafts(List<Craft> crafts) {
        this.crafts.clear();
        this.crafts.addAll(crafts);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets.clear();
        this.bullets.addAll(bullets);
    }

    public List<Ufo> getUfos() {
        return ufos;
    }

    public void setUfos(List<Ufo> ufos) {
        this.ufos.clear();
        this.ufos.addAll(ufos);
    }

    public List<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(List<Asteroid> asteroids) {
        this.asteroids.clear();
        this.asteroids.addAll(asteroids);
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts.clear();
        this.gifts.addAll(gifts);
    }

    public void start() {
        gameView = new GameView(ps);
        gameView.setKeyListener(this);
        switch (gameType) {
            case SINGLEPLAYER:
                crafts.add(new Craft());
                break;
            case CLIENT:
                crafts.add(new Craft(300, 770, 0, 3, 1)); //client's craft
                crafts.add(new Craft(500, 770, 0, 3, 1));
                network.connect();
                break;
            case SERVER:
                crafts.add(new Craft(300, 770, 0, 3, 1)); // server's craft
                crafts.add(new Craft(500, 770, 0, 3, 1));
                network.connect();
        }
        try {
            gameView.build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        timeline = new Timeline(new KeyFrame(Duration.millis(80), ev -> {
            switch (gameType) {
                case SERVER:
                    gameOverCheck();
                    elementCollision();
                    updateCraft();
                    updateBullet();
                    updateAsteroid();
                    updateUfo();
                    updateGift();
                    network.send();
                    break;
                case SINGLEPLAYER:
                    gameOverCheck();
                    elementCollision();
                    updateCraft();
                    updateBullet();
                    updateAsteroid();
                    updateUfo();
                    updateGift();
                    break;
                case CLIENT:
                    gameView.drawCraft(crafts);
                    gameView.drawAsteroids(asteroids);
                    gameView.drawBullets(bullets);
                    gameView.drawUfos(ufos);
                    gameView.drawGift(gifts);
                    gameView.drawHp(crafts.get(1).getHp());
                    gameView.drawScore(crafts.get(1).getScore());
                    network.send();
                    break;
            }

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //4 másodpercenként megjelenik egy ufo (időzítő)
        ufoTimer = new Timeline(new KeyFrame(Duration.seconds(8), ev -> ufos.add(new Ufo())));
        ufoTimer.setCycleCount(Animation.INDEFINITE);
        ufoTimer.play();

        //2 másodpercenként megjelenik egy aszteroida (időzítő)
        asteroidTimer = new Timeline(new KeyFrame(Duration.seconds(4), ev -> asteroids.add(new Asteroid())));
        asteroidTimer.setCycleCount(Animation.INDEFINITE);
        asteroidTimer.play();

        //2 másodpercenként lőnek az ufók (időzítő)
        ufoShootTimer = new Timeline(new KeyFrame(Duration.seconds(2), ev -> addUfoBullet = true));
        ufoShootTimer.setCycleCount(Animation.INDEFINITE);
        ufoShootTimer.play();


        //15 másodpercenként megjelenik egy ajándék
        giftTimer = new Timeline(new KeyFrame(Duration.seconds(20), ev -> {
            if ((Math.random() > 0.5)) {
                gifts.add(new HpGift());
            } else {
                gifts.add(new WeaponGift());
            }

        }));
        giftTimer.setCycleCount(Animation.INDEFINITE);
        giftTimer.play();

    }

    private void gameOverCheck() {
        for (Craft craft : crafts) {
            if (craft.getHp() <= 0) {
                // stop timers
                timeline.stop();
                ufoTimer.stop();
                asteroidTimer.stop();
                ufoShootTimer.stop();
                giftTimer.stop();
                // draw game over and get player name
                gameView.gameOver();
            }
        }
    }

    @Override
    public void highScoreName(String name) {
        System.out.println(name);
        Properties prop = new Properties();
        OutputStream output = null;
        InputStream input = null;
        List<Pair<String, Integer>> highScores = new ArrayList<>();

        try {
            input = new FileInputStream("scores.properties");
            String playerName;
            String score;
            int scoreInt;
            // load a properties file
            prop.load(input);
            for (int i = 1; i <= 10; i++) {
                playerName = prop.getProperty(i + "_name");
                score = prop.getProperty(i + "_score");
                if (score.length() == 0) {
                    scoreInt = 0;
                } else {
                    scoreInt = Integer.parseInt(score);
                }
                if (!(playerName.length() == 0)) {
                    highScores.add(i - 1, new Pair<>(playerName, scoreInt));
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        highScores.add(new Pair<>(name, crafts.get(0).getScore()));
        highScores.sort(new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });


        try {

            output = new FileOutputStream("scores.properties");

            // set the properties value
            for (int i = 0; (i < highScores.size()) && (i < 10); i++) {
                prop.setProperty((i + 1) + "_name", highScores.get(i).getKey());
                prop.setProperty((i + 1) + "_score", highScores.get(i).getValue().toString());
            }


            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isSpacePressed() {
        return addBullet;
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
        rightPressed = true;
        if (gameType != GameType.CLIENT) {
            crafts.get(0).setDx(10);
        }
    }

    @Override
    public void leftPressed() {
        leftPressed = true;
        if (gameType != GameType.CLIENT) {
            crafts.get(0).setDx(-10);
        }
    }

    @Override
    public void rightReleased() {
        rightPressed = false;
        if (gameType != GameType.CLIENT) {
            if (leftPressed) {
                crafts.get(0).setDx(-10);
            } else {
                crafts.get(0).setDx(0);
            }
        }
    }

    @Override
    public void leftReleased() {
        leftPressed = false;
        if (gameType != GameType.CLIENT) {
            if (rightPressed) {
                crafts.get(0).setDx(10);
            } else {
                crafts.get(0).setDx(0);
            }
        }

    }


    @Override
    public void exit() {
        System.exit(0);
    }

    //functions


    private void updateCraft() {
        for (Craft craft : crafts) {

            if ((addBullet && (crafts.indexOf(craft) == 0)) || (clientState.isSpacePressed() && (crafts.indexOf(craft) == 1))) {

                // craft-bullet correction
                bullets.add(new Bullet(craft.getX() + (Craft.WIDTH - Bullet.WIDTH) / 2, craft.getY() + Bullet.HEIGHT, true, 1, false, crafts.indexOf(craft)));

            }
            // TODO: csak teszt megoldás, meg kell csinálni rendesen
            if (crafts.indexOf(craft) == 1) {
                if (clientState.isLeftPressed()) {
                    craft.setDx(-10);
                }
                if (clientState.isRightPressed()) {
                    craft.setDx(10);
                }
                if (!clientState.isLeftPressed() && !clientState.isRightPressed()) {
                    craft.setDx(0);
                }
            }
            craft.move();
            if ((crafts.indexOf(craft) == 1) && (gameType == GameType.CLIENT)) {
                gameView.drawHp(craft.getHp());
                gameView.drawScore(craft.getScore());
                gameView.drawWeapon(craft.getWeaponPower());
            }
            if ((crafts.indexOf(craft) == 0) && ((gameType == GameType.SERVER) || (gameType == GameType.SINGLEPLAYER))) {
                gameView.drawHp(craft.getHp());
                gameView.drawScore(craft.getScore());
                gameView.drawWeapon(craft.getWeaponPower());
            }


            gameView.drawCraft(crafts);
        }
    }


    private void updateAsteroid() {

        AsteroidOutOfFrame();
        //remove asteroids
        Iterator<Asteroid> iter1 = asteroids.iterator();
        while (iter1.hasNext()) {

            Asteroid asteroid = iter1.next();
            if (asteroid.getHp() <= 0) {
                iter1.remove();
            }
        }
        //move asteroids
        for (Asteroid asteroid : asteroids) {
            asteroid.move();
        }
        gameView.drawAsteroids(asteroids);
    }

    private void updateUfo() {

        UfoOutOfFrame();
        //remove asteroids
        Iterator<Ufo> iter = ufos.iterator();
        while (iter.hasNext()) {
            Ufo ufo = iter.next();
            if (ufo.getHp() <= 0) {
                iter.remove();
            }

        }
        //move ufos
        for (Ufo ufo : ufos) {
            ufo.move();
        }

        if (addUfoBullet) {
            Iterator<Ufo> iter2 = ufos.iterator();
            while (iter2.hasNext()) {
                Ufo ufo = iter2.next();
                //x+, y+ : ufo-bullets corretction
                bullets.add(new Bullet(ufo.getX() + (Ufo.WIDTH - Bullet.WIDTH) / 2, ufo.getY() + Bullet.HEIGHT, false, 1, false));
            }
        }
        addUfoBullet = false;

        gameView.drawUfos(ufos);
    }

    private void updateBullet() {

        bulletOutOfFrame();

        //remove bullets
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if (bullet.getDestroyBullet()) {
                iter.remove();
            }

        }
        //move bullets
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        //draw bullets
        gameView.drawBullets(bullets);
    }

    private void updateGift() {
        GiftOutOfFrame();

        //remove gifts
        Iterator<Gift> iter = gifts.iterator();
        while (iter.hasNext()) {
            Gift gift = iter.next();
            if (!gift.isExist()) {
                iter.remove();
            }
        }

        //move gifts
        for (Gift gift : gifts) {
            gift.move();
        }
        gameView.drawGift(gifts);

    }
    //outOfFrame functions

    private void GiftOutOfFrame() {
        for (Gift gift : gifts) {
            if (gift.getY() > WINDOWBOTTOM) {
                gift.setExist(false);
            }
        }
    }

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
            if (bullet.getIsCraftBullet())  //if bullet was shot by Craft
            {
                for (Ufo ufo : ufos) {
                    if (Math.sqrt(Math.pow(bullet.getCenterX() - ufo.getCenterX(), 2) + Math.pow(bullet.getCenterY() - ufo.getCenterY(), 2)) < ufo.getRadius() + bullet.getRadius())
                    //if bullet is close to Ufo
                    {
                        ufo.setHp(ufo.getHp() - bullet.getBulletPow()); //decrease Ufo HP
                        bullet.setDestroyBullet(true); //destroy bullet flag=1
                        crafts.get(0).setScore(crafts.get(0).getScore() + 20); //increase player's score
                    }
                }
                for (Asteroid asteroid : asteroids) {
                    if (Math.sqrt(Math.pow(bullet.getCenterX() - asteroid.getCenterX(), 2) + Math.pow(bullet.getCenterY() - asteroid.getCenterY(), 2)) < asteroid.getRadius() + bullet.getRadius())
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
                    if (Math.sqrt(Math.pow(bullet.getX() - craft.getX(), 2) + Math.pow(bullet.getY() - craft.getY(), 2)) < craft.getRadius() + bullet.getRadius()) {
                        craft.setHp(craft.getHp() - bullet.getBulletPow()); //decrease Asteroid HP
                        bullet.setDestroyBullet(true); //destroy bullet flag=1
                    }
                }
            }
        }

        for (Craft craft : crafts) {
            for (Ufo ufo : ufos) {
                if (Math.sqrt(Math.pow(craft.getCenterX() - ufo.getCenterX(), 2) + Math.pow(craft.getCenterY() - ufo.getCenterY(), 2)) < craft.getRadius() + ufo.getRadius()) {
                    ufo.setHp(0); //Ufo destroyed
                    craft.setHp(craft.getHp() - 1); //decrease UFO HP
                }
            }
            for (Asteroid asteroid : asteroids) {
                if (Math.sqrt(Math.pow(craft.getCenterX() - asteroid.getCenterX(), 2) + Math.pow(craft.getCenterY() - asteroid.getCenterY(), 2)) < craft.getRadius() + asteroid.getRadius()) {
                    asteroid.setHp(0);//asteroid destroyed
                    craft.setHp(craft.getHp() - 1); //decrease UFO HP
                }
            }
            for (Gift gift : gifts) {
                if (Math.sqrt(Math.pow(craft.getCenterX() - gift.getCenterX(), 2) + Math.pow(craft.getCenterY() - gift.getCenterY(), 2)) < craft.getRadius() + gift.getRadius()) {

                    if (gift instanceof HpGift) {
                        craft.setHp(craft.getHp() + 1);

                    } else {
                        WeaponGift weapongift = (WeaponGift) gift;
                        craft.setBulletPower(craft.getWeaponPower() + weapongift.getWeaponPow());
                        if(craft.getWeaponPower()+weapongift.getWeaponPow()>5) //max weapon power: 5
                        {
                            craft.setBulletPower(5);
                        }
                    }
                    gift.setExist(false);
                }
            }

        }

    }
}