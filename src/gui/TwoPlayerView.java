package gui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
import main.Controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by arons on 2017. 04. 29..
 */

public class TwoPlayerView {
    private static final Font FONT = Font.font("", FontWeight.BOLD, 30);

    private VBox menuBox;
    private int currentItem = 0;

    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();
    private static Stage ps;

    public TwoPlayerView(Stage ps) {
        this.ps = ps;
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(800, 900);

        Image img = new Image("gui/images/space_bg.jpg");
        ImageView imgView = new ImageView(img);
        //imgView.setFitHeight(950);
        //imgView.setFitWidth(844);

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnActivate(() -> System.exit(0));

        menuBox = new VBox(10,
                new MenuItem("SERVER"),
                new MenuItem("CLIENT"),
                new MenuItem("BACK"),
                itemExit);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(300);
        menuBox.setTranslateY(200);



        getMenuItem(0).setActive(true);

        root.getChildren().addAll(imgView, menuBox);
        return root;
    }

    private MenuItem getMenuItem(int index) {
        return (MenuItem) menuBox.getChildren().get(index);
    }

    private static class MenuItem extends HBox {
        private SelectCircle c1 = new SelectCircle(), c2 = new SelectCircle();
        private Text text;
        private Runnable script;

        public MenuItem(String name) {
            super(15);
            setAlignment(Pos.CENTER);

            text = new Text(name);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2));

            getChildren().addAll(c1, text, c2);
            setActive(false);
            setOnActivate(() -> {
                System.out.println(name + " activated");
                switch (name) {
                    case "SERVER":
                        Controller controller = new Controller(ps);
                        controller.start();
                        break;
                    case "CLIENT":
                        try {
                            PopupView popupView = new PopupView(ps);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "BACK":
                        MenuView menuView = new MenuView(ps);
                        try {
                            menuView.build(ps);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }

            });
        }
        public void setActive(boolean b) {
            c1.setVisible(b);
            c2.setVisible(b);
            text.setFill(b ? Color.WHITE : Color.GREY);
        }

        public void setOnActivate(Runnable r) {
            script = r;
        }

        public void activate() {
            if (script != null)
                script.run();
        }
    }

    private static class SelectCircle extends Parent {
        public SelectCircle() {
            Shape shape = Shape.subtract(new Circle(5), new Circle(2));
            shape.setFill(Color.WHITE);
            getChildren().addAll(shape);
            setEffect(new GaussianBlur(2));
        }
    }

    public void build(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menuBox.getChildren().size() - 1) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.ENTER) {
                getMenuItem(currentItem).activate();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> bgThread.shutdownNow());
        primaryStage.show();
    }
}
