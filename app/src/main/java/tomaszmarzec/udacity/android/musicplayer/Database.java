package tomaszmarzec.udacity.android.musicplayer;

import java.util.ArrayList;
import java.util.Collections;


/* The biggest disadvantage of this app is, I think, large amounts of memory used to store all the Artist, Album and Song objects if user has lots of music.
I don't know which approach would be best to fix this */
 class Database
{
   private static ArrayList<Artist> sArtistList = new ArrayList<>();

    public static ArrayList<Artist> getArtistList()
    {
        return sArtistList;
    }

    /* Method taking album data as info, and then iterating through artists array to check if artist of new album is already in the array.
       If yes, method addAlbum of Album class is called to add album to artist. If no, new artist is added array of artists, and then album is added to it.
         */
    protected static void addNewAlbum(String artistName, String albumTitle, String releaseYear, String albumGenre, String[] songTitles)
    {
        for(Artist artist:sArtistList)
        {
            if(artist.getName().equals(artistName))
            {
                artist.addAlbum(albumTitle, releaseYear, albumGenre, songTitles);
                return;
            }
        }
        //Artist added this way needs to be updated later - member variable country takes default value of "data update required" and genre member variable may be inaccurate because it takes value from album.
        Artist newArtist = new Artist(artistName, albumGenre);
        newArtist.addAlbum(albumTitle, releaseYear, albumGenre, songTitles);
        sArtistList.add(newArtist);
    }

    //This method overload gives method which does not take genre for new album, Artist class handles this by assigning genre of artist to album.
    private static void addNewAlbum(String artistName, String albumTitle, String releaseYear,  String[] songTitles)
    {
        for(Artist artist:sArtistList)
        {
            if(artist.getName().equals(artistName))
            {
                artist.addAlbum(albumTitle, releaseYear, songTitles);
                return;
            }
        }
        //If new album has no genre, and it's artist is not present in artists array, then it takes "unknown" for genre.
        Artist newArtist = new Artist(artistName, "unknwon");
        newArtist.addAlbum(albumTitle, releaseYear, songTitles);
        sArtistList.add(newArtist);
    }


    //This method can be used to add song to artist while omitting album. If user buys single song, or song has been released as a single, then this songs belongs to artist, but not a particular album.
    protected static void addStandAloneSong(String artistName, String songTitle, String musicArtFileName)
    {
        for(Artist artist:sArtistList)
        {
            if(artist.getName().equals(artistName))
                artist.addSingleSong(songTitle, musicArtFileName);
        }

    }

    // In real application, I think, this method would probably be replaced with method querying database system, it is a provisional method to fill app with example artists.
    protected static void addArtists()
    {
        sArtistList.add(new Artist("Judas Priest", "UK", "heavy metal/rock"));
        sArtistList.add(new Artist("Florence And The Machine", "UK", "indie pop"));
        sArtistList.add(new Artist("Diana Ross", "USA", "R&B/soul"));
    }


    /*Like a previous one, this is a provisional method which adds new albums (along with songs for this albums), to artists. It is called from
      MainActivity when app starts. In real production app, this method probably would replaced with method connecting to database system.
     */
    protected static void addMusic()
    {
        String[] songsScreamingForVengeance = new String[]{"The Hellion","Electric Eye","Riding on the Wind","Bloodstone",	"(Take These) Chains",	"Pain and Pleasure", "Screaming for Vengeance", "You've Got Another Thing Comin'", "Fever", "Devil's Child"};
        addNewAlbum("Judas Priest", "Screaming For Vengeance", "1982",  songsScreamingForVengeance);
        String[] songsPainkiller = new String[]{"Painkiller","Hell Patrol","All Guns Blazing","Leather Rebel","Metal Meltdown","Night Crawler","A Touch of Evil","Battle Hymn","One Shot at Glory"};
        addNewAlbum("Judas Priest", "Painkiller", "1990", "heavy/speed metal", songsPainkiller);
        String[] songsHighAsHope = new String[]{"June", "Hunger", "South London Forever", "Big God", "Sky Full of Song", "Grace", "Patricia", "The End of Love", "No Choir"};
        addNewAlbum("Florence And The Machine", "High As Hope", "2018", "indie pop", songsHighAsHope );
        String[] songsCeremonials = new String[]{"Only If for a Night", "Shake It Out", "What the Water Gave Me", "Never Let Me Go", "Breaking Down", "Lover to Lover", "Seven Devils", "Heartlines", "Spectrum", "All This and Heaven Too", "Leave My Body"};
        addNewAlbum("Florence And The Machine", "Ceremonials", "2011", "indie pop", songsCeremonials);
        String[] songsDianaRoss = new String[]{"Reach Out and Touch (Somebody's Hand)" , "Now That There's You","You're All I Need to Get By","These Things Will Keep Me Loving You","Ain't No Mountain High Enough","Something on My Mind","I Wouldn't Change the Man He Is","Keep an Eye","Where There Was Darkness","Can't It Wait Until Tomorrow" , "Dark Side of the World"};
        addNewAlbum("Diana Ross", "Diana Ross", "1980", "R&B/soul", songsDianaRoss );
        String[] songsSurrender = new String[]{ "Surrender" , "I Can't Give Back the Love I Feel for You","Remember Me","And If You See Him","Reach Out (I'll Be There)","Didn't You Know?","A Simple Thing Like Cry","Did You Read the Morning Paper?","I'll Settle for You","I'm a Winner","All the Befores" };
        addNewAlbum("Diana Ross", "Surrender", "1971", "R&B/soul", songsSurrender);
    }


    //Method iterating through artists array, and for every artist, getting array of it's albums, and adding received albums to allAlbums array,
    protected static ArrayList<Album> getAllAlbumsList()
    {
        ArrayList<Album> allAlbums = new ArrayList<>();
        for(Artist artist:sArtistList)
        {
            allAlbums.addAll(artist.getAlbumsList());
        }

        return allAlbums;
    }

    protected static ArrayList<Song> getAllSongsList()
    {
        ArrayList<Album> allAlbums = getAllAlbumsList();
        ArrayList<Song> allSongsList = new ArrayList<>();
        for(Album album:allAlbums)
        {
            allSongsList.addAll(album.getAlbumSongs());
        }

        return allSongsList;
    }

    /*For this two methods - getAllAlbumsList and getAllSongsList, I considered creating two public static ArrayLists - one containing all Song objects from all artists and second all Album
      objects from all artists. Instead of populating in loop new allAlbums and allSongs ArrayLists each time user enter browse songs or browse albums in main menu, there would be two ArrayLists
      keeping track of all Albums and Songs added in app. I see two advantages: resources for populating ArrayLists would be used only when new Album or Song is added, and not every time user wants to browse
      all Albums/Songs. Second advantage: ArrayList of all Album objects in app could be sorted by album release year only when new album is added, and not every time user displays albums (in BrowseAlbumsActivity).
      On the other hand, this two ArrayLists would lower encapsulation level of data in app. I'm not sure which solution is better.
     */

}
