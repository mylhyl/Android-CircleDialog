package com.mylhyl.circledialog.params;

import android.widget.AdapterView;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

import java.io.Serializable;

/**
 * Created by hupei on 2017/3/30.
 */

public abstract class ItemsParams implements Serializable {
    public abstract void dismiss();

    public AdapterView.OnItemClickListener listener;
    public int itemHeight = CircleDimen.itemHeight;
    public int[] padding;
    public int textColor = CircleColor.content;
    public int textSize = CircleDimen.messageTextSize;
    public int backgroundColor;//背景
    public Object items;
}
