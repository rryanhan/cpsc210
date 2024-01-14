package persistence;

import model.Album;
import model.ListOfAlbums;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfAlbums loa = new ListOfAlbums("Ryan Albums");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAlbumList() {
        try {
            ListOfAlbums loa = new ListOfAlbums("Ryan's Albums");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAlbumList.json");
            writer.open();
            writer.write(loa);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAlbumList.json");
            loa = reader.read();
            assertEquals("Ryan's Albums", loa.getListName());
            assertEquals(0, loa.getLength());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAlbumList() {
        try {
            ListOfAlbums loa = new ListOfAlbums("Ronald");
            loa.addAlbum(new Album("Gumbo","Young Nudy", 4.5, "Rap"));
            loa.addAlbum(new Album("Donuts", "J Dilla", 5.0, "Rap" ));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(loa);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            loa = reader.read();
            assertEquals("Ronald", loa.getListName());
            List<Album> albums = loa.returnAlbumList();
            assertEquals(2, albums.size());
            checkAlbum("Gumbo", "Young Nudy", 4.5, "Rap", albums.get(0));
            checkAlbum("Donuts", "J Dilla", 5.0, "Rap", albums.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
