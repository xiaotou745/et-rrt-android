package com.task.service;

import java.nio.channels.GatheringByteChannel;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQReceiveTask;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSReceiveTask;
import com.renrentui.resultmodel.TaskMetarialContent;
import com.renrentui.tools.DateUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.TimeUtils;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.activity.MyTaskMaterialActivity;
import com.task.activity.TaskDetailInfoActivity;
import com.task.service.SuccessDialog.ExitDialogListener;

import org.w3c.dom.Text;

/**
 * 审核中资料适配器
 * 
 * @author llp
 * 
 */
public class GetOnGoingAdapter extends BaseAdapter {
	private Context context;
	private List<TaskMetarialContent> noGoingTaskInfos;

	public GetOnGoingAdapter(Context context,
			List<TaskMetarialContent> noGoingTaskInfos) {
		this.context = context;
		this.noGoingTaskInfos = noGoingTaskInfos;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int type) {
		if (type == 1) {
			//文本
			return 1;
		} else if (type == 2) {
			//图片
			return 2;
		} else  {
			//图片组
			return 2;
		}
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
		TaskMetarialContent beanContent = (TaskMetarialContent)getItem(position);
		int iType = getItemViewType(beanContent.groupType);
		if(convertView==null){
			switch (iType){
				case 1:
					viewholder_1= new ViewHolder_1();
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_ongoing_1,parent,false);
					viewholder_1.tv_push_time = (TextView)convertView.findViewById(R.id.tv_pusher_time);
					viewholder_1.tv_push_content = (TextView)convertView.findViewById(R.id.tv_pusher_taskcontent);
					viewholder_1.tv_task_status = (TextView)convertView.findViewById(R.id.tv_task_status);
					viewholder_1.tv_task_name = (TextView)convertView.findViewById(R.id.tv_task_name);
					viewholder_1.ll_amount = (LinearLayout)convertView.findViewById(R.id.ll_amount);
					viewholder_1.tv_task_amount = (TextView)convertView.findViewById(R.id.tv_task_amount);
					convertView.setTag(viewholder_1);
					break;
				case 2:
					viewHolder_2 = new ViewHolder_2();
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_ongoing_2,parent,false);
					viewHolder_2.tv_push_time = (TextView)convertView.findViewById(R.id.tv_pusher_time);
					viewHolder_2.gridView_task_pic = (GridView)convertView.findViewById(R.id.gridview_task_pic);
					viewHolder_2.tv_task_status = (TextView)convertView.findViewById(R.id.tv_task_status);
					viewHolder_2.tv_task_name = (TextView)convertView.findViewById(R.id.tv_task_name);
					viewHolder_2.ll_amount = (LinearLayout)convertView.findViewById(R.id.ll_amount);
					viewHolder_2.tv_task_amount = (TextView)convertView.findViewById(R.id.tv_task_amount);
					convertView.setTag(viewHolder_2);
					break;
			}
		}else{
			switch (iType){
				case 1:
					viewholder_1 = (ViewHolder_1)convertView.getTag();
					break;
				case 2:
					viewHolder_2 = (ViewHolder_2)convertView.getTag();
					break;
			}
		}

		switch (iType){
			case 1:
				viewholder_1.tv_push_time.setText("提交时间  "+ TimeUtils.StringPattern(beanContent.createDate,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));
				if(beanContent.titlesList!=null && beanContent.titlesList.size()>0){
					viewholder_1.tv_push_content.setText(beanContent.titlesList.get(0));
				}else{
					viewholder_1.tv_push_content.setText("...");
				}

				viewholder_1.tv_task_status.setText("进行中");
				viewholder_1.tv_task_status.setTextColor(context.getResources().getColor(R.color.tv_order_color_5));
				viewholder_1.tv_task_amount.setText(String.valueOf(beanContent.getAmount()));
				//内容信息变换
				String strType =  " "+beanContent.taskTypeName.toString()+" ";
				String strTypeContent =strType +" "+beanContent.taskName.toString();
				int fstart = strTypeContent.indexOf(beanContent.taskTypeName.toString());
				int fend = fstart + beanContent.taskTypeName.toString().length();
				int bstart = 0;
				int bend = strType.length();
				SpannableStringBuilder style = new SpannableStringBuilder(strTypeContent);
				style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)),fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				style.setSpan(new BackgroundColorSpan(context.getResources().getColor(R.color.tv_bg_color_1)),bstart,bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				viewholder_1.tv_task_name.setText(style);
				break;
			case 2:
				viewHolder_2.tv_push_time.setText("提交时间  "+ TimeUtils.StringPattern(beanContent.createDate,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));
				viewHolder_2.tv_task_status.setText("进行中");
				viewHolder_2.tv_task_status.setTextColor(context.getResources().getColor(R.color.tv_order_color_5));
				viewHolder_2.tv_task_amount.setText(String.valueOf(beanContent.getAmount()));
				//内容信息变换
				String strType_2 =  " "+beanContent.taskTypeName.toString()+" ";
				String strTypeContent_2 =strType_2 +" "+beanContent.taskName.toString();
				int fstart_2 = strTypeContent_2.indexOf(beanContent.taskTypeName.toString());
				int fend_2 = fstart_2 + beanContent.taskTypeName.toString().length();
				int bstart_2 = 0;
				int bend_2 = strType_2.length();
				SpannableStringBuilder style_2 = new SpannableStringBuilder(strTypeContent_2);
				style_2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)),fstart_2,fend_2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				style_2.setSpan(new BackgroundColorSpan(context.getResources().getColor(R.color.tv_bg_color_1)),bstart_2,bend_2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				viewHolder_2.tv_task_name.setText(style_2);
				viewHolder_2.gridView_task_pic.setAdapter(new GriveiwTaskPicAdapter(context,beanContent.titlesList));
				break;
		}

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
}
