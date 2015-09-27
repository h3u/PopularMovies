package com.udacity.h3u.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 27/09/15.
 */
public abstract class GridFragment extends Fragment {

    public final String LOG_TAG = this.getClass().getSimpleName();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    interface Updateable {
        public void update();
    }

    protected void startDetailWithIntent(Movie movie) {
        if (movie != null) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(TheMovieDb.MOVIE_KEY, movie);
            startActivity(intent);
        }
    }
}
