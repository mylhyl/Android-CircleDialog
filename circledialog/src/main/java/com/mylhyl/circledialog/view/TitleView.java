package com.mylhyl.circledialog.view;

import android.content.Context;
import android.os.Build;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.SubTitleParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawable;
import com.mylhyl.circledialog.view.listener.OnCreateTitleListener;

/**
 * 对话框标题
 * Created by hupei on 2017/3/29.
 */
final class TitleView extends ScaleLinearLayout {

    public TitleView(Context context, CircleParams params) {
        super(context);
        init(params);
    }

    private void init(CircleParams params) {

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);

        DialogParams dialogParams = params.dialogParams;
        TitleParams titleParams = params.titleParams;
        SubTitleParams subTitleParams = params.subTitleParams;


        ScaleRelativeLayout titleLayout = new ScaleRelativeLayout(getContext());
        titleLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT
                , LayoutParams.WRAP_CONTENT));
        titleLayout.setGravity(titleParams.gravity);
        titleLayout.setPadding(50, 0, 50, 0);

        setTitleBg(titleLayout, params, titleParams.backgroundColor
                , dialogParams.backgroundColor, dialogParams.radius);
        //标题图标
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
        titleLayout.addView(ivTitleIcon);
        //标题
        final ScaleTextView tvTitle = new ScaleTextView(getContext());
        tvTitle.setId(android.R.id.title);
        RelativeLayout.LayoutParams layoutParamsTitle = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsTitle.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tvTitle.setLayoutParams(layoutParamsTitle);
        tvTitle.setHeight(titleParams.height);
        tvTitle.setTextColor(titleParams.textColor);
        tvTitle.setTextSize(titleParams.textSize);
        tvTitle.setText(titleParams.text);
        tvTitle.setTypeface(tvTitle.getTypeface(), titleParams.styleText);
        titleLayout.addView(tvTitle);
        addView(titleLayout);

        //副标题
        ScaleTextView tvSubTitle = null;
        if (subTitleParams != null) {
            tvSubTitle = new ScaleTextView(getContext());
            setSubTitleBg(tvSubTitle, subTitleParams.backgroundColor, dialogParams.backgroundColor);
            tvSubTitle.setGravity(subTitleParams.gravity);
            if (subTitleParams.height != 0)
                tvSubTitle.setHeight(subTitleParams.height);
            tvSubTitle.setTextColor(subTitleParams.textColor);
            tvSubTitle.setTextSize(subTitleParams.textSize);
            tvSubTitle.setText(subTitleParams.text);
            int[] padding = subTitleParams.padding;
            if (padding != null)
                tvSubTitle.setAutoPadding(padding[0], padding[1], padding[2], padding[3]);
            tvSubTitle.setTypeface(tvSubTitle.getTypeface(), subTitleParams.styleText);
            addView(tvSubTitle);
        }
        OnCreateTitleListener createTitleListener = params.createTitleListener;
        if (createTitleListener != null) {
            createTitleListener.onCreateTitle(ivTitleIcon, tvTitle, tvSubTitle);
        }
    }

    private void setTitleBg(ScaleRelativeLayout tv, CircleParams params, int tbg, int dbg, int radius) {
        //如果标题没有背景色，则使用默认色
        int bg = tbg != 0 ? tbg : dbg;

        //有内容则顶部圆角
        if (params.textParams != null || params.itemsParams != null || params.progressParams != null
                || params.inputParams != null || params.bodyViewId != 0 || params.lottieParams != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                tv.setBackground(new CircleDrawable(bg, radius, radius, 0, 0));
            } else {
                tv.setBackgroundDrawable(new CircleDrawable(bg, radius, radius, 0, 0));
            }
        }
        //无内容则全部圆角
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                tv.setBackground(new CircleDrawable(bg, radius));
            } else {
                tv.setBackgroundDrawable(new CircleDrawable(bg, radius));
            }
        }
    }

    private void setSubTitleBg(ScaleTextView tv, int tbg, int dbg) {
        //如果标题没有背景色，则使用默认色
        int bg = tbg != 0 ? tbg : dbg;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tv.setBackground(new CircleDrawable(bg, 0));
        } else {
            tv.setBackgroundDrawable(new CircleDrawable(bg, 0));
        }
    }
}
