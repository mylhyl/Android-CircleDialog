package com.mylhyl.circledialog.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mylhyl.circledialog.CircleParams;
import com.mylhyl.circledialog.Controller;
import com.mylhyl.circledialog.callback.CircleItemLabel;
import com.mylhyl.circledialog.params.ItemsParams;
import com.mylhyl.circledialog.res.drawable.CircleDrawableSelector;
import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.view.listener.ItemsView;
import com.mylhyl.circledialog.view.listener.OnRvItemClickListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hupei on 2018/4/18.
 */

final class BodyItemsRecyclerView extends RecyclerView implements Controller.OnClickListener, ItemsView {
    private Adapter mAdapter;
    private CircleParams mParams;

    public BodyItemsRecyclerView(Context context, CircleParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, final CircleParams params) {
        this.mParams = params;
        ItemsParams itemsParams = params.itemsParams;

        //如果没有背景色，则使用默认色
        int backgroundColor = itemsParams.backgroundColor != 0
                ? itemsParams.backgroundColor : mParams.dialogParams.backgroundColor;
        setBackgroundColor(backgroundColor);

        if (itemsParams.layoutManager == null) {
            itemsParams.layoutManager = new LinearLayoutManager(getContext()
                    , itemsParams.linearLayoutManagerOrientation, false);
        } else if (itemsParams.layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) itemsParams.layoutManager;
            if (gridLayoutManager.getSpanCount() == 1) {
                itemsParams.layoutManager = new LinearLayoutManager(getContext()
                        , itemsParams.linearLayoutManagerOrientation, false);
            }
        }
        setLayoutManager(params.itemsParams.layoutManager);

        ItemDecoration itemDecoration = itemsParams.itemDecoration;
        if (itemsParams.layoutManager instanceof GridLayoutManager && itemDecoration == null) {
            itemDecoration = new GridItemDecoration(new ColorDrawable(CircleColor.divider)
                    , itemsParams.dividerHeight);
        } else if (itemsParams.layoutManager instanceof LinearLayoutManager && itemDecoration == null) {
            itemDecoration = new LinearItemDecoration(new ColorDrawable(CircleColor.divider)
                    , itemsParams.dividerHeight);
        }
        addItemDecoration(itemDecoration);

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
    public void onClick(View view, int which) {
        if (mParams.rvItemListener != null) {
            mParams.rvItemListener.onItemClick(view, which);
        }
    }

    static class ItemsAdapter<T> extends Adapter<ItemsAdapter.Holder> {

        private OnRvItemClickListener mItemClickListener;
        private Context mContext;
        private List<T> mItems;
        private int mBackgroundColorPress;
        private ItemsParams mItemsParams;

        public ItemsAdapter(Context context, CircleParams params) {
            this.mContext = context;
            this.mItemsParams = params.itemsParams;
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
            LayoutManager layoutManager = mItemsParams.layoutManager;
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    textView.setLayoutParams(new LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    textView.setPadding(10, 0, 10, 0);
                } else {
                    textView.setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                }
            } else {
                textView.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            }
            textView.setTextSize(mItemsParams.textSize);
            textView.setTextColor(mItemsParams.textColor);
            textView.setHeight(mItemsParams.itemHeight);
            Holder holder = new Holder(textView, mItemClickListener);
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.item.setBackground(new CircleDrawableSelector(Color.TRANSPARENT, mBackgroundColorPress));
            } else {
                holder.item.setBackgroundDrawable(new CircleDrawableSelector(Color.TRANSPARENT, mBackgroundColorPress));
            }
            String label;
            T item = mItems.get(position);
            if (item instanceof CircleItemLabel) {
                label = ((CircleItemLabel) item).getItemLabel();
            } else {
                label = item.toString();
            }
            holder.item.setText(String.valueOf(label));
        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }

        public void setOnItemClickListener(OnRvItemClickListener listener) {
            this.mItemClickListener = listener;
        }

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
    }

    class LinearItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;
        private int mDividerHeight;

        public LinearItemDecoration(Drawable divider, int dividerHeight) {
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

            int childCount = parent.getChildCount() - 1;
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDividerHeight;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    public class GridItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;
        private int mDividerHeight;

        public GridItemDecoration(Drawable divider, int dividerHeight) {
            mDivider = divider;
            mDividerHeight = dividerHeight;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }

        private void drawHorizontal(Canvas c, RecyclerView parent) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getLeft() - params.leftMargin;
                final int right = child.getRight() + params.rightMargin + mDividerHeight;
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDividerHeight;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        private void drawVertical(Canvas c, RecyclerView parent) {
            final int childCount = parent.getChildCount() - 1;
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);

                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getTop() - params.topMargin;
                final int bottom = child.getBottom() + params.bottomMargin;
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDividerHeight;

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        private boolean isLastColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
            LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                // 如果是最后一列，则不需要绘制右边
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
                if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                    // 如果是最后一列，则不需要绘制右边
                    if ((pos + 1) % spanCount == 0) {
                        return true;
                    }
                } else {
                    childCount = childCount - childCount % spanCount;
                    // 如果是最后一列，则不需要绘制右边
                    if (pos >= childCount) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
            LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount) {
                    return true;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
                // StaggeredGridLayoutManager 且纵向滚动
                if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                    childCount = childCount - childCount % spanCount;
                    // 如果是最后一行，则不需要绘制底部
                    if (pos >= childCount) {
                        return true;
                    }
                }
                // StaggeredGridLayoutManager 且横向滚动
                else {
                    // 如果是最后一行，则不需要绘制底部
                    if ((pos + 1) % spanCount == 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            int spanCount = getSpanCount(parent);
            int childCount = parent.getAdapter().getItemCount();
            // 如果是最后一行，则不需要绘制底部
            if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
                outRect.set(0, 0, mDividerHeight, 0);
            }
            // 如果是最后一列，则不需要绘制右边
            else if (isLastColumn(parent, itemPosition, spanCount, childCount)) {
                outRect.set(0, 0, 0, mDividerHeight);
            } else {
                outRect.set(0, 0, mDividerHeight, mDividerHeight);
            }
        }

        private int getSpanCount(RecyclerView parent) {
            // 列数
            int spanCount = -1;
            LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {

                spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            }
            return spanCount;
        }
    }
}
