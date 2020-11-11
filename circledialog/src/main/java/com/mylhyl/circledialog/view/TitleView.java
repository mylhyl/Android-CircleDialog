package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
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
        if (mTitleParams == null || mTitleView == null) {
            return;
        }
        mTitleView.setText(mTitleParams.text);
        if (mSubTitleView != null) {
            mSubTitleView.setText(mSubTitleParams.text);
        }
    }

    private void init() {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);

        // 标题
        mTitleView = createTitle();

        ImageView titleIcon = null;
        // 有图标
        if (mTitleParams.icon != 0) {
            RelativeLayout titleLayout = createTitleLayout();
            addView(titleLayout);

            // 标题图标
            titleIcon = createTitleIcon();

            titleLayout.addView(titleIcon);
            titleLayout.addView(mTitleView);
        }
        // 没有图标
        else {
            mTitleView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            mTitleView.setGravity(mTitleParams.gravity);

            handleTitleBackground(mTitleView);
            addView(mTitleView);
        }

        //副标题
        createSubTitle();

        if (mOnCreateTitleListener != null) {
            mOnCreateTitleListener.onCreateTitle(titleIcon, mTitleView, mSubTitleView);
        }
    }

    private RelativeLayout createTitleLayout() {
        RelativeLayout titleLayout = new RelativeLayout(getContext());
        titleLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        titleLayout.setGravity(mTitleParams.gravity);
        titleLayout.setPadding(50, 0, 50, 0);
        handleTitleBackground(titleLayout);

        return titleLayout;
    }

    private void handleTitleBackground(View view) {
        //如果标题没有背景色，则使用默认色
        int backgroundColor = mTitleParams.backgroundColor != 0
                ? mTitleParams.backgroundColor : mDialogParams.backgroundColor;
        BackgroundHelper.handleTitleBackground(view, backgroundColor, mDialogParams);
    }

    @NonNull
    private ImageView createTitleIcon() {
        ImageView titleIcon = new ImageView(getContext());
        titleIcon.setId(android.R.id.icon);
        RelativeLayout.LayoutParams layoutParamsTitleIcon = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsTitleIcon.addRule(RelativeLayout.LEFT_OF, android.R.id.title);
        layoutParamsTitleIcon.addRule(RelativeLayout.CENTER_VERTICAL);
        titleIcon.setLayoutParams(layoutParamsTitleIcon);
        titleIcon.setImageResource(mTitleParams.icon);
        titleIcon.setVisibility(VISIBLE);
        return titleIcon;
    }

    @NonNull
    private TextView createTitle() {
        TextView titleView = new TextView(getContext());

        if (mDialogParams.typeface != null) {
            titleView.setTypeface(mDialogParams.typeface);
        }
        titleView.setGravity(Gravity.CENTER);
        titleView.setId(android.R.id.title);
        RelativeLayout.LayoutParams layoutParamsTitle = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsTitle.addRule(RelativeLayout.CENTER_HORIZONTAL);
        titleView.setLayoutParams(layoutParamsTitle);
        if (mTitleParams.height != 0) {
            titleView.setHeight(Controller.dp2px(getContext(), mTitleParams.height));
        }
        titleView.setTextColor(mTitleParams.textColor);
        titleView.setTextSize(mTitleParams.textSize);
        titleView.setText(mTitleParams.text);
        titleView.setTypeface(titleView.getTypeface(), mTitleParams.styleText);

        int[] padding = mTitleParams.padding;
        if (padding != null) {
            if (mTitleParams.isShowBottomDivider) {
                padding[3] = padding[3] == 0 ? padding[1] : padding[3];
                DividerView dividerView = new DividerView(getContext(), LinearLayout.HORIZONTAL);
                addView(dividerView);
            }
            titleView.setPadding(Controller.dp2px(getContext(), padding[0]), Controller.dp2px(getContext(), padding[1]),
                    Controller.dp2px(getContext(), padding[2]), Controller.dp2px(getContext(), padding[3]));
        }
        return titleView;
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
