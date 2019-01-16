package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 广告参数
 * Created by hupei on 2019/1/11 11:04.
 */
public class AdParams implements Parcelable {
    public static final Creator<AdParams> CREATOR = new Creator<AdParams>() {
        @Override
        public AdParams createFromParcel(Parcel source) {
            return new AdParams(source);
        }

        @Override
        public AdParams[] newArray(int size) {
            return new AdParams[size];
        }
    };
    public int closeResId;
    public int closeSize;
    /**
     * int left, int top, int right, int bottom
     */
    public int[] closeMargins;
    /**
     * 广告图片资源id
     */
    public int[] imageResIds;
    /**
     * 广告图片url数组
     */
    public String[] urls;

    public AdParams() {
    }

    protected AdParams(Parcel in) {
        this.closeResId = in.readInt();
        this.closeSize = in.readInt();
        this.closeMargins = in.createIntArray();
        this.imageResIds = in.createIntArray();
        this.urls = in.createStringArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.closeResId);
        dest.writeInt(this.closeSize);
        dest.writeIntArray(this.closeMargins);
        dest.writeIntArray(this.imageResIds);
        dest.writeStringArray(this.urls);
    }
}
