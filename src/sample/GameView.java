package sample;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Aron on 2017. 04. 29..
 */
public class GameView {

    private static final Font FONT = Font.font("", FontWeight.BOLD, 30);
    private static final String UFO ="sample/ufo.png" ;
    private static final String BULLET = "sample/bullet.png";
    private static final String ASTEROID = "sample/ast.png";
    private static final String BACKGORUND = "sample/space_bg.jpg";
    private static final String CRAFT = "sample/Spaceship.png";

    private VBox gameBox;
    private int currentItem = 0;
    private ImageView imgView;
    private Pane root;
    private ImageView craft;
    private ImageView bullet;

    private List<ImageView> bullets = new ArrayList<ImageView>();
    private List<ImageView> ufos= new ArrayList<ImageView>();
    private List<ImageView> asteroids= new ArrayList<ImageView>();

    private KeyListener keyListener;


    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void drawCraft(int x, int y) {
        craft.setX(x);
        craft.setY(y);
    }

    //TODO összevonható a drawBullets()-el
    public void clearBullets() {
        root.getChildren().removeAll(bullets);
        bullets.clear();
    }


    public void drawBullets(List<Bullet> bulletList) {
        //root.getChildren().removeAll(bullets);
        //bullets.clear();

        for (Bullet b : bulletList) {
            ImageView bullet1 = new ImageView(new Image(BULLET));
            bullet1.setX(b.getX() + 310);
            bullet1.setY(b.getY() + 280);
            bullet1.setScaleY(0.15);
            bullet1.setScaleX(0.15);
            bullets.add(bullet1);
            root.getChildren().addAll(bullet1);
        }
    }

    public void drawUfo(List<Ufo> ufoList) {
        root.getChildren().removeAll(ufos);
        ufos.clear();

        for(Ufo u : ufoList){
            ImageView ufo = new ImageView(new Image(UFO));
            ufo.setScaleX(0.3);
            ufo.setScaleY(0.3);
            ufo.setX(u.getX());
            ufo.setY(u.getY());
            ufos.add(ufo);
            root.getChildren().addAll(ufo);
        }

    }
    public void drawAsteroid(List<Asteroid> asteroidList){
        root.getChildren().removeAll(asteroids);
        asteroids.clear();

        for (Asteroid a : asteroidList){
            ImageView asteroid = new ImageView(new Image(ASTEROID));
            asteroid.setScaleX(0.3);
            asteroid.setScaleY(0.3);
            asteroid.setX(a.getX());
            asteroid.setY(a.getY());
            root.getChildren().addAll(asteroid);
        }

    }

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(800, 900);

        imgView = new ImageView(new Image(BACKGORUND));

        bullet = new ImageView(new Image(BULLET));

        craft = new ImageView(new Image(CRAFT));
        craft.setScaleX(0.1);
        craft.setScaleY(0.1);


        root.getChildren().addAll(imgView, craft);
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
