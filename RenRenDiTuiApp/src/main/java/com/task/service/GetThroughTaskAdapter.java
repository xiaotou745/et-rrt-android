package com.task.service;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.tools.DateUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ImageLoadManager;
import com.task.activity.TaskDetailInfoActivity;

/**
 * 审核通过任务适配器
 * 
 * @author llp
 * 
 */
public class GetThroughTaskAdapter extends BaseAdapter {
	private Context context;
	private List<FinishedTaskInfo> finishedTaskInfos;

	public GetThroughTaskAdapter(Context context,
			List<FinishedTaskInfo> finishedTaskInfos) {
		this.context = context;
		this.finishedTaskInfos = finishedTaskInfos;
	}

	@Override
	public int getCount() {
		return finishedTaskInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return finishedTaskInfos.get(position);
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
					R.layout.item_task_through, null);
			viewholder = new ViewHolder(convertView);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		final FinishedTaskInfo finishedTaskInfo = finishedTaskInfos
				.get(position);
		if (Util.IsNotNUll(finishedTaskInfo.logo)) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(
					finishedTaskInfo.logo, viewholder.icon_pusher,
					R.drawable.pusher_logo);
		} else {
			viewholder.icon_pusher.setImageResource(R.drawable.pusher_logo);
		}
		String title = finishedTaskInfo.taskName;
		if(title.length()>10)
			title = title.substring(0,10)+"...";
		viewholder.tv_Pusher.setText(title);
		viewholder.tv_TaskGeneralInfo.setText(finishedTaskInfo.taskGeneralInfo);
		viewholder.tv_surolus_time.setText("审核时间   "
				+ DateUtils.getTime(finishedTaskInfo.auditTime));
		viewholder.tv_Amount.setText(finishedTaskInfo.getAmount());

		if (finishedTaskInfo.isAgainPickUp == 1) {
			viewholder.btn_collect_again.setVisibility(View.VISIBLE);
			viewholder.btn_collect_again
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(context,
									TaskDetailInfoActivity.class);
							intent.putExtra("TaskId", finishedTaskInfo.taskId);
							context.startActivity(intent);
						}
					});
		} else {
			viewholder.btn_collect_again.setVisibility(View.GONE);
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						TaskDetailInfoActivity.class);
				intent.putExtra("TaskId", finishedTaskInfo.taskId);
				intent.putExtra("OrderId", finishedTaskInfo.myReceivedTaskId);
				intent.putExtra("isList", 1);
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
		public Button btn_collect_again;

		public ViewHolder(View view) {
			icon_pusher = (ImageView) view.findViewById(R.id.icon_pusher);
			tv_Pusher = (TextView) view.findViewById(R.id.tv_pusher_taskName);
			tv_TaskGeneralInfo = (TextView) view
					.findViewById(R.id.tv_taskGeneralInfo);
			tv_surolus_time = (TextView) view
					.findViewById(R.id.tv_surolus_time);
			tv_Amount = (TextView) view.findViewById(R.id.tv_Amount);
			btn_collect_again = (Button) view
					.findViewById(R.id.btn_collect_again);
		}
	}
}
