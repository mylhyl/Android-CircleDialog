package com.mylhyl.circledialog.params;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.mylhyl.circledialog.res.values.CircleDimen;

import java.io.Serializable;

/**
 * 对话框参数
 * Created by hupei on 2017/3/30.
 */
public class DialogParams implements Parcelable {
    /**
     * 对话框的位置
     */
    public int gravity = Gravity.NO_GRAVITY;
    /**
     * 是否触摸外部关闭
     */
    public boolean canceledOnTouchOutside = true;
    /**
     * 返回键是否关闭
     */
    public boolean cancelable = true;
    /**
     * 对话框透明度，范围：0-1；1不透明
     */
    public float alpha = 1f;
    /**
     * 对话框宽度，范围：0-1；1整屏宽
     */
    public float width = 0.9f;
    /**
     * 对话框与屏幕边距
     */
    public int[] mPadding;
    /**
     * 对话框弹出动画,StyleRes
     */
    public int animStyle;
    /**
     * 对话框刷新动画，AnimRes
     */
    public int refreshAnimation;
    /**
     * 对话框背景是否昏暗，默认true
     */
    public boolean isDimEnabled = true;
    /**
     * 对话框的背景色透明，因为列表模式情况，内容与按钮中间有距离
     */
    public int backgroundColor = Color.TRANSPARENT;
    /**
     * 对话框的圆角半径
     */
    public int radius = CircleDimen.RADIUS;
    /**
     * 对话框 x 坐标偏移
     */
    public int xOff;
    /**
     * 对话框 y 坐标偏移
     */
    public int yOff;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.gravity);
        dest.writeByte(this.canceledOnTouchOutside ? (byte) 1 : (byte) 0);
        dest.writeByte(this.cancelable ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.alpha);
        dest.writeFloat(this.width);
        dest.writeIntArray(this.mPadding);
        dest.writeInt(this.animStyle);
        dest.writeInt(this.refreshAnimation);
        dest.writeByte(this.isDimEnabled ? (byte) 1 : (byte) 0);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.radius);
        dest.writeInt(this.xOff);
        dest.writeInt(this.yOff);
    }

    public DialogParams() {
    }

    protected DialogParams(Parcel in) {
        this.gravity = in.readInt();
        this.canceledOnTouchOutside = in.readByte() != 0;
        this.cancelable = in.readByte() != 0;
        this.alpha = in.readFloat();
        this.width = in.readFloat();
        this.mPadding = in.createIntArray();
        this.animStyle = in.readInt();
        this.refreshAnimation = in.readInt();
        this.isDimEnabled = in.readByte() != 0;
        this.backgroundColor = in.readInt();
        this.radius = in.readInt();
        this.xOff = in.readInt();
        this.yOff = in.readInt();
    }

    public static final Parcelable.Creator<DialogParams> CREATOR = new Parcelable.Creator<DialogParams>() {
        @Override
        public DialogParams createFromParcel(Parcel source) {
            return new DialogParams(source);
        }

        @Override
        public DialogParams[] newArray(int size) {
            return new DialogParams[size];
        }
    };
}
