package com.mylhyl.circledialog.scale;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by hupei on 2016/3/8 17:19.
 */
public class ScaleUtils {
    public static int scaleValue(int val) {
        return (int) (val * ScaleLayoutConfig.getInstance().getScale());
    }

    public static int[] getRealScreenSize(Context context) {

        int[] size = new int[2];

        WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);

        // since SDK_INT = 1;
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try {
                widthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(d);
                heightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        } else if (Build.VERSION.SDK_INT >= 17) {// includes window decorations (statusbar bar/menu bar)
            Point realSize = new Point();
            d.getRealSize(realSize);
            widthPixels = realSize.x;
            heightPixels = realSize.y;
        }

        size[0] = widthPixels;
        size[1] = heightPixels;
        return size;
    }


    public static double getDevicePhysicalSize(Context context) {
        int[] size = getRealScreenSize(context);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double x = Math.pow(size[0] / dm.xdpi, 2);
        double y = Math.pow(size[1] / dm.ydpi, 2);

        return Math.sqrt(x + y);
    }
}
