package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DataDAOTest {

    @BeforeEach
    void setUp() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DROP TABLE IF EXISTS Authors");
            stmt.executeUpdate("DROP TABLE IF EXISTS Movies");
            stmt.executeUpdate("DROP TABLE IF EXISTS Users");

        } catch (SQLException e) {
            fail("Database setup failed: " + e.getMessage());
        }

        boolean check = DataDAO.createTableIfNotExist();
        assertTrue(check, "TABLE CREATION SHOULD SUCCEED");
    }

    @Test
    void testAddMovie() {
        insertUser(1, "admin", "123456");

        boolean check = DataDAO.addMovies(
                "batman",
                null,
                null,
                null,
                null,
                "M001",
                1
        );

        assertTrue(check, "ADD MOVIE SHOULD SUCCEED");
    }

    @Test
    void testAddAuthor() {
        insertUser(1, "admin", "123456");

        DataDAO.addMovies(
                "batman",
                null,
                null,
                null,
                null,
                "M001",
                1
        );

        boolean check = DataDAO.addAuthor(
                1,
                "hoang",
                null,
                20,
                "Vietnam",
                "M001"
        );

        assertTrue(check, "ADD AUTHOR SHOULD SUCCEED");
    }


    private void insertUser(int id, String username, String password) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(
                "INSERT INTO Users(id, username, password) VALUES (" +
                id + ", '" + username + "', '" + password + "')"
            );

        } catch (SQLException e) {
            fail("Insert user failed: " + e.getMessage());
        }
    }
}
