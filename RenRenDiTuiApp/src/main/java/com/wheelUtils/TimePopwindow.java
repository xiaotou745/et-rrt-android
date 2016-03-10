package com.wheelUtils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.renrentui.app.R;

import java.util.Calendar;
import java.util.Locale;

public class TimePopwindow extends PopupWindow implements OnClickListener {
    private String hour;
    private String minute;
    private String day;
    private WheelView HourWV;
    private WheelView MinuteWV;
    private WheelView DayWV;
    private TextView tvTimeSelected;
    private Context context;

    private SelectTimeMsgListener mSelectTimeMsgObj;

    public SelectTimeMsgListener getmSelectTimeMsgObj() {
        return mSelectTimeMsgObj;
    }

    public void setmSelectTimeMsgObj(SelectTimeMsgListener mSelectTimeMsgObj) {
        this.mSelectTimeMsgObj = mSelectTimeMsgObj;
    }

    public TimePopwindow(Context context) {
        super();
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.timer_layout, null);
        tvTimeSelected = (TextView) view.findViewById(R.id.tv_time_selected_ok);
        tvTimeSelected.setOnClickListener(this);




        Calendar cYear = Calendar.getInstance(Locale.CHINA);
        final int currentYear = cYear.get(Calendar.YEAR)-1;
        int currentMonth = cYear.get(Calendar.MONTH);

        DayWV = (WheelView) view.findViewById(R.id.wv_day_setting);
        NumericWheelAdapter yearAdapter = new NumericWheelAdapter(1960, currentYear);

        DayWV.setAdapter(yearAdapter);
        DayWV.setLabel("年");
        DayWV.setCyclic(false);

        HourWV = (WheelView) view.findViewById(R.id.wv_hour_setting);
        MinuteWV = (WheelView) view.findViewById(R.id.wv_minute_setting);

        HourWV.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
        HourWV.setLabel("月");
        HourWV.setCyclic(true);


        for (int i = 0; i < yearAdapter.getItemsCount(); i++) {
            if (yearAdapter.getItem(i).equals("" + currentYear)) {
                DayWV.setCurrentItem(i);
            }
        }

        HourWV.setCurrentItem(currentMonth);
        HourWV.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year = Integer.parseInt(DayWV.getAdapter().getItem(DayWV.getCurrentItem()));
                int month = Integer.parseInt(HourWV.getAdapter().getItem(HourWV.getCurrentItem()));
                int minute = Integer.parseInt(MinuteWV.getAdapter().getItem(MinuteWV.getCurrentItem()));
                int maxMonth = getMonthLastDay(year, month);
                MinuteWV.setAdapter(new NumericWheelAdapter(1, maxMonth, "%02d"));
                MinuteWV.setLabel("日");
                MinuteWV.setCyclic(true);
                if (minute > maxMonth) {
                    MinuteWV.setCurrentItem(maxMonth);
                }
            }
        });
        DayWV.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year = Integer.parseInt(DayWV.getAdapter().getItem(DayWV.getCurrentItem()));
                int month = Integer.parseInt(HourWV.getAdapter().getItem(HourWV.getCurrentItem()));
                int minute = Integer.parseInt(MinuteWV.getAdapter().getItem(MinuteWV.getCurrentItem()));
                int maxMonth = getMonthLastDay(year, month);
                MinuteWV.setAdapter(new NumericWheelAdapter(1, maxMonth, "%02d"));
                MinuteWV.setLabel("日");
                MinuteWV.setCyclic(true);
                if (minute > maxMonth) {
                    MinuteWV.setCurrentItem(maxMonth);
                }
            }
        });

        int currentDay = cYear.get(Calendar.DAY_OF_MONTH);
        int maxMonth = getMonthLastDay(currentYear, currentMonth);
        MinuteWV.setAdapter(new NumericWheelAdapter(1, maxMonth, "%02d"));
        MinuteWV.setLabel("日");
        MinuteWV.setCyclic(true);
        MinuteWV.setCurrentItem(currentDay - 1);

        this.setContentView(view);
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        this.update();
    }

    /**
     * 得到指定月的天数
     */
    private static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time_selected_ok:
                if (mSelectTimeMsgObj != null) {
                    setSelectTimeMsg();
                }
                break;
            default:
                break;
        }

    }

    public void setSelectTimeMsg() {
        StringBuffer sb = new StringBuffer();
        day = DayWV.getAdapter().getItem(DayWV.getCurrentItem());
        hour = HourWV.getAdapter().getItem(HourWV.getCurrentItem());
        minute = MinuteWV.getAdapter().getItem(MinuteWV.getCurrentItem());
        sb.append(day).append("-").append(hour).append("-").append(minute);
        mSelectTimeMsgObj.selectTimeMsg(sb.toString());
    }

    public interface SelectTimeMsgListener {
        void selectTimeMsg(String strTime);
    }
}
