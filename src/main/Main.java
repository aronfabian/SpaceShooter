package main;

import gui.MenuView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuView menuView = new MenuView(primaryStage);
        menuView.build();
        primaryStage.getIcons().add(new Image("gui/images/icon.png"));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
