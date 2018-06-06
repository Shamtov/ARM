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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Connection.Connect;
import ru.coddvrn.Application.Entity.NavigationBlockTable;
import ru.coddvrn.Application.Icons.IconsLoader;
import ru.coddvrn.Application.Notifications.Notification;
import ru.coddvrn.Application.Scene.SubScene.SubBus;
import ru.coddvrn.Application.Scene.SubScene.SubNavBlock;

import java.sql.*;
import java.util.Optional;
import java.util.function.Predicate;

public class NavigationBlock {
    // Singleton
    private NavigationBlock() {
    }

    private static NavigationBlock instance;

    public static NavigationBlock getInstance() {
        if (instance == null)
            instance = new NavigationBlock();
        return instance;
    }

    // Create data Collection
    private ObservableList<NavigationBlockTable> data = FXCollections.observableArrayList();
    // Create table
    private TableView<NavigationBlockTable> table = new TableView<>();
    private FilteredList<NavigationBlockTable> filteredData = new FilteredList<>(data, e -> true);
    private Label rowCounterLabel = new Label();

    private void initColumns() {
        // Create table columns
        TableColumn blockNumberColumn = new TableColumn("Номер блока");
        //Initialize columns
        blockNumberColumn.setMinWidth(100);
        blockNumberColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, Integer>("blockNumber"));

