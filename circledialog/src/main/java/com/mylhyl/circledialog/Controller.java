package com.mylhyl.circledialog;

import android.content.Context;
import android.view.View;

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

    public void apply() {
        applyDialog();
        applyHeader();
        applyBody();
    }

    private void applyDialog() {
        mCreateView.buildRoot();
    }

    private void applyHeader() {
        if (mParams.getTitleParams() != null)
            mCreateView.buildTitle();
    }

    private void applyBody() {
        if (mParams.getTextParams() != null) {
            mCreateView.buildText();
            //有确定并且有取消按钮
            if (mParams.getPositiveParams() != null && mParams.getNegativeParams() != null)
                mCreateView.buildMultipleButton();
                //有确定或者有取消按钮
            else if (mParams.getPositiveParams() != null || mParams.getNegativeParams() != null)
                mCreateView.buildSingleButton();
        } else if (mParams.getTitleParams() != null) {
            mCreateView.buildItems();
            //有确定或者有取消按钮
            if (mParams.getPositiveParams() != null || mParams.getNegativeParams() != null)
                mCreateView.buildItemsButton();
        }
    }

    public View createView() {
        return mCreateView.getView();
    }
}
