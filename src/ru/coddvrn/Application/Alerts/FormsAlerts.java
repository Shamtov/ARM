package ru.coddvrn.Application.Alerts;

import javafx.scene.control.Alert;

public class FormsAlerts {
    public void getWarningAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(context);
        alert.showAndWait();
    }
}
