package vn.MovieManagement.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.sql.Connection;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionTest {

    @Test
    @DisplayName("Database connection check successful")
    void testConnectionSuccess() {
        System.out.println("Connecting to SQLite...");
        Connection conn = DBConnection.getConnection();

        assertNotNull(conn, "Error: Connection object is null (Unable to connect)!");

        try {
            boolean isClosed = conn.isClosed();
            assertFalse(isClosed, "Error: Connection has been closed!");
            
            System.out.println("connected to: " + conn.getMetaData().getDriverName());

            conn.close();
            
        } catch (SQLException e) {
            fail("Encountered an unexpected SQL error: " + e.getMessage());
        }
    }
}