package main;

import gui.MenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuView menuView = new MenuView(primaryStage);
        menuView.build(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
