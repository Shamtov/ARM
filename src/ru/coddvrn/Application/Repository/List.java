package ru.coddvrn.Application.Repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.coddvrn.Application.Connection.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class List {
    private final ObservableList statusList = FXCollections.observableArrayList("Не работает", "Работает");
    private final ObservableList installerList = FXCollections.observableArrayList();
    private final ObservableList carrierList = FXCollections.observableArrayList();
    private final ObservableList typeofBlocks = FXCollections.observableArrayList();
    private final Set<String> possibleStates = new HashSet<>();
    private final ObservableList routesList = FXCollections.observableArrayList();
    private final ObservableList carTypeList = FXCollections.observableArrayList();
    private final ObservableList carBrandList = FXCollections.observableArrayList();

    public ObservableList getStatusList() {
        return statusList;
    }

    public ObservableList getCarrierList() {
        if (carrierList.isEmpty()) {
            String query = "SELECT name_ FROM projects ORDER BY name_";
            try (Connection connection = Connect.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    carrierList.add(resultSet.getString("name_"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return carrierList;
    }

    public ObservableList getinstallerList() {
        if (installerList.isEmpty()) {
            String query = "SELECT name_ FROM providers ORDER BY name_";
            try (Connection connection = Connect.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    installerList.add(resultSet.getString("name_"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return installerList;
    }

    public ObservableList getTypeofBlocks() {
        if (typeofBlocks.isEmpty()) {
            String query = "SELECT bt_name_ FROM block_types ORDER BY bt_name_";
            try (Connection connection = Connect.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    typeofBlocks.add(resultSet.getString("bt_name_"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return typeofBlocks;
    }

    public Set<String> getPossibleStates() {
        if (possibleStates.isEmpty()) {
            String query = "SELECT o.name_ " +
                    "FROM objects o " +
                    "WHERE o.ids_ NOT IN (SELECT oids_ FROM granits)";
            try (Connection connection = Connect.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    possibleStates.add(resultSet.getString("name_"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return possibleStates;
    }

    public ObservableList getRouts() {
        if (routesList.isEmpty()) {
            String query = "SELECT name_ FROM routs ORDER BY name_";
            try (Connection connection = Connect.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    routesList.add(resultSet.getString("name_"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return routesList;
    }

    public ObservableList getBrand() {
        if (carTypeList.isEmpty()) {
            String query = "SELECT cb_name_ FROM car_brand ORDER BY cb_name_";
            try (Connection connection = Connect.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    carTypeList.add(resultSet.getString("cb_name_"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return carTypeList;
    }

    public ObservableList getCarType() {
        if (carBrandList.isEmpty()) {
            String query = "SELECT name_ FROM car_type_ ORDER BY name_";
            try (Connection connection = Connect.getConnect();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    carBrandList.add(resultSet.getString("name_"));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return carBrandList;
    }
}