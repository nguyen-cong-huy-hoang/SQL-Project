package vn.MovieManagement.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import vn.MovieManagement.enums.Movie.MovieSortField;
import vn.MovieManagement.enums.Movie.MovieTypeChar;
import vn.MovieManagement.model.Movie;
import vn.MovieManagement.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MovieDAOTest {

    @Mock private Connection mockConn;
    @Mock private PreparedStatement mockStmt;
    @Mock private Statement mockStatement;
    @Mock private ResultSet mockRs;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- TEST 1: Add Movie ---
    @Test
    @DisplayName("Test addMovies: Should insert successfully")
    void testAddMovies_Success() throws SQLException {

        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

            boolean result = MovieDAO.addMovies(
                "Inception", "Dream movie", "http://link", "148min", "2010-07-16", "MV001", 1
            );

            assertTrue(result, "Should return true on success");
            
            verify(mockStmt).setString(1, "Inception"); 
            verify(mockStmt).setString(6, "MV001");    
            verify(mockStmt).setInt(7, 1);         
            
            verify(mockStmt).executeUpdate();
        }
    }

    @Test
    @DisplayName("Test addMovies: Should return false if validation fails (Name too long)")
    void testAddMovies_ValidationFail() {
        String longName = "This name is definitely way too long for the database limit";

        boolean result = MovieDAO.addMovies(
            longName, "Desc", "Link", "Duration", "2023-01-01", "MV001", 1
        );

        assertFalse(result);
    }

    // --- TEST 2: Find Movie ---
    @Test
    @DisplayName("Test find: Should return list of movies")
    void testFind() throws SQLException {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeQuery()).thenReturn(mockRs);

            when(mockRs.next()).thenReturn(true).thenReturn(false);

            when(mockRs.getString("Name")).thenReturn("Titanic");
            when(mockRs.getString("Code")).thenReturn("MV002");
            when(mockRs.getInt("id")).thenReturn(10);
            when(mockRs.getInt("User_ID")).thenReturn(5);
            when(mockRs.getDate("Date")).thenReturn(Date.valueOf("1997-12-19"));

            ArrayList<Movie> list = MovieDAO.find("Titanic", 5);

            assertEquals(1, list.size());
            assertEquals("Titanic", list.get(0).getName());
            assertEquals("MV002", list.get(0).getCode());
        }
    }

    // --- TEST 3: Update Movie ---
    @Test
    @DisplayName("Test update: Should execute update using Enum Strategy")
    void testUpdate() throws SQLException {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

            boolean result = MovieDAO.update(1, 10, MovieTypeChar.NAME, "New Name");

            assertTrue(result);
 
            verify(mockStmt).setString(1, "New Name");

            verify(mockStmt).setInt(2, 1);

            verify(mockStmt).setInt(3, 10);
            
            verify(mockStmt).executeUpdate();
        }
    }

    // --- TEST 4: Sort Movie ---
    @Test
    @DisplayName("Test sort: Should execute select with order by")
    void testSort() throws SQLException {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeQuery()).thenReturn(mockRs);

            MovieDAO.sort(1, MovieSortField.NAME, true);
            verify(mockConn).prepareStatement(argThat(sql -> 
                sql.contains("ORDER BY Name DESC")
            ));
            
            verify(mockStmt).executeQuery();
        }
    }
}