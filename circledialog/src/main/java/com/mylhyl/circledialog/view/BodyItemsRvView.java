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
import android.support.v7.widget.StaggeredGridLayoutManager;
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

        if (itemsParams.layoutManager == null) {
            itemsParams.layoutManager = new LinearLayoutManager(getContext()
                    , itemsParams.linearLayoutManagerOrientation, false);
        } else {
            if (itemsParams.layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) itemsParams.layoutManager;
                if (gridLayoutManager.getSpanCount() == 1) {
                    itemsParams.layoutManager = new LinearLayoutManager(getContext()
                            , itemsParams.linearLayoutManagerOrientation, false);
                }
            }
        }
        setLayoutManager(params.itemsParams.layoutManager);

        ItemDecoration itemDecoration = itemsParams.itemDecoration;
        if (itemsParams.layoutManager instanceof GridLayoutManager && itemDecoration == null) {
            itemDecoration = new GridItemDecoration(new ColorDrawable(CircleColor.divider), itemsParams.dividerHeight);
        } else if (itemsParams.layoutManager instanceof LinearLayoutManager && itemDecoration == null) {
            itemDecoration = new LinearItemDecoration(new ColorDrawable(CircleColor.divider), itemsParams.dividerHeight);
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

        private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
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
            else if (isLastColum(parent, itemPosition, spanCount, childCount)) {
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
        private SelectorBtn bgItemAllRadius;
        private SelectorBtn bgItemTopRadius;
        private SelectorBtn bgItemBottomRadius;

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

            bgItemAllRadius = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                    , mRadius, mRadius, mRadius, mRadius);
            bgItemTopRadius = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                    , mRadius, mRadius, 0, 0);
            bgItemBottomRadius = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                    , 0, 0, mRadius, mRadius);

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
            LayoutManager layoutManager = mItemsParams.layoutManager;
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                glBg(holder, position, gridLayoutManager.getSpanCount());
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                    llvBg(holder, position);
                } else {
                    llhBg(holder, position);
                }
            }
            holder.item.setText(String.valueOf(mItems.get(position).toString()));
        }

        //LinearLayoutManager Vertical Background
        private void llvBg(Holder holder, int position) {
            //top 且没有标题
            if (position == 0 && mTitleParams == null) {
                if (getItemCount() == 1) {
                    setItemBg(holder, bgItemAllRadius);
                } else {
                    setItemBg(holder, bgItemTopRadius);
                }
            }
            //bottom 有标题与中间一样
            else if (position == getItemCount() - 1) {
                setItemBg(holder, bgItemBottomRadius);
            }
            //middle
            else {
                setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress, 0, 0, 0, 0));
            }
        }


        //LinearLayoutManager Horizontal Background
        private void llhBg(Holder holder, int position) {
            if (position == 0) {
                setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress, 0, 0, 0, mRadius));
            } else if (position == getItemCount() - 1) {
                setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress, 0, 0, mRadius, 0));
            } else {
                setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress, 0, 0, 0, 0));
            }
        }

        //GridLayoutManager Background
        private void glBg(Holder holder, int position, int spanCount) {
            int itemCount = getItemCount();
            int mod = itemCount % spanCount;

            if (itemCount == 1) {
                if (mTitleParams == null) {
                    setItemBg(holder, bgItemAllRadius);
                } else {
                    setItemBg(holder, bgItemBottomRadius);
                }
            } else {
                //bottom
                if (itemCount <= spanCount || position >= itemCount - (mod == 0 ? spanCount : mod)) {
                    int topRadius = itemCount <= spanCount && mTitleParams == null ? mRadius : 0;
                    if (position % spanCount == 0) {//left
                        if (mod == 1) {
                            setItemBg(holder, bgItemBottomRadius);
                        } else {
                            setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                    , topRadius, 0, 0, mRadius));
                        }
                    } else {
                        if (mod == 0) {//full
                            if (position % spanCount == spanCount - 1) {//right
                                setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                        , 0, topRadius, mRadius, 0));
                            } else {//middle
                                setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress, 0, 0, 0, 0));
                            }
                        } else {
                            if (position % spanCount == mod - 1) {//right
                                setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                        , 0, topRadius, mRadius, 0));
                            } else {//middle
                                setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress, 0, 0, 0, 0));
                            }
                        }
                    }
                } else {
                    if (mTitleParams == null && position % spanCount == 0) {
                        setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                , position < spanCount ? mRadius : 0, 0, 0, 0));
                    } else if (mTitleParams == null && position % spanCount == spanCount - 1) {//right
                        setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                , 0, position < spanCount ? mRadius : 0, 0, 0));
                    } else {
                        setItemBg(holder, new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                                , 0, 0, 0, 0));
                    }
                }
            }
        }

        private void setItemBg(Holder holder, SelectorBtn selectorBtn) {
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
