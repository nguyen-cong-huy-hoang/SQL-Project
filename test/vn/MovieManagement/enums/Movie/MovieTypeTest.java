package vn.MovieManagement.enums.Movie;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class MovieTypeTest {

    @Mock
    PreparedStatement stmt;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- GROUP 1: Tests for Character types (Name, Code) ---
    @Nested
    @DisplayName("Tests for MovieTypeChar")
    class CharTests {

        @Test
        @DisplayName("Should bind valid string correctly")
        void testBindValid() throws SQLException {
            String validName = "Titanic";

            MovieTypeChar.NAME.bind(stmt, 1, validName);

            verify(stmt).setString(1, validName);
        }

        @Test
        @DisplayName("Should throw exception if NAME is null")
        void testNameNotNull() {

            assertThrows(IllegalArgumentException.class, () -> 
                MovieTypeChar.NAME.bind(stmt, 1, null));
        }

        @Test
        @DisplayName("Should allow NULL for CODE column")
        void testCodeNullable() throws SQLException {

            MovieTypeChar.CODE.bind(stmt, 2, null);


            verify(stmt).setNull(2, Types.VARCHAR);
        }

        @Test
        @DisplayName("Should throw exception if string exceeds 15 characters")
        void testStringTooLong() {

            String longString = "1234567890123456";

            assertThrows(IllegalArgumentException.class, () -> 
                MovieTypeChar.NAME.bind(stmt, 1, longString));
        }
    }

    // --- GROUP 2: Tests for Integer types (User_ID) ---
    @Nested
    @DisplayName("Tests for MovieTypeInteger")
    class IntegerTests {

        @Test
        @DisplayName("Should bind valid integer correctly")
        void testBindValid() throws SQLException {
            MovieTypeInteger.USER_ID.bind(stmt, 1, 99);

            verify(stmt).setInt(1, 99);
        }

        @Test
        @DisplayName("Should throw exception if User_ID is null")
        void testUserIdNotNull() {
            assertThrows(IllegalArgumentException.class, () -> 
                MovieTypeInteger.USER_ID.bind(stmt, 1, null));
        }
    }

    // --- GROUP 3: Tests for Date types ---
    @Nested
    @DisplayName("Tests for MovieTypeDate")
    class DateTests {

        @Test
        @DisplayName("Should convert and bind valid date string")
        void testBindValid() throws SQLException {
            String dateStr = "2023-01-01";

            MovieTypeDate.DATE.bind(stmt, 3, dateStr);

            verify(stmt).setDate(eq(3), any(Date.class));
        }

        @Test
        @DisplayName("Should throw exception for invalid date format")
        void testInvalidDate() {

            String invalidDate = "invalid-date-text";

            assertThrows(IllegalArgumentException.class, () -> 
                MovieTypeDate.DATE.bind(stmt, 3, invalidDate));
        }

        @Test
        @DisplayName("Should set NULL if date input is null")
        void testDateNullable() throws SQLException {
            MovieTypeDate.DATE.bind(stmt, 3, null);

            verify(stmt).setNull(3, Types.DATE);
        }
    }
    
    // --- GROUP 4: Tests for Text types (Description, Link, Duration) ---
    @Nested
    @DisplayName("Tests for MovieTypeText")
    class TextTests {
        
        @Test
        @DisplayName("Should bind valid description correctly")
        void testBindValidDescription() throws SQLException {
            String desc = "This is a very long description about the movie...";

            MovieTypeText.DESCRIPTION.bind(stmt, 5, desc);

            verify(stmt).setString(5, desc);
        }

        @Test
        @DisplayName("Should set NULL if text input is null")
        void testTextNullable() throws SQLException {
            MovieTypeText.LINK.bind(stmt, 6, null);

            verify(stmt).setNull(6, Types.VARCHAR);
        }
    }
}