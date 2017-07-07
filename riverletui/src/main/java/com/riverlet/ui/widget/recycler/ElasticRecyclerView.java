package com.riverlet.ui.widget.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by liujian on 17-7-6.
 */

public class ElasticRecyclerView extends LinearLayout {
    private int orientation;
    private ERecyclerView recyclerView;

    public ElasticRecyclerView(Context context) {
        this(context, null);
    }

    public ElasticRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        recyclerView = new ERecyclerView(context);
        addView(recyclerView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    private float startX = -1;
    private float startY = -1;

    private boolean isChildCanScroll = true;

    public void onThisTouchEvent(MotionEvent e) {
        if (orientation == VERTICAL) {
            if (e.getAction() == MotionEvent.ACTION_MOVE) {
                if (recyclerView.isSlideToBottom()) {
                    if (startY == -1) {
                        startY = e.getY();
                    }
                    int dis = (int) ((startY - e.getY()) * 0.5f);
                    Log.d("ElasticRecyclerView", "dis" + dis);
                    if (dis > 0) {
                        scrollTo(0, dis);
                        isChildCanScroll = false;
                    } else {
                        isChildCanScroll = true;
                    }
                }
            }
            if (recyclerView.isSlideToTop()) {
                if (startY == -1) {
                    startY = e.getY();
                }
                int dis = (int) ((startY - e.getY()) * 0.5f);
                Log.d("ElasticRecyclerView", "dis" + dis);
                if (dis < 0) {
                    scrollTo(0, dis);
                    isChildCanScroll = false;
                } else {
                    isChildCanScroll = true;
                }
            }
            if (e.getAction() == MotionEvent.ACTION_UP) {
                scrollTo(0, 0);
                isChildCanScroll = true;
                startY = -1;
            }
            if (e.getAction() == MotionEvent.ACTION_CANCEL) {
                scrollTo(0, 0);
                isChildCanScroll = true;
                startY = -1;
            }

        }
        if (orientation == HORIZONTAL) {
            if (e.getAction() == MotionEvent.ACTION_MOVE) {
                if (recyclerView.isSlideToRight()) {
                    if (startX == -1) {
                        startX = e.getX();
                    }
                    int dis = (int) ((startX - e.getX()) * 0.5f);
                    Log.d("ElasticRecyclerView", "dis" + dis);
                    if (dis > 0) {
                        scrollTo(dis, 0);
                        isChildCanScroll = false;
                    } else {
                        isChildCanScroll = true;
                    }
                }
            }
            if (recyclerView.isSlideToLeft()) {
                if (startX == -1) {
                    startX = e.getX();
                }
                int dis = (int) ((startX - e.getX()) * 0.5f);
                Log.d("ElasticRecyclerView", "dis" + dis);
                if (dis < 0) {
                    scrollTo(dis, 0);
                    isChildCanScroll = false;
                } else {
                    isChildCanScroll = true;
                }
            }
            if (e.getAction() == MotionEvent.ACTION_UP) {
                scrollTo(0, 0);
                isChildCanScroll = true;
                startX = -1;
                Log.v("MotionEvent", "ACTION_UP");
            }
            if (e.getAction() == MotionEvent.ACTION_CANCEL) {
                scrollTo(0, 0);
                isChildCanScroll = true;
                startX = -1;
                Log.v("MotionEvent", "ACTION_CANCEL");
            }
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    final class ERecyclerView extends RecyclerView {

        public ERecyclerView(Context context) {
            super(context);
        }

        public ERecyclerView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public ERecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        public boolean isSlideToBottom() {
            Log.d("", "computeVerticalScrollExtent=" + computeVerticalScrollExtent() +
                    "....computeVerticalScrollOffset" + computeVerticalScrollOffset() +
                    "...computeVerticalScrollRange=" + computeVerticalScrollRange());
            Log.d("isSlideToBottom","child"+getChildAt(getChildCount()-1).getBottom()+"...."+getBottom());
            if (computeVerticalScrollExtent() + computeVerticalScrollOffset()
                    >= computeVerticalScrollRange()) {
                return true;
            }
            return false;
        }

        public boolean isSlideToTop() {
            if (computeVerticalScrollOffset() <= 0) {
                return true;
            }
            return false;
        }

        public boolean isSlideToLeft() {
            if (computeHorizontalScrollOffset() <= 0) {
                return true;
            }
            return false;
        }

        public boolean isSlideToRight() {
            if (computeHorizontalScrollExtent() + computeHorizontalScrollOffset() >= computeHorizontalScrollRange()) {
                return true;
            }
            return false;
        }

        public boolean isItemFull() {
            return computeHorizontalScrollRange() >= computeHorizontalScrollOffset();
        }

        @Override
        public void setLayoutManager(LayoutManager layout) {
            super.setLayoutManager(layout);
            if (!(layout instanceof LinearLayoutManager)) {
                throw new ClassCastException("这个view只能使用LinearLayoutManager");
            }
            orientation = ((LinearLayoutManager) layout).getOrientation();
        }

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            ElasticRecyclerView.this.onThisTouchEvent(e);

            if (isChildCanScroll) {
                return super.onTouchEvent(e);
            }
            return true;
        }
    }

}
