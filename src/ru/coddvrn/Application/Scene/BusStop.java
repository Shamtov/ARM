package ru.coddvrn.Application.Scene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Connection.Connect;
import ru.coddvrn.Application.Entity.BusStopTable;
import ru.coddvrn.Application.Notifications.Notification;
import ru.coddvrn.Application.Scene.SubScene.BusAdd;
import ru.coddvrn.Application.Scene.SubScene.BusEdit;

import java.sql.*;
import java.util.Optional;

public class BusStop {
    // Singleton
    private static BusStop instance;

    public static BusStop getInstance() {
        if (instance == null)
            instance = new BusStop();
        return instance;
    }

    // Create data Collection
    protected ObservableList<BusStopTable> data = FXCollections.observableArrayList();
    // Create table
    protected TableView<BusStopTable> table = new TableView<>();

    private void initCol() {
        // Create table columns
        TableColumn idColumn = new TableColumn("ID остановки");
        //Initialize columns
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<BusStopTable, Integer>("id"));

        TableColumn nameColumn = new TableColumn("Название остановки");
        nameColumn.setMinWidth(350);
        nameColumn.setCellValueFactory(new PropertyValueFactory<BusStopTable, String>("name"));

        TableColumn latitudeColumn = new TableColumn("Широта");
        latitudeColumn.setMinWidth(100);
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<BusStopTable, Double>("lat"));

        TableColumn longitudeColumn = new TableColumn("Долгота");
        longitudeColumn.setMinWidth(100);
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<BusStopTable, Double>("lon"));

        TableColumn azmthColumn = new TableColumn("Азимут");
        azmthColumn.setMinWidth(50);
        azmthColumn.setCellValueFactory(new PropertyValueFactory<BusStopTable, Integer>("azmth"));

        //Add columns to the table
        table.getColumns().addAll(idColumn, nameColumn, longitudeColumn, latitudeColumn, azmthColumn);
        table.setTableMenuButtonVisible(true);
        table.setEditable(true);
    }

    public void display() {
        // New window (Stage)
        Stage dirStopsStage = new Stage();
        dirStopsStage.initModality(Modality.WINDOW_MODAL);
        dirStopsStage.setTitle("Справочник остановок");
        initCol();
        fillTable();
        table.setItems(data);
//        table.contextMenuProperty();
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
        add.setOnAction(event -> new BusAdd().display());

        Button edit = new Button("Изменить");
        edit.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        edit.setOnAction(event -> new BusEdit().display(table.getSelectionModel().getSelectedItem().getId(),
                table.getSelectionModel().getSelectedItem().getName(),
                table.getSelectionModel().getSelectedItem().getLat(),
                table.getSelectionModel().getSelectedItem().getLon(),
                table.getSelectionModel().getSelectedItem().getAzmth()));

        Button delete = new Button("Удалить");
        delete.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(null);
            alert.setContentText("Вы действительно хотите удалить запись?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                deleteData(table.getSelectionModel().getSelectedItem().getId());
                table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
            }
        });
        Button refresh = new Button("Обновить");
        refresh.setOnAction(event -> refreshTable());

        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(add, edit, delete, refresh);
        hbox.setPadding(new Insets(20, 10, 10, 10));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(table);
        stackPane.setPadding(new Insets(10,10,30,10));
        BorderPane root = new BorderPane();
        root.setTop(hbox);
        root.setCenter(stackPane);
        // Set scene
        Scene dirStopsScene = new Scene(root, 800, 500);
        dirStopsStage.setScene(dirStopsScene);
        dirStopsStage.show();
    }

    public void fillTable() {
        final String query = "SELECT id ,name,lat,lon,azmth FROM bs";
        try (Connection connection = Connect.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                data.add(new BusStopTable(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("lat"),
                        resultSet.getDouble("lon"),
                        resultSet.getInt("azmth")
                ));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        this.table.setItems(data);
    }

    public void addData(TextField nameText, TextField lonText, TextField latText, TextField azmthText) {
        final String query = "INSERT INTO bs (name ,lat ,lon ,azmth ) VALUES (?,?,?,?)";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameText.getText());
            preparedStatement.setDouble(2, Double.parseDouble(latText.getText()));
            preparedStatement.setDouble(3, Double.parseDouble(lonText.getText()));
            preparedStatement.setInt(4, Integer.parseInt(azmthText.getText()));
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
            Notification.getErrorAdd(exception);
        }
        new Notification().getSuccessAdd();
        refreshTable();
        new BusAdd().clearFields(nameText, lonText, latText, azmthText);
    }

    protected void updateData(TextField nameText, TextField lonText, TextField latText, TextField azmthText, int idValue) {
        final String query = "UPDATE bs SET name = ? ,lat = ?,lon = ? ,azmth = ? WHERE id = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameText.getText());
            preparedStatement.setDouble(2, Double.parseDouble(latText.getText()));
            preparedStatement.setDouble(3, Double.parseDouble(lonText.getText()));
            preparedStatement.setInt(4, Integer.parseInt(azmthText.getText()));
            preparedStatement.setInt(5, idValue);
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
            Notification.getErrorEdit(exception);
        }
        Notification.getSuccessEdit();
        refreshTable();
    }

    private void deleteData(int idValue) {
        final String query = "DELETE FROM bs WHERE id = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idValue);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            Notification.getErrorDelete(exception);
        }
        refreshTable();
    }

    public void refreshTable() {
        this.data.clear();
        fillTable();
    }
}
