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
import android.widget.ListView;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

 class BrowseSongsActivity extends AppCompatActivity
{
    private ArrayList<Song> songsToDisplay;

@BindView(R.id.list_view) ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent i = getIntent();

        super.onCreate(savedInstanceState);
        if(i.getExtras()!=null)
            setContentView(R.layout.activity_browse_songs_no_filters);
        else /*If there is no extras in Intent, it means that BrowseSongsActivity is called from MainActivity, and is displaying all songs in app. To help user navigate through them,
               another layout is loaded for activity - a layout containing radio buttons for switching between different filters*/
        {
            setContentView(R.layout.activity_browse_songs_filters);

            RadioGroup rGroup = findViewById(R.id.radio_buttons);
            rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedID)
                {
                    switch (checkedID)
                    {
                        case R.id.radio_button_alphabetically:
                            Collections.sort(songsToDisplay, Song.songTitleComparator);
                            displaySongs(songsToDisplay);
                            break;
                        case R.id.radio_button_shuffle:
                            Collections.shuffle(songsToDisplay);
                            displaySongs(songsToDisplay);
                            break;
                        case R.id.radio_button_genre:
                            Collections.sort(songsToDisplay, Song.songGenreComparator);
                            displaySongs(songsToDisplay);
                            break;
                    }
                }
            });
        }


        if(i.getExtras()!=null)
        {
            Bundle extras = i.getExtras();
            songsToDisplay =  extras.getParcelableArrayList("array");
            boolean isFromArtist= extras.getBoolean("isFromArtist");
            if(isFromArtist)
            Collections.shuffle(songsToDisplay);

        }
        else //this code block is called when BrowseSongsActivity is called from MainActivity. It happens whenu user taps Songs in main menu of app to display all songs in library.
        {
            songsToDisplay = Database.getAllSongsList();
            Collections.sort(songsToDisplay, Song.songTitleComparator);
        }


        ButterKnife.bind(this);

        displaySongs(songsToDisplay);
        renderBackground();
    }

    private void displaySongs(ArrayList<Song> songsToDisplay)
    {
        SongAdapter songAdapter = new SongAdapter(this, songsToDisplay);
        listView.setAdapter(songAdapter);
    }

    private void renderBackground()
    {
        final ConstraintLayout rootView = findViewById(R.id.rootConstraintView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Glide.with(this).asBitmap().load(R.drawable.songs_background).into(new SimpleTarget<Bitmap>(displayMetrics.widthPixels, displayMetrics.heightPixels) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Drawable drawable = new BitmapDrawable(resource);
                rootView.setBackground(drawable);
            }
        });
    }
}
