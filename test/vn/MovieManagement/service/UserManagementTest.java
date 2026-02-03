package vn.MovieManagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vn.MovieManagement.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserManagementTest {

    private UserManagement manager;

    // Create Mock objects for User
    @Mock
    private User mockUser1;

    @Mock
    private User mockUser2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        manager = new UserManagement();
    }

    @Test
    @DisplayName("Test Initialization: List size should be 0")
    void testInitialSize() {
        assertEquals(0, manager.Size(), "Size must be 0 when initialized");
    }

    @Test
    @DisplayName("Test Add: Should increase size when adding users")
    void testAdd() {

        manager.add(0, mockUser1);
        assertEquals(1, manager.Size(), "Size should be 1 after adding 1 user");

        manager.add(1, mockUser2);
        assertEquals(2, manager.Size(), "Size should be 2 after adding 2 users");
    }

    @Test
    @DisplayName("Test Remove: Should decrease size when removing a user")
    void testRemove() {
        manager.add(0, mockUser1);
        manager.add(1, mockUser2);
        manager.remove(0);
        assertEquals(1, manager.Size(), "Size should be 1 after removal");
    }

    @Test
    @DisplayName("Test Clear: Should remove all users and reset size to 0")
    void testClear() {
        manager.add(0, mockUser1);
        manager.add(1, mockUser2);
        manager.clear();
        assertEquals(0, manager.Size(), "Size must be 0 after clear()");
    }
    
    @Test
    @DisplayName("Test Remove Exception: Should throw exception if removing from empty list")
    void testRemoveFromEmptyList() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            manager.remove(0);
        });
    }
}