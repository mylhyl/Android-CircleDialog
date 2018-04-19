package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
 * Created by hupei on 2018/4/18.
 */

public final class BodyItemsRvView extends RecyclerView implements Controller.OnClickListener, ItemsView {
    private ItemsAdapter mAdapter;
    private CircleParams mParams;

    public BodyItemsRvView(Context context, CircleParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, final CircleParams params) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams
                .MATCH_PARENT, LayoutParams
                .MATCH_PARENT, 1);
        setLayoutParams(layoutParams);
        this.mParams = params;

        ItemDecoration itemDecoration = params.itemsParams.itemDecoration;
        if (itemDecoration == null)
            itemDecoration = new DividerItemDecoration(getContext()
                    , new ColorDrawable(CircleColor.divider), 1);
        addItemDecoration(itemDecoration);
        if (params.itemsParams.layoutManager != null)
            setLayoutManager(params.itemsParams.layoutManager);
        else
            setLayoutManager(new LinearLayoutManager(getContext()));

        if (params.itemsParams.adapterRv == null) {
            mAdapter = new ItemsAdapter(context, mParams);
            setAdapter(mAdapter);
        } else {
            TitleParams titleParams = params.titleParams;
            ItemsParams itemsParams = params.itemsParams;

            int radius = mParams.dialogParams.radius;
            //如果没有背景色，则使用默认色
            int backgroundColor = itemsParams.backgroundColor != 0
                    ? itemsParams.backgroundColor : mParams.dialogParams.backgroundColor;

            final SelectorBtn RvBg = new SelectorBtn(backgroundColor, backgroundColor
                    , titleParams != null ? 0 : radius, titleParams != null ? 0 : radius
                    , radius, radius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(RvBg);
            } else {
                setBackgroundDrawable(RvBg);
            }
            setAdapter(params.itemsParams.adapterRv);
        }
    }

    @Override
    public void regOnItemClickListener(OnRvItemClickListener listener) {
        if (mAdapter != null)
            mAdapter.setOnItemClickListener(listener);
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
    public void regOnItemClickListener(AdapterView.OnItemClickListener listener) {

    }

    @Override
    public void onClick(View view, int which) {
        if (mParams.rvItemListener != null) {
            mParams.rvItemListener.onItemClick(view, which);
        }
    }

    class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;
        private int mDividerHeight;

        public DividerItemDecoration(Context context, Drawable divider, int dividerHeight) {
            mDivider = divider;
            mDividerHeight = dividerHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDividerHeight);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDividerHeight;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    static class ItemsAdapter<T> extends Adapter<ItemsAdapter.Holder> {

        static class Holder extends RecyclerView.ViewHolder implements OnClickListener {
            OnRvItemClickListener mItemClickListener;
            TextView item;

            public Holder(TextView itemView, OnRvItemClickListener listener) {
                super(itemView);
                item = itemView;
                mItemClickListener = listener;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        }

        private OnRvItemClickListener mItemClickListener;
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
                    ? mItemsParams.backgroundColor : params.dialogParams.backgroundColor;
            this.mBackgroundColorPress = mItemsParams.backgroundColorPress != 0
                    ? mItemsParams.backgroundColorPress : params.dialogParams.backgroundColorPress;

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
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            ScaleTextView textView = new ScaleTextView(mContext);
            textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            textView.setTextSize(mItemsParams.textSize);
            textView.setTextColor(mItemsParams.textColor);
            textView.setHeight(mItemsParams.itemHeight);
            Holder holder = new Holder(textView, mItemClickListener);
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            //top
            if (position == 0 && mTitleParams == null) {
                if (getItemCount() == 1) {
                    final SelectorBtn selectorBtn = new SelectorBtn(mBackgroundColor
                            , mBackgroundColorPress, mRadius, mRadius, mRadius, mRadius);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.item.setBackground(selectorBtn);
                    } else {
                        holder.item.setBackgroundDrawable(selectorBtn);
                    }
                } else {
                    final SelectorBtn selectorBtn = new SelectorBtn(mBackgroundColor
                            , mBackgroundColorPress, mRadius, mRadius, 0, 0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.item.setBackground(selectorBtn);
                    } else {
                        holder.item.setBackgroundDrawable(selectorBtn);
                    }
                }
            }
            //bottom
            else if (position == getItemCount() - 1) {
                final SelectorBtn selectorBtn = new SelectorBtn(mBackgroundColor
                        , mBackgroundColorPress, 0, 0, mRadius, mRadius);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.item.setBackground(selectorBtn);
                } else {
                    holder.item.setBackgroundDrawable(selectorBtn);
                }
            }
            //middle
            else {
                final SelectorBtn selectorBtn = new SelectorBtn(mBackgroundColor
                        , mBackgroundColorPress, 0, 0, 0, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.item.setBackground(selectorBtn);
                } else {
                    holder.item.setBackgroundDrawable(selectorBtn);
                }
            }

            holder.item.setText(String.valueOf(mItems.get(position).toString()));
        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }

        public void setOnItemClickListener(OnRvItemClickListener listener) {
            this.mItemClickListener = listener;
        }
    }
}
