package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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

import java.util.Arrays;
import java.util.List;

/**
 * Created by hupei on 2017/3/30.
 *
 * @hide
 */

public final class BodyItemsView extends ListView implements Controller.OnClickListener {
    private BaseAdapter mAdapter;
    private CircleParams params;

    public BodyItemsView(Context context, CircleParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, final CircleParams params) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams
                .MATCH_PARENT, LayoutParams
                .MATCH_PARENT,1);
        setLayoutParams(layoutParams);
        this.params = params;
        setSelector(new ColorDrawable(Color.TRANSPARENT));
        setDivider(new ColorDrawable(CircleColor.divider));
        setDividerHeight(1);

        mAdapter = new ItemsAdapter(context, params);
        setAdapter(mAdapter);
    }

    public void regOnItemClickListener(OnItemClickListener onItemClickListener) {
        setOnItemClickListener(onItemClickListener);
    }

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
        if (params.itemListener != null) {
            params.itemListener.onItemClick((AdapterView<?>) view, view, which, which);
        }
    }


    static class ItemsAdapter<T> extends BaseAdapter {
        class ViewHolder {
            TextView item;
        }

        private Context mContext;
        private List<T> mItems;
        private int mRadius;
        private int mBackgroundColor;
        private int mBackgroundColorPress;
        private ItemsParams mItemsParams;
        private TitleParams mTitleParams;

        public ItemsAdapter(Context context, CircleParams params) {
            this.mContext = context;
            this.mTitleParams = params.titleParams;
            this.mItemsParams = params.itemsParams;
            this.mRadius = params.dialogParams.radius;
            //如果没有背景色，则使用默认色
            this.mBackgroundColor = mItemsParams.backgroundColor != 0
                    ? mItemsParams.backgroundColor : params.dialogParams.backgroundColor == 0
                    ? CircleColor.bgDialog : params.dialogParams.backgroundColor;
            this.mBackgroundColorPress = mItemsParams.backgroundColorPress != 0
                    ? mItemsParams.backgroundColorPress : params.dialogParams.backgroundColorPress;
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

            //top
            if (position == 0 && mTitleParams == null) {
                if (getCount() == 1) {
                    final SelectorBtn selectorBtn = new SelectorBtn(mBackgroundColor
                            , mBackgroundColorPress, mRadius, mRadius, mRadius, mRadius);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        viewHolder.item.setBackground(selectorBtn);
                    } else {
                        viewHolder.item.setBackgroundDrawable(selectorBtn);
                    }
                } else {
                    final SelectorBtn selectorBtn = new SelectorBtn(mBackgroundColor
                            , mBackgroundColorPress, mRadius, mRadius, 0, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        viewHolder.item.setBackground(selectorBtn);
                    } else {
                        viewHolder.item.setBackgroundDrawable(selectorBtn);
                    }
                }
            }
            //bottom
            else if (position == getCount() - 1) {
                final SelectorBtn selectorBtn = new SelectorBtn(mBackgroundColor
                        , mBackgroundColorPress, 0, 0, mRadius, mRadius);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.item.setBackground(selectorBtn);
                } else {
                    viewHolder.item.setBackgroundDrawable(selectorBtn);
                }
            }
            //middle
            else {
                final SelectorBtn selectorBtn = new SelectorBtn(mBackgroundColor
                        , mBackgroundColorPress, 0, 0, 0, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewHolder.item.setBackground(selectorBtn);
                } else {
                    viewHolder.item.setBackgroundDrawable(selectorBtn);
                }
            }
            viewHolder.item.setText(String.valueOf(getItem(position).toString()));
            return convertView;
        }
    }
}
