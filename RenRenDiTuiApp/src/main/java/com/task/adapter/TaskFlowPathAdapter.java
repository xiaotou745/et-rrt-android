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
    private ArrayList<TaskSpecBeanInfo> data;
    public TaskFlowPathAdapter(Context con,ArrayList<TaskSpecBeanInfo> data) {
        this.mContext = con;
        this.data =data;
    }
    public  void setTaskData(ArrayList<TaskSpecBeanInfo> ndata){
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
        return 4;
    }

    @Override
    public int getItemViewType(int position) {

        if(getCount()==1 && position==0) {
            return 0;
        }else if(position==0){
            return  1;
        } else if(position>0 && position==getCount()-1){
            return 2;
        }else{
            return  3;
        }

    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        HoldeViewSingle mSingleView = null;
        HoldeViewTop mTopView = null ;
        HoldeViewMiddle mMiddleView=null ;
        HoldeViewEnd mEndView = null;
        int type = getItemViewType(i);
        String iOrderNum = String.valueOf(i+1);
        switch (type){
            case 0:
                //顶部
                if(convertView==null || convertView.getTag(R.id.listview_multiple_type_first_layout)==null ){
                    mSingleView= new HoldeViewSingle();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task_flow_single_layout,viewGroup,false);
                    mSingleView.tv_task_flowpath_content_single = (TextView)convertView.findViewById(R.id.tv_task_flowpath_content_single);
                    mSingleView.tv_order_num = (TextView)convertView.findViewById(R.id.tv_order_num);
                    convertView.setTag(R.id.listview_multiple_type_first_layout,mSingleView);
                }else{
                    mSingleView = (HoldeViewSingle)convertView.getTag(R.id.listview_multiple_type_first_layout);
                }
                break;
            case 1:
                //第一个
                if(convertView==null || convertView.getTag(R.id.listview_multiple_type_second_layout)==null ){
                    mTopView = new HoldeViewTop();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task_flowpath_top_layout,viewGroup,false);
                    mTopView.tv_task_flowpath_content_top = (TextView)convertView.findViewById(R.id.tv_task_flowpath_content_top);
                    mTopView.tv_order_num = (TextView)convertView.findViewById(R.id.tv_order_num);
                    convertView.setTag(R.id.listview_multiple_type_second_layout,mTopView);
                }else{
                    mTopView = (HoldeViewTop)convertView.getTag(R.id.listview_multiple_type_second_layout);
                }
                break;
            case 2:
                //底部
                if(convertView==null || convertView.getTag(R.id.listview_multiple_type_three_layout)==null ){
                    mEndView= new HoldeViewEnd();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task_flowpath_end_layout,viewGroup,false);
                    mEndView.tv_task_flowpath_content_end = (TextView)convertView.findViewById(R.id.tv_task_flowpath_content_end);
                    mEndView.tv_order_num = (TextView)convertView.findViewById(R.id.tv_order_num);
                    convertView.setTag(R.id.listview_multiple_type_three_layout,mSingleView);
                }else{
                    mEndView = (HoldeViewEnd)convertView.getTag(R.id.listview_multiple_type_three_layout);
                }
                break;
            case 3:
//                //中部
                if(convertView==null || convertView.getTag(R.id.listview_multiple_type_four_layout)==null ){
                    mMiddleView = new HoldeViewMiddle();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_task_flowpath_middle_layout,viewGroup,false);
                    mMiddleView.tv_task_flowpath_content_middle = (TextView)convertView.findViewById(R.id.tv_task_flowpath_content_middle);
                    mMiddleView.tv_order_num = (TextView)convertView.findViewById(R.id.tv_order_num);
                    convertView.setTag(R.id.listview_multiple_type_four_layout,mMiddleView);
                }else{
                    mMiddleView = (HoldeViewMiddle)convertView.getTag(R.id.listview_multiple_type_four_layout);
                }
                break;
        }
        //数据展示
        TaskSpecBeanInfo bean = (TaskSpecBeanInfo)this.getItem(i);
        switch (type){
            case 0:
                mSingleView.tv_order_num.setText(iOrderNum);
                mSingleView.tv_task_flowpath_content_single.setText(bean.getContent());
                break;
            case 1:
                mTopView.tv_order_num.setText(iOrderNum);
                mTopView.tv_task_flowpath_content_top.setText(bean.getContent());
                break;
            case 3:
                mMiddleView.tv_order_num.setText(iOrderNum);
                mMiddleView.tv_task_flowpath_content_middle.setText(bean.getContent());
                break;
            case 2:
                mEndView.tv_order_num.setText(iOrderNum);
                mEndView.tv_task_flowpath_content_end.setText(bean.getContent());
                break;
        }
        return convertView;

    }
    public class HoldeViewSingle{
        public TextView tv_order_num;
        public TextView tv_task_flowpath_content_single;
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
