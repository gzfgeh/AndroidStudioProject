package com.gzfgeh.customviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by gzfgeh on 4/28/15.
 */
public class CustomViewGroup extends ViewGroup {
    public CustomViewGroup(Context context) {
        this(context, null);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
