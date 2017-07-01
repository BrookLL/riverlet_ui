package com.riverlet.ui.widget.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by
 * Author:liujian
 * DATE:16/9/18.
 * Time:上午10:50
 */
public class RCalendarView extends View implements View.OnTouchListener {
    /**
     * 记录日期选定的索引
     */
    private static Calendar selectDay = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    public static final int TEXT_COLOR = Color.BLACK;

    public static final int BACKGROUND_COLOR = Color.WHITE;

    private OnItemClickListener onItemClickListener;

    private OnRefreshListener onRefreshListener;
    private Calendar calendar;

    private int selectedYear;

    private int selectedMonth;

    private int screenWidth;

    private int[] date = new int[42];
    /**
     * 本月第一日的索引
     */
    private int curStartIndex;

    /**
     * 本月最后一日的索引
     */
    private int curEndIndex;
    /**
     * 今天的索引
     */
    private int todayIndex = -1;


    /**
     * 记录日期选定的索引数组
     */
    private List<Integer> markIndexs = new ArrayList<>();

    /**
     * 记录在ACTION_DOWN实践中点击的date索引
     */
    private int actionDownIndex = -1;
    private float cellWidth;
    private float cellHeight;

    private int backgroundColor = BACKGROUND_COLOR;

    private int textColor = TEXT_COLOR;
    private Paint textPaint;
    private Paint weekTextPaint;
    //    private Paint grayPaint;
    private Paint whitePaint;
    //    private Paint bluePaint;
    private Paint blackPaint;
    private Paint todayBgPaint;
    private Paint selectedDayBgPaint;
    private Paint selectedDayTextPaint;

    public RCalendarView(Context context) {
        super(context);
        init(context);
    }

    public RCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        /**
         * 首先获取当前日期作为默认日期
         */
        calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH) + 1;
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;

        /**
         * 计算单个日期大小
         */
        cellWidth = screenWidth / 7f;
        cellHeight = cellWidth * 0.7f;

        setBackgroundColor(backgroundColor);

        setOnTouchListener(this);

        /**
         * 初始化文字画笔
         */
        textPaint = getPaint(textColor);
        textPaint.setTextSize(cellHeight * 0.4f);

        /**
         * 日历头颜色
         */
        weekTextPaint = getPaint(textColor);
        weekTextPaint.setTextSize(cellHeight * 0.4f);
        weekTextPaint.setTypeface(Typeface.DEFAULT_BOLD);

        whitePaint = getPaint(Color.WHITE);
        whitePaint.setTextSize(cellHeight * 0.4f);
        blackPaint = getPaint(Color.BLACK);
        blackPaint.setTextSize(cellHeight * 0.4f);
//
//        bluePaint = getPaint(Color.rgb(92, 158, 237));
//
//        grayPaint = getPaint(Color.rgb(200, 200, 200));

        todayBgPaint = getPaint(0xffd0d0d0);
        todayBgPaint.setStrokeWidth(3);

        selectedDayBgPaint = getPaint(0xffff7575);

        selectedDayTextPaint = getPaint(Color.WHITE);
        selectedDayTextPaint.setTextSize(cellHeight * 0.4f);

        initial();
    }

    private void initial() {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Log.v(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1), dayOfWeek + "");
        int monthStart = -1;
        if (dayOfWeek >= 0 && dayOfWeek <= 7) {
            monthStart = dayOfWeek - 1;
        } else if (dayOfWeek == -1) {
            monthStart = 0;
        }

        curStartIndex = monthStart;
        date[monthStart] = 1;
        int daysOfMonth = daysOfCurrentMonth();
        for (int i = 1; i < daysOfMonth; i++) {
            date[monthStart + i] = i + 1;
        }
        curEndIndex = monthStart + daysOfMonth;

        if (calendar.get(Calendar.YEAR) == Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).get(Calendar.MONTH)) {
            todayIndex = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).get(Calendar.DAY_OF_MONTH) + monthStart - 1;
