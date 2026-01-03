package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import java.sql.*;

public class DataDAO {
    public void createTableIfNotExist() {
        String sql = "CREATE TABLE IF NOT EXISTS Movies (" +
                     "id INTEGER FOREIGN KEY REFERENCES Users(id)," +
                     "Name NCHAR(15) NOT NULL, " +
                     "Description TEXT," +
                     "Link TEXT," +
                     "Duration TEXT," +
                     "Date DATE," +
                     "Code CHAR(10) NOT NULL PRIMARY KEY)";
        String sql2 = "CREATE TABLE IF NOT EXISTS Authors (" +
                      "id INTEGER FOREIGN KEY REFERENCES Users(id)," +
                      "Name NCHAR(15) NOT NULL, " +
                      "Description TEXT," +
                      "Age INTEGER," + 
                      "Country NCHAR(15) NOT NULL," +
                      "Code CHAR(10) FOREIGN KEY REFERENCES Movies(Code))"; 
        String sql3 = "CREATE TABLE IF NOT EXISTS Users (" +
                      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                      "username NCHAR(15) NOT NULL," +
                      "password CHAR(100))";
        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            stmt.execute(sql2);
            stmt.execute(sql3);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addMovies(String name, String description, String link, 
    String duration, String date, String code, int id) {
        String sql = "INSERT INTO Movies(id,Name, Description, Link, Duration, Date, Code) VALUES(?,?,?,?,?,?,?)"; 

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            if(description == "") {
                stmt.setNull(3, Types.VARCHAR);
            } else stmt.setString(3, description);
            if(link == "") {
                stmt.setNull(4, Types.VARCHAR);
            } else stmt.setString(4, link);
            if(duration == "") {
                stmt.setNull(5, Types.VARCHAR);
            } else stmt.setString(5, duration);
            if(date == "") {
                stmt.setNull(6, Types.VARCHAR);
            } else stmt.setString(6, date);
            stmt.setString(7, code);
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addAuthor(int id, String name, String description, int age, String country, String code) {
        String sql = "INSERT INTO Authors(id,Name, Description, Age, Country, Code) VALUES(?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            if(description == "") {
                stmt.setNull(3, Types.VARCHAR);
            } else stmt.setString(3, description);
            stmt.setInt(4, age);
            stmt.setString(5, country);
            stmt.setString(6, code);
            stmt.executeUpdate();
        } catch(SQLException e) {
                    System.err.println(e.getMessage());
                    return false;
        }
        return true;
    }
    
    


    
}
