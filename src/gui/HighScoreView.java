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
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by arons on 2017. 05. 02..
 */
public class HighScoreView {

    protected static final Font FONTABOUT = Font.font("", FontWeight.MEDIUM, 16);
    protected static final Font FONT = Font.font("", FontWeight.BOLD, 25);

    protected VBox menuBox;
    protected int currentItem = 0;

    protected final ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();
    protected static Stage ps;
    private Text highScoreText;

    private List<Pair<String, Integer>> highScores = new ArrayList<>();

    {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("scores.properties");
            String name;
            String score;
            int scoreInt;
            // load a properties file
            prop.load(input);
            for (int i = 1; i <= 10; i++) {
                name = prop.getProperty(i + "_name");
                score = prop.getProperty(i + "_score");
                if (score.length() == 0) {
                    scoreInt = 0;
                } else {
                    scoreInt = Integer.parseInt(score);
                }
                if (!(name.length() == 0)) {
                    highScores.add(i - 1, new Pair<>(name, scoreInt));
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
    }

    public HighScoreView(Stage ps) {
        this.ps = ps;
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(800, 900);

        Image img = new Image("gui/images/space_bg.jpg");
        ImageView imgView = new ImageView(img);

        highScoreText = new Text();
        highScoreText.setFont(FONT);
        highScoreText.setEffect(new GaussianBlur(2));
        highScoreText.setFill(Color.WHITE);
        StringBuilder sb = new StringBuilder();
        for (Pair<String, Integer> pair : highScores) {
            sb.append(pair.getKey() + ": " + pair.getValue() + "\n");
        }
        highScoreText.setText(sb.toString());
        highScoreText.setLayoutX(300);
        highScoreText.setLayoutY(100);

        menuBox = new VBox(10,
                new MenuItem("BACK"));
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(300);
        menuBox.setTranslateY(600);


        getMenuItem(0).setActive(true);

        root.getChildren().addAll(imgView, menuBox, highScoreText);
        return root;
    }


    private MenuItem getMenuItem(int index) {
        return (MenuItem) menuBox.getChildren().get(index);
    }


    private static class MenuItem extends HBox {
        private final SelectCircle c1 = new SelectCircle();
        private final SelectCircle c2 = new SelectCircle();
        private final Text text;
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

    protected static class SelectCircle extends Parent {
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
