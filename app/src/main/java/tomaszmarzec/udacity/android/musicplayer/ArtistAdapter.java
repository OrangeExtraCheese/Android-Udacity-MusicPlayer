package tomaszmarzec.udacity.android.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

 class ArtistAdapter extends ArrayAdapter<Artist>
{

    private Context mActivityContext;

    public ArtistAdapter(Activity context, ArrayList<Artist> array)

    {
        super(context,0,  array);
        mActivityContext = context;
    }

    //This listener is used for button opening list of albums possessed by artist. This list is controlled by BrowseAlbumsActivity. To display albums, this activity needs to receive array of Album objects in Intent.
    private View.OnClickListener createBrowseAlbumsListener(final Class activityToOpen, final ArrayList<Album> artistAlbumsArray)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(mActivityContext, activityToOpen);

                i.putExtra("array", artistAlbumsArray);
                mActivityContext.startActivity(i);
            }
        };
    }
    //This listener is used for button opening list of all songs possessed by artist. This list is controlled by BrowseSongsActivity. To display albums, this activity needs to receive array of Song objects in Intent.
    private View.OnClickListener createBrowseSongsListener(final Class activityToOpen, final ArrayList<Song> artistAlbumsArray)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(mActivityContext, activityToOpen);

                i.putExtra("array", artistAlbumsArray);
                //When user open list af all songs of particular artist, they will be shuffled.
                i.putExtra("isFromArtist", true);
                mActivityContext.startActivity(i);
            }
        };
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_list_item, parent, false);
        }

        Artist artist = getItem(position);

        ImageView picture = convertView.findViewById(R.id.album_art);
        picture.setImageResource(matchImage(artist.getMusicArtFilename()));

         /*How to assign background color only for text, not for all TextView:
    https://stackoverflow.com/questions/17001574/textview-with-background-color-only-on-the-text-itself
    Answer by lelloman */


        TextView artistName = convertView.findViewById(R.id.text_view_album_title);
        artistName.setText(getSpannableWithBackground(artist.getName(), R.color.colorArtistTextViews));

        TextView artistGenre = convertView.findViewById(R.id.text_view_genre);
        artistGenre.setText(getSpannableWithBackground(artist.getGenre(), R.color.colorArtistTextViews));

        TextView artistCountry = convertView.findViewById(R.id.text_view_country);
        artistCountry.setText(getSpannableWithBackground(artist.getCountry(), R.color.colorArtistTextViews));

        Button browseAlbums = convertView.findViewById(R.id.button_first);
        browseAlbums.setText(R.string.artist_list_item_button_albums);
        browseAlbums.setOnClickListener(createBrowseAlbumsListener(BrowseAlbumsActivity.class, artist.getAlbumsList()));

        Button browseSongs = convertView.findViewById(R.id.button_second);
        browseSongs.setText(R.string.artist_list_item_button_songs);
        ArrayList<Song> artistSongs = artist.getArtistSongs();
        browseSongs.setOnClickListener(createBrowseSongsListener(BrowseSongsActivity.class, artistSongs));

        return convertView;

    }

     //Method receiving name of artist image file, converting it to valid resource identifier.
    private int matchImage(String imageName)
    {
        return mActivityContext.getResources().getIdentifier(imageName, "drawable", "tomaszmarzec.udacity.android.musicplayer");
    }

        /*How to assign background color only for text, not for all TextView:
    https://stackoverflow.com/questions/17001574/textview-with-background-color-only-on-the-text-itself
    Answer by lelloman */
    private Spannable getSpannableWithBackground(String text, int colorResource)
    {
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new BackgroundColorSpan(getContext().getResources().getColor(colorResource)),0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }
}
