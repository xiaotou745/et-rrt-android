package com.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.renrentui.app.R;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.activity.WebViewActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/1 0001.
 * 任务详情连接
 */
public class TaskLinksAdapter extends BaseAdapter{
    public Context mContext;
    private ArrayList<String> data;
    public TaskLinksAdapter(Context con, ArrayList<String> data) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task_link_layout,viewGroup,false);
            mHolderView.tv_task_link_content = (TextView)convertView.findViewById(R.id.tv_task_link_content);
            convertView.setTag(mHolderView);

        }else{
            mHolderView = (HoldeView)convertView.getTag();
        }
        //数据展示
        final String strContent = (String)this.getItem(i);
        mHolderView.tv_task_link_content.setText("详情页链接");
        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(strContent) && !Utils.checkUrl(strContent)){
                    ToastUtil.show(mContext,"详情地址错误!"+strContent);
                }else {
                    Intent mIntent = new Intent();
                    mIntent.setClass(mContext, WebViewActivity.class);
                    mIntent.putExtra(WebViewActivity.STR_CONTENT_URL, strContent);
                    mIntent.putExtra(WebViewActivity.STR_TITLE,"任务详情");
                    mContext.startActivity(mIntent);
                }
            }
        });
        return convertView;

    }
    public class HoldeView{
        public TextView tv_task_link_content;
    }

}
