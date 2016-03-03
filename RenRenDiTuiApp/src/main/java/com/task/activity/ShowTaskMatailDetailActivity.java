package com.task.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.db.Bean.TaskTempleDBBean;
import com.renrentui.db.TaskTempleDBManager;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQTaskDatumModel;
import com.renrentui.requestmodel.RQTaskDatumSubmitModel;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.RSTaskDatum;
import com.renrentui.resultmodel.RSTaskDatumnSubmitModel;
import com.renrentui.resultmodel.TaskBeanInfo;
import com.renrentui.resultmodel.TaskDatumControlBean;
import com.renrentui.resultmodel.TaskDatumTemplateGroup;
import com.renrentui.resultmodel.TaskDatumTempletParamsBean;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.TimeUtils;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.UIHelper;
import com.renrentui.util.Utils;
import com.renrentui.util.ViewHolderUtil;
import com.task.adapter.TaskDatumTemplateImagesTeamAdapter;
import com.task.adapter.TaskDatumTemplateMultipleImagesTeamAdapter;
import com.task.adapter.TaskDatumTemplateTextsTeamAdapter;
import com.task.manager.PhotoManager;
import com.task.manager.TaskDatumTemplePIcManager;
import com.task.service.CancleDialog;
import com.task.service.SubmitSuccessDialog;
import com.task.service.SubmitSuccessDialog.ExitDialogListener;
import com.task.upload.UploadService;
import com.task.upload.Views.LoadingView;
import com.task.upload.bean.uploadPicBean;
import com.task.upload.interfaces.TaskTempleUploadPicInterface;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import base.BaseActivity;

/**
 * Created by Administrator on 2015/12/3 0003.
 * 任务资料详情页面
 */
