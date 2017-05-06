package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.Controller;
import main.GameType;

import java.util.regex.Pattern;

/**
 * Created by arons on 2017. 04. 29..
 */
public class PopupView {
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    Stage ps;
    private TextField textField;
    private Label label;
    private Popup popup;

    public static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    private void eventHandler() {
        String ip = textField.getText();
        if ((ip.length() == 0) || !validate(ip)) {
            label.setText("Not valid IP address!");
            return;
        } else {
            popup.hide();
            Controller controller = new Controller(ps, GameType.CLIENT, ip);
            controller.start();
        }
    }
    public PopupView(Stage ps) {
        this.ps = ps;
        popup = new Popup();
        //popup.setX(300); popup.setY(200);
        label = new Label();
        label.setText("Please give the server IP!");
        label.setFont(Font.font("", FontWeight.BOLD, 20));
        label.setEffect(new GaussianBlur(1));
        label.setTextFill(Color.WHITE);

        textField = new TextField();
        textField.setOnKeyPressed(event -> {
            eventHandler();
        });

        Button btn1 = new Button();
        btn1.setText("Connect");
        btn1.setOnAction(event -> {
            eventHandler();
        });

        HBox layout = new HBox();
        layout.setStyle("-fx-background-color: #030703;");
        layout.getChildren().addAll(label, textField, btn1);

        popup.getContent().addAll(layout);
        popup.setAutoHide(true);
        popup.show(ps);

    }


}
