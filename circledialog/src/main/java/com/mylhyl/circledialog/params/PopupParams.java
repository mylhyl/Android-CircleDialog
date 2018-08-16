package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hupei on 2018/8/9 20:04.
 */
public class PopupParams extends ItemsParams {
    public static final int DIRECTION_LEFT = 680;
    public static final int DIRECTION_TOP = 681;
    public static final int DIRECTION_RIGHT = 682;
    public static final int DIRECTION_BOTTOM = 683;

    public static final int GRAVITY_LEFT = 685;
    public static final int GRAVITY_TOP = 686;
    public static final int GRAVITY_RIGHT = 687;
    public static final int GRAVITY_BOTTOM = 688;
    public static final int GRAVITY_CENTER = 689;
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
    public @ArrowDirection
    int arrowDirection = DIRECTION_TOP;
    /**
     *
     */
    public @ArrowGravity
    int arrowGravity = GRAVITY_RIGHT;

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

    @IntDef({GRAVITY_LEFT, GRAVITY_TOP, GRAVITY_RIGHT, GRAVITY_BOTTOM, GRAVITY_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrowGravity {
    }
}
