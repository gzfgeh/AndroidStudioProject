package com.gzfgeh.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by guzhenfu on 4/28/2015.
 */
public class CustomProgressView extends View {
    private int firstColor;
    private int secondColor;
    private int speed;
    private int circleWidth;
    private String text;
    private int textColor;
    private int textSize;
    private Paint paint;
    private Rect rect;
    private int progress;
    private boolean next;

    public CustomProgressView(Context context) {
        this(context, null);
    }
    public CustomProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CustomProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, defStyleAttr, 0);
        firstColor = array.getColor(R.styleable.CustomView_firstColor, Color.GREEN);
        secondColor = array.getColor(R.styleable.CustomView_secondColor, Color.RED);
        speed = array.getIndex(R.styleable.CustomView_speed);
        circleWidth = array.getDimensionPixelOffset(R.styleable.CustomView_circleWidth, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        text = array.getString(R.styleable.CustomView_centerText);
        array.recycle();
        paint = new Paint();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    progress ++;
                    text = String.valueOf(progress/360 * 100) + "%";
                    if (progress == 360){
                        progress = 0;
                        if (next)
                            next = false;
                        else
                            next = true;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int radius = center - circleWidth/2;
        paint.setStrokeWidth(circleWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(center - radius,center - radius, center + radius, center + radius);
        if (!next){
            paint.setColor(firstColor);
            canvas.drawCircle(center, center, radius, paint);
            paint.setColor(secondColor);
            canvas.drawArc(rectF, -90, progress, false, paint);
        }else{
            paint.setColor(secondColor);
            canvas.drawCircle(center, center, radius, paint);
            paint.setColor(firstColor);
            canvas.drawArc(rectF, -90, progress, false, paint);
        }

    }
}
