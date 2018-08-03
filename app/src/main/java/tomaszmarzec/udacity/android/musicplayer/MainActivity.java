package tomaszmarzec.udacity.android.musicplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

/*    To shortly explain how this app functions: Every artist, music album and song is an object. There are three classes in which music is organised: Album, Artist and Song
    Artist possesses member variable mAlbumsList, an ArrayList<Album> of albums. Album similarly posses mAlbumSongs, an ArrayList<Song> of songs. So, Song objects are organised in Album objects,
    and Album objects are organised in Artist objects. There are three custom adapter classes, for each of mentioned three classes.
    */


    @OnClick({R.id.browse_artists})
    protected void open_browse_artists()
    {
        Intent i =new Intent(this, BrowseArtistsActivity.class);
        startActivity(i);
    }

    @OnClick({R.id.browse_albums})
    protected void open_browse_albums()
    {
        Intent i =new Intent(this, BrowseAlbumsActivity.class);
        startActivity(i);
    }

    @OnClick({R.id.browse_songs})
    protected void open_browse_songs()
    {
        Intent i =new Intent(this, BrowseSongsActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //This two public, static methods of Database class are used to populate arrays of artists, albums and songs with example objects.
        Database.addArtists();
        Database.addMusic();


        ButterKnife.bind(this);

        renderBackground();
    }


    private void renderBackground()
    {
        final LinearLayout rootView = findViewById(R.id.rootLinearView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Glide.with(this).asBitmap().load(R.drawable.chipset_background).into(new SimpleTarget<Bitmap>(displayMetrics.widthPixels, displayMetrics.heightPixels) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
            {
                Drawable drawable = new BitmapDrawable(resource);
                rootView.setBackground(drawable);
            }
        });

    }
/*    private void renderImages()
    {
        final LinearLayout rootView = findViewById(R.id.rootLinearView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //Code based on StackOverflow topic: https://stackoverflow.com/questions/33971626/set-background-image-to-relative-layout-using-glide-in-android/38025862
        // Answer by Chintan Desai
        Glide.with(this).load(R.drawable.chipset_background).asBitmap().into(new SimpleTarget<Bitmap>(displayMetrics.widthPixels, displayMetrics.heightPixels)
        {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
            {
                Drawable drawable = new BitmapDrawable(resource);
                rootView.setBackground(drawable);
            }
        });
    }*/
}
