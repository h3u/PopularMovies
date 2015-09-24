/**
 * Auto generated file. Configuration resides in app/src/generator/contentprovider/
 * @see https://github.com/BoD/android-contentprovider-generator
 * Generator version used:
 * https://github.com/BoD/android-contentprovider-generator/releases/download/v1.9.3/android_contentprovider_generator-1.9.3-bundle.jar
 */
package com.udacity.h3u.popularmovies.provider.movie;

import com.udacity.h3u.popularmovies.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A Movie that comes from api.themoviedb.org.
 */
public interface MovieModel extends BaseModel {

    /**
     * Reference id of movie in themoviedb.org
     */
    long getForeignId();

    /**
     * Path to backdrop image
     * Cannot be {@code null}.
     */
    @NonNull
    String getBackdropPath();

    /**
     * Original language
     * Can be {@code null}.
     */
    @Nullable
    String getOriginalLanguage();

    /**
     * Original language
     * Cannot be {@code null}.
     */
    @NonNull
    String getOriginalTitle();

    /**
     * Movie plot synopsis
     * Cannot be {@code null}.
     */
    @NonNull
    String getOverview();

    /**
     * Movie release date
     * Cannot be {@code null}.
     */
    @NonNull
    String getReleaseDate();

    /**
     * Path to poster image
     * Cannot be {@code null}.
     */
    @NonNull
    String getPosterPath();

    /**
     * Movie popularity value
     */
    float getPopularity();

    /**
     * Movie title
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Movie video
     * Can be {@code null}.
     */
    @Nullable
    Boolean getVideo();

    /**
     * Movie vote average
     */
    float getVoteAverage();

    /**
     * Movie vote count
     */
    long getVoteCount();

    /**
     * Movie is marked as favorite
     * Can be {@code null}.
     */
    @Nullable
    Boolean getFavorite();
}
