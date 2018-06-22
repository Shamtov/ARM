package ru.coddvrn.Application.Scene;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
import ru.coddvrn.Application.Entity.RoutesTable;
import ru.coddvrn.Application.Icons.IconsLoader;
import ru.coddvrn.Application.Notifications.Notification;
import ru.coddvrn.Application.Scene.SubScene.SubRoute;

import java.sql.*;
import java.util.Optional;
import java.util.function.Predicate;

public class Route {
    private Route() {
        initColumns();
        initScrollPane();
    }

    private static Route instance;

    public static Route getInstance() {
        if (instance == null)
            instance = new Route();
        return instance;
    }

    // Create data Collection
    private ObservableList<RoutesTable> data = FXCollections.observableArrayList();
    // Create table
    private TableView<RoutesTable> table = new TableView<>();
    private FilteredList<RoutesTable> filteredData = new FilteredList<>(data, e -> true);
    private Label rowCounterLabel = new Label();

    private void initColumns() {
        // Create table columns
        TableColumn nameColumn = new TableColumn("Название маршрута");
        //Initialize columns
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<RoutesTable, String>("routeName"));

        TableColumn counterColumn = new TableColumn("Количество остановок");
        counterColumn.setMinWidth(50);
        counterColumn.setCellValueFactory(new PropertyValueFactory<RoutesTable, Integer>("busStopCount"));

        TableColumn statusColumn = new TableColumn("Статус");
        statusColumn.setMinWidth(150);
        statusColumn.setCellValueFactory(new PropertyValueFactory<RoutesTable, String>("status"));

        //Add columns to the table
        table.getColumns().addAll(nameColumn, counterColumn, statusColumn);
        table.setTableMenuButtonVisible(true);
        table.setEditable(false);
        table.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    SubRoute.getInstance().display(table.getSelectionModel().getSelectedItem().getRouteName(),
                            table.getSelectionModel().getSelectedItem().getBusStopCount(),
                            table.getSelectionModel().getSelectedItem().getStatus(),
                            table.getSelectionModel().getSelectedItem().getRouteName());
                }
            }
        });
    }

    private void setStatusColor(TableColumn statusColumn) {
        statusColumn.setCellFactory(column -> {
            return new TableCell<RoutesTable, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
//                        setText(item);
                        setStyle("");
                    } else if (item.contains("Не используется")) {
                        setStyle("-fx-background-color: rgb(247,162,176)");
                        setTextFill(Color.BLACK);
                        setText(item);
                    } else if (item.contains("Работает")){
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
        // New window (Stage)
        Stage routesStage = new Stage();
        routesStage.initModality(Modality.APPLICATION_MODAL);
        routesStage.setTitle("Справочник маршрутов");
        fillTable();
        setStatusColor(table.getColumns().get(2));
        initRowsCounter();
        data.addListener(new ListChangeListener<RoutesTable>() {
            @Override
            public void onChanged(Change<? extends RoutesTable> c) {
                initRowsCounter();
            }
        });

        // Create buttons
        Button add = new Button("Добавить...", IconsLoader.getInstance().getAddIcon());
        add.setOnAction(event -> SubRoute.getInstance().display());
//        add.setContentDisplay(ContentDisplay.TOP);

        Button edit = new Button("Изменить...", IconsLoader.getInstance().getEditIcon());
        edit.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        edit.setOnAction(event ->
                SubRoute.getInstance().display(table.getSelectionModel().getSelectedItem().getRouteName(),
                        table.getSelectionModel().getSelectedItem().getBusStopCount(),
                        table.getSelectionModel().getSelectedItem().getStatus(),
                        table.getSelectionModel().getSelectedItem().getRouteName())
        );

        Button delete = new Button("Удалить", IconsLoader.getInstance().getDeleteIcon());
        delete.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane pane = alert.getDialogPane();
            pane.setPrefSize(450.0, 120.0);
            alert.setResizable(true);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(null);
            alert.setContentText("Вы действительно хотите удалить маршрут с номером " + table.getSelectionModel().getSelectedItem().getRouteName() + " ?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                deleteData(table.getSelectionModel().getSelectedItem().getRouteName());
            }
        });
        Button refresh = new Button("Обновить", IconsLoader.getInstance().getRefreshIcon());
        refresh.setOnAction(event -> refreshTable());

        rowCounterLabel.setFont(new Font("Arial", 14));

        TextField searchField = TextFields.createClearableTextField();
        searchField.setPromptText("Поиск по маршруту");
        searchField.setMinWidth(200);
        searchByItem(searchField);

        HBox hbox = new HBox(15);

        VBox searchBox = new VBox();
        searchBox.setPadding(new Insets(17, 0, 0, 0));
        searchBox.getChildren().add(searchField);

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
        Scene routeScene = new Scene(root, 800, 600);

        routesStage.setScene(routeScene);
        routesStage.setOnCloseRequest(event -> {
            data.clear();
            filteredData.clear();
        });
        routesStage.show();
    }

    private void initScrollPane() {
        // Add vertical and horizontal scrollPane
        ScrollPane sp = new ScrollPane(table);
        sp.setPrefSize(600, 200);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setHmax(3);
        sp.setHvalue(0);
        sp.setDisable(false);
    }
    private void searchByItem(TextField searchField){
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super RoutesTable>) obj -> {
                String lowerCaseFilter = newValue.toLowerCase();
                if (newValue == null || newValue.isEmpty())
                    return true;
                else if (obj.getRouteName().toLowerCase().contains(lowerCaseFilter))
                    return true;

                return false;
            });
            SortedList<RoutesTable> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
            rowCounterLabel.setText("Количество записей: "+ sortedData.size());
            setStatusColor(table.getColumns().get(2));
        });}

    public void fillTable() {
        final String query = "SELECT routs.name_,(SELECT count(*) FROM bs_route WHERE bs_route.route_id = routs.id_) AS counter, " +
                "CASE routs.route_active_ WHEN 0 THEN 'Не используется' WHEN 1 THEN 'Работает' END AS new_active FROM routs" +
                " ORDER BY routs.name_ ";
        try (Connection connection = Connect.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                data.add(
                        new RoutesTable(
                                resultSet.getString("name_"),
                                resultSet.getInt("counter"),
                                resultSet.getString("new_active")
                        ));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        table.setItems(data);
    }

    public void addData(TextField nameValue, int statusValue) {
        final String query = "INSERT INTO routs (name_,route_active_) VALUES (?,?)";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameValue.getText());
            preparedStatement.setInt(2, statusValue);
            preparedStatement.execute();
            new Notification().getSuccessAdd();
            SubRoute.getInstance().clearFields();
            refreshTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
            new Notification().getErrorAdd(exception);
        }
    }

    public void updateData(TextField newNameValue, int statusValue, String oldNameValue) {
        final String query = "UPDATE routs SET name_ = ? ,route_active_ = ? WHERE name_ = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newNameValue.getText());
            preparedStatement.setInt(2, statusValue);
            preparedStatement.setString(3, oldNameValue);
            preparedStatement.execute();
            SubRoute.getInstance().getStage().close();
            refreshTable();
            new Notification().getSucessEdit();
        } catch (SQLException exception) {
            exception.printStackTrace();
            new Notification().getErrorEdit(exception);
        }
    }

    private void deleteData(String nameValue) {
        final String query = "DELETE FROM routs WHERE name_ = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameValue);
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
        fillTable();
    }
}