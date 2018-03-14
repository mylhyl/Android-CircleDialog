package com.mylhyl.circledialog;

import android.view.View;
import android.widget.ListView;

import com.mylhyl.circledialog.view.BodyInputView;
import com.mylhyl.circledialog.view.BodyItemsView;
import com.mylhyl.circledialog.view.ItemsButton;
import com.mylhyl.circledialog.view.MultipleButton;
import com.mylhyl.circledialog.view.SingleButton;

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
    BodyItemsView buildItems();

    /**
     * 生成列表按钮
     */
    ItemsButton buildItemsButton();

    /**
     * 刷新列表内容
     */
    ListView refreshItems();

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
    MultipleButton buildMultipleButton();

    /**
     * 刷新多按钮文字
     */
    MultipleButton refreshMultipleButtonText();

    /**
     * 生成单个按钮布局
     */
    SingleButton buildSingleButton();

    /**
     * 刷新单个按钮文字
     */
    SingleButton refreshSingleButtonText();

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
