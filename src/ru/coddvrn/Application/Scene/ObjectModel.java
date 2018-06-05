package ru.coddvrn.Application.Scene;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Connection.Connect;
import ru.coddvrn.Application.Entity.ObjectTable;
import ru.coddvrn.Application.Icons.IconsLoader;
import ru.coddvrn.Application.Notifications.Notification;
import ru.coddvrn.Application.Scene.SubScene.SubObject;

import java.sql.*;
import java.util.Optional;

public class ObjectModel {
    // Singleton
    private ObjectModel() {
    }

    private static ObjectModel instance;

    public static ObjectModel getInstance() {
        if (instance == null)
            instance = new ObjectModel();
        return instance;
    }

    // Create data Collection
    private ObservableList<ObjectTable> data = FXCollections.observableArrayList();
    // Create table
    private TableView<ObjectTable> table = new TableView<>();

    private Label rowCounterLabel = new Label();

    private void initColumns() {
        // Create table columns
        TableColumn stateNumberColumn = new TableColumn("Гос. Номер ТС");
        //Initialize columns
        stateNumberColumn.setPrefWidth(130);
        stateNumberColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("stateNumber"));

        TableColumn carBrandColumn = new TableColumn("Марка ТС");
        carBrandColumn.setPrefWidth(100);
        carBrandColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("carBrand"));

        TableColumn yearReleasedColumn = new TableColumn("Год выпуска");
        yearReleasedColumn.setPrefWidth(100);
        yearReleasedColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, Integer>("yearReleased"));

        TableColumn carTypeColumn = new TableColumn("Тип ТС");
        carTypeColumn.setPrefWidth(100);
        carTypeColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("carType"));

        TableColumn lastTimeColumn = new TableColumn("Время последнего отклика");
        lastTimeColumn.setPrefWidth(150);
        lastTimeColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, Object>("lastTime"));

        TableColumn speedColumn = new TableColumn("Последняя скорость");
        speedColumn.setPrefWidth(100);
        speedColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, Integer>("lastSpeed"));

        TableColumn routeColumn = new TableColumn("Маршрут");
        routeColumn.setPrefWidth(100);
        routeColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("routsName"));

        TableColumn lastStationTimeColumn = new TableColumn("Время последней остановки");
        lastStationTimeColumn.setPrefWidth(150);
        lastStationTimeColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, Object>("lastStationTime"));

        TableColumn carrierColumn = new TableColumn("Перевозчик");
        carrierColumn.setPrefWidth(150);
        carrierColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("carrier"));

        TableColumn installerColumn = new TableColumn("Установщик");
        installerColumn.setPrefWidth(100);
        installerColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("installer"));

        TableColumn registrationTimeColumn = new TableColumn("Дата Регистрации");
        registrationTimeColumn.setPrefWidth(120);
        registrationTimeColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("dateInserted"));

        TableColumn statusColumn = new TableColumn("Состояние");
        statusColumn.setPrefWidth(120);
        statusColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("status"));

        TableColumn phoneColumn = new TableColumn("Номер телефона");
        phoneColumn.setPrefWidth(120);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, Long>("phoneNumber"));

        TableColumn commentsColumn = new TableColumn("Комментарий");
        commentsColumn.setPrefWidth(150);
        commentsColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("comment"));
        //Add columns to the table
        table.getColumns().addAll(stateNumberColumn, carBrandColumn, yearReleasedColumn, carTypeColumn, lastTimeColumn,
                routeColumn, speedColumn, lastStationTimeColumn, carrierColumn, installerColumn,
                statusColumn, phoneColumn, commentsColumn);
        table.setTableMenuButtonVisible(true);
        table.setEditable(false);

        /*table.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1) {
                }
            }
        });*/
             statusColumn.setCellFactory(column -> {
            return new TableCell<ObjectTable, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                         if (item == null || empty) {
                             setText(null);
                        setStyle("");}
                    else if (item.contains("Выведен")) {
                             setText(item);
                             setTextFill(Color.BLACK);
                             setStyle("-fx-background-color: rgb(241,35,30)");
                        } else {
                             setText(item);
                             setTextFill(Color.BLACK);
                             setStyle("-fx-background-color: rgb(23,187,43)");
                         }
                    }
                };
            });
    }

    private void initRowsCounter() {
        rowCounterLabel.setText("Количество записей: " + data.size());
    }

    public void display() {
        // New window (Stage)
        Stage objStage = new Stage();
        objStage.initModality(Modality.WINDOW_MODAL);
//        objStage.setFullScreen(true);
        objStage.setTitle("Справочник навигационных блоков");
        initColumns();
        fillTable();
        // Add vertical and horizontal scrollPane
        initScrollPane();
        initRowsCounter();
        data.addListener(new ListChangeListener<ObjectTable>() {
            @Override
            public void onChanged(Change<? extends ObjectTable> c) {
                initRowsCounter();
            }
        });
        // Create buttons
        Button add = new Button("Добавить...", IconsLoader.getInstance().getAddIcon());
        add.setOnAction(event -> SubObject.getInstance().display());

        Button edit = new Button("Изменить...", IconsLoader.getInstance().getEditIcon());
        edit.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
//        edit.setOnAction(event -> SubObject.getInstance().display(table.getSelectionModel().getSelectedItem().getId(),
//                table.getSelectionModel().getSelectedItem().getName(),
//                table.getSelectionModel().getSelectedItem().getLat(),
//                table.getSelectionModel().getSelectedItem().getLon(),
//                table.getSelectionModel().getSelectedItem().getAzmth()));

        Button delete = new Button("Удалить", IconsLoader.getInstance().getDeleteIcon());
        delete.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane pane = alert.getDialogPane();
            pane.setPrefSize(400.0, 120.0);
            alert.setResizable(true);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(null);
            alert.setContentText("Удалить объекст с Гос Номером " + table.getSelectionModel().getSelectedItem().getStateNumber() + " ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                deleteData(table.getSelectionModel().getSelectedItem().getStateNumber());
            }
        });
        Button refresh = new Button("Обновить", IconsLoader.getInstance().getRefreshIcon());
        refresh.setOnAction(event -> refreshTable());

        rowCounterLabel.setFont(new Font("Arial", 14));
        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(add, edit, delete, refresh);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(table);
        stackPane.setPadding(new Insets(10, 10, 15, 10));
        HBox rowCounterHbox = new HBox();
        rowCounterHbox.getChildren().add(rowCounterLabel);
        rowCounterHbox.setPadding(new Insets(10, 0, 10, 10));
        BorderPane root = new BorderPane();
        root.setTop(hbox);
        root.setCenter(stackPane);
        root.setBottom(rowCounterHbox);
        // Set scene
        Scene navBlockScene = new Scene(root, 1080, 720);
        objStage.setScene(navBlockScene);
        objStage.show();
        objStage.setOnCloseRequest(event -> data.clear());
    }

    private void initScrollPane() {
        // Add vertical and horizontal scrollPane
        ScrollPane sp = new ScrollPane(table);
        sp.setPrefSize(600, 200);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setHmax(10);
        sp.setHvalue(10);
        sp.setDisable(false);
    }

    public void fillTable() {
        final String query = "SELECT o.name_ AS state, car_brand.cb_name_ AS brand, o.year_release_ AS year_," +
                "car_type_.name_ AS type, SUBSTRING (100 + EXTRACT (DAY FROM o.last_time_) FROM 2 FOR 2)||'.'" +
                "   || SUBSTRING (100 + EXTRACT(MONTH FROM o.last_time_) FROM 2 FOR 2)||'.'" +
                "   || EXTRACT (YEAR FROM o.last_time_)||' '" +
                "   || SUBSTRING (100 + EXTRACT (HOUR FROM o.last_time_)FROM 2 FOR 2)||':'" +
                "   || SUBSTRING (100 + EXTRACT(MINUTE FROM o.last_time_) FROM 2 FOR 2)||':'" +
                "   || SUBSTRING (100 + EXTRACT(SECOND FROM o.last_time_) FROM 2 FOR 2) AS lastTime, " +
                "o.last_speed_ AS lastspeed, routs.name_ AS rout, " +
                "   SUBSTRING (100 + EXTRACT (DAY FROM  o.last_station_time_) FROM 2 FOR 2)||'.'" +
                "   || SUBSTRING (100 + EXTRACT(MONTH FROM  o.last_station_time_) FROM 2 FOR 2)||'.'" +
                "   || EXTRACT (YEAR FROM  o.last_station_time_)||' '" +
                "   || SUBSTRING (100 + EXTRACT (HOUR FROM  o.last_station_time_)FROM 2 FOR 2)||':'" +
                "   || SUBSTRING (100 + EXTRACT(MINUTE FROM  o.last_station_time_) FROM 2 FOR 2)||':'" +
                "   || SUBSTRING (100 + EXTRACT(SECOND FROM  o.last_station_time_) FROM 2 FOR 2) AS lastTimeStation, " +
                "p.name_ AS carrier, providers.name_ AS installer, " +
                "   SUBSTRING (100 + EXTRACT (DAY FROM  o.date_inserted_) FROM 2 FOR 2)||'.'" +
                "   || SUBSTRING (100 + EXTRACT(MONTH FROM  o.date_inserted_) FROM 2 FOR 2)||'.'" +
                "   || EXTRACT (YEAR FROM  o.date_inserted_) AS registrDate," +
                "       CASE o.obj_output_" +
                "         WHEN 0 THEN 'Выведен'" +
                "         ||' ('" +
                "         ||EXTRACT (DAY FROM o.obj_output_date_)|| '.'" +
                "         ||EXTRACT(MONTH FROM o.obj_output_date_)||'.'" +
                "         ||EXTRACT(year from o.obj_output_date_)||')'" +
                "         WHEN 1 THEN 'Активен'" +
                "       END AS status," +
                "       o.phone_ AS phone, o.user_comment_ AS comment " +
                "FROM objects o " +
                "LEFT JOIN car_brand ON o.car_brand_ = car_brand.cb_id_ " +
                "LEFT JOIN car_type_ ON o.vehicle_type_ = car_type_.ct_id_ " +
                "LEFT JOIN routs ON o.last_rout_ = routs.id_ " +
                "LEFT JOIN projects p ON o.proj_id_ = p.id_ " +
                "LEFT JOIN providers ON o.provider_ = providers.id_ ";
        try (Connection connection = Connect.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                data.add(new ObjectTable(
                        resultSet.getString("state"),
                        resultSet.getString("brand"),
                        resultSet.getInt("year_"),
                        resultSet.getString("type"),
                        resultSet.getObject("lastTime"),
                        resultSet.getShort("lastspeed"),
                        resultSet.getString("rout"),
                        resultSet.getObject("lastTimeStation"),
                        resultSet.getString("carrier"),
                        resultSet.getString("installer"),
                        resultSet.getString("registrDate"),
                        resultSet.getString("status"),
                        resultSet.getLong("phone"),
                        resultSet.getString("comment")
                ));

            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        table.setItems(data);
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
            Notification.getSuccessAdd();
//            SubObject.getInstance().clearFields();
            refreshTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
            Notification.getErrorAdd(exception);
        }
    }

    public void updateData(TextField nameText, TextField lonText, TextField latText, TextField azmthText, int idValue) {
        final String query = "UPDATE bs SET name = ? ,lat = ?,lon = ? ,azmth = ? WHERE id = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameText.getText());
            preparedStatement.setDouble(2, Double.parseDouble(latText.getText()));
            preparedStatement.setDouble(3, Double.parseDouble(lonText.getText()));
            preparedStatement.setInt(4, Integer.parseInt(azmthText.getText()));
            preparedStatement.setInt(5, idValue);
            preparedStatement.execute();
            Notification.getSuccessEdit();
//            SubObject.getInstance().getStage().close();
            refreshTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
            Notification.getErrorEdit(exception);
        }
    }

    private void deleteData(String stateName) {
        final String query = "DELETE FROM bs WHERE id = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, stateName);
            preparedStatement.executeUpdate();
            Notification.getSuccessDelete();
            refreshTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
            Notification.getErrorDelete(exception);
        }
    }

    public void refreshTable() {
        data.clear();
        fillTable();
    }
}
