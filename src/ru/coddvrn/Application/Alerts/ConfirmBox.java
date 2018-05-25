package ru.coddvrn.Application.Alerts;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

        public static void display(String title, String message){
            Stage exitWindow = new Stage();
            exitWindow.initModality(Modality.APPLICATION_MODAL);
            exitWindow.setTitle(title);

            Label label = new Label();
            label.setFont(Font.font(14));
            label.setText(message);

            //Create two buttons
            Button yesButton = new Button("Да");
            Button noButton = new Button("Нет");

            yesButton.setOnAction(event -> {
                System.exit(0);
            });
            noButton.setOnAction(event -> {
                exitWindow.close();
            });

            VBox vbox = new VBox(10);
            HBox hbox = new HBox(10);

            hbox.getChildren().addAll(yesButton, noButton);
            vbox.getChildren().addAll(label, hbox);
            hbox.setAlignment(Pos.CENTER);
            vbox.setAlignment(Pos.CENTER);

            Scene scene = new Scene (vbox,400,150);
            exitWindow.setScene(scene);
            exitWindow.showAndWait();
        }
    }

