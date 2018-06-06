package ru.coddvrn.Application.Repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.coddvrn.Application.Connection.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListRep {
    private final ObservableList statusList = FXCollections.observableArrayList("Не используется", "Работает");
    private final ObservableList installerList = FXCollections.observableArrayList();
    private final ObservableList carrierList = FXCollections.observableArrayList();
    private final ObservableList typeofBlocks = FXCollections.observableArrayList();

    public ObservableList getStatusList() {
        return statusList;
    }

    public ObservableList getCarrierList() {
        if (carrierList.isEmpty()) {
            String query = "SELECT name_ FROM projects";
            try (Connection connection = Connect.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    carrierList.add( resultSet.getString("name_"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return carrierList;
    }

    public ObservableList getinstallerList() {
        if (installerList.isEmpty()) {
            String query = "SELECT name_ FROM providers";
            try (Connection connection = Connect.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    installerList.add( resultSet.getString("name_"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return installerList;
    }

    public ObservableList getTypeofBlocks() {
        if (typeofBlocks.isEmpty()) {
            String query = "SELECT bt_name_ FROM block_types";
            try (Connection connection = Connect.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    typeofBlocks.add( resultSet.getString("bt_name_"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return typeofBlocks;
    }
}
