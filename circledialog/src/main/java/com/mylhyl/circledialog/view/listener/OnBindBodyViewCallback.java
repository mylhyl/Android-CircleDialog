package com.mylhyl.circledialog.view.listener;

import android.view.View;

/**
 * Created by hupei on 2018/6/13.
 */
public interface OnBindBodyViewCallback {

    /**
     * @param view
     * @return true关闭对话框 false不关闭
     */
    boolean onBindBodyView(View view);

}
