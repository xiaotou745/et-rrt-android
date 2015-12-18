package com.task.service;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.TaskMetarialContent;
import com.renrentui.util.TimeUtils;
import com.renrentui.util.UIHelper;
import com.task.activity.MyTaskMaterialDetailActivity;

import java.util.List;

/**
 * 审核通过资料适配器
 * 
 * @author llp
 * 
 */
public class GetOnGoneAdapter extends BaseAdapter {
	private Context context;
	private List<TaskMetarialContent> noGoingTaskInfos;

	public GetOnGoneAdapter(Context context,
							List<TaskMetarialContent> noGoingTaskInfos) {
		this.context = context;
		this.noGoingTaskInfos = noGoingTaskInfos;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public int getItemViewType(int type) {
		if (type == 1) {
			//文本
			type=0;
		} else if (type == 2) {
			//图片
			type=1;
		} else if(type==3) {
			//图片组
			type=2;
		}else{
			type = 0;
		}
		return type;
	}

	@Override
	public int getCount() {
		if(noGoingTaskInfos==null){
			return 0;
		}else {
			return noGoingTaskInfos.size();
		}
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
	public View getView(int position, View convertView, ViewGroup parent) {

		 ViewHolder_1 viewholder_1=null;
		ViewHolder_2 viewHolder_2 = null;
		ViewHolder_3 viewHolder_3 = null;
		final TaskMetarialContent beanContent = (TaskMetarialContent)getItem(position);
		int iType = getItemViewType(beanContent.groupType);
		switch (iType){
			case 0:
				//文本
				if(convertView==null || convertView.getTag(R.id.listview_multiple_type_first_layout)==null){
					viewholder_1= new ViewHolder_1();
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_ongoing_1,parent,false);
					viewholder_1.tv_push_time = (TextView)convertView.findViewById(R.id.tv_pusher_time);
					viewholder_1.tv_push_content = (TextView)convertView.findViewById(R.id.tv_pusher_taskcontent);
					viewholder_1.tv_task_status = (TextView)convertView.findViewById(R.id.tv_task_status);
					viewholder_1.tv_task_name = (TextView)convertView.findViewById(R.id.tv_task_name);
					viewholder_1.ll_amount = (LinearLayout)convertView.findViewById(R.id.ll_amount);
					viewholder_1.tv_task_amount = (TextView)convertView.findViewById(R.id.tv_task_amount);
					convertView.setTag(R.id.listview_multiple_type_first_layout,viewholder_1);
				}else{
					viewholder_1 = (ViewHolder_1)convertView.getTag(R.id.listview_multiple_type_first_layout);
				}
				break;
			case 1:
				//图片
				if(convertView==null || convertView.getTag(R.id.listview_multiple_type_second_layout)==null){
					viewHolder_2 = new ViewHolder_2();
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_ongoing_2,parent,false);
					viewHolder_2.tv_push_time = (TextView)convertView.findViewById(R.id.tv_pusher_time);
					viewHolder_2.gridView_task_pic = (GridView)convertView.findViewById(R.id.gridview_task_pic);
					viewHolder_2.tv_task_status = (TextView)convertView.findViewById(R.id.tv_task_status);
					viewHolder_2.tv_task_name = (TextView)convertView.findViewById(R.id.tv_task_name);
					viewHolder_2.ll_amount = (LinearLayout)convertView.findViewById(R.id.ll_amount);
					viewHolder_2.tv_task_amount = (TextView)convertView.findViewById(R.id.tv_task_amount);
					convertView.setTag(R.id.listview_multiple_type_second_layout,viewHolder_2);
				}else{
					viewHolder_2 = (ViewHolder_2)convertView.getTag(R.id.listview_multiple_type_second_layout);
				}
				break;
			case 2:
				//多组图片
				if(convertView==null || convertView.getTag(R.id.listview_multiple_type_three_layout)==null){
					viewHolder_3 = new ViewHolder_3();
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_ongoing_2,parent,false);
					viewHolder_3.tv_push_time = (TextView)convertView.findViewById(R.id.tv_pusher_time);
					viewHolder_3.gridView_task_pic = (GridView)convertView.findViewById(R.id.gridview_task_pic);
					viewHolder_3.tv_task_status = (TextView)convertView.findViewById(R.id.tv_task_status);
					viewHolder_3.tv_task_name = (TextView)convertView.findViewById(R.id.tv_task_name);
					viewHolder_3.ll_amount = (LinearLayout)convertView.findViewById(R.id.ll_amount);
					viewHolder_3.tv_task_amount = (TextView)convertView.findViewById(R.id.tv_task_amount);
					convertView.setTag(R.id.listview_multiple_type_three_layout,viewHolder_3);
				}else{
					viewHolder_3 = (ViewHolder_3)convertView.getTag(R.id.listview_multiple_type_three_layout);
				}
				break;
			default:
				if(convertView==null || convertView.getTag(R.id.listview_multiple_type_first_layout)==null){
					viewholder_1= new ViewHolder_1();
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_ongoing_1,parent,false);
					viewholder_1.tv_push_time = (TextView)convertView.findViewById(R.id.tv_pusher_time);
					viewholder_1.tv_push_content = (TextView)convertView.findViewById(R.id.tv_pusher_taskcontent);
					viewholder_1.tv_task_status = (TextView)convertView.findViewById(R.id.tv_task_status);
					viewholder_1.tv_task_name = (TextView)convertView.findViewById(R.id.tv_task_name);
					viewholder_1.ll_amount = (LinearLayout)convertView.findViewById(R.id.ll_amount);
					viewholder_1.tv_task_amount = (TextView)convertView.findViewById(R.id.tv_task_amount);
					convertView.setTag(R.id.listview_multiple_type_first_layout,viewholder_1);
				}else{
					viewholder_1 = (ViewHolder_1)convertView.getTag(R.id.listview_multiple_type_first_layout);
				}
				break;
		}
		SpannableStringBuilder style = null;
		switch (beanContent.taskType){
			case 1:
				//签约
				style =  UIHelper.setStyleColorByColor(context, beanContent.taskTypeName.toString(), beanContent.taskName.toString(), R.color.white, R.color.tv_bg_color_1);
				break;
			case 2:
				//分享
				style =  UIHelper.setStyleColorByColor(context,beanContent.taskTypeName.toString(),beanContent.taskName.toString(),R.color.white,R.color.tv_bg_color_3);
				break;
			case 3:
				//下载
				style =  UIHelper.setStyleColorByColor(context,beanContent.taskTypeName.toString(),beanContent.taskName.toString(),R.color.white,R.color.tv_bg_color_2);
				break;
			default:
				style =  UIHelper.setStyleColorByColor(context,beanContent.taskTypeName.toString(),beanContent.taskName.toString(),R.color.white,R.color.tv_bg_color_1);
				break;
		}
		switch (iType){
			case 0:
				viewholder_1.tv_push_time.setText("提交时间  "+ TimeUtils.StringPattern(beanContent.createDate,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));
				if(beanContent.titlesList!=null && beanContent.titlesList.size()>0){
					viewholder_1.tv_push_content.setText(beanContent.titlesList.get(0));
				}else{
					viewholder_1.tv_push_content.setText("");
				}

				viewholder_1.tv_task_status.setText(beanContent.taskStatusName);
				viewholder_1.tv_task_status.setTextColor(context.getResources().getColor(R.color.tv_order_color_5));
				viewholder_1.tv_task_amount.setText(String.valueOf(beanContent.getAmount()));
				viewholder_1.tv_task_name.setText(style);
				break;
			case 1:
				viewHolder_2.tv_push_time.setText("提交时间  "+ TimeUtils.StringPattern(beanContent.createDate,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));
				viewHolder_2.tv_task_status.setText(beanContent.taskStatusName);
				viewHolder_2.tv_task_status.setTextColor(context.getResources().getColor(R.color.tv_order_color_5));
				viewHolder_2.tv_task_amount.setText(String.valueOf(beanContent.getAmount()));
				viewHolder_2.tv_task_name.setText(style);
				viewHolder_2.gridView_task_pic.setAdapter(new GriveiwTaskPicAdapter(context,beanContent.titlesList));
				break;
			case 2:
				viewHolder_3.tv_push_time.setText("提交时间  "+ TimeUtils.StringPattern(beanContent.createDate,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));
				viewHolder_3.tv_task_status.setText(beanContent.taskStatusName);
				viewHolder_3.tv_task_status.setTextColor(context.getResources().getColor(R.color.tv_order_color_5));
				viewHolder_3.tv_task_amount.setText(String.valueOf(beanContent.getAmount()));
				viewHolder_3.tv_task_name.setText(style);
				viewHolder_3.gridView_task_pic.setAdapter(new GriveiwTaskPicAdapter(context,beanContent.titlesList));
				break;
		}
		convertView.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				//审核资料详情
				Intent mIntent = new Intent();
				mIntent.setClass(context,MyTaskMaterialDetailActivity.class);
				mIntent.putExtra("TaskMaterialId", beanContent.taskDatumId);
				mIntent.putExtra("TaskId",beanContent.taskId);
				mIntent.putExtra("Status",beanContent.auditStatus);
				mIntent.putExtra("Title_content",beanContent.taskName);
				mIntent.putExtra("VO",beanContent);
				mIntent.putExtra("ctId",beanContent.ctId);
				mIntent.putExtra("taskStatus",beanContent.taskStatus);
				context.startActivity(mIntent);
			}
		});
		return convertView;

