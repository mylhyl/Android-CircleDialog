package com.mylhyl.circledialog.engine.impl;

import android.content.Context;
import android.widget.ImageView;

import com.mylhyl.circledialog.engine.ImageLoadEngine;
import com.squareup.picasso.Picasso;

/**
 * Created by hupei on 2019/1/14 15:34.
 */
public final class PicassoImageLoadEngine implements ImageLoadEngine {
    @Override
    public void loadImage(Context context, ImageView imageView, String url) {
        Picasso.with(context)
                .load(url)
                .priority(Picasso.Priority.HIGH)
                .centerInside()
                .into(imageView);
    }
}
