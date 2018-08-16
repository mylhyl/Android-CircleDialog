package com.mylhyl.circledialog.res.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mylhyl.circledialog.params.PopupParams;

/**
 * Created by hupei on 2018/8/3 8:58.
 */
public class TriangleArrowDrawable extends Drawable {

    private final int arrowDirection;
    private final int bgColor;

    public TriangleArrowDrawable(@PopupParams.ArrowDirection int arrowDirection, int bgColor) {
        this.arrowDirection = arrowDirection;
        this.bgColor = bgColor;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.FILL);
        Path path = createPath();
        canvas.drawPath(path, paint);
    }

    private Path createPath() {
        Rect bound = getBounds();
        Path path = new Path();
        switch (arrowDirection) {
            case PopupParams.DIRECTION_LEFT:
                path.moveTo(bound.right / 2, bound.bottom / 2);
                path.lineTo(bound.right, 0);
                path.lineTo(bound.right, bound.bottom);
                break;
            case PopupParams.DIRECTION_TOP:
                path.moveTo(bound.right / 2, bound.bottom / 2);
                path.lineTo(0, bound.bottom);
                path.lineTo(bound.right, bound.bottom);
                break;
            case PopupParams.DIRECTION_RIGHT:
                path.moveTo(bound.right / 2, bound.bottom / 2);
                path.lineTo(0, 0);
                path.lineTo(0, bound.bottom);
                break;
            case PopupParams.DIRECTION_BOTTOM:
                path.moveTo(bound.right / 2, bound.bottom / 2);
                path.lineTo(0, 0);
                path.lineTo(bound.right, 0);
                break;
        }
        path.close();
        return path;

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
