package com.bruce.open.mortgage.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.bruce.open.mortgage.R;
import com.bruce.open.mortgage.Utils.Methods;

/**
 * Created by qizhenghao on 16/7/6.
 */
public class CustomTextView extends TextView {
    private int bottomLineColor = getResources().getColor(R.color.blue_light);
    private float bottomLineWidth = -1;
    private float bottomLineHeight = Methods.computePixelsWithDensity(2);

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.bottom_line, 0, 0);
        bottomLineWidth = array.getDimension(R.styleable.bottom_line_bottom_line_width, bottomLineWidth);
        bottomLineHeight = array.getDimension(R.styleable.bottom_line_bottom_line_height, bottomLineHeight);
        bottomLineColor = array.getColor(R.styleable.bottom_line_bottom_line_color, bottomLineColor);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint linePaint = new Paint();
        linePaint.setColor(bottomLineColor);
        linePaint.setAntiAlias(true);
        Log.d("Bruce", getMeasuredHeight() + " * " + getMeasuredWidth() + ", " + bottomLineHeight + " * " + bottomLineWidth);
        canvas.drawRect((getMeasuredWidth()-bottomLineWidth)/2, getMeasuredHeight() - bottomLineHeight, getMeasuredWidth() - (getMeasuredWidth() - bottomLineWidth)/2, getMeasuredHeight(), linePaint);
    }
}
