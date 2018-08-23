package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.support.annotation.IntDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hupei on 2018/8/9 20:04.
 */
public class PopupParams extends ItemsParams {

    public static final int TRIANGLE_LEFT_TOP = 151;
    public static final int TRIANGLE_LEFT_BOTTOM = 183;
    public static final int TRIANGLE_LEFT_CENTER = 119;

    public static final int TRIANGLE_TOP_LEFT = 351;
    public static final int TRIANGLE_TOP_RIGHT = 353;
    public static final int TRIANGLE_TOP_CENTER = 349;

    public static final int TRIANGLE_RIGHT_TOP = 553;
    public static final int TRIANGLE_RIGHT_BOTTOM = 585;
    public static final int TRIANGLE_RIGHT_CENTER = 521;

    public static final int TRIANGLE_BOTTOM_LEFT = 783;
    public static final int TRIANGLE_BOTTOM_RIGHT = 785;
    public static final int TRIANGLE_BOTTOM_CENTER = 781;

    public static final Creator<PopupParams> CREATOR = new Creator<PopupParams>() {
        @Override
        public PopupParams createFromParcel(Parcel source) {
            return new PopupParams(source);
        }

        @Override
        public PopupParams[] newArray(int size) {
            return new PopupParams[size];
        }
    };

    public @TriangleGravity
    int triangleGravity;
    public int triangleOffSet;//三角偏移量
    /**
     * 三角大小 [width,height]
     */
    public int[] triangleSize;
    /**
     * 三角显示
     */
    public boolean triangleShow = true;
    public View anchorView;

    public PopupParams() {
    }

    protected PopupParams(Parcel in) {
        super(in);
        this.triangleGravity = in.readInt();
        this.triangleOffSet = in.readInt();
        this.triangleSize = in.createIntArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.triangleGravity);
        dest.writeInt(this.triangleOffSet);
        dest.writeIntArray(this.triangleSize);
    }

    @IntDef({TRIANGLE_LEFT_TOP, TRIANGLE_LEFT_BOTTOM, TRIANGLE_LEFT_CENTER
            , TRIANGLE_TOP_LEFT, TRIANGLE_TOP_RIGHT, TRIANGLE_TOP_CENTER
            , TRIANGLE_RIGHT_TOP, TRIANGLE_RIGHT_BOTTOM, TRIANGLE_RIGHT_CENTER
            , TRIANGLE_BOTTOM_LEFT, TRIANGLE_BOTTOM_RIGHT, TRIANGLE_BOTTOM_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TriangleGravity {
    }
}
