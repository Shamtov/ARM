package ru.coddvrn.Application.Entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class NavigationBlockTable {

    public int getBlockNumber() {
        return blockNumber.get();
    }

    public SimpleIntegerProperty blockNumberProperty() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber.set(blockNumber);
    }

    public String getBlockType() {
        return blockType.get();
    }

    public SimpleStringProperty blockTypeProperty() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType.set(blockType);
    }

    public String getStateNumber() {
        return stateNumber.get();
    }

    public SimpleStringProperty stateNumberProperty() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        this.stateNumber.set(stateNumber);
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

    public Object getTime() {
        return time.get();
    }

    public SimpleObjectProperty timeProperty() {
        return time;
    }

    public void setTime(Object time) {
        this.time.set(time);
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

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    private final SimpleIntegerProperty blockNumber;
    private final SimpleStringProperty blockType;
    private final SimpleStringProperty stateNumber;
    private final SimpleLongProperty phoneNumber;
    private final SimpleObjectProperty time;
    private final SimpleStringProperty carrier;
    private final SimpleStringProperty installer;
    private final SimpleStringProperty comment;


    public NavigationBlockTable(int blockNumber, String blockType, String stateNumber, long phoneNumber, Object time, String carrier, String installer, String comment) {
        this.blockNumber = new SimpleIntegerProperty(blockNumber);
        this.blockType = new SimpleStringProperty(blockType);
        this.stateNumber = new SimpleStringProperty(stateNumber);
        this.phoneNumber = new SimpleLongProperty(phoneNumber);
        this.time = new SimpleObjectProperty(time);
        this.carrier = new SimpleStringProperty(carrier);
        this.installer = new SimpleStringProperty(installer);
        this.comment = new SimpleStringProperty(comment);
    }
}
