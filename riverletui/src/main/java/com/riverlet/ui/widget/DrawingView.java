package com.riverlet.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuj on 2015/12/28.
 */
public class DrawingView extends View implements View.OnTouchListener {

    /**
     * 圆点
     */
    public static final int CIRCLE_DOT = 1;
    /**
     * 圆形
     */
    public static final int CIRCLE = 2;
    /**
     * 矩形
     */
    public static final int RECTANGLE = 3;
    /**
     * 椭圆
     */
    public static final int OVAL = 4;
    /**
     * 路径
     */
    public static final int PATH = 5;
    /**
     * 直线
     */
    public static final int STRAIGHT_LINE = 6;
    /**
     * 橡皮擦
     */
    public static final int ERASER = 7;

    /**
     * 开始
     */
    private static final int STATUS_START = 0;
    /**
     * 正在画
     */
    private static final int STATUS_DRAWING = 1;
    /**
     * 结束
     */
    private static final int STATUS_END = 2;
    /**
     * 需要实用二次贝塞尔曲线时的最小移动的像素数
     */
    private static final float MOVE_TOLERANCE = 4;
    /**
     * 当前状态
     */
    private int currentStatus = -1;
    /**
     * 当前图形类型
     */
    private int type = PATH;
    /**
     * 新建路径
     */
    private Path path;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 记录当前的坐标
     */
    private float currentX, currentY;
    /**
     * 记录上次的坐标
     */
    private float lastX, lastY;
    /**
     * 圆形半径
     */
    private float radius;
    /**
     * 画笔的颜色
     */
    private int paintColor = Color.RED;
    /**
     * 画笔大小
     */
    private int paintSize = 5;
    /**
     * 画笔透明度
     */
    private int paintAlpha = 255;
    /**
     * 背景颜色
     */
    private int canvasColor = Color.WHITE;
    /**
     * 橡皮paint
     */
    private Paint eraserPaint;
    /**
     * 橡皮擦尺寸
     */
    private int eraserPaintSize = paintSize*2;
    /**
     * 绘画记录
     */
    private List<GraphRecord> recordList = new ArrayList<>();
    /**
     * 绘画记录的备份
     */
    private List<GraphRecord> recordListTemp = new ArrayList<>();
    /**
     * 当前绘画的记录
     */
    private GraphRecord record;

    public DrawingView(Context context) {
        this(context, null);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundColor(canvasColor);
        this.setOnTouchListener(this);
        initPaint();
    }

    /**
     * 初始化Paint
     */
    private void initPaint() {
        paint = new Paint();
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);//设置为空心
        paint.setStrokeWidth(paintSize);

