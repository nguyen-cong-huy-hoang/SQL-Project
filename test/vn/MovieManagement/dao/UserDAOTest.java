package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    @BeforeEach
    void setUp() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DROP TABLE IF EXISTS Users");

            String sql = "CREATE TABLE Users (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "username TEXT UNIQUE NOT NULL," +
                         "password TEXT NOT NULL)";
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Database setup failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Register: Successfully create a new user")
    void testRegisterSuccess() {

        String username = "hungo";
        String password = "123456";

        boolean result = UserDAO.register(username, password);

        assertTrue(result, "Registration should return TRUE");

        assertTrue(
            UserDAO.checkUserName(username),
            "User should exist in database after successful registration"
        );
    }

    @Test
    @DisplayName("Register: Duplicate username is not allowed")
    void testRegisterDuplicate() {

        UserDAO.register("admin", "admin123");

        boolean result = UserDAO.register("admin", "newpass");

        assertFalse(
            result,
            "Registering with a duplicate username should return FALSE"
        );
    }

    @Test
    @DisplayName("Login: Successful login with correct credentials")
    void testLoginSuccess() {

        UserDAO.register("lananh", "secret");

        int userId = UserDAO.checkLogin("lananh", "secret");

        assertTrue(
            userId > 0,
            "Successful login should return a valid user ID (> 0)"
        );
    }

    @Test
    @DisplayName("Login: Fail with wrong username or password")
    void testLoginFail() {

        UserDAO.register("tuan", "123");

        int wrongPasswordId = UserDAO.checkLogin("tuan", "456");
        assertEquals(
            -1,
            wrongPasswordId,
            "Login with wrong password should return -1"
        );

        int wrongUserId = UserDAO.checkLogin("non_existing_user", "123");
        assertEquals(
            -1,
            wrongUserId,
            "Login with non-existing username should return -1"
        );
    }
}
