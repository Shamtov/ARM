package ru.coddvrn.Application.Scene.SubScene;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Connection.Connect;


public class SubBusStopScene {
    public static void display(TableColumn column1, TableColumn column2, TableColumn column3, TableColumn column4, TableColumn column5, TableColumn column6) {
        Stage subDirStopsStage = new Stage();
        subDirStopsStage.initModality(Modality.WINDOW_MODAL);
        subDirStopsStage.setTitle("Добавить остановку");

        GridPane root = new GridPane();
        root.setPadding(new Insets(20,10,10,10));
        root.setHgap(25);
        root.setVgap(15);

        Label label1 = new Label("Номер");
        label1.setFont(new Font("Arial", 14));
        TextField numberText = new TextField();
        numberText.setPromptText("Например, 0");
        numberText.setMinWidth(100);
        GridPane.setHalignment(label1, HPos.CENTER);
        root.add(label1,0,0);
        root.add(numberText,1,0);

        Label label2 = new Label("Название остановки");
        label2.setFont(new Font("Arial", 14));
        TextField nameText = new TextField();
        nameText.setPromptText("Перхоровича(конечная)");
        nameText.setMinWidth(200);
        GridPane.setHalignment(label2, HPos.CENTER);
        root.add(label2,0,1);
        root.add(nameText,1,1);

        Label label3 = new Label("Долгота");
        label3.setFont(new Font("Arial", 14));
        TextField lonText = new TextField();
        lonText.setPromptText("51,657974");
        lonText.setMinWidth(150);
        GridPane.setHalignment(label3, HPos.CENTER);
        root.add(label3,0,2);
        root.add(lonText,1,2);

        Label label4 = new Label("Широта");
        label4.setFont(new Font("Arial", 14));
        TextField latText = new TextField();
        latText.setPromptText("39,103365");
        latText.setMinWidth(150);
        GridPane.setHalignment(label4, HPos.CENTER);
        root.add(label4,0,3);
        root.add(latText,1,3);

        Label label5 = new Label("Маршрут");
        label5.setFont(new Font("Arial", 14));
        TextField routeText = new TextField();
        routeText.setPromptText("Например, 50");
        routeText.setMinWidth(100);
        GridPane.setHalignment(label5, HPos.CENTER);
        root.add(label5,0,4);
        root.add(routeText,1,4);

        Label label6 = new Label("Конечная");
        label6.setFont(new Font("Arial", 14));
        TextField controlText = new TextField();
        controlText.setPromptText("0 или 1");
        controlText.setMinWidth(100);
        GridPane.setHalignment(label6, HPos.CENTER);
        root.add(label6,0,5);
        root.add(controlText,1,5);

        Button add = new Button("Сохранить");
        add.setOnAction(event -> subDirStopsStage.close());

        Button cancel = new Button("Отмена");
        cancel.setOnAction(event -> subDirStopsStage.close());

        HBox buttonBox = new HBox(20);
//        buttonBox.setPadding(new Insets(10,10,10,10));
        buttonBox.getChildren().addAll(add,cancel);
        root.add(buttonBox,1,7);

        Scene subDirStopsScene = new Scene(root, 450, 350);
        subDirStopsStage.setScene(subDirStopsScene);
//        Connect con = new Connect();
        // Setup database connection
//        con.getConnect();
        // Close database connection
//        con.close();
        subDirStopsStage.show();
    }
}
