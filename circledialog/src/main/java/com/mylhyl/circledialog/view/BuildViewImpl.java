package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.mylhyl.circledialog.BuildView;
import com.mylhyl.circledialog.params.CircleParams;

/**
 * Created by hupei on 2017/3/29.
 */

public class BuildViewImpl implements BuildView {
    private Context mContext;
    private CircleParams mParams;
    private LinearLayout mRoot;

    public BuildViewImpl(Context context, CircleParams params) {
        this.mContext = context;
        this.mParams = params;
    }

    @Override
    public void buildRoot() {
        mRoot = new AutoLinearLayout(mContext);
        mRoot.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public void buildTitle() {
        TitleView titleView = new TitleView(mContext, mParams);
        mRoot.addView(titleView);
    }

    @Override
    public void buildText() {
        BodyTextView bodyTextView = new BodyTextView(mContext, mParams);
        mRoot.addView(bodyTextView);
    }

    @Override
    public void buildItems() {
        BodyItemsView bodyItemsView = new BodyItemsView(mContext, mParams);
//        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
//                AbsListView.LayoutParams.MATCH_PARENT);
//        mRoot.addView(bodyItemsView, params);
        mRoot.addView(bodyItemsView);
    }

    @Override
    public void buildMultipleButton() {
        DividerView dividerViewV = new DividerView(mContext);
        dividerViewV.setVertical();
        mRoot.addView(dividerViewV);
        mRoot.addView(new MultipleButton(mContext, mParams));
    }

    @Override
    public void buildSingleButton() {
        DividerView dividerViewV = new DividerView(mContext);
        dividerViewV.setVertical();
        mRoot.addView(dividerViewV);
        mRoot.addView(new SingleButton(mContext, mParams));
    }

    @Override
    public void buildItemsButton() {
        mRoot.addView(new ItemsButton(mContext, mParams));
    }

    @Override
    public View getView() {
        return mRoot;
    }
}
