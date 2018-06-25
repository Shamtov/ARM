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
import org.controlsfx.control.textfield.TextFields;
import ru.coddvrn.Application.Alerts.FormsAlerts;
import ru.coddvrn.Application.Repository.AutoCompleteComboBoxListener;
import ru.coddvrn.Application.Repository.List;
import ru.coddvrn.Application.Scene.ObjectModel;
import ru.coddvrn.Application.Validate.ValidateFields;

public class SubObject {
    private SubObject() {
        super();
    }

    private static SubObject instance;

    public static SubObject getInstance() {
        if (instance == null)
            instance = new SubObject();
        return instance;
    }

    private TextField stateNumberText = TextFields.createClearableTextField();
    private ComboBox carrier = new ComboBox(new List().getCarrierList());
    private ComboBox installer = new ComboBox(new List().getinstallerList());
    private ComboBox routes = new ComboBox(new List().getRouts());
    private TextField phoneNmberText = TextFields.createClearableTextField();
    private TextField commentText = TextFields.createClearableTextField();
    private ComboBox brand = new ComboBox(new List().getBrand());
    private TextField yearText = TextFields.createClearableTextField();

    private Stage subObjStage = new Stage();

    public Stage getStage() {
        if (subObjStage == null)
            subObjStage.initModality(Modality.APPLICATION_MODAL);
        return subObjStage;
    }

