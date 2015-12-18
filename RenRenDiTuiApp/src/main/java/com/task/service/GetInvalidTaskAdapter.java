package com.task.service;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.FinishedTaskInfo;
import com.renrentui.resultmodel.MyTaskContentBean;
import com.renrentui.tools.DateUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.UIHelper;
import com.renrentui.util.Utils;
import com.task.activity.MyTaskMaterialActivity;
import com.task.activity.ShareViewActivity;
import com.task.activity.TaskDetailInfoActivity;

/**
 * 过期任务适配器
 * 
 * @author llp
 * 
 */
public class GetInvalidTaskAdapter extends BaseAdapter {
	private Context context;
	private List<MyTaskContentBean> finishedTaskInfos;

	public GetInvalidTaskAdapter(Context context,
			List<MyTaskContentBean> finishedTaskInfos) {
		this.context = context;
		this.finishedTaskInfos = finishedTaskInfos;
	}
	public void setInvalidTaskData(List<MyTaskContentBean> finishedTaskInfos){
		this.finishedTaskInfos = finishedTaskInfos;
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		if(finishedTaskInfos==null){
			return 0;
		}else {
			return finishedTaskInfos.size();
		}
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
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public int getItemViewType(int type) {
		switch (type){
			case 1:
				//签约
				type = 0;
				break;
			case 2:
				//分享
				type = 1;
				break;
			case 3:
				//下载
				type = 2;
				break;
			default:
				type=0;
				break;

		}
		return type;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder_one =null;
		ViewHolderSecond viewholder_two = null;
		ViewHolderThree viewholder_thread =null;
		final MyTaskContentBean taskBean = (MyTaskContentBean) this.getItem(position);
		int type = this.getItemViewType(taskBean.taskType);
		switch (type){
			case 0:
				//签约
				if(convertView==null || convertView.getTag(R.id.listview_multiple_type_first_layout)==null){
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_through, parent, false);
					viewholder_one = new ViewHolder(convertView);
					convertView.setTag(R.id.listview_multiple_type_first_layout,viewholder_one);
				}else{
					viewholder_one = (ViewHolder) convertView.getTag(R.id.listview_multiple_type_first_layout);
				}
				break;
			case 1:
				//分享
				if(convertView==null || convertView.getTag(R.id.listview_multiple_type_second_layout)==null){
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_through_other, parent, false);
					viewholder_two = new ViewHolderSecond(convertView);
					convertView.setTag(R.id.listview_multiple_type_second_layout,viewholder_two);
				}else{
					viewholder_two = (ViewHolderSecond) convertView.getTag(R.id.listview_multiple_type_second_layout);
				}
				break;
			case 2:
				//下载
				if(convertView==null ||convertView.getTag(R.id.listview_multiple_type_three_layout)==null){
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_through_three, parent, false);
					viewholder_thread = new ViewHolderThree(convertView);
					convertView.setTag(R.id.listview_multiple_type_three_layout,viewholder_thread);
				}else{
					viewholder_thread = (ViewHolderThree) convertView.getTag(R.id.listview_multiple_type_three_layout);
				}
				break;
			default:
				//签约
				if(convertView==null ||convertView.getTag(R.id.listview_multiple_type_first_layout)==null){
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_through, parent, false);
					viewholder_one = new ViewHolder(convertView);
					convertView.setTag(R.id.listview_multiple_type_first_layout,viewholder_one);
				}else{
					viewholder_one = (ViewHolder) convertView.getTag(R.id.listview_multiple_type_first_layout);
				}
				break;
		}
		SpannableStringBuilder style = null;
		switch (taskBean.taskType){

			case 1:
				//签约
				style =  UIHelper.setStyleColorByColor(context, taskBean.taskTypeName.toString(), taskBean.taskGeneralInfo.toString(), R.color.white, R.color.tv_bg_color_1);
				break;
			case 2:
				//分享
				style =  UIHelper.setStyleColorByColor(context,taskBean.taskTypeName.toString(),taskBean.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_3);
				break;
			case 3:
				//下载
				style =  UIHelper.setStyleColorByColor(context,taskBean.taskTypeName.toString(),taskBean.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_2);
				break;
			default:
				style =  UIHelper.setStyleColorByColor(context,taskBean.taskTypeName.toString(),taskBean.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_1);
				break;
		}
		if(type==0){
//签约
			if (Util.IsNotNUll(taskBean.logo) && Utils.checkUrl(taskBean.logo)) {
				ImageLoadManager.getLoaderInstace().disPlayNormalImg(
						taskBean.logo, viewholder_one.icon_pusher,
						R.drawable.pusher_logo);
			} else {
				viewholder_one.icon_pusher.setImageResource(R.drawable.pusher_logo);
			}
			viewholder_one.tv_Pusher_taskName.setText(taskBean.taskName);
			viewholder_one.tv_pusher_amount.setText(taskBean.getAmount());


			viewholder_one.tv_pusher_taskType_content.setText(style);
			viewholder_one.textView_1.setText(context.getResources().getString(R.string.my_task_list_itemt_1, taskBean.getWaitNum()));
			viewholder_one.textView_2.setText(context.getResources().getString(R.string.my_task_list_itemt_2, taskBean.getPassNum()));
			viewholder_one.textView_3.setText(context.getResources().getString(R.string.my_task_list_itemt_3, taskBean.getRefuseNum()));
			viewholder_one.textView_1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					setTaskDetailListActivity(taskBean.taskId, taskBean.taskName, taskBean.ctId, ToMainPage.审核中.getValue(),taskBean.status);
				}
			});
			viewholder_one.textView_2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					setTaskDetailListActivity(taskBean.taskId, taskBean.taskName, taskBean.ctId, ToMainPage.已通过.getValue(),taskBean.status);
				}
			});
			viewholder_one.textView_3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					setTaskDetailListActivity(taskBean.taskId, taskBean.taskName, taskBean.ctId, ToMainPage.未通过.getValue(),taskBean.status);
				}
			});
			viewholder_one.mRL_task_content.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					setTaskDetailActivity(taskBean.taskId, taskBean.taskName);
				}
			});
		}else if(type==1){
			//分享
			if (Util.IsNotNUll(taskBean.logo) && Utils.checkUrl(taskBean.logo)) {
				ImageLoadManager.getLoaderInstace().disPlayNormalImg(
						taskBean.logo, viewholder_two.icon_pusher,
						R.drawable.pusher_logo);
			} else {
				viewholder_two.icon_pusher.setImageResource(R.drawable.pusher_logo);
			}
			viewholder_two.tv_Pusher_taskName.setText(taskBean.taskName);
			viewholder_two.tv_pusher_amount.setText(taskBean.getAmount());


			viewholder_two.tv_pusher_taskType_content.setText(style);
			if("0".equals(taskBean.getComplateNum())){
				viewholder_two.textView_4.setText(context.getResources().getString(R.string.my_task_list_itemt_5));
			}else {
				viewholder_two.textView_4.setText(context.getResources().getString(R.string.my_task_list_itemt_4, taskBean.getComplateNum()));
			}
			//viewholder_two.textView_4.setText(context.getResources().getString(R.string.my_task_list_itemt_4, taskBean.getComplateNum()));
			viewholder_two.btn_1.setVisibility(View.GONE);
//			viewholder_two.mRL_task_content.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					setTaskDetailActivity(taskBean.taskId, taskBean.taskName);
//				}
//			});
			viewholder_two.mRL_task_content.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					setTaskDetailActivity(taskBean.taskId, taskBean.taskName);
				}
			});
		}else if(type==2){
			//下载
			if (Util.IsNotNUll(taskBean.logo) && Utils.checkUrl(taskBean.logo)) {
				ImageLoadManager.getLoaderInstace().disPlayNormalImg(
						taskBean.logo, viewholder_thread.icon_pusher,
						R.drawable.pusher_logo);
			} else {
				viewholder_thread.icon_pusher.setImageResource(R.drawable.pusher_logo);
			}
			viewholder_thread.tv_Pusher_taskName.setText(taskBean.taskName);
			viewholder_thread.tv_pusher_amount.setText(taskBean.getAmount());

			viewholder_thread.tv_pusher_taskType_content.setText(style);
			if("0".equals(taskBean.getComplateNum())){
				viewholder_thread.textView_4.setText(context.getResources().getString(R.string.my_task_list_itemt_6));
			}else {
				viewholder_thread.textView_4.setText(context.getResources().getString(R.string.my_task_list_itemt_4, taskBean.getComplateNum()));
			}
			//viewholder_thread.textView_4.setText(context.getResources().getString(R.string.my_task_list_itemt_4, taskBean.getComplateNum()));
			viewholder_thread.btn_1.setVisibility(View.GONE);
			viewholder_thread.mRL_task_content.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					setTaskDetailActivity(taskBean.taskId, taskBean.taskName);
				}
			});
		}
