package ru.coddvrn.Application.Scene;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Connection.Connect;
import ru.coddvrn.Application.Controller;
import ru.coddvrn.Application.Entity.BusStopTable;
import ru.coddvrn.Application.Scene.SubScene.SubBusStop;

import javax.xml.transform.sax.SAXSource;
import java.sql.*;
import java.util.Optional;

public class BusStop extends Controller {
    // Create data Collection
    protected ObservableList<BusStopTable> data = FXCollections.observableArrayList();
    // Create table
    protected TableView<BusStopTable> table = new TableView<>();

    private void initCol() {
        // Create table columns
        TableColumn numberColumn = new TableColumn("Номер");
        //Initialize columns
        numberColumn.setMinWidth(50);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn stopNameColumn = new TableColumn("Название остановки");
        stopNameColumn.setMinWidth(350);
        stopNameColumn.setCellValueFactory(new PropertyValueFactory<>("stopName"));

        TableColumn longitudeColumn = new TableColumn("Долгота");
        longitudeColumn.setMinWidth(100);
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("lon"));

        TableColumn latitudeColumn = new TableColumn("Широта");
        latitudeColumn.setMinWidth(100);
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("lat"));

        TableColumn routeIdColumn = new TableColumn("Маршрут ID");
        routeIdColumn.setMinWidth(50);
        routeIdColumn.setCellValueFactory(new PropertyValueFactory<>("route"));

        TableColumn controlColumn = new TableColumn("Конечная");
        controlColumn.setMinWidth(50);
        controlColumn.setCellValueFactory(new PropertyValueFactory<>("control"));
        //Add columns to the table
        table.getColumns().addAll(numberColumn, stopNameColumn, longitudeColumn, latitudeColumn, routeIdColumn, controlColumn);
        table.setTableMenuButtonVisible(true);
    }
    public void display() {
        // New window (Stage)
        Stage dirStopsStage = new Stage();
        dirStopsStage.initModality(Modality.WINDOW_MODAL);
        dirStopsStage.setTitle("Справочник остановок");
        initCol();
        fillTable();
        table.contextMenuProperty();
        // Add vertical and horizontal scrollPane
        ScrollPane sp = new ScrollPane(table);
        sp.setPrefSize(600, 200);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setHmax(3);
        sp.setHvalue(0);
        sp.setDisable(false);
        // Create buttons
        Button add = new Button("Новая запись...");
        add.setOnAction(event -> new SubBusStop().display("Добавить остановку"));
        Button edit = new Button("Изменить");
        edit.setOnAction(event -> new SubBusStop().display("Изменить остановку"));
        Button delete = new Button("Удалить");
  /*      delete.setOnAction(e ->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(null);
            alert.setContentText("Вы действительно хотите удалить запись?");
            Optional<ButtonType> action = alert.showAndWait();

            if(action.get() == ButtonType.OK){}});*/
        Button refresh = new Button("Обновить");
        refresh.setOnAction(event -> refreshTable());

        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(add, edit, delete, refresh);
        hbox.setPadding(new Insets(20, 10, 10, 10));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(table);
        VBox root = new VBox(20);
        root.getChildren().addAll(hbox, stackPane);

        // Set scene
        Scene dirStopsScene = new Scene(root, 800, 500);
        dirStopsStage.setScene(dirStopsScene);
        dirStopsStage.show();
    }

    public void fillTable() {
        System.out.println("fillTable");
        final String query = "SELECT * FROM Bus_Stations";
        try (Connection connection = Connect.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                data.add(new BusStopTable(
                        resultSet.getInt("number_"),
                        resultSet.getString("name_"),
                        resultSet.getDouble("lon_"),
                        resultSet.getDouble("lat_"),
                        resultSet.getInt("rout_"),
                        resultSet.getShort("control_")
                ));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        table.setItems(data);
    }

    public void addData(TextField numberText, TextField nameText, TextField lonText, TextField latText, TextField routeText, TextField controlText) {
        final String query = "INSERT INTO Bus_Stations (number_,name_,lon_,lat_,rout_,control_) VALUES (?,?,?,?,?,?)";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(numberText.getText()));
            preparedStatement.setString(2, nameText.getText());
            preparedStatement.setDouble(3, Double.parseDouble(lonText.getText()));
            preparedStatement.setDouble(4, Double.parseDouble(latText.getText()));
            preparedStatement.setInt(5, Integer.parseInt(routeText.getText()));
            preparedStatement.setShort(6, Short.parseShort(controlText.getText()));
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        SubBusStop.clearFields(numberText, nameText, lonText, latText, routeText, controlText);
    }
    public void updateData(TextField numberText, TextField nameText, TextField lonText, TextField latText, TextField routeText, TextField controlText) {
        final String query = "Update Bus_Stations SET number_ = ? ,name_ = ? ,lon_ = ?,lat_ = ? ,rout_ = ? ,control_ = ? WHERE number_ = ? and name_ = ? and rout_ = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(numberText.getText()));
            preparedStatement.setString(2, nameText.getText());
            preparedStatement.setDouble(3, Double.parseDouble(lonText.getText()));
            preparedStatement.setDouble(4, Double.parseDouble(latText.getText()));
            preparedStatement.setInt(5, Integer.parseInt(routeText.getText()));
            preparedStatement.setShort(6, Short.parseShort(controlText.getText()));
            preparedStatement.setInt(7, Integer.parseInt(numberText.getText()));
            preparedStatement.setString(8, nameText.getText());
            preparedStatement.setInt(9, Integer.parseInt(routeText.getText()));
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        SubBusStop.clearFields(numberText, nameText, lonText, latText, routeText, controlText);
    }
    private void deleteData(TextField numberText, TextField nameText, TextField lonText, TextField latText, TextField routeText, TextField controlText){
        final String query = "DELETE FROM Bus_Stations WHERE number_ = ? and name_ = ? and rout_ = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, Integer.parseInt(numberText.getText()));
            preparedStatement.setString(2, nameText.getText());
            preparedStatement.setInt(3, Integer.parseInt(routeText.getText()));
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void refreshTable() {
        System.out.println(data.size()+" refresh");
        this.data.clear();
        System.out.println(data.size()+" after clear");
        fillTable();
        System.out.println(data.size()+" after fill");
    }
}
