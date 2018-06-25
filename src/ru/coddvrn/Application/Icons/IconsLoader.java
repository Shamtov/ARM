package ru.coddvrn.Application.Icons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    public ImageView getRefreshIcon(){
        ImageView refreshImage = new ImageView(new Image("ru/coddvrn/Application/res/icons/Symbol-Refresh.png"));
        return refreshImage;
    }
    public ImageView getDirectoryIcon() {
        ImageView directoryImage = new ImageView(new Image("ru/coddvrn/Application/res/icons/Report.png"));
        return directoryImage;

    }
    public ImageView getLogoutIcon() {
        ImageView logoutImage = new ImageView(new Image("ru/coddvrn/Application/res/icons/Logout.png"));
        return logoutImage;
    }
    public ImageView getLogo() {
        ImageView logoutImage = new ImageView(new Image("ru/coddvrn/Application/res/icons/codd.png"));
        return logoutImage;
    }
    }


