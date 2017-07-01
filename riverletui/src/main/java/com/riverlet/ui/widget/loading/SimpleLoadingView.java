package com.riverlet.ui.widget.loading;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Create By
 * Author: liujian
 * Date:2016/8/90.
 * Time:14:11
 */
public class SimpleLoadingView extends LoadingView {

    private int radius;
    private float radiusRatioCentre = 1.0f;
    private float radiusRatioLeft = 1.0f;
    private float radiusRatioRight = 1.0f;
    private int centreX, leftX, rightX, y;
    private Paint paint;

    private ObjectAnimator animatorLeft;
    private ObjectAnimator animatorLeft2;
    private ObjectAnimator animatorCentre;
    private ObjectAnimator animatorCentre2;
    private ObjectAnimator animatorRight;
    private ObjectAnimator animatorRight2;


    public SimpleLoadingView(Context context) {
        super(context);
        initAnim();
    }

    public SimpleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnim();
    }

    public SimpleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnim();
    }

    @Override
    public void animStart() {
        setVisibility(VISIBLE);
    }

    @Override
    public void animStop() {
        setVisibility(INVISIBLE);
    }


    private void initCiecle() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setColor(0xffffffff);
        paint.setStyle(Paint.Style.FILL);


        int width = getWidth();
        int height = getHeight();
        radius = height / 2;
        centreX = width / 2;
        y = height / 2;
        leftX = centreX - radius * 2;
        rightX = centreX + radius * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centreX, y, radius * radiusRatioCentre, paint);
        canvas.drawCircle(leftX, y, radius * radiusRatioLeft, paint);
        canvas.drawCircle(rightX, y, radius * radiusRatioRight, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initCiecle();
    }

    private void initAnim() {
        animatorLeft = ObjectAnimator.ofFloat(this, "radiusRatioLeft", 0.0f, 1.0f);
        animatorLeft.setDuration(200);
        animatorLeft2 = ObjectAnimator.ofFloat(this, "radiusRatioLeft", 1.0f, 0.0f);
        animatorLeft2.setDuration(500);

        animatorCentre = ObjectAnimator.ofFloat(this, "radiusRatioCentre", 0.0f, 1.0f);
        animatorCentre.setDuration(200);
        animatorCentre2 = ObjectAnimator.ofFloat(this, "radiusRatioCentre", 1.0f, 0.0f);
        animatorCentre2.setDuration(500);

        animatorRight = ObjectAnimator.ofFloat(this, "radiusRatioRight", 0.0f, 1.0f);
        animatorRight.setDuration(200);
        animatorRight2 = ObjectAnimator.ofFloat(this, "radiusRatioRight", 1.0f, 0.0f);
        animatorRight2.setDuration(500);

        animatorLeft.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorLeft2.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorLeft2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animatorCentre.start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorCentre.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorCentre2.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorCentre2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animatorRight.start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorRight.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorRight2.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorRight2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorLeft.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorLeft.start();

    }

    public float getRadiusRatioCentre() {
        return radiusRatioCentre;
    }

    public void setRadiusRatioCentre(float radiusRatioCentre) {
        this.radiusRatioCentre = radiusRatioCentre;
        invalidate();
    }

    public float getRadiusRatioLeft() {
        return radiusRatioLeft;
    }

    public void setRadiusRatioLeft(float radiusRatioLeft) {
        this.radiusRatioLeft = radiusRatioLeft;
        invalidate();
    }

    public float getRadiusRatioRight() {
        return radiusRatioRight;
    }

    public void setRadiusRatioRight(float radiusRatioRight) {
        this.radiusRatioRight = radiusRatioRight;
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animatorLeft.removeAllListeners();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            initAnim();
        } else {
            animatorLeft.removeAllListeners();
        }
    }
}