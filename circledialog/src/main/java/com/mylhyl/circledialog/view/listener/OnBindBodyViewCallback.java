package com.mylhyl.circledialog.view.listener;

import com.mylhyl.circledialog.CircleViewHolder;

/**
 * Created by hupei on 2018/6/13.
 */
public interface OnBindBodyViewCallback {

    /**
     * @param viewHolder
     * @return true关闭对话框 false不关闭
     */
    boolean onBindBodyView(CircleViewHolder viewHolder);

}
