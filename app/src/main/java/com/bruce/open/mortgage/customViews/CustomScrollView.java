package com.bruce.open.mortgage.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by qizhenghao on 16/6/21.
 */
public class CustomScrollView extends ScrollView {

    public static boolean IS_INTERCEPT = false;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (IS_INTERCEPT)
            return false;
        else
            return super.onInterceptTouchEvent(ev);
    }
}
