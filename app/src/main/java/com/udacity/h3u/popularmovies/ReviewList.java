package com.udacity.h3u.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ReviewList
 * Represents list Object of /movies/:id/reviews response from TheMovieDb
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 2015-09-05.
 */
public class ReviewList implements Parcelable {
    private Integer page;
    @SerializedName("results")
    private List<Review> results;
    private Integer total_pages;
    private Integer total_results;

    ReviewList() {}

    public List<Review> getResults() {
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

    protected ReviewList(Parcel in) {
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(Review.CREATOR);
        this.total_pages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.total_results = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<ReviewList> CREATOR = new Creator<ReviewList>() {
        public ReviewList createFromParcel(Parcel source) {
            return new ReviewList(source);
        }

        public ReviewList[] newArray(int size) {
            return new ReviewList[size];
        }
    };
}
