package com.mylhyl.circledialog;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;

final class SystemBarConfig {

    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

    private final int mStatusBarHeight;
    private final int mScreenWidth;
    private final int mScreenHeight;

    public SystemBarConfig(Activity activity) {
        Resources res = activity.getResources();
        mStatusBarHeight = getInternalDimensionSize(res, STATUS_BAR_HEIGHT_RES_NAME);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
    }

    //通过此方法获取资源对应的像素值
    private int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
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

    /**
     * 获取status bar状态栏高度
     *
     * @return 状态栏高度的像素值
     */
    public int getStatusBarHeight() {
        return mStatusBarHeight;
    }

}