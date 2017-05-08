package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * 文本内容参数
 * Created by hupei on 2017/3/30.
 */
public class TextParams implements Parcelable {
    /**
     * body文本内间距
     */
    public int[] padding;
    /**
     * 文本
     */
    public String text;
    /**
     * 文本高度
     */
    public int height = CircleDimen.TITLE_HEIGHT;
    /**
     * 文本背景颜色
     */
    public int backgroundColor;
    /**
     * 文本字体颜色
     */
    public int textColor = CircleColor.content;
    /**
     * 文本字体大小
     */
    public int textSize = CircleDimen.CONTENT_TEXT_SIZE;

    public int gravity = Gravity.CENTER;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.padding);
        dest.writeString(this.text);
        dest.writeInt(this.height);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.textColor);
        dest.writeInt(this.textSize);
        dest.writeInt(this.gravity);
    }

    public TextParams() {
    }

    protected TextParams(Parcel in) {
        this.padding = in.createIntArray();
        this.text = in.readString();
        this.height = in.readInt();
        this.backgroundColor = in.readInt();
        this.textColor = in.readInt();
        this.textSize = in.readInt();
        this.gravity = in.readInt();
    }

    public static final Parcelable.Creator<TextParams> CREATOR = new Parcelable
            .Creator<TextParams>() {
        @Override
        public TextParams createFromParcel(Parcel source) {
            return new TextParams(source);
        }

        @Override
        public TextParams[] newArray(int size) {
            return new TextParams[size];
        }
    };
}
