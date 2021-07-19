package com.zyj.motion.touch;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.zyj.motion.R;

public class TouchDrawActivity extends AppCompatActivity {

    private TouchDrawView mTouchDrawView;
    private RectView mRectView;

    private int mDownX, mDownY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_draw);

        mTouchDrawView = findViewById(R.id.touchDrawView);
        mRectView = findViewById(R.id.touchRectView);

        mTouchDrawView.setOnTouchEventListener(new TouchDrawView.OnTouchEventListener() {
            @Override
            public void onActionDown(MotionEvent event) {
                mDownX = mTouchDrawView.getLeft();
                mDownY = mTouchDrawView.getTop();
                if (mRectView != null) {
                    mRectView.updateRectPosition(mTouchDrawView.getLeft(), mTouchDrawView.getTop(),
                            mTouchDrawView.getLeft(), mTouchDrawView.getTop());
                }
            }

            @Override
            public void onActionMove(MotionEvent event) {
                if (mRectView != null) {
                    mRectView.updateRectPosition(mDownX, mDownY,
                            mTouchDrawView.getLeft(), mTouchDrawView.getTop());
                }

            }

            @Override
            public void onActionUp(MotionEvent event) {
                if (mRectView != null) {
                    mRectView.updateRectPosition(0, 0, 0, 0);
                }
            }
        });

    }
}