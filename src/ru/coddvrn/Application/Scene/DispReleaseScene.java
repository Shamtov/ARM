package ru.coddvrn.Application.Scene;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Connection.Connect;

import java.sql.Connection;

public class DispReleaseScene {
    public static void display() {
        // New window (Stage)
        Stage dispReleaseStage = new Stage();
        dispReleaseStage.initModality(Modality.WINDOW_MODAL);
        dispReleaseStage.setTitle("Контроль выхода ТС на маршруты по факту");

        // New window (Scene)
        StackPane root = new StackPane();
        Scene dispReleaseScene = new Scene(root, 900, 900);
        dispReleaseStage.setScene(dispReleaseScene);
        // create connection
//        Connect con = new Connect();
//         Setup database connection
//        con.getConnect();
        // Close database connection
        dispReleaseStage.show();
    }
}
