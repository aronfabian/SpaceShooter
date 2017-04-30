package sample;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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

    private VBox gameBox;
    private int currentItem = 0;
    private ImageView imgView;
    private Pane root;
    private ImageView ship;
    private ImageView bullet;
    private List<ImageView> bullets;
    private KeyListener keyListener;

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void drawCraft(int x, int y) {
        ship.setX(x);
        ship.setY(y);
    }
    public void clearBullets(){
        root.getChildren().removeAll(bullets);
        bullets.clear();
    }
    public void drawBullets(List<Bullet> bulletList){
        for (Bullet b : bulletList) {
            ImageView bullet1 = new ImageView(new Image("sample/bullet.png"));
            bullet1.setX(b.getX()+151);
            bullet1.setY(b.getY()+180);
            bullet1.setScaleY(0.15);
            bullet1.setScaleX(0.15);
            bullets.add(bullet1);
            root.getChildren().addAll(bullet1);
        }



    }

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(800, 900);


        Image img = new Image("sample/space_bg.jpg");
        imgView = new ImageView(img);
        bullets = new ArrayList<ImageView>();
        bullet = new ImageView(new Image("sample/bullet.png"));

        ship = new ImageView(new Image("sample/Spaceship.png"));
        ship.setScaleX(0.1);
        ship.setScaleY(0.1);

        root.getChildren().addAll(imgView,ship);
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
//                ImageView bullet = new ImageView(new Image("sample/bullet.png"));
//                bullet.setScaleX(0.15);
//                bullet.setScaleY(0.15);
//                bullet.setX(ship.getX()+151);
//                bullet.setY(ship.getY()+180);
//                root.getChildren().addAll(bullet);
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
