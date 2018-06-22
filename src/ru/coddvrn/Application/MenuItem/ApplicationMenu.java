package ru.coddvrn.Application.MenuItem;

import ru.coddvrn.Application.Alerts.ConfirmBox;
import ru.coddvrn.Application.Icons.IconsLoader;
import ru.coddvrn.Application.Scene.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import ru.coddvrn.Application.Scene.SubScene.InstallerReport;

public class ApplicationMenu {

    public static MenuBar getMenu() {
        MenuBar menuBar = new MenuBar();
        // Create menus
        Menu plansMenu = new Menu("Планы");
        Menu dispMenu = new Menu("Диспетчеризация");
        Menu directoryMenu = new Menu("Справочники", IconsLoader.getInstance().getDirectoryIcon());
        Menu reportMenu = new Menu("Отчётность");
        Menu serviceMenu = new Menu("Сервис");
        Menu exitMenu = new Menu("Выход", IconsLoader.getInstance().getLogoutIcon());
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

        MenuItem reportingControlItem = new MenuItem("Контроль выполнения отчетов");
        reportingControlItem.setOnAction(event -> new InstallerReport().display());

        MenuItem objectOperationsLogItem = new MenuItem("Журнал операций над объектами");

        MenuItem exitItem = new MenuItem("Выход");

        exitItem.setOnAction(event -> {
            ConfirmBox.display();
        });

        // Add menuItems to the Menus
        plansMenu.getItems().addAll(planItem, separator, plansTemplateItem);
        dispMenu.getItems().addAll(releaseRouteItem, routeAssignmentItem);
        directoryMenu.getItems().addAll(directoryOfStopsItem, directoryOfRoutesItem, separator2, directoryOfObjectsItem,
                /*directoryOfVehicleModelsItem, separator3, directoryOfCarriersItem, directoryOfContractsItem,*/ directoryOfNavigationBlockItem);
        reportMenu.getItems().addAll(reportingControlItem);
        serviceMenu.getItems().addAll(objectOperationsLogItem);
        exitMenu.getItems().add(exitItem);

        // Add Menus to the MenuBar
        menuBar.getMenus().addAll(plansMenu, dispMenu, directoryMenu, reportMenu, serviceMenu, exitMenu);

        return menuBar;
    }
}
