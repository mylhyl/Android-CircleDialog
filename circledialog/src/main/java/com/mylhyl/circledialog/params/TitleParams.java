package com.mylhyl.circledialog.params;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

import java.io.Serializable;

/**
 * Created by hupei on 2017/3/30.
 */

public class TitleParams implements Serializable {
    public String text;//标题
    public int textSize = CircleDimen.titleTextSize;//标题字体大小
    public int textColor = CircleColor.title;//标题字体颜色
    public int height = CircleDimen.titleHeight;//标题高度
    public int backgroundColor;//标题背景
}
