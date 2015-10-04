package com.udacity.h3u.popularmovies;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.udacity.h3u.popularmovies.adapter.MovieCursorAdapter;
import com.udacity.h3u.popularmovies.provider.movie.MovieColumns;
import com.udacity.h3u.popularmovies.provider.movie.MovieCursor;
import com.udacity.h3u.popularmovies.provider.movie.MovieSelection;

/**
 * Fragment containing a grid of movie posters.
 */
public class MovieGridFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public final String LOG_TAG = this.getClass().getSimpleName();

    private static final int LOADER_ID = 9;
    private static final String GRID_POSITION = "key_grid_position";

    private String mSortBy;
    private MovieCursorAdapter movieAdapter;
    private GridView mGridView;
    private int mPosition = 0;

    public MovieGridFragment() {
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback if an item has been selected.
         */
        public void onItemSelected(Long movieId);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManager session = new SessionManager(getContext());
        mSortBy = session.getSortBy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_grid, container, false);

        movieAdapter = new MovieCursorAdapter(getActivity(), null, 0);
        getLoaderManager().initLoader(LOADER_ID, null, this);

        mGridView = (GridView) rootView.findViewById(R.id.grid_view_movies);
        mGridView.setAdapter(movieAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (null != cursor) {

                    MovieCursor movieCursor = new MovieCursor(cursor);
                    ((Callback) getActivity()).onItemSelected(movieCursor.getForeignId());

                    // save position
                    mPosition = position;
                }
            }
        });

        if (null != savedInstanceState && savedInstanceState.containsKey(GRID_POSITION)) {
            mPosition = savedInstanceState.getInt(GRID_POSITION);
        }

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri movieUri = MovieColumns.CONTENT_URI;
        String sortOrder = MovieColumns.POPULARITY + " DESC";
        MovieSelection filter = new MovieSelection();

        if (mSortBy.equalsIgnoreCase("favorite")) {
            filter.favorite(true);
            sortOrder = MovieColumns.TITLE + " ASC";
        } else if (mSortBy.equalsIgnoreCase("vote_average")) {
            sortOrder = MovieColumns.VOTE_AVERAGE + " DESC";
        }

        return new CursorLoader(
                getActivity(),
                movieUri,
                MovieColumns.ALL_COLUMNS,
                filter.sel(), filter.args(), sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieAdapter.swapCursor(data);
        if (mPosition != GridView.INVALID_POSITION) {
            mGridView.setSelection(mPosition);
            mGridView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }

    public void fetch(String sort_by) {
        mSortBy = sort_by;
        FetchMoviesTask task = new FetchMoviesTask(getActivity());
        task.execute(mSortBy);
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != GridView.INVALID_POSITION) {
            outState.putInt(GRID_POSITION, mPosition);
        }
        super.onSaveInstanceState(outState);
    }
}
