package com.mylhyl.circledialog.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mylhyl.circledialog.engine.ImageLoadEngine;
import com.mylhyl.circledialog.params.AdParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.view.listener.AdView;
import com.mylhyl.circledialog.view.listener.OnAdItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告
 * Created by hupei on 2019/1/11 11:01.
 */
public class BodyAdView extends RelativeLayout implements AdView, ViewPager.OnPageChangeListener {
    private DialogParams mDialogParams;
    private AdParams mAdParams;
    private ImageView mImageCloseView;
    private ViewPager mViewPager;
    private ImageLoadEngine mImageLoadEngine;
    private List<View> mViews;
    private List<String> mUrls;
    private OnAdItemClickListener mImageClickListener;

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
        mImageCloseView = new ImageView(getContext());
        LayoutParams layoutParamsClose = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        if (mAdParams.closeSize != 0) {
            layoutParamsClose.width = layoutParamsClose.height = mAdParams.closeSize;
        }
        if (mAdParams.closeMargins != null && mAdParams.closeMargins.length == 4)
            layoutParamsClose.setMargins(mAdParams.closeMargins[0], mAdParams.closeMargins[1]
                    , mAdParams.closeMargins[2], mAdParams.closeMargins[3]);
        layoutParamsClose.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, android.R.id.icon);
        mImageCloseView.setId(android.R.id.icon);
        if (mAdParams.closeResId != 0) {
            mImageCloseView.setImageResource(mAdParams.closeResId);
        }
        mImageCloseView.setAdjustViewBounds(true);
        addView(mImageCloseView, layoutParamsClose);

        //内容
        mViewPager = new WrapViewPage(getContext());
        mViews = new ArrayList<>();

        LayoutParams layoutParamsAd = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        layoutParamsAd.addRule(RelativeLayout.BELOW, android.R.id.icon);
        addView(mViewPager, layoutParamsAd);

        ImageView imageView;
        if (mAdParams.urls != null) {
            mUrls = new ArrayList<>();
            for (String url : mAdParams.urls) {
                imageView = new ImageView(getContext());
                imageView.setAdjustViewBounds(true);
                mViews.add(imageView);
                mUrls.add(url);
            }
        } else if (mAdParams.imageResIds != null) {
            for (int imageResId : mAdParams.imageResIds) {
                imageView = new ImageView(getContext());
                imageView.setAdjustViewBounds(true);
                imageView.setImageResource(imageResId);
                mViews.add(imageView);
            }
        }

        mViewPager.setAdapter(new PageAdapter());
        mViewPager.setOverScrollMode(OVER_SCROLL_NEVER);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void regOnCloseClickListener(OnClickListener listener) {
        if (mImageCloseView != null) {
            mImageCloseView.setOnClickListener(listener);
        }
    }

    @Override
    public void regOnImageClickListener(OnAdItemClickListener listener) {
        mImageClickListener = listener;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class PageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViews == null ? 0 : mViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (mViews == null || mViews.get(position) == null) {
                return null;
            }
            final int finalPosition = position % mViews.size();
            View view = mViews.get(finalPosition);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mImageClickListener != null) {
                        int position = mViewPager.getCurrentItem() % mViews.size();
                        mImageClickListener.onItemClick(v, position);
                    }
                }
            });
            if (mUrls != null && !mUrls.isEmpty() && mImageLoadEngine != null) {
                mImageLoadEngine.loadImage(getContext(), (ImageView) view, mUrls.get(finalPosition));
            }
            ViewParent viewParent = view.getParent();
            if (viewParent != null) {
                ((ViewGroup) viewParent).removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
