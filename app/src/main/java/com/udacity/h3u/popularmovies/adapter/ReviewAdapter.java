package com.udacity.h3u.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.h3u.popularmovies.R;
import com.udacity.h3u.popularmovies.Review;
import com.udacity.h3u.popularmovies.ReviewActivity;
import com.udacity.h3u.popularmovies.TheMovieDb;

import java.util.List;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 06/09/15.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context mContext;
    private List<Review> mItems;

    public ReviewAdapter(Context context, List<Review> list) {
        mContext = context;
        mItems = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Review mReview;

        public final View mView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.review_item_author);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.review_list_item, null, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder viewHolder, int i) {
        final Review item = mItems.get(i);
        viewHolder.mReview = item;
        viewHolder.mTextView.setText(item.getAuthor());


        viewHolder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent intent = new Intent(context, ReviewActivity.class);
                intent.putExtra(TheMovieDb.REVIEW_KEY, item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
