package com.mylhyl.circledialog.params;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/30.
 */

public class TextParams extends TitleParams {
    public int[] padding; //body文本内间距

    //text  //文本
    //height    //body高度
    //backgroundColor 标题背景颜色
    public TextParams() {
        textColor = CircleColor.content;//文本字体颜色
        textSize = CircleDimen.contentTextSize;//文本字体大小
    }
}
