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


    /**
     * 广告图片资源id
     */
    public int[] resIds;

    /**
     * 广告图片url数组
     */
    public String[] urls;

    public AdParams() {
    }

    protected AdParams(Parcel in) {
        this.resIds = in.createIntArray();
        this.urls = in.createStringArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.resIds);
        dest.writeStringArray(this.urls);
    }
}