//            selectedIndex = selectedIndex==-1?todayIndex:-1;
        } else {
            todayIndex = -1;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        for (int i = curStartIndex; i < curEndIndex; i++) {
            textPaint = blackPaint;
            if (i == todayIndex) {
                drawCircle(canvas, i, todayBgPaint, cellHeight * 0.48f);
            }

            if (calendar.get(Calendar.YEAR) == selectDay.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == selectDay.get(Calendar.MONTH) && i == selectDay.get(Calendar.DATE) + curStartIndex - 1) {
                drawCircle(canvas, i, selectedDayBgPaint, cellHeight * 0.48f);
                textPaint = whitePaint;
            }

            for (int j = 0; j < markIndexs.size(); j++) {
                if (i == markIndexs.get(j) + curStartIndex - 1) {
                    drawPoint(canvas, i, selectedDayBgPaint);
                }
            }

            drawText(canvas, i, textPaint, "" + date[i]);
        }
    }

    /**
     * 绘制圆
     *
     * @param canvas
     * @param index
     * @param paint
     * @param radius
     */
    private void drawCircle(Canvas canvas, int index, Paint paint, float radius) {
        if (isIllegalIndex(index)) {
            return;
        }
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        float centreY = cellHeight + (y - 1) * cellHeight + cellHeight * 0.5f;
        float centreX = cellWidth * (x - 1) + cellWidth * 0.5f;
        canvas.drawCircle(centreX, centreY, radius, paint);
    }

    /**
     * 绘制原点
     *
     * @param canvas
     * @param index
     * @param paint
     */
    private void drawPoint(Canvas canvas, int index, Paint paint) {
        if (isIllegalIndex(index)) {
            return;
        }
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        float centreY = cellHeight + (y - 1) * cellHeight + cellHeight * 0.5f;
        float centreX = cellWidth * (x - 1) + cellWidth * 0.5f;
        canvas.drawCircle(centreX, centreY + cellHeight * 0.3f, cellHeight * 0.05f, paint);
    }


    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas, int index, Paint paint, String text) {
        if (isIllegalIndex(index)) {
            return;
        }
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        float top = cellHeight + (y - 1) * cellHeight;
        float bottom = top + cellHeight;
        float baseline = getBaseline(top, bottom, paint);
        float startX = getStartX(cellWidth * (x - 1) + cellWidth * 0.5f, paint, text);
        canvas.drawText(text, startX, baseline, paint);
    }

    /**
     * 判断是否是非法的索引
     *
     * @param i
     * @return
     */
    private boolean isIllegalIndex(int i) {
        return i < curStartIndex || i >= curEndIndex;
    }

    /**
     * 根据日期索引计算X位置
     *
     * @param i 日期的索引
     * @return
     */
    private int getXByIndex(int i) {
        return i % 7 + 1;
    }

    /**
     * 根据日期索引计算Y位置
     *
     * @param i 日期的索引
     * @return
     */
    private int getYByIndex(int i) {
        return i / 7;
    }

    /**
     * 当前月的天数
     *
     * @return
     */
    private int daysOfCurrentMonth() {
        int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.v("daysOfMonth", daysOfMonth + "");
        return daysOfMonth;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int index = getIndexByCoordinate(x, y);
                if (!isIllegalIndex(index)) {
                    actionDownIndex = index;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                int actionUpIndex = getIndexByCoordinate(x, y);
                if (!isIllegalIndex(actionUpIndex)) {
                    if (actionDownIndex == actionUpIndex) {
                        actionDownIndex = -1;
                        int day = date[actionUpIndex];
                        if (onItemClickListener != null) {
                            selectDay.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), day);
                            onItemClickListener.onItemClick(day, selectDay, markIndexs.indexOf(day) != -1);
                        }
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 根据左边计算date[]的索引
     *
     * @param x
     * @param y
     * @return
     */
    private int getIndexByCoordinate(float x, float y) {
        int m = (int) (Math.floor(x / cellWidth) + 1);
        int n = (int) (Math.floor(y / cellHeight));
        return n * 7 + m - 1;
    }

    public Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        return paint;
    }

    public static float getBaseline(float top, float bottom, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (top + bottom - fontMetrics.bottom - fontMetrics.top) / 2;
    }

    public static float getStartX(float middle, Paint paint, String text) {
        return middle - paint.measureText(text) * 0.5f;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setMarkIndexs(List<Integer> markIndexs) {
        this.markIndexs = markIndexs;
        invalidate();
    }

    public void refresh(int year, int month) {
        selectedYear = year;
        selectedMonth = month;
        calendar.set(Calendar.YEAR, selectedYear);
        calendar.set(Calendar.MONTH, selectedMonth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        initial();
        invalidate();
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int day, Calendar calendar, boolean isMarked);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        selectDay = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    }
}
