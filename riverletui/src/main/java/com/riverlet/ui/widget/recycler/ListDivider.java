package com.riverlet.ui.widget.recycler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.riverlet.ui.util.DensityUtil;

/**
 * Created by
 * Author:liujian
 * DATE:16/11/29.
 * Time:上午11:02
 */

public class ListDivider extends RecyclerView.ItemDecoration {

    private int size;
    private Paint paint;
    private int orientation;

    public ListDivider(float size) {
        this(size, null, -1);
    }

    public ListDivider(float size, int orientation) {
        this(size, null, orientation);
    }

    public ListDivider(float size, String color) {
        this(size, color, -1);
    }

    public ListDivider(float size, String color, int orientation) {
        this.size = DensityUtil.dip2px(size);
        this.orientation = orientation;
        if (orientation < 0) {
            this.orientation = LinearLayoutManager.VERTICAL;
        }
        if (color == null) {
            color = "#00000000";
        }
        this.paint = new Paint();
        this.paint.setColor(Color.parseColor(color));
        this.paint.setStyle(Paint.Style.FILL);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, size);
        } else {
            outRect.set(0, 0, size, 0);
        }

    }

    /**
     * 绘制纵向 item 分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + size;
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    /**
     * 绘制横向 item 分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + size;
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

}
