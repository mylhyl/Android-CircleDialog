package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.view.Gravity;

/**
 * Created by hupei on 2018/8/9 20:04.
 */
public class PopupParams extends ItemsParams {
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
     * {@linkplain android.view.Gravity#LEFT }
     * {@linkplain android.view.Gravity#TOP }
     * {@linkplain android.view.Gravity#RIGHT }
     * {@linkplain android.view.Gravity#BOTTOM }
     */
    public int arrowDirection = Gravity.TOP;
    /**
     * 三角的位置
     * {@linkplain android.view.Gravity#LEFT }
     * {@linkplain android.view.Gravity#CENTER_HORIZONTAL }
     * {@linkplain android.view.Gravity#RIGHT }
     * <p>
     * {@linkplain android.view.Gravity#TOP }
     * {@linkplain android.view.Gravity#CENTER_VERTICAL }
     * {@linkplain android.view.Gravity#BOTTOM }
     */
    public int arrowGravity = Gravity.RIGHT;

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
}
