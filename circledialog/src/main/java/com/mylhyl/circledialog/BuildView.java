package com.mylhyl.circledialog;

import android.view.View;

/**
 * Created by hupei on 2017/3/29.
 */

public interface BuildView {
    /**
     * 生成根布局
     */
    void buildRoot();

    /**
     * 生成标题布局
     */
    void buildTitle();

    /**
     * 生成文本布局
     */
    void buildText();

    /**
     * 刷新文本内容
     */
    void refreshText();

    /**
     * 生成列表布局
     */
    void buildItems();

    /**
     * 生成列表按钮
     */
    void buildItemsButton();

    /**
     * 刷新列表内容
     */
    void refreshItems();

    /**
     * 生成进度条布局
     */
    void buildProgress();

    /**
     * 刷新进度条
     */
    void refreshProgress();

    /**
     * 生成输入布局
     */
    void buildInput();

    /**
     * 生成多按钮布局
     */
    void buildMultipleButton();

    /**
     * 生成单个按钮布局
     */
    void buildSingleButton();

    /**
     * 注册输入框确定事件
     */
    void regInputListener();

    /**
     * 取出根布局
     *
     * @return 对话框视图
     */
    View getView();
}
