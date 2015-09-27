package com.udacity.h3u.popularmovies;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.udacity.h3u.popularmovies.adapter.MovieCursorAdapter;
import com.udacity.h3u.popularmovies.provider.movie.MovieColumns;
import com.udacity.h3u.popularmovies.provider.movie.MovieCursor;

import java.util.ArrayList;


/**
 * Fragment containing a grid of movie posters.
 */
public class FavoriteMovieGridFragment extends GridFragment
        implements LoaderManager.LoaderCallbacks<Cursor>, GridFragment.Updateable  {

    private static final int LOADER_ID = 9;
    private ArrayList<Movie> mList;
    private MovieCursorAdapter movieAdapter;

    public FavoriteMovieGridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_grid, container, false);

        movieAdapter = new MovieCursorAdapter(getActivity(), null, 0);
        getLoaderManager().initLoader(LOADER_ID, null, this);

        GridView gridview = (GridView) rootView.findViewById(R.id.grid_view_movies);
        gridview.setAdapter(movieAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (null != cursor) {

                    MovieCursor movieCursor = new MovieCursor(cursor);
                    Movie movie = new Movie();
                    movie.createBy(movieCursor);
                    startDetailWithIntent(movie);
                }
            }
        });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri movieUri = MovieColumns.CONTENT_URI;
        String sortOrder = MovieColumns.TITLE + " ASC";

        return new CursorLoader(
                getActivity(),
                movieUri,
                MovieColumns.ALL_COLUMNS,
                null, null, sortOrder
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }

    public void update() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