    private GridPane initScene() {
        carrier.setMaxWidth(150);
        installer.setMaxWidth(150);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 10, 10, 10));
        gridPane.setHgap(25);
        gridPane.setVgap(15);

        Label stateLabel = new Label("Гос. номер ТС*");
        stateLabel.setFont(new Font("SanSerif", 14));
        stateNumberText.setPromptText("а000аа36");
        stateNumberText.setMinWidth(150);
        GridPane.setHalignment(stateLabel, HPos.LEFT);
        gridPane.add(stateLabel, 0, 1);
        gridPane.add(stateNumberText, 1, 1);

        Label routeLabel = new Label("Маршрут");
        routeLabel.setFont(new Font("SanSerif", 14));
        GridPane.setHalignment(routeLabel, HPos.LEFT);
        routes.getSelectionModel().select("Неизвестный");
        new AutoCompleteComboBoxListener<>(routes);
        gridPane.add(routeLabel, 0, 2);
        gridPane.add(routes, 1, 2);

        Label carrierLabel = new Label("Перевозчик*");
        carrierLabel.setFont(new Font("SanSerif", 14));
        GridPane.setHalignment(carrierLabel, HPos.LEFT);
        new AutoCompleteComboBoxListener<>(carrier);
        gridPane.add(carrierLabel, 0, 3);
        gridPane.add(carrier, 1, 3);

        Label installerLabel = new Label("Установщик");
        installerLabel.setFont(new Font("SanSerif", 14));
        GridPane.setHalignment(installerLabel, HPos.LEFT);
        installer.getSelectionModel().select("Не закреплены за установщиком");
        new AutoCompleteComboBoxListener<>(installer);
        gridPane.add(installerLabel, 0, 4);
        gridPane.add(installer, 1, 4);

        Label phoneLabel = new Label("Телефон*");
        phoneLabel.setFont(new Font("SanSerif", 14));
        GridPane.setHalignment(phoneLabel, HPos.LEFT);
        gridPane.add(phoneLabel, 0, 5);
        gridPane.add(phoneNmberText, 1, 5);

        Label brandLabel = new Label("Марка ТС");
        brandLabel.setFont(new Font("SanSerif", 14));
        GridPane.setHalignment(phoneLabel, HPos.LEFT);
        brand.getSelectionModel().select(0);
        new AutoCompleteComboBoxListener<>(brand);
        gridPane.add(brandLabel, 0, 6);
        gridPane.add(brand, 1, 6);

        Label yearLabel = new Label("Год выпуска");
        yearLabel.setFont(new Font("SanSerif", 14));
        yearText.setMinWidth(150);
        yearText.setText("2018");
        GridPane.setHalignment(phoneLabel, HPos.LEFT);
        gridPane.add(yearLabel, 0, 7);
        gridPane.add(yearText, 1, 7);

        Label commentLabel = new Label("Комментарий");
        commentLabel.setFont(new Font("SanSerif", 14));
        GridPane.setHalignment(commentText, HPos.LEFT);
        gridPane.add(commentLabel, 0, 8);
        gridPane.add(commentText, 1, 8);
        return gridPane;
    }

    public void display() {

        GridPane root = initScene();

        subObjStage.setTitle("Добавить");
        Button add = new Button("Сохранить");
        add.setOnAction(event -> {
            if ((new ValidateFields().validateString(stateNumberText.getText())) &&
                    ((new ValidateFields().validateString(phoneNmberText.getText()))))
                ObjectModel.getInstance().addData(stateNumberText.getText(),
                        (String) carrier.getSelectionModel().getSelectedItem(),
                        (String) routes.getSelectionModel().getSelectedItem(),
                        (String) installer.getSelectionModel().getSelectedItem(),
                        (String) brand.getSelectionModel().getSelectedItem(),
                        phoneNmberText.getText(),
                        yearText.getText(),
                        commentText.getText());

            else
                new FormsAlerts().getWarningAlert("Отправка формы", "Поля с * не должны быть пустыми");
        });
        Button cancel = new Button("Отмена");
        cancel.setOnAction(event -> {
                    clearFields();
                    subObjStage.close();
                }
        );
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(add, cancel);
        root.add(buttonBox, 1, 9);

        Scene objectScene = new Scene(root, 380, 435);
        objectScene.setOnKeyPressed(keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case ENTER: {
                            if ((new ValidateFields().validateString(stateNumberText.getText())) &&
                                    ((new ValidateFields().validateString(phoneNmberText.getText()))))
                                ObjectModel.getInstance().addData(stateNumberText.getText(),
                                        (String) carrier.getSelectionModel().getSelectedItem(),
                                        (String) routes.getSelectionModel().getSelectedItem(),
                                        (String) installer.getSelectionModel().getSelectedItem(),
                                        (String) brand.getSelectionModel().getSelectedItem(),
                                        phoneNmberText.getText(),
                                        yearText.getText(),
                                        commentText.getText());

                            else
                                new FormsAlerts().getWarningAlert("Отправка формы", "Поля с * не должны быть пустыми");
                        }
                    }
                }
        );
        subObjStage.setScene(objectScene);
        subObjStage.show();
    }
    public void display(String stateVal, String carrierVal, String routVal, String installerVal,
                        String carBrandVal, long phoneVal, int yearVal, String commentVal) {
        GridPane root = initScene();

        subObjStage.setTitle("Изменить");

        stateNumberText.setText(stateVal);
        carrier.getSelectionModel().select(carrierVal);
        routes.getSelectionModel().select(routVal);
        installer.getSelectionModel().select(installerVal);
        brand.getSelectionModel().select(carBrandVal);
        phoneNmberText.setText(String.valueOf(phoneVal));
        yearText.setText(String.valueOf(yearVal));
        commentText.setText(commentVal);

        Button add = new Button("Сохранить");
        add.setOnAction(event -> {
            if ((new ValidateFields().validateString(stateNumberText.getText())) &&
                    ((new ValidateFields().validateString(phoneNmberText.getText()))))
                ObjectModel.getInstance().updateData(stateNumberText.getText(),
                        (String) carrier.getSelectionModel().getSelectedItem(),
                        (String) routes.getSelectionModel().getSelectedItem(),
                        (String) installer.getSelectionModel().getSelectedItem(),
                        (String) brand.getSelectionModel().getSelectedItem(),
                        phoneNmberText.getText(),
                        yearText.getText(),
                        commentText.getText(),
                        stateVal);

            else
                new FormsAlerts().getWarningAlert("Отправка формы", "Поля с * не должны быть пустыми");
        });
        Button cancel = new Button("Отмена");
        cancel.setOnAction(event -> {
                    clearFields();
                    subObjStage.close();
                }
        );
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(add, cancel);
        root.add(buttonBox, 1, 9);

        Scene objectScene = new Scene(root, 380, 435);
        objectScene.setOnKeyPressed(keyEvent -> {
                    switch (keyEvent.getCode()) {
                        case ENTER: {
                            if ((new ValidateFields().validateString(stateNumberText.getText())) &&
                                    ((new ValidateFields().validateString(phoneNmberText.getText()))))
                                ObjectModel.getInstance().updateData(stateNumberText.getText(),
                                        (String) carrier.getSelectionModel().getSelectedItem(),
                                        (String) routes.getSelectionModel().getSelectedItem(),
                                        (String) installer.getSelectionModel().getSelectedItem(),
                                        (String) brand.getSelectionModel().getSelectedItem(),
                                        phoneNmberText.getText(),
                                        yearText.getText(),
                                        commentText.getText(),
                                        stateVal
                                        );

                            else
                                new FormsAlerts().getWarningAlert("Отправка формы", "Поля с * не должны быть пустыми");
                        }
                    }
                }
        );
        subObjStage.setScene(objectScene);
        subObjStage.show();
    }

    public void clearFields() {
        stateNumberText.clear();
        carrier.getSelectionModel().clearSelection();
        routes.getSelectionModel().clearSelection();
        installer.getSelectionModel().clearSelection();
        phoneNmberText.clear();
        yearText.clear();
        commentText.clear();
    }
}