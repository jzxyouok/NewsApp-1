package com.example.alexanderlee.bmob_test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by alexanderlee on 2017/11/24.
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;
    private Paint dividerPaint;
    public ItemDecoration(Context context){
        dividerPaint=new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.colorGrey2));
        dividerHeight=context.getResources().getDimensionPixelOffset(R.dimen.divider_Hight);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view)!=0){
            outRect.top=dividerHeight;
        }

    }

//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
////        int childCount=parent.getChildCount();
////        int left=parent.getPaddingLeft();
////        int right=parent.getWidth()-parent.getPaddingRight();
////        for(int i=0;i<childCount-1;i++){
////            View view=parent.getChildAt(i);
////            float top=view.getBottom();
////            float bottom=view.getBottom()+dividerHeight;
//            c.drawRect(0,0,0,0,dividerPaint);
////        }
//    }
}
