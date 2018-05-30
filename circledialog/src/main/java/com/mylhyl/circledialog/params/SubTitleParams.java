package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * 副标题参数
 * Created by hupei on 2018/4/12.
 */
public class SubTitleParams implements Parcelable {
    /**
     * 标题
     */
    public String text;
    /**
     * 内间距 [left, top, right, bottom]
     */
    public int[] padding = {50, 0, 50, 50};
    /**
     * 标题高度
     */
    public int height;
    /**
     * 标题字体大小
     */
    public int textSize = CircleDimen.SUBTITLE_TEXT_SIZE;
    /**
     * 标题字体颜色
     */
    public int textColor = CircleColor.subtitle;
    /**
     * 标题背景颜色
     */
    public int backgroundColor;

    public int gravity = Gravity.CENTER;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeInt(this.height);
        dest.writeInt(this.textSize);
        dest.writeInt(this.textColor);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.gravity);
    }

    public SubTitleParams() {
    }

    protected SubTitleParams(Parcel in) {
        this.text = in.readString();
        this.height = in.readInt();
        this.textSize = in.readInt();
        this.textColor = in.readInt();
        this.backgroundColor = in.readInt();
        this.gravity = in.readInt();
    }

    public static final Parcelable.Creator<SubTitleParams> CREATOR = new Parcelable
            .Creator<SubTitleParams>() {
        @Override
        public SubTitleParams createFromParcel(Parcel source) {
            return new SubTitleParams(source);
        }

        @Override
        public SubTitleParams[] newArray(int size) {
            return new SubTitleParams[size];
        }
    };
}
