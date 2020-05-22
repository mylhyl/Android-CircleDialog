package com.mylhyl.circledialog.view.listener;

import android.content.DialogInterface;
import android.view.View;

import com.mylhyl.circledialog.CircleViewHolder;

/**
 * Created by hupei on 2020/5/20.
 * <p>
 * 功能描述：
 * <p>
 * <ul>
 * 业务逻辑说明：
 * <li></li>
 * </ul>
 * <p>
 * <ul>
 * 注意事项：
 * <li></li>
 * </ul>
 * <p>
 * <ul>
 * 变更记录：
 * <li></li>
 * </ul>
 *
 * @author hupei
 * @since 5.3.1
 */
public interface OnShowListener {

    void onShow(DialogInterface dialog, CircleViewHolder viewHolder);

}
