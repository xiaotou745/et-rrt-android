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
import com.renrentui.util.Utils;
import com.task.activity.MyTaskMaterialActivity;
import com.task.activity.TaskDetailInfoActivity;

/**
 * 进行中的任务适配器
 * 
 * @author llp
 * 
 */
public class GetThroughTaskAdapter extends BaseAdapter {
	private Context context;
	private List<MyTaskContentBean> finishedTaskInfos;


	public GetThroughTaskAdapter(Context context,
			List<MyTaskContentBean> finishedTaskInfos) {
		this.context = context;
		this.finishedTaskInfos = finishedTaskInfos;
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
		return 2;
	}

	@Override
	public int getItemViewType(int type) {
		switch (type){
			case 1:
				//签约
				break;
			case 2:
				//分享
				break;
			case 3:
				//下载
				break;

		}
		return type;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		 ViewHolder viewholder =null;
		 ViewHolder_other vieholderOther = null;
		final MyTaskContentBean taskBean = (MyTaskContentBean)this.getItem(position);
		int type = taskBean.taskType;
		if(convertView==null){
			switch (type){
				case 1:
				case 3:
					//签约
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_through,parent,false);
					viewholder = new ViewHolder(convertView);
					convertView.setTag(viewholder);
					break;
				case 2:
					//分享
					convertView = LayoutInflater.from(context).inflate(R.layout.item_task_through_other,parent,false);
					vieholderOther = new ViewHolder_other(convertView);
					convertView.setTag(vieholderOther);
					break;
			}
		}else{
			switch (type){
				case 1:
				case 3:
					//签约
					viewholder = (ViewHolder)convertView.getTag();
					break;
				case 2:
					vieholderOther = (ViewHolder_other)convertView.getTag();
					break;
			}
		}
		if(type==3 || type==1){
			//签约
			if (Util.IsNotNUll(taskBean.logo) && Utils.checkUrl(taskBean.logo)) {
				ImageLoadManager.getLoaderInstace().disPlayNormalImg(
						taskBean.logo, viewholder.icon_pusher,
						R.drawable.pusher_logo);
			} else {
				viewholder.icon_pusher.setImageResource(R.drawable.pusher_logo);
			}
			viewholder.tv_Pusher_taskName.setText(taskBean.taskName);
			viewholder.tv_pusher_amount.setText(taskBean.getAmount());

			//内容信息变换
			String strType =  " "+taskBean.taskTypeName.toString()+" ";
			String strTypeContent =strType +" "+taskBean.taskGeneralInfo.toString();
			int fstart = strTypeContent.indexOf(taskBean.taskTypeName.toString());
			int fend = fstart + taskBean.taskTypeName.toString().length();
			int bstart = 0;
			int bend = strType.length();
			SpannableStringBuilder style = new SpannableStringBuilder(strTypeContent);
			style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)),fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			style.setSpan(new BackgroundColorSpan(context.getResources().getColor(R.color.tv_bg_color_1)),bstart,bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			viewholder.tv_pusher_taskType_content.setText(style);
			viewholder.textView_1.setText(context.getResources().getString(R.string.my_task_list_itemt_1,taskBean.getWaitNum()));
			viewholder.textView_2.setText(context.getResources().getString(R.string.my_task_list_itemt_2,taskBean.getPassNum()));
			viewholder.textView_3.setText(context.getResources().getString(R.string.my_task_list_itemt_3,taskBean.getRefuseNum()));
			viewholder.textView_1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					setTaskDetailListActivity(taskBean.taskId,taskBean.taskName,taskBean.ctId, ToMainPage.审核中.getValue());
				}
			});
			viewholder.textView_2.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					setTaskDetailListActivity(taskBean.taskId,taskBean.taskName,taskBean.ctId,ToMainPage.已通过.getValue());
				}
			});
			viewholder.textView_3.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					setTaskDetailListActivity(taskBean.taskId,taskBean.taskName,taskBean.ctId,ToMainPage.未通过.getValue());
				}
			});
		}else{
			//分享
			if (Util.IsNotNUll(taskBean.logo) && Utils.checkUrl(taskBean.logo)) {
				ImageLoadManager.getLoaderInstace().disPlayNormalImg(
						taskBean.logo, vieholderOther.icon_pusher,
						R.drawable.pusher_logo);
			} else {
				vieholderOther.icon_pusher.setImageResource(R.drawable.pusher_logo);
			}
			vieholderOther.tv_Pusher_taskName.setText(taskBean.taskName);
			vieholderOther.tv_pusher_amount.setText(taskBean.getAmount());

			//内容信息变换
			String strType =  " "+taskBean.taskTypeName.toString()+" ";
			String strTypeContent =strType +" "+taskBean.taskGeneralInfo.toString();
			int fstart = strTypeContent.indexOf(taskBean.taskTypeName.toString());
			int fend = fstart + taskBean.taskTypeName.toString().length();
			int bstart = 0;
			int bend = strType.length();
			SpannableStringBuilder style = new SpannableStringBuilder(strTypeContent);
			style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			style.setSpan(new BackgroundColorSpan(context.getResources().getColor(R.color.tv_bg_color_1)), bstart, bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			vieholderOther.tv_pusher_taskType_content.setText(style);
			vieholderOther.textView_4.setText(context.getResources().getString(R.string.my_task_list_itemt_4,taskBean.getComplateNum()));
			vieholderOther.btn_1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//分享
				}
			});
			vieholderOther.mRL_task_content.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					setTaskDetailActivity(taskBean.taskId,taskBean.taskName);
				}
			});

		}

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
	//任务资料
	private void setTaskDetailListActivity(String taskId,String taskName,String ctid,int page){
		Intent mIntent = new Intent();
		mIntent.setClass(context,MyTaskMaterialActivity.class);
		mIntent.putExtra("TASK_ID", taskId);
		mIntent.putExtra("TASK_NAME",taskName);
		mIntent.putExtra("ctId",ctid);
		mIntent.putExtra("topage",page);
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
		public TextView textView_4;
		public TextView btn_1;


		public ViewHolder(View view) {
			mRL_task_content = (RelativeLayout)view.findViewById(R.id.rl_task_content);
			icon_pusher = (ImageView) view.findViewById(R.id.icon_pusher);
			tv_Pusher_taskName = (TextView) view.findViewById(R.id.tv_pusher_taskName);
			tv_pusher_taskType_content = (TextView) view
					.findViewById(R.id.tv_pusher_taskType_content);
			ll_pusher_amount = (LinearLayout) view
					.findViewById(R.id.ll_amount);
			tv_pusher_amount = (TextView) view.findViewById(R.id.tv_Amount);

			textView_1 = (TextView)view.findViewById(R.id.tv_1);
			textView_2 = (TextView)view.findViewById(R.id.tv_2);
			textView_3 = (TextView)view.findViewById(R.id.tv_3);
			textView_4 = (TextView)view.findViewById(R.id.tv_4);

			btn_1 = (Button)view.findViewById(R.id.btn_other);
		}
	}
	public class ViewHolder_other {
		public RelativeLayout mRL_task_content;
		public ImageView icon_pusher;
		public TextView tv_Pusher_taskName;
		public TextView tv_pusher_taskType_content;
		public LinearLayout ll_pusher_amount;
		public TextView tv_pusher_amount;
		//		分类

		public TextView textView_4;
		public TextView btn_1;


		public ViewHolder_other(View view) {
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
