package com.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.MyTaskContentBean;
import com.renrentui.tools.Util;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.Utils;
import com.task.activity.MyTaskMaterialActivity;
import com.task.activity.ShareViewActivity;
import com.task.activity.TaskDetailInfoNewActivity;

import java.util.List;

/**
 * 我的任务 新 适配器
 * 
 * @author llp
 * 
 */
public class MyFragmentTaskAdapter extends BaseAdapter {
	private Context context;
	private List<MyTaskContentBean> finishedTaskInfos;
	private int itype= 0;//0: 进行张 1：过期
	public MyFragmentTaskAdapter(Context context,
								 List<MyTaskContentBean> finishedTaskInfos,int itype) {
		this.context = context;
		this.finishedTaskInfos = finishedTaskInfos;
		this.itype = itype;
	}
public void setThroughTaskData(List<MyTaskContentBean> finishedTaskInfos){
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
	public View getView( int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder =null;
		final MyTaskContentBean beanContent = (MyTaskContentBean)getItem(position);
		if(convertView==null || convertView.getTag(R.id.listview_multiple_type_ten_layout)==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_mytasknew_layout,parent,false);
			mViewHolder = new ViewHolder (convertView);
			convertView.setTag(R.id.listview_multiple_type_ten_layout,mViewHolder);
		}else{
			mViewHolder = (ViewHolder)convertView.getTag(R.id.listview_multiple_type_ten_layout);
		}

		mViewHolder.mTV_1.setText(beanContent.getAmount());
		mViewHolder.mTV_2.setText(beanContent.taskName);
		mViewHolder.mTV_3.setText(beanContent.taskGeneralInfo);

		if (Util.IsNotNUll(beanContent.logo) && Utils.checkUrl(beanContent.logo.trim())) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(
					beanContent.logo, mViewHolder.mIV_0,
					R.drawable.pusher_logo);
		} else {
			mViewHolder.mIV_0.setImageResource(R.drawable.pusher_logo);
		}
		switch (beanContent.taskType){
			case 1:
				//签约任务
				mViewHolder.mIV_4.setImageResource(R.drawable.team_qianyue);
				break;
			case 2:
				//分享
				mViewHolder.mIV_4.setImageResource(R.drawable.team_share);
				break;
			case 3:
				//下载
				mViewHolder.mIV_4.setImageResource(R.drawable.team_down);
				break;
		}
		switch (itype){
			case 0:
				//进行中
				if(beanContent.taskType==1){
					//签约
					mViewHolder.mRL_bottom_0.setVisibility(View.VISIBLE);
					if(!TextUtils.isEmpty(beanContent.complateNum) && !beanContent.complateNum.equals("0")) {
						mViewHolder.mTV_bottom_0.setText(context.getResources().getString(R.string.my_tasknew_qianyue_flag, beanContent.complateNum));
					}else{
						mViewHolder.mTV_bottom_0.setText(context.getResources().getString(R.string.my_task_list_itemt_5));
					}

				}else if(beanContent.taskType==2){
					//分享
					mViewHolder.mRL_bottom_1.setVisibility(View.VISIBLE);
					if(!TextUtils.isEmpty(beanContent.complateNum) && !beanContent.complateNum.equals("0")) {
						mViewHolder.mTV_bottom_1.setText(context.getResources().getString(R.string.my_task_list_itemt_4, beanContent.complateNum));
					}else{
						mViewHolder.mTV_bottom_1.setText(context.getResources().getString(R.string.my_task_list_itemt_5));
					}
				}else if(beanContent.taskType==3) {
					//下载
					mViewHolder.mRL_bottom_0.setVisibility(View.VISIBLE);
					if(!TextUtils.isEmpty(beanContent.complateNum) && !beanContent.complateNum.equals("0")) {
						mViewHolder.mTV_bottom_0.setText(context.getResources().getString(R.string.my_tasknew_qianyue_flag, beanContent.complateNum));
					}else{
						mViewHolder.mTV_bottom_0.setText(context.getResources().getString(R.string.my_task_list_itemt_5));
					}
				}
				//继续提交
				mViewHolder.mTV_bottom_0.setOnClickListener(new View.OnClickListener(){
					public void onClick(View v) {
						setTaskDetailListActivity(beanContent.taskId, beanContent.taskName, beanContent.ctId,beanContent.status);
					}
				});
				//分享
				mViewHolder.mTV_bottom_1.setOnClickListener(new View.OnClickListener(){
					public void onClick(View v) {
						setShareActivity(beanContent);
					}
				});
				break;
			case 1:
				//过期
				if(beanContent.taskType==1){
					//签约
					mViewHolder.mRL_bottom_2.setVisibility(View.VISIBLE);
					if(!TextUtils.isEmpty(beanContent.complateNum) && !beanContent.complateNum.equals("0")) {
						mViewHolder.mTV_bottom_2.setText(context.getResources().getString(R.string.my_tasknew_qianyue_flag, beanContent.complateNum));
					}else{
						mViewHolder.mTV_bottom_2.setText(context.getResources().getString(R.string.my_task_list_itemt_6));
					}
				}else if(beanContent.taskType==2){
					//分享
					mViewHolder.mRL_bottom_3.setVisibility(View.VISIBLE);
					if(!TextUtils.isEmpty(beanContent.complateNum) && !beanContent.complateNum.equals("0")) {
						mViewHolder.mTV_bottom_3.setText(context.getResources().getString(R.string.my_task_list_itemt_4,beanContent.complateNum));
					}else{
						mViewHolder.mTV_bottom_2.setText(context.getResources().getString(R.string.my_task_list_itemt_6));
					}
				}else if(beanContent.taskType==3) {
					//下载
					mViewHolder.mRL_bottom_2.setVisibility(View.VISIBLE);
					if(!TextUtils.isEmpty(beanContent.complateNum) && !beanContent.complateNum.equals("0")) {
						mViewHolder.mTV_bottom_2.setText(context.getResources().getString(R.string.my_tasknew_qianyue_flag,beanContent.complateNum));
					}else{
						mViewHolder.mTV_bottom_2.setText(context.getResources().getString(R.string.my_task_list_itemt_6));
					}
				}
				break;
		}
		convertView.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				setTaskDetailActivity(beanContent.taskId,beanContent.taskName);
			}
		});
		return convertView;
	}
	//分享
	private void setShareActivity(MyTaskContentBean beanContent){
		Intent mIntent = new Intent(context, ShareViewActivity.class);
		mIntent.putExtra("TASK_ID", beanContent.taskId);
		mIntent.putExtra("SHARE_CONTENT", beanContent.downUrl);
		mIntent.putExtra("scanTip", beanContent.scanTip);
		mIntent.putExtra("reminder",beanContent.reminder);
		mIntent.putExtra("taskStatus",beanContent.status);
		context.startActivity(mIntent);
	}
	//任务详情页面
	private void setTaskDetailActivity(String taskId,String taskName){
		Intent mIntent = new Intent();
		mIntent.setClass(context, TaskDetailInfoNewActivity.class);
		mIntent.putExtra("TaskId",taskId);
		mIntent.putExtra("TaskName",taskName);
		context.startActivity(mIntent);
	}
	//再此资料提交
	private void setTaskDetailListActivity(String taskId,String taskName,String ctid,String taskStatus){
		Intent mIntent = new Intent();
		mIntent.setClass(context, MyTaskMaterialActivity.class);
		mIntent.putExtra("TASK_ID", taskId);
		mIntent.putExtra("TASK_NAME",taskName);
		mIntent.putExtra("ctId",ctid);
		mIntent.putExtra("taskStatus",taskStatus);
		mIntent.putExtra("isShowSubmitBtn",true);
		context.startActivity(mIntent);
	}

	public class ViewHolder{
		private ImageView mIV_0;//图标
		private TextView mTV_1;//金额
		private TextView mTV_2;//title
		private TextView mTV_3;//content
		private ImageView mIV_4;//icon flag

		private RelativeLayout mRL_bottom_0;
		private TextView mTV_bottom_0;
		private Button mBtn_bottom_0;

		private RelativeLayout mRL_bottom_1;
		private TextView mTV_bottom_1;
		private Button mBtn_bottom_1;

		private RelativeLayout mRL_bottom_2;
		private TextView mTV_bottom_2;
		private Button mBtn_bottom_2;

		private RelativeLayout mRL_bottom_3;
		private TextView mTV_bottom_3;
		private Button mBtn_bottom_3;
		ViewHolder(){

		}

		ViewHolder(View view){
			mIV_0 = (ImageView)view.findViewById(R.id.iv_team_0);
			mTV_1 = (TextView)view.findViewById(R.id.tv_team_1);
			mTV_2 = (TextView)view.findViewById(R.id.tv_team_2);
			mTV_3 = (TextView)view.findViewById(R.id.tv_team_3);
			mIV_4 = (ImageView)view.findViewById(R.id.iv_team_4);

			mRL_bottom_0 = (RelativeLayout)view.findViewById(R.id.rl_task_pass_0);
			mTV_bottom_0 = (TextView)view.findViewById(R.id.tv_bottom_0);
			mBtn_bottom_0 = (Button)view.findViewById(R.id.btn_bottom_0);

			mRL_bottom_1 = (RelativeLayout)view.findViewById(R.id.rl_task_pass_1);
			mTV_bottom_1 = (TextView)view.findViewById(R.id.tv_bottom_1);
			mBtn_bottom_1 = (Button)view.findViewById(R.id.btn_bottom_1);

			mRL_bottom_2 = (RelativeLayout)view.findViewById(R.id.rl_task_pass_2);
			mTV_bottom_2 = (TextView)view.findViewById(R.id.tv_bottom_2);
			mBtn_bottom_2 = (Button)view.findViewById(R.id.btn_bottom_2);

			mRL_bottom_3 = (RelativeLayout)view.findViewById(R.id.rl_task_pass_3);
			mTV_bottom_3 = (TextView)view.findViewById(R.id.tv_bottom_3);
		}

	}
}
