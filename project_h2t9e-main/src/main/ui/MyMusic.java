package ui;

import model.ListOfAlbums;
import model.Album;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// functionality for non-GUI version of MyMusic
// citation: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
public class MyMusic {
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/workroom.json";
    private ListOfAlbums albumList;
    private ListOfAlbums filteredAlbums;
    private Album a1;
    private Album a2;

    // runs MyMusic application
    public MyMusic() {
        input = new Scanner(System.in);
        albumList = new ListOfAlbums("Ryan's Albums");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runMyMusic();
    }

    // modifies: this
    // effects: uses user input to run application, if "q", application stops running
    private void runMyMusic() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
                System.out.println("The application has closed");

            } else {
                processCommand(command);
            }
        }

    }

    // modifies: this
    // effects: initializes list of albums
    private void init() {
        albumList = new ListOfAlbums("Ryan's Albums");
        a1 = new Album("Vinyl Days", "Logic", 5.0, "Rap");
        a2 = new Album("Whole Lotta Red", "Playboi Carti", 4.5, "Rap");
        albumList.addAlbum(a1);
        albumList.addAlbum(a2);

        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // modifies: this
    // effects: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addAlbum();
        } else if (command.equals("r")) {
            removeAlbum(albumList.returnAlbumList());
        } else if (command.equals("f")) {
            filterAlbum(albumList.returnAlbumList());
        } else if (command.equals("v")) {
            viewAllAlbums(albumList.returnAlbumList());
        } else if (command.equals("t")) {
            showTotalNumberOfAlbums(albumList.returnAlbumList());
        } else if (command.equals("z")) {
            showAverageRating(albumList.returnAlbumList());
        } else if (command.equals("s")) {
            saveAlbumList();
        } else if (command.equals("l")) {
            loadAlbumList();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // effects: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add an album");
        System.out.println("\tr -> remove an album");
        System.out.println("\tf -> filter your albums by artist, rating, or tags");
        System.out.println("\tv -> view all your albums");
        System.out.println("\tl -> view your total number albums");
        System.out.println("\tz -> view the average rating of your albums");
        System.out.println("\ts -> save album list to file");
        System.out.println("\tl -> load album list from file");
        System.out.println("\tq -> quit");
    }

    // requires: the rating to be in intervals of 0.5 (0.0, 1.5, 2.0, 2.5...)
    // modifies: this
    // effects: adds an album to a list of albums, and sends confirmation message to user
    private void addAlbum() {
        System.out.println("Enter Album Name:");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Enter Artist:");
        String artist = input.nextLine();
        System.out.println("Enter Rating:");
        Double rating = input.nextDouble();
        System.out.println("Enter Genre:");
        input.nextLine();
        String genre = input.nextLine();
        Album album = new Album(name, artist, rating, genre);
        albumList.addAlbum(album);

        System.out.println(album.getName() + " has been added to your list");
    }

    // modifies: this
    // effects: removes an album from the list of albums.
    private void removeAlbum(List<Album> listofalbums) {
        System.out.println("Enter Album to remove");
        String albumname = input.next();

        for (Album m : listofalbums) {
            if (albumname.equals(m.getName())) {
                listofalbums.remove(m);
                System.out.println(albumname + " removed from your list of albums");
                break;
            }
        }
    }


    // modifies: this
    // effects: filters the user's list of albums by artist, rating, or genre
    // (the user can also filter by two of these variables or by all three), and returns the list.
    private void filterAlbum(List<Album> listofalbums) {
        System.out.println("Enter artist name (or click enter to skip filter by artist): ");
        String artist = input.next();
        System.out.println("Enter rating (or enter -0.0 to skip filter by rating): ");
        Double rating = input.nextDouble();
        System.out.println("Enter genre (or click enter to skip filter by genre): ");
        String genre = input.next();
        filterAlbumHelper(artist, rating, genre, albumList.returnAlbumList());
    }

    // modifies: listofalbums
    // effects: filters out albums that match the user's inputs
    private void filterAlbumHelper(String artist, Double rating, String genre, List<Album> listofalbums) {
        filteredAlbums = new ListOfAlbums("Hi");
        if (!artist.equals("")) {
            for (Album a : listofalbums) {
                if (artist.equals(a.getArtist())) {
                    filteredAlbums.addAlbum(a);
                }
            }
        } else if (!genre.equals("")) {
            for (Album a : listofalbums) {
                if (genre.equals(a.getGenre())) {
                    filteredAlbums.addAlbum(a);
                }
            }
        } else if (!rating.equals(-0.0)) {
            for (Album a : listofalbums) {
                if (rating.equals(a.getRating())) {
                    filteredAlbums.addAlbum(a);
                }
            }
        }
        filteredAlbumEmpty(filteredAlbums.returnAlbumList());
    }

    // effects: returns "no albums were found" if no albums were selected from filter, otherwise return
    // the filtered list of albums
    private void filteredAlbumEmpty(List<Album> filteredalbums) {
        if (filteredAlbums.albumListEmpty()) {
            System.out.println("No albums were found");
        } else {
            viewAllAlbums(filteredAlbums.returnAlbumList());
        }
    }

    // effects: displays all the albums in the user's list and their corresponding information
    private void viewAllAlbums(List<Album> listofalbums) {
        for (Album a : listofalbums) {
            System.out.println(a.getName() + ". Artist: " + a.getArtist()
                    + ", Rating: " + a.getRating() + ", Genre: " + a.getGenre());
        }
    }

    // effects: displays the total number of albums in the user's list
    private void showTotalNumberOfAlbums(List<Album> listofalbums) {
        int size = listofalbums.size();
        System.out.println("Your list has " + size + " albums");
    }

    // effects: displays the average rating among all albums in the user's list
    private void showAverageRating(List<Album> listofalbums) {
        double rating = 0.0;
        int size = listofalbums.size();
        for (Album a : listofalbums) {
            rating = rating + a.getRating();
        }
        double averagerating = rating / size;
        System.out.println("The average rating of your albums is " + averagerating);
    }

    // EFFECTS: saves the album list to file
    private void saveAlbumList() {
        try {
            jsonWriter.open();
            jsonWriter.write(albumList);
            jsonWriter.close();
            System.out.println("Saved " + albumList.getListName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads album list from file
    private void loadAlbumList() {
        try {
            albumList = jsonReader.read();
            System.out.println("Loaded " + albumList.getListName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}




