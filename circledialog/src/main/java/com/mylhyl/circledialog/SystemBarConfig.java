package com.mylhyl.circledialog;

import android.app.Activity;
import android.util.DisplayMetrics;

class SystemBarConfig {

    private final int mScreenWidth;
    private final int mScreenHeight;

    public SystemBarConfig(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
    }

    public int[] getScreenSize() {
        return new int[]{mScreenWidth, mScreenHeight};
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

}