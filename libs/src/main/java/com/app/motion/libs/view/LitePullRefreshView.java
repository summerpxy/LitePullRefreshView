package com.app.motion.libs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;
import android.view.View;

public class LitePullRefreshView extends ViewGroup {

    private int mTouchSlop;
    private int mDensity;
    private static final int MAX_DRAGGER_DISTANCE = 250;
    private int mTotalDraggerDistance;
    private ImageView mImageView;
    private Drawable mLoadingDrawable;
    private View mTargetView;
    private boolean mIsDragger;
    private int mCurrentOffsetTop;


    public LitePullRefreshView(Context context) {
        this(context, null);
    }

    public LitePullRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LitePullRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mDensity = (int) this.getResources().getDisplayMetrics().density;
        mTotalDraggerDistance = Math.round(MAX_DRAGGER_DISTANCE * mDensity);

        this.setWillNotDraw(false);
        //insert imageView into ViewGroup
        mImageView = new ImageView(context);
        this.addView(mImageView, 0);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureTargetView();
        if (mTargetView == null) {
            return;
        }
        int width = MeasureSpec.makeMeasureSpec(this.getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY);
        int height = MeasureSpec.makeMeasureSpec(this.getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY);
        mImageView.measure(width, height);
        mTargetView.measure(width, height);
    }

    private void ensureTargetView() {
        if (mTargetView != null) {
            return;
        }
        final int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            final View childView = this.getChildAt(i);
            if (childView != mImageView) {
                mTargetView = childView;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ensureTargetView();
        if (mTargetView == null) {
            return;
        }
        int left = getPaddingLeft();
        int top = getPaddingTop();
        mTargetView.layout(left, top + mCurrentOffsetTop, left + mTargetView.getMeasuredWidth(),
                top + mCurrentOffsetTop + mTargetView.getMeasuredHeight());
        mImageView.layout(left, top, left + mImageView.getMeasuredWidth(), top + mImageView.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mTargetView == null) {
            return super.onInterceptTouchEvent(ev);
        }
        int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        return mIsDragger;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    private void setLoadingDrawable() {

    }
}