//		convertView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				setTaskDetailActivity(taskBean.taskId, taskBean.taskName);
//			}
//		});
		return convertView;
	}
	//任务详情页面
	private void setTaskDetailActivity(String taskId,String taskName){
		Intent mIntent = new Intent();
		mIntent.setClass(context,TaskDetailInfoActivity.class);
		mIntent.putExtra("TaskId",taskId);
		mIntent.putExtra("TaskName",taskName);
		context.startActivity(mIntent);
	}
	//任务资料模板
	private void setTaskDetailListActivity(String taskId,String taskName,String ctid,int page,String taskStatus){
		Intent mIntent = new Intent();
		mIntent.setClass(context,MyTaskMaterialActivity.class);
		mIntent.putExtra("TASK_ID", taskId);
		mIntent.putExtra("TASK_NAME", taskName);
		mIntent.putExtra("ctId", ctid);
		mIntent.putExtra("topage", page);
		mIntent.putExtra("taskStatus",taskStatus);
		mIntent.putExtra("isShowSubmitBtn",true);
		context.startActivity(mIntent);
	}
	public class ViewHolder {
		public RelativeLayout mRL_task_content;
		public ImageView icon_pusher;
		public TextView tv_Pusher_taskName;
		public TextView tv_pusher_taskType_content;
		public LinearLayout ll_pusher_amount;
		public TextView tv_pusher_amount;
		//		分类
		public TextView textView_1;
		public TextView textView_2;
		public TextView textView_3;
		public TextView btn_1;


		public ViewHolder(View view) {
			mRL_task_content = (RelativeLayout)view.findViewById(R.id.rl_task_content);
			icon_pusher = (ImageView) view.findViewById(R.id.icon_pusher);
			tv_Pusher_taskName = (TextView) view.findViewById(R.id.tv_pusher_taskName);
			tv_pusher_taskType_content = (TextView) view
					.findViewById(R.id.tv_pusher_taskType_content);
			ll_pusher_amount = (LinearLayout) view
					.findViewById(R.id.ll_amount);
			tv_pusher_amount = (TextView) view.findViewById(R.id.tv_amount);

			textView_1 = (TextView)view.findViewById(R.id.tv_1);
			textView_2 = (TextView)view.findViewById(R.id.tv_2);
			textView_3 = (TextView)view.findViewById(R.id.tv_3);

			btn_1 = (Button)view.findViewById(R.id.btn_other);
		}
	}
	public class ViewHolderSecond {
		public RelativeLayout mRL_task_content;
		public ImageView icon_pusher;
		public TextView tv_Pusher_taskName;
		public TextView tv_pusher_taskType_content;
		public LinearLayout ll_pusher_amount;
		public TextView tv_pusher_amount;
		//		分类

		public TextView textView_4;
		public TextView btn_1;


		public ViewHolderSecond(View view) {
			mRL_task_content = (RelativeLayout)view.findViewById(R.id.rl_task_content);
			icon_pusher = (ImageView) view.findViewById(R.id.icon_pusher);
			tv_Pusher_taskName = (TextView) view.findViewById(R.id.tv_pusher_taskName);
			tv_pusher_taskType_content = (TextView) view
					.findViewById(R.id.tv_pusher_taskType_content);
			ll_pusher_amount = (LinearLayout) view
					.findViewById(R.id.ll_amount);
			tv_pusher_amount = (TextView) view.findViewById(R.id.tv_amount);

			textView_4 = (TextView)view.findViewById(R.id.tv_4);

			btn_1 = (Button)view.findViewById(R.id.btn_other);
		}
	}
	class ViewHolderThree {
		public RelativeLayout mRL_task_content;
		public ImageView icon_pusher;
		public TextView tv_Pusher_taskName;
		public TextView tv_pusher_taskType_content;
		public LinearLayout ll_pusher_amount;
		public TextView tv_pusher_amount;
		//		分类

		public TextView textView_4;
		public TextView btn_1;


		public ViewHolderThree(View view) {
			mRL_task_content = (RelativeLayout)view.findViewById(R.id.rl_task_content);
			icon_pusher = (ImageView) view.findViewById(R.id.icon_pusher);
			tv_Pusher_taskName = (TextView) view.findViewById(R.id.tv_pusher_taskName);
			tv_pusher_taskType_content = (TextView) view
					.findViewById(R.id.tv_pusher_taskType_content);
			ll_pusher_amount = (LinearLayout) view
					.findViewById(R.id.ll_amount);
			tv_pusher_amount = (TextView) view.findViewById(R.id.tv_amount);

			textView_4 = (TextView)view.findViewById(R.id.tv_4);

			btn_1 = (Button)view.findViewById(R.id.btn_other);
		}
	}
}
