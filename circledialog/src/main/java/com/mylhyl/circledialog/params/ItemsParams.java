package com.mylhyl.circledialog.params;

import android.widget.AdapterView;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/30.
 */

public abstract class ItemsParams extends BaseParams {
    public abstract void dismiss();

    public AdapterView.OnItemClickListener listener;//item点击事件
    public int itemHeight = CircleDimen.itemHeight;//item高度
    public int[] padding;//item内间距
    public Object items;//数据源：array or list

    //backgroundColor   item背景色
    public ItemsParams() {
        textColor = CircleColor.content;//item字体色
        textSize = CircleDimen.contentTextSize;//item字体大小
    }
}
