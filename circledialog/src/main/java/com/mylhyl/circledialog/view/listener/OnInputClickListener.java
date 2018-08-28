package com.mylhyl.circledialog.view.listener;

import android.view.View;

/**
 * Created by hupei on 2017/3/30.
 */
public interface OnInputClickListener {
    /**
     * 点击输入确定按钮时此方法将会调用
     *
     * @param text
     * @param v
     * @return true关闭对话框 false不关闭
     */
    boolean onClick(String text, View v);
}
