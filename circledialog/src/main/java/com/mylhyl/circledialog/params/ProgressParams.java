package com.mylhyl.circledialog.params;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/31.
 */

public class ProgressParams extends TitleParams {
    public int[] margins;
    public int progressDrawableId;
    public int progressHeight = CircleDimen.progressHeight;
    public int max;
    public int progress;

    public ProgressParams() {
        text = "";
        textColor = CircleColor.content;
        textSize = CircleDimen.messageTextSize;
    }
}
