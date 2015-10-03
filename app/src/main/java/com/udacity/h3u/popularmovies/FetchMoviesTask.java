package com.udacity.h3u.popularmovies;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.udacity.h3u.popularmovies.provider.movie.MovieContentValues;
import com.udacity.h3u.popularmovies.provider.movie.MovieSelection;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 30/09/15.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, Void> {

    private final String LOG_TAG = this.getClass().getSimpleName();

    private Context mContext;

    public FetchMoviesTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(String... params) {

        final String sort_by = params[0];

        RequestInterceptor interceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addEncodedQueryParam("api_key", BuildConfig.API_KEY);
                request.addEncodedQueryParam("vote_count.gte", TheMovieDb.MINIMUM_VOTES);
                request.addEncodedQueryParam("sort_by", sort_by + ".desc");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(TheMovieDb.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setRequestInterceptor(interceptor)
                .build();

        TheMovieDb apiRestAdapter = restAdapter.create(TheMovieDb.class);

        try {
            MovieList movieList = apiRestAdapter.discoverMovies();

            // store result via content provider
            for (Movie movie : movieList.getResults()) {

                MovieContentValues movieItem = new MovieContentValues();
                movieItem
                        .putForeignId(movie.getId())
                        .putBackdropPath(movie.getBackdrop_path())
                        .putOriginalTitle(movie.getOriginal_title())
                        .putTitle(movie.getOriginal_title())
                        .putOverview(movie.getOverview())
                        .putPopularity(movie.getPopularity())
                        .putPosterPath(movie.getPoster_path())
                        .putReleaseDate(movie.getRelease_date())
                        .putVoteAverage(movie.getVote_average())
                        .putVoteCount(movie.getVote_count());

                // Is there an existing movie than refresh attributes
                if (Util.movieExist(mContext, movie)) {
                    MovieSelection filter = new MovieSelection();
                    filter.foreignId(movie.getId());

                    mContext.getContentResolver().update(
                            movieItem.uri(), movieItem.values(), filter.sel(), filter.args());
                } else {
                    // insert movie
                    movieItem.putFavorite(false);
                    mContext.getContentResolver().insert(movieItem.uri(), movieItem.values());
                }

            }

        } catch (RetrofitError err) {
            Response r = err.getResponse();
            if (r != null) {
                Log.e(LOG_TAG, Integer.toString(r.getStatus()) + err.getMessage());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return null;
    }
}
