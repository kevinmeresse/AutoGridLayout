package com.km.myproject.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.GridLayout;

import com.km.myproject.R;

/**
 * A custom implementation of the standard GridLayout to allow having a dynamic span count,
 * so that we can fit as many columns as we can depending on column width and screen size
 */

public class AutoGridLayout extends GridLayout {

    private int defaultColumnCount;
    private int columnWidth;

    public AutoGridLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public AutoGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public AutoGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AutoGridLayout, 0, defStyleAttr);
        try {
            columnWidth = a.getDimensionPixelSize(R.styleable.AutoGridLayout_columnWidth, 0);

            int[] set = { android.R.attr.columnCount /* id 0 */ };
            a = getContext().obtainStyledAttributes(attrs, set, 0, defStyleAttr);
            defaultColumnCount = a.getInt(0, 10);
        } finally {
            a.recycle();
        }

        /* Initially set columnCount to 1, will be changed automatically later. */
        setColumnCount(1);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        int width = MeasureSpec.getSize(widthSpec);
        if (columnWidth > 0 && width > 0) {
            int totalSpace = width - getPaddingRight() - getPaddingLeft();
            int columnCount = Math.max(1, totalSpace / columnWidth);
            setColumnCount(columnCount);
        } else {
            setColumnCount(defaultColumnCount);
        }
    }
}
