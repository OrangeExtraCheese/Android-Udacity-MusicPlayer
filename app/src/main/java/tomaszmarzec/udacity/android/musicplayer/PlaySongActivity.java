package tomaszmarzec.udacity.android.musicplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

public class PlaySongActivity extends AppCompatActivity
{
    @BindView(R.id.album_art) ImageView musicArt;
    @BindView(R.id.text_view_song_title) TextView songTitle;

    private boolean songPlaying = false;

   @OnClick({R.id.previous_song_button})
   protected void playPreviousSong()
   {
       if(currentSongPosition-1 >= 0)
       {
           currentSongPosition--;
           playSong(songsToPlay.get(currentSongPosition));
       }
   }

    @OnClick({R.id.next_song_button})
    protected void playNextSong()
    {
        if(currentSongPosition+1 < songsToPlay.size())
        {
            currentSongPosition++;
            playSong(songsToPlay.get(currentSongPosition));
        }
    }

    @OnClick({R.id.play_song_button})
    protected void playSong()
    {
        if (songPlaying==false)
        {
            songPlaying = true;
            Toast toast =  Toast.makeText(this, "Music is playing!", Toast.LENGTH_SHORT);
            toast.show();
            ImageView playIcon = findViewById(R.id.play_song_button);
            playIcon.setImageResource(R.drawable.pause_button);
        }

        else if (songPlaying==true)
        {
            songPlaying = false;
            Toast toast =  Toast.makeText(this, "Silence has fallen!", Toast.LENGTH_SHORT);
            toast.show();
            ImageView playIcon = findViewById(R.id.play_song_button);
            playIcon.setImageResource(R.drawable.play_button);
        }
    }

    ArrayList<Song> songsToPlay;
    int currentSongPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        Intent i = getIntent();

        Song songToPlay = i.getParcelableExtra("songToPlay");
        songsToPlay = i.getParcelableArrayListExtra("array");
        currentSongPosition =  i.getIntExtra("songNumber", 0);

        playSong(songToPlay);
        songPlaying = true;
        renderBackground();
    }

    private void playSong(Song song)
    {
        ButterKnife.bind(this);
        songTitle.setText(song.getSongTitle());
        // Name of file containg music art, of the type of String, is converted to resource identifier
        musicArt.setImageResource(getResources().getIdentifier(song.getMusicArtFileName(), "drawable", "tomaszmarzec.udacity.android.musicplayer"));
    }

    private void renderBackground()
    {
        final ConstraintLayout rootView = findViewById(R.id.rootConstraintView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Glide.with(this).asBitmap().load(R.drawable.play_background).into(new SimpleTarget<Bitmap>(displayMetrics.widthPixels, displayMetrics.heightPixels)
        {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Drawable drawable = new BitmapDrawable(resource);
                rootView.setBackground(drawable);
            }
        });
    }
}
