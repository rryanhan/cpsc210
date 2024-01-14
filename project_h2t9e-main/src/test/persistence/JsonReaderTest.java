package persistence;


import model.Album;
import model.ListOfAlbums;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfAlbums loa = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAlbumList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAlbumList.json");
        try {
            ListOfAlbums loa = reader.read();
            assertEquals("Ryan's Albums", loa.getListName());
            assertEquals(0, loa.getLength());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAlbumList() {
        JsonReader reader = new JsonReader("./data/testReaderAlbumList.json");
        try {
            ListOfAlbums loa = reader.read();
            assertEquals("Carti Gangalang", loa.getListName());
            List<Album> albums = loa.returnAlbumList();
            assertEquals(2, albums.size());
            checkAlbum("Vinyl Days","Logic", 5.0, "Rap", albums.get(0));
            checkAlbum("Whole Lotta Red","Playboi Carti", 4.5, "Rap", albums.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}