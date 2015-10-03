package com.udacity.h3u.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 03/10/15.
 */
public class SessionManager {

    public static final String TAG = "SessionManager";
    public static final String PREFS_NAME = "PopularMoviesPreferencesFile";
    public static final String KEY_PREFS_PREFIX = "MoviesLastUpdate_";
    private static final int CONTENT_EXPIRES = 1000 * 60 * 60 * 12;

    private Context mContext;
    private SharedPreferences mPreferences;

    public SessionManager(Context context) {
        this.mContext = context;
        mPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Get stored refresh time for given key and return true if time is greater than
     * 12 hours, false otherwise.
     * The Movie DB API requests are cached for 24 hours, see:
     * https://www.themoviedb.org/talk/523f69a319c2952ef50fe36a
     *
     * @param key sort by key for updating
     * @return boolean
     */
    public boolean needUpdate(String key) {

        // favorite movies cannot be fetched
        if (mContext.getString(R.string.pref_sortby_value_favorite).equalsIgnoreCase(key)) {

            return false;
        }

        Long lastUpdate;
        Date actualDate = new Date();
        lastUpdate = mPreferences.getLong(KEY_PREFS_PREFIX + key, actualDate.getTime());

        return (actualDate.getTime() - lastUpdate) > CONTENT_EXPIRES;
    }

    /**
     * Set expiry for given key.
     *
     * @param key sort_by key
     */
    public void setLastUpdate(String key) {

        // favorite movies doesn't need to set this
        if (mContext.getString(R.string.pref_sortby_value_favorite).equalsIgnoreCase(key)) {

            return;
        }
        Date actualDate = new Date();
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putLong(KEY_PREFS_PREFIX + key, actualDate.getTime());
        editor.commit();
    }

    public String getSortBy() {

        return PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .getString(mContext.getString(R.string.pref_sortby_key),
                        mContext.getString(R.string.pref_sortby_value_default));
    }
}
