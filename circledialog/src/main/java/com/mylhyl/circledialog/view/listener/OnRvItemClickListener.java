package com.mylhyl.circledialog.view.listener;

import android.view.View;

/**
 * Created by hupei on 2018/4/18.
 */

public interface OnRvItemClickListener {
    /**
     * @param view
     * @param position
     * @return true关闭对话框 false不关闭
     */
    boolean onItemClick(View view, int position);
}
