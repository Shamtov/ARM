package ru.coddvrn.Application.Connection;

import ru.coddvrn.Application.Notifications.Notification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private final static String USER = "SYSDBA";
    private final static String PASSWORD = "masterkey";
    private final static String URL = "";
    private final static String PORT = "";
    private final static String URL_STRING = "jdbc:firebirdsql://localhost:3050/C:\\Users\\SergeyIggy\\IdeaProjects\\PROJECTS_23_04.FDB?encoding=WIN1251";

    public static Connection getConnect() {

        Connection con = null;
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            con = DriverManager.getConnection(URL_STRING, USER, PASSWORD);
//            con.setAutoCommit(true);
        } catch (SQLException except) {
            except.printStackTrace();
            new Notification().getConnect(except);
        }
        catch (ClassNotFoundException ignore){/*NOP*/}
        return con;
    }
}

