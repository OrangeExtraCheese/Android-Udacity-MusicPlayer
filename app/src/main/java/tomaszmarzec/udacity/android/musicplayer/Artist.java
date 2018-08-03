package tomaszmarzec.udacity.android.musicplayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

 class Artist
{
    private String mName, mCountry, mGenre, mMusicArtFileName;

    private ArrayList<Album> mAlbumsList = new ArrayList<>();
    private ArrayList<Song> mStandaloneSongsList = new ArrayList<>();


    public Artist(String name, String country, String genre)
    {
        mName = name;
        mCountry = country;
        mGenre = genre;

        mMusicArtFileName = (mName).toLowerCase().replaceAll("\\s","");
    }

    //This constructor, taking less parameters, is used for automatically adding new artist alongside adding new album, in addNewAlbum method of Database class.
    public Artist(String name,  String genre)
    {
        mName = name;
        mCountry = "data update required";
        mGenre = genre;

        mMusicArtFileName = (mName).toLowerCase().replaceAll("\\s","");
    }

    //Some values of Album member variables are inherited from Artist
    public void addAlbum(String albumTitle, String releaseYear, String albumGenre, String[] songTitles)
    {
        Album albumToAdd = new Album(albumTitle, releaseYear, albumGenre, mName);
        albumToAdd.setArtist(mName);
        mAlbumsList.add(albumToAdd);
        albumToAdd.insertSongs(songTitles);
    }

    //If new album is added without specified genre, then this method gives it a genre of artist
    public void addAlbum(String albumTitle, String releaseYear, String[] songTitles)
    {
        Album albumToAdd = new Album(albumTitle, releaseYear,mGenre, mName);
        albumToAdd.setArtist(mName);
        mAlbumsList.add(albumToAdd);
        albumToAdd.insertSongs(songTitles);
    }

    public void addSingleSong(String songTitle, String musicArtFileName)
    {
        Song songToAdd = new Song(songTitle, mName, mGenre, "single song", musicArtFileName);
        mStandaloneSongsList.add(songToAdd);
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getName() {
        return mName;
    }

    public String getGenre() {
        return mGenre;
    }

    public String getCountry() {
        return mCountry;
    }

    public ArrayList<Album> getAlbumsList() {
        return mAlbumsList;
    }

    public String getMusicArtFilename()
    {
        return mMusicArtFileName;
    }


    public ArrayList<Song> getArtistSongs()
    {
        ArrayList<Song> allArtistSongs = new ArrayList<>();
        allArtistSongs.addAll(mStandaloneSongsList);

        for(Album album:mAlbumsList)
        {
            allArtistSongs.addAll(album.getAlbumSongs());
        }

        return allArtistSongs;
    }
}
