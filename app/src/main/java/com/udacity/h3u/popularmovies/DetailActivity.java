package com.udacity.h3u.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_detail, new DetailFragment())
                    .commit();
        }
    }

    public static class DetailFragment extends android.support.v4.app.Fragment {

        private final String LOG_TAG = this.getClass().getSimpleName();

        private Movie movie;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            // The detail Activity called via intent.
            Intent intent = getActivity().getIntent();

            if (intent != null) {
                // get movie from intent
                movie = (Movie) intent.getExtras().getParcelable(TheMovieDb.MOVIE_KEY);

                if (movie != null) {

                    // fill all views with movie data
                    // start with title
                    TextView titleView = (TextView) rootView.findViewById(R.id.movie_detail_title);
                    titleView.setText(movie.getOriginal_title());

                    // fill rating values
                    TextView ratingView = (TextView) rootView.findViewById(R.id.movie_detail_rating);
                    ratingView.setText(movie.getVote_average().toString()
                            + " " + getString(R.string.movie_detail_rating)
                            + " (" + movie.getVote_count() + ")");
                    ratingView.setContentDescription(
                            getText(R.string.movie_detail_rating_description_average)
                            + Float.toString(movie.getVote_average())
                            + getText(R.string.movie_detail_rating_description_max_rating)
                            + getText(R.string.movie_detail_rating_description_voting_count)
                            + Long.toString(movie.getVote_count())
                    );

                    // fill release date
                    TextView releaseDateView = (TextView) rootView.findViewById(R.id.movie_detail_release_date);
                    releaseDateView.setText(movie.getRelease_date());

                    // fill overview (synopsis)
                    DocumentView overviewTextView = (DocumentView) rootView.findViewById(R.id.movie_detail_overview);
                    overviewTextView.setText(movie.getOverview());
                    overviewTextView.setContentDescription(
                            getText(R.string.movie_detail_overview_description)
                            + movie.getOriginal_title() + ": " + movie.getOverview());

                    // fill poster view
                    ImageView posterView = (ImageView) rootView.findViewById(R.id.movie_detail_poster_thumbnail);
                    String fullURL = TheMovieDb.IMAGE_URL + "/w154" + movie.getPoster_path();
                    Glide.with(getActivity()).load(fullURL).into(posterView);
                    posterView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    posterView.setContentDescription(getText(R.string.movie_detail_thumbnail_description) + " " + movie.getOriginal_title());

                    // fill backdrop view
                    ImageView backdropView = (ImageView) rootView.findViewById(R.id.movie_detail_backdrop);
                    String backdropURL = TheMovieDb.IMAGE_URL + "/w780" + movie.getBackdrop_path();
                    Glide.with(getActivity()).load(backdropURL).into(backdropView);
                    backdropView.setContentDescription(getText(R.string.movie_detail_backdrop_description) + " " + movie.getOriginal_title());
                    backdropView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                }
            }

            return rootView;
        }
    }
}
