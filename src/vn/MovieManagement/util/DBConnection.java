package vn.MovieManagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:sqlite:C:\\hoang\\SQL Project\\db";
    
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(url);
            return conn;
        }
        catch(SQLException e) {
            System.err.println("Loi ket noi Database: " + e.getMessage());
            return null;
        }
    }
}
