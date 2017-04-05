package com.mylhyl.circledialog;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mylhyl.circledialog.params.CircleParams;
import com.mylhyl.circledialog.view.BuildViewImpl;

/**
 * Created by hupei on 2017/3/29.
 */

public class Controller {
    private Context mContext;
    private CircleParams mParams;
    private BuildView mCreateView;

    public Controller(Context context, CircleParams params) {
        this.mContext = context;
        this.mParams = params;
        mCreateView = new BuildViewImpl(mContext, mParams);
    }

    public View createView() {
        applyRoot();
        applyHeader();
        applyBody();
        return getView();
    }

    public void refreshView() {
        mCreateView.refreshText();
        mCreateView.refreshItems();
        mCreateView.refreshProgress();
        //刷新时带动画
        if (mParams.getDialogParams().refreshAnimation != 0 && getView() != null)
            getView().post(new Runnable() {
                @Override
                public void run() {
                    Animation animation = AnimationUtils.loadAnimation(mContext, mParams.getDialogParams()
                            .refreshAnimation);
                    if (animation != null) getView().startAnimation(animation);
                }
            });
    }

    private void applyRoot() {
        mCreateView.buildRoot();
    }

    private void applyHeader() {
        if (mParams.getTitleParams() != null)
            mCreateView.buildTitle();
    }

    private void applyBody() {
        //文本
        if (mParams.getTextParams() != null) {
            mCreateView.buildText();
            applyButton();
        }
        //列表
        else if (mParams.getItemsParams() != null) {
            mCreateView.buildItems();
            //有确定或者有取消按钮
            if (mParams.getPositiveParams() != null || mParams.getNegativeParams() != null)
                mCreateView.buildItemsButton();
        }
        //进度条
        else if (mParams.getProgressParams() != null) {
            mCreateView.buildProgress();
            applyButton();
        }
        //输入框
        else if (mParams.getInputParams() != null) {
            mCreateView.buildInput();
            applyButton();
            mCreateView.regInputListener();
        }
    }

    private void applyButton() {
        //有确定并且有取消按钮
        if (mParams.getPositiveParams() != null && mParams.getNegativeParams() != null)
            mCreateView.buildMultipleButton();
            //有确定或者有取消按钮
        else if (mParams.getPositiveParams() != null || mParams.getNegativeParams() != null)
            mCreateView.buildSingleButton();
    }

    private View getView() {
        return mCreateView.getView();
    }
}
