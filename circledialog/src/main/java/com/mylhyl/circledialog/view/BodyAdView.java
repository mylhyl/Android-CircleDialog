package com.mylhyl.circledialog.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mylhyl.circledialog.engine.ImageLoadEngine;
import com.mylhyl.circledialog.params.AdParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.view.listener.AdView;

/**
 * 广告
 * Created by hupei on 2019/1/11 11:01.
 */
public class BodyAdView extends RelativeLayout implements AdView {
    private DialogParams mDialogParams;
    private AdParams mAdParams;
    private ImageView mImageView;
    private ImageView mImageCloseView;
    private ImageLoadEngine mImageLoadEngine;

    public BodyAdView(Context context, DialogParams dialogParams, AdParams adParams
            , ImageLoadEngine imageLoadEngine) {
        super(context);
        this.mDialogParams = dialogParams;
        this.mAdParams = adParams;
        this.mImageLoadEngine = imageLoadEngine;
        init();
    }

    private void init() {
        //关闭按钮
        LayoutParams layoutParamsClose = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        if (mAdParams.closeSize != 0) {
            layoutParamsClose.width = layoutParamsClose.height = mAdParams.closeSize;
        }
        if (mAdParams.closeMargins != null && mAdParams.closeMargins.length == 4)
            layoutParamsClose.setMargins(mAdParams.closeMargins[0], mAdParams.closeMargins[1]
                    , mAdParams.closeMargins[2], mAdParams.closeMargins[3]);
        layoutParamsClose.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, android.R.id.icon);
        mImageCloseView = new ImageView(getContext());
        mImageCloseView.setId(android.R.id.icon);
        if (mAdParams.closeResId != 0) {
            mImageCloseView.setImageResource(mAdParams.closeResId);
        }
        mImageCloseView.setAdjustViewBounds(true);
        addView(mImageCloseView, layoutParamsClose);

        LayoutParams layoutParamsAd = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        layoutParamsAd.addRule(RelativeLayout.BELOW, android.R.id.icon);
        mImageView = new ImageView(getContext());
        if (mImageLoadEngine != null && mAdParams.urls != null && !mAdParams.urls.isEmpty()) {
            mImageLoadEngine.loadImage(getContext(), mImageView, mAdParams.urls.get(0));
        } else if (mAdParams.imageResId != 0) {
            mImageView.setImageResource(mAdParams.imageResId);
        }
        mImageView.setAdjustViewBounds(true);
        addView(mImageView, layoutParamsAd);

    }

    @Override
    public void regOnCloseClickListener(OnClickListener listener) {
        if (mImageCloseView != null) {
            mImageCloseView.setOnClickListener(listener);
        }
    }

    @Override
    public void regOnImageClickListener(OnClickListener listener) {
        if (mImageView != null) {
            mImageView.setOnClickListener(listener);
        }
    }

    @Override
    public View getView() {
        return this;
    }
}