        eraserPaint = new Paint();
        eraserPaint.setDither(true);
        eraserPaint.setStrokeJoin(Paint.Join.ROUND);
        eraserPaint.setStrokeCap(Paint.Cap.ROUND);
        eraserPaint.setColor(canvasColor);
        eraserPaint.setAntiAlias(true);
        eraserPaint.setStyle(Paint.Style.STROKE);//设置为空心
        eraserPaint.setStrokeWidth(eraserPaintSize);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentStatus = STATUS_START;
                touchDwon(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                currentStatus = STATUS_DRAWING;
                touchMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                currentStatus = STATUS_END;
                touchUp(event.getX(), event.getY());
                break;
        }
        return true;
    }

    /**
     * 复写绘图方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < recordList.size(); i++) {
            identifyType(canvas, recordList.get(i));
            Log.v("recordList" + i, recordList.get(i).toString());
        }
        switch (currentStatus) {
            case STATUS_START:
                identifyType(canvas, null);
                break;
            case STATUS_DRAWING:
                identifyType(canvas, null);
                break;
            case STATUS_END:
                identifyType(canvas, null);
                break;
            default:
                break;
        }

        super.onDraw(canvas);
    }

    /**
     * 根据不同类型绘图
     *
     * @param canvas
     * @param record
     */
    private void identifyType(Canvas canvas, GraphRecord record) {
        switch (record == null ? type : record.getType()) {
            case CIRCLE_DOT:
                if (record == null) {
                    drawPoints(canvas, lastX, lastY, paintSize, paint);
                } else {
                    drawPoints(canvas, record.getX(), record.getY(), record.getPaint().getStrokeWidth(), record.getPaint());
                }
                break;
            case CIRCLE:
                if (record == null) {
                    drawCicles(canvas, lastX, lastY, radius, paint);
                } else {
                    drawCicles(canvas, record.getX(), record.getY(), record.getRadius(), record.getPaint());
                }
                break;
            case RECTANGLE:
                if (record == null) {
                    drawRectangles(canvas, lastX, lastY, currentX, currentY, paint);
                } else {
                    drawRectangles(canvas, record.getX(), record.getY(), record.getX2(), record.getY2(), record.getPaint());
                }
                break;
            case OVAL:
                if (record == null) {
                    drawOvals(canvas, lastX, lastY, currentX, currentY, paint);
                } else {
                    drawOvals(canvas, record.getX(), record.getY(), record.getX2(), record.getY2(), record.getPaint());
                }
                break;
            case PATH:
                if (record == null) {
                    drawPaths(canvas, path, paint);
                } else {
                    drawPaths(canvas, record.getPath(), record.getPaint());
                }
                break;
            case STRAIGHT_LINE:
                if (record == null) {
                    drawLines(canvas, lastX, lastY, currentX, currentY, paint);
                } else {
                    drawLines(canvas, record.getX(), record.getY(), record.getX2(), record.getY2(), record.getPaint());
                }
                break;
            case ERASER:
                if (record == null) {
                    drawPaths(canvas, path, eraserPaint);
                } else {
                    drawPaths(canvas, record.getPath(), record.getPaint());
                }
                break;
            default:
                break;
        }
    }


    /**
     * 绘圆形
     *
     * @param canvas
     * @param lastX
     * @param lastY
     * @param radius
     * @param paint
     */
    private void drawCicles(Canvas canvas, float lastX, float lastY, float radius, Paint paint) {
        canvas.drawCircle(lastX, lastY, radius, paint);
    }

    /**
     * 画圆点
     *
     * @param canvas
     * @param lastX
     * @param lastY
     * @param paintSize
     * @param paint
     */
    private void drawPoints(Canvas canvas, float lastX, float lastY, float paintSize, Paint paint) {
        canvas.drawCircle(lastX, lastY, paintSize / 2, paint);
    }

    /**
     * 绘路径
     *
     * @param canvas
     * @param path
     * @param paint
     */
    private void drawPaths(Canvas canvas, Path path, Paint paint) {
        canvas.drawPath(path, paint);
    }

    /**
     * 绘直线
     *
     * @param canvas
     * @param lastX
     * @param lastY
     * @param currentX
     * @param currentY
     * @param paint
     */
    private void drawLines(Canvas canvas, float lastX, float lastY, float currentX, float currentY, Paint paint) {
        canvas.drawLine(lastX, lastY, currentX, currentY, paint);
    }

    /**
     * 绘矩形
     *
     * @param canvas
     * @param lastX
     * @param lastY
     * @param currentX
     * @param currentY
     * @param paint
     */
    private void drawRectangles(Canvas canvas, float lastX, float lastY, float currentX, float currentY, Paint paint) {
        float left = lastX < currentX ? lastX : currentX;
        float top = lastY < currentY ? lastY : currentY;
        float right = lastX > currentX ? lastX : currentX;
        float bottom = lastY > currentY ? lastY : currentY;
        canvas.drawRect(left, top, right, bottom, paint);
    }

    /**
     * 绘制椭圆
     *
     * @param canvas
     * @param lastX
     * @param lastY
     * @param currentX
     * @param currentY
     * @param paint
     */
    private void drawOvals(Canvas canvas, float lastX, float lastY, float currentX, float currentY, Paint paint) {
        float left = lastX < currentX ? lastX : currentX;
        float top = lastY < currentY ? lastY : currentY;
        float right = lastX > currentX ? lastX : currentX;
        float bottom = lastY > currentY ? lastY : currentY;
        RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawOval(rectF, paint);
    }

    /**
     * 按下时执行
     *
     * @param x
     * @param y
     */
    private void touchDwon(float x, float y) {
        record = new GraphRecord();
        path = new Path();
        switch (type) {
            case CIRCLE_DOT:
                lastX = x;
                lastY = y;
                invalidate();

                record.setType(CIRCLE_DOT);
                record.setX(x);
                record.setY(y);
                break;
            case CIRCLE:
                lastX = x;
                lastY = y;
                radius = paintSize / 2;
                invalidate();

                record.setType(CIRCLE);
                record.setX(x);
                record.setY(y);
                record.setRadius(radius);
                break;
            case RECTANGLE:
                lastX = x;
                lastY = y;

                record.setType(RECTANGLE);
                record.setX(x);
                record.setY(y);
                break;
            case OVAL:
                lastX = x;
                lastY = y;

                record.setType(OVAL);
                record.setX(x);
                record.setY(y);
                break;
            case PATH:
                path.moveTo(x, y);
                lastX = x;
                lastY = y;
                invalidate();

                record.setType(PATH);
                record.setPath(path);
                break;
            case ERASER:
                path.moveTo(x, y);
                lastX = x;
                lastY = y;
                invalidate();

                record.setType(ERASER);
                record.setPath(path);
                break;
            case STRAIGHT_LINE:
                lastX = x;
                lastY = y;

                record.setType(STRAIGHT_LINE);
                record.setX(x);
                record.setY(y);
                break;
            default:
                break;
        }
    }

    /**
     * 手指移动时执行
     *
     * @param x
     * @param y
     */
    private void touchMove(float x, float y) {

        switch (type) {
            case CIRCLE_DOT:

                break;
            case CIRCLE:
                radius = (float) Math.sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY));
                invalidate();
                break;
            case RECTANGLE:
                currentX = x;
                currentY = y;
                invalidate();
                break;
            case OVAL:
                currentX = x;
                currentY = y;
                invalidate();
                break;
            case PATH:
                float dx = Math.abs(x - lastX);
                float dy = Math.abs(y - lastY);
                if (dx >= MOVE_TOLERANCE || dy >= MOVE_TOLERANCE) {
                    path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2);
                    lastX = x;
                    lastY = y;
                }
                invalidate();
                break;
            case ERASER:
                float cx = Math.abs(x - lastX);
                float cy = Math.abs(y - lastY);
                if (cx >= MOVE_TOLERANCE || cy >= MOVE_TOLERANCE) {
                    path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2);
                    lastX = x;
                    lastY = y;
                }
                invalidate();
                break;
            case STRAIGHT_LINE:
                currentX = x;
                currentY = y;
                invalidate();
                break;
            default:
                break;
        }

    }

    /**
     * 松开时执行
     *
     * @param x
     * @param y
     */
    private void touchUp(float x, float y) {
        record.setPaint(new Paint(paint));
        switch (type) {
            case CIRCLE_DOT:

                break;
            case CIRCLE:
                radius = (float) Math.sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY));
                invalidate();

                record.setRadius(radius);
                break;
            case RECTANGLE:
                currentX = x;
                currentY = y;
                invalidate();

                record.setX2(x);
                record.setY2(y);
                break;
            case OVAL:
                currentX = x;
                currentY = y;
                invalidate();

                record.setX2(x);
                record.setY2(y);
                break;
            case PATH:
                path.lineTo(lastX, lastY);
                invalidate();

                record.setPath(new Path(path));
                break;
            case ERASER:
                path.lineTo(lastX, lastY);
                invalidate();

                record.setPaint(new Paint(eraserPaint));
                record.setPath(new Path(path));
                break;
            case STRAIGHT_LINE:
                currentX = x;
                currentY = y;
                invalidate();

                record.setX2(x);
                record.setY2(y);
                break;
            default:
                break;
        }

        recordList.add(record);
        recordListTemp.clear();
        recordListTemp.addAll(recordList);
    }

    /**
     * 清空所有内容
     */
    public void clear() {
        lastX = 0;
        lastY = 0;
        currentX = 0;
        currentY = 0;
        path.reset();
        radius = 0;
        recordList.clear();
        invalidate();
    }

    /**
     * 返回一步
     */
    public void back() {
        if (recordList.size() != 0) {
            recordList.remove(recordList.size() - 1);
            lastX = 0;
            lastY = 0;
            currentX = 0;
            currentY = 0;
            path.reset();
            radius = 0;
            invalidate();
        }
    }

    /**
     * 前进一步
     */
    public void forward() {
        Log.v("recordListTemp", "" + recordListTemp.size());
        Log.v("recordList", "" + recordList.size());
        if (recordListTemp.size() > recordList.size()) {
            recordList.add(recordListTemp.get(recordList.size()));
            invalidate();
        }
    }

    /**
     * 获取画笔颜色
     *
     * @return
     */
    public int getPaintColor() {
        return paintColor;
    }

    /**
     * 设置画笔艳色
     *
     * @param paintColor
     */
    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
        paint.setColor(paintColor);
    }

    /**
     * 获取画笔尺寸
     *
     * @return
     */
    public int getPaintSize() {
        return paintSize;
    }

    /**
     * 设置画笔尺寸
     *
     * @param paintSize
     */
    public void setPaintSize(int paintSize) {
        this.paintSize = paintSize;
        paint.setStrokeWidth(paintSize);
    }

    /**
     * 获取当前画笔透明度
     *
     * @return
     */
    public int getPaintAlpha() {
        return paintAlpha;
    }

    /**
     * 设置当前画笔透明度
     *
     * @param paintAlpha
     */
    public void setPaintAlpha(int paintAlpha) {
        this.paintAlpha = paintAlpha;
        paint.setAlpha(paintAlpha);
    }

    /**
     * 获取当前绘制图形类型
     *
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     * 设置图片绘制类型
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    public int getCanvasColor() {
        return canvasColor;
    }

    public void setCanvasColor(int canvasColor) {
        this.canvasColor = canvasColor;
        this.setBackgroundColor(canvasColor);
        eraserPaint.setColor(canvasColor);
    }


    public int getEraserPaintSize() {
        return eraserPaintSize;
    }

    public void setEraserPaintSize(int eraserPaintSize) {
        this.eraserPaintSize = eraserPaintSize;
        eraserPaint.setStrokeWidth(eraserPaintSize*2);
    }

    public class GraphRecord {

        private int type = -1;

        private float x;//x坐标
        private float y;//y坐标
        private float x2;//第二x坐标
        private float y2;//第二y坐标
        private float radius;//圆形的半径

        private Paint paint;//画笔

        private Path path;//路径

        private Rect rect;//矩形边界

        private RectF oval;//椭圆边界

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public Paint getPaint() {
            return paint;
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }

        public Path getPath() {
            return path;
        }

        public void setPath(Path path) {
            this.path = path;
        }

        public Rect getRect() {
            return rect;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }

        public RectF getOval() {
            return oval;
        }

        public void setOval(RectF oval) {
            this.oval = oval;

        }

        public float getX2() {
            return x2;
        }

        public void setX2(float x2) {
            this.x2 = x2;
        }

        public float getY2() {
            return y2;
        }

        public void setY2(float y2) {
            this.y2 = y2;
        }

        @Override
        public String toString() {
            return "GraphRecord{" +
                    "type=" + type +
                    ", x=" + x +
                    ", y=" + y +
                    ", x2=" + x2 +
                    ", y2=" + y2 +
                    ", radius=" + radius +
                    ", paint=" + paint +
                    ", path=" + path +
                    ", rect=" + rect +
                    ", oval=" + oval +
                    '}';
        }
    }
}
