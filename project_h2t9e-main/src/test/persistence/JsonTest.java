package persistence;


import model.Album;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAlbum(String name, String artist, Double rating, String genre, Album album) {
        assertEquals(name, album.getName());
        assertEquals(artist, album.getArtist());
        assertEquals(rating, album.getRating());
        assertEquals(genre, album.getGenre());
    }
}
