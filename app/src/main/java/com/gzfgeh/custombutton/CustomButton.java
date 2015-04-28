package com.gzfgeh.custombutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by guzhenfu on 4/27/2015.
 */
public class CustomButton extends View {
    private String text;
    private int textColor;
    private int textSize;
    private Paint paint;
    private Rect rect, bound;
    private Bitmap image;
    private int imageScrollType;
    private int width, height;

    public CustomButton(Context context) {
        this(context, null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomButton, defStyleAttr, 0);
        text = array.getString(R.styleable.CustomButton_text);
        textColor = array.getColor(R.styleable.CustomButton_textColor, textColor);
        textSize = array.getDimensionPixelSize(R.styleable.CustomButton_textSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        image = BitmapFactory.decodeResource(getResources(),array.getResourceId(R.styleable.CustomButton_image,0));
        imageScrollType = array.getInt(R.styleable.CustomButton_imageScallType, 0);

        array.recycle();
        paint = new Paint();
        rect = new Rect();
        bound = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), rect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY)
            width = widthSize;
        else{
            int imageWidth = image.getWidth() + getPaddingRight() + getPaddingLeft();
            int textWidths = rect.width() + getPaddingLeft() + getPaddingRight();
            width = Math.min(Math.max(imageWidth, textWidths), widthSize);
            if (widthMode == MeasureSpec.UNSPECIFIED)
                width = Math.max(imageWidth, textWidths);
        }

        if (heightMode == MeasureSpec.EXACTLY)
            height = heightSize;
        else{
            int imageHeight = image.getHeight() + getPaddingBottom() + getPaddingTop();
            int textHeight = rect.height() + imageHeight;
            height = Math.min(textHeight, heightSize);
            if (heightMode == MeasureSpec.UNSPECIFIED)
                height = textHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw bounds
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        //draw text
        bound.left = getPaddingLeft();
        bound.right = width - getPaddingRight();
        bound.top = getPaddingTop();
        bound.bottom = height - getPaddingBottom();

        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);

        if (rect.width() > width){
            TextPaint textPaint = new TextPaint(paint);
            String msg = TextUtils.ellipsize(text, textPaint,
                    (float)width - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), height - getPaddingBottom(), paint);
        }else{
            canvas.drawText(text,(width - rect.width())/2, height - getPaddingBottom(), paint);
        }
        bound.bottom = bound.bottom - rect.height();

        if (imageScrollType == 0){
            canvas.drawBitmap(image, null, bound, paint);
        }else{
            bound.left = width/2 - image.getWidth()/2;
            bound.right = width/2 + image.getWidth()/2;
            bound.top = (height - rect.height())/2 - image.getHeight()/2;
            bound.bottom = (height - rect.height())/2 + image.getHeight()/2;
            canvas.drawBitmap(image,null, bound, paint);
        }
    }
}
