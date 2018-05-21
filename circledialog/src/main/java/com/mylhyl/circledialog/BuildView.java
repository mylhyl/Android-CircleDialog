package com.mylhyl.circledialog;

import android.view.View;

import com.mylhyl.circledialog.view.BodyInputView;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.ItemsView;

/**
 * Created by hupei on 2017/3/29.
 */

public interface BuildView {
    /**
     * 生成根布局
     */
    View buildRoot();

    /**
     * 生成标题布局
     */
    View buildTitle();

    /**
     * 生成文本布局
     */
    View buildText();

    /**
     * 刷新文本内容
     */
    View refreshText();

    /**
     * 生成列表布局
     */
    ItemsView buildItems();

    /**
     * 生成列表按钮
     */
    ButtonView buildItemsButton();

    /**
     * 刷新列表内容
     */
    ItemsView refreshItems();

    /**
     * 生成进度条布局
     */
    View buildProgress();

    /**
     * 刷新进度条
     */
    View refreshProgress();

    /**
     * 生成输入布局
     */
    BodyInputView buildInput();

    /**
     * 生成多按钮布局
     */
    ButtonView buildMultipleButton();

    /**
     * 刷新多按钮文字
     */
    ButtonView refreshMultipleButtonText();

    /**
     * 取出根布局
     *
     * @return 对话框视图
     */
    View getView();
}
