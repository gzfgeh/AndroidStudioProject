package com.gzfgeh.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gzfgeh on 4/28/15.
 */
public class CustomVoiceView extends View {
    private int firstColor;
    private int secondColor;
    private int dotCount;
    private int circleWidth;
    private Bitmap bg;

    public CustomVoiceView(Context context) {
        this(context, null);
    }

    public CustomVoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //TypedArray array = context.getTheme().obtainStyledAttributes(attrs)
    }
}
