package com.zyj.motion.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.zyj.motion.R;

import java.util.ArrayList;

/**
 * @Author zhuyijun
 * @Version 1.0.0
 * @Date 2021/7/29 15:31
 */
public class EventDispatchParentLayout extends LinearLayout {

    public static final String TAG = "EventDispatch";

    private Context mContext;

    private OverScroller mOverScroller;
    private VelocityTracker mVelocityTracker;
    private int scaledMaximumFlingVelocity;
    private int scaledMinimumFlingVelocity;
    private int scaledTouchSlop;

    private View topView;
    private View tabView;
    private ViewPager2 viewPager;
    private RecyclerView mInnerScrollView;

    private int mTopHeight;
    private int mDownX, mDownY;
    private boolean mIsDragging = false;
    private boolean mIsTop = false;

    private ArrayList<ItemFragment> list = new ArrayList<>();


    public EventDispatchParentLayout(Context context) {
        this(context, null);
    }

    public EventDispatchParentLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventDispatchParentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        mOverScroller = new OverScroller(context);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        scaledMaximumFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        scaledMinimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();

    }

    public void updateFragmentList(ArrayList<ItemFragment> list) {
        this.list = list;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG, "+++++++++++++onFinishInflate++++++++");
        topView = findViewById(R.id.id_event_dispatch_top);
        tabView = findViewById(R.id.id_event_dispatch_tab);
        viewPager = findViewById(R.id.id_event_dispatch_viewpager);
        mInnerScrollView = findViewById(R.id.id_event_dispatch_inner_scroll);
    }

    private void getCurrentInnerScrollView() {
        int currentItem = viewPager.getCurrentItem();
        ItemFragment itemFragment = list.get(currentItem);
        mInnerScrollView = itemFragment.getRootView().findViewById(R.id.id_event_dispatch_inner_scroll);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - tabView.getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopHeight = topView.getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mVelocityTracker = VelocityTracker.obtain();
                mDownX = (int) ev.getRawX();
                mDownY = (int) ev.getRawY();
                getCurrentInnerScrollView();
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = (int) (ev.getRawY() - mDownY);
                int dx = (int) (ev.getRawX() - mDownX);
                if (Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > scaledTouchSlop) {
                    if (!mIsTop || ( mInnerScrollView.computeVerticalScrollOffset() == 0 && mIsTop && dy > 0)) {
                        return true;
                    }
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //如果还处在惯性滑动，手指按下时停止惯性滑动
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                mDownX = (int) event.getRawX();
                mDownY = (int) event.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();

                int dx = moveX - mDownX;
                int dy = moveY - mDownY;

                if (!mIsDragging && Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > scaledTouchSlop) {
                    mIsDragging = true;
                }

                if (mIsDragging) {
                    scrollBy(0, -dy);
                    mDownY = moveY;
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsDragging = false;
                mVelocityTracker.computeCurrentVelocity(1000, scaledMaximumFlingVelocity);
                int yVelocity = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(yVelocity) > scaledMinimumFlingVelocity) {
                    mOverScroller.fling(0, getScrollY(), 0, -yVelocity,
                            0, 0, 0, mTopHeight);
                    invalidate();
                }
                releaseVelocityTracker();
                break;
        }

        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(event);
        }

        return super.onTouchEvent(event);
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
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
        if (y <= 0) {
            y = 0;
        }

        if (y >= mTopHeight) {
            y = mTopHeight;
        }

        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }

        mIsTop = getScrollY() == mTopHeight;
    }
}
