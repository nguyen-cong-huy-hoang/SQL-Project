package vn.MovieManagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vn.MovieManagement.model.author; 

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthorManagementTest {

    private AuthorManagement manager;

    @Mock
    private author mockAuthor1;

    @Mock
    private author mockAuthor2;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        
        manager = new AuthorManagement();


        when(mockAuthor1.getID()).thenReturn(1);
        when(mockAuthor1.getName()).thenReturn("To Hoai"); 
        when(mockAuthor1.getAge()).thenReturn(60);
        when(mockAuthor1.getCountry()).thenReturn("Vietnam");
        when(mockAuthor1.getDescription()).thenReturn("Famous Vietnamese writer");
        when(mockAuthor1.getUserID()).thenReturn(101);

        when(mockAuthor2.getID()).thenReturn(2);
        when(mockAuthor2.getName()).thenReturn("J.K. Rowling");
        when(mockAuthor2.getAge()).thenReturn(55);
        when(mockAuthor2.getCountry()).thenReturn("UK");
        when(mockAuthor2.getDescription()).thenReturn("Author of Harry Potter");
        when(mockAuthor2.getUserID()).thenReturn(102);
    }

    @Test
    @DisplayName("Test Initialization: List size should be 0 initially")
    void testInitialSize() {
        assertEquals(0, manager.Size(), "Size must be 0 when initialized");
    }

    @Test
    @DisplayName("Test Add: Should increase size when adding authors")
    void testAdd() {

        manager.add(0, mockAuthor1);
        assertEquals(1, manager.Size(), "Size should be 1 after adding 1 author");


        manager.add(1, mockAuthor2);
        assertEquals(2, manager.Size(), "Size should be 2 after adding 2 authors");
    }

    @Test
    @DisplayName("Test Remove: Should decrease size when removing an author")
    void testRemove() {

        manager.add(0, mockAuthor1);
        manager.add(1, mockAuthor2);

 
        manager.remove(0);

        assertEquals(1, manager.Size(), "Size should be 1 after removal");
    }

    @Test
    @DisplayName("Test Remove: Should handle removal from empty list gracefully")
    void testRemoveFromEmptyList() {

        assertDoesNotThrow(() -> manager.remove(0));
        assertEquals(0, manager.Size());
    }

    @Test
    @DisplayName("Test Clear: Should remove all authors and reset size to 0")
    void testClear() {

        manager.add(0, mockAuthor1);
        manager.add(1, mockAuthor2);

        manager.clear();

        assertEquals(0, manager.Size(), "Size must be 0 after clear()");
    }

    @Test
    @DisplayName("Test Print: Should execute without exceptions")
    void testPrint() {

        manager.add(0, mockAuthor1);
        manager.add(1, mockAuthor2);

        assertDoesNotThrow(() -> manager.print());
    }
}