/**
 * Auto generated file. Configuration resides in app/src/generator/contentprovider/
 * @see https://github.com/BoD/android-contentprovider-generator
 * Generator version used:
 * https://github.com/BoD/android-contentprovider-generator/releases/download/v1.9.3/android_contentprovider_generator-1.9.3-bundle.jar
 */
package com.udacity.h3u.popularmovies.provider.movie;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.h3u.popularmovies.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code movie} table.
 */
public class MovieContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MovieColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MovieSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MovieSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Reference id of movie in themoviedb.org
     */
    public MovieContentValues putForeignId(long value) {
        mContentValues.put(MovieColumns.FOREIGN_ID, value);
        return this;
    }


    /**
     * Path to backdrop image
     */
    public MovieContentValues putBackdropPath(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("backdropPath must not be null");
        mContentValues.put(MovieColumns.BACKDROP_PATH, value);
        return this;
    }


    /**
     * Original language
     */
    public MovieContentValues putOriginalLanguage(@Nullable String value) {
        mContentValues.put(MovieColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public MovieContentValues putOriginalLanguageNull() {
        mContentValues.putNull(MovieColumns.ORIGINAL_LANGUAGE);
        return this;
    }

    /**
     * Original language
     */
    public MovieContentValues putOriginalTitle(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("originalTitle must not be null");
        mContentValues.put(MovieColumns.ORIGINAL_TITLE, value);
        return this;
    }


    /**
     * Movie plot synopsis
     */
    public MovieContentValues putOverview(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("overview must not be null");
        mContentValues.put(MovieColumns.OVERVIEW, value);
        return this;
    }


    /**
     * Movie release date
     */
    public MovieContentValues putReleaseDate(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("releaseDate must not be null");
        mContentValues.put(MovieColumns.RELEASE_DATE, value);
        return this;
    }


    /**
     * Path to poster image
     */
    public MovieContentValues putPosterPath(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("posterPath must not be null");
        mContentValues.put(MovieColumns.POSTER_PATH, value);
        return this;
    }


    /**
     * Movie popularity value
     */
    public MovieContentValues putPopularity(float value) {
        mContentValues.put(MovieColumns.POPULARITY, value);
        return this;
    }


    /**
     * Movie title
     */
    public MovieContentValues putTitle(@Nullable String value) {
        mContentValues.put(MovieColumns.TITLE, value);
        return this;
    }

    public MovieContentValues putTitleNull() {
        mContentValues.putNull(MovieColumns.TITLE);
        return this;
    }

    /**
     * Movie video
     */
    public MovieContentValues putVideo(@Nullable Boolean value) {
        mContentValues.put(MovieColumns.VIDEO, value);
        return this;
    }

    public MovieContentValues putVideoNull() {
        mContentValues.putNull(MovieColumns.VIDEO);
        return this;
    }

    /**
     * Movie vote average
     */
    public MovieContentValues putVoteAverage(float value) {
        mContentValues.put(MovieColumns.VOTE_AVERAGE, value);
        return this;
    }


    /**
     * Movie vote count
     */
    public MovieContentValues putVoteCount(long value) {
        mContentValues.put(MovieColumns.VOTE_COUNT, value);
        return this;
    }


    /**
     * Movie is marked as favorite
     */
    public MovieContentValues putFavorite(@Nullable Boolean value) {
        mContentValues.put(MovieColumns.FAVORITE, value);
        return this;
    }

    public MovieContentValues putFavoriteNull() {
        mContentValues.putNull(MovieColumns.FAVORITE);
        return this;
    }
}
