package com.riverlet.ui.widget.recycler;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.riverlet.ui.util.DensityUtil;

/**
 * Created by
 * Author:liujian
 * DATE:16/11/29.
 * Time:上午11:02
 */

public class GridDivider extends RecyclerView.ItemDecoration {

    private boolean isShowFirstRaw;
    private boolean isShowLastRaw;
    private boolean isShowFirstColum;
    private boolean isShowLastColum;
    private int size;
    private Paint paint;

    public GridDivider() {
        this(1);
    }

    public GridDivider(float size) {
        this.size = DensityUtil.dip2px(size);
        paint = new Paint();
        paint.setColor(0x0000000);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        drawHorizontal(c, parent);
        drawVertical(c, parent);

    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + size;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + size;
            c.drawRect(left, top, right, bottom, paint);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + size;

            c.drawRect(left, top, right, bottom, paint);
        }
    }

    private boolean isFirstColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 1)// 如果是第一列，则不需要绘制左边
            {
                return true;
            }
        }
        return false;
    }

    private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        }
        return false;
    }

    private boolean isFirstRaw(RecyclerView parent, int pos, int spanCount,
                               int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (pos < spanCount) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int left = size / 2;
        int top = size / 2;
        int right = size / 2;
        int bottom = size / 2;
        if (isFirstRaw(parent, itemPosition, spanCount, childCount)) {
            if (isShowFirstRaw) {
                top = size;
            } else {
                top = 0;
            }
        }

        if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
            if (isShowLastRaw) {
                bottom = size;
            } else {
                bottom = 0;
            }
        }

        if (isFirstColum(parent, itemPosition, spanCount, childCount)) {
            if (isShowFirstColum) {
                left = size;
            } else {
                left = 0;
            }
        }

        if (isLastColum(parent, itemPosition, spanCount, childCount)) {
            if (isShowLastColum) {
                right = size;
            } else {
                right = 0;
            }
        }
        outRect.set(left, top, right, bottom);
    }

    public void setShowFirstRaw(boolean showFirstRaw) {
        isShowFirstRaw = showFirstRaw;
    }

    public void setShowLastRaw(boolean showLastRaw) {
        isShowLastRaw = showLastRaw;
    }

    public void setShowFirstColum(boolean showFirstColum) {
        isShowFirstColum = showFirstColum;
    }

    public void setShowLastColum(boolean showLastColum) {
        isShowLastColum = showLastColum;
    }
}
