package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Created by arons on 2017. 04. 29..
 */
public class PopupView {
    Stage ps;

    public PopupView(Stage ps) {
        this.ps = ps;
        Popup popup = new Popup();
        //popup.setX(300); popup.setY(200);

        TextField textField = new TextField();
        Label label = new Label();
        label.setText("Please give the server IP!");
        label.setFont(Font.font("", FontWeight.BOLD, 20));
        label.setEffect(new GaussianBlur(1));
        label.setTextFill(Color.WHITE);


        Button btn1 = new Button();
        btn1.setText("Enter");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.hide();

                Controller controller = new Controller(ps);
                controller.start();
            }
        });

        HBox layout = new HBox();
        layout.setStyle("-fx-background-color: #030703;");
        layout.getChildren().addAll(label, textField, btn1);

        popup.getContent().addAll(layout);
        popup.setAutoHide(true);
        popup.show(ps);

    }



}
