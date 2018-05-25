package ru.coddvrn.Application;

import ru.coddvrn.Application.MenuItem.ApplicationMenu;
import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controller extends Application {

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        root.setTop(ApplicationMenu.getMenu());
//        root.setPadding(new Insets(15,20,10,10));

        Scene scene = new Scene(root, 600, 600);

        stage.setTitle("АРМ МБУ " + "ЦОДД");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}