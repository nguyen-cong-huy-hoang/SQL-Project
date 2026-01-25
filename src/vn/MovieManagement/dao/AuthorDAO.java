package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import java.sql.*;

public class AuthorDAO {
    private AuthorDAO(){};
    
    public static boolean createTable() {
        String authors = "CREATE TABLE IF NOT EXISTS Authors (" +
                         "id INTEGER AUTOINCREMENT," +
                         "Name NCHAR(15) NOT NULL," +
                         "Description TEXT," +
                         "Age INTEGER," +
                         "Country NCHAR(15) NOT NULL," +
                         "User_ID INTEGER," +
                         "FOREIGN KEY (User_ID) REFERENCES Users(id),";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(authors);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addAuthor(int id, String name, String description,
                                    int age, String country, int UserID) {

        String sql = "INSERT INTO Authors(id, Name, Description, Age, Country, UserID) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);

            // Description
            if (description == null || description.isEmpty()) {
                stmt.setNull(3, Types.VARCHAR);
            } else {
                stmt.setString(3, description);
            }

            // Age
            if (age <= 0) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, age);
            }

            stmt.setString(5, country);
            stmt.setInt(6, UserID);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
