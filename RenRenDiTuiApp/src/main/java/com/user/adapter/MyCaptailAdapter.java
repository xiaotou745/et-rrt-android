package com.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.RSMyCaptailModel;
import com.user.activity.MyCapialDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/1/11 0011.
 * 我的资金明细适配器
 */
public class MyCaptailAdapter extends BaseAdapter {
    private Context context;
    private int iTypeCount =2;
    private int iTypeIndex = 0;
    private List<RSMyCaptailModel.DataList> data;

    public MyCaptailAdapter(Context con,int type){
        context = con;
        iTypeIndex = type;
    }
    public void setData(List<RSMyCaptailModel.DataList> mData){
        if(data!=null){
            data.clear();
        }
        data = mData;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return iTypeIndex;
    }

    @Override
    public int getViewTypeCount() {
        return iTypeCount;
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
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView mHolderView =null;
        final int index = position;
switch (iTypeIndex) {
    case 0:
        if (convertView == null || convertView.getTag(R.id.listview_multiple_type_seven_layout)==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_captail_1, parent, false);
            mHolderView = new HolderView(convertView);
            convertView.setTag(R.id.listview_multiple_type_seven_layout,mHolderView);
        } else {
            mHolderView = (HolderView) convertView.getTag(R.id.listview_multiple_type_seven_layout);
        }
        break;
    case 1:
        if (convertView == null  || convertView.getTag(R.id.listview_multiple_type_eight_layout)==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_captail_2, parent, false);
            mHolderView = new HolderView(convertView);
            convertView.setTag(R.id.listview_multiple_type_eight_layout,mHolderView);
        } else {
            mHolderView = (HolderView) convertView.getTag(R.id.listview_multiple_type_eight_layout);
        }
        break;

}
       final  RSMyCaptailModel.DataList dataBean = (RSMyCaptailModel.DataList)this.getItem(index);
        switch (iTypeIndex){
            case 0:
                //收入
                mHolderView.mTV_0.setText( Html.fromHtml(context.getResources().getString(R.string.captail_add, dataBean.getAmount())));
                mHolderView.mTV_1.setText(dataBean.getRecordTypeName());
                mHolderView.mTV_2.setText(dataBean.getOperateTime());
                break;
            case 1:
                //支出
                mHolderView.mTV_0.setText( Html.fromHtml(context.getResources().getString(R.string.captail_reduce, dataBean.getAmount())));
                mHolderView.mTV_1.setText(dataBean.getRecordTypeName());
                mHolderView.mTV_2.setText(dataBean.getOperateTime());
                break;
        }
        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.setClass(context, MyCapialDetailActivity.class);
                mIntent.putExtra("VO", dataBean);
                mIntent.putExtra("isAdd",iTypeIndex);
                context.startActivity(mIntent);
            }
        });

        return convertView;
    }
    class HolderView{
        private TextView mTV_0;
        private TextView mTV_1;
        private TextView mTV_2;
        HolderView(View view){
            mTV_0 = (TextView)view.findViewById(R.id.tv_captail_0);
            mTV_1 = (TextView)view.findViewById(R.id.tv_captail_1);
            mTV_2 = (TextView)view.findViewById(R.id.tv_captail_2);

        }

    }

}
