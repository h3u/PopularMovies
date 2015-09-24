/**
 * Auto generated file. Configuration resides in app/src/generator/contentprovider/
 * @see https://github.com/BoD/android-contentprovider-generator
 * Generator version used:
 * https://github.com/BoD/android-contentprovider-generator/releases/download/v1.9.3/android_contentprovider_generator-1.9.3-bundle.jar
 */
package com.udacity.h3u.popularmovies.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.udacity.h3u.popularmovies.BuildConfig;
import com.udacity.h3u.popularmovies.provider.movie.MovieColumns;

public class PopularMoviesSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = PopularMoviesSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "popular_movies.db";
    private static final int DATABASE_VERSION = 1;
    private static PopularMoviesSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final PopularMoviesSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE IF NOT EXISTS "
            + MovieColumns.TABLE_NAME + " ( "
            + MovieColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MovieColumns.FOREIGN_ID + " INTEGER NOT NULL, "
            + MovieColumns.BACKDROP_PATH + " TEXT NOT NULL, "
            + MovieColumns.ORIGINAL_LANGUAGE + " TEXT, "
            + MovieColumns.ORIGINAL_TITLE + " TEXT NOT NULL, "
            + MovieColumns.OVERVIEW + " TEXT NOT NULL, "
            + MovieColumns.RELEASE_DATE + " TEXT NOT NULL, "
            + MovieColumns.POSTER_PATH + " TEXT NOT NULL, "
            + MovieColumns.POPULARITY + " REAL NOT NULL, "
            + MovieColumns.TITLE + " TEXT, "
            + MovieColumns.VIDEO + " INTEGER DEFAULT 0, "
            + MovieColumns.VOTE_AVERAGE + " REAL NOT NULL, "
            + MovieColumns.VOTE_COUNT + " INTEGER NOT NULL, "
            + MovieColumns.FAVORITE + " INTEGER DEFAULT 0 "
            + ", CONSTRAINT unique_id UNIQUE (foreign_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_INDEX_MOVIE_FOREIGN_ID = "CREATE INDEX IDX_MOVIE_FOREIGN_ID "
            + " ON " + MovieColumns.TABLE_NAME + " ( " + MovieColumns.FOREIGN_ID + " );";

    // @formatter:on

    public static PopularMoviesSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static PopularMoviesSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static PopularMoviesSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new PopularMoviesSQLiteOpenHelper(context);
    }

    private PopularMoviesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new PopularMoviesSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static PopularMoviesSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new PopularMoviesSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private PopularMoviesSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new PopularMoviesSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_INDEX_MOVIE_FOREIGN_ID);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
