package ru.coddvrn.Application.Scene.SubScene;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SubObject {
    private SubObject() {
        super();
    }

    private static SubObject instance;

    public static SubObject getInstance() {
        if (instance == null)
            instance = new SubObject();
        return instance;
    }

    public void display() {
    }

//    public Stage getStage() {
//    }

//    public void clearFields(TextField nameText, TextField lonText, TextField latText, TextField azmthText) {
//    }
}
