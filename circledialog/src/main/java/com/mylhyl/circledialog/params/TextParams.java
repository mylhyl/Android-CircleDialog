package com.mylhyl.circledialog.params;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

import java.io.Serializable;

/**
 * 文本内容参数
 * Created by hupei on 2017/3/30.
 */
public class TextParams implements Serializable {
    /**
     * body文本内间距
     */
    public int[] padding;
    /**
     * 文本
     */
    public String text;
    /**
     * 文本高度
     */
    public int height = CircleDimen.TITLE_HEIGHT;
    /**
     * 文本背景颜色
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
}
