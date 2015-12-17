package com.renrentui.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

import com.renrentui.app.R;

public class UIHelper {

    /**
     * dp2px
     *
     * @param context
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px2dp
     *
     * @param context
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * @param context
     * @return
     */
    public static int getScreenWidthDip(Context context) {
        int widthPx = UIHelper.getScreenWidth(context);
        int widthDip = UIHelper.px2dip(context, widthPx);
        return widthDip;
    }

    /**
     * @param context
     * @return
     */
    public static int getScreenHeightDip(Context context) {
        int heightPx = UIHelper.getScreenHeight(context);
        int heightDip = UIHelper.px2dip(context, heightPx);
        return heightDip;
    }

    /**
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (context != null) {
            return context.getResources().getDisplayMetrics().widthPixels;
        } else {
            return 0;
        }
    }

    /**
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (context != null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        } else {
            return 0;
        }
    }

    /**
     * @param context
     * @param oldScreenWidth 原有尺寸所在屏幕宽度
     * @param size           尺寸
     * @return 在现有屏幕中的尺寸
     */

    public static int getFitSize(Context context, int oldScreenWidth, int size) {
        if (context != null) {
            int thisScreenWidth = getScreenWidth(context);
            return size * thisScreenWidth / oldScreenWidth;
        } else {
            return 0;
        }
    }

    /** 获取状态栏高度
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 2*statusHeight;
    }

    /**
     * 设置前景色和背景色
     * @param context
     * @param str_1
     * @param str_2
     * @param fColor
     * @param bColor
     * @return
     */
    public static SpannableStringBuilder setStyleColorByColor(Context context,String str_1,String str_2,int fColor,int bColor){
        String strType =  " "+str_1+" ";
        String strTypeContent =strType +" "+str_2;
        int fstart = strTypeContent.indexOf(str_1);
        int fend = fstart + str_1.length();
        int bstart = 0;
        int bend = strType.length();
        SpannableStringBuilder style = new SpannableStringBuilder(strTypeContent);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(fColor)),fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new BackgroundColorSpan(context.getResources().getColor(bColor)),bstart,bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }
}
