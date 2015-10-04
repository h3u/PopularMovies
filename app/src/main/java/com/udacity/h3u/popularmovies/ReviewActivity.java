package com.udacity.h3u.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_review, new ReviewFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class ReviewFragment extends android.support.v4.app.Fragment {

        public ReviewFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_review, container, false);

            // The detail Activity called via intent.
            Intent intent = getActivity().getIntent();

            if (intent != null) {

                // get review from intent
                Review review = (Review) intent.getExtras().getParcelable(TheMovieDb.REVIEW_KEY);
                if (null != review) {
                    TextView authorTextView = (TextView) rootView.findViewById(R.id.movie_detail_review_author);
                    authorTextView.setText(getText(R.string.review_author_start)
                            + " " + review.getAuthor());
                    TextView contentTextView = (TextView) rootView.findViewById(R.id.movie_detail_review_content);
                    contentTextView.setText(review.getContent());
                    contentTextView.setContentDescription(review.getContent());
                }
            }

            return rootView;
        }
    }
}
