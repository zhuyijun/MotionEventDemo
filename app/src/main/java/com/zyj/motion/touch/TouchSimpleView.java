package com.zyj.motion.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/19 14:34
 * @Desc: view移动的几种方式
 */
public class TouchSimpleView extends View {

    //    public static final String TAG = TouchSimpleView.class.getSimpleName();
    private static final String TAG = "TouchSimpleView";

    private int mLastX, mLastY;

    public TouchSimpleView(Context context) {
        super(context);
        initView(context);
    }

    public TouchSimpleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TouchSimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //手指触摸屏幕
                Log.i(TAG, "+++++++++++MotionEvent.ACTION_DOWN+++++++++++");

                //获取手指在屏幕上触摸的位置
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                //手指再屏幕上移动
                Log.i(TAG, "+++++++++++MotionEvent.ACTION_MOVE+++++++++++");

                //获取手指实时移动再屏幕上的位置
                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();

                //获取两次之间的移动的距离差
                int dX = moveX - mLastX;
                int dY = moveY - mLastY;

                //view移动方法一
                layout(getLeft() + dX, getTop() + dY, getRight() + dX, getBottom() + dY);

                //view移动方法二
//                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin += dX;
//                layoutParams.topMargin += dY;
//                setLayoutParams(layoutParams);

                //view移动方法三
//                offsetLeftAndRight(dX);
//                offsetTopAndBottom(dY);

                //view移动方法四. 此种方法比较特殊，scrollBy方法是移动当前内容，所以获取父布局来移动内容的方式
//                ((View) getParent()).scrollBy(dX, dY);


                //更新上次移动的位置
                mLastX = moveX;
                mLastY = moveY;

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //手指抬起，离开屏幕
                Log.i(TAG, "+++++++++++MotionEvent.ACTION_UP+++++++++++");

                //清空此次移动记录
                mLastX = mLastY = 0;

                break;
        }

        //表示自己消耗此次事件
        return true;
    }
}
