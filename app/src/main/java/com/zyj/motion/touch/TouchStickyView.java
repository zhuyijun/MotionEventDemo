package com.zyj.motion.touch;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/19 15:12
 * @Desc: 边界判断以及松手贴边
 */
public class TouchStickyView extends View {

    public static final String TAG = "TouchStickyView";

    private int mDownX, mDownY;
    private int mLastX, mLastY;

    private int mScreenWidth, mScreenHeight;

    public TouchStickyView(Context context) {
        super(context);
        initView(context);
    }

    public TouchStickyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TouchStickyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "++++++++++ACTION_DOWN++++++++++");

                mDownX = mLastX = (int) event.getRawX();
                mDownY = mLastY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "++++++++++ACTION_MOVE++++++++++");

                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();

                int dX = moveX - mLastX;
                int dY = moveY - mLastY;

                //边界判断
                int left = getLeft();
                int top = getTop();
                int right = getRight();
                int bottom = getBottom();

                left += dX;
                top += dY;
                right += dX;
                bottom += dY;

                if (left <= 0) {
                    left = 0;
                    right = left + getWidth();
                }

                if (top <= 0) {
                    top = 0;
                    bottom = top + getHeight();
                }

                if (right >= mScreenWidth) {
                    right = mScreenWidth;
                    left = right - getWidth();
                }

                if (bottom >= mScreenHeight) {
                    bottom = mScreenHeight;
                    top = bottom - getHeight();
                }

                layout(left, top, right, bottom);

                mLastX = moveX;
                mLastY = moveY;

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "++++++++++ACTION_UP++++++++++");
                //点击事件拦截
                if (mDownX != mLastX && mDownY != mLastY) {
                    //说明移动了，进行贴边动画处理
                    gotoEdgeSticky(getLeft() + getWidth() / 2);
                    mLastX = mLastY = 0;
                    return true;
                }
                mLastX = mLastY = 0;
                break;
        }

        return super.onTouchEvent(event);
    }

    private void gotoEdgeSticky(int centerX) {

        if (centerX < mScreenWidth / 2) {
            //左边靠
            startAnimEdge(0);
        } else {
            //右边靠
            startAnimEdge(1);
        }

    }

    /**
     * @param orientationType 0: 左边 1：右边
     */
    private void startAnimEdge(int orientationType) {
        //ofInt(...):起始值和结束值
        ValueAnimator valueAnimator = ValueAnimator.ofInt(getLeft(),
                orientationType == 0 ? 0 : mScreenWidth - getWidth())
                .setDuration(220);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                layout(animatedValue, getTop(), animatedValue + getWidth(), getBottom());
            }
        });
        valueAnimator.start();
    }
}
