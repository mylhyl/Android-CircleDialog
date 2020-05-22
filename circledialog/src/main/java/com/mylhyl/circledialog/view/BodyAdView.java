package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mylhyl.circledialog.engine.ImageLoadEngine;
import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;
import com.mylhyl.circledialog.params.AdParams;
import com.mylhyl.circledialog.view.listener.AdView;
import com.mylhyl.circledialog.view.listener.OnAdItemClickListener;
import com.mylhyl.circledialog.view.listener.OnAdPageChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告
 * Created by hupei on 2019/1/11 11:01.
 */
final class BodyAdView extends RelativeLayout implements AdView, ViewPager.OnPageChangeListener {
    private AdParams mAdParams;
    private ImageLoadEngine mImageLoadEngine;
    private ViewPager mViewPager;
    private LinearLayout mLlIndicator;
    private List<ImageView> mViews;
    private List<String> mUrls;
    private OnAdItemClickListener mImageClickListener;
    private OnAdPageChangeListener mPageChangeListener;

    public BodyAdView(Context context, CircleParams circleParams) {
        super(context);
        this.mAdParams = circleParams.adParams;
        this.mImageLoadEngine = circleParams.imageLoadEngine;
        this.mPageChangeListener = circleParams.circleListeners.adPageChangeListener;
        init();
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
        if (mPageChangeListener != null && mUrls != null) {
            mPageChangeListener.onPageScrolled(getContext(), mViews.get(position), mUrls.get(position), position,
                    positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        position = position % mViews.size();
        pageSelectedToPoint(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private void init() {
        initViewPager();
        //指示器
        initIndicator();
    }

    private void initViewPager() {
        mViewPager = new WrapViewPage(getContext());
        mViewPager.setId(android.R.id.list);
        mViews = new ArrayList<>();

        if (mAdParams.urls != null) {
            mUrls = new ArrayList<>();
            for (String url : mAdParams.urls) {
                ImageView imageView = new ImageView(getContext());
                imageView.setAdjustViewBounds(true);
                mViews.add(imageView);
                mUrls.add(url);
            }
        } else if (mAdParams.resIds != null) {
            for (int imageResId : mAdParams.resIds) {
                ImageView imageView = new ImageView(getContext());
                imageView.setAdjustViewBounds(true);
                imageView.setImageResource(imageResId);
                mViews.add(imageView);
            }
        }

        mViewPager.setAdapter(new PageAdapter());
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOverScrollMode(OVER_SCROLL_NEVER);
        addView(mViewPager);
    }

    private void initIndicator() {
        if (!mAdParams.isShowIndicator) {
            return;
        }
        if (mLlIndicator != null) {
            mLlIndicator.removeAllViews();
        }
        mLlIndicator = new LinearLayout(getContext());
        mLlIndicator.setOrientation(LinearLayout.HORIZONTAL);
        mLlIndicator.setGravity(Gravity.CENTER_VERTICAL);
        RelativeLayout.LayoutParams lpIndicator = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 80);
        lpIndicator.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lpIndicator.addRule(RelativeLayout.ALIGN_BOTTOM, android.R.id.list);
        mLlIndicator.setLayoutParams(lpIndicator);


        LinearLayout.LayoutParams lpPoint = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        int pointLeftRightMargin = Controller.dp2px(getContext(), mAdParams.pointLeftRightMargin);
        lpPoint.setMargins(pointLeftRightMargin, 0, pointLeftRightMargin, 0);
        for (int i = 0; i < mViews.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setSelected(true);
            imageView.setLayoutParams(lpPoint);
            if (mAdParams.pointDrawableResId != 0) {
                imageView.setImageResource(mAdParams.pointDrawableResId);
            } else {
                Drawable pointDrawable = new BuildViewAdImpl.SelectorPointDrawable(Color.WHITE, 20);
                imageView.setImageDrawable(pointDrawable);
            }
            mLlIndicator.addView(imageView);
        }
        addView(mLlIndicator);
        pageSelectedToPoint(0);
    }

    private void pageSelectedToPoint(int position) {
        if (!mAdParams.isShowIndicator || mLlIndicator == null) {
            return;
        }
        int childCount = mLlIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mLlIndicator.getChildAt(i);
            boolean selected = i == position;
            childAt.setSelected(selected);
            childAt.requestLayout();
        }
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
            ImageView view = mViews.get(finalPosition);
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
                mImageLoadEngine.loadImage(getContext(), view, mUrls.get(finalPosition));
            }
            if (mUrls != null && !mUrls.isEmpty() && mPageChangeListener != null) {
                mPageChangeListener.onPageSelected(getContext(), view, mUrls.get(finalPosition), finalPosition);
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
