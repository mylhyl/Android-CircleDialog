package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;

import com.mylhyl.circledialog.internal.CircleParams;

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

public final class BuildViewCustomBodyImpl extends AbsBuildView {
    private View mCustomBodyView;

    public BuildViewCustomBodyImpl(Context context, CircleParams params) {
        super(context, params);
    }

    @Override
    public void buildBodyView() {
        buildRootView();
        buildTitleView();

        if (mCustomBodyView != null) {
            return;
        }
        if (mParams.bodyViewId != 0) {
            mCustomBodyView = layoutInflaterFrom(mParams.bodyViewId);
        } else {
            mCustomBodyView = mParams.bodyView;
        }
        if (mCustomBodyView != null) {
            addViewByBody(mCustomBodyView);
        }
    }

    @Override
    public View getBodyView() {
        return mCustomBodyView;
    }

    @Override
    public void refreshContent() {

    }
}
