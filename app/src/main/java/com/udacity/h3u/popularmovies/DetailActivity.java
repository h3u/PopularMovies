package com.udacity.h3u.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle arguments = new Bundle();
        arguments.putLong(
                TheMovieDb.MOVIE_KEY, getIntent().getLongExtra(TheMovieDb.MOVIE_KEY, -1));

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit();

    }
}
