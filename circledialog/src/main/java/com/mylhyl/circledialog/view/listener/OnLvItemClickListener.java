package com.mylhyl.circledialog.view.listener;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by hupei on 2018/8/28 11:17.
 */
public interface OnLvItemClickListener {
    /**
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return true关闭对话框 false不关闭
     */
    boolean onItemClick(AdapterView<?> parent, View view, int position, long id);
}
