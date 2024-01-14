package model;


import org.json.JSONObject;
import persistence.Writable;

// Represent an album with album name, artist name, rating (from 0-5), and genre.
public class Album implements Writable {
    private String name;            // Album name
    private String artist;          // Artist of Album
    private Double rating;          // Rating of Album
    private String genre;           // Album genre


    public Album(String name, String artist, Double rating, String genre) {
        this.name = name;
        this.artist = artist;
        this.rating = rating;
        this.genre = genre;

    }

    // effects: returns the album name
    public String getName() {
        return this.name;
    }

    // effects: returns the artist of the album
    public String getArtist() {
        return this.artist;
    }

    // effects: returns the rating of the album
    public Double getRating() {
        return this.rating;
    }

    // effects: returns the genre of the album
    public String getGenre() {
        return this.genre;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("artist", artist);
        json.put("rating", rating);
        json.put("genre", genre);
        return json;
    }

}






