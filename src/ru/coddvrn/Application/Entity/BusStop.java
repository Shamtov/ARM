package ru.coddvrn.Application.Entity;

import javafx.beans.property.SimpleStringProperty;

public class BusStop {
    private final SimpleStringProperty number;
    private final SimpleStringProperty stopName;
    private final SimpleStringProperty lon;
    private final SimpleStringProperty lat;
    private final SimpleStringProperty route;
    private final SimpleStringProperty control;


    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
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

    public String getLon() {
        return lon.get();
    }

    public SimpleStringProperty lonProperty() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon.set(lon);
    }

    public String getLat() {
        return lat.get();
    }

    public SimpleStringProperty latProperty() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat.set(lat);
    }

    public String getRoute() {
        return route.get();
    }

    public SimpleStringProperty routeProperty() {
        return route;
    }

    public void setRoute(String route) {
        this.route.set(route);
    }

    public String getControl() {
        return control.get();
    }

    public SimpleStringProperty controlProperty() {
        return control;
    }

    public void setControl(String control) {
        this.control.set(control);
    }


    public BusStop(String number, String stopName, String lon, String lat, String route, String control) {
        this.number = new SimpleStringProperty(number);
        this.stopName = new SimpleStringProperty(stopName);
        this.lon = new SimpleStringProperty(lon);
        this.lat = new SimpleStringProperty(lat);
        this.route = new SimpleStringProperty(route);
        this.control = new SimpleStringProperty(control);
    }
}
