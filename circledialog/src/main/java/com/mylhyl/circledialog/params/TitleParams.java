package com.mylhyl.circledialog.params;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/30.
 */
public class TitleParams extends BaseParams {
    public String text;//标题
    public int height = CircleDimen.titleHeight;//标题高度

    //backgroundColor 标题背景颜色
    public TitleParams() {
        textSize = CircleDimen.titleTextSize;//标题字体大小
        textColor = CircleColor.title;//标题字体颜色
    }
}
