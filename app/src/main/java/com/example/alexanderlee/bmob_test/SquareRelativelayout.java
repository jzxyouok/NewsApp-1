package com.example.alexanderlee.bmob_test;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by alexanderlee on 2017/11/14.
 */

public class SquareRelativelayout extends RelativeLayout {
    public SquareRelativelayout(Context context) {
        super(context);
    }

    public SquareRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelativelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0,widthMeasureSpec),getDefaultSize(0,heightMeasureSpec));
        int childWidthSize=getMeasuredWidth();
        widthMeasureSpec=MeasureSpec.makeMeasureSpec(childWidthSize,MeasureSpec.EXACTLY);
        heightMeasureSpec=widthMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
