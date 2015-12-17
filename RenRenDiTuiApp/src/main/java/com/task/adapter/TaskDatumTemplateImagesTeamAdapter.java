package com.task.adapter;

import android.content.Context;
import android.text.TextUtils;
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
import com.renrentui.db.Bean.TaskTempleDBBean;
import com.renrentui.db.TaskTempleDBManager;
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
    private TaskTempleDBManager mTaskTempleManager;
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
        mTaskTempleManager = new TaskTempleDBManager(con);
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
        if(view==null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_team_images_layout, viewGroup, false);
        }
        LinearLayout mLL_image = ViewHolderUtil.get(view,R.id.linear_img);
        LoadingView  mLoadingImage = ViewHolderUtil.get(view, R.id.item_img_iv);
        TextView mTV_image_reset = ViewHolderUtil.get(view,R.id.item_reset_tv);
        TextView mTV_image_status = ViewHolderUtil.get(view, R.id.item_status_tv);
        TextView mTV_image_title = ViewHolderUtil.get(view, R.id.tv_team_images_title_item);

        //setTag
        mLoadingImage.setTag(str_tag + String.valueOf("_"+iTeam_num+"_") + String.valueOf(position)+"_img");
        mTV_image_reset.setTag(str_tag + String.valueOf("_"+iTeam_num+"_") + String.valueOf(position)+"_reset");
        mTV_image_status.setTag(str_tag + String.valueOf("_" + iTeam_num + "_") + String.valueOf(position) + "_status");

        final TaskDatumControlBean taskDean = (TaskDatumControlBean) getItem(i);


        if(iShowContentType==1){
            //展示
            mTV_image_title.setText(taskDean.controlTitle);
            if(!TextUtils.isEmpty(taskDean.controlValue)){
                mLoadingImage.setLoadingVisibility(View.INVISIBLE);
            }else{
                mLoadingImage.setLoadingVisibility(View.VISIBLE);
            }
            ImageLoadManager.getLoaderInstace().disPlayNormalImg(taskDean.controlValue,
                    mLoadingImage, R.drawable.pusher_logo);
        }else{
            //编辑
            //添加空数据
            TaskTempleDBBean beanD = new TaskTempleDBBean();
            beanD.setUSER_ID(str_userId);
            beanD.setTASK_ID(str_taskId);
            beanD.setTEAM_TYPE(String.valueOf(iTeam_type));
            beanD.setTEAM_NUM(String.valueOf(iTeam_num));
            beanD.setTEAM_NUM_INDEX(String.valueOf(position));
            beanD.setTEAM_CONTENT_TYPE(taskDean.controlTypeId);
            beanD.setTEAM_CONTENT_KEY(taskDean.controlKey);
            beanD.setTEAM_CONTENT_VALUE("");
            mTaskTempleManager.AddTaskTemplateEmptyValue(beanD);

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
                        bean.setControlKey(taskDean.controlKey);
                        mTaskDatumTemplePIcManager.showCameraDialog("img.jpg", bean, str_userId);
                    } else {
                        Toast.makeText(mContext, "相机错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return view;
    }
}
