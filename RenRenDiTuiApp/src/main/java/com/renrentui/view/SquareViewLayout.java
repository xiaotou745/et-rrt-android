/*
 * File Name: SquareView.java 
 * History:
 * Created by Administrator on 2015-11-3
 */
package com.renrentui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/*
 * 方形布局 (Description)
 * 
 * @author zaokafei
 * @version 1.0
 * @date 2015-11-3
 */
public class SquareViewLayout extends RelativeLayout {
    public SquareViewLayout(Context context) {
        super(context);
    }

    public SquareViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareViewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
