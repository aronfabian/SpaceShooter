package gui;

import gameelement.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    protected static final Font FONT = Font.font("", FontWeight.BOLD, 60);

    private static final String UFO = "gui/images/ufo.png";
    private static final String BULLET = "gui/images/bullet.png";
    private static final String ASTEROID = "gui/images/ast.png";
    private static final String BACKGORUND = "gui/images/space_bg.jpg";
    private static final String CRAFT = "gui/images/Spaceship.png";
    private static final String WEAPONGIFT = "gui/images/weapon.png";
    private static final String HPGIFT = "gui/images/heal.png";

    private Stage ps;
    private Pane root;
    private ImageView background;
    private Rectangle hpRect;
    private Label hpLabel;
    private Label scoreLabel;
    private Label craftCoord;
    private Label overLabel;
    private TextField nameField;

    private final List<ImageView> crafts = new ArrayList<>();
    private final List<ImageView> bullets = new ArrayList<>();
    private final List<ImageView> ufos = new ArrayList<>();
    private final List<ImageView> asteroids = new ArrayList<>();
    private final List<ImageView> gifts = new ArrayList<>();
    private KeyListener keyListener;

    public GameView(Stage ps) {
        this.ps = ps;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void drawCraft(List<Craft> craftList) {
        root.getChildren().removeAll(crafts);
        crafts.clear();

        for (Craft c : craftList) {
            ImageView craft = new ImageView(CRAFT);
            craft.setX(c.getX());
            craft.setY(c.getY());
            crafts.add(craft);
            root.getChildren().addAll(craft);
        }
    }


    public void drawBullets(List<Bullet> bulletList) {
        root.getChildren().removeAll(bullets);
        bullets.clear();

        for (Bullet b : bulletList) {
            ImageView bullet = new ImageView(new Image(BULLET));
            bullet.setX(b.getX());
            bullet.setY(b.getY());
            bullets.add(bullet);
            root.getChildren().addAll(bullet);
        }
    }

    public void drawUfos(List<Ufo> ufoList) {
        root.getChildren().removeAll(ufos);
        ufos.clear();

        for (Ufo u : ufoList) {
            ImageView ufo = new ImageView(new Image(UFO));
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

    public void drawGift(List<Gift> giftList) {
        root.getChildren().removeAll(gifts);
        gifts.clear();

        for (Gift g : giftList) {
            ImageView gift;
            if (g instanceof WeaponGift) {
                gift = new ImageView(new Image(WEAPONGIFT));
            } else {
                gift = new ImageView(new Image(HPGIFT));
            }
            gift.setX(g.getX());
            gift.setY(g.getY());
            gifts.add(gift);
            root.getChildren().addAll(gift);
        }
    }

    public void gameOver() {
        overLabel = new Label("GAME OVER");
        overLabel.setFont(FONT);
        overLabel.setTextFill(Color.WHITE);
        overLabel.setEffect(new GaussianBlur(2));
        overLabel.setLayoutY(200);
        overLabel.setLayoutX(230);

        nameField = new TextField("Give your name, please!");
        nameField.setLayoutX(270);
        nameField.setLayoutY(300);
        nameField.setFont(Font.font("", FontWeight.BOLD, 20));
        nameField.setEffect(new GaussianBlur(1));
        nameField.setOnAction(event -> {
            keyListener.highScoreName(nameField.getText());
            MenuView menuView = new MenuView(ps);
            try {
                menuView.build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        root.getChildren().addAll(overLabel, nameField);
    }

    public void gameOverMulti(boolean isWinner) {
        overLabel = new Label();
        if (isWinner) {
            overLabel.setText("CONGRATULATION\nYOU WIN");
        } else {
            overLabel.setText("YOU LOSE");
        }
        overLabel.setFont(FONT);
        overLabel.setTextFill(Color.WHITE);
        overLabel.setEffect(new GaussianBlur(2));
        overLabel.setLayoutY(200);
        overLabel.setLayoutX(230);
        overLabel.setOnMouseClicked(event -> {

            MenuView menuView = new MenuView(ps);
            try {
                menuView.build();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        root.getChildren().addAll(overLabel);
    }

    private final ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(800, 900);

        background = new ImageView(new Image(BACKGORUND));

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

        craftCoord = new Label();
        craftCoord.setText("");
        craftCoord.setTextFill(Color.WHITE);
        craftCoord.setFont(Font.font("", FontWeight.BOLD, 20));
        craftCoord.setEffect(new GaussianBlur(1));

        root.getChildren().addAll(background, hpRect, hpLabel, scoreLabel, craftCoord);
        return root;
    }


    public void build() throws Exception {
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

        ps.setScene(scene);
        ps.setResizable(false);
        ps.setOnCloseRequest(event -> {
            bgThread.shutdownNow();
            keyListener.exit();
        });
        ps.show();
    }
}
