package com.riverlet.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by
 * Author:liujian
 * DATE:16/8/2.
 * Time:下午3:09
 */
public class BetterRadio extends View {
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int WHITE = 0xffffffff;
    private static final int BLACK = 0xff333333;

    private OnButtonClickListener onButtonClickListener;
    private int current = LEFT;
    private Paint bgPaint;
    private Paint carPaint;
    private Paint leftTextPaint;
    private Paint rightTextPaint;
    private int distance;
    private float distanceRate = 0.0f;
    private String leftText = "Tab Left";
    private String rightText = "Tab Right";
    private float leftTextX;
    private float leftTextY;
    private float rightTextX;
    private float rightTextY;
    private RectF bgRectF = new RectF();
    private RectF carRectF = new RectF();
    private int radius;

    private int width;
    private int height;

    private ObjectAnimator animatorGoRight;
    private ObjectAnimator animatorGoLeft;

    public BetterRadio(Context context) {
        this(context, null);
    }

    public BetterRadio(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BetterRadio(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        radius = dip2px(5f);
        animatorGoRight = ObjectAnimator.ofFloat(this, "distanceRate", 0f, 1f);
        animatorGoLeft = ObjectAnimator.ofFloat(this, "distanceRate", 1f, 0f);
        initPaint();
    }

    private void initPaint() {
        bgPaint = new Paint();
        carPaint = new Paint();
        leftTextPaint = new Paint();
        rightTextPaint = new Paint();

        bgPaint.setColor(0xff448ff2);
        bgPaint.setStrokeWidth(dip2px(0.5f));
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setAntiAlias(true);

        carPaint.setColor(0xff448ff2);
        carPaint.setStyle(Paint.Style.FILL);
        carPaint.setAntiAlias(true);

        leftTextPaint.setColor(WHITE);
        leftTextPaint.setAntiAlias(true);
        leftTextPaint.setTextSize(dip2px(15));

        rightTextPaint.setColor(BLACK);
        rightTextPaint.setAntiAlias(true);
        rightTextPaint.setTextSize(dip2px(15));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        bgRectF.left = 0;
        bgRectF.right = width;
        bgRectF.top = 0;
        bgRectF.bottom = height;
        carRectF.left = 0;
        carRectF.right = width / 2;
        carRectF.top = 0;
        carRectF.bottom = height;
        distance = width / 2;
        measureText();
    }

    public void setButtonText(String leftText, String rightText) {
        this.leftText = leftText;
        this.rightText = rightText;
        measureText();
    }

    private void measureText() {
        Rect bounds = new Rect();
        if (!TextUtils.isEmpty(leftText)) {
            leftTextPaint.getTextBounds(leftText, 0, leftText.length(), bounds);
            leftTextX = width / 4 - bounds.width() / 2;
            leftTextY = height / 2 + bounds.height() / 2 - dip2px(1);
        }
        if (!TextUtils.isEmpty(rightText)) {
            rightTextPaint.getTextBounds(rightText, 0, rightText.length(), bounds);
            rightTextX = width / 4 - bounds.width() / 2 + width / 2;
            rightTextY = height / 2 + bounds.height() / 2 - dip2px(1);
        }
    }

    public float getDistanceRate() {
        return distanceRate;
    }

    public void setDistanceRate(float distanceRate) {
        this.distanceRate = distanceRate;
        invalidate();
    }

    private void clickRight() {
        current = RIGHT;
        animatorGoRight.start();
        leftTextPaint.setColor(BLACK);
        rightTextPaint.setColor(WHITE);
    }

    private void clickLeft() {
        current = LEFT;
        animatorGoLeft.start();
        leftTextPaint.setColor(WHITE);
        rightTextPaint.setColor(BLACK);
    }

    public void setCurrentButton(int currentButton) {
        if (currentButton == 0) {
            if (current == RIGHT) {
                clickRight();
            }
            current = LEFT;
        } else {
            if (current == LEFT) {
                clickLeft();
            }
            current = RIGHT;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawRoundRect(bgRectF, radius, radius, bgPaint);
        carRectF.left = 0 + distance * distanceRate;
        carRectF.right = width / 2 + distance * distanceRate;
        canvas.drawRoundRect(carRectF, radius, radius, carPaint);
        if (!TextUtils.isEmpty(leftText)) {
            canvas.drawText(leftText, leftTextX, leftTextY, leftTextPaint);
        }
        if (!TextUtils.isEmpty(rightText)) {
            canvas.drawText(rightText, rightTextX, rightTextY, rightTextPaint);
        }
    }

    public int dip2px(float dipValue) {
        final float scale = getContext().getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    int clickWhere = LEFT;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                if (x < width / 2) {
                    clickWhere = LEFT;
                } else {
                    clickWhere = RIGHT;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (clickWhere == LEFT) {
                    if (current == RIGHT) {
                        clickLeft();
                        if (onButtonClickListener != null) {
                            onButtonClickListener.onLeftButtonClick();
                        }
                    }
                } else {
                    if (current == LEFT) {
                        clickRight();
                        if (onButtonClickListener != null) {
                            onButtonClickListener.onRightButtonClick();
                        }
                    }
                }
                break;
        }
        return true;
    }

    public interface OnButtonClickListener {
        void onLeftButtonClick();

        void onRightButtonClick();
    }
}
