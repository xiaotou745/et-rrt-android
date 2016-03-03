package com.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.renrentui.app.R;
import com.renrentui.tools.Util;
import com.renrentui.util.ImageLoadManager;
import com.task.activity.ShowTaskMateriaBitPic;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/2 0002.
 * 任务材料图片适配器
 */
public class GriveiwTaskPicAdapter extends BaseAdapter {
    public Context context;
    private ArrayList<String> picData = new ArrayList<String>();
    GriveiwTaskPicAdapter(Context con,ArrayList<String> data){
    context = con;
        picData = data;
    }
    @Override
    public int getCount() {
        if(picData==null){
            return 0;
        }else {
            return  picData.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return picData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder = new ViewHolder();
        final int position = i;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_task_pic_layout,viewGroup,false);
            mViewHolder.mImageView = (ImageView)view.findViewById(R.id.img_pic);
            view.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder)view.getTag();
        }
        if (Util.IsNotNUll(getItem(i).toString())) {
            ImageLoadManager.getLoaderInstace().disPlayNormalImg(getItem(i).toString(), mViewHolder.mImageView,
                    R.drawable.pusher_logo);
        } else {
            mViewHolder.mImageView.setImageResource(R.drawable.pusher_logo);
        }
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context,ShowTaskMateriaBitPic.class);
                mIntent.putExtra(ShowTaskMateriaBitPic.STR_STATE_POSIION,position);
                mIntent.putExtra(ShowTaskMateriaBitPic.STR_STATE_URLS,picData);
                context.startActivity(mIntent);
            }
        });
        return view;
    }
    public  class ViewHolder{
        public ImageView mImageView;

    }
}
