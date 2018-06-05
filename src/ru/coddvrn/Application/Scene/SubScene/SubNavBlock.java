package ru.coddvrn.Application.Scene.SubScene;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Scene.BusStop;

public class SubNavBlock {
    private SubNavBlock() {
        super();
    }

    private static SubNavBlock instance;

    public static SubNavBlock getInstance() {
        if (instance == null)
            instance = new SubNavBlock();
        return instance;
    }
    private TextField blockNumberText = new TextField();
    private TextField blockTypeText = new TextField();
    private TextField stateNumberText = new TextField();
    private TextField phoneText = new TextField();
    private TextField azmthText = new TextField();

    private Stage subDirStopsStage = new Stage();

    public Stage getStage() {
        if (subDirStopsStage == null)
            subDirStopsStage.initModality(Modality.APPLICATION_MODAL);
        return subDirStopsStage;
    }

    private GridPane initScene() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 10, 10, 10));
        gridPane.setHgap(25);
        gridPane.setVgap(15);

        Label nameLabel = new Label("Название остановки");
        nameLabel.setFont(new Font("Arial", 14));
        blockTypeText = new TextField();
        blockTypeText.setPromptText("ул. Леваневского (из центра)");
        blockTypeText.setMinWidth(200);
        GridPane.setHalignment(nameLabel, HPos.CENTER);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(blockTypeText, 1, 1);

        Label lonLabel = new Label("Широта");
        lonLabel.setFont(new Font("Arial", 14));
        stateNumberText = new TextField();
        stateNumberText.setPromptText("39,103365");
        stateNumberText.setMinWidth(150);
        GridPane.setHalignment(lonLabel, HPos.CENTER);
        gridPane.add(lonLabel, 0, 2);
        gridPane.add(stateNumberText, 1, 2);

        Label latLabel = new Label("Долгота");
        latLabel.setFont(new Font("Arial", 14));
        phoneText = new TextField();
        phoneText.setPromptText("51,657974");
        phoneText.setMinWidth(150);
        GridPane.setHalignment(latLabel, HPos.CENTER);
        gridPane.add(latLabel, 0, 3);
        gridPane.add(phoneText, 1, 3);

        Label azmthLabel = new Label("Азимут");
        azmthLabel.setFont(new Font("Arial", 14));
        azmthText = new TextField();
        azmthText.setPromptText("Например, 50");
        azmthText.setMinWidth(100);
        GridPane.setHalignment(azmthLabel, HPos.CENTER);
        gridPane.add(azmthLabel, 0, 4);
        gridPane.add(azmthText, 1, 4);

        return gridPane;
    }

    public void display() {
        GridPane root = initScene();
        subDirStopsStage.setTitle("Добавить");

        Button add = new Button("Сохранить");
        add.setOnKeyReleased(enter -> {
            if (enter.getCode() == KeyCode.ENTER)
                BusStop.getInstance().addData(blockTypeText, phoneText, stateNumberText, azmthText);
        });
        add.setOnAction(event -> {
            BusStop.getInstance().addData(blockTypeText, phoneText, stateNumberText, azmthText);
        });
        Button cancel = new Button("Отмена");
        cancel.setOnKeyReleased(escape -> {
            if (escape.getCode() == KeyCode.ESCAPE)
                subDirStopsStage.close();
        });
        cancel.setOnAction(event ->
                subDirStopsStage.close()
        );
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(add, cancel);
        root.add(buttonBox, 1, 6);

        Scene subDirStopsScene = new Scene(root, 450, 300);
        subDirStopsStage.setScene(subDirStopsScene);
        subDirStopsStage.show();
    }

    public void display(int idValue, String nameValue, double latValue, double lonValue, int azmthValue) {
        GridPane root = initScene();
        subDirStopsStage.setTitle("Изменить");

        blockTypeText.setText(nameValue);
        phoneText.setText(String.valueOf(lonValue));
        stateNumberText.setText(String.valueOf(latValue));
        azmthText.setText(String.valueOf(azmthValue));

        Button add = new Button("Сохранить");
        add.setOnKeyReleased(enter -> {
            if (enter.getCode() == KeyCode.ENTER)
                BusStop.getInstance().updateData(blockTypeText, phoneText, stateNumberText, azmthText, idValue);
        });
        add.setOnAction(event ->
                BusStop.getInstance().updateData(blockTypeText, phoneText, stateNumberText, azmthText, idValue));

        Button cancel = new Button("Отмена");
        cancel.setOnKeyReleased(escape -> {
            if (escape.getCode() == KeyCode.ESCAPE)
                subDirStopsStage.close();
        });
        cancel.setOnAction(event ->
                subDirStopsStage.close()
        );
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(add, cancel);
        root.add(buttonBox, 1, 6);

        Scene subDirStopsScene = new Scene(root, 450, 300);
        subDirStopsStage.setScene(subDirStopsScene);
        subDirStopsStage.show();
        subDirStopsStage.setOnCloseRequest(event -> BusStop.getInstance().refreshTable());

    }

    public void clearFields(TextField nameText, TextField lonText, TextField latText, TextField azmthText) {
        nameText.clear();
        lonText.clear();
        latText.clear();
        azmthText.clear();
    }

}
