# MyMusic

## A personalized album tracker

#### Application Purpose

This application called *My Music* will allow users to organize their
music by genre and rank their music as well. It will contain all of the user's
albums in an organized way such that they can access albums by artist, genre, or rating.
Users have the option of associating an artist to an album, rank their albums through a 5-point rating system,
and add a genre to the album.
This way, when a user feels like listening to a certain type of music
(genre, rating, or by artist), they can simply filter by
their tags and select an album.
This app will allow the user to see the number of albums they have listened to and also see the average rating among uiuu
the albums.

#### Who should use the application? Why is it of interest to me?

<p> This app would be used by avid music listeners such as myself. When one has so many 
types of genres of music they listen to, it is easy to lose track of albums
one has listened to. This app would allow users to keep track of all of their music
in an organized way so that they can select an album from any genre, rating,
or artist.  This is why this project is of such great interest to me - it is a project
I think would be of great benefit to not just me but also to music listeners alike.</p>

**User Stories**:

- As a user, I want to create new albums with an artist name, a rating on a scale of 0-5 (in intervals of 0.5),
  a genre to the album, and be able to add it to a list of albums.
- As a user, I want to be able to filter my list of albums by artist, the 5-point rating system, or genre
  (the user can choose to filter with just one of these options, or by all three - for example, sorting their albums
  by star ranking "4 or higher", artist name "Playboi Carti," and by a tag "Rap")
- As a user, I want to be able to remove albums from my list
- As a user, I want the application to show the number of albums inputted and display all of my albums with their
  corresponding information
- As a user, I want to see the average rating of the list of albums.
  As a user, I want to have the choice of saving my album list to file
  As a user, I want to have the choice of loading my album list from file 
 
**Instructions for Grader**
- Generate first required action by clicking 'view all your albums' button
  - If no albums inputted, pop-up window occurs and asks if you want to add an album
- Generate second required action by clicking "add an album" button
- The visual component is on the front page, and also shows up when a pop-up window occurs (add an album to empty list
  of albums or add another album)
- Save the file by clicking the "save your albums" button
- Load the file by clicking the "load your albums" button

**Phase 4: Task 2**
```
Tue Apr 11 21:05:29 PDT 2023
Vinyl Days by Logic added to album list
Tue Apr 11 21:05:38 PDT 2023
Freewave 2 by LUCKI added to album list
Tue Apr 11 21:06:18 PDT 2023
Last Ones Left by 42 Dugg added to album list
Tue Apr 11 21:06:42 PDT 2023
The Family by BROCKHAMPTON added to album list
Tue Apr 11 21:06:47 PDT 2023
Vinyl Days by Logic removed from album list
Tue Apr 11 21:06:57 PDT 2023
Luv 4 Rent was not found in album list
Tue Apr 11 21:07:27 PDT 2023
Darklife by death's dynamic shroud added to album list
```
**Phase 4: Task 3**
I believe that my MainGUI class is very disorganized and hard to follow, because it contains every single method 
for the GUI of the application. Thus, to find a certain method, it takes time to find. I would refactor it so each 
component has their own class. For example, having the functionality of adding an album as its own class. The classes
would have a bidirectional associative relationship with the MainGUI class. This would make the code much more 
organized. Additionally, when further functionality is added, it makes it much easier to locate certain parts of the 
code. It was manageable this time around, but when working with larger code, having all the methods under one class
will result in a mess. 
