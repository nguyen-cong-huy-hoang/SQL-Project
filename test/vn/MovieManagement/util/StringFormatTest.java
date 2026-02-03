package vn.MovieManagement.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class StringFormatTest {

    // --- GROUP 1: Tests for stringLimit() ---
    @Nested
    @DisplayName("Tests for stringLimit method")
    class StringLimitTests {

        @Test
        @DisplayName("Should return TRUE if string length is within limit")
        void testWithinLimit() {
            assertTrue(StringFormat.stringLimit(10, "Hello"));
        }

        @Test
        @DisplayName("Should return TRUE if string length equals limit (Boundary)")
        void testExactLimit() {
            assertTrue(StringFormat.stringLimit(5, "12345"));
        }

        @Test
        @DisplayName("Should return FALSE if string length exceeds limit")
        void testExceedsLimit() {
            assertFalse(StringFormat.stringLimit(3, "Hello"));
        }

        @Test
        @DisplayName("Should return TRUE if input string is NULL")
        void testNullInput() {
            assertTrue(StringFormat.stringLimit(10, null));
        }
    }

    // --- GROUP 2: Tests for toSqlDate() ---
    @Nested
    @DisplayName("Tests for toSqlDate method")
    class ToSqlDateTests {

        @Test
        @DisplayName("Should convert valid string to SQL Date")
        void testValidDate() {
            String input = "2023-10-25";
            Date result = StringFormat.toSqlDate(input);
            
            assertNotNull(result);
            assertEquals("2023-10-25", result.toString());
        }

        @Test
        @DisplayName("Should return NULL if input format is invalid")
        void testInvalidFormat() {
            assertNull(StringFormat.toSqlDate("abc"));
        }

        @Test
        @DisplayName("Should return NULL if input is NULL")
        void testNullInput() {
            assertNull(StringFormat.toSqlDate(null));
        }
    }

    // --- GROUP 3: Tests for parseSqlDate() ---
    @Nested
    @DisplayName("Tests for parseSqlDate method")
    class ParseSqlDateTests {

        @Test
        @DisplayName("Should parse valid date string correctly")
        void testValidDate() {
            Date result = StringFormat.parseSqlDate("2025-01-01");
            assertEquals("2025-01-01", result.toString());
        }

        @Test
        @DisplayName("Should THROW IllegalArgumentException if format is invalid")
        void testInvalidFormat() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                StringFormat.parseSqlDate("invalid-date");
            });
            assertTrue(exception.getMessage().contains("Invalid date format"));
        }
    }

    // --- GROUP 4: Tests for isValidDate() ---
    @Nested
    @DisplayName("Tests for isValidDate method")
    class IsValidDateTests {

        @Test
        @DisplayName("Should return TRUE for valid yyyy-MM-dd format")
        void testValidDate() {
            assertTrue(StringFormat.isValidDate("2023-12-31"));
        }

        @Test
        @DisplayName("Should return FALSE for invalid format")
        void testInvalidFormat() {
            assertFalse(StringFormat.isValidDate("31-12-2023")); 
            assertFalse(StringFormat.isValidDate("Hello"));  
        }

        @Test
        @DisplayName("Should return FALSE for non-existent dates")
        void testNonExistentDate() {
            assertFalse(StringFormat.isValidDate("2023-02-30"));
        }
    }
}