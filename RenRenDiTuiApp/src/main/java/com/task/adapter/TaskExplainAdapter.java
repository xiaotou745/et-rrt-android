package com.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renrentui.app.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/1 0001.
 * 任务流程补充说明
 */
public class TaskExplainAdapter extends BaseAdapter{
    public Context mContext;
    private ArrayList<String> data;
    public TaskExplainAdapter(Context con, ArrayList<String> data) {
        this.mContext = con;
        this.data =data;
    }
    public  void setTaskData(ArrayList<String> ndata){
        data = ndata;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(data==null){
            return 0;
        }else {
            return  data.size();
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
    public int getViewTypeCount() {
        return 1;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        HoldeView mHolderView = null ;
        if(convertView==null){
            mHolderView = new HoldeView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task_explain_layout,viewGroup,false);
            mHolderView.tv_task_explain_content = (TextView)convertView.findViewById(R.id.tv_task_explain_content);
            convertView.setTag(mHolderView);

        }else{
            mHolderView = (HoldeView)convertView.getTag();
        }
        //数据展示
        String strContent = (String)this.getItem(i);
        mHolderView.tv_task_explain_content.setText(strContent);
        return convertView;

    }
    public class HoldeView{
        public TextView tv_task_explain_content;
    }

}
