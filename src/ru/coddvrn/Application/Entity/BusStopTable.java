package ru.coddvrn.Application.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BusStopTable {
    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public String getStopName() {
        return stopName.get();
    }

    public SimpleStringProperty stopNameProperty() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName.set(stopName);
    }

    public double getLon() {
        return lon.get();
    }

    public SimpleDoubleProperty lonProperty() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon.set(lon);
    }

    public double getLat() {
        return lat.get();
    }

    public SimpleDoubleProperty latProperty() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat.set(lat);
    }

    public int getRoute() {
        return route.get();
    }

    public SimpleIntegerProperty routeProperty() {
        return route;
    }

    public void setRoute(int route) {
        this.route.set(route);
    }

    public int getControl() {
        return control.get();
    }

    public SimpleIntegerProperty controlProperty() {
        return control;
    }

    public void setControl(int control) {
        this.control.set(control);
    }

    private final SimpleIntegerProperty number;
    private final SimpleStringProperty stopName;
    private final SimpleDoubleProperty lon;
    private final SimpleDoubleProperty lat;
    private final SimpleIntegerProperty route;
    private final SimpleIntegerProperty control;


    public BusStopTable(int number, String stopName, double lon, Double lat, int route, short control) {
        this.number = new SimpleIntegerProperty(number);
        this.stopName = new SimpleStringProperty(stopName);
        this.lon = new SimpleDoubleProperty(lon);
        this.lat = new SimpleDoubleProperty(lat);
        this.route = new SimpleIntegerProperty(route);
        this.control = new SimpleIntegerProperty(control);
    }
}
