package com.udacity.h3u.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.udacity.h3u.popularmovies.provider.movie.MovieColumns;
import com.udacity.h3u.popularmovies.provider.movie.MovieContentValues;
import com.udacity.h3u.popularmovies.provider.movie.MovieCursor;
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

    public static boolean isMovieFavorite(Context context, Movie movie) {
        boolean favorite = false;
        String[] projection = { MovieColumns._ID, MovieColumns.FAVORITE };

        MovieSelection filter = new MovieSelection();
        filter.foreignId(movie.getId());

        Cursor cursor = context.getContentResolver().query(
                MovieColumns.CONTENT_URI, projection,
                filter.sel(), filter.args(), null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            MovieCursor movieCursor = new MovieCursor(cursor);
            favorite = movieCursor.getFavorite();
            cursor.close();
        }

        return favorite;
    }

    public static boolean removeMovie(Context context, Movie movie) {

        MovieSelection filter = new MovieSelection();
        filter.foreignId(movie.getId());

        int deleted = context.getContentResolver().delete(
                MovieColumns.CONTENT_URI,
                filter.sel(), filter.args());

        return (deleted == 1);
    }

    public static void addFavorite(Context context, Movie movie) {

        MovieContentValues movieItem = new MovieContentValues();
        movieItem
                .putForeignId(movie.getId())
                .putBackdropPath(movie.getBackdrop_path())
                .putOriginalTitle(movie.getOriginal_title())
                .putTitle(movie.getOriginal_title())
                .putOverview(movie.getOverview())
                .putPopularity(movie.getPopularity())
                .putPosterPath(movie.getPoster_path())
                .putReleaseDate(movie.getRelease_date())
                .putVoteAverage(movie.getVote_average())
                .putVoteCount(movie.getVote_count())
                .putFavorite(true);

        context.getContentResolver().insert(movieItem.uri(), movieItem.values());
    }
}
