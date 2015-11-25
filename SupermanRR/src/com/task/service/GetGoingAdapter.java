package com.task.service;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.OnGoingTaskInfo;
import com.renrentui.tools.DateUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ImageLoadManager;
import com.task.activity.TaskDetailInfoActivity;

/**
 * 已领取任务适配器
 * 
 * @author llp
 * 
 */
public class GetGoingAdapter extends BaseAdapter {
	private Context context;
	private List<OnGoingTaskInfo> noGoingTaskInfos;

	public GetGoingAdapter(Context context,
			List<OnGoingTaskInfo> noGoingTaskInfos) {
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
					R.layout.item_task_going, null);
			viewholder = new ViewHolder(convertView);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		final OnGoingTaskInfo noGoingTaskInfo = noGoingTaskInfos.get(position);
		if (Util.IsNotNUll(noGoingTaskInfo.logo)) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(
					noGoingTaskInfo.logo, viewholder.icon_pusher,
					R.drawable.pusher_logo);
		} else {
			viewholder.icon_pusher.setImageResource(R.drawable.pusher_logo);
		}
		String title = noGoingTaskInfo.taskName;
		if (title.length() > 10)
			title = title.substring(0, 10) + "...";
		viewholder.tv_Pusher.setText(title);
		viewholder.tv_TaskGeneralInfo.setText(noGoingTaskInfo.taskGeneralInfo);
		String now = DateUtils.ft.format(new Date());
		String date = "";
		if (Math.abs(DateUtils.getSecondInterval(now,
				noGoingTaskInfo.receivedTime, noGoingTaskInfo.taskCycle)) > 60
				&& DateUtils
						.getSecondInterval(now, noGoingTaskInfo.receivedTime,
								noGoingTaskInfo.taskCycle) < 0) {
			if (Math.abs(DateUtils.getMinutesInterval(now,
					noGoingTaskInfo.receivedTime, noGoingTaskInfo.taskCycle)) > 60
					&& DateUtils.getMinutesInterval(now,
							noGoingTaskInfo.receivedTime,
							noGoingTaskInfo.taskCycle) < 0) {
				if (Math.abs(DateUtils
						.getHoursInterval(now, noGoingTaskInfo.receivedTime,
								noGoingTaskInfo.taskCycle)) > 60
						&& DateUtils.getHoursInterval(now,
								noGoingTaskInfo.receivedTime,
								noGoingTaskInfo.taskCycle) < 0) {
					date = "剩余完成时间"
							+ Math.abs(DateUtils.getDayInterval(now,
									noGoingTaskInfo.receivedTime,
									noGoingTaskInfo.taskCycle)) + "天";
				} else {
					date = "剩余完成时间"
							+ Math.abs(DateUtils.getHoursInterval(now,
									noGoingTaskInfo.receivedTime,
									noGoingTaskInfo.taskCycle)) + "小时";
				}
			} else {
				date = "剩余完成时间"
						+ Math.abs(DateUtils.getMinutesInterval(now,
								noGoingTaskInfo.receivedTime,
								noGoingTaskInfo.taskCycle)) + "分";
			}
		} else {
			if (DateUtils.getSecondInterval(now, noGoingTaskInfo.receivedTime,
					noGoingTaskInfo.taskCycle) >= 0) {
				date = "已结束";
			} else {
				date = "剩余完成时间"
						+ Math.abs(DateUtils.getSecondInterval(now,
								noGoingTaskInfo.receivedTime,
								noGoingTaskInfo.taskCycle)) + "秒";
			}
		}
		viewholder.tv_zhifufangshi.setText(date);
		viewholder.tv_Amount.setText(noGoingTaskInfo.getAmount());
		viewholder.tv_AvailableCount.setText("领取时间  "
				+ DateUtils.getTime(noGoingTaskInfo.receivedTime));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						TaskDetailInfoActivity.class);
				intent.putExtra("TaskId", noGoingTaskInfo.taskId);
				intent.putExtra("OrderId", noGoingTaskInfo.myReceivedTaskId);
				intent.putExtra("isList", 5);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	public class ViewHolder {
		public ImageView icon_pusher;
		public TextView tv_Pusher;
		public TextView tv_TaskGeneralInfo;
		public TextView tv_zhifufangshi;
		public TextView tv_Amount;
		public TextView tv_AvailableCount;

		public ViewHolder(View view) {
			icon_pusher = (ImageView) view.findViewById(R.id.icon_pusher);
			tv_Pusher = (TextView) view.findViewById(R.id.tv_pusher_taskName);
			tv_TaskGeneralInfo = (TextView) view
					.findViewById(R.id.tv_taskGeneralInfo);
			tv_zhifufangshi = (TextView) view
					.findViewById(R.id.tv_zhifufangshi);
			tv_Amount = (TextView) view.findViewById(R.id.tv_Amount);
			tv_AvailableCount = (TextView) view
					.findViewById(R.id.tv_AvailableCount);
		}
	}
}
