package com.udacity.h3u.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private SessionManager mSessionManager;
    private String mSortBy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSessionManager = new SessionManager(this);
        mSortBy = mSessionManager.getSortBy();
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
        MovieGridFragment fragment = (MovieGridFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_movie_grid);

        if (sortBy != null
                && (!sortBy.equalsIgnoreCase(mSortBy) || mSessionManager.needUpdate(sortBy)) ) {
            mSortBy = sortBy;
            fragment.fetch(mSortBy);
            mSessionManager.setLastUpdate(mSortBy);
        }
    }
}
