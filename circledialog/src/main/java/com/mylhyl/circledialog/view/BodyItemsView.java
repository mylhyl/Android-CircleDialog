package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.params.ItemsParams;
import com.mylhyl.circledialog.params.TitleParams;
import com.mylhyl.circledialog.res.drawable.SelectorBtn;
import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.view.listener.ItemsView;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hupei on 2017/3/30.
 *
 * @hide
 */

public final class BodyItemsView extends ListView implements Controller.OnClickListener, ItemsView {
    private BaseAdapter mAdapter;
    private CircleParams mParams;
    private TitleParams mTitleParams;
    private int mRadius;
    private int mBackgroundColor;
    private int mBackgroundColorPress;

    public BodyItemsView(Context context, CircleParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, final CircleParams params) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams
                .MATCH_PARENT, LayoutParams
                .MATCH_PARENT, 1);
        setLayoutParams(layoutParams);
        this.mParams = params;
        this.mTitleParams = params.titleParams;
        ItemsParams itemsParams = params.itemsParams;

        this.mRadius = mParams.dialogParams.radius;
        //如果没有背景色，则使用默认色
        this.mBackgroundColor = itemsParams.backgroundColor != 0
                ? itemsParams.backgroundColor : mParams.dialogParams.backgroundColor;
        this.mBackgroundColorPress = itemsParams.backgroundColorPress != 0
                ? itemsParams.backgroundColorPress : mParams.dialogParams.backgroundColorPress;

        final SelectorBtn listViewBg = new SelectorBtn(mBackgroundColor, mBackgroundColor
                , mTitleParams != null ? 0 : mRadius, mTitleParams != null ? 0 : mRadius
                , mRadius, mRadius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(listViewBg);
        } else {
            setBackgroundDrawable(listViewBg);
        }

        setSelector(new ColorDrawable(Color.TRANSPARENT));
        setDivider(new ColorDrawable(CircleColor.divider));
        setDividerHeight(1);

        mAdapter = itemsParams.adapter;
        if (mAdapter == null) {
            mAdapter = new ItemsAdapter(context, params);
        }
        setAdapter(mAdapter);
    }

    @Override
    public void regOnItemClickListener(OnItemClickListener onItemClickListener) {
        setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void regOnItemClickListener(OnRvItemClickListener listener) {

    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void refreshItems() {
        post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view, int which) {
        if (mParams.itemListener != null) {
            mParams.itemListener.onItemClick((AdapterView<?>) view, view, which, which);
        }
    }

    /****
     * 拦截触摸事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                int position = pointToPosition(x, y);
                if (position == AdapterView.INVALID_POSITION)
                    break;
                else {
                    //top
                    if (position == 0 && mTitleParams == null) {
                        if (position == (getAdapter().getCount() - 1)) {
                            final SelectorBtn selectorTopOne = new SelectorBtn(mBackgroundColor
                                    , mBackgroundColorPress, mRadius, mRadius, mRadius, mRadius);
                            setSelector(selectorTopOne);
                        } else {
                            final SelectorBtn selectorTop = new SelectorBtn(mBackgroundColor
                                    , mBackgroundColorPress, mRadius, mRadius, 0, 0);
                            setSelector(selectorTop);
                        }
                    }
                    //bottom
                    else if (position == (getAdapter().getCount() - 1)) {
                        // 最后一项
                        final SelectorBtn selectorBottom = new SelectorBtn(mBackgroundColor
                                , mBackgroundColorPress, 0, 0, mRadius, mRadius);
                        setSelector(selectorBottom);
                    }
                    //middle
                    else {
                        // 中间项
                        final SelectorBtn selectorMiddle = new SelectorBtn(mBackgroundColor
                                , mBackgroundColorPress, 0, 0, 0, 0);
                        setSelector(selectorMiddle);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    static class ItemsAdapter<T> extends BaseAdapter {
        class ViewHolder {
            TextView item;
        }

        private Context mContext;
        private List<T> mItems;
        private ItemsParams mItemsParams;

        public ItemsAdapter(Context context, CircleParams params) {
            this.mContext = context;
            this.mItemsParams = params.itemsParams;

            Object entity = this.mItemsParams.items;
            if (entity != null && entity instanceof Iterable) {
                this.mItems = (List<T>) entity;
            } else if (entity != null && entity.getClass().isArray()) {
                this.mItems = Arrays.asList((T[]) entity);
            } else {
                throw new IllegalArgumentException("entity must be an Array or an Iterable.");
            }
        }

        @Override
        public int getCount() {
            if (mItems != null)
                return mItems.size();
            return 0;
        }

        @Override
        public T getItem(int position) {
            if (mItems != null)
                return mItems.get(position);
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                ScaleTextView textView = new ScaleTextView(mContext);
                textView.setTextSize(mItemsParams.textSize);
                textView.setTextColor(mItemsParams.textColor);
                textView.setHeight(mItemsParams.itemHeight);
                viewHolder.item = textView;
                convertView = textView;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.item.setText(String.valueOf(getItem(position).toString()));
            return convertView;
        }
    }
}
