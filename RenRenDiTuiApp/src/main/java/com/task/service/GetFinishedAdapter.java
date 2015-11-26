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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGiveupTask;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.FinishedTaskInfo;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.tools.DateUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.activity.FragmentFinishedTask;
import com.task.activity.SubmitDataActivity;
import com.task.activity.TaskDetailInfoActivity;

/**
 * 未通过任务适配器
 * 
 * @author llp
 * 
 */
public class GetFinishedAdapter extends BaseAdapter {
	private Context context;
	private List<FinishedTaskInfo> finishedTaskInfos;

	public GetFinishedAdapter(Context context,
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
					R.layout.item_task_finished, null);
			viewholder = new ViewHolder(convertView);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		final View view = convertView;
		
		final RQHandler<RSBase> rqHandler_giveupTask = new RQHandler<>(
				new IRqHandlerMsg<RSBase>() {

					@Override
					public void onBefore() {
					}

					@Override
					public void onNetworknotvalide() {
						ToastUtil.show(
								context,
								context.getResources().getString(
										R.string.networknotconnect));
					}

					@Override
					public void onSuccess(RSBase t) {
						ToastUtil.show(context, "放弃任务成功");
						view.setVisibility(View.GONE);
					}

					@Override
					public void onSericeErr(RSBase t) {
						ToastUtil.show(context, t.msg);
					}

					@Override
					public void onSericeExp() {

					}
				});
		
		final FinishedTaskInfo finishedTaskInfo = finishedTaskInfos
				.get(position);
		if (Util.IsNotNUll(finishedTaskInfo.logo)) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(finishedTaskInfo.logo, viewholder.icon_pusher,
					R.drawable.pusher_logo);
		} else {
			viewholder.icon_pusher.setImageResource(R.drawable.pusher_logo);
		}
		String title = finishedTaskInfo.taskName;
		if(title.length()>10)
			title = title.substring(0,10)+"...";
		viewholder.tv_Pusher.setText(title);
		viewholder.tv_TaskGeneralInfo.setText(finishedTaskInfo.taskGeneralInfo);
		String now = DateUtils.ft.format(new Date());
		viewholder.tv_zhifufangshi.setText(DateUtils
				.getTime(finishedTaskInfo.finishTime));
		viewholder.tv_Amount.setText(finishedTaskInfo.getAmount());
		viewholder.tv_AvailableCount.setText(DateUtils
				.getTime(finishedTaskInfo.auditTime));

		viewholder.btn_giveup_task.setOnClickListener(new OnClickListener() {// 放弃任务

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						ApiUtil.Request(new RQBaseModel<RQGiveupTask, RSBase>(
								context, new RQGiveupTask(Utils
										.getUserDTO(context).data.userId,
										finishedTaskInfo.myReceivedTaskId, ""),
								new RSBase(), ApiNames.放弃任务.getValue(),
								RequestType.POST, rqHandler_giveupTask));
					}
				});

		viewholder.btn_submit_again_task
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context,
								SubmitDataActivity.class);
						intent.putExtra("auditStatus",
								finishedTaskInfo.auditStatus);
						intent.putExtra("taskId", finishedTaskInfo.taskId);
						intent.putExtra("myReceivedTaskId",
								finishedTaskInfo.myReceivedTaskId);
						context.startActivity(intent);
					}
				});

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						TaskDetailInfoActivity.class);
				intent.putExtra("TaskId", finishedTaskInfo.taskId);
				intent.putExtra("OrderId", finishedTaskInfo.myReceivedTaskId);
				intent.putExtra("isList", 4);
				context.startActivity(intent);
			}
		});
		return view;
	}

	public class ViewHolder {
		public ImageView icon_pusher;
		public TextView tv_Pusher;
		public TextView tv_TaskGeneralInfo;
		public TextView tv_zhifufangshi;
		public TextView tv_Amount;
		public TextView tv_AvailableCount;
		public Button btn_giveup_task;
		public Button btn_submit_again_task;

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
			btn_giveup_task = (Button) view.findViewById(R.id.btn_giveup_task);
			btn_submit_again_task = (Button) view
					.findViewById(R.id.btn_submit_again_task);
		}
	}
}
