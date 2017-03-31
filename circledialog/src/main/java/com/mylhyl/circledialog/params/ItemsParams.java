package com.mylhyl.circledialog.params;

import android.widget.AdapterView;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

import java.io.Serializable;

/**
 * items 内容参数
 * Created by hupei on 2017/3/30.
 */
public abstract class ItemsParams implements Serializable {
    public abstract void dismiss();

    /**
     * item点击事件
     */
    public AdapterView.OnItemClickListener listener;
    /**
     * item高度
     */
    public int itemHeight = CircleDimen.itemHeight;
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
    public int textSize = CircleDimen.contentTextSize;
}
