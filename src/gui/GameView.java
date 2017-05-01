package gui;

import gameelement.Asteroid;
import gameelement.Bullet;
import gameelement.Ufo;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.KeyListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Aron on 2017. 04. 29..
 */
public class GameView {

    private static final String UFO = "gui/images/ufo.png";
    private static final String BULLET = "gui/images/bullet.png";
    private static final String ASTEROID = "gui/images/ast.png";
    private static final String BACKGORUND = "gui/images/space_bg.jpg";
    private static final String CRAFT = "gui/images/Spaceship.png";
    private static final String WEAPONGIFT = "gui/images/weapon.png";
    private static final String HPGIFT = "gui/images/heal.png";

    private Pane root;
    private ImageView background;
    private ImageView craft;
    private ImageView hpGift;
    private ImageView weaponGift;
    private Rectangle hpRect;
    private Label hpLabel;
    private Label scoreLabel;

    private List<ImageView> bullets = new ArrayList<ImageView>();
    private List<ImageView> ufos = new ArrayList<ImageView>();
    private List<ImageView> asteroids = new ArrayList<ImageView>();

    private KeyListener keyListener;

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void drawCraft(int x, int y) {
        craft.setX(x);
        craft.setY(y);
    }

    public void drawBullets(List<Bullet> bulletList) {
        root.getChildren().removeAll(bullets);
        bullets.clear();

        for (Bullet b : bulletList) {
            ImageView bullet1 = new ImageView(new Image(BULLET));
            bullet1.setX(b.getX()); //+310
            bullet1.setY(b.getY()); // +280
            bullet1.setScaleY(0.15);
            bullet1.setScaleX(0.15);
            bullets.add(bullet1);
            root.getChildren().addAll(bullet1);
        }
    }

    public void drawUfos(List<Ufo> ufoList) {
        root.getChildren().removeAll(ufos);
        ufos.clear();

        for (Ufo u : ufoList) {
            ImageView ufo = new ImageView(new Image(UFO));
            ufo.setScaleX(0.3);
            ufo.setScaleY(0.3);
            ufo.setX(u.getX());
            ufo.setY(u.getY());
            ufos.add(ufo);
            root.getChildren().addAll(ufo);
        }

    }

    public void drawAsteroids(List<Asteroid> asteroidList) {
        root.getChildren().removeAll(asteroids);
        asteroids.clear();

        for (Asteroid a : asteroidList) {
            ImageView asteroid = new ImageView(new Image(ASTEROID));
            asteroid.setScaleX(0.3);
            asteroid.setScaleY(0.3);
            asteroid.setX(a.getX());
            asteroid.setY(a.getY());
            asteroids.add(asteroid);
            root.getChildren().addAll(asteroid);
        }

    }

    public void drawScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void drawHp(int hp) {
        if (hp == 0) {
            hpRect.setWidth(100);
            hpRect.setFill(Color.valueOf("#ff0000"));
        } else {
            hpRect.setFill(Color.valueOf("#00ff00"));
            hpRect.setWidth(100 / (4 - hp));
        }
        hpLabel.setText("HP: " + hp);

    }

    public void drawWeaponGift(int x, int y) {
        weaponGift.setX(x);
        weaponGift.setY(y);
    }

    public void drawHpGift(int x, int y) {
        hpGift.setX(x);
        hpGift.setY(y);
    }

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(800, 900);

        background = new ImageView(new Image(BACKGORUND));

        craft = new ImageView(new Image(CRAFT));
        craft.setScaleX(0.1);
        craft.setScaleY(0.1);

        hpRect = new Rectangle(100, 20);
        hpRect.setY(10);
        hpRect.setX(10);
        hpRect.setFill(Color.valueOf("#00ff00"));

        hpLabel = new Label("HP: 3");
        hpLabel.setLayoutX(15);
        hpLabel.setLayoutY(5);
        hpLabel.setTextFill(Color.WHITE);
        hpLabel.setFont(Font.font("", FontWeight.BOLD, 20));
        hpLabel.setEffect(new GaussianBlur(1));

        scoreLabel = new Label("Score:");
        scoreLabel.setLayoutY(30);
        scoreLabel.setLayoutX(15);
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font("", FontWeight.BOLD, 20));
        scoreLabel.setEffect(new GaussianBlur(1));

        weaponGift = new ImageView(new Image(WEAPONGIFT));
        weaponGift.setFitWidth(50);
        weaponGift.setFitHeight(50);
        weaponGift.setX(-100);
        weaponGift.setY(-100);

        hpGift = new ImageView(new Image(HPGIFT));
        hpGift.setFitHeight(50);
        hpGift.setFitWidth(50);
        hpGift.setY(-100);
        hpGift.setX(-100);


        root.getChildren().addAll(background, craft, hpRect, hpLabel, scoreLabel, weaponGift, hpGift);
        return root;
    }


    public void build(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {

            }

            if (event.getCode() == KeyCode.DOWN) {

            }

            if (event.getCode() == KeyCode.RIGHT) {
                keyListener.rightPressed();
            }
            if (event.getCode() == KeyCode.LEFT) {
                keyListener.leftPressed();
            }
            if (event.getCode() == KeyCode.SPACE) {
                keyListener.spacePressed();
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP) {

            }

            if (event.getCode() == KeyCode.DOWN) {

            }

            if (event.getCode() == KeyCode.RIGHT) {
                keyListener.rightReleased();
            }
            if (event.getCode() == KeyCode.LEFT) {
                keyListener.leftReleased();
            }
            if (event.getCode() == KeyCode.SPACE) {
                keyListener.spaceReleased();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            bgThread.shutdownNow();
            keyListener.exit();
        });
        primaryStage.show();
    }
}
