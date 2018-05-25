package ru.coddvrn.Application.Scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Connection.Connect;
import ru.coddvrn.Application.Entity.BusStop;
import ru.coddvrn.Application.Scene.SubScene.SubBusStopScene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusStopScene {
    public static void display() {
        // New window (Stage)
        Stage dirStopsStage = new Stage();
        dirStopsStage.initModality(Modality.WINDOW_MODAL);
        dirStopsStage.setTitle("Справочник остановок");
        // Create connection
        Connection con = null;
        //Create table
        TableView<BusStop> table = new TableView<>();

        ObservableList<BusStop> data = FXCollections.observableArrayList();

        //Create table
        TableColumn column1 = new TableColumn("Номер");
        column1.setMinWidth(50);
        column1.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn column2 = new TableColumn("Название остановки");
        column2.setMinWidth(300);
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn column3 = new TableColumn("Долгота");
        column3.setMinWidth(100);
        column3.setCellValueFactory(new PropertyValueFactory<>("lon"));

        TableColumn column4 = new TableColumn("Широта");
        column4.setMinWidth(100);
        column4.setCellValueFactory(new PropertyValueFactory<>("lat"));

        TableColumn column5 = new TableColumn("Маршрут ID");
        column5.setMinWidth(50);
        column5.setCellValueFactory(new PropertyValueFactory<>("route"));

        TableColumn column6 = new TableColumn("Конечная");
        column6.setMinWidth(50);
        column6.setCellValueFactory(new PropertyValueFactory<>("control"));
        //Add columns to the table
        table.getColumns().addAll(column1, column2, column3, column4, column5, column6);
        PreparedStatement pst = null;
        ResultSet rs = null;
        con = Connect.getConnect();
        try {
            // Setup database connection
            String query = "SELECT * FROM Bus_Stations";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new BusStop(
                        rs.getString("number_"),
                rs.getString("name_"),
                rs.getString("lon_"),
                rs.getString("lat_"),
                rs.getString("rout_"),
                rs.getString("control_")
                ));
                table.setItems(data);
            }
            pst.close();
            rs.close();
        } catch (SQLException exception) {
            exception.printStackTrace();

        } finally {
            // Close database connection
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Button add = new Button("Новая запись");
        add.setOnAction(event -> SubBusStopScene.display(column1, column2, column3, column4, column5, column6));
        Button edit = new Button("Изменить");
        Button delete = new Button("Удалить");

        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(add, edit, delete);
        hbox.setPadding(new Insets(20, 10, 10, 10));
        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(table, hbox);
        // New window (Scene)
        Scene dirStopsScene = new Scene(vbox, 900, 900);
        dirStopsStage.setScene(dirStopsScene);
        dirStopsStage.show();
    }
}
