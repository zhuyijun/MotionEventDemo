package com.zyj.motion.gesture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/20 16:46
 * @Desc 屏幕亮度调节
 */
@SuppressLint("AppCompatCustomView")
public class LightGestureView extends TextView {


    public static final String TAG = "LightGestureView";

    private GestureDetector mGestureDetector;

    private static int mLastX, mLastY;

    public LightGestureView(Context context) {
        super(context);
        initView(context);
    }

    public LightGestureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LightGestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {

        mGestureDetector = new GestureDetector(context, new MyGestureDetectorListener());

        setClickable(true);
        setLongClickable(true);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mGestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    public static class MyGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            mLastX = (int) e.getRawX();
            mLastY = (int) e.getRawY();
            return super.onDown(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            int moveX = (int) e2.getRawX();
            int moveY = (int) e2.getRawY();

            int dx = moveX - mLastX;
            int dy = moveY - mLastY;
//            Log.i(TAG, "++++++++++++++dy=" + dy);
            if (Math.abs(dy) > Math.abs(dx)) {
                if (mLightListener != null) {
                    mLightListener.onScreenLightChanged(dy);
                }
            }
            mLastX = moveX;
            mLastY = moveY;

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            return super.onContextClick(e);
        }
    }

    private static ScreenLightListener mLightListener;

    public void setScreenLightListener(ScreenLightListener lightListener) {
        this.mLightListener = lightListener;
    }

    public interface ScreenLightListener {
        void onScreenLightChanged(int dy);
    }
}
