package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * items 内容参数
 * Created by hupei on 2017/3/30.
 */
public class ItemsParams implements Parcelable {

    /**
     * item高度
     */
    public int itemHeight = CircleDimen.ITEM_HEIGHT;
    /**
     * item内间距
     */
    public int[] padding;
    /**
     * 数据源：array or list
     */
    public Object items;
    /**
     * item背景色
     */
    public int backgroundColor;
    /**
     * item字体色
     */
    public int textColor = CircleColor.content;
    /**
     * item字体大小
     */
    public int textSize = CircleDimen.CONTENT_TEXT_SIZE;
    /**
     * 按下颜色值
     */
    public int backgroundColorPress;

    /**
     * 是否触发自动关闭对话框
     */
    public boolean isManualClose;

    public BaseAdapter adapter;
    //==============RecyclerView 相关====================
    /**
     * RecyclerView 适配器
     */
    public RecyclerView.Adapter adapterRv;
    /**
     * RecyclerView 布局管理
     */
    public RecyclerView.LayoutManager layoutManager;
    /**
     * RecyclerView 分隔线
     */
    public RecyclerView.ItemDecoration itemDecoration;

    public ItemsParams() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemHeight);
        dest.writeIntArray(this.padding);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.textColor);
        dest.writeInt(this.textSize);
        dest.writeInt(this.backgroundColorPress);
        dest.writeByte(this.isManualClose ? (byte) 1 : (byte) 0);
    }

    protected ItemsParams(Parcel in) {
        this.itemHeight = in.readInt();
        this.padding = in.createIntArray();
        this.backgroundColor = in.readInt();
        this.textColor = in.readInt();
        this.textSize = in.readInt();
        this.backgroundColorPress = in.readInt();
        this.isManualClose = in.readByte() != 0;
    }

    public static final Creator<ItemsParams> CREATOR = new Creator<ItemsParams>() {
        @Override
        public ItemsParams createFromParcel(Parcel source) {
            return new ItemsParams(source);
        }

        @Override
        public ItemsParams[] newArray(int size) {
            return new ItemsParams[size];
        }
    };
}
