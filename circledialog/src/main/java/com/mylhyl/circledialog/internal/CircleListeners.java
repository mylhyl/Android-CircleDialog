package com.mylhyl.circledialog.internal;

import android.content.DialogInterface;
import android.view.View;

import com.mylhyl.circledialog.view.listener.OnAdItemClickListener;
import com.mylhyl.circledialog.view.listener.OnAdPageChangeListener;
import com.mylhyl.circledialog.view.listener.OnCreateBodyViewListener;
import com.mylhyl.circledialog.view.listener.OnCreateButtonListener;
import com.mylhyl.circledialog.view.listener.OnCreateInputListener;
import com.mylhyl.circledialog.view.listener.OnCreateLottieListener;
import com.mylhyl.circledialog.view.listener.OnCreateProgressListener;
import com.mylhyl.circledialog.view.listener.OnCreateTextListener;
import com.mylhyl.circledialog.view.listener.OnCreateTitleListener;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;
import com.mylhyl.circledialog.view.listener.OnInputCounterChangeListener;
import com.mylhyl.circledialog.view.listener.OnLvItemClickListener;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

/**
 * Created by hupei on 2019/12/17.
 * <p>
 * 功能：所有的监听器类
 *
 * @author hupei
 * @since 5.0.7
 */
public class CircleListeners {

    /**
     * 确定按钮点击事件
     */
    public static View.OnClickListener clickPositiveListener;

    /**
     * 中间按钮点击事件
     */
    public static View.OnClickListener clickNeutralListener;

    /**
     * 取消按钮点击事件
     */
    public static View.OnClickListener clickNegativeListener;

    /**
     * 输入框确定事件
     */
    public static OnInputClickListener inputListener;

    /**
     * RecyclerView Item点击事件
     */
    public static OnRvItemClickListener rvItemListener;

    /**
     * item 点击事件
     */
    public static OnLvItemClickListener itemListener;

    /**
     * dialog 关闭事件
     */
    public static DialogInterface.OnDismissListener dismissListener;

    /**
     * dialog 取消事件
     */
    public static DialogInterface.OnCancelListener cancelListener;

    /**
     * dialog 显示事件
     */
    public static DialogInterface.OnShowListener showListener;
    public static OnCreateBodyViewListener createBodyViewListener;
    public static OnCreateProgressListener createProgressListener;
    public static OnCreateLottieListener createLottieListener;
    public static OnCreateTitleListener createTitleListener;
    public static OnCreateTextListener createTextListener;
    public static OnCreateInputListener createInputListener;
    public static OnCreateButtonListener createButtonListener;
    public static OnInputCounterChangeListener inputCounterChangeListener;
    public static OnAdItemClickListener adItemClickListener;
    public static OnAdPageChangeListener adPageChangeListener;

    CircleListeners() {
    }
}
