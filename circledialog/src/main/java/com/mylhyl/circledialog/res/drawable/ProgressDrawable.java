package com.mylhyl.circledialog.res.drawable;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;

/**
 * 进度条背景，不提供内置水平样式
 * Created by hupei on 2017/3/29.
 */
@Deprecated
public final class ProgressDrawable {
    private static final int colorsBg[] = {0xffBEBEBE, 0xffF5F5F5};
    private static final int colorsSecondaryProgress[] = {0xff85B0E9, 0xff165CBC};
    private static final int colorsProgress[] = {0xff85B0E9, 0xff165CBC};
    private LayerDrawable mLayerDrawable;

    public ProgressDrawable() {
        GradientDrawable background = createBackground();
        ClipDrawable secondaryProgress = createSecondaryProgress();
        ClipDrawable progress = createProgress();
        Drawable[] layers = {background, secondaryProgress, progress};
        mLayerDrawable = new LayerDrawable(layers);
        mLayerDrawable.setId(0, android.R.id.background);
        mLayerDrawable.setId(1, android.R.id.secondaryProgress);
        mLayerDrawable.setId(2, android.R.id.progress);
    }

    private GradientDrawable createBackground() {
/*     <item android:id="@android:id/background">
            <shape>
                <corners android:radius="5dip" />

                <gradient
                android:angle="270"
                android:centerY="0.75"
                android:endColor="#F5F5F5"
                android:startColor="#BEBEBE" />
            </shape>
        </item>*/

        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colorsBg);
        drawable.setCornerRadius(5);
        drawable.setGradientCenter(0.5f, 0.75f);
        return drawable;
    }

    private ClipDrawable createSecondaryProgress() {
/*      <item android:id="@android:id/secondaryProgress">
            <clip>
                <shape>
                    <corners android:radius="0dip" />

                    <gradient
                    android:angle="270"
                    android:centerY="0.75"
                    android:endColor="#165CBC"
                    android:startColor="#85B0E9" />
                    </shape>
            </clip>
        </item>*/
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                colorsSecondaryProgress);
        drawable.setGradientCenter(0.5f, 0.75f);
        ClipDrawable clipDrawable = new ClipDrawable(drawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        return clipDrawable;
    }

    private ClipDrawable createProgress() {
/*      <item android:id="@android:id/progress">
            <clip>
                <shape>
                    <corners android:radius="5dip" />

                    <gradient
                    android:angle="270"
                    android:centerY="0.75"
                    android:endColor="#165CBC"
                    android:startColor="#85B0E9" />
                </shape>
            </clip>
        </item>*/

        //android:angle 默认是Orientation.TOP_BOTTOM，见源码 GradientDrawableGradient_angle 处
        //见源码 updateGradientDrawableGradient
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colorsProgress);
        drawable.setCornerRadius(5);//android:radius
        drawable.setGradientCenter(0.5f, 0.75f);//x默认是0.5，android:centerY
        ClipDrawable clipDrawable = new ClipDrawable(drawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        return clipDrawable;
    }

    public LayerDrawable getLayerDrawable() {
        return mLayerDrawable;
    }
}
