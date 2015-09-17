package com.udacity.h3u.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Uli Wucherer (u.wucherer@gmail.com) on 13/09/15.
 */
public class Util {

    public static void watchYoutubeVideo(Context context, String id) {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            context.startActivity(intent);

        } catch (ActivityNotFoundException ex) {

            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            context.startActivity(intent);

        }
    }
}
