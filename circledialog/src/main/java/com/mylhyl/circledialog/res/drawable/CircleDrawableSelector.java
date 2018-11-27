package com.mylhyl.circledialog.res.drawable;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by hupei on 2017/3/30.
 */

public class CircleDrawableSelector extends StateListDrawable {

    public CircleDrawableSelector(int backgroundColor, int backgroundColorPress) {
        this(backgroundColor, backgroundColorPress, 0, 0, 0, 0);
    }

    public CircleDrawableSelector(int backgroundColor, int backgroundColorPress, int leftTopRadius
            , int rightTopRadius, int rightBottomRadius, int leftBottomRadius) {
        //按下
        ShapeDrawable drawablePress = new ShapeDrawable(DrawableHelper.getRoundRectShape(
                leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius));
        drawablePress.getPaint().setColor(backgroundColorPress);
        //默认
        ShapeDrawable defaultDrawable = new ShapeDrawable(DrawableHelper.getRoundRectShape(
                leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius));
        defaultDrawable.getPaint().setColor(backgroundColor);

        addState(new int[]{android.R.attr.state_pressed}, drawablePress);
        addState(new int[]{-android.R.attr.state_pressed}, defaultDrawable);
    }
}
