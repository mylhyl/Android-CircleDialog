package com.mylhyl.circledialog.params;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * LottieAnimationView参数
 * Created by hupei on 2018/7/7.
 */
public class LottieParams implements Parcelable {

    public static final Creator<LottieParams> CREATOR = new Creator<LottieParams>() {
        @Override
        public LottieParams createFromParcel(Parcel source) {
            return new LottieParams(source);
        }

        @Override
        public LottieParams[] newArray(int size) {
            return new LottieParams[size];
        }
    };

    /**
     * 进度条与body的边距 [left, top, right, bottom]
     */
    public int[] margins;
    /**
     * 底部文字内边距 [left, top, right, bottom]
     */
    public int[] textPadding;
    public int[] textMargins = CircleDimen.LOTTIE_TEXT_MARGINS;
    public int lottieHeight = CircleDimen.LOTTIE_HEIGHT;
    public int lottieWidth = CircleDimen.LOTTIE_WIDTH;
    public int animationResId;
    public String animationFileName;
    public boolean autoPlay;
    public boolean loop;
    /**
     * 加载文字
     */
    public String text = "";
    /**
     * body背景颜色
     */
    public int backgroundColor;
    /**
     * 文本字体颜色
     */
    public int textColor = CircleColor.LOADING_TEXT;
    /**
     * 文本字体大小
     */
    public int textSize = CircleDimen.LOADING_TEXT_SIZE;
    /**
     * 字样式
     * {@linkplain Typeface#NORMAL NORMAL}
     * {@linkplain Typeface#BOLD BOLD}
     * {@linkplain Typeface#ITALIC ITALIC}
     * {@linkplain Typeface#BOLD_ITALIC BOLD_ITALIC}
     */
    public int styleText = Typeface.NORMAL;

    public LottieParams() {
    }

    protected LottieParams(Parcel in) {
        this.margins = in.createIntArray();
        this.textPadding = in.createIntArray();
        this.textMargins = in.createIntArray();
        this.lottieHeight = in.readInt();
        this.lottieWidth = in.readInt();
        this.animationResId = in.readInt();
        this.animationFileName = in.readString();
        this.autoPlay = in.readByte() != 0;
        this.loop = in.readByte() != 0;
        this.text = in.readString();
        this.backgroundColor = in.readInt();
        this.textColor = in.readInt();
        this.textSize = in.readInt();
        this.styleText = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.margins);
        dest.writeIntArray(this.textPadding);
        dest.writeIntArray(this.textMargins);
        dest.writeInt(this.lottieHeight);
        dest.writeInt(this.lottieWidth);
        dest.writeInt(this.animationResId);
        dest.writeString(this.animationFileName);
        dest.writeByte(this.autoPlay ? (byte) 1 : (byte) 0);
        dest.writeByte(this.loop ? (byte) 1 : (byte) 0);
        dest.writeString(this.text);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.textColor);
        dest.writeInt(this.textSize);
        dest.writeInt(this.styleText);
    }
}
