package com.mylhyl.circledialog.view.listener;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by hupei on 2019/6/6 17:02.
 */
public abstract class OnAdPageChangeListener {

    public void onPageScrolled(Context context, ImageView imageView, String url, int position, float positionOffset,
                               int positionOffsetPixels) {
    }

    public abstract void onPageSelected(Context context, ImageView imageView, String url, int position);

    public void onPageScrollStateChanged(int state) {
    }
}
