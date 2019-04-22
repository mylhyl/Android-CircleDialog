package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mylhyl.circledialog.callback.CircleItemLabel;
import com.mylhyl.circledialog.callback.CircleItemViewBinder;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.ItemsParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawableSelector;
import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.view.listener.ItemsView;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hupei on 2017/3/30.
 */

final class BodyListView extends ListView implements ItemsView {
    private BaseAdapter mAdapter;
    private DialogParams mDialogParams;
    private ItemsParams mItemsParams;
    private int mBackgroundColor;
    private int mBackgroundColorPress;

    public BodyListView(Context context, DialogParams dialogParams, ItemsParams itemsParams) {
        super(context);
        this.mDialogParams = dialogParams;
        this.mItemsParams = itemsParams;
        init();
    }

    private void init() {

        //如果没有背景色，则使用默认色
        this.mBackgroundColor = mItemsParams.backgroundColor != 0
                ? mItemsParams.backgroundColor : mDialogParams.backgroundColor;
        this.mBackgroundColorPress = mItemsParams.backgroundColorPress != 0
                ? mItemsParams.backgroundColorPress : mDialogParams.backgroundColorPress;

        setBackgroundColor(mBackgroundColor);

        CircleDrawableSelector bgItemNotRadius = new CircleDrawableSelector(Color.TRANSPARENT, mBackgroundColorPress);

        setSelector(bgItemNotRadius);
        setDivider(new ColorDrawable(CircleColor.divider));
        setDividerHeight(mItemsParams.dividerHeight);

        mAdapter = mItemsParams.adapter;
        if (mAdapter == null) {
            mAdapter = new ItemsAdapter(getContext(), mItemsParams);
        }
        setAdapter(mAdapter);
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
    public void regOnItemClickListener(AdapterView.OnItemClickListener listener) {
        setOnItemClickListener(listener);
    }

    @Override
    public void regOnItemClickListener(OnRvItemClickListener listener) {

    }

    @Override
    public View getView() {
        return this;
    }

    static class ItemsAdapter<T> extends BaseAdapter {
        private Context mContext;
        private List<T> mItems;
        private ItemsParams mItemsParams;

        public ItemsAdapter(Context context, ItemsParams params) {
            this.mContext = context;
            this.mItemsParams = params;

            Object entity = mItemsParams.items;
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
                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(mItemsParams.textSize);
                textView.setTextColor(mItemsParams.textColor);
                textView.setHeight(mItemsParams.itemHeight);
                if (mItemsParams.padding != null) {
                    textView.setPadding(mItemsParams.padding[0], mItemsParams.padding[1]
                            , mItemsParams.padding[2], mItemsParams.padding[3]);
                }
                if (mItemsParams.textGravity != Gravity.NO_GRAVITY)
                    textView.setGravity(mItemsParams.textGravity);
                viewHolder.item = textView;
                convertView = textView;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            bindView(position, viewHolder);

            return convertView;
        }

        private void bindView(int position, ViewHolder viewHolder) {
            String label;
            T item = getItem(position);
            if (item instanceof CircleItemLabel) {
                label = ((CircleItemLabel) item).getItemLabel();
            } else {
                label = item.toString();
            }
            viewHolder.item.setText(String.valueOf(label));

            CircleItemViewBinder viewBinder = mItemsParams.viewBinder;
            if (viewBinder != null) {
                viewBinder.onBinder(viewHolder.item, item, position);
            }
        }

        class ViewHolder {
            TextView item;
        }
    }
}
