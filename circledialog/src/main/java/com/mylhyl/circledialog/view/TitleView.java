package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mylhyl.circledialog.internal.BackgroundHelper;
import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.SubTitleParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.view.listener.OnCreateTitleListener;

/**
 * Created by hupei on 2017/3/29.
 * <p>
 * 对话框标题
 * <ul>
 * 变更记录：
 * <li>add: 2020/4/27 八阿哥 since 5.2.0 增加底部分隔线</li>
 * </ul>
 */
final class TitleView extends LinearLayout {
    private RelativeLayout mTitleLayout;
    private ImageView mTitleIcon;
    private TextView mTitleView;
    private TextView mSubTitleView;
    private DialogParams mDialogParams;
    private TitleParams mTitleParams;
    private SubTitleParams mSubTitleParams;
    private OnCreateTitleListener mOnCreateTitleListener;

    public TitleView(Context context, CircleParams circleParams) {
        super(context);
        this.mDialogParams = circleParams.dialogParams;
        this.mTitleParams = circleParams.titleParams;
        this.mSubTitleParams = circleParams.subTitleParams;
        this.mOnCreateTitleListener = circleParams.circleListeners.createTitleListener;
        init();
    }

    public void refreshText() {
        if (mTitleParams == null || mTitleView == null) return;
        mTitleView.setText(mTitleParams.text);
        if (mSubTitleView != null) {
            mSubTitleView.setText(mSubTitleParams.text);
        }
    }

    private void init() {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        createTitleLayout();
        //标题图标
        createTitleIcon();
        //标题
        createTitle();
        //副标题
        createSubTitle();
        if (mOnCreateTitleListener != null) {
            mOnCreateTitleListener.onCreateTitle(mTitleIcon, mTitleView, mSubTitleView);
        }
    }

    private void createTitleLayout() {
        mTitleLayout = new RelativeLayout(getContext());
        addView(mTitleLayout);

        mTitleLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mTitleLayout.setGravity(mTitleParams.gravity);
        mTitleLayout.setPadding(50, 0, 50, 0);

        //如果标题没有背景色，则使用默认色
        int backgroundColor = mTitleParams.backgroundColor != 0
                ? mTitleParams.backgroundColor : mDialogParams.backgroundColor;
        BackgroundHelper.INSTANCE.handleTitleBackground(mTitleLayout, backgroundColor);
    }

    @NonNull
    private void createTitleIcon() {
        mTitleIcon = new ImageView(getContext());
        mTitleIcon.setId(android.R.id.icon);
        RelativeLayout.LayoutParams layoutParamsTitleIcon = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsTitleIcon.addRule(RelativeLayout.LEFT_OF, android.R.id.title);
        layoutParamsTitleIcon.addRule(RelativeLayout.CENTER_VERTICAL);
        mTitleIcon.setLayoutParams(layoutParamsTitleIcon);
        if (mTitleParams.icon != 0) {
            mTitleIcon.setImageResource(mTitleParams.icon);
            mTitleIcon.setVisibility(VISIBLE);
        } else {
            mTitleIcon.setVisibility(GONE);
        }
        mTitleLayout.addView(mTitleIcon);
    }

    @NonNull
    private void createTitle() {
        mTitleView = new TextView(getContext());
        mTitleLayout.addView(mTitleView);

        if (mDialogParams.typeface != null) {
            mTitleView.setTypeface(mDialogParams.typeface);
        }
        mTitleView.setGravity(Gravity.CENTER);
        mTitleView.setId(android.R.id.title);
        RelativeLayout.LayoutParams layoutParamsTitle = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsTitle.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mTitleView.setLayoutParams(layoutParamsTitle);
        if (mTitleParams.height != 0) {
            mTitleView.setHeight(Controller.dp2px(getContext(), mTitleParams.height));
        }
        mTitleView.setTextColor(mTitleParams.textColor);
        mTitleView.setTextSize(mTitleParams.textSize);
        mTitleView.setText(mTitleParams.text);
        mTitleView.setTypeface(mTitleView.getTypeface(), mTitleParams.styleText);

        int[] padding = mTitleParams.padding;
        if (padding == null) {
            return;
        }
        if (mTitleParams.isShowBottomDivider) {
            padding[3] = padding[3] == 0 ? padding[1] : padding[3];
            DividerView dividerView = new DividerView(getContext(), LinearLayout.HORIZONTAL);
            addView(dividerView);
        }
        mTitleView.setPadding(Controller.dp2px(getContext(), padding[0]), Controller.dp2px(getContext(), padding[1]),
                Controller.dp2px(getContext(), padding[2]), Controller.dp2px(getContext(), padding[3]));
    }

    @Nullable
    private void createSubTitle() {
        if (mSubTitleParams == null) {
            return;
        }
        mSubTitleView = new TextView(getContext());
        mSubTitleView.setId(android.R.id.summary);
        addView(mSubTitleView);

        if (mDialogParams.typeface != null) {
            mSubTitleView.setTypeface(mDialogParams.typeface);
        }
        mSubTitleView.setGravity(Gravity.CENTER);
        setSubTitleBg(mSubTitleView, mSubTitleParams.backgroundColor, mDialogParams.backgroundColor);
        mSubTitleView.setGravity(mSubTitleParams.gravity);
        if (mSubTitleParams.height != 0) {
            mSubTitleView.setHeight(Controller.dp2px(getContext(), mSubTitleParams.height));
        }
        mSubTitleView.setTextColor(mSubTitleParams.textColor);
        mSubTitleView.setTextSize(mSubTitleParams.textSize);
        mSubTitleView.setText(mSubTitleParams.text);
        mSubTitleView.setTypeface(mSubTitleView.getTypeface(), mSubTitleParams.styleText);

        int[] padding = mSubTitleParams.padding;
        if (padding == null) {
            return;
        }
        if (mSubTitleParams.isShowBottomDivider) {
            padding[3] = padding[3] == 0 ? padding[1] : padding[3];
            DividerView dividerView = new DividerView(getContext(), LinearLayout.HORIZONTAL);
            addView(dividerView);
        }
        mSubTitleView.setPadding(Controller.dp2px(getContext(), padding[0]), Controller.dp2px(getContext(),
                padding[1]), Controller.dp2px(getContext(), padding[2]), Controller.dp2px(getContext(), padding[3]));
    }

    private void setSubTitleBg(TextView subTitle, int tbg, int dbg) {
        //如果标题没有背景色，则使用默认色
        int bgColor = tbg != 0 ? tbg : dbg;
        subTitle.setBackgroundColor(bgColor);
    }
}
