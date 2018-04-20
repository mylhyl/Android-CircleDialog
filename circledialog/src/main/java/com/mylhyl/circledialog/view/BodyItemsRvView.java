package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
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
    private Adapter mAdapter;
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
        ItemsParams itemsParams = params.itemsParams;

        ItemDecoration itemDecoration = itemsParams.itemDecoration;
        if (itemDecoration == null)
            itemDecoration = new DividerItemDecoration(getContext()
                    , new ColorDrawable(CircleColor.divider), 1);
        addItemDecoration(itemDecoration);

        if (itemsParams.layoutManager == null) {
            itemsParams.layoutManager = new LinearLayoutManager(getContext());
        } else {
            if (itemsParams.layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) itemsParams.layoutManager;
                if (gridLayoutManager.getSpanCount() == 1) {
                    itemsParams.layoutManager = new LinearLayoutManager(getContext());
                }
            }
        }
        setLayoutManager(params.itemsParams.layoutManager);

        mAdapter = params.itemsParams.adapterRv;
        if (mAdapter == null) {
            mAdapter = new ItemsAdapter(context, mParams);
            if (itemsParams.layoutManager instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) itemsParams.layoutManager;
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int itemCount = mAdapter.getItemCount();
                        int spanCount = gridLayoutManager.getSpanCount();
                        int mod = itemCount % spanCount;
                        if (mod == 0 || position < itemCount - 1) {
                            return 1;
                        } else {
                            return spanCount - mod + 1;
                        }
                    }
                });
            }
        } else {
            TitleParams titleParams = params.titleParams;

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
        }
        setAdapter(mAdapter);
    }

    @Override
    public void regOnItemClickListener(OnRvItemClickListener listener) {
        if (mAdapter != null && mAdapter instanceof ItemsAdapter) {
            ((ItemsAdapter) mAdapter).setOnItemClickListener(listener);
        }
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
            LayoutManager layoutManager = mItemsParams.layoutManager;
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                glBg(holder, position, gridLayoutManager.getSpanCount());
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                    llvBg(holder, position);
                } else {

                }
            }
            holder.item.setText(String.valueOf(mItems.get(position).toString()));
        }

        //LinearLayoutManager Vertical Background
        private void llvBg(Holder holder, int position) {
            SelectorBtn selectorBtn;
            //top 且没有标题
            if (position == 0 && mTitleParams == null) {
                if (getItemCount() == 1) {
                    selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                            , mRadius, mRadius, mRadius, mRadius);
                } else {
                    selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                            , mRadius, mRadius, 0, 0);
                }
            }
            //bottom 有标题与中间一样
            else if (position == getItemCount() - 1) {
                selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                        , 0, 0, mRadius, mRadius);
            }
            //middle
            else {
                selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress, 0, 0, 0, 0);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.item.setBackground(selectorBtn);
            } else {
                holder.item.setBackgroundDrawable(selectorBtn);
            }
        }

        //GridLayoutManager Background
        private void glBg(Holder holder, int position, int spanCount) {
            int itemCount = getItemCount();
            int mod = itemCount % spanCount;
            SelectorBtn selectorBtn = null;

            if (itemCount == 1) {
                if (mTitleParams == null) {
                    selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                            , mRadius, mRadius, mRadius, mRadius);
                } else {
                    selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                            , 0, 0, mRadius, mRadius);
                }
            } else {
                //bottom
                if (itemCount <= spanCount || position >= itemCount - (mod == 0 ? spanCount : mod)) {
                    int topRadius = itemCount <= spanCount && mTitleParams == null ? mRadius : 0;
                    if (position % spanCount == 0) {//left
                        if (mod == 1) {
                            selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                    , 0, 0, mRadius, mRadius);
                        } else {
                            selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                    , topRadius, 0, 0, mRadius);
                        }
                    } else {
                        if (mod == 0) {//full
                            if (position % spanCount == spanCount - 1) {//right
                                selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                        , 0, topRadius, mRadius, 0);
                            } else {//middle
                                selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                        , 0, 0, 0, 0);
                            }
                        } else {
                            if (position % spanCount == mod - 1) {//right
                                selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                        , 0, topRadius, mRadius, 0);
                            } else {//middle
                                selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                        , 0, 0, 0, 0);
                            }
                        }
                    }
                } else {
                    if (mTitleParams == null && position % spanCount == 0) {
                        selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                , position < spanCount ? mRadius : 0, 0, 0, 0);
                    } else if (mTitleParams == null && position % spanCount == spanCount - 1) {//right
                        selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                , 0, position < spanCount ? mRadius : 0, 0, 0);
                    } else {
                        selectorBtn = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                , 0, 0, 0, 0);
                    }
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.item.setBackground(selectorBtn);
            } else {
                holder.item.setBackgroundDrawable(selectorBtn);
            }
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
