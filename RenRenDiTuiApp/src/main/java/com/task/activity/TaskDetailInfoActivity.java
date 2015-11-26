package com.task.activity;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGetTaskDetailInfo;
import com.renrentui.requestmodel.RQGiveupTask;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQReceiveTask;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.resultmodel.RSGetTaskDetailInfo;
import com.renrentui.resultmodel.RSReceiveTask;
import com.renrentui.resultmodel.TaskDetailInfo;
import com.renrentui.tools.DateUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.service.SuccessDialog;
import com.task.service.SuccessDialog.ExitDialogListener;
import com.user.activity.LoginActivity;

/**
 * 任务详细信息界面
 * 
 * @author llp
 * 
 */
public class TaskDetailInfoActivity extends BaseActivity implements
		OnClickListener, INodata {

	private Context context;
	private ImageView icon_pusher;// 任务发布商logo
	private TextView tv_pusher_taskName;// 任务名称
	private TextView tv_taskGeneralInfo;// 任务公告
	private TextView tv_Amount;// 任务单价
	private TextView tv_deadline;// 截止日期
	private TextView tv_finish_time;// 完成时间
	private TextView tv_AvailableCount;// 任务剩余数量
	private TextView tv_audit_time;// 审核时间
	private LinearLayout ll_task_steps;
	private TextView tv_company_profile;// 发布商简单介绍

	private TextView tv_states;// 任务状态
	private LinearLayout ll_time;// 时间Layout
	private TextView tv_time;// 什么时间
	private TextView tv_time_show;// 具体日期

	private LinearLayout ll_task_description;
	private TextView tv_task_description;// 任务描述

	private LinearLayout ll_matters_need_attention;
	private TextView tv_matters_need_attention;// 注意事项

	private Button btn_receive_task;// 领取任务按钮
	private LinearLayout ll_receive_task;
	private Button btn_giveup_task;// 放弃任务
	private Button btn_submit_data;// 提交资料

	private LinearLayout ll_through_task;// 通过任务查看按钮
	private Button btn_look_shenhe;// 查看审核
	private Button btn_receive_task_again;// 再次领取

	private LinearLayout ll_giveup_task;
	private RSGetTaskDetailInfo rsGetTaskDetailInfo;
	private String orderId = "0";
	private int isList = 0;

	private RQHandler<RSGetTaskDetailInfo> rqHandler_getTaskDetailInfo = new RQHandler<>(
			new IRqHandlerMsg<RSGetTaskDetailInfo>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
				}

				@Override
				public void onNetworknotvalide() {
					TaskDetailInfoActivity.this.onNodata(
							ResultMsgType.NetworkNotValide, null, null, null);
				}

				@Override
				public void onSuccess(RSGetTaskDetailInfo t) {
					rsGetTaskDetailInfo = t;
					orderId = t.data.orderId;
					initData(rsGetTaskDetailInfo.data);
					hideProgressDialog();
				}

				@Override
				public void onSericeErr(RSGetTaskDetailInfo t) {
					TaskDetailInfoActivity.this.onNodata(
							ResultMsgType.ServiceErr, "刷新", "数据加载失败！",
							TaskDetailInfoActivity.this);
				}

				@Override
				public void onSericeExp() {
					TaskDetailInfoActivity.this.onNodata(
							ResultMsgType.ServiceExp, "刷新", "数据加载失败！",
							TaskDetailInfoActivity.this);
				}
			});

	private RQHandler<RSReceiveTask> rqHandler_receiveTask = new RQHandler<>(
			new IRqHandlerMsg<RSReceiveTask>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
				}

				@Override
				public void onNetworknotvalide() {
					hideProgressDialog();
					ToastUtil.show(
							context,
							context.getResources().getString(
									R.string.networknotconnect));
				}

				@Override
				public void onSuccess(RSReceiveTask t) {
					orderId = t.data;
					hideProgressDialog();
					SuccessDialog dialog = new SuccessDialog(context, "请在"
							+ rsGetTaskDetailInfo.data.taskCycle
							+ "小时内完成\n提示：完成之后不要忘记提交审核哦");
					dialog.addListener(new ExitDialogListener() {

						@Override
						public void clickCommit() {
							ll_receive_task.setVisibility(View.GONE);
							ll_through_task.setVisibility(View.GONE);
							ll_giveup_task.setVisibility(View.VISIBLE);
							tv_states.setText("已领取");
							isList = 5;
							getInitData();
						}

						@Override
						public void clickCancel() {
							Intent intent = new Intent(context,
									MyTaskMainActivity.class);
							context.startActivity(intent);
							finish();
						}
					});
					dialog.show();
					dialog.setCancelable(false);
				}

				@Override
				public void onSericeErr(RSReceiveTask t) {
					hideProgressDialog();
					ToastUtil.show(context, t.msg);
				}

				@Override
				public void onSericeExp() {
					hideProgressDialog();
				}
			});

	private RQHandler<RSBase> rqHandler_giveupTask = new RQHandler<>(
			new IRqHandlerMsg<RSBase>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
				}

				@Override
				public void onNetworknotvalide() {
					hideProgressDialog();
					ToastUtil.show(
							context,
							context.getResources().getString(
									R.string.networknotconnect));
				}

				@Override
				public void onSuccess(RSBase t) {
					hideProgressDialog();
					tv_time.setText("");
					tv_time_show.setText("");
					ToastUtil.show(context, "放弃任务成功");
					ll_receive_task.setVisibility(View.VISIBLE);
					ll_giveup_task.setVisibility(View.GONE);
					ll_through_task.setVisibility(View.GONE);
					tv_states.setText("待领取");
					isList = 0;
					hideProgressDialog();
					getInitData();
				}

				@Override
				public void onSericeErr(RSBase t) {
					hideProgressDialog();
					ToastUtil.show(context, t.msg);
				}

				@Override
				public void onSericeExp() {
					hideProgressDialog();
				}
			});

	private String userId;// 用户id
	private String taskId;// 任务id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);
		super.init();
		initControl();
		Intent intent = getIntent();
		taskId = intent.getStringExtra("TaskId");
		orderId = intent.getStringExtra("OrderId");
		if (orderId == null || orderId.equals(""))
			orderId = "0";
		isList = intent.getIntExtra("isList", 0);

		if (isList == 0) {// 待领取
			ll_through_task.setVisibility(View.GONE);
			ll_giveup_task.setVisibility(View.GONE);
			ll_receive_task.setVisibility(View.VISIBLE);
			tv_states.setText("待领取");
		} else if (isList == 1) {// 已通过
			ll_through_task.setVisibility(View.VISIBLE);
			ll_giveup_task.setVisibility(View.GONE);
			ll_receive_task.setVisibility(View.GONE);
			tv_states.setText("已通过");
		} else if (isList == 2) {// 已失效
			ll_through_task.setVisibility(View.GONE);
			ll_giveup_task.setVisibility(View.GONE);
			ll_receive_task.setVisibility(View.GONE);
			tv_states.setText("已失效");
		} else if (isList == 3) {// 已取消
			ll_through_task.setVisibility(View.GONE);
			ll_giveup_task.setVisibility(View.GONE);
			ll_receive_task.setVisibility(View.GONE);
			tv_states.setText("已取消");
		} else if (isList == 4) {// 审核失败
			ll_through_task.setVisibility(View.GONE);
			ll_giveup_task.setVisibility(View.VISIBLE);
			ll_receive_task.setVisibility(View.GONE);
			btn_submit_data.setText("重新提交");
			tv_states.setText("未通过");
		} else if (isList == 5) {// 已领取
			ll_through_task.setVisibility(View.GONE);
			ll_giveup_task.setVisibility(View.VISIBLE);
			ll_receive_task.setVisibility(View.GONE);
			tv_states.setText("已领取");
		} else if (isList == 6) {// 审核中
			ll_through_task.setVisibility(View.VISIBLE);
			ll_giveup_task.setVisibility(View.GONE);
			ll_receive_task.setVisibility(View.GONE);
			tv_states.setText("审核中");
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (Utils.getUserDTO(context) != null)
			userId = Utils.getUserDTO(context).data.userId;
		else
			userId = "0";
		getInitData();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getInitData();
	}

	/**
	 * 获取数据
	 */
	private void getInitData() {
		showProgressDialog();
		ApiUtil.Request(new RQBaseModel<RQGetTaskDetailInfo, RSGetTaskDetailInfo>(
				context, new RQGetTaskDetailInfo(userId, taskId, orderId),
				new RSGetTaskDetailInfo(), ApiNames.获取任务详细信息.getValue(),
				RequestType.POST, rqHandler_getTaskDetailInfo));
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		context = this;
		icon_pusher = (ImageView) findViewById(R.id.icon_pusher);
		tv_pusher_taskName = (TextView) findViewById(R.id.tv_pusher_taskName);
		tv_taskGeneralInfo = (TextView) findViewById(R.id.tv_taskGeneralInfo);
		tv_Amount = (TextView) findViewById(R.id.tv_Amount);
		tv_deadline = (TextView) findViewById(R.id.tv_deadline);
		tv_finish_time = (TextView) findViewById(R.id.tv_finish_time);
		tv_AvailableCount = (TextView) findViewById(R.id.tv_AvailableCount);
		tv_audit_time = (TextView) findViewById(R.id.tv_audit_time);
		ll_task_steps = (LinearLayout) findViewById(R.id.ll_task_steps);
		tv_company_profile = (TextView) findViewById(R.id.tv_company_profile);
		ll_task_description = (LinearLayout) findViewById(R.id.ll_task_description);
		tv_task_description = (TextView) findViewById(R.id.tv_task_description);
		ll_matters_need_attention = (LinearLayout) findViewById(R.id.ll_matters_need_attention);
		tv_matters_need_attention = (TextView) findViewById(R.id.tv_matters_need_attention);

		btn_receive_task = (Button) findViewById(R.id.btn_receive_task);
		btn_receive_task.setOnClickListener(this);
		ll_receive_task = (LinearLayout) findViewById(R.id.ll_receive_task);
		btn_giveup_task = (Button) findViewById(R.id.btn_giveup_task);
		btn_giveup_task.setOnClickListener(this);
		btn_submit_data = (Button) findViewById(R.id.btn_submit_data);
		btn_submit_data.setOnClickListener(this);
		ll_giveup_task = (LinearLayout) findViewById(R.id.ll_giveup_task);

		ll_through_task = (LinearLayout) findViewById(R.id.ll_through_task);
		btn_look_shenhe = (Button) findViewById(R.id.btn_look_shenhe);
		btn_look_shenhe.setOnClickListener(this);
		btn_receive_task_again = (Button) findViewById(R.id.btn_receive_task_again);
		btn_receive_task_again.setOnClickListener(this);

		tv_states = (TextView) findViewById(R.id.tv_states);
		ll_time = (LinearLayout) findViewById(R.id.ll_time);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_time_show = (TextView) findViewById(R.id.tv_time_show);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_look_shenhe:// 查看审核
			intent = new Intent(context, SubmitDataActivity.class);
			intent.putExtra("auditStatus", 2);
			intent.putExtra("taskId", rsGetTaskDetailInfo.data.id);
			intent.putExtra("myReceivedTaskId",
					rsGetTaskDetailInfo.data.orderId);
			context.startActivity(intent);
			break;
		case R.id.btn_submit_data:// 提交资料
			intent = new Intent(context, SubmitDataActivity.class);
			intent.putExtra("taskId", taskId);
			intent.putExtra("myReceivedTaskId", orderId);
			context.startActivity(intent);
			break;
		case R.id.btn_giveup_task:// 放弃任务
			showProgressDialog();
			ApiUtil.Request(new RQBaseModel<RQGiveupTask, RSBase>(context,
					new RQGiveupTask(Utils.getUserDTO(context).data.userId,
							orderId, ""), new RSBase(), ApiNames.放弃任务
							.getValue(), RequestType.POST, rqHandler_giveupTask));
			break;
		case R.id.btn_receive_task_again:// 再次领取
		case R.id.btn_receive_task:// 点击领取任务时
			if (userId.equals("0")) {
				intent = new Intent(context, LoginActivity.class);
				context.startActivity(intent);
				break;
			}
			showProgressDialog();
			ApiUtil.Request(new RQBaseModel<RQReceiveTask, RSReceiveTask>(
					context, new RQReceiveTask(
							Utils.getUserDTO(context).data.userId, taskId),
					new RSReceiveTask(), ApiNames.领取任务.getValue(),
					RequestType.POST, rqHandler_receiveTask));
			break;
		default:
			break;
		}
	}

	/**
	 * 初始化页面数据
	 */
	@SuppressWarnings("deprecation")
	private void initData(TaskDetailInfo tdi) {
		orderId = tdi.orderId;
		if (Util.IsNotNUll(tdi.logo)) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(tdi.logo,
					icon_pusher, R.drawable.pusher_logo);
		} else {
			icon_pusher.setImageResource(R.drawable.pusher_logo);
		}
		String title = tdi.taskTitle;
		if (title.length() > 10)
			title = title.substring(0, 10) + "...";
		tv_pusher_taskName.setText(title);
		tv_taskGeneralInfo.setText(tdi.taskNotice);
		if (isList == 5) {
			tv_time.setText("领取时间");
			tv_time_show.setText(DateUtils.getTime(tdi.receivedTime));
		}
		if (isList == 0 || isList == 5) {
			tv_finish_time.setText("结束时间 " + DateUtils.getTime(tdi.endTime));
		} else if (isList == 4) {
			tv_finish_time.setText("提交时间 " + DateUtils.getTime(tdi.finishTime));
		} else if (isList == 6 || isList == 1 || isList == 2 || isList == 3) {
			tv_finish_time.setVisibility(View.GONE);
		}
		tv_Amount.setText(tdi.getAmount());
		tv_audit_time.setText("审核 " + tdi.auditCycle + "天");
		tv_AvailableCount.setText("可领 " + tdi.availableCount + "次");
		tv_deadline.setText("完成时间 " + tdi.taskCycle + "小时");
		if (isList == 0) {
			tv_deadline.setText("完成时间 " + tdi.taskCycle + "小时");
		} else if (isList == 5) {
			String now = DateUtils.ft.format(new Date());
			String date = "";
			if (Util.IsNotNUll(tdi.receivedTime)) {
				if (Math.abs(DateUtils.getSecondInterval(now, tdi.receivedTime,
						tdi.taskCycle)) > 60
						&& DateUtils.getSecondInterval(now, tdi.receivedTime,
								tdi.taskCycle) < 0) {
					if (Math.abs(DateUtils.getMinutesInterval(now,
							tdi.receivedTime, tdi.taskCycle)) > 60
							&& DateUtils.getMinutesInterval(now,
									tdi.receivedTime, tdi.taskCycle) < 0) {
						if (Math.abs(DateUtils.getHoursInterval(now,
								tdi.receivedTime, tdi.taskCycle)) > 60
								&& DateUtils.getHoursInterval(now,
										tdi.receivedTime, tdi.taskCycle) < 0) {
							date = "剩余完成时间"
									+ Math.abs(DateUtils.getDayInterval(now,
											tdi.receivedTime, tdi.taskCycle))
									+ "天";
						} else {
							date = "剩余完成时间"
									+ Math.abs(DateUtils.getHoursInterval(now,
											tdi.receivedTime, tdi.taskCycle))
									+ "小时";
						}
					} else {
						date = "剩余完成时间"
								+ Math.abs(DateUtils.getMinutesInterval(now,
										tdi.receivedTime, tdi.taskCycle)) + "分";
					}
				} else {
					if (DateUtils.getSecondInterval(now, tdi.receivedTime,
							tdi.taskCycle) <= 0) {
						date = "已结束";
					} else {
						date = "剩余完成时间"
								+ Math.abs(DateUtils.getSecondInterval(now,
										tdi.receivedTime, tdi.taskCycle)) + "秒";
					}
				}
			}
			tv_deadline.setText(date);
		} else if (isList == 6 || isList == 4 || isList == 1 || isList == 2
				|| isList == 3) {
			String now = DateUtils.ft.format(new Date());
			String date = "";
			if (DateUtils.getSecondInterval(tdi.endTime, now) > 60) {
				if (DateUtils.getMinutesInterval(tdi.endTime, now) > 60) {
					if (DateUtils.getHoursInterval(tdi.endTime, now) > 60) {
						date = "领取截止时间"
								+ DateUtils.getDayInterval(tdi.endTime, now)
								+ "天";
					} else {
						date = "领取截止时间"
								+ DateUtils.getHoursInterval(tdi.endTime, now)
								+ "小时";
					}
				} else {
					date = "领取截止时间"
							+ DateUtils.getMinutesInterval(tdi.endTime, now)
							+ "分";
				}
			} else {
				if (DateUtils.getSecondInterval(tdi.endTime, now) <= 0) {
					date = "已结束";
				} else {
					date = "领取截止时间"
							+ DateUtils.getSecondInterval(tdi.endTime, now)
							+ "秒";
				}
			}
			tv_deadline.setText(date);
		}
		tv_company_profile.setText(tdi.companySummary);
		if (tdi.taskNote != null && !tdi.taskNote.equals("")) {
			tv_matters_need_attention.setText(tdi.taskNote);
			ll_matters_need_attention.setVisibility(View.VISIBLE);
		} else {
			ll_matters_need_attention.setVisibility(View.GONE);
		}
		if (tdi.taskGeneralInfo != null && !tdi.taskGeneralInfo.equals("")) {
			tv_task_description.setText(tdi.taskGeneralInfo);
			ll_task_description.setVisibility(View.VISIBLE);
		} else {
			ll_task_description.setVisibility(View.GONE);
		}
		if (tdi.isAgainPickUp == 0) {
			btn_receive_task_again.setOnClickListener(null);
			btn_receive_task_again
					.setBackgroundColor(R.drawable.btn_grey_wane_bg);
		}
	}

	@Override
	public void onNoData() {
		getInitData();
	}

}
