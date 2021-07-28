package com.zyj.motion.gesture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/21 9:46
 * @Desc 缩放的ImageView
 */
@SuppressLint("AppCompatCustomView")
public class GestureImageView extends ImageView {

    public static final String TAG = "GestureImageView";

    private ScaleGestureDetector mScaleGestureDetector;
    private Matrix matrix;

    private float mBaseScale = 1.0f;
    private float mLastScale = 1.0f;

    public GestureImageView(Context context) {
        super(context);
        initView(context);
    }

    public GestureImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GestureImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        matrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector) {

                mBaseScale = detector.getScaleFactor() * mLastScale;

                //超出缩放范围，结束此次缩放
                if (mBaseScale >= 4 || mBaseScale <= 0.5f) {
                    mLastScale = mBaseScale;
                    return true;
                }

                matrix.setScale(mBaseScale, mBaseScale);
                setImageMatrix(matrix);

                mLastScale = mBaseScale;

                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                super.onScaleEnd(detector);
            }
        });

        setScaleType(ScaleType.MATRIX);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mScaleGestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

}
