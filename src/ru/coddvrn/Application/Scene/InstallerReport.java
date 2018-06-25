package ru.coddvrn.Application.Scene;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.time.LocalDate;

public class InstallerReport {
    public void display() {
        Stage installerStage = new Stage();
        installerStage.setTitle("Контроль отклика навигационных блоков");
        GridPane root = new GridPane();
        root.setPadding(new Insets(20, 10, 10, 10));
        root.setHgap(25);
        root.setVgap(15);

        Label label1 = new Label("Время от");
        label1.setFont(new Font("Arial", 14));

        Label label2 = new Label("Время до");
        label2.setFont(new Font("Arial", 14));

        Label label3 = new Label("Дата от");
        label3.setFont(new Font("Arial", 14));

        Label label4 = new Label("Дата до");
        label4.setFont(new Font("Arial", 14));

        TextField timeFrom = TextFields.createClearableTextField();
        timeFrom.setMinWidth(100);
        TextField timeTo = TextFields.createClearableTextField();
        timeTo.setMinWidth(100);

        DatePicker dateFrom = new DatePicker();
//        dateFrom = new DatePicker(LocalDate.of(1998, 10, 8));

        DatePicker dateTo = new DatePicker();
//        dateTo = new DatePicker(LocalDate.of(1998, 10, 8));

        GridPane.setHalignment(label3, HPos.CENTER);
        root.add(label3, 0, 0);
        root.add(dateFrom, 1, 0);

        GridPane.setHalignment(label1, HPos.CENTER);
        root.add(label1, 0, 1);
        root.add(timeFrom, 1, 1);

        GridPane.setHalignment(label4, HPos.CENTER);
        root.add(label4, 0, 2);
        root.add(dateTo, 1, 2);

        GridPane.setHalignment(label2, HPos.CENTER);
        root.add(label2, 0, 3);
        root.add(timeTo, 1, 3);

        Button form = new Button("Сформировать");

        Button cancel = new Button("Отмена");
        cancel.setOnKeyPressed(escape -> {
            if (escape.getCode() == KeyCode.ESCAPE)
                installerStage.close();
        });

        cancel.setOnAction(event ->
                installerStage.close()
        );
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(form, cancel);
        root.add(buttonBox, 1, 5);

        Scene installerScene = new Scene(root, 380, 250);

        installerStage.setScene(installerScene);
        installerStage.show();
    }
}