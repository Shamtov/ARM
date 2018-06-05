package ru.coddvrn.Application.Notifications;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;

public class Notification {

    public static void getConnect(SQLException except){
        Notifications.create()
                .title("Соединение с базой данных")
                .text("Не удаётся установить соединение"+"/n"+except)
                .graphic(null)
                .darkStyle()
                .hideAfter(Duration.minutes(3.0))
                .position(Pos.CENTER)
                .showError();
    }
    public static void getSuccessAdd(){
        Notifications.create()
                .title("Добавление в базу")
                .text("Запись успешно добавлена")
                .graphic(null)
                .darkStyle()
                .hideAfter(Duration.seconds(3.0))
                .position(Pos.BOTTOM_RIGHT)
                .showInformation();
    }
    public static void getSuccessEdit(){
        Notifications.create()
                .title("Изменение записи")
                .text("Запись успешно изменена")
                .darkStyle()
                .graphic(null)
                .hideAfter(Duration.seconds(3.0))
                .position(Pos.BOTTOM_RIGHT)
                .showInformation();
    }
    public static void getSuccessDelete(){
        Notifications.create()
                .title("Удаление записи")
                .text("Запись успешно удалена")
                .darkStyle()
                .graphic(null)
                .hideAfter(Duration.seconds(3.0))
                .position(Pos.BOTTOM_RIGHT)
                .showInformation();
    }
    public static void getErrorAdd(SQLException except){
        Notifications.create()
                .title("Добавление в базу")
                .text("Не удалось добавить запись"+"/n"+except)
                .graphic(null)
                .darkStyle()
                .hideAfter(Duration.minutes(3.0))
                .position(Pos.BOTTOM_RIGHT)
                .showError();
    }
    public static void getErrorEdit(SQLException except){
        Notifications.create()
                .title("Изменение записи")
                .text("Не удалось изменить запись"+"/n"+except)
                .graphic(null)
                .darkStyle()
                .hideAfter(Duration.minutes(3.0))
                .position(Pos.BOTTOM_RIGHT)
                .showError();
    }
    public static void getErrorDelete(SQLException except){
        Notifications.create()
                .title("Удаление записи")
                .text("Не удалось удалить запись"+"/n"+except)
                .graphic(null)
                .darkStyle()
                .hideAfter(Duration.minutes(3.0))
                .position(Pos.BOTTOM_RIGHT)
                .showError();
    }


}
