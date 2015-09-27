package com.udacity.h3u.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.udacity.h3u.popularmovies.adapter.MovieAdapter;

import java.util.ArrayList;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Fragment containing a grid of movie posters.
 */
public class MovieGridFragment extends GridFragment implements GridFragment.Updateable {

    private MovieAdapter movieAdapter;
    private MovieList movieList;

    public MovieGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        // check for saved movie list object
        if (savedInstanceState == null || !savedInstanceState.containsKey(TheMovieDb.MOVIE_LIST_KEY)) {
            // not found -> create new list
            update();
        } else {
            // found -> re-create from instance state
            movieList = savedInstanceState.getParcelable(TheMovieDb.MOVIE_LIST_KEY);
            movieAdapter.clear();
            for (Movie el : movieList.getResults()) {
                movieAdapter.add(el);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(TheMovieDb.MOVIE_LIST_KEY, movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_grid, container, false);

        GridView gridview = (GridView) rootView.findViewById(R.id.grid_view_movies);
        gridview.setAdapter(movieAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {

                Movie movie = (Movie) adapterView.getItemAtPosition(position);
                startDetailWithIntent(movie);
            }
        });

        return rootView;
    }

    // helper method that fetch movies with sorting value from shared preferences
    public void update() {

        FetchMovieListTask task = new FetchMovieListTask();

        String sort_by = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(getString(R.string.pref_sortby_key), getString(R.string.pref_sortby_value_default));
        task.execute(sort_by);
    }

    public class FetchMovieListTask extends AsyncTask<String, Void, MovieList> {

        private final String LOG_TAG = this.getClass().getSimpleName();

        private MovieList mList;


        @Override
        protected MovieList doInBackground(String... params) {

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
                mList = apiRestAdapter.discoverMovies();

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
        protected void onPostExecute(MovieList list) {
            // fill adapter with list from response
            if (list != null && list.getTotal_results() > 0) {
                movieList = list;
                movieAdapter.clear();
                for (Movie el : movieList.getResults()) {
                    movieAdapter.add(el);
                }
            }
        }
    }
}
