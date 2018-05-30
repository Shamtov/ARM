package ru.coddvrn.Application.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BusStopTable {

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public double getLon() {
        return lon.get();
    }

    public SimpleDoubleProperty lonProperty() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon.set(lon);
    }

    public int getAzmth() {
        return azmth.get();
    }

    public SimpleIntegerProperty azmthProperty() {
        return azmth;
    }

    public void setAzmth(int azmth) {
        this.azmth.set(azmth);
    }

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty lat;
    private final SimpleDoubleProperty lon;
    private final SimpleIntegerProperty azmth;


    public BusStopTable(int id, String name, double lat, double lon, int azmth) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.lat = new SimpleDoubleProperty(lat);
        this.lon = new SimpleDoubleProperty(lon);
        this.azmth = new SimpleIntegerProperty(azmth);
    }
}
