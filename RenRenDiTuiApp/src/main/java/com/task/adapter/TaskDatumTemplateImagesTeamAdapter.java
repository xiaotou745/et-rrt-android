package com.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.renrentui.app.R;
import com.renrentui.resultmodel.TaskDatumControlBean;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.ViewHolderUtil;
import com.task.manager.TaskDatumTemplePIcManager;
import com.task.upload.Views.LoadingView;
import com.task.upload.bean.uploadPicBean;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/4 0004.
 * 任务资料模板中图片组适配器
 */
public class TaskDatumTemplateImagesTeamAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<TaskDatumControlBean> mData;
    private String str_userId;//用户id
    private String str_taskId;//任务id
    private String str_tag;//组的标签
    private int iTeam_type;//组的类型
    private int iTeam_num;//组号
    private int iShowContentType = 0;//0：编辑  1：展示
    private TaskDatumTemplePIcManager mTaskDatumTemplePIcManager = null;//拍照管理类

    public TaskDatumTemplateImagesTeamAdapter(Context con, ArrayList<TaskDatumControlBean> data, int iShowContentType ,
                                              String userId,String str_taskId,String tag,int iTeam_type,int iTeam_num){
        mContext = con;
        mData= data;
        this.str_userId = userId;
        str_tag = tag;
        this.iTeam_type = iTeam_type;
        this.iTeam_num = iTeam_num;
        this.iShowContentType = iShowContentType;
        this.str_taskId = str_taskId;
    }
    public void  setTaskDatumTemplePIcManager(TaskDatumTemplePIcManager mObj){
        if(mTaskDatumTemplePIcManager==null){
            mTaskDatumTemplePIcManager = mObj;
        }
    }
    public void setData(ArrayList<TaskDatumControlBean> data){
        if(mData==null){
            mData = new ArrayList<TaskDatumControlBean>();
        }else{
            mData.clear();
        }
        mData = data;
    }
    @Override
    public int getCount() {
        if(mData==null){
            return 0;
        }
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final  int position = i;
//        HolderView mHolderView = null;
//        if(view==null){
//            view = LayoutInflater.from(mContext).inflate(R.layout.item_team_images_layout,viewGroup,false);
//            mHolderView = new HolderView();
//            LinearLayout mLL_image = (LinearLayout)view.findViewById(R.id.linear_img);
//            LoadingView  mLoadingImage = (LoadingView)view.findViewById(R.id.iv_team_image_pic_item);
//            TextView mTV_image_reset = (TextView)view.findViewById(R.id.item_reset_tv);
//            TextView mTV_image_status = (TextView)view.findViewById(R.id.item_status_tv);
//            mLoadingImage.setTag(str_tag + String.valueOf("_"+iTeam_num+"_") + String.valueOf(position)+"_img");
//            mTV_image_reset.setTag(str_tag + String.valueOf("_"+iTeam_num+"_") + String.valueOf(position)+"_reset");
//            mTV_image_status.setTag(str_tag + String.valueOf("_"+iTeam_num+"_") + String.valueOf(position)+"_status");
//
//            mHolderView.mLL_image = mLL_image;
//            mHolderView.mImageView_images_pic = mLoadingImage;
//            mHolderView.mTV_image_reset = mTV_image_reset;
//            mHolderView.mTV_image_upload_status =mTV_image_status;
//            mHolderView.mTV_images_title = (TextView)view.findViewById(R.id.tv_team_images_title_item);
//            view.setTag(mHolderView);
//        }else {
//            mHolderView = (HolderView)view.getTag();
//        }
        if(view==null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_team_images_layout, viewGroup, false);
        }
        LinearLayout mLL_image = (LinearLayout)ViewHolderUtil.get(view,R.id.linear_img);
        LoadingView  mLoadingImage = (LoadingView)ViewHolderUtil.get(view, R.id.iv_team_image_pic_item);
        TextView mTV_image_reset = (TextView)ViewHolderUtil.get(view,R.id.item_reset_tv);
        TextView mTV_image_status = (TextView)ViewHolderUtil.get(view, R.id.item_status_tv);
        TextView mTV_image_title = (TextView)ViewHolderUtil.get(view, R.id.tv_team_images_title_item);

        //setTag
        mLoadingImage.setTag(str_tag + String.valueOf("_"+iTeam_num+"_") + String.valueOf(position)+"_img");
        mTV_image_reset.setTag(str_tag + String.valueOf("_"+iTeam_num+"_") + String.valueOf(position)+"_reset");
        mTV_image_status.setTag(str_tag + String.valueOf("_" + iTeam_num + "_") + String.valueOf(position) + "_status");

        TaskDatumControlBean bean = (TaskDatumControlBean) getItem(i);


        if(iShowContentType==1){
            //展示
            mTV_image_title.setText(bean.controlTitle);
            ImageLoadManager.getLoaderInstace().disPlayNormalImg(bean.controlValue,
                    mLoadingImage, R.drawable.pusher_logo);
        }else{
            //编辑
            mTV_image_title.setText(bean.defaultValue);
            mLoadingImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //调用拍照信息
                    if (mTaskDatumTemplePIcManager != null) {
                        uploadPicBean bean = new uploadPicBean();
                        bean.setUser_id(str_userId);
                        bean.setTask_id(str_taskId);
                        bean.setTag(str_tag);
                        bean.setTeam_type(String.valueOf(iTeam_type));
                        bean.setTeam_num(String.valueOf(iTeam_num));
                        bean.setTeam_position(String.valueOf(position));
                        mTaskDatumTemplePIcManager.showCameraDialog("img.jpg", bean, str_userId);
                    } else {
                        Toast.makeText(mContext, "相机错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return view;
    }

//    public class HolderView{
//        public LinearLayout mLL_image;
//        public LoadingView mImageView_images_pic;//图片
//        public TextView mTV_image_reset;//重新上传
//        public TextView mTV_image_upload_status;//上传装填
//        public TextView mTV_images_title;
//    }

}
