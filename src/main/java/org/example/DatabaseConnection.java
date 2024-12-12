package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database configuration parameters
    private  final String URL = "jdbc:mysql://localhost:3306/devoirlibre2";
    private  final String USERNAME = "root";
    private  final String PASSWORD = "root";

    // Logging


    // Prevent instantiation
    public DatabaseConnection() {}


    public Connection getConnection() throws SQLException {
        try {
            // Ensure the MySQL JDBC driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish and return the database connection
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Optional: Set connection properties
             // Recommended for more control over transactions

            return connection;
        } catch (ClassNotFoundException e) {
            // Log and rethrow as SQLException

            throw new SQLException("Database driver not found", e);
        }
    }


    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
