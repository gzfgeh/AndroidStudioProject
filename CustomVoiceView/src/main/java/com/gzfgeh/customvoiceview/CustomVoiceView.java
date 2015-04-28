package com.gzfgeh.customvoiceview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gzfgeh on 4/28/15.
 */
public class CustomVoiceView extends View {
    private int firstColor;
    private int secondColor;
    private int circleWidth;
    private int dotCount;
    private Bitmap bg;
    private int splitSize;
    private Paint paint;
    private Rect rect;
    private int currentCnt = 3;

    public CustomVoiceView(Context context) {
        this(context, null);
    }

    public CustomVoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomVoiceView, defStyleAttr, 0);
        firstColor = array.getColor(R.styleable.CustomVoiceView_firstColor, Color.GREEN);
        secondColor = array.getColor(R.styleable.CustomVoiceView_secondColor, Color.RED);
        dotCount = array.getInt(R.styleable.CustomVoiceView_dotCount, 20);
        circleWidth = array.getDimensionPixelOffset(R.styleable.CustomVoiceView_circleWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        bg = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.CustomVoiceView_bg, 0));
        splitSize = array.getInt(R.styleable.CustomVoiceView_splitSize, 20);
        array.recycle();

        paint = new Paint();
        rect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStrokeWidth(circleWidth); // 设置圆环的宽度
        paint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断电形状为圆头
        paint.setAntiAlias(true); // 消除锯齿
        paint.setStyle(Paint.Style.STROKE); // 设置空心
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - circleWidth / 2;// 半径

        drawOval(canvas, centre, radius);

        /**
         * 计算内切正方形的位置
         */
        int relRadius = radius - circleWidth / 2;// 获得内圆的半径
        /**
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
         */
        rect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + circleWidth;
        /**
         * 内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2
         */
        rect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + circleWidth;
        rect.bottom = (int) (rect.left + Math.sqrt(2) * relRadius);
        rect.right = (int) (rect.left + Math.sqrt(2) * relRadius);

        /**
         * 如果图片比较小，那么根据图片的尺寸放置到正中心
         */
        if (bg.getWidth() < Math.sqrt(2) * relRadius){
            rect.left = (int) (rect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - bg.getWidth() * 1.0f / 2);
            rect.top = (int) (rect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - bg.getHeight() * 1.0f / 2);
            rect.right = (int) (rect.left + bg.getWidth());
            rect.bottom = (int) (rect.top + bg.getHeight());
        }

        // 绘图
        canvas.drawBitmap(bg, null, rect, paint);
    }

    /**
     * 根据参数画出每个小块
     *
     * @param canvas
     * @param centre
     * @param radius
     */
    private void drawOval(Canvas canvas, int centre, int radius){
        /**
         * 根据需要画的个数以及间隙计算每个块块所占的比例*360
         */
        float itemSize = (360 * 1.0f - dotCount * splitSize) / dotCount;

        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限

        paint.setColor(firstColor); // 设置圆环的颜色
        for (int i = 0; i < dotCount; i++)
        {
            canvas.drawArc(oval, i * (itemSize + splitSize), itemSize, false, paint); // 根据进度画圆弧
        }

        paint.setColor(secondColor); // 设置圆环的颜色
        for (int i = 0; i < currentCnt; i++)
        {
            canvas.drawArc(oval, i * (itemSize + splitSize), itemSize, false, paint); // 根据进度画圆弧
        }
    }

    public void up(){
        currentCnt ++;
        if (currentCnt > 20)
            currentCnt = 20;

        postInvalidate();
    }
    public void down(){
        currentCnt --;
        if (currentCnt <= 0)
            currentCnt = 0;

        postInvalidate();
    }

    private int yDown, yUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                yDown = (int)event.getY();
                break;

            case MotionEvent.ACTION_UP:
                yUp = (int) event.getY();
                if (yUp > yDown)
                    down();
                else
                    up();
                break;

            default:
                break;
        }
        return true;
    }
}
