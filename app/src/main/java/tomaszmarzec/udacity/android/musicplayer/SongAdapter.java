package tomaszmarzec.udacity.android.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


 class SongAdapter extends ArrayAdapter<Song>
{
    private ArrayList<Song> mSongsToPlay;

    public SongAdapter(Activity context, ArrayList<Song> songsArray)
    {
        super(context, 0, songsArray);
        mSongsToPlay = songsArray;

    }

    private View.OnClickListener createPlaySongListener(final Song songToPlay, final int songPosition)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i =new Intent(getContext(), PlaySongActivity.class);
                i.putExtra("songToPlay", songToPlay);
                i.putExtra("songNumber", songPosition);
                i.putExtra("array", mSongsToPlay);
                getContext().startActivity(i);
            }
        };
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View listItemView = convertView;

        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.song_list_item, parent, false);
        }
            Song currentSong = getItem(position);

            TextView songTitle = listItemView.findViewById(R.id.text_view_song_title);
            songTitle.setText(currentSong.getSongTitle());

            TextView artistTextView = listItemView.findViewById(R.id.text_view_song_artist);
            artistTextView.setText(getSpannableWithBackground(currentSong.getArtist(), R.color.colorSongTextViews));

            TextView genreTextView = listItemView.findViewById(R.id.text_view_song_genre);
            genreTextView.setText(getSpannableWithBackground(currentSong.getGenre(), R.color.colorSongTextViews));

            songTitle.setOnClickListener(createPlaySongListener(currentSong, position));


        return listItemView;
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
