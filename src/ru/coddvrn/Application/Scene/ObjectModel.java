package ru.coddvrn.Application.Scene;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import ru.coddvrn.Application.Connection.Connect;
import ru.coddvrn.Application.Entity.ObjectTable;
import ru.coddvrn.Application.Icons.IconsLoader;
import ru.coddvrn.Application.Notifications.Notification;
import ru.coddvrn.Application.Repository.Query;
import ru.coddvrn.Application.Scene.SubScene.SubObject;

import java.sql.*;
import java.util.Optional;
import java.util.function.Predicate;

public class ObjectModel {
    // Singleton
    private ObjectModel() {
        initColumns();
        initScrollPane();
    }

    private static ObjectModel instance;

    public static ObjectModel getInstance() {
        if (instance == null)
            instance = new ObjectModel();
        return instance;
    }

    private TextField searchField;

    public String getSearchValue() {
        return searchField.getText();
    }

    // Create data Collection
    private ObservableList<ObjectTable> data = FXCollections.observableArrayList();
    // Create table
    private TableView<ObjectTable> table = new TableView<>();
    private FilteredList<ObjectTable> filteredData = new FilteredList<>(data, e -> true);
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

        TableColumn registrationDateColumn = new TableColumn("Дата Регистрации");
        registrationDateColumn.setPrefWidth(120);
        registrationDateColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("dateInserted"));

        TableColumn statusColumn = new TableColumn("Состояние");
        statusColumn.setPrefWidth(140);
        statusColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("status"));

        TableColumn phoneColumn = new TableColumn("Номер телефона");
        phoneColumn.setPrefWidth(120);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, Long>("phoneNumber"));

        TableColumn commentsColumn = new TableColumn("Комментарий");
        commentsColumn.setPrefWidth(150);
        commentsColumn.setCellValueFactory(new PropertyValueFactory<ObjectTable, String>("comment"));
        //Add columns to the table
        table.getColumns().addAll(stateNumberColumn, routeColumn, statusColumn, lastTimeColumn,
                lastStationTimeColumn, phoneColumn, commentsColumn, installerColumn, carrierColumn,
                speedColumn, carTypeColumn, registrationDateColumn, yearReleasedColumn, carBrandColumn);
        table.setTableMenuButtonVisible(true);
        table.setEditable(false);

    }

    private void setStatusColor(TableColumn statusColumn) {
        statusColumn.setCellFactory(column -> {
            return new TableCell<ObjectTable, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(item);
                        setStyle("");
                    } else if (item.contains("Выведен")) {
                        setStyle("-fx-background-color: rgb(247,162,176)");
                        setTextFill(Color.BLACK);
                        setText(item);
                    } else {
                        setStyle("-fx-background-color: rgb(146,222,156)");
                        setTextFill(Color.BLACK);
                        setText(item);
                    }
                }
            };
        });
    }

    private void initRowsCounter() {
        rowCounterLabel.setText("Количество записей: " + data.size());
    }

    public void display() {
        // New window
        Stage objStage = new Stage();
        objStage.initModality(Modality.APPLICATION_MODAL);
//        objStage.setFullScreen(true);
        objStage.setTitle("Справочник объектов");
        fillTable();
        setStatusColor(table.getColumns().get(2));
        // Add vertical and horizontal scrollPane
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
            pane.setPrefSize(350.0, 120.0);
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

        searchField = TextFields.createClearableTextField();
        searchField.setPromptText("Поиск по номеру или телефону");
        searchField.setMinWidth(200);
        searchByItem(searchField);

        VBox searchBox = new VBox();
        searchBox.setPadding(new Insets(17, 0, 0, 0));
        searchBox.getChildren().add(searchField);

        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(add, edit, delete, refresh, searchBox);
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
        Scene navBlockScene = new Scene(root, 1080, 700);
        objStage.setScene(navBlockScene);
        objStage.show();
        objStage.setOnCloseRequest(event -> {
            data.clear();
            filteredData.clear();
        });
    }

    private void initScrollPane() {
        // Add vertical and horizontal scrollPane
        ScrollPane sp = new ScrollPane(table);
        sp.setPrefSize(1080, 720);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setHmax(3);
        sp.setHvalue(3);
    }

    private void searchByItem(TextField searchField) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super ObjectTable>) obj -> {
                String lowerCaseFilter = newValue.toLowerCase();
                if (newValue == null || newValue.isEmpty())
                    return true;
                else if (obj.getStateNumber().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (String.valueOf(obj.getPhoneNumber()).startsWith(lowerCaseFilter))
                    return true;

                return false;
            });
            SortedList<ObjectTable> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
            rowCounterLabel.setText("Количество записей: " + sortedData.size());
            setStatusColor(table.getColumns().get(2));
        });
    }

    public void fillTable() {
        final String query = new Query().getObjectQuery();
        try (Connection connection = Connect.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                data.add(new ObjectTable(
                        resultSet.getString("state"),
                        resultSet.getString("rout"),
                        resultSet.getString("status"),
                        resultSet.getObject("lastTime"),
                        resultSet.getObject("lastTimeStation"),
                        resultSet.getLong("phone"),
                        resultSet.getString("comment"),
                        resultSet.getString("installer"),
                        resultSet.getString("carrier"),
                        resultSet.getShort("lastspeed"),
                        resultSet.getString("type"),
                        resultSet.getString("registrDate"),
                        resultSet.getInt("year_"),
                        resultSet.getString("brand")
                ));

            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        table.setItems(data);
    }

    public void fillTable(String param) {
        final String query = new Query().getObjectQuery() + "  WHERE o.name_ LIKE ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
//            ResultSet resultSet = preparedStatement.executeQuery(query);
            preparedStatement.setString(1, "%" + param + "%");
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
//            preparedStatement.setString(2, param + "%");
            while (resultSet.next()) {
                data.add(new ObjectTable(
                        resultSet.getString("state"),
                        resultSet.getString("rout"),
                        resultSet.getString("status"),
                        resultSet.getObject("lastTime"),
                        resultSet.getObject("lastTimeStation"),
                        resultSet.getLong("phone"),
                        resultSet.getString("comment"),
                        resultSet.getString("installer"),
                        resultSet.getString("carrier"),
                        resultSet.getShort("lastspeed"),
                        resultSet.getString("type"),
                        resultSet.getString("registrDate"),
                        resultSet.getInt("year_"),
                        resultSet.getString("brand")
                ));

            }
            resultSet.close();
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
            new Notification().getSuccessAdd();
//            SubObject.getInstance().clearFields();
            refreshTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
            new Notification().getErrorAdd(exception);
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
//            SubObject.getInstance().getStage().close();
            refreshTable();
            new Notification().getSucessEdit();
        } catch (SQLException exception) {
            exception.printStackTrace();
            new Notification().getErrorEdit(exception);
        }
    }

    private void deleteData(String stateName) {
        final String query = "DELETE FROM bs WHERE id = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, stateName);
            preparedStatement.executeUpdate();
            new Notification().getSuccessDelete();
            refreshTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
            new Notification().getErrorDelete(exception);
        }
    }

    public void refreshTable() {
        data.clear();
        if (getSearchValue().isEmpty()) {
            fillTable();
        } else {
            fillTable(getSearchValue());
        }
    }
}