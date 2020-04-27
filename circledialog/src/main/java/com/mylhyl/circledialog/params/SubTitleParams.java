package com.mylhyl.circledialog.params;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2018/4/12.
 * <p>
 * 副标题参数
 * <ul>
 * 变更记录：
 * <li>add: 2020/4/27 八阿哥 since 5.2.0 增加底部分隔线属性 isShowBottomDivider </li>
 * </ul>
 */
public class SubTitleParams implements Parcelable {
    public static final Creator<SubTitleParams> CREATOR = new Creator<SubTitleParams>() {
        @Override
        public SubTitleParams createFromParcel(Parcel source) {
            return new SubTitleParams(source);
        }

        @Override
        public SubTitleParams[] newArray(int size) {
            return new SubTitleParams[size];
        }
    };
    /**
     * 标题
     */
    public String text;
    /**
     * 内间距 [left, top, right, bottom]
     */
    public int[] padding = CircleDimen.SUBTITLE_PADDING;
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
    public int textColor = CircleColor.SUBTITLE_TEXT;
    /**
     * 标题背景颜色
     */
    public int backgroundColor;
    public int gravity = Gravity.CENTER;
    /**
     * 字样式
     * {@linkplain Typeface#NORMAL NORMAL}
     * {@linkplain Typeface#BOLD BOLD}
     * {@linkplain Typeface#ITALIC ITALIC}
     * {@linkplain Typeface#BOLD_ITALIC BOLD_ITALIC}
     */
    public int styleText = Typeface.NORMAL;
    /**
     * 是否显示底部分隔线<br>
     * title与body之间是没有分隔线的，默认不显示<br>
     * 如需要显示设置此属性为true即可
     */
    public boolean isShowBottomDivider;

    public SubTitleParams() {
    }

    protected SubTitleParams(Parcel in) {
        this.text = in.readString();
        this.padding = in.createIntArray();
        this.height = in.readInt();
        this.textSize = in.readInt();
        this.textColor = in.readInt();
        this.backgroundColor = in.readInt();
        this.gravity = in.readInt();
        this.styleText = in.readInt();
        this.isShowBottomDivider = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeIntArray(this.padding);
        dest.writeInt(this.height);
        dest.writeInt(this.textSize);
        dest.writeInt(this.textColor);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.gravity);
        dest.writeInt(this.styleText);
        dest.writeByte(this.isShowBottomDivider ? (byte) 1 : (byte) 0);
    }
}
