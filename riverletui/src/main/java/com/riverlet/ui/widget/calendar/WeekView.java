package com.riverlet.ui.widget.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.riverlet.ui.R;
import com.riverlet.ui.util.DensityUtil;

/**
 * Created by
 * Author:liujian
 * DATE:16/9/20.
 * Time:下午5:54
 */
public class WeekView extends View {
    private Paint paint;
    private String[] weekText = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private int baseLineY;
    private float textSize;
    private int cellDistance;

    public WeekView(Context context) {
        this(context, null);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WeekView);
        textSize = ta.getDimensionPixelSize(R.styleable.WeekView_textSize, DensityUtil.sp2px(20));
        int textColor = ta.getColor(R.styleable.WeekView_textColor, 0xff199dff);
        paint = new Paint();
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        textSize = paint.getTextSize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        baseLineY = height - (int) (height / 2 - textSize / 2);
        cellDistance = width / 7 / 2;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        for (int i = 0; i < weekText.length; i++) {
            canvas.drawText(weekText[i], cellDistance * 2 * (i + 1) - cellDistance, baseLineY, paint);
        }
    }

    public void setTextSize(float textSize) {
        paint.setTextSize(textSize);
    }

    public void setTextColor(int color) {
        paint.setColor(color);
    }
}
