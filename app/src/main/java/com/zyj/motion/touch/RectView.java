package com.zyj.motion.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/19 16:18
 * @Desc 矩形View
 */
public class RectView extends View {

    private Paint mPaint;

    private int mLeft, mTop, mRight, mBottom;

    public RectView(Context context) {
        super(context);
        initView(context);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mPaint = new Paint();
        //画笔颜色
        mPaint.setColor(ContextCompat.getColor(context, android.R.color.holo_red_light));
        //抗锯齿
        mPaint.setAntiAlias(true);
        //边框宽度
        mPaint.setStrokeWidth(3);
        //填充模式
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void updateRectPosition(int left, int top, int right, int bottom) {
        this.mLeft = left;
        this.mTop = top;
        this.mRight = right;
        this.mBottom = bottom;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mLeft, mTop, mRight, mBottom, mPaint);
    }
}
