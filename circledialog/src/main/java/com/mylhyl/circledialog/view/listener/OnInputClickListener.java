package com.mylhyl.circledialog.view.listener;

import android.widget.EditText;

/**
 * Created by hupei on 2017/3/30.
 */
public interface OnInputClickListener {
    /**
     * 点击输入确定按钮时此方法将会调用
     *
     * @param text     输入框文本
     * @param editText 输入框
     * @return true关闭对话框 false不关闭
     */
    boolean onClick(String text, EditText editText);
}
