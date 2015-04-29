package com.gzfgeh.customviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
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
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        int childWidth, childHeight;
        int leftHeight = 0, rightHeight = 0, topWidth = 0, bottomWidth = 0;
        MarginLayoutParams childParams = null;

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        for(int i=0; i<getChildCount(); i++){
            View childView = getChildAt(i);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            childParams = (MarginLayoutParams) childView.getLayoutParams();
            if (i == 0 || i ==1){
                topWidth += childWidth + childParams.leftMargin + childParams.rightMargin;
            }

            if (i == 2 || i == 3){
                bottomWidth += childWidth + childParams.leftMargin + childParams.rightMargin;
            }

            if (i == 0 || i == 2){
                leftHeight += childHeight + childParams.topMargin + childParams.bottomMargin;
            }

            if (i == 1 || i == 3){
                rightHeight += childHeight + childParams.topMargin + childParams.bottomMargin;
            }
        }
        width = Math.max(topWidth, bottomWidth);
        height = Math.max(leftHeight, rightHeight);

        if (widthMode == MeasureSpec.EXACTLY)
            width = widthSize;
        if (heightMode == MeasureSpec.EXACTLY)
            height = heightSize;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childWidth = 0, childHeight = 0;
        MarginLayoutParams childParams = null;

        for (int i=0; i<getChildCount(); i++){
            View childView = getChildAt(i);
            childWidth = childView.getWidth();
            childHeight = childView.getHeight();
            childParams = (MarginLayoutParams) childView.getLayoutParams();
            int childLeft = 0, childTop = 0, childRigth = 0, childBottom = 0;
            switch (i){
                case 0:
                    childLeft = childParams.leftMargin;
                    childTop = childParams.topMargin;
                    break;

                case 1:
                    childLeft = getWidth() - childWidth - childParams.leftMargin - childParams.rightMargin;
                    childTop = childParams.topMargin;
                    break;

                case 2:
                    childLeft = childParams.leftMargin;
                    childTop = getHeight() - childHeight - childParams.bottomMargin;
                    break;

                case 3:
                    childLeft = getWidth() - childWidth - childParams.leftMargin - childParams.rightMargin;
                    childTop = getHeight() - childHeight - childParams.bottomMargin;
                    break;

                default:
                    break;
            }

            childRigth = childLeft + childWidth;
            childBottom = childHeight + childTop;
            childView.layout(childLeft, childTop, childRigth, childBottom);
        }
    }
}
