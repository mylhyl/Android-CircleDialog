package com.mylhyl.circledialog.params;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

import java.io.Serializable;

/**
 * 进度条参数
 * Created by hupei on 2017/3/31.
 */
public class ProgressParams implements Serializable {
    private static final int[] MARGINS = {20, 45, 20, 45};
    private static final int[] TEXT_PADDING = {0, 0, 0, 45};
    /**
     * 进度条与body的边距
     */
    public int[] margins = MARGINS;
    /**
     * 底部文字内边距
     */
    public int[] padding = TEXT_PADDING;
    /**
     * 进度条资源背景
     */
    public int progressDrawableId;
    /**
     * 进度条高度
     */
    public int progressHeight = CircleDimen.progressHeight;
    /**
     * 最大刻度
     */
    public int max;
    /**
     * 刻度
     */
    public int progress;
    /**
     * 进度条显示的文字，支持String.format() 例如：已经下载%s
     */
    public String text = "";

    /**
     * body背景颜色
     */
    public int backgroundColor;
    /**
     * 文本字体颜色
     */
    public int textColor = CircleColor.content;
    /**
     * 文本字体大小
     */
    public int textSize = CircleDimen.contentTextSize;
}
