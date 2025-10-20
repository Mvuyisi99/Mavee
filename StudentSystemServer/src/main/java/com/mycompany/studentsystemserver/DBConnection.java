package com.mycompany.studentsystemserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mvuyi
 */
public class DBConnection {

    public static Connection derbyConnection() throws SQLException {
        String DATABASE_URL = "jdbc:derby://localhost:1527/StudentSystem";
        String username = "Administrator";
        String password = "Admin";
        Connection connection = DriverManager.getConnection(DATABASE_URL,
                username, password);
        return connection;
    }

}