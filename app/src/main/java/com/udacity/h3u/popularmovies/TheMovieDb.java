package com.udacity.h3u.popularmovies;

import retrofit.http.GET;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 01/08/15.
 * The needed retrofit interface, see http://square.github.io/retrofit/
 */
public interface TheMovieDb {

    // the base url for all api requests
    public String BASE_URL = "http://api.themoviedb.org/3";

    // the base url for all image requests
    public String IMAGE_URL = "http://image.tmdb.org/t/p";

    // minimum count of votes (parameter)
    public String MINIMUM_VOTES = "50";

    // keys to identify the parcelable objects
    public String MOVIE_LIST_KEY = "MOVIE_LIST";
    public String MOVIE_KEY = "MOVIE";

    // definition of discover movies endpoint
    @GET("/discover/movie")
    MovieList discoverMovies();

}
