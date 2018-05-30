package ru.coddvrn.Application.Entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RoutesTable {

    public String getRouteName() {
        return routeName.get();
    }

    public SimpleStringProperty routeNameProperty() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName.set(routeName);
    }

    public int getBusStopCount() {
        return busStopCount.get();
    }

    public SimpleIntegerProperty busStopCountProperty() {
        return busStopCount;
    }

    public void setBusStopCount(int busStopCount) {
        this.busStopCount.set(busStopCount);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    private final SimpleStringProperty routeName;
    private final SimpleIntegerProperty busStopCount;
    private final SimpleStringProperty status;


    public RoutesTable(String routeName, int busStopCount, String status) {
        this.routeName = new SimpleStringProperty(routeName);
        this.busStopCount = new SimpleIntegerProperty(busStopCount);
        this.status = new SimpleStringProperty(status);
    }
}
