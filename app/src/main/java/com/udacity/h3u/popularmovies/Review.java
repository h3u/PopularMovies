package com.udacity.h3u.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Review
 * Represents a review object an element in the response of /movie/:id/reviews
 * fetched from API of TheMovieDb
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 2015-09-05.
 */
public class Review implements Parcelable {
    private String id;
    private String author;
    private String content;
    private String url;

    Review() {}

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    protected Review(Parcel in) {
        this.id = in.readString();
        this.author = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
