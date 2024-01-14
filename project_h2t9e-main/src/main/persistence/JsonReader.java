package persistence;

import model.Album;
import model.ListOfAlbums;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a reader that reads an album list from JSON data stored in file
// Sources: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfAlbums read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfAlbums(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses album list from JSON object and returns it
    private ListOfAlbums parseListOfAlbums(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ListOfAlbums loa = new ListOfAlbums(name);
        addAlbums(loa, jsonObject);
        return loa;
    }

    // MODIFIES: loa
    // EFFECTS: parses albums from JSON object and adds them to workroom
    private void addAlbums(ListOfAlbums loa, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("albums");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addAlbum(loa, nextThingy);
        }
    }

    // MODIFIES: loa
    // EFFECTS: parses album from JSON object and adds it to workroom
    private void addAlbum(ListOfAlbums loa, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String artist = jsonObject.getString("artist");
        Double rating = jsonObject.getDouble("rating");
        String genre = jsonObject.getString("genre");
        Album album = new Album(name, artist, rating, genre);
        loa.addAlbum(album);
    }

}

