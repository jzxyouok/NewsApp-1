package com.example.alexanderlee.bmob_test;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by alexanderlee on 2017/12/19.
 */

public class AleeView extends ScrollView {
    private static final int LEN = 0xc8;
    private static final int DURATION = 500;
    private static final int MAX_DY = 200;
    private Scroller mScroller;
    TouchTool tool;
    int left, top;
    float startX, startY, currentX, currentY;
    int relativeLayoutH;
    int rootW, rootH;
    RelativeLayout relativeLayout;
    boolean scrollerType;

    public AleeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public AleeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public AleeView(Context context) {
        super(context);

    }

    public void setFramLayout(RelativeLayout framLayout) {
        this.relativeLayout = relativeLayout;
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }

    private int[] li = new int[2];
    private int[] li2 = new int[2];
    private float lastLy;
    private boolean startIsTop = true;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        int action = event.getAction();
        if (!mScroller.isFinished()) {
            return super.onTouchEvent(event);
        }
        currentX = event.getX();
        currentY = event.getY();
        relativeLayout=(RelativeLayout) findViewById(R.id.relativelayout_user) ;
        relativeLayout.getLocationInWindow(li);
        getLocationOnScreen(li2);
        relativeLayout.getTop();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (li[1] != li2[1]) {// 判断开始触摸时，relativeLayout和窗口顶部对齐没
                    startIsTop = false;
                }
                left = relativeLayout.getLeft();
                top = relativeLayout.getBottom();
                rootW = getWidth();
                rootH = getHeight();
                relativeLayoutH = relativeLayout.getHeight();
                startX = currentX;
                startY = currentY;
                tool = new TouchTool(relativeLayout.getLeft(), relativeLayout.getBottom(), relativeLayout.getLeft(),
                        relativeLayout.getBottom() + LEN);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!startIsTop && li[1] == li2[1]) {
                    startY = currentY;
                    startIsTop = true;
                }
                if (relativeLayout.isShown() && relativeLayout.getTop() >= 0) {
                    if (tool != null) {
                        int t = tool.getScrollY(currentY - startY);
                        if (!scrollerType && currentY < lastLy && relativeLayout.getHeight() > relativeLayoutH) {
                            scrollTo(0, 0);
                            relativeLayout.getLocationInWindow(li);
                            getLocationOnScreen(li2);
                            android.view.ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
                            params.height = t;
                            relativeLayout.setLayoutParams(params);
                            if (relativeLayout.getHeight() == relativeLayoutH && li[1] == li2[1]) {
                                scrollerType = true;
                            }
                            if (startIsTop && li[1] != li2[1]) {
                                startIsTop = false;
                            }
                        }
                        if (t >= top && t <= relativeLayout.getBottom() + LEN && li[1] == li2[1] && currentY > lastLy) {
                            android.view.ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
                            params.height = t;
                            relativeLayout.setLayoutParams(params);
                        }
                    }
                    scrollerType = false;
                }

                lastLy = currentY;
                break;
            case MotionEvent.ACTION_UP:
                if (li[1] == li2[1]) {
                    scrollerType = true;
                    mScroller.startScroll(relativeLayout.getLeft(), relativeLayout.getBottom(), 0 - relativeLayout.getLeft(),
                            relativeLayoutH - relativeLayout.getBottom(), DURATION);
                    invalidate();
                }
                startIsTop = true;
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            relativeLayout.layout(0, 0, x + relativeLayout.getWidth(), y);
            invalidate();
            if (!mScroller.isFinished() && scrollerType && y > MAX_DY) {
                android.view.ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
                params.height = y;
                relativeLayout.setLayoutParams(params);
            }
        }
    }

    public class TouchTool {

        private int startX, startY;

        public TouchTool(int startX, int startY, int endX, int endY) {
            super();
            this.startX = startX;
            this.startY = startY;
        }

        public int getScrollX(float dx) {
            int xx = (int) (startX + dx / 2.5F);
            return xx;
        }

        public int getScrollY(float dy) {
            int yy = (int) (startY + dy / 2.5F);
            return yy;
        }
    }
}
