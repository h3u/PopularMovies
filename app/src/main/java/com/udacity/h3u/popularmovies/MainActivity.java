package com.udacity.h3u.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private final String MOVIE_FRAGMENT_TAG = "MovieGrid";
    private final String MOVIE_FAVORITE_FRAGMENT_TAG = "FavoriteMovieGrid";

    private String mSortBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSortBy = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(getString(R.string.pref_sortby_key), getString(R.string.pref_sortby_value_default));

        if (null == savedInstanceState) {

            if ("favorite".equals(mSortBy)) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new FavoriteMovieGridFragment(), MOVIE_FAVORITE_FRAGMENT_TAG)
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new MovieGridFragment(), MOVIE_FRAGMENT_TAG)
                        .commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // get actual sorting
        String sort_by = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(getString(R.string.pref_sortby_key), getString(R.string.pref_sortby_value_default));

        // fetch movies with previous selected sort by value
        if (!mSortBy.equalsIgnoreCase(sort_by)) {

            if (sort_by.equals("favorite")) {
                FavoriteMovieGridFragment fragment = (FavoriteMovieGridFragment) getSupportFragmentManager()
                        .findFragmentByTag(MOVIE_FAVORITE_FRAGMENT_TAG);
                if (null != fragment) {
                    fragment.update();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, new FavoriteMovieGridFragment(), MOVIE_FAVORITE_FRAGMENT_TAG)
                            .commit();
                }
            } else {

                MovieGridFragment fragment = (MovieGridFragment) getSupportFragmentManager()
                        .findFragmentByTag(MOVIE_FRAGMENT_TAG);
                if (null != fragment) {
                    fragment.update();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, new MovieGridFragment(), MOVIE_FRAGMENT_TAG)
                            .commit();
                }
                mSortBy = sort_by;
            }
        }
    }
}
