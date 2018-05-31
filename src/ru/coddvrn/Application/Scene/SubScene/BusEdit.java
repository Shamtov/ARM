package ru.coddvrn.Application.Scene.SubScene;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.coddvrn.Application.Scene.BusStop;


public class BusEdit extends BusStop{
    private TextField nameText = new TextField();
    private TextField latText = new TextField();
    private TextField lonText = new TextField();
    private TextField azmthText = new TextField();

   public void display(int idValue, String nameValue, double latValue, double lonValue, int azmthValue) {
       Stage subDirStopsStage = new Stage();
       subDirStopsStage.initModality(Modality.APPLICATION_MODAL);
       subDirStopsStage.setTitle("Изменить");

       GridPane root = new GridPane();
       root.setPadding(new Insets(20, 10, 10, 10));
       root.setHgap(25);
       root.setVgap(15);

       Label nameLabel = new Label("Название остановки");
       nameLabel.setFont(new Font("Arial", 14));
       nameText.setText(nameValue);
       nameText.setPromptText("ул. Леваневского (из центра)");
       nameText.setMinWidth(200);
       GridPane.setHalignment(nameLabel, HPos.CENTER);
       root.add(nameLabel, 0, 1);
       root.add(nameText, 1, 1);

       Label latLabel = new Label("Долгота");
       latLabel.setFont(new Font("Arial", 14));
       lonText.setText(String.valueOf(lonValue));
       lonText.setPromptText("51,657974");
       lonText.setMinWidth(150);
       GridPane.setHalignment(latLabel, HPos.CENTER);
       root.add(latLabel, 0, 3);
       root.add(lonText, 1, 3);

       Label lonLabel = new Label("Широта");
       lonLabel.setFont(new Font("Arial", 14));
//       this.latText = new TextField();
       latText.setText(String.valueOf(latValue));
       latText.setPromptText("39,103365");
       latText.setMinWidth(150);
       GridPane.setHalignment(lonLabel, HPos.CENTER);
       root.add(lonLabel, 0, 2);
       root.add(latText, 1, 2);

       Label azmthLabel = new Label("Азимут");
       azmthLabel.setFont(new Font("Arial", 14));
//       this.azmthText = new TextField();
       azmthText.setText(String.valueOf(azmthValue));
       azmthText.setPromptText("Например, 50");
       azmthText.setMinWidth(100);
       GridPane.setHalignment(azmthLabel, HPos.CENTER);
       root.add(azmthLabel, 0, 4);
       root.add(azmthText, 1, 4);

       Button add = new Button("Сохранить");
       add.setOnAction(event ->
           updateData(nameText, lonText, latText, azmthText,idValue));
       Button cancel = new Button("Отмена");
       cancel.setOnAction(event -> {
           subDirStopsStage.close();
       });

       HBox buttonBox = new HBox(20);
       buttonBox.getChildren().addAll(add, cancel);
       root.add(buttonBox, 1, 6);
       Scene subDirStopsScene = new Scene(root, 450, 300);
       subDirStopsStage.setScene(subDirStopsScene);
       subDirStopsStage.show();
       subDirStopsStage.setOnCloseRequest(event -> BusStop.getInstance().refreshTable());

   }
   }

