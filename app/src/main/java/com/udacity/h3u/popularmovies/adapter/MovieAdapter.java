package com.udacity.h3u.popularmovies.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.udacity.h3u.popularmovies.Movie;
import com.udacity.h3u.popularmovies.R;
import com.udacity.h3u.popularmovies.TheMovieDb;

import java.util.List;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 05/08/15.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context mContext;

    public MovieAdapter(Context context, List<Movie> list) {
        super(context, 0, list);
        mContext = context;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the Movie object from the ArrayAdapter at the appropriate position
        Movie movie = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_grid_item, parent, false);
        }

        ImageView posterView = (ImageView) convertView.findViewById(R.id.image_movie_poster);
        posterView.setContentDescription(
                mContext.getText(R.string.movie_grid_item_content_description)
                + movie.getOriginal_title()
        );
        String posterURL = TheMovieDb.IMAGE_URL + "/w185" + movie.getPoster_path();
        Glide.with(getContext()).load(posterURL).centerCrop().into(posterView);

        return convertView;
    }
}