//
//		if (convertView == null) {
//			convertView = LayoutInflater.from(context).inflate(
//					R.layout.item_task_ongoing_1, null);
//			viewholder = new ViewHolder(convertView);
//			convertView.setTag(viewholder);
//		} else {
//			viewholder = (ViewHolder) convertView.getTag();
//		}
//		final FinishedTaskInfo noGoingTaskInfo = noGoingTaskInfos.get(position);
//		if (Util.IsNotNUll(noGoingTaskInfo.logo)) {
//			ImageLoadManager.getLoaderInstace().disPlayNormalImg(
//					noGoingTaskInfo.logo, viewholder.icon_pusher,
//					R.drawable.pusher_logo);
//		} else {
//			viewholder.icon_pusher.setImageResource(R.drawable.pusher_logo);
//		}
//		String title = noGoingTaskInfo.taskName;
//		if(title.length()>10)
//			title = title.substring(0,10)+"...";
//		viewholder.tv_Pusher.setText(title);
//		viewholder.tv_TaskGeneralInfo.setText(noGoingTaskInfo.taskGeneralInfo);
//		String now = DateUtils.ft.format(new Date());
//		viewholder.tv_Amount.setText(noGoingTaskInfo.getAmount());
//		viewholder.tv_AvailableCount.setText("领取时间  "
//				+ DateUtils.getTime(noGoingTaskInfo.receivedTime));
//
//		if (noGoingTaskInfo.isAgainPickUp == 1) {
//			viewholder.btn_collect_again.setVisibility(View.VISIBLE);
//			final RQHandler<RSReceiveTask> rqHandler_receiveTask = new RQHandler<>(
//					new IRqHandlerMsg<RSReceiveTask>() {
//
//						@Override
//						public void onBefore() {
//						}
//
//						@Override
//						public void onNetworknotvalide() {
//							ToastUtil.show(
//									context,
//									context.getResources().getString(
//											R.string.networknotconnect));
//						}
//
//						@Override
//						public void onSuccess(RSReceiveTask t) {
//							final String orderId = t.data;
//							SuccessDialog dialog = new SuccessDialog(context, "请在"
//									+ noGoingTaskInfo.taskCycle
//									+ "小时内完成\n提示：完成之后不要忘记提交审核哦");
//							dialog.addListener(new ExitDialogListener() {
//
//								@Override
//								public void clickCommit() {
//									Intent intent = new Intent(context,
//											TaskDetailInfoActivity.class);
//									intent.putExtra("TaskId", noGoingTaskInfo.taskId);
//									intent.putExtra("OrderId",orderId);
//									intent.putExtra("isList", 5);
//									context.startActivity(intent);
//								}
//
//								@Override
//								public void clickCancel() {
//									((MyTaskMaterialActivity) context).vp_task_main.setCurrentItem(ToMainPage.审核中.getValue());
//								}
//							});
//							dialog.show();
//							dialog.setCancelable(false);
//						}
//
//						@Override
//						public void onSericeErr(RSReceiveTask t) {
//							ToastUtil.show(context, t.msg);
//						}
//
//						@Override
//						public void onSericeExp() {
//
//						}
//					});
//
//			viewholder.btn_collect_again
//					.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							ApiUtil.Request(new RQBaseModel<RQReceiveTask, RSReceiveTask>(
//									context, new RQReceiveTask(
//											Utils.getUserDTO(context).data.userId, noGoingTaskInfo.taskId),
//									new RSReceiveTask(), ApiNames.领取任务.getValue(),
//									RequestType.POST, rqHandler_receiveTask));
//						}
//					});
//		} else {
//			viewholder.btn_collect_again.setVisibility(View.GONE);
//		}
//
//		convertView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context,
//						TaskDetailInfoActivity.class);
//				intent.putExtra("TaskId", noGoingTaskInfo.taskId);
//				intent.putExtra("OrderId", noGoingTaskInfo.myReceivedTaskId);
//				intent.putExtra("isList", 6);
//				context.startActivity(intent);
//			}
//		});
//		return convertView;
	}

	public class ViewHolder_1 {
		public  TextView tv_push_time;
		public TextView tv_push_content;
		public TextView tv_task_status;
		public LinearLayout ll_amount;
		public TextView tv_task_amount;
		public TextView tv_task_name;
	}
	public class ViewHolder_2 {
		public  TextView tv_push_time;
		public GridView gridView_task_pic;
		public TextView tv_task_status;
		public LinearLayout ll_amount;
		public TextView tv_task_amount;
		public TextView tv_task_name;
	}
	public class ViewHolder_3 {
		public  TextView tv_push_time;
		public GridView gridView_task_pic;
		public TextView tv_task_status;
		public LinearLayout ll_amount;
		public TextView tv_task_amount;
		public TextView tv_task_name;
	}
}
