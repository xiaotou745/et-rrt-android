/*
 * File Name: LoadingView.java 
 * History:
 * Created by admin on 2015-4-9
 */
package com.task.upload.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.renrentui.app.R;


public class LoadingView extends ImageView {

    /**
     * 圆属性
     */
    private Paint paint;
    private Paint paintFrame;
    /**
     * 位置大小属性
     */
    private RectF oval;
    private RectF ovalFrame;
    /**
     * 其实角度、到达角度
     */
    private float mStart = 270;
    private float mSweep;
    /**
     * 圆半径
     */
    private int radius;
    /**
     * 边框宽度
     */
    private int frameWidth = 1;
    /**
     * 内间距
     */
    private int framePadding = 1;
    /**
     * 当前进度
     */
    private float progress = 0;
    /**
     * 是否显示加载视图
     */
    private boolean isShowLoadingView = true;
    /**
     * 是否正在进行加载
     */
    private boolean isProgress = true;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        frameWidth = getContext().getResources().getDimensionPixelSize(R.dimen.loadingview_line);
        radius = getContext().getResources().getDimensionPixelSize(R.dimen.loadingview_radius);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x32FFFFFF);

        paintFrame = new Paint(paint);
        paintFrame.setStyle(Paint.Style.STROKE);
        paintFrame.setStrokeWidth(frameWidth);
    }

    private int i = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 初始化圆位置，居中
         */
        if (oval == null) {
            oval = new RectF(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight()
                    / 2 + radius);

            ovalFrame = new RectF(getWidth() / 2 - radius - frameWidth - framePadding, getHeight() / 2 - radius
                    - frameWidth - framePadding, getWidth() / 2 + radius + frameWidth + framePadding, getHeight() / 2
                    + radius + frameWidth + framePadding);
        }
        /**
         * 验证角度
         */
        if (mSweep > 360) {
            mSweep = 360;
        }

        if (isShowLoadingView && isProgress) {
            /**
             * 绘制被色半透明背景
             */
            canvas.drawColor(Color.parseColor("#88000000"));
            /**
             * 绘制圆
             */
            canvas.drawArc(oval, mStart, mSweep, true, paint);

            /**
             * 绘制圆边框
             */
            canvas.drawArc(ovalFrame, 0, 360, true, paintFrame);
        }

        // /**
        // * 轮训画圆
        // */
        // setProgress(i);
        // i++;
        // if (i == 100)
        // i = 0;
        // invalidate();
    }

    /**
     * 设置进度
     * 
     * @param progress
     */
    public void setProgress(float progress) {
        if (this.progress != progress && progress < 100) {
            this.progress = progress;
            isProgress = true;
            mSweep = progress * (360.0f / 100.0f);
            invalidate();
        } else if (progress >= 100) {
            isProgress = false;
            invalidate();
        }
    }

    /**
     * 设置加载状态
     */
    public void setLoadingVisibility(int visibility) {
        isShowLoadingView = View.VISIBLE == visibility ? true : false;
        invalidate();
    }

    /**
     * 获取当前进度
     * 
     * @return
     */
    public float getProgress() {
        return progress;
    }
}
