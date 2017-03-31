package com.mylhyl.circledialog.params;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * Created by hupei on 2017/3/30.
 */

public class TextParams extends TitleParams {
    public int[] padding;

    public TextParams() {
        textColor = CircleColor.content;
        textSize = CircleDimen.contentTextSize;
    }
}
