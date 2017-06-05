package com.example.kz.scratchcard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class Rubble extends View {

    private final int PAINT_STROKE_WIDTH;
    private final float TOUCH_TOLERANCE; // 填充距离，使线条更自然，柔和,值越小，越柔和。
    private final int TEXT_SIZE;

    private Bitmap mBitmap;
    // 画布
    private Canvas mCanvas;
    // 画笔
    private Paint mPaint;
    private Path mPath;
    private float mX, mY;
    private final int X, Y, W, H;

    private final Rect touchRect;

    public Rubble(Context context, String bgText, Rect rect,
                  int paintStrokeWidth, float touchTolerance, int textSize) {
        super(context);
        setFocusable(true);
        touchRect = rect;
        W = rect.right - rect.left;
        H = rect.bottom - rect.top;
        X = rect.left;
        Y = rect.top;
        TEXT_SIZE = textSize;
        PAINT_STROKE_WIDTH = paintStrokeWidth;
        TOUCH_TOLERANCE = touchTolerance;
        setBackground(touchRect, bgText);
        initDrowTools();

    }

    private void setBackground(Rect rect, String bgText) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = this.getResources().getDisplayMetrics();

        Bitmap bitmap = Bitmap.createBitmap(dm.widthPixels,
                dm.heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(0x88000000);
        // paint.setStyle(Style.STROKE);
        // paint.setTextAlign(Align.CENTER);
        paint.setTextSize(TEXT_SIZE);

        // paint.setTextScaleX(1.5f);
        canvas.drawColor(Color.WHITE);
        // 画字的坐标不好控制
        int x = rect.left
                + (rect.right - rect.left - bgText.length() * TEXT_SIZE)
                / 2;
        int y = rect.top + (rect.bottom - rect.top - TEXT_SIZE) / 2;
        // int y = 218+25;
        canvas.drawText(bgText, x, y, paint);
        Drawable drawable = new BitmapDrawable(bitmap);
        setBackgroundDrawable(drawable);
    }

    private void initDrowTools() {
        // 设置画笔
        mPaint = new Paint();
        // mPaint.setAlpha(0);
        // 画笔划过的痕迹就变成透明色了
        mPaint.setColor(Color.BLACK); // 此处不能为透明色
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        // 或者
        // mPaint.setAlpha(0);
        // mPaint.setXfermode(new
        // PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 前圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 后圆角
        mPaint.setStrokeWidth(PAINT_STROKE_WIDTH); // 笔宽

        // 痕迹
        mPath = new Path();

        // 覆盖
        mBitmap = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(0x88000000);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas.drawPath(mPath, mPaint);
        // mCanvas.drawPoint(mX, mY, mPaint);
        canvas.drawBitmap(mBitmap, X, Y, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.print("X--" + event.getX());
        System.out.println("Y--" + event.getY());
        if (!touchRect.contains((int) event.getX(), (int) event.getY())) {
            return false;
        }

        switch (event.getAction()) {
            // 触点按下
            case MotionEvent.ACTION_DOWN: {
                touchDown(event.getRawX(), event.getRawY());
                touchDown(event.getX() - touchRect.left, event.getY()
                        - touchRect.top);
                invalidate();
                break;
            }

            case MotionEvent.ACTION_MOVE: // 触点移动
                touchMove(event.getX() - touchRect.left, event.getY()
                        - touchRect.top);

                invalidate();
                break;
            case MotionEvent.ACTION_UP: // 触点弹起
                touchUp(event.getX() - touchRect.left, event.getY()
                        - touchRect.top);
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    private void touchDown(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }

    }

    private void touchUp(float x, float y) {
        mPath.lineTo(x, y);
        mCanvas.drawPath(mPath, mPaint);
        mPath.reset();
    }

}