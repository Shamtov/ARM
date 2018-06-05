package ru.coddvrn.Application.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private final static String USER = "SYSDBA";
    private final static String PASSWORD = "masterkey";
    private final static String URL = "";
    private final static String PORT = "";
    private final static String URL_STRING = "jdbc:firebirdsql://localhost:3050/C:\\Users\\D\\Downloads\\Универ\\PROJECTS123.FDB?encoding=WIN1251";
    public static Connection getConnect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL_STRING, USER, PASSWORD);
        } catch (SQLException except) {
            except.printStackTrace();
        }
        System.out.println("Connection success");
        return con;
    }
}

