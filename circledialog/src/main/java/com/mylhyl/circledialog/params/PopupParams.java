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

    public static final int ARROW_LEFT_TOP = 151;
    public static final int ARROW_LEFT_BOTTOM = 183;
    public static final int ARROW_LEFT_CENTER = 119;

    public static final int ARROW_TOP_LEFT = 351;
    public static final int ARROW_TOP_RIGHT = 353;
    public static final int ARROW_TOP_CENTER = 349;

    public static final int ARROW_RIGHT_TOP = 553;
    public static final int ARROW_RIGHT_BOTTOM = 585;
    public static final int ARROW_RIGHT_CENTER = 521;

    public static final int ARROW_BOTTOM_LEFT = 783;
    public static final int ARROW_BOTTOM_RIGHT = 785;
    public static final int ARROW_BOTTOM_CENTER = 781;

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

    public @ArrowGravity
    int arrowGravity;
    public int arrowOffSet;//三角偏移量
    public View anchor;

    public PopupParams() {
    }

    protected PopupParams(Parcel in) {
        super(in);
        this.arrowGravity = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.arrowGravity);
    }

    @IntDef({ARROW_LEFT_TOP, ARROW_LEFT_BOTTOM, ARROW_LEFT_CENTER
            , ARROW_TOP_LEFT, ARROW_TOP_RIGHT, ARROW_TOP_CENTER
            , ARROW_RIGHT_TOP, ARROW_RIGHT_BOTTOM, ARROW_RIGHT_CENTER
            , ARROW_BOTTOM_LEFT, ARROW_BOTTOM_RIGHT, ARROW_BOTTOM_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ArrowGravity {
    }
}
