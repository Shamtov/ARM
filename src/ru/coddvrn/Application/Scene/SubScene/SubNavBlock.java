package ru.coddvrn.Application.Scene.SubScene;

import javafx.scene.control.TextField;
import ru.coddvrn.Application.Icons.IconsLoader;

public class SubNavBlock {
    private SubNavBlock() {
        super();
    }

    private static SubNavBlock instance;

    public static SubNavBlock getInstance() {
        if (instance == null)
            instance = new SubNavBlock();
        return instance;
    }

    private TextField nameText = new TextField();
    private TextField latText = new TextField();
    private TextField lonText = new TextField();
    private TextField azmthText = new TextField();
    private TextField commentText = new TextField();

    public void display() {
    }
}

