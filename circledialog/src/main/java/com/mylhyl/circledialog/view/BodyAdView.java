package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mylhyl.circledialog.params.AdParams;
import com.mylhyl.circledialog.params.DialogParams;

/**
 * 广告
 * Created by hupei on 2019/1/11 11:01.
 */
public class BodyAdView extends RelativeLayout {
    private DialogParams mDialogParams;
    private AdParams mAdParams;
    private ImageView mImageView;

    public BodyAdView(Context context, DialogParams dialogParams, AdParams adParams) {
        super(context);
        this.mDialogParams = dialogParams;
        this.mAdParams = adParams;
        init();
    }

    private void init() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        mImageView = new ImageView(getContext());
        mImageView.setId(android.R.id.icon);
        mImageView.setImageResource(mAdParams.imageResId);
        mImageView.setAdjustViewBounds(true);
        addView(mImageView, layoutParams);
    }
}
