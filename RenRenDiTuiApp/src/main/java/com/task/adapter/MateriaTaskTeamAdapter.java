package com.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.RSMyMaterialTaskTeamModel;
import com.renrentui.resultmodel.TaskMetarialContent;
import com.renrentui.tools.Util;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.TimeUtils;
import com.renrentui.util.UIHelper;
import com.renrentui.util.Utils;
import com.task.activity.MyTaskMaterialActivity;
import com.task.activity.MyTaskMaterialDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的资料任务分组适配器
 */
public class MateriaTaskTeamAdapter extends BaseAdapter {
	private Context context;
	private List<RSMyMaterialTaskTeamModel.Content> mDataContent;
	private int iType = 1;

	public MateriaTaskTeamAdapter(Context context,int type) {
		this.context = context;
		iType = type;
	}
	public MateriaTaskTeamAdapter(Context context, List<RSMyMaterialTaskTeamModel.Content> data) {
		this.context = context;
		this.mDataContent = data;
	}
	public void setData(List<RSMyMaterialTaskTeamModel.Content> data){
		if(mDataContent==null){
			mDataContent = new ArrayList<RSMyMaterialTaskTeamModel.Content>();
		}else{
			mDataContent.clear();
		}
		mDataContent.addAll(data);
		this.notifyDataSetChanged();
	}



	@Override
	public int getCount() {
		if(mDataContent==null){
			return 0;
		}else {
			return mDataContent.size();
		}
	}

	@Override
	public Object getItem(int position) {
		return mDataContent.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mViewHolder =null;
		final RSMyMaterialTaskTeamModel.Content beanContent = (RSMyMaterialTaskTeamModel.Content)getItem(position);
		if(convertView==null || convertView.getTag(R.id.listview_multiple_type_nine_layout)==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_mymaterialtaskteam_layout,parent,false);
			mViewHolder = new ViewHolder (convertView);
			convertView.setTag(R.id.listview_multiple_type_nine_layout,mViewHolder);
		}else{
			mViewHolder = (ViewHolder)convertView.getTag(R.id.listview_multiple_type_nine_layout);
		}

		mViewHolder.mTV_1.setText(Html.fromHtml(context.getResources().getString(R.string.my_material_task_team_num, beanContent.getTaskDatumCount())));
		mViewHolder.mTV_2.setText(beanContent.getTaskName());
		mViewHolder.mTV_3.setText(Html.fromHtml(context.getResources().getString(R.string.my_material_task_team_content,beanContent.getAmount())));

		if (Util.IsNotNUll(beanContent.getLogo()) && Utils.checkUrl(beanContent.getLogo())) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(
					beanContent.getLogo(), mViewHolder.mIV_0,
					R.drawable.pusher_logo);
		} else {
			mViewHolder.mIV_0.setImageResource(R.drawable.pusher_logo);
		}
//		switch (beanContent.getTaskType()){
//			case 1:
//				//签约任务
//				mViewHolder.mIV_4.setImageResource(R.drawable.team_qianyue);
//				break;
//			case 2:
//				//分享
//				mViewHolder.mIV_4.setImageResource(R.drawable.team_share);
//				break;
//			case 3:
//				//下载
//				mViewHolder.mIV_4.setImageResource(R.drawable.team_down);
//				break;
//		}
		mViewHolder.mIV_4.setText(beanContent.tagName);
		if(!TextUtils.isEmpty(beanContent.tagColorCode)){
			mViewHolder.mIV_4.setBackgroundColor(Color.parseColor(beanContent.tagColorCode));
		}
		convertView.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent();
				mIntent.setClass(context,MyTaskMaterialActivity.class);
				mIntent.putExtra("topage",iType);
				mIntent.putExtra("TASK_ID",beanContent.getTaskId());
				mIntent.putExtra("TASK_NAME",beanContent.getTaskName());
				mIntent.putExtra("ctId",beanContent.getCtId());
				mIntent.putExtra("TASK_TYPENAME",beanContent.getTaskTypeName());
				context.startActivity(mIntent);
			}
		});
		return convertView;

	}

	public class ViewHolder{
		private ImageView mIV_0;
		private TextView mTV_1;
		private TextView mTV_2;
		private TextView mTV_3;
		private TextView mIV_4;
		ViewHolder(){

		}

		ViewHolder(View view){
			mIV_0 = (ImageView)view.findViewById(R.id.iv_team_0);
			mTV_1 = (TextView)view.findViewById(R.id.tv_team_1);
			mTV_2 = (TextView)view.findViewById(R.id.tv_team_2);
			mTV_3 = (TextView)view.findViewById(R.id.tv_team_3);
			mIV_4 = (TextView)view.findViewById(R.id.iv_team_4);
		}

	}

}
