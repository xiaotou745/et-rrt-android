package com.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.TaskSpecBeanInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/1 0001.
 * 任务流程适配器
 */
public class TaskFlowPathAdapter extends BaseAdapter{
    public Context mContext;
    private ArrayList<String> data;
    public TaskFlowPathAdapter(Context con,ArrayList<String> data) {
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
        return 3;
    }

    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return  0;
        } else if(position>0 && position==getCount()-1){
            return 2;
        }else{
            return  1;
        }

    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        HoldeViewTop mTopView = null ;
        HoldeViewMiddle mMiddleView=null ;
        HoldeViewEnd mEndView = null;
        int type = getItemViewType(i);
        String iOrderNum = String.valueOf(i+1);
        if(convertView==null){
            switch (type){
                case 0:
                    mTopView = new HoldeViewTop();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task_flowpath_top_layout,viewGroup,false);
                    mTopView.tv_task_flowpath_content_top = (TextView)convertView.findViewById(R.id.tv_task_flowpath_content_top);
                    mTopView.tv_order_num = (TextView)convertView.findViewById(R.id.tv_order_num);
                    convertView.setTag(mTopView);
                    break;
                case 1:
                    mMiddleView = new HoldeViewMiddle();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task_flowpath_middle_layout,viewGroup,false);
                    mMiddleView.tv_task_flowpath_content_middle = (TextView)convertView.findViewById(R.id.tv_task_flowpath_content_middle);
                    mMiddleView.tv_order_num = (TextView)convertView.findViewById(R.id.tv_order_num);
                    convertView.setTag(mMiddleView);
                    break;
                case 2:
                    mEndView = new HoldeViewEnd();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task_flowpath_end_layout,viewGroup,false);
                    mEndView.tv_task_flowpath_content_end = (TextView)convertView.findViewById(R.id.tv_task_flowpath_content_end);
                    mEndView.tv_order_num = (TextView)convertView.findViewById(R.id.tv_order_num);
                    convertView.setTag(mEndView);
                    break;
            }
        }else{
            switch (type) {
                case 0:
                    mTopView = (HoldeViewTop)convertView.getTag();
                    break;
                case 1:
                    mMiddleView = (HoldeViewMiddle)convertView.getTag();
                    break;
                case 2:
                    mEndView = (HoldeViewEnd)convertView.getTag();
                    break;
            }
        }
        //数据展示
        String strContent = (String)this.getItem(i);
        switch (type){
            case 0:
                mTopView.tv_order_num.setText(iOrderNum);
                mTopView.tv_task_flowpath_content_top.setText(strContent);
                break;
            case 1:
                mMiddleView.tv_order_num.setText(iOrderNum);
                mMiddleView.tv_task_flowpath_content_middle.setText(strContent);
                break;
            case 2:
                mEndView.tv_order_num.setText(iOrderNum);
                mEndView.tv_task_flowpath_content_end.setText(strContent);
                break;
        }
        return convertView;

    }
    public class HoldeViewTop{
        public TextView tv_order_num;
        public TextView tv_task_flowpath_content_top;
    }
    public class HoldeViewMiddle{
         public TextView tv_order_num;
         public TextView tv_task_flowpath_content_middle;
    }
    public class HoldeViewEnd{
         public TextView tv_order_num;
         public TextView tv_task_flowpath_content_end;
    }
}
