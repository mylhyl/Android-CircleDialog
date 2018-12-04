package com.mylhyl.circledialog.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.SubTitleParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.view.listener.OnCreateTitleListener;

/**
 * 对话框标题
 * Created by hupei on 2017/3/29.
 */
final class TitleView extends LinearLayout {
    private RelativeLayout mTitleLayout;

    public TitleView(Context context, CircleParams params) {
        super(context);
        if (params.titleParams == null) return;
        init(params);
    }

    private void init(CircleParams params) {

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);

        DialogParams dialogParams = params.dialogParams;
        TitleParams titleParams = params.titleParams;
        SubTitleParams subTitleParams = params.subTitleParams;

        createTitleLayout(dialogParams, titleParams);

        //标题图标
        ImageView ivTitleIcon = createTitleIcon(titleParams);
        //标题
        final TextView tvTitle = createTitle(titleParams);

        //副标题
        TextView tvSubTitle = createSubTitle(dialogParams, subTitleParams);
        OnCreateTitleListener createTitleListener = params.createTitleListener;
        if (createTitleListener != null) {
            createTitleListener.onCreateTitle(ivTitleIcon, tvTitle, tvSubTitle);
        }
    }

    private void createTitleLayout(DialogParams dialogParams, TitleParams titleParams) {
        mTitleLayout = new RelativeLayout(getContext());
        mTitleLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT
                , LayoutParams.WRAP_CONTENT));
        mTitleLayout.setGravity(titleParams.gravity);
        mTitleLayout.setPadding(50, 0, 50, 0);

        //如果标题没有背景色，则使用默认色
        int bg = titleParams.backgroundColor != 0 ? titleParams.backgroundColor : dialogParams.backgroundColor;
        mTitleLayout.setBackgroundColor(bg);
    }

    @NonNull
    private ImageView createTitleIcon(TitleParams titleParams) {
        ImageView ivTitleIcon = new ImageView(getContext());
        RelativeLayout.LayoutParams layoutParamsTitleIcon = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsTitleIcon.addRule(RelativeLayout.LEFT_OF, android.R.id.title);
        layoutParamsTitleIcon.addRule(RelativeLayout.CENTER_VERTICAL);
        ivTitleIcon.setLayoutParams(layoutParamsTitleIcon);
        if (titleParams.icon != 0) {
            ivTitleIcon.setImageResource(titleParams.icon);
            ivTitleIcon.setVisibility(VISIBLE);
        } else {
            ivTitleIcon.setVisibility(GONE);
        }
        mTitleLayout.addView(ivTitleIcon);
        return ivTitleIcon;
    }

    @NonNull
    private TextView createTitle(TitleParams titleParams) {
        final TextView tvTitle = new TextView(getContext());
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setId(android.R.id.title);
        RelativeLayout.LayoutParams layoutParamsTitle = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsTitle.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tvTitle.setLayoutParams(layoutParamsTitle);
        if (titleParams.height != 0)
            tvTitle.setHeight(titleParams.height);
        tvTitle.setTextColor(titleParams.textColor);
        tvTitle.setTextSize(titleParams.textSize);
        tvTitle.setText(titleParams.text);
        int[] padding = titleParams.padding;
        if (padding != null)
            tvTitle.setPadding(padding[0], padding[1], padding[2], padding[3]);
        tvTitle.setTypeface(tvTitle.getTypeface(), titleParams.styleText);
        mTitleLayout.addView(tvTitle);
        addView(mTitleLayout);
        return tvTitle;
    }

    @Nullable
    private TextView createSubTitle(DialogParams dialogParams, SubTitleParams subTitleParams) {
        TextView tvSubTitle = null;
        if (subTitleParams != null) {
            tvSubTitle = new TextView(getContext());
            tvSubTitle.setGravity(Gravity.CENTER);
            setSubTitleBg(tvSubTitle, subTitleParams.backgroundColor, dialogParams.backgroundColor);
            tvSubTitle.setGravity(subTitleParams.gravity);
            if (subTitleParams.height != 0)
                tvSubTitle.setHeight(subTitleParams.height);
            tvSubTitle.setTextColor(subTitleParams.textColor);
            tvSubTitle.setTextSize(subTitleParams.textSize);
            tvSubTitle.setText(subTitleParams.text);
            int[] padding = subTitleParams.padding;
            if (padding != null)
                tvSubTitle.setPadding(padding[0], padding[1], padding[2], padding[3]);
            tvSubTitle.setTypeface(tvSubTitle.getTypeface(), subTitleParams.styleText);
            addView(tvSubTitle);
        }
        return tvSubTitle;
    }

    private void setSubTitleBg(TextView tvSubTitle, int tbg, int dbg) {
        //如果标题没有背景色，则使用默认色
        int bg = tbg != 0 ? tbg : dbg;
        tvSubTitle.setBackgroundColor(bg);
    }
}
