package ru.coddvrn.Application.Connection;

import ru.coddvrn.Application.Notifications.Notification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private final static String USER = "SYSDBA";
    private final static String PASSWORD = "masterkey";
    private final static String URL = "C:\\Users\\D\\Downloads\\Универ\\PROJECTS123.FDB";
    private final static String PORT = "3050";
    private final static String URL_STRING = "jdbc:firebirdsql://localhost:"+PORT+"/"+URL+"?encoding=WIN1251";
    public static Connection getConnect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL_STRING, USER, PASSWORD);
        } catch (SQLException except) {
            Notification.getErrorConnect(except);
        }
        return con;
    }
}

