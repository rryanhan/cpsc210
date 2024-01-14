package ui;

import model.Album;
import model.ListOfAlbums;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

// functionality of the GUI for MyMusic
public class GUI extends JFrame implements ActionListener {
    private JPanel mainScreen;
    private JPanel viewAlbumScreen;
    private JPanel addAlbumScreen;
    private JPanel removeAlbumScreen;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton addAlbum1;
    private JButton removeAlbum1;
    private ImageIcon imageIcon;
    private ListOfAlbums albumList;
    private Album album;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/GUIworkroom.json";

    private JLabel name;
    private JLabel artist;
    private JLabel rating;
    private JLabel genre;
    private JFrame noAlbumPopUp;
    private JFrame addedAlbumPopUp;
    private JTextField nameName;
    private JTextField nameArtist;
    private JTextField nameRating;
    private JTextField nameGenre;

    private JLabel removeName;
    private JTextField removeNameText;

    // EFFECTS: Instantiates home panel for MyMusic GUI
    public GUI() {
        super("MyMusic");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        albumList = new ListOfAlbums("Album list");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 475));

        mainScreen = new JPanel();
        mainScreen.setBackground(Color.white);
        add(mainScreen);
        addWindowListener(windowClosing);

        JLabel welcomeLabel = new JLabel("Your Album List", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 60));
        mainScreen.add(welcomeLabel);
        System.getProperty("line.separator");
        nameMenuButtons();
        makeNewButton(button1, mainScreen);
        makeNewButton(button2, mainScreen);
        makeNewButton(button3, mainScreen);


        addMenuImage();
        makeNewButton(button6, mainScreen);
        makeNewButton(button7, mainScreen);

        setActionCommand();

        mainScreen.setVisible(true);
    }

    // EFFECTS: Prints Events onto console
    private WindowAdapter windowClosing = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent we) {

            // if autosave didn't fail, or if user selects yes both times, app closes
            for (model.Event e : model.EventLog.getInstance()) {
                System.out.println(e);
            }
            System.exit(0);
        }
    };

    // EFFECTS: create icon for main screen
    public void addMenuImage() {
        JLabel mainScreenImage = new JLabel();
        mainScreenImage.setIcon(new ImageIcon("./data/Screen Shot 2023-03-26 at 5.41.37 PM.png"));
        mainScreen.add(mainScreenImage);
    }

    // EFFECTS: create labels for main screen buttonss
    public void nameMenuButtons() {
        button1 = new JButton("View all your albums");
        button2 = new JButton("Add an album");
        button3 = new JButton("Remove an album");
        button4 = new JButton("Filter your albums by artist, rating, or tags");
        button5 = new JButton("View your album statistics");
        button6 = new JButton("Save your albums");
        button7 = new JButton("Load your albums");
        button8 = new JButton("Exit application");
    }

    // MODIFIES: this
    // EFFECTS: create a new button and add it to given panel
    public void makeNewButton(JButton b, JPanel p) {
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setBackground(Color.white);
        pack();
        p.add(b);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // EFFECTS: set action command to buttons
    private void setActionCommand() {
        button1.addActionListener(this);
        button1.setActionCommand("view");
        button2.addActionListener(this);
        button2.setActionCommand("add");
        button3.addActionListener(this);
        button3.setActionCommand("remove");
//      button4.setActionCommand();
//      button5.setActionCommand();
        button6.addActionListener(this);
        button6.setActionCommand("Save");
        button7.addActionListener(this);
        button7.setActionCommand("Load");
        button8.addActionListener(this);
        button8.setActionCommand("Exit");

    }


    // MODIFIES: this
    // EFFECTS: calls given methods when buttons are clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("view")) {
            viewAlbums();
        } else if (e.getActionCommand().equals("add")) {
            addAlbumGUI();
        } else if (e.getActionCommand().equals("Add to list")) {
            addAlbumToList();

            //    } else if (e.getActionCommand().equals("remove")) {

        } else if (e.getActionCommand().equals("Menu")) {
            returnToMainMenu();
        } else if (e.getActionCommand().equals("Save")) {
            saveAlbumList();
        } else if (e.getActionCommand().equals("Load")) {
            loadAlbumList();
        } else if (e.getActionCommand().equals("remove")) {
            removeFromAlbumListGUI();
        } else if (e.getActionCommand().equals("Remove from list")) {
            removeAlbumFromList();
        }
    }

    // EFFECTS: creates new window to view all albums, if empty, asks to add an album
    public void viewAlbums() {
        if (checkIfEmpty()) {
            noAlbums();
        } else {
            viewAlbumScreen = new JPanel();
            viewAlbumScreen.removeAll();
            viewAlbumScreen.updateUI();
            viewAlbumScreen.setVisible(true);
            mainScreen.setVisible(false);
            add(viewAlbumScreen);

            JButton toMainScreen = new JButton("Back to main menu");
            toMainScreen.setActionCommand("Menu");
            toMainScreen.addActionListener(this);
            makeNewButton(toMainScreen, viewAlbumScreen);

            addAlbumsToMainScreen(viewAlbumScreen);
        }
    }

    // EFFECTS: checks if album list is empty
    public boolean checkIfEmpty() {
        return (albumList.albumListEmpty());
    }

    // EFFECTS: creates a pop-up asking to add an album
    public void noAlbums() {
        if (albumList.albumListEmpty()) {
            noAlbumPopUp = new JFrame();
            imageIcon = new ImageIcon("./data/Screen Shot 2023-03-28 at 3.16.03 PM.png");
            String[] options = {"No", "Yes"};
            int result = JOptionPane.showOptionDialog(noAlbumPopUp, "No albums added yet! Would you like to add one?",
                    "No album!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    imageIcon, options, options[1]);
            if (result == JOptionPane.NO_OPTION) {
                addAlbumGUI();
            } else if (result == JOptionPane.YES_OPTION) {
                returnToMainMenu();
            } else {
                returnToMainMenu();
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: adds album as JLabel to view album screen
    public void addAlbumsToMainScreen(JPanel viewAlbumScreen) {

        for (Album a : albumList.returnAlbumList()) {
            JLabel album = new JLabel("<html>" + "<B>" + a.getName() + " by " + a.getArtist() + ". " + "</B>"
                    + "<br><br>"
                    + "Rating: " + a.getRating() + ". "
                    + "<br><br>"
                    + "Genre: " + a.getGenre() + "." + "</html>");
            album.setFont(new Font("Ariel", Font.BOLD, 10));
            viewAlbumScreen.add(album);
            pack();
            setLocationRelativeTo(null);
        }
    }

    // EFFECTS: creates window for removing album from a list
    public void removeFromAlbumListGUI() {
        removeAlbumScreen = new JPanel();
        removeAlbumScreen.removeAll();
        removeAlbumScreen.updateUI();

        removeAlbumScreen.setVisible(true);
        if (viewAlbumScreen != null) {
            viewAlbumScreen.setVisible(false);
        }
        mainScreen.setVisible(false);
        add(removeAlbumScreen);

        JButton toMainScreen = new JButton("Back to main menu");
        toMainScreen.setActionCommand("Menu");
        toMainScreen.addActionListener(this);
        makeNewButton(toMainScreen, removeAlbumScreen);

        addRemoveText();
    }

    //EFFECTS: adds label and text to removeAlbum1 window
    public void addRemoveText() {
        removeAlbum1 = new JButton("Remove album");
        removeAlbum1.setActionCommand("Remove from list");
        removeAlbum1.addActionListener(this);

        removeName = new JLabel("Album name: ");
        removeName.setFont(new Font("Ariel", Font.BOLD, 20));
        removeNameText = new JTextField(10);
        removeAlbumScreen.add(removeName);
        removeAlbumScreen.add(removeNameText);
        removeAlbumScreen.add(removeNameText);
        removeAlbumScreen.add(removeAlbum1);
    }


    // EFFECTS: creates window for add album
    public void addAlbumGUI() {
        addAlbumScreen = new JPanel();
        addAlbumScreen.removeAll();
        addAlbumScreen.updateUI();

        addAlbumScreen.setVisible(true);
        if (viewAlbumScreen != null) {
            viewAlbumScreen.setVisible(false);
        }
        mainScreen.setVisible(false);
        add(addAlbumScreen);

        JButton toMainScreen = new JButton("Back to main menu");
        toMainScreen.setActionCommand("Menu");
        toMainScreen.addActionListener(this);
        makeNewButton(toMainScreen, addAlbumScreen);

        addTextToAlbum();
    }

    // EFFECTS: creates labels and text fields for all album
    public void addTextToAlbum() {
        addAlbum1 = new JButton("Add album");
        addAlbum1.setActionCommand("Add to list");
        addAlbum1.addActionListener(this);

        name = new JLabel("Album name: ");
        name.setFont(new Font("Ariel", Font.BOLD, 20));
        nameName = new JTextField(10);
        artist = new JLabel("Artist:");
        artist.setFont(new Font("Ariel", Font.BOLD, 20));
        nameArtist = new JTextField(10);
        rating = new JLabel("Rating (0-5 on 0.5 scale):");
        rating.setFont(new Font("Ariel", Font.BOLD, 20));
        nameRating = new JTextField(10);
        genre = new JLabel("Genre:");
        genre.setFont(new Font("Ariel", Font.BOLD, 20));
        nameGenre = new JTextField(10);

        labelsToAddTextToAlbum();
    }

    // EFFECTS: adds jUnits to add album window
    public void labelsToAddTextToAlbum() {
        addAlbumScreen.add(name);
        addAlbumScreen.add(nameName);
        addAlbumScreen.add(artist);
        addAlbumScreen.add(nameArtist);
        addAlbumScreen.add(rating);
        addAlbumScreen.add(nameRating);
        addAlbumScreen.add(genre);
        addAlbumScreen.add(nameGenre);

        addAlbumScreen.add(addAlbum1);
    }

    // EFFECTS: adds the album to list of albums, asks to add another album with a pop up window
    public void addAlbumToList() {
        album = new Album(nameName.getText(), nameArtist.getText(),
                parseDouble(nameRating.getText()), nameGenre.getText());
        albumList.addAlbum(album);
        addedAlbumPopUp = new JFrame();
        imageIcon = new ImageIcon("./data/Screen Shot 2023-03-28 at 4.06.38 PM.png");
        String[] options = {"No", "Yes"};
        int result = JOptionPane.showOptionDialog(addedAlbumPopUp, "Album added! Add another one?",
                "Album added!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                imageIcon, options, options[1]);
        if (result == JOptionPane.NO_OPTION) {
            nameName.setText("");
            nameArtist.setText("");
            nameGenre.setText("");
            nameRating.setText("");
        } else if (result == JOptionPane.YES_OPTION) {
            returnToMainMenu();
        } else {
            returnToMainMenu();
        }
    }

    // EFFECTS: removes given album from list. if it does not exist, will send a popup
    public void removeAlbumFromList() {
        List<String> albumNames = new ArrayList<>();
        for (Album m : albumList.returnAlbumList()) {
            String name = m.getName();
            albumNames.add(name);
        }
        for (Album m : albumList.returnAlbumList()) {
            if (removeNameText.getText().equals(m.getName())) {
                albumList.returnAlbumList().remove(m);
                albumList.removeAlbumEventLog(m);
                break;
            }
        }
        if (!(albumNames.contains(removeNameText.getText()))) {
            noAlbumToRemove();
        } else {
            albumRemovedPopUp();
        }
    }

    // EFFECTS: creates  a popup window asking user to remove another album. if no, returns to main screen
    public void albumRemovedPopUp() {
        addedAlbumPopUp = new JFrame();
        imageIcon = new ImageIcon("./data/Screen Shot 2023-03-28 at 4.06.38 PM.png");
        String[] options = {"No", "Yes"};
        int result = JOptionPane.showOptionDialog(addedAlbumPopUp, "Album removed! Remove another one?",
                "Album removed!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                imageIcon, options, options[1]);
        if (result == JOptionPane.NO_OPTION) {
            removeNameText.setText("");
        } else if (result == JOptionPane.YES_OPTION) {
            returnToMainMenu();
        } else {
            returnToMainMenu();
        }
    }

    public void noAlbumToRemove() {
        JOptionPane.showMessageDialog(removeAlbumScreen, "No such album in list!");
        albumList.alreadyRemovedAlbumEventLog(removeNameText.getText());
        removeNameText.setText("");
    }

    // EFFECTS: return to main menu
    public void returnToMainMenu() {
        mainScreen.setVisible(true);
        if (removeAlbumScreen != null) {
            removeAlbumScreen.setVisible(false);
        }
        if (viewAlbumScreen != null) {
            viewAlbumScreen.setVisible(false);
        }
        if (addAlbumScreen != null) {
            addAlbumScreen.setVisible(false);
        }
    }

    // EFFECTS: saves album list to filw
    public void saveAlbumList() {
        try {
            jsonWriter.open();
            jsonWriter.write(albumList);
            jsonWriter.close();
            JOptionPane.showMessageDialog(mainScreen, "Albums saved!");

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(mainScreen, "No albums added");
        }
    }

    // EFFECTS: loads album list from file
    public void loadAlbumList() {
        try {
            albumList = jsonReader.read();
            JOptionPane.showMessageDialog(mainScreen, "Albums loaded!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainScreen, "No albums saved");
        }
    }
}
