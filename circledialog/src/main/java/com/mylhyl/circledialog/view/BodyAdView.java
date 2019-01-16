package com.mylhyl.circledialog.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.mylhyl.circledialog.engine.ImageLoadEngine;
import com.mylhyl.circledialog.params.AdParams;
import com.mylhyl.circledialog.view.listener.AdView;
import com.mylhyl.circledialog.view.listener.OnAdItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告
 * Created by hupei on 2019/1/11 11:01.
 */
public class BodyAdView extends WrapViewPage implements AdView {
    private AdParams mAdParams;

    private ImageLoadEngine mImageLoadEngine;
    private List<View> mViews;
    private List<String> mUrls;
    private OnAdItemClickListener mImageClickListener;

    public BodyAdView(Context context, AdParams adParams, ImageLoadEngine imageLoadEngine) {
        super(context);
        this.mAdParams = adParams;
        this.mImageLoadEngine = imageLoadEngine;
        init();
    }

    private void init() {
        mViews = new ArrayList<>();

//        if (mAdParams.closeGravity == AdParams.CLOSE_TOP_LEFT
//                || mAdParams.closeGravity == AdParams.CLOSE_TOP_CENTER
//                || mAdParams.closeGravity == AdParams.CLOSE_TOP_RIGHT) {
//            addView(mImageCloseView, layoutParamsClose);
//            addView(mViewPager, layoutParamsAd);
//        } else {
//            addView(mViewPager, layoutParamsAd);
//            addView(mImageCloseView, layoutParamsClose);
//        }

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

        setOverScrollMode(OVER_SCROLL_NEVER);
        setAdapter(new PageAdapter());
    }

    @Override
    public void regOnImageClickListener(OnAdItemClickListener listener) {
        mImageClickListener = listener;
    }

    @Override
    public View getView() {
        return this;
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
                        int position = getCurrentItem() % mViews.size();
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
