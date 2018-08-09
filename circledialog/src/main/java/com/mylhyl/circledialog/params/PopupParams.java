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
     * 三角的位置
     * {@linkplain Gravity#LEFT Gravity.LEFT}
     * {@linkplain Gravity#TOP Gravity.TOP}
     * {@linkplain Gravity#RIGHT Gravity.RIGHT}
     * {@linkplain Gravity#BOTTOM Gravity.BOTTOM}
     */
    public int arrowGravity;

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
}
