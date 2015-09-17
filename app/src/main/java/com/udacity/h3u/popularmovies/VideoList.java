package com.udacity.h3u.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * VideoList
 * Represents list Object of /movies/:id/videos response from TheMovieDb
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 2015-09-05.
 */
public class VideoList {
    private Integer id;
    @SerializedName("results")
    private List<Video> results;

    VideoList() {}

    public List<Video> getResults() {
        return results;
    }
}
