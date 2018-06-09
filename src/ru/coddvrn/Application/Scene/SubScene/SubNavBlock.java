package ru.coddvrn.Application.Scene.SubScene;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Alerts.FormsAlerts;
import ru.coddvrn.Application.Repository.ListRep;
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
    private TextField commentText = new TextField();

    private Stage subNavStage = new Stage();

    public Stage getStage() {
        if (subNavStage == null)
            subNavStage.initModality(Modality.APPLICATION_MODAL);
        return subNavStage;
    }

    private GridPane initScene() {
        typeBox.setMaxWidth(150);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 10, 10, 10));
        gridPane.setHgap(25);
        gridPane.setVgap(15);

        Label numberLabel = new Label("Номер блока");
        numberLabel.setFont(new Font("Arial", 14));
        blockNumberText.setPromptText("6563");
        blockNumberText.setMinWidth(200);
        GridPane.setHalignment(numberLabel, HPos.CENTER);
        gridPane.add(numberLabel, 0, 1);
        gridPane.add(blockNumberText, 1, 1);

        Label typeLabel = new Label("Тип блока");
        typeLabel.setFont(new Font("Arial", 14));
        GridPane.setHalignment(typeLabel, HPos.CENTER);
        gridPane.add(typeLabel, 0, 2);
        gridPane.add(typeBox, 1, 2);

        Label stateLabel = new Label("Гос номер");
        stateLabel.setFont(new Font("Arial", 14));
        stateNumberText.setPromptText("а000аа36");
        stateNumberText.setMinWidth(150);
        GridPane.setHalignment(stateLabel, HPos.CENTER);
        gridPane.add(stateLabel, 0, 3);
        gridPane.add(stateNumberText, 1, 3);

        Label commentLabel = new Label("Комментарий");
        commentLabel.setFont(new Font("Arial", 14));
        GridPane.setHalignment(commentText, HPos.CENTER);
        gridPane.add(commentLabel, 0, 4);
        gridPane.add(commentText, 1, 4);
        return gridPane;
    }

    public void display() {
        GridPane root = initScene();

        subNavStage.setTitle("Добавить");
        Button add = new Button("Сохранить");
        add.setOnAction(event -> {
            if ((new ValidateFields().validateString(blockNumberText.getText())) &&
                    (new ValidateFields().validateString(stateNumberText.getText()) &&
                            (typeBox.getValue() != "")))
                NavigationBlock.getInstance().addData(blockNumberText.getText(),
                        (String) typeBox.getSelectionModel().getSelectedItem(),
                        stateNumberText.getText(),
                        commentText.getText());
        });
        Button cancel = new Button("Отмена");
        cancel.setOnAction(event -> {
                    clearFields();
                    subNavStage.close();
                }
        );
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(add, cancel);
        root.add(buttonBox, 1, 5);

        Scene navBlockScene = new Scene(root, 350, 250);
        navBlockScene.setOnKeyPressed(keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case ENTER: {
                            if ((new ValidateFields().validateString(blockNumberText.getText())) &&
                                    (new ValidateFields().validateString(stateNumberText.getText()) &&
                                            (typeBox.getValue() != "")))
                                NavigationBlock.getInstance().addData(blockNumberText.getText(),
                                        (String) typeBox.getSelectionModel().getSelectedItem(),
                                        stateNumberText.getText(),
                                        commentText.getText());
                            else new FormsAlerts().getWarningAlert("Отправка формы", "Все поля обязательны для заполнения");
                        }
                    }
                }
        );
        subNavStage.setScene(navBlockScene);
        subNavStage.show();
    }

    public void display(int block, String type, String state, String comment) {
        GridPane root = initScene();

        subNavStage.setTitle("Изменить");

        blockNumberText.setText(String.valueOf(block));
        typeBox.getSelectionModel().select(type);
        stateNumberText.setText(state);
        commentText.setText(comment);

        Button add = new Button("Сохранить");
        add.setOnAction(event -> {
            if ((new ValidateFields().validateString(blockNumberText.getText())) &&
                    (new ValidateFields().validateString(stateNumberText.getText()) &&
                            (typeBox.getValue() != "")))
                NavigationBlock.getInstance().updateData(blockNumberText.getText(),
                        (String) typeBox.getValue(),
                        stateNumberText.getText(),
                        block,
                        commentText.getText());
            else
                new FormsAlerts().getWarningAlert("Отправка формы", "Все поля обязательны для заполнения");
        });
        Button cancel = new Button("Отмена");
        cancel.setOnAction(event -> {
                    clearFields();
                    subNavStage.close();
                }
        );
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(add, cancel);
        root.add(buttonBox, 1, 5);

        Scene navBlockScene = new Scene(root, 350, 250);
        navBlockScene.setOnKeyPressed(keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case ENTER: {
                            if ((new ValidateFields().validateString(blockNumberText.getText())) &&
                                    (new ValidateFields().validateString(stateNumberText.getText()) &&
                                            (typeBox.getValue() != null)))
                                NavigationBlock.getInstance().updateData(blockNumberText.getText(),
                                        (String) typeBox.getValue(),
                                        stateNumberText.getText(),
                                        block,
                                        commentText.getText());
                            else
                                new FormsAlerts().getWarningAlert("Отправка формы", "Все поля обязательны для заполнения");
                        }
                    }
                }
        );
        subNavStage.setScene(navBlockScene);
        subNavStage.show();
    }

    public void clearFields() {
        blockNumberText.clear();
        typeBox.getSelectionModel().clearSelection();
        stateNumberText.clear();
    }

}
