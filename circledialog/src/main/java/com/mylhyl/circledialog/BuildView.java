package com.mylhyl.circledialog;

import android.view.View;

import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.InputView;
import com.mylhyl.circledialog.view.listener.ItemsView;

/**
 * Created by hupei on 2017/3/29.
 */

public interface BuildView {
    /**
     * 生成根布局
     *
     * @return 根视图
     */
    View buildRoot();

    /**
     * 生成标题布局
     *
     * @return 标题视图
     */
    View buildTitle();

    /**
     * 刷新标题
     *
     * @return
     */
    View refreshTitle();

    View buildCustomBodyView();

    /**
     * 生成文本布局
     *
     * @return 文本视图
     */
    View buildText();

    /**
     * 刷新文本内容
     *
     * @return 文本视图
     */
    View refreshText();

    /**
     * 生成列表布局
     *
     * @return 列表视图
     */
    ItemsView buildItems();

    /**
     * 生成列表按钮
     *
     * @return 列表按钮视图
     */
    ButtonView buildItemsButton();

    /**
     * 刷新列表内容
     *
     * @return 列表视图
     */
    ItemsView refreshItems();

    /**
     * 生成进度条布局
     *
     * @return 进度条视图
     */
    View buildProgress();

    /**
     * 生成lottie动画框
     */
    void buildLottie();

    /**
     * 刷新进度条
     *
     * @return 进度条视图
     */
    View refreshProgress();

    /**
     * 生成输入布局
     *
     * @return 输入框视图
     */
    InputView buildInput();

    /**
     * 生成多按钮布局
     *
     * @return 按钮视图
     */
    ButtonView buildMultipleButton();

    /**
     * 刷新多按钮文字
     *
     * @return 按钮视图
     */
    ButtonView refreshMultipleButtonText();

    /**
     * 取出根布局
     *
     * @return 对话框视图
     */
    View getView();

    InputView getInputView();
}
