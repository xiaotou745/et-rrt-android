package com.user.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.MyMessageContentBean;
import com.renrentui.util.TimeUtils;
import com.user.activity.MyMessageDetails;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/10 0010.
 * 消息数据适配器
 */
public class MyMessageAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<MyMessageContentBean> data;
    public MyMessageAdapter(Context context){
        this.context = context;
    }
    public void setMessageData(ArrayList<MyMessageContentBean> data){
        if(this.data==null){
            this.data = new ArrayList<MyMessageContentBean>();
        }
        this.data=data;
        this.notifyDataSetChanged();
    }
    public void refreshDataView(){
        if(data!=null && data.size()>0){
            this.notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        if(data==null){
            return 0;
        }else {
            return data.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  int position = i;
        ViewHolder mViewHolder =null;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_mymessage_layout,viewGroup,false);
            mViewHolder = new ViewHolder(view);
            view.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder)view.getTag();
        }
        final MyMessageContentBean bean = (MyMessageContentBean)this.getItem(position);
        if(bean.hasRead){
            mViewHolder.mTV_content.setTextColor(context.getResources().getColor(R.color.tv_order_color_4));
        }else{
            mViewHolder.mTV_content.setTextColor(context.getResources().getColor(R.color.tv_order_color_1));
        }
        mViewHolder.mTV_time.setTextColor(context.getResources().getColor(R.color.tv_order_color_4));
        mViewHolder.mTV_title.setText(bean.title);
        mViewHolder.mTV_content.setText(bean.msg);
        mViewHolder.mTV_new_title.setText(bean.createDate);
        mViewHolder.mTV_new_title.setText(TimeUtils.StringPattern(bean.createDate,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));
        mViewHolder.mTV_time.setText(TimeUtils.StringPattern(bean.createDate,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm"));

       view.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               Intent mIntent = new Intent();
               mIntent.setClass(context, MyMessageDetails.class);
               mIntent.putExtra("content", bean.msg);
               mIntent.putExtra("time", bean.createDate);
               mIntent.putExtra("id", bean.id);
               bean.hasRead = true;
               data.set(position,bean);
               context.startActivity(mIntent);
           }
       });
        return view;
    }
    public  class ViewHolder{
        private TextView mTV_title;
        private TextView mTV_time;
        private TextView mTV_content;
        private  TextView mTV_new_title;

        public ViewHolder(View view) {
            this.mTV_title = (TextView)view.findViewById(R.id.tv_title);
            this.mTV_time = (TextView)view.findViewById(R.id.tv_time);
            this.mTV_content = (TextView)view.findViewById(R.id.tv_content);
            mTV_new_title = (TextView)view.findViewById(R.id.tv_new_time);
        }
    }

}
