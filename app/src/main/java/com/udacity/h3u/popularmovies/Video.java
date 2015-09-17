package com.udacity.h3u.popularmovies;

/**
 * Video
 * Represents a video element in the response of /movie/:id/videos
 * fetched from API of TheMovieDb
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 2015-09-05.
 */
public class Video {
    private String id;
    private String iso_639_1;
    private String key;
    private String name;
    private String site;
    private String size;
    private String type;

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getId() {
        return id;
    }
}
