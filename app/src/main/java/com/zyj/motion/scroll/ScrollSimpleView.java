package com.zyj.motion.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/19 16:58
 * @Desc 滑动View
 */
public class ScrollSimpleView extends LinearLayout {

    public static final String TAG = "ScrollSimpleView";

    private int mTouchSlop;
    private int scaledMaximumFlingVelocity;
    private int scaledMinimumFlingVelocity;
    private int mMaxScrollDistance;

    private int mLastX, mLastY;

    /**
     * 滑动辅助类
     * 一般使用步骤：
     * 1：初始化：OverScroller mOverScroller = new OverScroller(context)
     * 2：重写computeScroll()：
     * if (mOverScroller.computeScrollOffset()) {
     * scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
     * invalidate();
     * }
     * 3：结合VelocityTracker的计算速率开启滑动：
     * mOverScroller.fling(0, getScrollY(), 0, -yVelocity, 0, 0, -0, mMaxScrollDistance);
     * invalidate();
     */
    private OverScroller mOverScroller;

    /**
     * 惯性滑动计算
     * 一般使用步骤：
     * 1：初始化： VelocityTracker mVelocityTracker = = VelocityTracker.obtain()
     * 2：添加事件：mVelocityTracker.addMovement(event)
     * 3：计算滑动速率: mVelocityTracker.computeCurrentVelocity(1000, scaledMaximumFlingVelocity)
     * 4: 获取相应滑动速率：int yVelocity = (int) mVelocityTracker.getYVelocity()
     * 5：开启惯性滑动：
     * mOverScroller.fling(0, getScrollY(), 0, -yVelocity, 0, 0, -0, mMaxScrollDistance);
     * invalidate();
     * 6：回收： mVelocityTracker.recycle();
     */
    private VelocityTracker mVelocityTracker;

    public ScrollSimpleView(Context context) {
        super(context);
        initView(context);
    }

    public ScrollSimpleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ScrollSimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        scaledMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        scaledMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();

        mOverScroller = new OverScroller(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算滑动内容高度
        int childCount = getChildCount();
        int contentHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            contentHeight = contentHeight + layoutParams.topMargin + layoutParams.bottomMargin
                    + childAt.getMeasuredHeight();
        }
        //计算滑动最大距离 = 内容高度 - 展示高度
        mMaxScrollDistance = contentHeight - getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.i(TAG, "++++++++onInterceptTouchEvent=ACTION_DOWN+++++++++");
                //初始化VelocityTracker
                mVelocityTracker = VelocityTracker.obtain();
                //判断是否再惯性滑动，还在滑动则停止
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                mLastX = (int) ev.getRawX();
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.i(TAG, "++++++++onInterceptTouchEvent=ACTION_MOVE+++++++++");
                int dX = (int) (ev.getRawX() - mLastX);
                int dY = (int) (ev.getRawY() - mLastY);

                //判断垂直滑动条件，拦截此次事件，自己消耗滑动事件
                if (Math.abs(dY) > Math.abs(dX) && Math.abs(dY) > mTouchSlop) {
                    return true;
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                Log.i(TAG, "++++++++onInterceptTouchEvent=ACTION_UP+++++++++");
                break;

        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.i(TAG, "++++++++onTouchEvent=ACTION_DOWN+++++++++");
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.i(TAG, "++++++++onTouchEvent=ACTION_MOVE+++++++++");

                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();

                int dX = mLastX - moveX;
                int dY = mLastY - moveY;

                int scrollY = getScrollY();
                if (Math.abs(dY) > Math.abs(dX)) {
                    //上下滑动边界判断
                    if (dY < 0 && scrollY <= 0) {
                        //到顶
                        scrollTo(0, 0);
                    } else if (dY > 0 && scrollY >= mMaxScrollDistance) {
                        //到底
                        scrollTo(0, mMaxScrollDistance);
                    } else {
                        scrollBy(0, dY);
                    }
                }

                mLastX = moveX;
                mLastY = moveY;

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                Log.i(TAG, "++++++++onTouchEvent=ACTION_UP+++++++++");

                //计算滑动速率
                mVelocityTracker.computeCurrentVelocity(1000, scaledMaximumFlingVelocity);
                //获取垂直方向滑动速率
                int yVelocity = (int) mVelocityTracker.getYVelocity();
                //开启惯性滑动
                if (Math.abs(yVelocity) > scaledMinimumFlingVelocity) {
                    mOverScroller.fling(0, getScrollY(), 0, -yVelocity, 0, 0, -0, mMaxScrollDistance);
                    invalidate();
                }

                mLastX = mLastY = 0;
                releaseVelocityTracker();

                break;
        }

        //为VelocityTracker添加事件
        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(event);
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断每次滑动动画是否结束，没有则每次刷新当前滑动位置
        if (mOverScroller.computeScrollOffset()) {
            scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
            invalidate();
        }
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
}
