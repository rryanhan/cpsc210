package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMusicTest {
    private ListOfAlbums testlistofalbums;
    private Album a1;
    private Album a2;
    private Album a3;

    @BeforeEach
    void runBefore() {
        testlistofalbums = new ListOfAlbums("Test Album");
        a1 = new Album("College Park", "Logic", 5.0, "Rap");
        a2 = new Album("Whole Lotta Red", "Playboi Carti", 4.5, "Rap");
        a3 = new Album("OK Computer", "Radiohead", 4.5, "Rock");
    }
    @Test
    void testConstructor() {
        assertEquals("Test Album", testlistofalbums.getListName());
        assertEquals(0, testlistofalbums.getLength());
        assertTrue(testlistofalbums.albumListEmpty());

        assertEquals("College Park", a1.getName());
        assertEquals("Logic", a1.getArtist());
        assertEquals(5.0, a1.getRating());
        assertEquals("Rap", a1.getGenre());

    }
    @Test
    void testAddAlbum() {
        assertTrue(testlistofalbums.addAlbum(a1));
        assertTrue(testlistofalbums.addAlbum(a2));
        assertFalse(testlistofalbums.addAlbum(a1));
    }

    @Test
    void testListOfAlbums() {
        testlistofalbums.addAlbum(a1);
        testlistofalbums.addAlbum(a2);
        testlistofalbums.addAlbum(a3);
        assertEquals(a1, testlistofalbums.searchForAlbum(0));
        assertEquals(a2, testlistofalbums.searchForAlbum(1));
        assertEquals(a3, testlistofalbums.searchForAlbum(2));
        assertEquals(3, testlistofalbums.getLength());

        assertFalse(testlistofalbums.albumListEmpty());
    }

    @Test
    void testReturnAlbum() {
        ListOfAlbums returnalbum = new ListOfAlbums("Return Album");
        returnalbum.addAlbum(a1);
        returnalbum.addAlbum(a2);
        testlistofalbums.addAlbum(a1);
        testlistofalbums.addAlbum(a2);
        testlistofalbums.addAlbum(a3);
        assertFalse(returnalbum.returnAlbumList().equals(testlistofalbums.returnAlbumList()));
        returnalbum.addAlbum(a3);
        assertTrue(returnalbum.returnAlbumList().equals(testlistofalbums.returnAlbumList()));
    }

}