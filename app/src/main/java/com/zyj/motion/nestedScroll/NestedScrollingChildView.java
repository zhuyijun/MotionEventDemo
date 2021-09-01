package com.zyj.motion.nestedScroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/30 16:50
 */
public class NestedScrollingChildView extends LinearLayout implements NestedScrollingChild {

    public static final String TAG = "NestedScrolling";

    private Context mContext;

    private NestedScrollingChildHelper mChildHelper;
    private OverScroller mOverScroller;

    private int mDownX, mDownY;

    public NestedScrollingChildView(@NonNull Context context) {
        this(context, null);
    }

    public NestedScrollingChildView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollingChildView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        mChildHelper = new NestedScrollingChildHelper(this);
        mOverScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG, "++++++++++++onFinishInflate++++++++");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "++++++++++++onMeasure++++++++");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "++++++++++++onSizeChanged++++++++");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getRawX();
                mDownY = (int) event.getRawY();
                //子View开始滑动
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();

                int dx = moveX - mDownX;
                int dy = moveY - mDownY;


                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        super.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return super.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return super.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        super.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return super.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return super.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
    }

}
