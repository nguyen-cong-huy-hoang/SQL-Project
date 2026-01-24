package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import java.sql.*;

public class AuthorDAO {
    private AuthorDAO(){};
    
    public static boolean createTable() {
        String authors = "CREATE TABLE IF NOT EXISTS Authors (" +
                         "id INTEGER," +
                         "Name NCHAR(15) NOT NULL," +
                         "Description TEXT," +
                         "Age INTEGER," +
                         "Country NCHAR(15) NOT NULL," +
                         "Code CHAR(10)," +
                         "FOREIGN KEY (id) REFERENCES Users(id)," +
                         "FOREIGN KEY (Code) REFERENCES Movies(Code))";
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
                                    int age, String country, String code) {

        String sql = "INSERT INTO Authors(id, Name, Description, Age, Country, Code) " +
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
            stmt.setString(6, code);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
