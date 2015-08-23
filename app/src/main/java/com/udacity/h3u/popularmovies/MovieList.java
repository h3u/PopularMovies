package com.udacity.h3u.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * MovieList
 * Represents list Object of /discover/movies response from TheMovieDb
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 02/08/15.
 */
public class MovieList implements Parcelable {
    private Integer page;
    @SerializedName("results")
    private List<Movie> results;
    private Integer total_pages;
    private Integer total_results;

    MovieList() {}

    public List<Movie> getResults() {
        return results;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.page);
        dest.writeTypedList(results);
        dest.writeValue(this.total_pages);
        dest.writeValue(this.total_results);
    }

    protected MovieList(Parcel in) {
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(Movie.CREATOR);
        this.total_pages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.total_results = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieList> CREATOR = new Parcelable.Creator<MovieList>() {
        public MovieList createFromParcel(Parcel source) {
            return new MovieList(source);
        }

        public MovieList[] newArray(int size) {
            return new MovieList[size];
        }
    };
}
