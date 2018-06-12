package ru.coddvrn.Application.Repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.coddvrn.Application.Connection.Connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ListRep {
    private final ObservableList statusList = FXCollections.observableArrayList("Не работает","Работает");
    private final Set<String> installerList = new HashSet<>();
    private final Set<String> carrierList = new HashSet<>();
    private final ObservableList typeofBlocks = FXCollections.observableArrayList();
    private final Set<String> possibleStates = new HashSet<>();

    public ObservableList getStatusList() {
        return statusList;
    }

    public Set<String> getCarrierList() {
        if (carrierList.isEmpty()) {
            String query = "SELECT name_ FROM projects";
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

    public Set<String> getinstallerList() {
        if (installerList.isEmpty()) {
            String query = "SELECT name_ FROM providers";
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
            String query = "SELECT bt_name_ FROM block_types";
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
                    "WHERE o.ids_ NOT IN (SELECT g.oids_ FROM granits g)";
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
        System.out.print(possibleStates.size());
        return possibleStates;
    }
}