        TableColumn blockTypeColumn = new TableColumn("Тип блока");
        blockTypeColumn.setMinWidth(130);
        blockTypeColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("blockType"));

        TableColumn stateNumberColumn = new TableColumn("Гос. Номер ТС");
        stateNumberColumn.setMinWidth(100);
        stateNumberColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("stateNumber"));

        TableColumn phoneColumn = new TableColumn("Номер телефона");
        phoneColumn.setMinWidth(130);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, Long>("phoneNumber"));

        TableColumn timeColumn = new TableColumn("Время последнего");
        timeColumn.setMinWidth(150);
        timeColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, Object>("time"));

        TableColumn carrierColumn = new TableColumn("Перевозчик");
        carrierColumn.setPrefWidth(100);
        carrierColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("carrier"));

        TableColumn installerColumn = new TableColumn("Установщик");
        installerColumn.setMinWidth(100);
        installerColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("installer"));

        TableColumn commentsColumn = new TableColumn("Комментарий");
        commentsColumn.setMinWidth(200);
        commentsColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("comment"));
        //Add columns to the table
        table.getColumns().addAll(blockNumberColumn, blockTypeColumn, stateNumberColumn, phoneColumn, timeColumn, carrierColumn, installerColumn, commentsColumn);
        table.setTableMenuButtonVisible(true);
        table.setEditable(false);
        /*table.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1) {
                    SubBus.getInstance().display(table.getSelectionModel().getSelectedItem().getId(),
                            table.getSelectionModel().getSelectedItem().getName(),
                            table.getSelectionModel().getSelectedItem().getLat(),
                            table.getSelectionModel().getSelectedItem().getLon(),
                            table.getSelectionModel().getSelectedItem().getAzmth());
                }
            }
        });*/
    }

    private void initRowsCounter() {
        rowCounterLabel.setText("Количество записей: " + data.size());
    }

    public void display() {
        // New window (Stage)
        Stage navBlockStage = new Stage();
        navBlockStage.initModality(Modality.WINDOW_MODAL);
        navBlockStage.setTitle("Справочник навигационных блоков");
        initColumns();
        fillTable();
        // Add vertical and horizontal scrollPane
        initScrollPane();
        initRowsCounter();
        data.addListener(new ListChangeListener<NavigationBlockTable>() {
            @Override
            public void onChanged(Change<? extends NavigationBlockTable> c) {
                initRowsCounter();
            }
        });
        // Create buttons
        Button add = new Button("Добавить...", IconsLoader.getInstance().getAddIcon());
        add.setOnAction(event -> SubNavBlock.getInstance().display());

        Button edit = new Button("Изменить...", IconsLoader.getInstance().getEditIcon());
        edit.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        /*edit.setOnAction(event -> SubBus.getInstance().display(table.getSelectionModel().getSelectedItem().getId(),
                table.getSelectionModel().getSelectedItem().getName(),
                table.getSelectionModel().getSelectedItem().getLat(),
                table.getSelectionModel().getSelectedItem().getLon(),
                table.getSelectionModel().getSelectedItem().getAzmth()));*/

        Button delete = new Button("Удалить", IconsLoader.getInstance().getDeleteIcon());
        delete.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            DialogPane pane = alert.getDialogPane();
            pane.setPrefSize(500.0, 120.0);
            alert.setResizable(true);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(null);
            alert.setContentText("Вы действительно хотите удалить блок(-и) с номером " + table.getSelectionModel().getSelectedItem().getBlockNumber() + " ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                deleteData(table.getSelectionModel().getSelectedItem().getBlockNumber());
            }
        });
        Button refresh = new Button("Обновить", IconsLoader.getInstance().getRefreshIcon());
        refresh.setOnAction(event -> refreshTable());

        rowCounterLabel.setFont(new Font("Arial", 14));

        TextField searchField = new TextField();
        searchField.setPromptText("Поиск по блоку или номеру");
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
        Scene NavBlockScene = new Scene(root, 1000, 700);
        navBlockStage.setScene(NavBlockScene);
        navBlockStage.show();
        navBlockStage.setOnCloseRequest(event -> data.clear());
    }

    private void searchByItem(TextField searchField) {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super NavigationBlockTable>) obj -> {
                String lowerCaseFilter = newValue.toLowerCase();
                if (newValue == null || newValue.isEmpty())
                    return true;
                else if (String.valueOf(obj.getBlockNumber()).contains(lowerCaseFilter))
                    return true;
                else if (obj.getStateNumber().toLowerCase().contains(lowerCaseFilter))
                    return true;

                return false;
            });
            SortedList<NavigationBlockTable> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedData);
            rowCounterLabel.setText("Количество записей: " + sortedData.size());
        });
    }

    private void initScrollPane() {
        // Add vertical and horizontal scrollPane
        ScrollPane sp = new ScrollPane(table);
        sp.setPrefSize(1000, 700);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setFitToHeight(true);
        sp.setHmax(3);
        sp.setHvalue(3);
//        sp.setDisable(false);
    }

    public void fillTable() {
        final String query = "SELECT g.block_number AS block, b.bt_name_ AS type, o.name_ AS state," +
                "o.phone_ AS phone, SUBSTRING (100 + EXTRACT (DAY FROM o.last_time_) FROM 2 FOR 2)||'.'" +
                "                 || SUBSTRING (100 + EXTRACT(MONTH FROM o.last_time_) FROM 2 FOR 2)||'.'" +
                "                  || EXTRACT (YEAR FROM o.last_time_)||' '" +
                "                  || SUBSTRING (100 + EXTRACT (HOUR FROM o.last_time_)FROM 2 FOR 2)||':'" +
                "                  || SUBSTRING (100 + EXTRACT(MINUTE FROM o.last_time_) FROM 2 FOR 2)||':'" +
                "                  || SUBSTRING (100 + EXTRACT(SECOND FROM o.last_time_) FROM 2 FOR 2) AS time_ ," +
                "p.name_ AS pere, providers.name_ AS prov," +
                "o.user_comment_ AS comm " +
                "FROM granits g " +
                "LEFT JOIN block_types b " +
                "ON g.block_type = b.bt_id_ " +
                "LEFT JOIN  objects o " +
                "ON g.oids_ = o.ids_ " +
                "LEFT JOIN projects p " +
                "ON o.proj_id_= p.id_ " +
                "LEFT JOIN providers " +
                "ON o.provider_= providers.id_";
        try (Connection connection = Connect.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                data.add(new NavigationBlockTable(
                        resultSet.getInt("block"),
                        resultSet.getString("type"),
                        resultSet.getString("state"),
                        resultSet.getLong("phone"),
                        resultSet.getObject("time_"),
                        resultSet.getString("pere"),
                        resultSet.getString("prov"),
                        resultSet.getString("comm")
                ));

            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        table.setItems(data);
    }

    public void addData(String numberInput, String typeString, String stateNumInput) {
        final String subquery = ("SELECT ids_ FROM objects WHERE name_ = 'АХ52736')");
        final String subquery2 = ("SELECT bt_id_ FROM block_types WHERE bt_name_ = 'Гранит/07'");
        final String query = "INSERT INTO granits (block_number, block_type, oids_) VALUES (?,?,?)";
        System.out.println(subquery);
        System.out.println(subquery2);
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Statement query1 = connection.createStatement(subquery);
            preparedStatement.setString(1, numberInput);
            preparedStatement.setInt(2, Integer.parseInt(subquery));
            preparedStatement.setInt(3, Integer.parseInt(subquery2));
            preparedStatement.execute();
            Notification.getSuccessAdd();
            SubNavBlock.getInstance().clearFields();
            refreshTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
            Notification.getErrorAdd(exception);
        }
    }

    public void updateData(TextField nameText, TextField lonText, TextField latText, TextField azmthText, int idValue) {
        final String query = "UPDATE granits SET name = ? ,lat = ?,lon = ? ,azmth = ? WHERE id = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nameText.getText());
            preparedStatement.setDouble(2, Double.parseDouble(latText.getText()));
            preparedStatement.setDouble(3, Double.parseDouble(lonText.getText()));
            preparedStatement.setInt(4, Integer.parseInt(azmthText.getText()));
            preparedStatement.setInt(5, idValue);
            preparedStatement.execute();
            Notification.getSuccessEdit();
            SubNavBlock.getInstance().getStage().close();
            refreshTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
            Notification.getErrorEdit(exception);
        }
    }

    private void deleteData(int idValue) {
        final String query = "DELETE FROM granits WHERE block_number = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idValue);
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


