package com.udacity.h3u.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Movie
 * Represents a movie object an element in the response of /discover/movies
 * fetched from API of TheMovieDb
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 02/08/15.
 */
public class Movie implements Parcelable {
    private Long id;
    private Boolean adult;
    private Integer[] genre_ids;
    private String backdrop_path;
    private String original_language;
    private String original_title;
    private String overview;
    private String release_date;
    private String poster_path;
    private Float popularity;
    private String title;
    private Boolean video;
    private Float vote_average;
    private Long vote_count;

    Movie() {}

    Movie(Long id, String title) {
        this.original_title = title;
        this.id = id;
        this.poster_path = "movie_placeholder";
    }

    public Long getId() {
        return id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public Float getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Float getPopularity() {
        return popularity;
    }

    public Long getVote_count() {
        return vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.poster_path);
        dest.writeValue(this.popularity);
        dest.writeString(this.title);
        dest.writeValue(this.video);
        dest.writeValue(this.vote_average);
        dest.writeValue(this.vote_count);
    }

    protected Movie(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.backdrop_path = in.readString();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.poster_path = in.readString();
        this.popularity = (Float) in.readValue(Float.class.getClassLoader());
        this.title = in.readString();
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.vote_average = (Float) in.readValue(Float.class.getClassLoader());
        this.vote_count = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
