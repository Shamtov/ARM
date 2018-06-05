package ru.coddvrn.Application.Entity;

import javafx.beans.property.*;

public class ObjectTable {

    public String getStateNumber() {
        return stateNumber.get();
    }

    public SimpleStringProperty stateNumberProperty() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber.set(stateNumber);
    }

    public String getCarBrand() {
        return carBrand.get();
    }

    public SimpleStringProperty carBrandProperty() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand.set(carBrand);
    }

    public int getYearReleased() {
        return yearReleased.get();
    }

    public SimpleIntegerProperty yearReleasedProperty() {
        return yearReleased;
    }

    public void setYearReleased(int yearReleased) {
        this.yearReleased.set(yearReleased);
    }

    public String getCarType() {
        return carType.get();
    }

    public SimpleStringProperty carTypeProperty() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType.set(carType);
    }

    public Object getLastTime() {
        return lastTime.get();
    }

    public SimpleObjectProperty lastTimeProperty() {
        return lastTime;
    }

    public void setLastTime(Object lastTime) {
        this.lastTime.set(lastTime);
    }

    public int getLastSpeed() {
        return lastSpeed.get();
    }

    public SimpleIntegerProperty lastSpeedProperty() {
        return lastSpeed;
    }

    public void setLastSpeed(int lastSpeed) {
        this.lastSpeed.set(lastSpeed);
    }

    public String getRoutsName() {
        return routsName.get();
    }

    public SimpleStringProperty routsNameProperty() {
        return routsName;
    }

    public void setRoutsName(String routsName) {
        this.routsName.set(routsName);
    }

    public Object getLastStationTime() {
        return lastStationTime.get();
    }

    public SimpleObjectProperty lastStationTimeProperty() {
        return lastStationTime;
    }

    public void setLastStationTime(Object lastStationTime) {
        this.lastStationTime.set(lastStationTime);
    }

    public String getCarrier() {
        return carrier.get();
    }

    public SimpleStringProperty carrierProperty() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier.set(carrier);
    }

    public String getInstaller() {
        return installer.get();
    }

    public SimpleStringProperty installerProperty() {
        return installer;
    }

    public void setInstaller(String installer) {
        this.installer.set(installer);
    }

    public String getDateInserted() {
        return dateInserted.get();
    }

    public SimpleStringProperty dateInsertedProperty() {
        return dateInserted;
    }

    public void setDateInserted(String dateInserted) {
        this.dateInserted.set(dateInserted);
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

    public long getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleLongProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    private final SimpleStringProperty stateNumber;
    private final SimpleStringProperty carBrand;
    private final SimpleIntegerProperty yearReleased;
    private final SimpleStringProperty carType;
    private final SimpleObjectProperty lastTime;
    private final SimpleIntegerProperty lastSpeed;
    private final SimpleStringProperty routsName;
    private final SimpleObjectProperty lastStationTime;
    private final SimpleStringProperty carrier;
    private final SimpleStringProperty installer;
    private final SimpleStringProperty dateInserted;
    private final SimpleStringProperty status;
    private final SimpleLongProperty phoneNumber;
    private final SimpleStringProperty comment;

    public ObjectTable(String stateNumber, String carBrand, int yearReleased, String carType, Object lastTime, int lastSpeed,
                       String routsName, Object lastStationTime, String carrier, String installer, String dateInserted, String status,
                       long phoneNumber, String comment) {
        this.stateNumber = new SimpleStringProperty(stateNumber);
        this.carBrand = new SimpleStringProperty(carBrand);
        this.yearReleased = new SimpleIntegerProperty(yearReleased);
        this.carType = new SimpleStringProperty(carType);
        this.lastTime = new SimpleObjectProperty(lastTime);
        this.lastSpeed = new SimpleIntegerProperty(lastSpeed);
        this.routsName = new SimpleStringProperty(routsName);
        this.lastStationTime = new SimpleObjectProperty(lastStationTime);
        this.carrier = new SimpleStringProperty(carrier);
        this.installer = new SimpleStringProperty(installer);
        this.dateInserted = new SimpleStringProperty(dateInserted);
        this.status = new SimpleStringProperty(status);
        this.phoneNumber = new SimpleLongProperty(phoneNumber);
        this.comment = new SimpleStringProperty(comment);

    }

}
