package vn.MovieManagement.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import vn.MovieManagement.model.Movie;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MovieManagementTest {

    private MovieManagement manager;

    // Create Mock objects for Movie
    @Mock
    private Movie mockMovie1;

    @Mock
    private Movie mockMovie2;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        

        manager = new MovieManagement();


        when(mockMovie1.getID()).thenReturn(1);
        when(mockMovie1.getName()).thenReturn("Inception");
        when(mockMovie1.getDuration()).thenReturn("148 min");
        when(mockMovie1.getCode()).thenReturn("MV001");
        when(mockMovie1.getDate()).thenReturn("2010-07-16"); 
        when(mockMovie1.getDescription()).thenReturn("Dream within a dream");


        when(mockMovie2.getID()).thenReturn(2);
        when(mockMovie2.getName()).thenReturn("Titanic");
        when(mockMovie2.getDuration()).thenReturn("195 min");
        when(mockMovie2.getCode()).thenReturn("MV002");
        when(mockMovie2.getDate()).thenReturn("1997-12-19");
        when(mockMovie2.getDescription()).thenReturn("Ship hits iceberg");
    }

    @Test
    @DisplayName("Test Initialization: List size should be 0")
    void testInitialSize() {
        assertEquals(0, manager.Size(), "Size must be 0 initially");
    }

    @Test
    @DisplayName("Test Add: Should increase size when adding movies")
    void testAdd() {

        manager.add(0, mockMovie1);
        assertEquals(1, manager.Size(), "Size should be 1 after adding first movie");

  
        manager.add(1, mockMovie2);
        assertEquals(2, manager.Size(), "Size should be 2 after adding second movie");
    }

    @Test
    @DisplayName("Test Remove: Should decrease size")
    void testRemove() {

        manager.add(0, mockMovie1);
        manager.add(1, mockMovie2);

        manager.remove(0);

   
        assertEquals(1, manager.Size(), "Size should be 1 after removal");
    }

    @Test
    @DisplayName("Test Clear: Should empty the list")
    void testClear() {
        manager.add(0, mockMovie1);
        manager.add(1, mockMovie2);

        manager.clear();

        assertEquals(0, manager.Size(), "Size must be 0 after clear");
    }

    @Test
    @DisplayName("Test Print: Should run without errors")
    void testPrint() {

        manager.add(0, mockMovie1);
        manager.add(1, mockMovie2);
        assertDoesNotThrow(() -> manager.print());
    }
}