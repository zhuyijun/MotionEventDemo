package com.zyj.motion.nestedScroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/30 16:50
 */
public class NestedScrollingChildView extends LinearLayout implements NestedScrollingChild2 {

    private Context mContext;
    private NestedScrollingChildHelper mChildHelper;
    private OverScroller mOverScroller;
    private VelocityTracker mVelocityTracker;

    private int maximumFlingVelocity;//系统定义的最大滑动速率
    private int minimumFlingVelocity;//系统定义的最小滑动速率
    private int touchSlop;//系统定义的最小滑动距离
    private int maxScrollHeight;//子View能够滑动的最大高度

    public NestedScrollingChildView(Context context) {
        this(context, null);
    }

    public NestedScrollingChildView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollingChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initNestedConfig(context);
    }

    private void initNestedConfig(Context context) {
        this.mContext = context;
        mChildHelper = new NestedScrollingChildHelper(this);
        mChildHelper.setNestedScrollingEnabled(true);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        maximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        minimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        touchSlop = viewConfiguration.getScaledTouchSlop();

        mOverScroller = new OverScroller(context);
        mVelocityTracker = VelocityTracker.obtain();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //遍历子View, 获取子View的高度
        int childCount = getChildCount();
        int sum = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int measuredHeight = childAt.getMeasuredHeight();
            sum += measuredHeight;
        }
        maxScrollHeight = sum - (getScreenHeight(mContext) - dp2px(mContext, 56));
    }

    private int mDownY = 0;
    private int[] mConsumed = new int[2];
    private int[] mOffset = new int[2];

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                stopOverScroll();
                mDownY = (int) event.getRawY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) event.getRawY();
                int dy = mDownY - moveY;

                if (dispatchNestedPreScroll(0, dy, mConsumed, mOffset, ViewCompat.TYPE_TOUCH)) {
                    dy -= mConsumed[1];
                }

                scrollBySelf(dy);

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                //TODO 缺少惯性滑动处理
                mVelocityTracker.computeCurrentVelocity(1000);
                int yVelocity = (int) mVelocityTracker.getYVelocity();
                if (mOverScroller != null) {
                    mOverScroller.fling(0, getScrollY(), 0, -yVelocity, 0, 0, 0, maxScrollHeight);
                    invalidate();
                }

                stopNestedScroll(ViewCompat.TYPE_TOUCH);
                releaseVelocityTracker();
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mOverScroller.computeScrollOffset()) {
            scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public void scrollTo(int x, int y) {

        if (y < 0) {
            y = 0;
        }

        if (y > maxScrollHeight) {
            y = maxScrollHeight;
        }

        super.scrollTo(x, y);
    }

    private void scrollBySelf(int dy) {

        int consumed = 0;
        int unConsumed = 0;

        if (dy != 0) {
            int oldScrollY = getScrollY();
            scrollBy(0, dy);
            consumed = getScrollY() - oldScrollY;
            unConsumed = dy - consumed;

            if (unConsumed != 0) {
                dispatchNestedScroll(0, consumed, 0, unConsumed, mOffset, ViewCompat.TYPE_TOUCH);
            }
        }
    }

    private void stopOverScroll() {
        if (mOverScroller != null && !mOverScroller.isFinished()) {
            mOverScroller.abortAnimation();
        }
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return mChildHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        mChildHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return mChildHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    public static int dp2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
