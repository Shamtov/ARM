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

public class SubBus {

    private SubBus() {
        super();
    }

    private static SubBus instance;

    public static SubBus getInstance() {
        if (instance == null)
            instance = new SubBus();
        return instance;
    }

    private TextField nameText = new TextField();
    private TextField latText = new TextField();
    private TextField lonText = new TextField();
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
        nameText = new TextField();
        nameText.setPromptText("ул. Леваневского (из центра)");
        nameText.setMinWidth(200);
        GridPane.setHalignment(nameLabel, HPos.CENTER);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameText, 1, 1);

        Label lonLabel = new Label("Широта");
        lonLabel.setFont(new Font("Arial", 14));
        latText = new TextField();
        latText.setPromptText("39,103365");
        latText.setMinWidth(150);
        GridPane.setHalignment(lonLabel, HPos.CENTER);
        gridPane.add(lonLabel, 0, 2);
        gridPane.add(latText, 1, 2);

        Label latLabel = new Label("Долгота");
        latLabel.setFont(new Font("Arial", 14));
        lonText = new TextField();
        lonText.setPromptText("51,657974");
        lonText.setMinWidth(150);
        GridPane.setHalignment(latLabel, HPos.CENTER);
        gridPane.add(latLabel, 0, 3);
        gridPane.add(lonText, 1, 3);

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
                BusStop.getInstance().addData(nameText, lonText, latText, azmthText);
        });
        add.setOnAction(event -> {
            BusStop.getInstance().addData(nameText, lonText, latText, azmthText);
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

        nameText.setText(nameValue);
        lonText.setText(String.valueOf(lonValue));
        latText.setText(String.valueOf(latValue));
        azmthText.setText(String.valueOf(azmthValue));

        Button add = new Button("Сохранить");
        add.setOnKeyReleased(enter -> {
            if (enter.getCode() == KeyCode.ENTER)
                BusStop.getInstance().updateData(nameText, lonText, latText, azmthText, idValue);
        });
        add.setOnAction(event ->
                BusStop.getInstance().updateData(nameText, lonText, latText, azmthText, idValue));

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
        subDirStopsStage.setOnCloseRequest(event -> clearFields());

    }

    public void clearFields() {
        nameText.clear();
        lonText.clear();
        latText.clear();
        azmthText.clear();
    }

}

