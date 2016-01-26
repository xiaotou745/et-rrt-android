package com.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.renrentui.app.R;

import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/1/15 0015.
 * 任务排序
 */
public class TaskSorAdapter extends BaseAdapter {
    private Context context;
    private String[] strings_sort ={"佣金最高","审核最快","预计用时最短","参数人数最多","发布时间最新"};
    int is_selection=0;//选中那个
    public TaskSorAdapter(Context con) {
        context = con;
    }
    public void setIs_selection(int index){
        is_selection = index;
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return strings_sort.length;
    }

    @Override
    public Object getItem(int position) {
        return strings_sort[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task_order_sort_layout, parent,false);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.mIV_0.setVisibility(View.VISIBLE);
        viewholder.mTV_1.setText(getItem(position).toString());
        if(position==this.getCount()){
            viewholder.mView_2.setVisibility(View.GONE);
        }else{
            viewholder.mView_2.setVisibility(View.VISIBLE);
        }
        if(is_selection==(position+1)){
            viewholder.mTV_1.setTextColor(context.getResources().getColor(R.color.blue_bg));
            viewholder.mIV_0.setVisibility(View.VISIBLE);
        }else{
            viewholder.mTV_1.setTextColor(context.getResources().getColor(R.color.tv_order_color_2));
            viewholder.mIV_0.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    public class ViewHolder{
        private ImageView mIV_0;//图标
        private TextView mTV_1;//金额
       private View mView_2;

        public ViewHolder(View view) {
            mIV_0 = (ImageView)view.findViewById(R.id.iv_task_sort_0);
            mTV_1 = (TextView)view.findViewById(R.id.tv_task_sort_0);
            mView_2 =view.findViewById(R.id.line_0);
        }
    }
}
