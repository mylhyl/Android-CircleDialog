package com.mylhyl.circledialog.view.listener;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hupei on 2018/6/13.
 */

public interface OnCreateTitleListener {
    /**
     * @param titleIcon 如果没有设置标题栏的图标，此对象的值为 null
     * @param title
     * @param subTitle
     */
    void onCreateTitle(ImageView titleIcon, TextView title, TextView subTitle);
}
