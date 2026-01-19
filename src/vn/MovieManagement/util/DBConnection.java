package vn.MovieManagement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:sqlite:data.db";
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        }  catch (ClassNotFoundException e) {
            System.err.println("SOS: Khong tim thay file jar SQLite trong thu muc lib!");
            e.printStackTrace();
        } catch(SQLException e) {
            System.err.println("database connection error: " + e.getMessage());
            return null;
        }
        return conn;
    }
}
