package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.res.values.CircleColor;

/**
 * 分隔线，默认垂直线
 * Created by hupei on 2017/3/30.
 */
final class DividerView extends View {
    static final int DEFAULT_ORIENTATION = LinearLayout.VERTICAL;
    private int mOrientation = DEFAULT_ORIENTATION;

    public DividerView(Context context) {
        this(context, DEFAULT_ORIENTATION);
    }

    public DividerView(Context context, int orientation) {
        this(context, orientation, 1);
    }

    public DividerView(Context context, int orientation, int dividerHeight) {
        super(context);
        this.mOrientation = orientation;
        setBackgroundColor(CircleColor.divider);
        if (mOrientation == DEFAULT_ORIENTATION)
            setLayoutParams(new LinearLayout.LayoutParams(dividerHeight, LinearLayout.LayoutParams.MATCH_PARENT));
        else
            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dividerHeight));
    }

    public void setBgColor(int bgColor) {
        setBackgroundColor(bgColor);
    }
}
