package ru.coddvrn.Application.Scene;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Connection.Connect;
import ru.coddvrn.Application.Entity.BusStopTable;
import ru.coddvrn.Application.Entity.NavigationBlockTable;
import ru.coddvrn.Application.Icons.IconsLoader;
import ru.coddvrn.Application.Notifications.Notification;
import ru.coddvrn.Application.Scene.SubScene.SubBus;
import ru.coddvrn.Application.Scene.SubScene.SubNavBlock;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

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
    private ObservableList<NavigationBlock> data = FXCollections.observableArrayList();
    // Create table
    private TableView<NavigationBlock> table = new TableView<>();

    private Label rowCounterLabel = new Label();

    private void initColumns() {
        // Create table columns
        TableColumn blockNumberColumn = new TableColumn("Номер блока");
        //Initialize columns
        blockNumberColumn.setMinWidth(50);
        blockNumberColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, Integer>("blockNumber"));

        TableColumn blockTypeColumn = new TableColumn("Тип блока");
        blockTypeColumn.setMinWidth(100);
        blockTypeColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("blockType"));

        TableColumn stateNumberColumn = new TableColumn("Гос. Номер ТС");
        stateNumberColumn.setMinWidth(100);
        stateNumberColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("stateNumber"));

        TableColumn phoneColumn = new TableColumn("Номер телефона");
        phoneColumn.setMinWidth(100);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, Long>("phoneNumber"));

        TableColumn timeColumn = new TableColumn("Время последнего");
        timeColumn.setMinWidth(100);
        timeColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("time"));

        TableColumn carrierColumn = new TableColumn("Перевозчик");
        carrierColumn.setMinWidth(100);
        carrierColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("carrier"));

        TableColumn installerColumn = new TableColumn("Установщик");
        installerColumn.setMinWidth(100);
        installerColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("installer"));

        TableColumn commentsColumn = new TableColumn("Комментарий");
        commentsColumn.setMinWidth(100);
        commentsColumn.setCellValueFactory(new PropertyValueFactory<NavigationBlockTable, String>("comments"));
        //Add columns to the table
        table.getColumns().addAll(blockNumberColumn, blockTypeColumn, stateNumberColumn, phoneColumn, timeColumn, carrierColumn, installerColumn, commentsColumn);
        table.setTableMenuButtonVisible(true);
        table.setEditable(true);
    }

    private void initRowsCounter() {
        rowCounterLabel.setText("Количество записей: " + data.size());
    }

    public void display() {
        // New window (Stage)
        Stage navigationScene = new Stage();
        navigationScene.initModality(Modality.WINDOW_MODAL);
        navigationScene.setTitle("Справочник навигационных блоков");
        initColumns();
        fillTable();
        // Add vertical and horizontal scrollPane
        initScrollPane();
        initRowsCounter();
        data.addListener(new ListChangeListener<NavigationBlock>() {
            @Override
            public void onChanged(Change<? extends NavigationBlock> c) {
                initRowsCounter();
            }
        });
        // Create buttons
        Button add = new Button("Добавить...", IconsLoader.getInstance().getAddIcon());
        add.setOnAction(event -> SubNavBlock.getInstance().display());

        Button edit = new Button("Изменить...", IconsLoader.getInstance().getEditIcon());
       /* edit.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        edit.setOnAction(event -> SubBus.getInstance().display(table.getSelectionModel().getSelectedItem().getId(),
                table.getSelectionModel().getSelectedItem().getName(),
                table.getSelectionModel().getSelectedItem().getLat(),
                table.getSelectionModel().getSelectedItem().getLon(),
                table.getSelectionModel().getSelectedItem().getAzmth()));

        Button delete = new Button("Удалить", IconsLoader.getInstance().getDeleteIcon());
        delete.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        delete.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText(null);
            alert.setContentText("Вы действительно хотите удалить запись?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                deleteData(table.getSelectionModel().getSelectedItem().getId());
            }
        });
        Button refresh = new Button("Обновить", IconsLoader.getInstance().getRefreshIcon());
        refresh.setOnAction(event -> refreshTable());
*/
        rowCounterLabel.setFont(new Font("Arial", 14));
//        HBox hbox = new HBox(15);
//        hbox.getChildren().addAll(add, edit, delete, refresh);
//        hbox.setPadding(new Insets(10, 10, 10, 10));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(table);
        stackPane.setPadding(new Insets(10, 10, 15, 10));
        HBox rowCounterHbox = new HBox();
        rowCounterHbox.getChildren().add(rowCounterLabel);
        rowCounterHbox.setPadding(new Insets(10, 0, 10, 10));
        BorderPane root = new BorderPane();
//        root.setTop(hbox);
        root.setCenter(stackPane);
        root.setBottom(rowCounterHbox);
        // Set scene
        Scene dirStopsScene = new Scene(root, 800, 500);
        navigationScene.setScene(dirStopsScene);
        navigationScene.show();
        navigationScene.setOnCloseRequest(event -> data.clear());
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

    public void fillTable() {
        final String query = "SELECT g.block_number AS block, b.bt_name_ AS type, o.name_ AS state," +
                "o.phone_ AS phone, p.name_ AS pere, providers.name_ AS prov," +
                "o.user_comment_ AS comm" +
                "FROM granits g" +
                "LEFT JOIN block_types b" +
                "ON g.block_type = b.bt_id_" +
                "LEFT JOIN  objects o" +
                "ON g.oids_ = o.ids_" +
                "LEFT JOIN projects p" +
                "ON o.proj_id_= p.id_" +
                "LEFT JOIN providers" +
                "ON o.provider_= providers.id_";
        try (Connection connection = Connect.getConnect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                data.add(new NavigationBlockTable(
                        resultSet.getInt("block_number"),
                        resultSet.getString("type"),
                        resultSet.getString("state"),
                        resultSet.getLong("phone"),
                        resultSet.getTimestamp("tme"),
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
            SubBus.getInstance().clearFields(nameText, lonText, latText, azmthText);
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
            SubBus.getInstance().getStage().close();
            refreshTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
            Notification.getErrorEdit(exception);
        }
    }

    private void deleteData(int idValue) {
        final String query = "DELETE FROM bs WHERE id = ?";
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

}
