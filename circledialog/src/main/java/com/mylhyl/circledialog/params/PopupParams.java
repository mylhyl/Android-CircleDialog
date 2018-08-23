package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.support.annotation.IntDef;
import android.view.Gravity;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hupei on 2018/8/9 20:04.
 */
public class PopupParams extends ItemsParams {
    public static final int DIRECTION_LEFT = Gravity.LEFT;
    public static final int DIRECTION_TOP = Gravity.TOP;
    public static final int DIRECTION_RIGHT = Gravity.RIGHT;
    public static final int DIRECTION_BOTTOM = Gravity.BOTTOM;

    public static final int GRAVITY_LEFT = Gravity.LEFT;
    public static final int GRAVITY_TOP = Gravity.TOP;
    public static final int GRAVITY_RIGHT = Gravity.RIGHT;
    public static final int GRAVITY_BOTTOM = Gravity.BOTTOM;
    public static final int GRAVITY_CENTER_HORIZONTAL = Gravity.CENTER_HORIZONTAL;
    public static final int GRAVITY_CENTER_VERTICAL = Gravity.CENTER_VERTICAL;
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
    /**
     * 三角的方向
     * {@linkplain ArrowDirection#DIRECTION_LEFT }
     * {@linkplain ArrowDirection#DIRECTION_TOP }
     * {@linkplain ArrowDirection#DIRECTION_RIGHT }
     * {@linkplain ArrowDirection#DIRECTION_BOTTOM }
     */
    public @ArrowDirection int arrowDirection;
    /**
     * 三角的位置
     * {@linkplain ArrowGravity#GRAVITY_LEFT }
     * {@linkplain ArrowGravity#GRAVITY_CENTER_HORIZONTAL }
     * {@linkplain ArrowGravity#GRAVITY_RIGHT }
     * <p>
     * {@linkplain ArrowGravity#GRAVITY_TOP }
     * {@linkplain ArrowGravity#GRAVITY_CENTER_VERTICAL }
     * {@linkplain ArrowGravity#GRAVITY_BOTTOM }
     */
    public @ArrowGravity
    int arrowGravity;
    public int arrowOffSet;//三角偏移量
    public View anchor;

    public PopupParams() {
    }

    protected PopupParams(Parcel in) {
        super(in);
        this.arrowDirection = in.readInt();
        this.arrowGravity = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.arrowDirection);
        dest.writeInt(this.arrowGravity);
    }

    @IntDef({DIRECTION_LEFT, DIRECTION_TOP, DIRECTION_RIGHT, DIRECTION_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrowDirection {
    }

    @IntDef({GRAVITY_LEFT, GRAVITY_TOP, GRAVITY_RIGHT, GRAVITY_BOTTOM, GRAVITY_CENTER_HORIZONTAL, GRAVITY_CENTER_VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrowGravity {
    }
}
