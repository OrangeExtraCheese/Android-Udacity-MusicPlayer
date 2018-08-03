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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Collections;

class BrowseAlbumsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_music);

        Intent i = getIntent();
        ArrayList<Album> albumsToDisplay;

        if(i.getExtras()!=null)
        {
            Bundle extras = i.getExtras();
            albumsToDisplay =  extras.getParcelableArrayList("array");
            Collections.sort(albumsToDisplay, Album.albumReleaseYearComparator);

        }
        else
        {
            albumsToDisplay = Database.getAllAlbumsList();
            Collections.shuffle(albumsToDisplay);

        }


        //After being used as argument for AlbumAdapter constructor, ArrayList of albums is sorted by year of album release.


        AlbumAdapter albumAdapter = new AlbumAdapter(this, albumsToDisplay);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(albumAdapter);

        renderBackground();
    }

    private void renderBackground()
    {
        final ConstraintLayout rootView = findViewById(R.id.rootConstraintView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Glide.with(this).asBitmap().load(R.drawable.albums_background).into(new SimpleTarget<Bitmap>(displayMetrics.widthPixels, displayMetrics.heightPixels) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
            {
                Drawable drawable = new BitmapDrawable(resource);
                rootView.setBackground(drawable);
            }
        });

    }
}
