package com.mylhyl.circledialog.params;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/31.
 */

public class ProgressParams extends BaseParams {
    public int[] margins = CircleDimen.progressMargins;//进度条与body的边距
    public int[] padding = CircleDimen.progressTextPadding;//底部文字内边距
    public int progressDrawableId;//进度条资源背景
    public int progressHeight = CircleDimen.progressHeight;//进度条高度
    public int max;//最大刻度
    public int progress;//刻度
    public String text = "";//进度条显示的文字，支持String.format() 例如：已经下载%s

    //backgroundColor //body背景颜色
    public ProgressParams() {
        textColor = CircleColor.content;//文本字体颜色
        textSize = CircleDimen.contentTextSize;//文本字体大小
    }
}
