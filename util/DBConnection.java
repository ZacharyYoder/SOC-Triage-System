package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;

    // DB credentials
    private static final String URL = "jdbc:mysql://localhost:3306/incident_db";
    private static final String USER = "root";
    private static final String PASSWORD = "toor";

    // private constructor
    private DBConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            System.out.println("DB Connection failed: " + e.getMessage());
        }
    }

    // get single instance
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // expose connection
    public Connection getConnection() {
        return connection;
    }
}