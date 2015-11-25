package com.task.service;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.NoGoingTaskInfo;
import com.renrentui.tools.DateUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ImageLoadManager;
import com.task.activity.TaskDetailInfoActivity;

/**
 * 未领取任务适配器
 * 
 * @author llp
 * 
 */
public class GetNoGoingAdapter extends BaseAdapter {
	private Context context;
	private List<NoGoingTaskInfo> noGoingTaskInfos;

	public GetNoGoingAdapter(Context context,
			List<NoGoingTaskInfo> noGoingTaskInfos) {
		this.context = context;
		this.noGoingTaskInfos = noGoingTaskInfos;
	}

	@Override
	public int getCount() {
		return noGoingTaskInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return noGoingTaskInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewholder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_task_nogoing, null);
			viewholder = new ViewHolder(convertView);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		final NoGoingTaskInfo noGoingTaskInfo = noGoingTaskInfos.get(position);
		if (Util.IsNotNUll(noGoingTaskInfo.logo)) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(
					noGoingTaskInfo.logo, viewholder.icon_pusher,
					R.drawable.pusher_logo);
		} else {
			viewholder.icon_pusher.setImageResource(R.drawable.pusher_logo);
		}
		String title = noGoingTaskInfo.taskName;
		if(title.length()>10)
			title = title.substring(0,10)+"...";
		viewholder.tv_Pusher.setText(title);
		viewholder.tv_TaskGeneralInfo.setText(noGoingTaskInfo.taskGeneralInfo);
		String now = DateUtils.ft.format(new Date());
		String date = "";
		if (DateUtils.getSecondInterval(noGoingTaskInfo.endTime, now) > 60) {
			if (DateUtils.getMinutesInterval(noGoingTaskInfo.endTime, now) > 60) {
				if (DateUtils.getHoursInterval(noGoingTaskInfo.endTime, now) > 60) {
					date = "剩余领取时间"
							+ DateUtils.getDayInterval(noGoingTaskInfo.endTime,
									now) + "天";
				} else {
					date = "剩余领取时间"
							+ DateUtils.getHoursInterval(
									noGoingTaskInfo.endTime, now) + "小时";
				}
			} else {
				date = "剩余领取时间"
						+ DateUtils.getMinutesInterval(noGoingTaskInfo.endTime,
								now) + "分";
			}
		} else {
			if (DateUtils.getSecondInterval(noGoingTaskInfo.endTime, now) == 0) {
				date = "任务不可领取";
			} else {
				date = "剩余领取时间"
						+ DateUtils.getSecondInterval(noGoingTaskInfo.endTime,
								now) + "秒";
			}
		}
		viewholder.tv_surolus_time.setText(date);
		viewholder.tv_Amount.setText(noGoingTaskInfo.getAmount());
		viewholder.tv_AvailableCount.setText("可领  "
				+ noGoingTaskInfo.availableCount + "");

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						TaskDetailInfoActivity.class);
				intent.putExtra("TaskId", noGoingTaskInfo.taskId);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	public class ViewHolder {
		public ImageView icon_pusher;
		public TextView tv_Pusher;
		public TextView tv_TaskGeneralInfo;
		public TextView tv_surolus_time;
		public TextView tv_Amount;
		public TextView tv_AvailableCount;

		public ViewHolder(View view) {
			icon_pusher = (ImageView) view.findViewById(R.id.icon_pusher);
			tv_Pusher = (TextView) view.findViewById(R.id.tv_pusher_taskName);
			tv_TaskGeneralInfo = (TextView) view
					.findViewById(R.id.tv_taskGeneralInfo);
			tv_surolus_time = (TextView) view
					.findViewById(R.id.tv_surolus_time);
			tv_Amount = (TextView) view.findViewById(R.id.tv_Amount);
			tv_AvailableCount = (TextView) view
					.findViewById(R.id.tv_AvailableCount);
		}
	}
}
