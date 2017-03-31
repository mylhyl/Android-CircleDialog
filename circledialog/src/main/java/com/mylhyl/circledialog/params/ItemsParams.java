package com.mylhyl.circledialog.params;

import android.widget.AdapterView;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/30.
 */

public abstract class ItemsParams extends BaseParams {
    public abstract void dismiss();

    public AdapterView.OnItemClickListener listener;
    public int itemHeight = CircleDimen.itemHeight;
    public int[] padding;
    public Object items;

    public ItemsParams() {
        textColor = CircleColor.content;
        textSize = CircleDimen.contentTextSize;
    }
}
