package com.mylhyl.circledialog.internal;

import android.content.DialogInterface;
import android.view.View;

import com.mylhyl.circledialog.view.listener.CountDownTimerObserver;
import com.mylhyl.circledialog.view.listener.OnAdItemClickListener;
import com.mylhyl.circledialog.view.listener.OnAdPageChangeListener;
import com.mylhyl.circledialog.view.listener.OnBindBodyViewCallback;
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
import com.mylhyl.circledialog.view.listener.OnShowListener;

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
    public View.OnClickListener clickPositiveListener;

    /**
     * 中间按钮点击事件
     */
    public View.OnClickListener clickNeutralListener;

    /**
     * 取消按钮点击事件
     */
    public View.OnClickListener clickNegativeListener;

    /**
     * 输入框确定事件
     */
    public OnInputClickListener inputListener;

    /**
     * RecyclerView Item点击事件
     */
    public OnRvItemClickListener rvItemListener;

    /**
     * item 点击事件
     */
    public OnLvItemClickListener itemListener;

    /**
     * dialog 关闭事件
     */
    public DialogInterface.OnDismissListener dismissListener;

    /**
     * dialog 取消事件
     */
    public DialogInterface.OnCancelListener cancelListener;

    /**
     * dialog 显示事件
     */
    public OnShowListener showListener;
    public DialogInterface.OnKeyListener keyListener;
    public OnCreateBodyViewListener createBodyViewListener;
    public OnCreateProgressListener createProgressListener;
    public OnCreateLottieListener createLottieListener;
    public OnCreateTitleListener createTitleListener;
    public OnCreateTextListener createTextListener;
    public OnCreateInputListener createInputListener;
    public OnCreateButtonListener createButtonListener;
    public OnInputCounterChangeListener inputCounterChangeListener;
    public OnAdItemClickListener adItemClickListener;
    public OnAdPageChangeListener adPageChangeListener;
    public CountDownTimerObserver countDownTimerObserver;
    public OnBindBodyViewCallback bindBodyViewCallback;

    CircleListeners() {

    }

}
