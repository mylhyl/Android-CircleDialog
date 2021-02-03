package com.mylhyl.circledialog.view.listener;

import android.view.View;

/**
 * Created by hupei on 2021/2/1.
 * <p>
 * 确定，中间，取消按钮的点击事件
 *
 * @since 5.7.4
 */
public interface OnButtonClickListener {

    /**
     * 点击输入确定按钮时此方法将会调用
     *
     * @param v 按钮
     * @return true关闭对话框 false不关闭
     */
    boolean onClick(View v);
}