public class ShowTaskMatailDetailActivity extends BaseActivity implements
        View.OnClickListener, INodata {
    public Activity mAct;
    private String userId;//用户id
    private String taskId;//任务did
    private String taskName;//任务名称
    private String taskDatumId;//资料id
    private String ctId;//地推关系id

    private ImageView icon_pusher;// 任务发布商logo
    private LinearLayout ll_amount;//任务单价
    private TextView tv_Amount;// 任务单价

    private TextView tv_pusher_taskName;// 任务名称
    private TextView mIV_pusher_type_flag;//任务类型
    //private TextView tv_pusher_type_content;//任务类型及内容
    private TextView tv_task_examine;//审核
    private TextView tv_deadline_time;// 截止日期
    private TextView tv_task_tel;//咨询电话

    //区域
    private LinearLayout ll_texts_area;//文字组区域
    private LinearLayout ll_images_area;//图片组区域
    private LinearLayout ll_multiple_images_area;//多组图片区域

    private RSTaskDatum mRSTaskDatum;//

    //view  tag(tag_team_id)(texts_team_id,images_team_id,multiple_images_team_id)
    public String texts_tag = "texts";
    public String images_tag = "images";
    public String multiple_images = "multiple_images";
    public  AtomicInteger texts_team = new AtomicInteger(-1) ;
    public AtomicInteger images_team = new AtomicInteger(-1);
    public AtomicInteger multiple_images_team = new AtomicInteger(-1);
    public LinearLayout.LayoutParams mLayoutParams = null;
    public String str_hotPhone = "";//电话

    //文字组
    public ArrayList<TaskDatumTempletParamsBean> mListTaskDatumTemplet_Texts_data=new  ArrayList<TaskDatumTempletParamsBean>();
    //图片组
    public ArrayList<TaskDatumTempletParamsBean> mListTaskDatumTemplet_Images_data=new  ArrayList<TaskDatumTempletParamsBean>();
    //多图片组
    public ArrayList<TaskDatumTempletParamsBean> mListTaskDatumTemplet_multiple_images_data=new  ArrayList<TaskDatumTempletParamsBean>();

    //数据集合
    public ArrayList<TaskDatumTempletParamsBean> mListTaskDatumTemplet_data=new  ArrayList<TaskDatumTempletParamsBean>();


    //=====================获取任务模板========
    private RQHandler<RSTaskDatum> rqHandler_getTaskDatumn = new RQHandler<>(
            new IRqHandlerMsg<RSTaskDatum>() {

                @Override
                public void onBefore() {
                    hideProgressDialog();
                }

                @Override
                public void onNetworknotvalide() {
                    ShowTaskMatailDetailActivity.this.onNodata(
                            ResultMsgType.NetworkNotValide, 0, 0,"",null);
                }

                @Override
                public void onSuccess(RSTaskDatum t) {
                    mRSTaskDatum = t;
                    initTaskData(mRSTaskDatum.data.task);
                    initTaskDatumTemplet(mRSTaskDatum.data.templateGroup);
                    hideProgressDialog();
                }

                @Override
                public void onSericeErr(RSTaskDatum t) {
                    ShowTaskMatailDetailActivity.this.onNodata(
                            ResultMsgType.ServiceErr, 0, R.string.every_no_data_error,"",ShowTaskMatailDetailActivity.this);
                }

                @Override
                public void onSericeExp() {
                    ShowTaskMatailDetailActivity.this.onNodata(
                            ResultMsgType.ServiceExp, 0,R.string.every_no_data_error,"",
                            ShowTaskMatailDetailActivity.this);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_datum_details_layout);
        super.init();
        userId = Utils.getUserDTO(context).data.userId;
        mAct = ShowTaskMatailDetailActivity.this;
        taskId = getIntent().getStringExtra("taskId");
        taskDatumId = getIntent().getStringExtra("taskDatumId");
        taskName = getIntent().getStringExtra("taskName");
        ctId = getIntent().getStringExtra("ctId");//地推关系id
        initControl();
       // initViewValue();
        getTaskDatumnTemplement();
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.tv_task_tel:
                //咨询电话
                Utils.callPhone(context, str_hotPhone.trim());
                break;
        }

    }
    @Override
    public void onNoData() {
        getTaskDatumnTemplement();
    }
    /**
     * 初始化view
     */
    private void initControl() {
        if(mIV_title_left!=null){
            mIV_title_left.setVisibility(View.VISIBLE);
            mIV_title_left.setOnClickListener(this);
        }
        if(mTV_title_content!=null){
            mTV_title_content.setText("资料详情");
        }

        icon_pusher = (ImageView) findViewById(R.id.icon_pusher);
        ll_amount = (LinearLayout) findViewById(R.id.ll_amount);
        tv_Amount = (TextView) findViewById(R.id.tv_amount);
        tv_pusher_taskName = (TextView) findViewById(R.id.tv_pusher_taskName);
        mIV_pusher_type_flag = (TextView)findViewById(R.id.iv_flag);
        //tv_pusher_type_content = (TextView) findViewById(R.id.tv_pusher_taskType_content);

        tv_task_examine = (TextView) findViewById(R.id.tv_task_examine);
        tv_deadline_time = (TextView) findViewById(R.id.tv_deadline_time);
        tv_task_tel = (TextView) findViewById(R.id.tv_task_tel);
        ll_texts_area = (LinearLayout)findViewById(R.id.ll_texts_area);
        ll_images_area = (LinearLayout)findViewById(R.id.ll_images_area);
        ll_multiple_images_area = (LinearLayout)findViewById(R.id.ll_multiple_images_area);
//        layout_back.setOnClickListener(this);
    }

//    /**
//     * 初始化view 默认值
//     * @return
//     */
//    private  void initViewValue(){
//        mTV_title_content.setText(taskName);
//    }

    /**
     * 获取模板信息
     */
    private void  getTaskDatumnTemplement(){
        showProgressDialog();
        ApiUtil.Request(new RQBaseModel<RQTaskDatumModel, RSTaskDatum>(
                context, new RQTaskDatumModel(userId, taskId,taskDatumId),
                new RSTaskDatum(), ApiNames.获取资料模板或模板详情.getValue(),
                RequestType.POST, rqHandler_getTaskDatumn));
    }

    /**
     * 初始化任务的基本信息     *
     */
    private  void initTaskData(TaskBeanInfo taskBean){
        //头像
        if (Util.IsNotNUll(taskBean.logo)  && Utils.checkUrl(taskBean.logo)) {
            ImageLoadManager.getLoaderInstace().disPlayNormalImg(taskBean.logo,
                    icon_pusher, R.drawable.pusher_logo);
        } else {
            icon_pusher.setImageResource(R.drawable.pusher_logo);
        }
        tv_Amount.setText(taskBean.getAmount());
        ll_amount.setVisibility(View.VISIBLE);

        tv_pusher_taskName.setText(taskBean.taskTitle);

//        if(taskBean.taskType==1){
////签约
//            mIV_pusher_type_flag.setImageResource(R.drawable.team_qianyue);
//
//        }else if(taskBean.taskType==2){
//            //分享
//            mIV_pusher_type_flag.setImageResource(R.drawable.team_share);
//        }else if(taskBean.taskType==3){
//            // 下载
//            mIV_pusher_type_flag.setImageResource(R.drawable.team_down);
//        }else{
//            mIV_pusher_type_flag.setImageResource(R.drawable.team_qianyue);
//        }
        mIV_pusher_type_flag.setVisibility(View.VISIBLE);
        mIV_pusher_type_flag.setText(taskBean.tagName);
        if(!TextUtils.isEmpty(taskBean.tagColorCode)){
            mIV_pusher_type_flag.setBackgroundColor(Color.parseColor(taskBean.tagColorCode));
        }

        //简介
//        SpannableStringBuilder style = null;
//        switch (taskBean.taskType){
//            case 1:
//                //签约
//                style =  UIHelper.setStyleColorByColor(context, taskBean.taskTypeName.toString(), taskBean.taskGeneralInfo.toString(), R.color.white, R.color.tv_bg_color_1);
//                break;
//            case 2:
//                //分享
//                style =  UIHelper.setStyleColorByColor(context,taskBean.taskTypeName.toString(),taskBean.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_3);
//                break;
//            case 3:
//                //下载
//                style =  UIHelper.setStyleColorByColor(context,taskBean.taskTypeName.toString(),taskBean.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_2);
//                break;
//            default:
//                style =  UIHelper.setStyleColorByColor(context,taskBean.taskTypeName.toString(),taskBean.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_1);
//                break;
//        }
//
//        tv_pusher_type_content.setText(style);

        //审核
        tv_task_examine.setText(context.getResources().getString(R.string.task_detail_examine_format, taskBean.auditCycle));
        tv_task_examine.setVisibility(View.VISIBLE);
        //截止日期
        tv_deadline_time.setText(context.getResources().getString(R.string.task_detail_dealtime_format, TimeUtils.StringPattern(taskBean.endTime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd")));
        tv_deadline_time.setVisibility(View.VISIBLE);

        //tel
        if(TextUtils.isEmpty(taskBean.hotLine)){
            tv_task_tel.setVisibility(View.INVISIBLE);
        }else{
            tv_task_tel.setVisibility(View.VISIBLE);
            tv_task_tel.setText(Html.fromHtml(context.getResources().getString(R.string.task_detail_tel_format, taskBean.hotLine)));
            tv_task_tel.setOnClickListener(this);
            str_hotPhone =  taskBean.hotLine;
        }
    }

    /**
     * 初始化任务模板详情
     * @param templateGroups
     */
    private void initTaskDatumTemplet(ArrayList<TaskDatumTemplateGroup> templateGroups){
        if(templateGroups==null || templateGroups.size()==0){
            return ;
        }else{
            if(mLayoutParams==null){
                mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
            }

            //展示数据
            int isize = templateGroups.size();
            for(int i=0;i<isize;i++){
                TaskDatumTemplateGroup mTaskDatumTemplateGroupBean = templateGroups.get(i);
                int iType = mTaskDatumTemplateGroupBean.groupType;
                String str_title = mTaskDatumTemplateGroupBean.title;
                switch (iType){
                    case 1:
                        //文字组
                        showTaskDatumTextsTemplet(iType,str_title, mTaskDatumTemplateGroupBean.controlList);
                        break;
                    case 2:
                       //图片组
                        showTaskDatumImagesTemplet(iType,str_title,mTaskDatumTemplateGroupBean.controlList);
                        break;
                    case 3:
                        //多组图片组
                        showTaskDatumMultipeImagesTemplet(iType,str_title,mTaskDatumTemplateGroupBean.controlList);
                        break;
                }
            }
        }

    }

    /**
     *  文字组模板
     * @param title
     * @param controlDataList
     */
    private void showTaskDatumTextsTemplet(int team_type,String title,ArrayList<TaskDatumControlBean> controlDataList){
        if(controlDataList==null || controlDataList.size()<=0){
            return;
        }else{

             texts_team.getAndIncrement();
            if(ll_texts_area.getVisibility()!=View.VISIBLE){
                ll_texts_area.setVisibility(View.VISIBLE);
            }
            //展示数据
            View Texts_view = LayoutInflater.from(context).inflate(R.layout.team_texts_layout,null);
            TextView mTexts_title = (TextView)Texts_view.findViewById(R.id.tv_team_texts_title);
            ListView mTextsListView = (ListView)Texts_view.findViewById(R.id.ll_team_texts_data);
            mTexts_title.setText(title);
            mTextsListView.setAdapter(new TaskDatumTemplateTextsTeamAdapter(context, controlDataList, 1, userId, taskId, texts_tag, team_type, texts_team.get()));
            ll_texts_area.addView(Texts_view, mLayoutParams);
            View line_view= new View(context);
            line_view.setBackgroundColor(context.getResources().getColor(R.color.line_bg_color));
            line_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    2));
            ll_texts_area.addView(line_view);
        }
    }
    /**
     *  图片组模板
     * @param title
     * @param controlDataList
     */
    private void showTaskDatumImagesTemplet(int team_type,String title,ArrayList<TaskDatumControlBean> controlDataList){
        if(controlDataList==null || controlDataList.size()<=0){
            return;
        }else{

            images_team.getAndIncrement();
            if(ll_images_area.getVisibility()!=View.VISIBLE){
                ll_images_area.setVisibility(View.VISIBLE);
            }
            //展示数据
            View Images_view = LayoutInflater.from(context).inflate(R.layout.team_images_layout,null);
            TextView mImages_title = (TextView)Images_view.findViewById(R.id.tv_team_images_title);
            ListView mImagesListView = (ListView)Images_view.findViewById(R.id.ll_team_images_content);
            mImages_title.setText(title);
            TaskDatumTemplateImagesTeamAdapter mImagesAdapter= new TaskDatumTemplateImagesTeamAdapter(context,controlDataList,1,userId,taskId,images_tag, team_type, texts_team.get());
            mImagesListView.setAdapter(mImagesAdapter);
            ll_images_area.addView(Images_view, mLayoutParams);
            View line_view= new View(context);
            line_view.setBackgroundColor(context.getResources().getColor(R.color.line_bg_color));
            line_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    2));
            ll_images_area.addView(line_view);
        }
    }
    /**
     *  多组图片组模板
     * @param title
     * @param controlDataList
     */
    private void showTaskDatumMultipeImagesTemplet(int team_type,String title,ArrayList<TaskDatumControlBean> controlDataList){
        if(controlDataList==null || controlDataList.size()<=0){
            return;
        }else{

            multiple_images_team.getAndIncrement();
            if(ll_multiple_images_area.getVisibility()!=View.VISIBLE){
                ll_multiple_images_area.setVisibility(View.VISIBLE);
            }
            //展示数据
            View Multiple_images_view = LayoutInflater.from(context).inflate(R.layout.team_multiple_imges_layout,null);
            TextView mMultiple_images_title = (TextView)Multiple_images_view.findViewById(R.id.gv_multipe_team_images_title);
            GridView mMultiple_images_gridview = (GridView)Multiple_images_view.findViewById(R.id.gv_multipe_team_images_content);
            mMultiple_images_title.setText(title);
            TaskDatumTemplateMultipleImagesTeamAdapter multipleImagesTeamAdapter = new TaskDatumTemplateMultipleImagesTeamAdapter(context,controlDataList,1,userId,taskId,multiple_images,team_type,multiple_images_team.get());
            mMultiple_images_gridview.setAdapter(multipleImagesTeamAdapter);
            ll_multiple_images_area.addView(Multiple_images_view, mLayoutParams);
            View line_view= new View(context);
            line_view.setBackgroundColor(context.getResources().getColor(R.color.line_bg_color));
            line_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    2));
            ll_multiple_images_area.addView(line_view);
        }
    }
}
