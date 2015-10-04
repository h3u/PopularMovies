package com.udacity.h3u.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.udacity.h3u.popularmovies.provider.movie.MovieColumns;
import com.udacity.h3u.popularmovies.provider.movie.MovieContentValues;
import com.udacity.h3u.popularmovies.provider.movie.MovieSelection;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 13/09/15.
 */
public class Util {

    public static void watchYoutubeVideo(Context context, String id) {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            context.startActivity(intent);

        } catch (ActivityNotFoundException ex) {

            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            context.startActivity(intent);

        }
    }

    public static void addFavorite(Context context, Long movieId) {

        MovieContentValues movieItem = new MovieContentValues();
        movieItem.putFavorite(true);
        if (saveMovieItem(context, movieId, movieItem)) {
            Toast.makeText(context, context.getText(R.string.movie_detail_toggle_add),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void removeFavorite(Context context, Long movieId) {

        MovieContentValues movieItem = new MovieContentValues();
        movieItem.putFavorite(false);
        if (saveMovieItem(context, movieId, movieItem)) {
            Toast.makeText(context, context.getText(R.string.movie_detail_toggle_remove),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean saveMovieItem(Context context, Long movieId, MovieContentValues movieItem) {
        MovieSelection filter = new MovieSelection();
        filter.foreignId(movieId);

        return (context.getContentResolver().update(
                movieItem.uri(), movieItem.values(), filter.sel(), filter.args())
                == 1);

    }

    public static boolean movieExist(Context context, Movie movie) {
        boolean exist = false;
        String[] projection = { MovieColumns._ID };

        MovieSelection filter = new MovieSelection();
        filter.foreignId(movie.getId());

        Cursor cursor = context.getContentResolver().query(
                MovieColumns.CONTENT_URI, projection,
                filter.sel(), filter.args(), null);

        if (cursor != null && cursor.getCount() > 0) {
            exist = true;
            cursor.close();
        }

        return exist;
    }
}
