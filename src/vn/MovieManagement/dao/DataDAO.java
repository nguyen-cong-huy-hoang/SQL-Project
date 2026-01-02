package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import java.sql.*;

public class DataDAO {
    public void createTableIfNotExist() {
        String sql = "CREATE TABLE IF NOT EXISTS Movies (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "Name NCHAR(15) NOT NULL, " +
                     "Description TEXT," +
                     "Link TEXT," +
                     "Duration TIME," +
                     "Date DATE," +
                     "Code CHAR(10) NOT NULL PRIMARY KEY)";
        String sql2 = "CREATE TABLE IF NOT EXISTS Movies (" +
                      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                      "Name NCHAR(15) NOT NULL, " +
                      "Description TEXT," +
                      "Age INTEGER," + 
                      "Country NCHAR(15)," +
                      "Code CHAR(10) FOREIGN KEY REFERENCES Movies(Code))"; 
        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            stmt.execute(sql2);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMovies()


    
}
