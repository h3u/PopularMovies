package com.udacity.h3u.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

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
        saveMovieItem(context, movieId, movieItem);
    }

    public static void removeFavorite(Context context, Long movieId) {

        MovieContentValues movieItem = new MovieContentValues();
        movieItem.putFavorite(false);
        saveMovieItem(context, movieId, movieItem);
    }

    private static void saveMovieItem(Context context, Long movieId, MovieContentValues movieItem) {
        MovieSelection filter = new MovieSelection();
        filter.foreignId(movieId);

        context.getContentResolver().update(
                movieItem.uri(), movieItem.values(), filter.sel(), filter.args());

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
