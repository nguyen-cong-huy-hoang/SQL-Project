package vn.MovieManagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:sqlite:data.db";
    
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        }
        catch(SQLException e) {
            System.err.println("database connection error: " + e.getMessage());
            return null;
        }
    }
}
