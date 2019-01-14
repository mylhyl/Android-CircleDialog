package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * 对话框参数
 * Created by hupei on 2017/3/30.
 */
public class DialogParams implements Parcelable {
    public static final Creator<DialogParams> CREATOR = new Creator<DialogParams>() {
        @Override
        public DialogParams createFromParcel(Parcel source) {
            return new DialogParams(source);
        }

        @Override
        public DialogParams[] newArray(int size) {
            return new DialogParams[size];
        }
    };
    /**
     * 对话框的位置
     */
    public int gravity = Gravity.CENTER;
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
    public float alpha = CircleDimen.DIALOG_ALPHA;
    /**
     * 对话框宽度，范围：0-1；1整屏宽
     */
    public float width = CircleDimen.DIALOG_WIDTH;
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
     * 对话框的背景色
     */
    public int backgroundColor = CircleColor.DIALOG_BACKGROUND;
    /**
     * 对话框的圆角半径
     */
    public int radius = CircleDimen.DIALOG_RADIUS;
    /**
     * 对话框 x 坐标偏移
     */
    public int xOff;
    /**
     * 对话框 y 坐标偏移
     */
    public int yOff = -1;
    /**
     * 按下颜色值
     */
    public int backgroundColorPress = CircleColor.DIALOG_BACKGROUND_PRESS;
    public float maxHeight;//最大高度
    public int systemUiVisibility;
    /**
     * 延迟弹出
     */
    public int delayShow;

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
        this.backgroundColorPress = in.readInt();
        this.maxHeight = in.readFloat();
        this.systemUiVisibility = in.readInt();
        this.delayShow = in.readInt();
    }

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
        dest.writeInt(this.backgroundColorPress);
        dest.writeFloat(this.maxHeight);
        dest.writeInt(this.systemUiVisibility);
        dest.writeInt(this.delayShow);
    }
}
