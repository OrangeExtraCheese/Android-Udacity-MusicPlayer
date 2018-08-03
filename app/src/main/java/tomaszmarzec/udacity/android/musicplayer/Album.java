package tomaszmarzec.udacity.android.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;
import java.util.Comparator;


 class Album implements Parcelable
{
    private String mAlbumTitle, mAlbumGenre, mReleaseYear, mArtist, mMusicArtFileName;
    private ArrayList<Song> albumSongs = new ArrayList<>();


    //As Song objects inherits all values (except title) of it's member variables from Album object, then this method by iterating through array of titles, creates for each one new Song object.
   public void insertSongs(String[] songTitles)
   {
       /* If I write this in following way:

       Song songToAdd;
       for(String title:songTitles)
           songToAdd = new Song(title, mArtist, mAlbumGenre, mAlbumTitle);
           albumSongs.add(songToAdd);
           Database.addToAllSongsList(songToAdd);

       Then I get an error that songToAdd might not have been initialized, so to avoid it i created additional constructor in song class,
       taking no parameters.
       */

       Song songToAdd = new Song();
       for(String title:songTitles)
       {
           songToAdd = new Song(title, mArtist, mAlbumGenre, mAlbumTitle, mMusicArtFileName);
           albumSongs.add(songToAdd);
       }

   }

    public String getAlbumTitle() {
        return mAlbumTitle;
    }

    public String getAlbumGenre() {
        return mAlbumGenre;
    }

    public String getReleaseYear() {
        return mReleaseYear;
    }

    public ArrayList<Song> getAlbumSongs() {
        return albumSongs;
    }

    public String getMusicArtFilename()
    {
        return mMusicArtFileName;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }


    protected Album(String albumTitle, String releaseYear, String albumGenre, String artist)
    {
        mAlbumTitle = albumTitle;
        mReleaseYear = releaseYear;
        mAlbumGenre = albumGenre;
        mArtist = artist;

        mMusicArtFileName = (mArtist+mAlbumTitle).toLowerCase().replaceAll("\\s","");
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

    private Album(Parcel parcel)
    {
        // the order needs to be the same as parcel writeToParcel() method
        mAlbumTitle = parcel.readString();
        mArtist = parcel.readString();
        mAlbumGenre = parcel.readString();
        mReleaseYear = parcel.readString();
        mMusicArtFileName = parcel.readString();
        albumSongs = parcel.readArrayList(Song.class.getClassLoader());
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
                        dest.writeString(mAlbumTitle);
                        dest.writeString(mArtist);
                        dest.writeString(mAlbumGenre);
                        dest.writeString(mReleaseYear);
                        dest.writeString(mMusicArtFileName);
                        dest.writeList(albumSongs);
    }
    protected static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        @Override
        public Album createFromParcel(Parcel source)
        {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    /* Solution how to sort ArrayList by object properties, using Comparator, from:

    https://beginnersbook.com - "Java ArrayList of Object Sort Example (Comparable And Comparator)" -by Chaitanya Singh
        https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/ */

    protected static Comparator<Album> albumReleaseYearComparator = new Comparator<Album>()
    {
        public int compare(Album s1, Album s2)
        {
            String releaseYear1 = s1.getReleaseYear().toUpperCase();
            String releaseYear2 = s2.getReleaseYear().toUpperCase();

            //ascending order
            return releaseYear2.compareTo(releaseYear1);
        }
    };





}



