package com.task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.renrentui.app.R;
import com.renrentui.resultmodel.TaskDatumControlBean;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.ViewHolderUtil;
import com.renrentui.view.SquareImageView;
import com.task.manager.TaskDatumTemplePIcManager;
import com.task.upload.Views.LoadingView;
import com.task.upload.bean.uploadPicBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/4 0004.
 * 任务资料模板中多图片组适配器
 */
public class TaskDatumTemplateMultipleImagesTeamAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<TaskDatumControlBean> mData;
    private String str_userId;//用户id
    private String str_taskId;//任务id
    private String str_tag;//组的标签
    private int iTeam_type;//组的类型
    private int iTeam_num;//组号
    private int iShowContentType = 0;//0：编辑  1：展示
    private TaskDatumTemplePIcManager mTaskDatumTemplePIcManager = null;//拍照管理类

    public TaskDatumTemplateMultipleImagesTeamAdapter(Context con, ArrayList<TaskDatumControlBean> data, int iShowContentType ,
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
      //  HolderView mHolderView = null;
        final  int position = i;
        if(view==null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_multiple_team_images_layout, viewGroup, false);
        }
        LinearLayout mLL_image = ViewHolderUtil.get(view, R.id.linear_img);
        LoadingView mLoadingImage = ViewHolderUtil.get(view, R.id.item_img_iv);
        TextView mTV_image_reset = ViewHolderUtil.get(view,R.id.item_reset_tv);
        TextView mTV_image_status = ViewHolderUtil.get(view, R.id.item_status_tv);

        //setTag
        mLoadingImage.setTag(str_tag + String.valueOf("_"+iTeam_num+"_") + String.valueOf(position)+"_img");
        mTV_image_reset.setTag(str_tag + String.valueOf("_"+iTeam_num+"_") + String.valueOf(position)+"_reset");
        mTV_image_status.setTag(str_tag + String.valueOf("_"+iTeam_num + "_") + String.valueOf(position) + "_status");

       final  TaskDatumControlBean taskBean = (TaskDatumControlBean) getItem(i);

        if(iShowContentType==1){
            //展示
            ImageLoadManager.getLoaderInstace().disPlayNormalImg(taskBean.controlValue,
                    mLoadingImage, R.drawable.pusher_logo);
        }else{
            //编辑
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
                        bean.setControlKey(taskBean.controlKey);
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
//        public SquareImageView mImageView_multpile_images_pic;
//    }

}
