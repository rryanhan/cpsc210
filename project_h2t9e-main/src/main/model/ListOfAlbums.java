package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// functionality of a list of albums
public class ListOfAlbums implements Writable {
    private String yourListName;
    private List<Album> listOfAlbums;


    //effects: creates a list of albums with a name for the list
    public ListOfAlbums(String yourListName) {
        this.yourListName = yourListName;
        this.listOfAlbums = new ArrayList<>();
    }

    // modifies: this
    // effects: adds an album to a list if it isn't already in the list and produces true,
    // otherwise return false
    public Boolean addAlbum(Album album) {
        if (!listOfAlbums.contains(album)) {
            listOfAlbums.add(album);
            EventLog.getInstance().logEvent(new Event(album.getName() + " by "
                    + album.getArtist() + " added to album list"));
            return true;
        } else {
            return false;
        }
    }

    public void removeAlbumEventLog(Album album) {
        EventLog.getInstance().logEvent(new Event(album.getName() + " by "
                + album.getArtist() + " removed from album list"));
    }

    public void alreadyRemovedAlbumEventLog(String album) {
        EventLog.getInstance().logEvent(new Event(album + " was not found in album list"));
    }

    // effects: returns the name of the list of albums
    public String getListName() {
        return this.yourListName;
    }

    // effects: searches for album by index and returns it
    public Album searchForAlbum(int number) {
        return listOfAlbums.get(number);
    }

    // effects: returns true if list of albums is empty, false otherwise
    public Boolean albumListEmpty() {
        return listOfAlbums.isEmpty();
    }

    // effects: returns the list of albums
    public List<Album> returnAlbumList() {
        return listOfAlbums;
    }

    // effects: returns the size of the list of albums
    public Integer getLength() {
        return listOfAlbums.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", yourListName);
        json.put("albums", albumsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray albumsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Album t : listOfAlbums) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}

