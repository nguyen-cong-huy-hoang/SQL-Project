package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import java.sql.*;

public class MovieDAO {
    public static boolean createTable() {
        String movies = "CREATE TABLE IF NOT EXISTS Movies (" +
                        "id INTEGER," +
                        "Name NCHAR(15) NOT NULL," +
                        "Description TEXT," +
                        "Link TEXT," +
                        "Duration TEXT," +
                        "Date DATE," +
                        "Code CHAR(10) PRIMARY KEY," +
                        "FOREIGN KEY (id) REFERENCES Users(id))";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(movies);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     public static boolean addMovies(String name, String description, String link,
                                    String duration, String date, String code, int id) {

        String sql = "INSERT INTO Movies(id, Name, Description, Link, Duration, Date, Code) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);

            if (description == null || description.isEmpty()) {
                stmt.setNull(3, Types.VARCHAR);
            } else {
                stmt.setString(3, description);
            }
            if (link == null || link.isEmpty()) {
                stmt.setNull(4, Types.VARCHAR);
            } else {
                stmt.setString(4, link);
            }
            if (duration == null || duration.isEmpty()) {
                stmt.setNull(5, Types.VARCHAR);
            } else {
                stmt.setString(5, duration);
            }
            if (date == null || date.isEmpty()) {
                stmt.setNull(6, Types.DATE);
            } else {
                stmt.setDate(6, Date.valueOf(date)); 
            }

            stmt.setString(7, code);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}