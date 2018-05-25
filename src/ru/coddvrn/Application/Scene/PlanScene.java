package ru.coddvrn.Application.Scene;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlanScene {
    public static void display(){
        // New window (Stage)
        Stage planStage = new Stage();
        planStage.initModality(Modality.WINDOW_MODAL);
        planStage.setTitle("План");

        // New window (Scene)
        StackPane root = new StackPane();
        Scene planScene = new Scene(root,900,900);
        planStage.setScene(planScene);
        planStage.show();
    }

}
