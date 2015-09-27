package com.udacity.h3u.popularmovies.adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.udacity.h3u.popularmovies.R;
import com.udacity.h3u.popularmovies.TheMovieDb;
import com.udacity.h3u.popularmovies.provider.movie.MovieCursor;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 24/09/15.
 */
public class MovieCursorAdapter extends CursorAdapter {

    public MovieCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_grid_item, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        MovieCursor mCursor = new MovieCursor(cursor);

        ImageView posterView = (ImageView) view;
        posterView.setContentDescription(
                mContext.getText(R.string.movie_grid_item_content_description)
                        + mCursor.getOriginalTitle()
        );
        String posterURL = TheMovieDb.IMAGE_URL + "/w185" + mCursor.getPosterPath();
        Glide.with(context).load(posterURL).centerCrop().into(posterView);
    }
}
