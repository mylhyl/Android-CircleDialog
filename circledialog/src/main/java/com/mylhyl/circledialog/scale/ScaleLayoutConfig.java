package com.mylhyl.circledialog.scale;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by hupei on 2016/3/8 17:32.
 */
public class ScaleLayoutConfig {
    private static ScaleLayoutConfig sInstance;

    private static final String KEY_DESIGN_WIDTH = "design_width";
    private static final String KEY_DESIGN_HEIGHT = "design_height";

    private int mScreenWidth;
    private int mScreenHeight;

    private int mDesignWidth = 1080;
    private int mDesignHeight = 1920;

    private float mScale;

    private ScaleLayoutConfig() {
    }

    public static void init(Context context) {
        if (sInstance == null) {
            sInstance = new ScaleLayoutConfig();
            sInstance.initInternal(context, new ScaleAdapter(context));
        }
    }

    public static ScaleLayoutConfig getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Must init before using.");
        }
        return sInstance;
    }

    private void initInternal(Context context, ScaleAdapter scaleAdapter) {
        getMetaData(context);
        checkParams();
        int[] size = ScaleUtils.getRealScreenSize(context);
        mScreenWidth = size[0];
        mScreenHeight = size[1];

        if (mScreenWidth > mScreenHeight) {//横屏状态下，宽高互换，按竖屏模式计算scale
            mScreenWidth = mScreenWidth + mScreenHeight;
            mScreenHeight = mScreenWidth - mScreenHeight;
            mScreenWidth = mScreenWidth - mScreenHeight;
        }

        float deviceScale = (float) mScreenHeight / mScreenWidth;
        float designScale = (float) mDesignHeight / mDesignWidth;
        if (deviceScale <= designScale) {//高宽比小于等于标准比（较标准屏宽一些），以高为基准计算scale（以短边计算），否则以宽为基准计算scale
            mScale = (float) mScreenHeight / mDesignHeight;
        } else {
            mScale = (float) mScreenWidth / mDesignWidth;
        }

        if (scaleAdapter != null) {
            mScale = scaleAdapter.adapt(mScale, mScreenWidth, mScreenHeight);
        }
    }

    private void getMetaData(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(context
                    .getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null
                    && applicationInfo.metaData.containsKey(KEY_DESIGN_WIDTH)
                    && applicationInfo.metaData.containsKey(KEY_DESIGN_HEIGHT)) {
                mDesignWidth = (int) applicationInfo.metaData.get(KEY_DESIGN_WIDTH);
                mDesignHeight = (int) applicationInfo.metaData.get(KEY_DESIGN_HEIGHT);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
    }

    private void checkParams() {
        if (mDesignHeight <= 0 || mDesignWidth <= 0) {
            throw new RuntimeException(
                    "you must set " + KEY_DESIGN_WIDTH + " and " + KEY_DESIGN_HEIGHT + " > 0");
        }
    }

    public float getScale() {
        return mScale;
    }
}
