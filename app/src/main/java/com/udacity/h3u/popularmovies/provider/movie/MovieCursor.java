/**
 * Auto generated file. Configuration resides in app/src/generator/contentprovider/
 * @see https://github.com/BoD/android-contentprovider-generator
 * Generator version used:
 * https://github.com/BoD/android-contentprovider-generator/releases/download/v1.9.3/android_contentprovider_generator-1.9.3-bundle.jar
 */
package com.udacity.h3u.popularmovies.provider.movie;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.h3u.popularmovies.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code movie} table.
 */
public class MovieCursor extends AbstractCursor implements MovieModel {
    public MovieCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(MovieColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Reference id of movie in themoviedb.org
     */
    public long getForeignId() {
        Long res = getLongOrNull(MovieColumns.FOREIGN_ID);
        if (res == null)
            throw new NullPointerException("The value of 'foreign_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Path to backdrop image
     * Cannot be {@code null}.
     */
    @NonNull
    public String getBackdropPath() {
        String res = getStringOrNull(MovieColumns.BACKDROP_PATH);
        if (res == null)
            throw new NullPointerException("The value of 'backdrop_path' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Original language
     * Can be {@code null}.
     */
    @Nullable
    public String getOriginalLanguage() {
        String res = getStringOrNull(MovieColumns.ORIGINAL_LANGUAGE);
        return res;
    }

    /**
     * Original language
     * Cannot be {@code null}.
     */
    @NonNull
    public String getOriginalTitle() {
        String res = getStringOrNull(MovieColumns.ORIGINAL_TITLE);
        if (res == null)
            throw new NullPointerException("The value of 'original_title' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie plot synopsis
     * Cannot be {@code null}.
     */
    @NonNull
    public String getOverview() {
        String res = getStringOrNull(MovieColumns.OVERVIEW);
        if (res == null)
            throw new NullPointerException("The value of 'overview' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie release date
     * Cannot be {@code null}.
     */
    @NonNull
    public String getReleaseDate() {
        String res = getStringOrNull(MovieColumns.RELEASE_DATE);
        if (res == null)
            throw new NullPointerException("The value of 'release_date' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Path to poster image
     * Cannot be {@code null}.
     */
    @NonNull
    public String getPosterPath() {
        String res = getStringOrNull(MovieColumns.POSTER_PATH);
        if (res == null)
            throw new NullPointerException("The value of 'poster_path' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie popularity value
     */
    public float getPopularity() {
        Float res = getFloatOrNull(MovieColumns.POPULARITY);
        if (res == null)
            throw new NullPointerException("The value of 'popularity' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie title
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(MovieColumns.TITLE);
        return res;
    }

    /**
     * Movie video
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getVideo() {
        Boolean res = getBooleanOrNull(MovieColumns.VIDEO);
        return res;
    }

    /**
     * Movie vote average
     */
    public float getVoteAverage() {
        Float res = getFloatOrNull(MovieColumns.VOTE_AVERAGE);
        if (res == null)
            throw new NullPointerException("The value of 'vote_average' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie vote count
     */
    public long getVoteCount() {
        Long res = getLongOrNull(MovieColumns.VOTE_COUNT);
        if (res == null)
            throw new NullPointerException("The value of 'vote_count' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Movie is marked as favorite
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getFavorite() {
        Boolean res = getBooleanOrNull(MovieColumns.FAVORITE);
        return res;
    }
}
