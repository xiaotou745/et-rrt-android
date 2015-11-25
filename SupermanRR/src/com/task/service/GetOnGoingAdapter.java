package com.task.service;

import java.util.Date;
import java.util.List;

import android.app.Activity;
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
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQReceiveTask;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.FinishedTaskInfo;
import com.renrentui.resultmodel.RSReceiveTask;
import com.renrentui.tools.DateUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.activity.MyTaskMainActivity;
import com.task.activity.TaskDetailInfoActivity;
import com.task.service.SuccessDialog.ExitDialogListener;

/**
 * 审核中任务的适配器
 * 
 * @author llp
 * 
 */
public class GetOnGoingAdapter extends BaseAdapter {
	private Context context;
	private List<FinishedTaskInfo> noGoingTaskInfos;

	public GetOnGoingAdapter(Context context,
			List<FinishedTaskInfo> noGoingTaskInfos) {
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
					R.layout.item_task_ongoing, null);
			viewholder = new ViewHolder(convertView);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		final FinishedTaskInfo noGoingTaskInfo = noGoingTaskInfos.get(position);
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
		viewholder.tv_Amount.setText(noGoingTaskInfo.getAmount());
		viewholder.tv_AvailableCount.setText("领取时间  "
				+ DateUtils.getTime(noGoingTaskInfo.receivedTime));

		if (noGoingTaskInfo.isAgainPickUp == 1) {
			viewholder.btn_collect_again.setVisibility(View.VISIBLE);
			final RQHandler<RSReceiveTask> rqHandler_receiveTask = new RQHandler<>(
					new IRqHandlerMsg<RSReceiveTask>() {

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
						public void onSuccess(RSReceiveTask t) {
							final String orderId = t.data;
							SuccessDialog dialog = new SuccessDialog(context, "请在"
									+ noGoingTaskInfo.taskCycle
									+ "小时内完成\n提示：完成之后不要忘记提交审核哦");
							dialog.addListener(new ExitDialogListener() {

								@Override
								public void clickCommit() {
									Intent intent = new Intent(context,
											TaskDetailInfoActivity.class);
									intent.putExtra("TaskId", noGoingTaskInfo.taskId);
									intent.putExtra("OrderId",orderId);
									intent.putExtra("isList", 5);
									context.startActivity(intent);
								}

								@Override
								public void clickCancel() {
									((MyTaskMainActivity) context).vp_task_main.setCurrentItem(ToMainPage.已领取.getValue());
								}
							});
							dialog.show();
							dialog.setCancelable(false);
						}

						@Override
						public void onSericeErr(RSReceiveTask t) {
							ToastUtil.show(context, t.msg);
						}

						@Override
						public void onSericeExp() {

						}
					});
			
			viewholder.btn_collect_again
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							ApiUtil.Request(new RQBaseModel<RQReceiveTask, RSReceiveTask>(
									context, new RQReceiveTask(
											Utils.getUserDTO(context).data.userId, noGoingTaskInfo.taskId),
									new RSReceiveTask(), ApiNames.领取任务.getValue(),
									RequestType.POST, rqHandler_receiveTask));
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
				intent.putExtra("TaskId", noGoingTaskInfo.taskId);
				intent.putExtra("OrderId", noGoingTaskInfo.myReceivedTaskId);
				intent.putExtra("isList", 6);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	public class ViewHolder {
		public ImageView icon_pusher;
		public TextView tv_Pusher;
		public TextView tv_TaskGeneralInfo;
		public TextView tv_Amount;
		public TextView tv_AvailableCount;
		public Button btn_collect_again;

		public ViewHolder(View view) {
			icon_pusher = (ImageView) view.findViewById(R.id.icon_pusher);
			tv_Pusher = (TextView) view.findViewById(R.id.tv_pusher_taskName);
			tv_TaskGeneralInfo = (TextView) view
					.findViewById(R.id.tv_taskGeneralInfo);
			tv_Amount = (TextView) view.findViewById(R.id.tv_Amount);
			tv_AvailableCount = (TextView) view
					.findViewById(R.id.tv_AvailableCount);
			btn_collect_again = (Button) view
					.findViewById(R.id.btn_collect_again);
		}
	}
}
