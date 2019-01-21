package com.mylhyl.circledialog.view.listener;

import android.view.View;

/**
 * Created by hupei on 2019/1/14 16:32.
 */
public interface OnAdItemClickListener {
    /**
     * 点击广告图片时将调用
     *
     * @param view     view
     * @param position position
     * @return true关闭，false不关闭
     */
    boolean onItemClick(View view, int position);
}
