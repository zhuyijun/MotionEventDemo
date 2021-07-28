package com.zyj.motion.gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/20 10:09
 * @Desc GestureDetector手势支持类
 */
public class GestureSimpleView extends View {

    public static final String TAG = "GestureSimpleView";

    private GestureDetector mGestureDetector;
    private static View mView;

    private static int mLastX, mLastY;
    private static int mDownX, mDownY;

    public GestureSimpleView(Context context) {
        super(context);
        initView(context);
    }

    public GestureSimpleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GestureSimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mGestureDetector = new GestureDetector(context, new MyGestureDetectorListener());
        setClickable(true);
        setLongClickable(true);
        /**
         * onTouch优先级比onTouchEvent高，onTouch先执行
         */
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mView = view;
                return mGestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    public static class MyGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //单机、双击都会调用
            Log.i(TAG, "+++++++++++++++onSingleTapUp+++++++++++");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //双击事件，调用一次
            Log.i(TAG, "+++++++++++++++onDoubleTap+++++++++++");
            Toast.makeText(mView.getContext(), "Double Click Event", Toast.LENGTH_SHORT).show();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            //触摸屏幕，每次都有
            Log.i(TAG, "+++++++++++++++onDown+++++++++++");
            mDownX = mLastX = (int) e.getRawX();
            mDownY = mLastY = (int) e.getRawY();
            return super.onDown(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //单击事件，调用一次
            Log.i(TAG, "+++++++++++++++onSingleTapConfirmed+++++++++++");
            Toast.makeText(mView.getContext(), "Click Event", Toast.LENGTH_SHORT).show();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            //双击事件。多次调用
            Log.i(TAG, "+++++++++++++++onDoubleTapEvent+++++++++++");
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //手指滑动一段距离松开手指的惯性滑动
            Log.i(TAG, "+++++++++++++++onFling+++++++++++");
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //手指再屏幕上滑动
            Log.i(TAG, "+++++++++++++++onScroll+++++++++++");

            int moveX = (int) e2.getRawX();
            int moveY = (int) e2.getRawY();

            int dX = moveX - mLastX;
            int dY = moveY - mLastY;

            mView.layout(mView.getLeft() + dX, mView.getTop() + dY, mView.getRight() + dX,
                    mView.getBottom() + dY);

            mLastX = moveX;
            mLastY = moveY;


            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            Log.i(TAG, "+++++++++++++++onContextClick+++++++++++");
            return super.onContextClick(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            //长按事件
            Toast.makeText(mView.getContext(), "Long Press Event", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "+++++++++++++++onLongPress+++++++++++");
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
            Log.i(TAG, "+++++++++++++++onShowPress+++++++++++");
        }
    }

}
