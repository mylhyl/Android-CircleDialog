package com.mylhyl.circledialog.params;

import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/31.
 */

public class ProgressParams extends TextParams {
    public int[] margins = CircleDimen.progressMargins;
    public int progressDrawableId;
    public int progressHeight = CircleDimen.progressHeight;
    public int max;
    public int progress;

    public ProgressParams() {
        text = "";//进度条显示的文字，支持String.format() 例如：已经下载%s
        padding = CircleDimen.progressTextPadding;//进度条底部文字内边距
    }
}
