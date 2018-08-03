package tomaszmarzec.udacity.android.musicplayer;

import android.app.Activity;
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

 class AlbumAdapter extends ArrayAdapter<Album>
{
    // This class creates views representing albums to populate ListView used in BrowseAlbumsActivity.

    protected AlbumAdapter(Activity context, ArrayList<Album> albumsArray)
    {
        super(context, 0, albumsArray);
    }

    //This listener is used for button opening list of songs in album. This list is controlled by BrowseSongsActivity. To display songs, this activity needs to receive array of Song objects in Intent.
    private View.OnClickListener createBrowseAlbumListener(final Class activityToOpen, final ArrayList<Song> albumSongsArray)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getContext(), activityToOpen);
                i.putExtra("array", albumSongsArray);
                getContext().startActivity(i);
            }
        };
    }

    //This listener is used for button which opens PlaySongActivity, feeding it with first song on album's songs list. This activity also receives all Song objects from album, so user can switch between them.
    private View.OnClickListener createPlaySongListener(final int songPosition, final ArrayList<Song> albumSongsArray)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i =new Intent(getContext(), PlaySongActivity.class);
                i.putExtra("songToPlay", albumSongsArray.get(0));
                i.putExtra("songNumber", songPosition);
                i.putExtra("array", albumSongsArray);
                getContext().startActivity(i);
            }
        };
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.album_list_item, parent, false);
        }

        Album album = getItem(position);

        TextView albumTitle = convertView.findViewById(R.id.text_view_album_title);
        albumTitle.setText(album.getAlbumTitle());

        TextView albumGenre = convertView.findViewById(R.id.text_view_genre);
        albumGenre.setText(getSpannableWithBackground(album.getAlbumGenre(), R.color.colorAlbumTextViews));

        TextView albumReleaseYear = convertView.findViewById(R.id.text_view_year);
        albumReleaseYear.setText(getSpannableWithBackground(album.getReleaseYear(), R.color.colorAlbumTextViews));

        ImageView picture = convertView.findViewById(R.id.album_art);
        picture.setImageResource(matchImage(album.getMusicArtFilename()));

        Button browseAlbums = convertView.findViewById(R.id.button_first);
        browseAlbums.setText(R.string.album_list_item_button_songs);
        browseAlbums.setOnClickListener(createBrowseAlbumListener(BrowseSongsActivity.class, album.getAlbumSongs()));

        Button browseSongs = convertView.findViewById(R.id.button_second);
        browseSongs.setText("Play");
        browseSongs.setOnClickListener(createPlaySongListener(0, album.getAlbumSongs()));

        return convertView;
    }

    //Method receiving name of artist image file, converting it to valid resource identifier.
    private int matchImage(String imageName)
    {
        return getContext().getResources().getIdentifier(imageName, "drawable", "tomaszmarzec.udacity.android.musicplayer");
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
