package ru.coddvrn.Application.Scene;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import ru.coddvrn.Application.Alerts.ConfirmBox;
import ru.coddvrn.Application.Icons.IconsLoader;

public class MainMenu {
    public void display(Stage primaryStage, String user) {
        Label userLabel = new Label("Пользователь: " + user);
//        userLabel.setFont(new Font("Arial", 14));
        userLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 13) );
        HBox hbox = new HBox(10);
        hbox.getChildren().add(userLabel);
        hbox.setPadding(new Insets(0, 10, 5, 5));

        Stage main = new Stage();
        BorderPane root = new BorderPane();

        MenuBar menuBar = new MenuBar();
        // Create menus
        Menu plans = new Menu("Планы");
        Menu disp = new Menu("Диспетчеризация");
        Menu directory = new Menu("Справочники", IconsLoader.getInstance().getDirectoryIcon());
        Menu report = new Menu("Отчётность");
        Menu service = new Menu("Сервис");
        Menu exit = new Menu("Выход", IconsLoader.getInstance().getLogoutIcon());
        // SeparatorMenuItems
        SeparatorMenuItem separator = new SeparatorMenuItem();
        SeparatorMenuItem separator2 = new SeparatorMenuItem();
//        SeparatorMenuItem separator3 = new SeparatorMenuItem();
//        SeparatorMenuItem separator4 = new SeparatorMenuItem();

        // Create MenuItems
        MenuItem planItem = new MenuItem("Планы");
        planItem.setOnAction(event -> {
//            Plan.display();
        });
        MenuItem plansTemplateItem = new MenuItem("Шаблоны планов");
        plansTemplateItem.setOnAction(event -> {
//            PlansTemplate.display();
        });
        MenuItem releaseRouteItem = new MenuItem("Выпуск ТС на маршруты по факту");
        releaseRouteItem.setOnAction(event -> {
//        DispRelease.display();
        });
        MenuItem routeAssignmentItem = new MenuItem("Назначение маршрута");

        MenuItem directoryOfStopsItem = new MenuItem("Справочник остановок");
        directoryOfStopsItem.setOnAction(event -> BusStop.getInstance().display());
        MenuItem directoryOfRoutesItem = new MenuItem("Справочник маршрутов");
        directoryOfRoutesItem.setOnAction(event -> Route.getInstance().display());
        MenuItem directoryOfObjectsItem = new MenuItem("Справочник объектов");
        directoryOfObjectsItem.setOnAction(event -> ObjectModel.getInstance().display());
//        MenuItem directoryOfVehicleModelsItem = new MenuItem("Справочник моделей транспортных средств");
//        MenuItem directoryOfCarriersItem = new MenuItem("Справочник перевозчиков");
//        MenuItem directoryOfContractsItem = new MenuItem("Справочник договоров");
        MenuItem directoryOfNavigationBlockItem = new MenuItem("Справочник навигационных блоков");
        directoryOfNavigationBlockItem.setOnAction(event -> NavigationBlock.getInstance().display());

        MenuItem reportingControlItem = new MenuItem("Отчет по отклику навигационных блоков");

        MenuItem objectOperationsLogItem = new MenuItem("Журнал операций над объектами");

        MenuItem changeUser = new MenuItem("Сменить пользователя");
        changeUser.setOnAction(event -> {
            primaryStage.show();
            main.close();
        });
        MenuItem exitItem = new MenuItem("Выход");

        exitItem.setOnAction(event -> {
            ConfirmBox.display();
        });

        // Add menuItems to the Menus
//        plans.getItems().addAll(planItem, separator, plansTemplateItem);
//        disp.getItems().addAll(releaseRouteItem, routeAssignmentItem);
        directory.getItems().addAll(directoryOfStopsItem, directoryOfRoutesItem, separator2, directoryOfObjectsItem, /*directoryOfVehicleModelsItem, separator3, directoryOfCarriersItem, directoryOfContractsItem,*/ directoryOfNavigationBlockItem);
        report.getItems().addAll(reportingControlItem);
//        service.getItems().addAll(objectOperationsLogItem);
        exit.getItems().addAll(changeUser, exitItem);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(/*plans, disp, */directory, report, /*service,*/ exit);
        root.setTop(menuBar);
        root.setBottom(hbox);

        Scene scene = new Scene(root, 600, 600);
//        main.setOnCloseRequest(event -> ConfirmBox.display());
        main.setTitle("АРМ МБУ " + "ЦОДД");
        main.setScene(scene);
        main.show();

    }


}
