package com.zyj.motion.nestedScroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.viewpager2.widget.ViewPager2;

import com.zyj.motion.R;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/30 14:00
 */
public class NestedScrollingParentView extends LinearLayout implements NestedScrollingParent {

    public static final String TAG = "NestedScrolling";

    private Context mContext;
    private NestedScrollingParentHelper mParentHelper;
    private OverScroller mOverScroller;

    private View mTopView;
    private View mTabView;
    private ViewPager2 mViewPager;

    private int mTopViewHeight;

    public NestedScrollingParentView(Context context) {
        this(context, null);
    }

    public NestedScrollingParentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollingParentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        mParentHelper = new NestedScrollingParentHelper(this);
        mOverScroller = new OverScroller(context);
    }

    /**
     * 在xml中初始化控件，会执行该方法；如果采用动态news初始化，则不会调用该方法(可以在onAttachedToWindow中)
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.id_nested_scrolling_top);
        mTabView = findViewById(R.id.id_nested_scrolling_tab);
        mViewPager = findViewById(R.id.id_nested_scrolling_viewpager);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置ViewPager显示区域大小
        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - mTabView.getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTopView.getMeasuredHeight();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onStopNestedScroll(View child) {
        mParentHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    /**
     * 后于child滚动
     *
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    /**
     * 先于child滚动
     *
     * @param target
     * @param dx
     * @param dy       dy > 0 向上滑动；dy < 0 向下滑动
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //canScrollVertically(-1): -1表示向下滑动，1表示向上滑动
        if ((dy > 0 && getScrollY() < mTopViewHeight) ||
                (dy < 0 && getScrollY() > 0 && !target.canScrollVertically(-1))) {
            scrollBy(0, dy);
            //通知child滑动了多少
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (getScrollY() >= mTopViewHeight) {
            return false;
        }
        mOverScroller.fling(0, getScrollY(), 0, (int) velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mOverScroller.computeScrollOffset()) {
            scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public void scrollTo(int x, int y) {

        //滑动范围限制
        if (y < 0) {
            y = 0;
        }

        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }

        super.scrollTo(x, y);
    }
}
