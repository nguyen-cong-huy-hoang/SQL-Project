package vn.MovieManagement.enums.Author;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class AuthorTypeTest {


    @Mock
    private PreparedStatement stmt;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    // --- GROUP 1: Tests for AuthorTypeChar (Name, Country) ---
    @Nested
    @DisplayName("Tests for AuthorTypeChar")
    class CharTests {

        @Test
        @DisplayName("Should bind valid string correctly")
        void testBindValid() throws SQLException {

            String validName = "JK Rowling";

            AuthorTypeChar.NAME.bind(stmt, 1, validName);

            verify(stmt).setString(1, validName);
        }

        @Test
        @DisplayName("Should throw Exception when value is NULL")
        void testBindNull_ThrowsException() {

            assertThrows(IllegalArgumentException.class, () -> {
                AuthorTypeChar.NAME.bind(stmt, 1, null);
            });
        }

        @Test
        @DisplayName("Should throw Exception when string is too long (>15 chars)")
        void testBindTooLong_ThrowsException() {
            String longString = "1234567890123456"; 

            assertThrows(IllegalArgumentException.class, () -> {
                AuthorTypeChar.COUNTRY.bind(stmt, 1, longString);
            });
        }
    }

    // --- GROUP 2: Tests for AuthorTypeInteger (User_ID, Age) ---
    @Nested
    @DisplayName("Tests for AuthorTypeInteger")
    class IntegerTests {

        @Test
        @DisplayName("Should bind valid integer correctly")
        void testBindValid() throws SQLException {
            int age = 45;

            AuthorTypeInteger.AGE.bind(stmt, 2, age);
            verify(stmt).setInt(2, 45);
        }

        @Test
        @DisplayName("Should bind NULL correctly (setNull)")
        void testBindNull() throws SQLException {

            AuthorTypeInteger.AGE.bind(stmt, 2, null);

            verify(stmt).setNull(2, Types.INTEGER);
        }
    }

    // --- GROUP 3: Tests for AuthorTypeText (Description) ---
    @Nested
    @DisplayName("Tests for AuthorTypeText")
    class TextTests {

        @Test
        @DisplayName("Should bind valid text correctly")
        void testBindValid() throws SQLException {
  
            String description = "A famous British author.";

            AuthorTypeText.DESCRIPTION.bind(stmt, 3, description);

            verify(stmt).setString(3, description);
        }

        @Test
        @DisplayName("Should bind NULL correctly (setNull)")
        void testBindNull() throws SQLException {
            AuthorTypeText.DESCRIPTION.bind(stmt, 3, null);

            verify(stmt).setNull(3, Types.VARCHAR);
        }
    }
}