package ru.coddvrn.Application.Scene;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlansTemplateScene {
    public static void display(){
        // New window (Stage)
        Stage planTemplateStage = new Stage();
        planTemplateStage.initModality(Modality.WINDOW_MODAL);
        planTemplateStage.setTitle("Шаблоны планов");

        // New scene (scene)
        StackPane root = new StackPane();
        Scene planTemplateScene = new Scene(root,900,900);
        planTemplateStage.setScene(planTemplateScene);
        planTemplateStage.show();
    }

}
