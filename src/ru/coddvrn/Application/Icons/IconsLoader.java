package ru.coddvrn.Application.Icons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.coddvrn.Application.Scene.Route;

public class IconsLoader {
    private static IconsLoader instance;

    public static IconsLoader getInstance() {
        if (instance == null)
            instance = new IconsLoader();
        return instance;
    }
    public ImageView getAddIcon() {
        ImageView addImage = new ImageView(new Image("ru/coddvrn/Application/res/icons/Symbol-Add.png"));
        return addImage;

    }
    public ImageView getEditIcon() {
        ImageView editImage = new ImageView(new Image("ru/coddvrn/Application/res/icons/Symbol-Edit.png"));
        return editImage;

    }
    public ImageView getDeleteIcon() {
        ImageView deleteImage = new ImageView(new Image("ru/coddvrn/Application/res/icons/Symbol-Delete.png"));
        return deleteImage;

    }
    public ImageView getRefreshIcon() {
        ImageView refreshImage = new ImageView(new Image("ru/coddvrn/Application/res/icons/Symbol-Refresh.png"));
        return refreshImage;

    }
}
