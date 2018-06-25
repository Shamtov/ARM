package ru.coddvrn.Application;


import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import ru.coddvrn.Application.Alerts.FormsAlerts;
import ru.coddvrn.Application.Connection.Connect;
import ru.coddvrn.Application.Notifications.Notification;
import ru.coddvrn.Application.Scene.MainMenu;
import ru.coddvrn.Application.Validate.ValidateFields;

import java.sql.*;

public class Controller extends Application {
    private TextField userText = new TextField();
    private PasswordField passText = new PasswordField();

    @Override
    public void start(Stage stage) {

        Label userLabel = new Label("Логин");
        userLabel.setFont(new Font("SanSerif", 14));
        Label passLabel = new Label("Пароль");
        passLabel.setFont(new Font("SanSerif", 14));

        userText.setPromptText("Введите логин");
        userText = TextFields.createClearableTextField();
        userText.setText("ntk");

        passText.setPromptText("Введите пароль");
        passText = TextFields.createClearablePasswordField();
        passText.setText("ntk");

        Button enter = new Button("Войти");
        enter.setOnAction(event -> {
            if ((new ValidateFields().validateString(userText.getText())) &&
                    new ValidateFields().validateString(passText.getText()))
                getLogin(userText.getText(), passText.getText(), stage);
        });
        Button cancel = new Button("Отмена");
        cancel.setOnAction(event ->
                stage.close()
        );

        GridPane root = new GridPane();
        root.setPadding(new Insets(20, 10, 10, 10));
        root.setHgap(25);
        root.setVgap(15);
        GridPane.setHalignment(userLabel, HPos.CENTER);
        root.add(userLabel, 0, 0);
        root.add(userText, 1, 0);

        GridPane.setHalignment(passLabel, HPos.CENTER);
        root.add(passLabel, 0, 1);
        root.add(passText, 1, 1);

        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(enter, cancel);
        root.add(hbox, 1, 2);

        Scene scene = new Scene(root, 270, 150);
        stage.setTitle("Вход в систему");
        scene.getStylesheets().add("ru/coddvrn/Application/res/Style/Login.css");
        stage.getIcons().add(new Image("ru/coddvrn/Application/res/icons/Logo.png"));
        stage.setScene(scene);

        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if ((new ValidateFields().validateString(userText.getText())) &&
                        new ValidateFields().validateString(passText.getText()))
                    getLogin(userText.getText(), passText.getText(), stage);
            }
        });

        stage.show();
    }

    private void getLogin(String user, String pass, Stage stage) {
        final String query = "SELECT name_, pass_ from users where name_ = ? and pass_ = ?";
        try (Connection connection = Connect.getConnect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                new MainMenu().display(stage, resultSet.getString("name_"));
                resultSet.close();
                clearFields();
                stage.close();
            } else {
                new FormsAlerts().getWarningAlert("Вход в систему", "Неправильный логин или пароль");
                clearFields();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            Notification.getErrorConnect(exception);
        }
    }

    void clearFields() {
        userText.clear();
        passText.clear();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}