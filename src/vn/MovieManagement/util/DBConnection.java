package vn.MovieManagement.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_FOLDER = "db";
    private static final String url = "jdbc:sqlite:" + DB_FOLDER + "/data.db";
    private DBConnection(){};
    public static Connection getConnection() {
        Connection conn = null;
        try {
            File folder = new File(DB_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs(); 
            }

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.err.println("SOS: No SQLite jar file found in the lib folder!");
            e.printStackTrace();
        } catch(SQLException e) {
            System.err.println("database connection error: " + e.getMessage());
            return null;
        }
        return conn;
    }
}