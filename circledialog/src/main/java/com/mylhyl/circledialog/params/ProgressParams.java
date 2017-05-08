package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.os.Parcelable;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * 进度条参数
 * Created by hupei on 2017/3/31.
 */
public class ProgressParams implements Parcelable {

    private static final int[] MARGINS = {20, 45, 20, 45};
    private static final int[] TEXT_PADDING = {0, 0, 0, 45};

    /**
     * 水平进度条
     */
    public static final int STYLE_HORIZONTAL = 0;
    /**
     * 旋转进度条
     */
    public static final int STYLE_SPINNER = 1;

    /**
     * 进度条样式，默认水平样式
     */
    public int style = STYLE_HORIZONTAL;
    /**
     * 进度条与body的边距
     */
    public int[] margins = MARGINS;
    /**
     * 底部文字内边距
     */
    public int[] padding = TEXT_PADDING;
    /**
     * 进度条资源背景
     */
    public int progressDrawableId;
    /**
     * 进度条高度
     */
    public int progressHeight;
    /**
     * 最大刻度
     */
    public int max;
    /**
     * 刻度
     */
    public int progress;
    /**
     * 进度条显示的文字，支持String.format() 例如：已经下载%s
     */
    public String text = "";

    /**
     * body背景颜色
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.style);
        dest.writeIntArray(this.margins);
        dest.writeIntArray(this.padding);
        dest.writeInt(this.progressDrawableId);
        dest.writeInt(this.progressHeight);
        dest.writeInt(this.max);
        dest.writeInt(this.progress);
        dest.writeString(this.text);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.textColor);
        dest.writeInt(this.textSize);
    }

    public ProgressParams() {
    }

    protected ProgressParams(Parcel in) {
        this.style = in.readInt();
        this.margins = in.createIntArray();
        this.padding = in.createIntArray();
        this.progressDrawableId = in.readInt();
        this.progressHeight = in.readInt();
        this.max = in.readInt();
        this.progress = in.readInt();
        this.text = in.readString();
        this.backgroundColor = in.readInt();
        this.textColor = in.readInt();
        this.textSize = in.readInt();
    }

    public static final Parcelable.Creator<ProgressParams> CREATOR = new Parcelable
            .Creator<ProgressParams>() {
        @Override
        public ProgressParams createFromParcel(Parcel source) {
            return new ProgressParams(source);
        }

        @Override
        public ProgressParams[] newArray(int size) {
            return new ProgressParams[size];
        }
    };
}
