package com.udacity.h3u.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements MovieGridFragment.Callback {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private static final String MOVIE_DETAIL_FRAGMENT = "MovieDetailFragment";

    private SessionManager mSessionManager;
    private String mSortBy;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSessionManager = new SessionManager(this);

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailFragment(), MOVIE_DETAIL_FRAGMENT)
                        .commit();
            }
        } else {
            mTwoPane = false;
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

        // get sorting from preferences
        String sortBy = mSessionManager.getSortBy();
        Boolean sortingChanged = false;

        if (null != mSortBy) {
            sortingChanged = !sortBy.equalsIgnoreCase(mSortBy);
        }
        mSortBy = sortBy;

        MovieGridFragment gridFragment = (MovieGridFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_movie_grid);
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentByTag(MOVIE_DETAIL_FRAGMENT);

        if (sortingChanged || mSessionManager.needUpdate(sortBy)) {

            if (null != gridFragment) {
                gridFragment.fetch(mSortBy);

                mSessionManager.setLastUpdate(mSortBy);
            }
            if (null != detailFragment && sortingChanged) {
                detailFragment.switchInvisible();
            }
        }
    }

    @Override
    public void onItemSelected(Long movieId) {
        if (mTwoPane) {
            // replace detail fragment
            Bundle args = new Bundle();
            args.putLong(TheMovieDb.MOVIE_KEY, movieId);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, MOVIE_DETAIL_FRAGMENT)
                    .commit();
        } else {
            Intent detail = new Intent(this, DetailActivity.class);
            detail.putExtra(TheMovieDb.MOVIE_KEY, movieId);
            startActivity(detail);
        }
    }
}
