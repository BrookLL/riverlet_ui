package com.liu.riverletui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by
 * Author:liujian
 * DATE:17/4/28.
 * Time:上午9:25
 */

public class RingView extends View {

    private int innerRadius;
    private int ringWidth;
    private Paint[] paints;
    private int centerX;
    private int centerY;
    private RectF oval;
    private int[] angles;
    private int[] colors = new int[]{0xff82B8FF, 0xffFF7F78, 0xffFFAE72, 0xff74D1B1, 0xffC38AFC};
    private float progress;
    private ObjectAnimator animator;

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initRectF(w, h);
    }

    private void initRectF(int w, int h) {
        centerX = (int) ((float) w / 2);
        centerY = (int) ((float) h / 2);
        innerRadius = (int) ((float) w / 2 / 89 * 64);
        ringWidth = (int) ((float) w / 2 / 89 * 25);
        oval = new RectF(centerX - innerRadius, centerY - innerRadius, centerX + innerRadius, centerY + innerRadius);
    }

    private void initData() {
        if (angles != null) {
            //用于定义的圆弧的形状和大小的界限
            paints = new Paint[angles.length];
            for (int i = 0; i < angles.length; i++) {
                Paint paint = new Paint();
                paint.setColor(colors[i % colors.length]);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(25);
                paint.setAntiAlias(true);
                paints[i] = paint;
            }
        }
        initAnimator();
    }

    private void initAnimator() {
        if (animator == null) {
            progress = 0;
            animator = ObjectAnimator.ofFloat(this, "progress", 0f, 1.0f);
            animator.setDuration(800);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.start();
        } else {
            animStart();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initRectF(getWidth(), getHeight());
        canvas.save();
        int lastAngle = 0;
        int nums = angles == null ? 0 : angles.length;
        if (nums > 0) {
            for (int i = 0; i < nums; i++) {
                if (i > 0) {
                    lastAngle = (int) (lastAngle + angles[i - 1] * progress);
                }
                paints[i].setStrokeWidth(ringWidth);
                if (angles[i] > 0) {
                    canvas.drawArc(oval, 270 + lastAngle, (angles[i] + 1) * progress, false, paints[i]);
                }
            }
        } else {
            Paint paint = new Paint();
            paint.setColor(0xffa0a0a0);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(ringWidth);
            paint.setAntiAlias(true);
            canvas.drawArc(oval, 270, 360 * progress + 1, false, paint);
        }
        canvas.restore();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        invalidate();
        this.progress = progress;
    }

    public void animStart() {
        if (animator == null) {
            return;
        }
        if (animator.isStarted()) {
            animator.cancel();
        }
        animator.start();
    }

    public void setData(float[] datas) {
        if (datas == null) {
            angles = null;
        } else {
            float total = 0;
            for (float data : datas) {
                total += data;
            }
            if (total<=0){
                angles = null;
                initData();
                return;
            }
            angles = new int[datas.length];
            int sumAngles = 0;
            for (int i = 0; i < datas.length; i++) {
                float angle;
                if (i == datas.length - 1) {
                    angles[i] = 360 - sumAngles;
                    Log.v("setData",angles[i]+"");
                } else {
                     angle = datas[i] / total * 360;
                    if (angle < 1) {
                        angles[i] = 1;
                    }else {
                        angles[i] = Math.round(angle);
                    }
                    sumAngles += angles[i];
                    Log.v("setData",angles[i]+"");
                }
            }
        }
        initData();
    }
}
