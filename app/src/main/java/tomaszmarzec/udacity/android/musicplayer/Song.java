package tomaszmarzec.udacity.android.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

 class Song implements  Parcelable
{
    private String mTitle, mArtist, mGenre, mAlbum, mMusicArtFileName;

    public Song(String songTitle, String artist, String genre, String album, String musicArtFileName)
    {
        mTitle = songTitle;
        mArtist = artist;
        mGenre = genre;
        mAlbum = album;
        mMusicArtFileName = musicArtFileName;
    }


    //This constructor is used in Album class for the purpose of creating Song objects in loop, I did'n know how to solve it in other way
    public Song()
    {

    }

    public String getSongTitle() {
        return mTitle;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getGenre() {
        return mGenre;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public String getMusicArtFileName() {
        return mMusicArtFileName;
    }

      /*  To pass ArrayList of custom class objects to new activity by storing it in Intent object, the class needs to implement parcelable.

      ###Solutions from:

       https://stackoverflow.com/questions/22446359/android-class-parcelable-with-arraylist
       by Miro Markaravanes

       ###and:
       https://stackoverflow.com/questions/7181526/how-can-i-make-my-custom-objects-parcelable
       by fiction

       Code from second StackOverflow question from:
       How to do that in Android  Techdroid. (2010) - http://techdroid.kbeanie.com/2010/06/parcelable-how-to-do-that-in-android.html

     */

    public Song(Parcel in){
        String[] data = new String[5];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        mTitle = data[0];
        mArtist = data[1];
        mGenre = data[2];
        mAlbum = data[3];
        mMusicArtFileName = data[4];

    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringArray(new String[]
                {
                        this.getSongTitle(),
                        this.getArtist(),
                        this.getGenre(),
                        this.getAlbum(),
                        this.getMusicArtFileName()
                });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        @Override
        public Song createFromParcel(Parcel in)
        {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    /* Solution how to sort ArrayList by object properties, using Comparator, from:

https://beginnersbook.com - "Java ArrayList of Object Sort Example (Comparable And Comparator)" -by Chaitanya Singh
    https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/ */

    public static Comparator<Song> songTitleComparator = new Comparator<Song>()
    {
        public int compare(Song s1, Song s2)
        {
            String song1 = s1.getSongTitle().toUpperCase();
            String song2 = s2.getSongTitle().toUpperCase();

            //ascending order
            return song1.compareTo(song2);
        }
    }; // Sorts Song objects alphabetically by title

    public static Comparator<Song> songGenreComparator = new Comparator<Song>()
    {
        public int compare(Song s1, Song s2)
        {
            String song1 = s1.getGenre().toUpperCase();
            String song2 = s2.getGenre().toUpperCase();

            //ascending order
            return song1.compareTo(song2);
        }
    }; // Sorts Song objects alphabetically by genre
}
