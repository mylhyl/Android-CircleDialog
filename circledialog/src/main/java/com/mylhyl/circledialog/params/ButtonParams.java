package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;

import java.io.Serializable;

/**
 * 按钮参数
 * Created by hupei on 2017/3/30.
 */
public class ButtonParams implements Parcelable {
    public void dismiss() {

    }

    /**
     * 按钮点击事件
     */
    public View.OnClickListener listener;
    /**
     * 输入框确定事件
     */
    public OnInputClickListener inputListener;
    /**
     * 按钮框与顶部距离
     */
    public int topMargin;
    /**
     * 按钮文本颜色
     */
    public int textColor = CircleColor.button;
    /**
     * 按钮文本大小
     */
    public int textSize = CircleDimen.FOOTER_TEXT_SIZE;
    /**
     * 按钮高度
     */
    public int height = CircleDimen.FOOTER_HEIGHT;
    /**
     * 按钮背景颜色
     */
    public int backgroundColor;
    /**
     * 按钮文本
     */
    public String text;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.topMargin);
        dest.writeInt(this.textColor);
        dest.writeInt(this.textSize);
        dest.writeInt(this.height);
        dest.writeInt(this.backgroundColor);
        dest.writeString(this.text);
    }

    public ButtonParams() {
    }

    protected ButtonParams(Parcel in) {
        this.topMargin = in.readInt();
        this.textColor = in.readInt();
        this.textSize = in.readInt();
        this.height = in.readInt();
        this.backgroundColor = in.readInt();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<ButtonParams> CREATOR = new Parcelable.Creator<ButtonParams>() {
        @Override
        public ButtonParams createFromParcel(Parcel source) {
            return new ButtonParams(source);
        }

        @Override
        public ButtonParams[] newArray(int size) {
            return new ButtonParams[size];
        }
    };
}
