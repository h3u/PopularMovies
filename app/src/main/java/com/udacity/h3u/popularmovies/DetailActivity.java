package com.udacity.h3u.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.bumptech.glide.Glide;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        private RecyclerView mReviewView;
        private LinearLayout mReviewViewLayout;

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            outState.putParcelable(TheMovieDb.MOVIE_KEY, movie);
            super.onSaveInstanceState(outState);
        }


        protected void fetchReviews(Movie movie) {
            FetchReviewListTask task = new FetchReviewListTask();
            task.execute(movie.getId());
        }

        private void setupRecyclerView(RecyclerView recyclerView) {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            // check for saved movie object
            if (savedInstanceState != null && savedInstanceState.containsKey(TheMovieDb.MOVIE_KEY)) {
                movie = savedInstanceState.getParcelable(TheMovieDb.MOVIE_KEY);
            } else {
                // get movie from intent
                Intent intent = getActivity().getIntent();
                movie = (Movie) intent.getExtras().getParcelable(TheMovieDb.MOVIE_KEY);
            }

            mReviewView = (RecyclerView) rootView.findViewById(R.id.movie_detail_review_list);
            mReviewViewLayout = (LinearLayout) rootView.findViewById(R.id.movie_detail_reviews_layout);
            setupRecyclerView(mReviewView);

            if (movie != null) {

                fetchReviews(movie);
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

            return rootView;
        }

        public class FetchReviewListTask extends AsyncTask<Long, Void, ReviewList> {

            private final String LOG_TAG = this.getClass().getSimpleName();

            private ReviewList mList;


            @Override
            protected ReviewList doInBackground(Long... params) {

                final Long movieId = params[0];

                RequestInterceptor interceptor = new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.API_KEY);
                    }
                };

                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint(TheMovieDb.BASE_URL)
                        .setLogLevel(RestAdapter.LogLevel.BASIC)
                        .setRequestInterceptor(interceptor)
                        .build();

                TheMovieDb apiRestAdapter = restAdapter.create(TheMovieDb.class);

                try {
                    mList = apiRestAdapter.movieReviews(movieId);

                } catch (RetrofitError err) {
                    Response r = err.getResponse();
                    if (r != null) {
                        Log.e(LOG_TAG, Integer.toString(r.getStatus()) + err.getMessage());
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                }

                return mList;
            }

            @Override
            protected void onPostExecute(ReviewList list) {
                // fill adapter with list from response
                if (list != null && list.getTotal_results() > 0) {
                    mReviewView.setAdapter(new ReviewAdapter(getActivity(), list.getResults()));
                    mReviewViewLayout.setVisibility(View.VISIBLE);
                } else {
                    mReviewViewLayout.setVisibility(View.GONE);
                }
            }
        }
    }
}
