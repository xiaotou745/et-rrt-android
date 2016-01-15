package com.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.PartnerList;
import com.renrentui.resultmodel.TaskDeatailInfoNew;
import com.renrentui.tools.Util;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.Utils;
import com.task.activity.TaskMyFriendListViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/14 0014.
 * 任务参与人适配器
 */
public class TaskFriendGridAdapter extends BaseAdapter {
    private Context context;
    private List<PartnerList> mData = new ArrayList<PartnerList>();
    private TaskDeatailInfoNew mTaskDetailInfoNew =null;
    private int iType=0;//0:gridView 1:listview
    public TaskFriendGridAdapter(Context context ,int iType){
        this.context = context;
        this.iType = iType;
    }
    public void setData(List<PartnerList> data){
        if(mData==null){
            mData = new ArrayList<PartnerList>();
        }else{
            mData.clear();
        }
        mData.addAll(data);
        this.notifyDataSetChanged();
    }
    public void setTaskDetailInfoNewData(TaskDeatailInfoNew vo){
        this.mTaskDetailInfoNew =vo;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return iType;
    }

    @Override
    public int getCount() {
        if(mData==null){
            return 0;
        }else{
            if(iType==0){
                return mData.size()>5?5:mData.size();
            }else{
                return mData.size();
            }
        }
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoldView mHolderView = null;
        if(iType==0){
            if (convertView == null || convertView.getTag(R.id.listview_multiple_type_eleven_layout) == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_mytaskdetail_new_friend_layout, parent, false);
                mHolderView = new HoldView(convertView);
                convertView.setTag(R.id.listview_multiple_type_eleven_layout, mHolderView);
            } else {
                mHolderView = (HoldView) convertView.getTag(R.id.listview_multiple_type_eleven_layout);
            }
        }else{
            if (convertView == null || convertView.getTag(R.id.listview_multiple_type_twelve_layout) == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_mytaskdetail_new_friend_layout_1, parent, false);
                mHolderView = new HoldView(convertView);
                convertView.setTag(R.id.listview_multiple_type_twelve_layout, mHolderView);
            } else {
                mHolderView = (HoldView) convertView.getTag(R.id.listview_multiple_type_twelve_layout);
            }
        }
        PartnerList bean  = (PartnerList)getItem(position);
        mHolderView.mTV_1.setText(bean.getClienterName());
        if(iType==0){
            //gridview
            if(position==4){
                //更多
                mHolderView.mIV_0.setImageResource(R.drawable.icon_task_friend_more);
                mHolderView.mTV_1.setText("更多");
                convertView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent mIntent = new Intent(context, TaskMyFriendListViewActivity.class);
                        mIntent.putExtra("VO", mTaskDetailInfoNew);
                        context.startActivity(mIntent);
                    }
                });
            }else if ( Util.IsNotNUll(bean.getHeadImage().trim()) && Utils.checkUrl(bean.getHeadImage().trim())) {
                ImageLoadManager.getLoaderInstace().disPlayNormalImg(bean.getHeadImage(), mHolderView.mIV_0,
                        R.drawable.icon_task_friend);
            } else {
                mHolderView.mIV_0.setImageResource(R.drawable.icon_task_friend);
            }

        }else{
            //listview
        if ( Util.IsNotNUll(bean.getHeadImage().trim()) && Utils.checkUrl(bean.getHeadImage().trim())) {
            ImageLoadManager.getLoaderInstace().disPlayNormalImg(bean.getHeadImage(), mHolderView.mIV_0,
                    R.drawable.icon_task_friend);
        } else {
            mHolderView.mIV_0.setImageResource(R.drawable.icon_task_friend);
        }
        }
        return convertView;
    }

    public class HoldView{
        private ImageView mIV_0;
        private TextView mTV_1;
        HoldView(){

        }
        HoldView(View view){
            mIV_0 = (ImageView)view.findViewById(R.id.iv_task_friend_icon);
            mTV_1 = (TextView)view.findViewById(R.id.tv_task_friend_name);
        }
    }

}
