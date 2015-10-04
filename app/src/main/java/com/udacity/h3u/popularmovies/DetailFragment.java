package com.udacity.h3u.popularmovies;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.udacity.h3u.popularmovies.adapter.ReviewAdapter;
import com.udacity.h3u.popularmovies.adapter.VideoAdapter;
import com.udacity.h3u.popularmovies.provider.movie.MovieColumns;
import com.udacity.h3u.popularmovies.provider.movie.MovieCursor;
import com.udacity.h3u.popularmovies.provider.movie.MovieSelection;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 13/09/15.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String LOG_TAG = this.getClass().getSimpleName();
    private static final int LOADER_ID = 77;

    private Long mMovieId;
    private RelativeLayout mDetailView;
    private RecyclerView mReviewView;
    private LinearLayout mReviewViewLayout;
    private RecyclerView mVideoView;
    private LinearLayout mVideoViewLayout;
    private ImageButton mFavoriteButton;
    private TextView mTitleView;
    private TextView mRatingView;
    private TextView mReleaseDateView;
    private TextView mOverviewTextView;
    private ImageView mPosterView;
    private ImageView mBackdropView;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (null != mMovieId) {
            outState.putLong(TheMovieDb.MOVIE_KEY, mMovieId);
        }
        super.onSaveInstanceState(outState);
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
            mMovieId = savedInstanceState.getLong(TheMovieDb.MOVIE_KEY);
        } else {
            // get mMovie from arguments
            Bundle args = getArguments();
            if (null != args && args.containsKey(TheMovieDb.MOVIE_KEY)) {
                mMovieId = args.getLong(TheMovieDb.MOVIE_KEY);
            }
        }

        mDetailView = (RelativeLayout) rootView.findViewById(R.id.movie_detail_view);
        mFavoriteButton = (ImageButton) rootView.findViewById(R.id.movie_detail_button_favorite);

        mReviewView = (RecyclerView) rootView.findViewById(R.id.movie_detail_review_list);
        mReviewViewLayout = (LinearLayout) rootView.findViewById(R.id.movie_detail_reviews_layout);
        mVideoView = (RecyclerView) rootView.findViewById(R.id.movie_detail_trailer_list);
        mVideoViewLayout = (LinearLayout) rootView.findViewById(R.id.movie_detail_videos_layout);
        mTitleView = (TextView) rootView.findViewById(R.id.movie_detail_title);
        mRatingView = (TextView) rootView.findViewById(R.id.movie_detail_rating);
        mReleaseDateView = (TextView) rootView.findViewById(R.id.movie_detail_release_date);
        mOverviewTextView = (TextView) rootView.findViewById(R.id.movie_detail_overview);
        mPosterView = (ImageView) rootView.findViewById(R.id.movie_detail_poster_thumbnail);
        mBackdropView = (ImageView) rootView.findViewById(R.id.movie_detail_backdrop);

        setupRecyclerView(mReviewView);
        setupRecyclerView(mVideoView);

        if (null != mMovieId) {
            mDetailView.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    private View.OnClickListener favoriteButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if ("add" == mFavoriteButton.getTag()) {
                Util.addFavorite(getActivity(), mMovieId);
                Toast.makeText(getActivity(), getText(R.string.movie_detail_toggle_add),
                        Toast.LENGTH_SHORT).show();
                setButtonRemove();
            } else {
                Util.removeFavorite(getActivity(), mMovieId);
                Toast.makeText(getActivity(), getText(R.string.movie_detail_toggle_remove),
                        Toast.LENGTH_SHORT).show();
                setButtonAdd();
            }
        }
    };

    private void setButtonRemove() {
        mFavoriteButton.setImageResource(R.drawable.ic_remove_circle_white_48dp);
        mFavoriteButton.setTag("remove");
    }

    private void setButtonAdd() {
        mFavoriteButton.setImageResource(R.drawable.ic_add_circle_white_48dp);
        mFavoriteButton.setTag("add");
    }

    protected void fetchReviews(Movie movie) {
        FetchReviewListTask task = new FetchReviewListTask();
        task.execute(movie.getId());
    }

    protected void fetchVideos(Movie movie) {
        FetchVideoListTask task = new FetchVideoListTask();
        task.execute(movie.getId());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        if (null != mMovieId) {
            Uri movieUri = MovieColumns.CONTENT_URI;
            MovieSelection filter = new MovieSelection();
            filter.foreignId(mMovieId);

            return new CursorLoader(
                    getActivity(),
                    movieUri,
                    MovieColumns.ALL_COLUMNS,
                    filter.sel(), filter.args(), null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (!data.moveToFirst()) {
            return;
        }
        MovieCursor movieCursor = new MovieCursor(data);
        Movie movie = new Movie();
        movie.createBy(movieCursor);
        fillViews(movie);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, null, this);
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

    public class FetchVideoListTask extends AsyncTask<Long, Void, VideoList> {

        private final String LOG_TAG = this.getClass().getSimpleName();

        private VideoList mList;


        @Override
        protected VideoList doInBackground(Long... params) {

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
                mList = apiRestAdapter.movieVideos(movieId);

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
        protected void onPostExecute(VideoList list) {
            // fill adapter with list from response
            if (list != null && list.getResults().size() > 0) {
                mVideoView.setAdapter(new VideoAdapter(getActivity(), list.getResults()));
                mVideoViewLayout.setVisibility(View.VISIBLE);
            } else {
                mVideoViewLayout.setVisibility(View.GONE);
            }
        }
    }

    private void fillViews(Movie movie) {

        if (movie != null) {
            fetchVideos(movie);
            fetchReviews(movie);
            // fill all views with movie data
            // start with title
            mTitleView.setText(movie.getOriginal_title());

            // fill rating values
            mRatingView.setText(movie.getVote_average().toString()
                    + " " + getString(R.string.movie_detail_rating)
                    + " (" + movie.getVote_count() + ")");
            mRatingView.setContentDescription(
                    getText(R.string.movie_detail_rating_description_average)
                            + Float.toString(movie.getVote_average())
                            + getText(R.string.movie_detail_rating_description_max_rating)
                            + getText(R.string.movie_detail_rating_description_voting_count)
                            + Long.toString(movie.getVote_count())
            );

            // fill release date
            mReleaseDateView.setText(movie.getRelease_date());

            if (movie.getFavorite()) {
                setButtonRemove();
            } else {
                setButtonAdd();
            }
            mFavoriteButton.setOnClickListener(favoriteButtonClickListener);

            // fill overview (synopsis)
            mOverviewTextView.setText(movie.getOverview());
            mOverviewTextView.setContentDescription(
                    getText(R.string.movie_detail_overview_description)
                            + movie.getOriginal_title() + ": " + movie.getOverview());

            // fill poster view
            Glide.with(getActivity())
                    .load(TheMovieDb.IMAGE_URL + "/w154" + movie.getPoster_path())
                    .into(mPosterView);
            mPosterView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mPosterView.setContentDescription(getText(R.string.movie_detail_thumbnail_description) + " " + movie.getOriginal_title());

            // fill backdrop view
            Glide.with(getActivity())
                    .load(TheMovieDb.IMAGE_URL + "/w780" + movie.getBackdrop_path())
                    .into(mBackdropView);
            mBackdropView.setContentDescription(getText(R.string.movie_detail_backdrop_description) + " " + movie.getOriginal_title());
            mBackdropView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    public void switchInvisible() {
        mDetailView.setVisibility(View.INVISIBLE);
    }
}
