package com.riverlet.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by
 * Author:liujian
 * DATE:17/6/5.
 * Time:下午4:33
 */
public class SwitchView extends View implements View.OnClickListener {

    private OnStateChangeListener mOnStateChangeListener;

    private Paint bgPaint;
    private Paint switchPaint;
    private Paint textPaint;
    private Paint shadowPaint;

    private RectF bgRect;
    private RectF switchRect;

    private float radius;
    private boolean isSwitchOn;
    private float rate = 0f;
    private int switchWith;
    private int switchheight;
    private int movingDistance;
    private String text = "关闭";
    private float textX;
    private float textY;

    private ObjectAnimator animatorOn;
    private ObjectAnimator animatorOff;

    public SwitchView(Context context) {
        this(context, null);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        setOnClickListener(this);
        setBackgroundColor(0x000000);
        bgPaint = new Paint();
        bgPaint.setColor(0xfff0f0f0);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);

        switchPaint = new Paint();
        switchPaint.setColor(0xffffffff);
        switchPaint.setStyle(Paint.Style.FILL);
        switchPaint.setAntiAlias(true);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(0xffa0a0a0);
        textPaint.setStrokeWidth(3);
        textPaint.setTextSize(dip2px(14));
        textPaint.setTextAlign(Paint.Align.LEFT);

        shadowPaint=new Paint();
        shadowPaint.setAntiAlias(true);          //抗锯齿
        shadowPaint.setColor(0x0A000000);//画笔颜色
        shadowPaint.setStyle(Paint.Style.FILL);  //画笔风格
        BlurMaskFilter bmf = new BlurMaskFilter(5,BlurMaskFilter.Blur.OUTER);
        shadowPaint.setMaskFilter(bmf);

        radius = dip2px(2);
        switchWith = dip2px(40);
        switchheight = dip2px(30);
        movingDistance = dip2px(20);

        bgRect = new RectF(2, 2, dip2px(60) - 2, dip2px(30) - 2);
        switchRect = new RectF(0, 2, switchWith, switchheight-2);

        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        textX = switchWith / 2 - bounds.width() / 2;
        textY = switchheight / 2 + bounds.height() / 2;

        animatorOn = ObjectAnimator.ofFloat(this,"rate",0f,1f);
        animatorOn.setDuration(150);
        animatorOn.setRepeatCount(0);
        animatorOn.setInterpolator(new AccelerateInterpolator());
        animatorOff = ObjectAnimator.ofFloat(this,"rate",1f,0f);
        animatorOff.setDuration(150);
        animatorOff.setRepeatCount(0);
        animatorOff.setInterpolator(new AccelerateInterpolator());
    }


    private void swicthON() {
        bgPaint.setColor(0xff448ff2);
        textPaint.setColor(0xff448ff2);
        text = "开启";
        if (!animatorOn.isStarted()) {
            animatorOn.start();
        }
    }

    private void swicthOFF() {
        bgPaint.setColor(0xfff0f0f0);
        textPaint.setColor(0xffa0a0a0);
        text = "关闭";
        if (!animatorOff.isStarted()) {
            animatorOff.start();
        }
    }

    private void setSwitch(boolean isSwicthOn) {
        if (isSwicthOn != this.isSwitchOn) {
            if (isSwicthOn) {
                swicthON();
            } else {
                swicthOFF();
            }
        }
        this.isSwitchOn = isSwicthOn;
        if(mOnStateChangeListener!=null){
            mOnStateChangeListener.onStateChange(isSwicthOn);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawRoundRect(bgRect, radius, radius, bgPaint);
        switchRect.left = 0 + movingDistance * rate+2;
        switchRect.right = switchWith + movingDistance * rate-2;
        canvas.drawRect(0 + movingDistance * rate, 0, switchWith, switchWith + movingDistance * rate,shadowPaint);
        canvas.drawRoundRect(switchRect, radius, radius, switchPaint);
        canvas.drawText(text, textX + movingDistance * rate, textY, textPaint);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if (params != null) {
            params.width = dip2px(60);
            params.height = dip2px(30);
        }
        super.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        setSwitch(!isSwitchOn);
    }

    public int dip2px(float dipValue) {
        final float scale = getContext().getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
        invalidate();
    }
    public void setChecked(boolean checked) {
        if (checked!=this.isSwitchOn){
            if (checked) {
                swicthON();
            } else {
                swicthOFF();
            }
        }
        this.isSwitchOn = checked;
        if(mOnStateChangeListener!=null){
            mOnStateChangeListener.onStateChange(checked);
        }
    }
    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        mOnStateChangeListener = onStateChangeListener;
    }
    public interface OnStateChangeListener{
        void onStateChange(boolean isChecked);
    }
}
