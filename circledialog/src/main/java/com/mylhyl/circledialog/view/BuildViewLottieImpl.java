package com.mylhyl.circledialog.view;

import android.content.Context;

import com.mylhyl.circledialog.CircleParams;

/**
 * view的层次结构
 * <pre>
 * CardView
 *    ╚--LinearLayout
 *          ╚--TitleView
 *          ╚--BodyView
 *          ╚--ButtonView
 * </pre>
 * Created by hupei on 2018/8/14.
 */

public final class BuildViewLottieImpl extends AbsBuildView {
    private BodyLottieView mBodyLottieView;

    public BuildViewLottieImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView();

        if (mBodyLottieView == null) {
            mBodyLottieView = new BodyLottieView(mContext, mParams.dialogParams, mParams.lottieParams,
                    mParams.createLottieListener);
            addViewByBody(mBodyLottieView);
        }
    }

    @Override
    public BodyLottieView getBodyView() {
        return mBodyLottieView;
    }

    @Override
    public void refreshContent() {
    }
}
