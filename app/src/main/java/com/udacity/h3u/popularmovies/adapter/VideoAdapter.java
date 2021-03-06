package com.udacity.h3u.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.h3u.popularmovies.R;
import com.udacity.h3u.popularmovies.Util;
import com.udacity.h3u.popularmovies.Video;

import java.util.List;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 06/09/15.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context mContext;
    private List<Video> mItems;

    public VideoAdapter(Context context, List<Video> list) {
        mContext = context;
        mItems = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Video mVideo;

        public final View mView;
        public final TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.video_item_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.video_list_item, null, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder viewHolder, int i) {
        final Video item = mItems.get(i);
        viewHolder.mVideo = item;
        viewHolder.mTextView.setText(item.getName());

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Context context = v.getContext();

                if ("YouTube".equals(item.getSite())) {
                    Util.watchYoutubeVideo(context, item.getKey());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
