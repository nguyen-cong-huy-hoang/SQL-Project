package vn.MovieManagement.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import vn.MovieManagement.enums.Author.AuthorSortField;
import vn.MovieManagement.enums.Author.IAuthorType;
import vn.MovieManagement.model.author;
import vn.MovieManagement.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthorDAOTest {
    @Mock private Connection mockConn;
    @Mock private PreparedStatement mockStmt;
    @Mock private Statement mockStatement;
    @Mock private ResultSet mockRs;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    // --- TEST 1: Create Table ---
    @Test
    @DisplayName("Test createTable: Should execute SQL successfully")
    void testCreateTable() throws SQLException {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConn);
            when(mockConn.createStatement()).thenReturn(mockStatement);
            when(mockStatement.execute(anyString())).thenReturn(true);

            boolean result = AuthorDAO.createTable();

            assertTrue(result);
            verify(mockStatement).execute(contains("CREATE TABLE IF NOT EXISTS Authors"));
        }
    }

    // --- TEST 2: Add Author (Success) ---
    @Test
    @DisplayName("Test addAuthor: Should insert valid data successfully")
    void testAddAuthor_Success() throws SQLException {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            // Given
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

            boolean result = AuthorDAO.addAuthor(
                "Nam Cao", "Vietnam Writer", 45, "Vietnam", 101
            );

            assertTrue(result);

            verify(mockStmt).setString(1, "Nam Cao");    // Name
            verify(mockStmt).setString(2, "Vietnam Writer"); // Desc
            verify(mockStmt).setInt(3, 45);              // Age
            verify(mockStmt).setString(4, "Vietnam");    // Country
            verify(mockStmt).setInt(5, 101);             // UserID

            verify(mockStmt).executeUpdate();
        }
    }

    // --- TEST 3: Add Author (Validation Fail) ---
    @Test
    @DisplayName("Test addAuthor: Should fail if Name is too long")
    void testAddAuthor_ValidationFail() {
        String longName = "This name is way too long for the database";

        // When
        boolean result = AuthorDAO.addAuthor(
            longName, "Desc", 30, "VN", 1
        );

        // Then
        assertFalse(result);
    
    }

    // --- TEST 4: Add Author (Null Age Handling) ---
    @Test
    @DisplayName("Test addAuthor: Should set NULL if Age is <= 0")
    void testAddAuthor_NullAge() throws SQLException {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

            AuthorDAO.addAuthor("To Hoai", "Desc", -1, "VN", 1);


            verify(mockStmt).setNull(3, Types.INTEGER);
        }
    }

    // --- TEST 5: Find Author ---
    @Test
    @DisplayName("Test find: Should return a list of authors")
    void testFind() throws SQLException {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeQuery()).thenReturn(mockRs);

            when(mockRs.next()).thenReturn(true).thenReturn(false); 
            
            // Stub data columns
            when(mockRs.getString("Name")).thenReturn("JK Rowling");
            when(mockRs.getString("Description")).thenReturn("UK Writer");
            when(mockRs.getInt("age")).thenReturn(55);
            when(mockRs.getString("country")).thenReturn("UK");
            when(mockRs.getInt("id")).thenReturn(1);
            when(mockRs.getInt("User_ID")).thenReturn(100);

            ArrayList<author> result = AuthorDAO.find("Rowling", 100);

            assertEquals(1, result.size());
            assertEquals("JK Rowling", result.get(0).getName());
            assertEquals(55, result.get(0).getAge());
        }
    }

    // --- TEST 6: Update Author ---
    @Test
    @DisplayName("Test update: Should execute update using Field Strategy")
    void testUpdate() throws SQLException {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
            IAuthorType mockField = mock(IAuthorType.class);
            when(mockField.getColumn()).thenReturn("Name");

            boolean res = AuthorDAO.update(100, 1, mockField, "New Name");

            assertTrue(res);
            
            verify(mockField).bind(mockStmt, 1, "New Name");
            verify(mockStmt).setInt(2, 100); 
            verify(mockStmt).setInt(3, 1);   
            verify(mockStmt).executeUpdate();
        }
    }

    // --- TEST 7: Sort Author ---
    @Test
    @DisplayName("Test sort: Should include ORDER BY in SQL")
    void testSort() throws SQLException {
        try (MockedStatic<DBConnection> mockedDB = mockStatic(DBConnection.class)) {
            mockedDB.when(DBConnection::getConnection).thenReturn(mockConn);
            when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
            when(mockStmt.executeQuery()).thenReturn(mockRs);
            AuthorSortField mockSort = mock(AuthorSortField.class);
            when(mockSort.getColumn()).thenReturn("Age");

            AuthorDAO.sort(1, mockSort, true); 

            verify(mockConn).prepareStatement(argThat(sql -> 
                sql.contains("ORDER BY Age DESC")
            ));
        }
    }
}