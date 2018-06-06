package ru.coddvrn.Application.Scene.SubScene;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Alerts.FormsAlerts;
import ru.coddvrn.Application.Repository.ListRep;
import ru.coddvrn.Application.Scene.BusStop;
import ru.coddvrn.Application.Scene.NavigationBlock;
import ru.coddvrn.Application.Validate.ValidateFields;

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
    private ComboBox typeBox = new ComboBox(new ListRep().getTypeofBlocks());
    private TextField stateNumberText = new TextField();

    private Stage subNavStage = new Stage();

    public Stage getStage() {
        if (subNavStage == null)
            subNavStage.initModality(Modality.APPLICATION_MODAL);
        return subNavStage;
    }

    public void display() {
        typeBox.setMaxWidth(150);

        GridPane root = new GridPane();
        root.setPadding(new Insets(20, 10, 10, 10));
        root.setHgap(25);
        root.setVgap(15);

        Label numberLabel = new Label("Номер блока");
        numberLabel.setFont(new Font("Arial", 14));
        blockNumberText.setPromptText("6563");
//        blockTypeText.setMinWidth(200);
        GridPane.setHalignment(numberLabel, HPos.CENTER);
        root.add(numberLabel, 0, 1);
        root.add(blockNumberText, 1, 1);

        Label typeLabel = new Label("Тип блока");
        typeLabel.setFont(new Font("Arial", 14));
        GridPane.setHalignment(typeLabel, HPos.CENTER);
        root.add(typeLabel, 0, 2);
        root.add(typeBox, 1, 2);

        Label stateLabel = new Label("Гос номер");
        stateLabel.setFont(new Font("Arial", 14));
        stateNumberText.setPromptText("а000аа36");
//        stateNumberText.setMinWidth(150);
        GridPane.setHalignment(stateLabel, HPos.CENTER);
        root.add(stateLabel, 0, 3);
        root.add(stateNumberText, 1, 3);

        subNavStage.setTitle("Добавить");

        Button add = new Button("Сохранить");
        add.setOnAction(event -> {
            NavigationBlock.getInstance().addData(blockNumberText.getText(), (String) typeBox.getSelectionModel().getSelectedItem(), stateNumberText.getText());
        });
        Button cancel = new Button("Отмена");
        cancel.setOnAction(event ->
                subNavStage.close()
        );
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(add, cancel);
        root.add(buttonBox, 1, 4);

        Scene subDirStopsScene = new Scene(root, 400, 240);
        /*navBlockScene.setOnKeyPressed(keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case ENTER: {
                            if (new ValidateFields().validateString(nameText.getText()))
                                NavigationBlock.getInstance().updateData(nameText, status.getSelectionModel().getSelectedIndex(), oldNameValue);
                            else new FormsAlerts().getWarningAlert("Отправка формы", "Название маршрута не должно быть пыстым");
                        }
                    }
                }
        );*/
        subNavStage.setScene(subDirStopsScene);
        subNavStage.show();
    }

    public void display(int blockInput, String stateInput, long phoneInput) {
      /*GridPane root = initScene();
        subNavStage.setTitle("Изменить");

        blockNumberText.setText(String.valueOf(blockInput));
        stateNumberText.setText(stateInput);
        phoneText.setText(String.valueOf(phoneInput));

        Button add = new Button("Сохранить");
        add.setOnAction(event -> {
//                BusStop.getInstance().updateData(blockNumberText, stateNumberText, phoneText));
                });
        Button cancel = new Button("Отмена");
        cancel.setOnAction(event ->
                subNavStage.close()
        );
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(add, cancel);
        root.add(buttonBox, 1, 6);

        Scene navBlockScene = new Scene(root, 450, 300);
        navBlockScene.setOnKeyPressed(keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case ENTER: {
                            if (new ValidateFields().validateString(nameText.getText()))
                                NavigationBlock.getInstance().updateData(nameText, status.getSelectionModel().getSelectedIndex(), oldNameValue);
                            else new FormsAlerts().getWarningAlert("Отправка формы", "Название маршрута не должно быть пыстым");
                        }
                    }
                }
        );
        subNavStage.setScene(navBlockScene);
        subNavStage.show();*/
    }

    public void clearFields() {
        blockNumberText.clear();
        stateNumberText.clear();
    }

}
