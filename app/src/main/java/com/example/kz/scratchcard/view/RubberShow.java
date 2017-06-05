package com.example.kz.scratchcard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;


/**
 * 橡皮擦类
 */
public class RubberShow extends View {
    private float TOUCH_TO_ERANCE;// 填充的最小距离，这个值越小画出来的曲线越柔和
    private Bitmap bitmap;
    private Canvas canvas;// 临时画布
    private Paint paint;// 画笔
    private Path mPath;// 鼠标的运行路径
    private float mx, my;// 坐标
    private boolean isDraw = false;

    public RubberShow(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RubberShow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public RubberShow(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas mCanvas) {
        super.onDraw(mCanvas);
        if (isDraw) {
            Log.i("tag", "111");
            mCanvas.drawPath(mPath, paint);
            mCanvas.drawBitmap(bitmap, 0, 0, null);// 从起点开始画
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isDraw) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event.getX(), event.getY());
                invalidate();// 刷新
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event.getX(), event.getY());
                invalidate();// 刷新
                break;
            case MotionEvent.ACTION_UP:
                touchUp(event.getX(), event.getY());
                invalidate();// 刷新
                break;

            default:
                break;
        }
        return true;
    }

    private void touchUp(float x, float y) {
        // 画出路线
        mPath.lineTo(x, y);
        canvas.drawPath(mPath, paint);
        mPath.reset();
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mx);
        float dy = Math.abs(y - my);
        // 两点之间的距离大于TOUCH_TO_ERANCE，就生成贝瑟尔曲线
        if (dx >= TOUCH_TO_ERANCE || dy >= TOUCH_TO_ERANCE) {
        // 用贝瑟尔实现平滑的曲线
        // mPath.lineTo(dx, dy);
            mPath.quadTo(mx, my, (x + mx) / 2, (y + my) / 2);
            mx = x;
            my = y;
        }

    }

    private void touchDown(float x, float y) {
        mPath.reset();// 重置路径
        mPath.moveTo(x, y);
        mx = x;
        my = y;

    }

    /**
     * @param bgColor          覆盖的背景颜色
     * @param paintStrokeWidth 橡皮擦宽度
     * @param touchToLerance   填充距离
     */
    public void beginRubber(final int bgColor, final int paintStrokeWidth,
                            float touchToLerance) {
        TOUCH_TO_ERANCE = touchToLerance;
        paint = new Paint();
        // 画笔划过的痕迹变为透明
        paint.setColor(Color.BLACK);// 此处不能为透明
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint.setAntiAlias(true);// 变为光滑
        paint.setStyle(Style.STROKE);// 空心和实心
        paint.setStrokeJoin(Paint.Join.ROUND);// 前面圆角
        paint.setStrokeCap(Paint.Cap.ROUND);// 后圆角
        paint.setStrokeWidth(paintStrokeWidth);// 画笔宽度

        // 覆盖
        LayoutParams layoutParams = getLayoutParams();
        int height = layoutParams.height;
        int width = layoutParams.width;
        // if(layoutParams.height ==LayoutParams.MATCH_PARENT){
        //
        // }else{
        //
        // }

        // 记录痕迹
        mPath = new Path();
        bitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);// 4444占内存更少
        canvas = new Canvas(bitmap);
        canvas.drawColor(bgColor);
        isDraw = true;
    }
}