/**
 * Auto generated file. Configuration resides in app/src/generator/contentprovider/
 * @see https://github.com/BoD/android-contentprovider-generator
 * Generator version used:
 * https://github.com/BoD/android-contentprovider-generator/releases/download/v1.9.3/android_contentprovider_generator-1.9.3-bundle.jar
 */
package com.udacity.h3u.popularmovies.provider.movie;

import android.net.Uri;
import android.provider.BaseColumns;

import com.udacity.h3u.popularmovies.provider.PopularMoviesProvider;
import com.udacity.h3u.popularmovies.provider.movie.MovieColumns;

/**
 * A Movie that comes from api.themoviedb.org.
 */
public class MovieColumns implements BaseColumns {
    public static final String TABLE_NAME = "movie";
    public static final Uri CONTENT_URI = Uri.parse(PopularMoviesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Reference id of movie in themoviedb.org
     */
    public static final String FOREIGN_ID = "foreign_id";

    /**
     * Path to backdrop image
     */
    public static final String BACKDROP_PATH = "backdrop_path";

    /**
     * Original language
     */
    public static final String ORIGINAL_LANGUAGE = "original_language";

    /**
     * Original language
     */
    public static final String ORIGINAL_TITLE = "original_title";

    /**
     * Movie plot synopsis
     */
    public static final String OVERVIEW = "overview";

    /**
     * Movie release date
     */
    public static final String RELEASE_DATE = "release_date";

    /**
     * Path to poster image
     */
    public static final String POSTER_PATH = "poster_path";

    /**
     * Movie popularity value
     */
    public static final String POPULARITY = "popularity";

    /**
     * Movie title
     */
    public static final String TITLE = "title";

    /**
     * Movie video
     */
    public static final String VIDEO = "video";

    /**
     * Movie vote average
     */
    public static final String VOTE_AVERAGE = "vote_average";

    /**
     * Movie vote count
     */
    public static final String VOTE_COUNT = "vote_count";

    /**
     * Movie is marked as favorite
     */
    public static final String FAVORITE = "favorite";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            FOREIGN_ID,
            BACKDROP_PATH,
            ORIGINAL_LANGUAGE,
            ORIGINAL_TITLE,
            OVERVIEW,
            RELEASE_DATE,
            POSTER_PATH,
            POPULARITY,
            TITLE,
            VIDEO,
            VOTE_AVERAGE,
            VOTE_COUNT,
            FAVORITE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(FOREIGN_ID) || c.contains("." + FOREIGN_ID)) return true;
            if (c.equals(BACKDROP_PATH) || c.contains("." + BACKDROP_PATH)) return true;
            if (c.equals(ORIGINAL_LANGUAGE) || c.contains("." + ORIGINAL_LANGUAGE)) return true;
            if (c.equals(ORIGINAL_TITLE) || c.contains("." + ORIGINAL_TITLE)) return true;
            if (c.equals(OVERVIEW) || c.contains("." + OVERVIEW)) return true;
            if (c.equals(RELEASE_DATE) || c.contains("." + RELEASE_DATE)) return true;
            if (c.equals(POSTER_PATH) || c.contains("." + POSTER_PATH)) return true;
            if (c.equals(POPULARITY) || c.contains("." + POPULARITY)) return true;
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(VIDEO) || c.contains("." + VIDEO)) return true;
            if (c.equals(VOTE_AVERAGE) || c.contains("." + VOTE_AVERAGE)) return true;
            if (c.equals(VOTE_COUNT) || c.contains("." + VOTE_COUNT)) return true;
            if (c.equals(FAVORITE) || c.contains("." + FAVORITE)) return true;
        }
        return false;
    }

}
