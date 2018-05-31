package ru.coddvrn.Application.Scene.SubScene;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Scene.Route;

public class RouteEdit extends Route {
    private TextField nameText = new TextField();
    private TextField countText = new TextField();
    private TextField statusText = new TextField();

    public void display(String nameValue, int countValue, String statusValue, String oldNameValue) {
        Stage subRoutesStage = new Stage();
        subRoutesStage.initModality(Modality.APPLICATION_MODAL);
        subRoutesStage.setTitle("Изменить");

        GridPane root = new GridPane();
        root.setPadding(new Insets(20, 10, 10, 10));
        root.setHgap(25);
        root.setVgap(15);

        Label label1 = new Label("Название маршрута");
        label1.setFont(new Font("Arial", 14));
        nameText.setText(nameValue);
        nameText.setPromptText("Например, 1");
        nameText.setMinWidth(100);
        GridPane.setHalignment(label1, HPos.CENTER);
        root.add(label1, 0, 0);
        root.add(nameText, 1, 0);

        Label label2 = new Label("Количество остановок");
        label2.setFont(new Font("Arial", 14));
        countText.setText(String.valueOf(countValue));
        countText.setMinWidth(200);
        countText.setDisable(true);
        GridPane.setHalignment(label2, HPos.CENTER);
        root.add(label2, 0, 1);
        root.add(countText, 1, 1);

        Label label3 = new Label("Статус");
        label3.setFont(new Font("Arial", 14));
        statusText.setText(statusValue);
        statusText.setPromptText("0 или 1");
        statusText.setMinWidth(200);
        GridPane.setHalignment(label3, HPos.CENTER);
        root.add(label3, 0, 2);
        root.add(statusText, 1, 2);

        Button add = new Button("Сохранить");
        add.setOnAction(event -> updateData(nameText, statusText, oldNameValue));
        Button cancel = new Button("Отмена");
        cancel.setOnAction(event ->
                subRoutesStage.close()
        );

        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(add, cancel);
        root.add(buttonBox, 1, 4);
        Scene subRoutesScene = new Scene(root, 450, 250);
        subRoutesStage.setScene(subRoutesScene);
        subRoutesStage.show();

    }
}
