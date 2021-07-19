package com.zyj.motion.touch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/19 16:12
 * @Desc: view移动画矩形
 */
@SuppressLint("AppCompatCustomView")
public class TouchDrawView extends ImageView {

    private int mLastX, mLastY;

    public TouchDrawView(Context context) {
        super(context);
        initView(context);
    }

    public TouchDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TouchDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mListener != null) {
                    mListener.onActionDown(event);
                }

                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                if (mListener != null) {
                    mListener.onActionMove(event);
                }

                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();

                int dX = moveX - mLastX;
                int dY = moveY - mLastY;

                layout(getLeft() + dX, getTop() + dY, getRight() + dX, getBottom() + dY);

                mLastX = moveX;
                mLastY = moveY;

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mListener != null) {
                    mListener.onActionUp(event);
                }
                mLastX = mLastY = 0;
                break;
        }

        return true;
    }

    private OnTouchEventListener mListener;

    public void setOnTouchEventListener(OnTouchEventListener listener) {
        this.mListener = listener;
    }

    public interface OnTouchEventListener {
        void onActionDown(MotionEvent event);

        void onActionMove(MotionEvent event);

        void onActionUp(MotionEvent event);
    }
}
