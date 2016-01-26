package com.task.activity;

import android.app.ActionBar;
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
import com.renrentui.tools.ExitApplication;
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
import com.task.upload.UploadService;
import com.task.upload.Views.LoadingView;
import com.task.upload.bean.uploadPicBean;
import com.task.upload.interfaces.TaskTempleUploadPicInterface;
import com.task.service.SubmitSuccessDialog.ExitDialogListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import base.BaseActivity;

/**
 * Created by Administrator on 2015/12/3 0003.
 * 任务资料提交页面
 */
public class TaskDatumSubmitActiviyt extends BaseActivity implements
        View.OnClickListener, INodata,TaskTempleUploadPicInterface {
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
    private ImageView mIV_pusher_type_flag;//任务类型
   // private TextView tv_pusher_type_content;//任务类型及内容
    private TextView tv_task_examine;//审核
    private TextView tv_deadline_time;// 截止日期
    private TextView tv_task_tel;//咨询电话

    //区域
    private ScrollView mScrollView;
    private LinearLayout ll_texts_area;//文字组区域
    private LinearLayout ll_images_area;//图片组区域
    private LinearLayout ll_multiple_images_area;//多组图片区域
    //提交
    private Button mBtn_submit;

    private RSTaskDatum mRSTaskDatum;//

    //view  tag(tag_team_id)(texts_team_id,images_team_id,multiple_images_team_id)
    public String texts_tag = "texts";
    public String images_tag = "images";
    public String multiple_images = "multiple_images";
    public  AtomicInteger texts_team = new AtomicInteger(-1) ;
    public AtomicInteger images_team = new AtomicInteger(-1);
    public AtomicInteger multiple_images_team = new AtomicInteger(-1);
    public LinearLayout.LayoutParams mLayoutParams = null;
    public String str_hotPhone;//电话

    //文字组
    public ArrayList<TaskDatumTempletParamsBean> mListTaskDatumTemplet_Texts_data=new  ArrayList<TaskDatumTempletParamsBean>();
    //图片组
    public ArrayList<TaskDatumTempletParamsBean> mListTaskDatumTemplet_Images_data=new  ArrayList<TaskDatumTempletParamsBean>();
    //多图片组
    public ArrayList<TaskDatumTempletParamsBean> mListTaskDatumTemplet_multiple_images_data=new  ArrayList<TaskDatumTempletParamsBean>();

    //数据集合
    public ArrayList<TaskDatumTempletParamsBean> mListTaskDatumTemplet_data=new  ArrayList<TaskDatumTempletParamsBean>();

    //管理类
   public TaskDatumTemplePIcManager mTaskDatumTemplePIcManager;
    public TaskTempleDBManager mTaskTempleDBManager;//数据库管理类

    //=====================获取任务模板========
    private RQHandler<RSTaskDatum> rqHandler_getTaskDatumn = new RQHandler<>(
            new IRqHandlerMsg<RSTaskDatum>() {

                @Override
                public void onBefore() {
                    hideProgressDialog();
                }

                @Override
                public void onNetworknotvalide() {
                    TaskDatumSubmitActiviyt.this.onNodata(
                            ResultMsgType.NetworkNotValide, null, null, null);
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
                    TaskDatumSubmitActiviyt.this.onNodata(
                            ResultMsgType.ServiceErr, "刷新", "数据加载失败！",
                            TaskDatumSubmitActiviyt.this);
                }

                @Override
                public void onSericeExp() {
                    TaskDatumSubmitActiviyt.this.onNodata(
                            ResultMsgType.ServiceExp, "刷新", "数据加载失败！",
                            TaskDatumSubmitActiviyt.this);
                }
            });
    //====================提交任务模板============================
    private RQHandler<RSTaskDatumnSubmitModel> rqHandler_SubmitTaskDatumn = new RQHandler<>(
            new IRqHandlerMsg<RSTaskDatumnSubmitModel>() {

                @Override
                public void onBefore() {
                    hideProgressDialog();
                }

                @Override
                public void onNetworknotvalide() {
                   ToastUtil.show(context,"网络错误!");
                }

                @Override
                public void onSuccess(RSTaskDatumnSubmitModel t) {
                    //清空数据库
                    showSubmitTaskDatumInfo(t);
                }

                @Override
                public void onSericeErr(RSTaskDatumnSubmitModel t) {
                    ToastUtil.show(context,t.msg);
                }

                @Override
                public void onSericeExp() {
                    ToastUtil.show(context,"服务器错误!");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_datum_submint_layout);
        super.init();
        userId = Utils.getUserDTO(context).data.userId;
        mAct = TaskDatumSubmitActiviyt.this;
        taskId = getIntent().getStringExtra("taskId");
        taskDatumId = getIntent().getStringExtra("taskDatumId");
        taskName = getIntent().getStringExtra("taskName");
        ctId = getIntent().getStringExtra("ctId");//地推关系id
        initControl();
        //initViewValue();
        mTaskDatumTemplePIcManager = new TaskDatumTemplePIcManager(context,context,this,this,userId,taskId);
        mTaskTempleDBManager = new TaskTempleDBManager(context);
       // mTaskTempleDBManager.delTaskTempleInfoByUserId(userId);//清空模板数据表
        mTaskTempleDBManager.delTaskTempleTable();
        getTaskDatumnTemplement();
    }
    @Override
    protected void onDestroy() {
        if (mTaskDatumTemplePIcManager != null) {
            mTaskDatumTemplePIcManager.unRegisterReceiver();
        }
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_left:
                showCancleTaskDatum();
                break;
            case R.id.btn_submit:
                submitTaskDatumInfo();
                break;
            case R.id.tv_task_tel:
                //电话
                Utils.callPhone(context,str_hotPhone.trim());
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
         //按下的如果是BACK，同时没有重复
            showCancleTaskDatum();
            return true;
        }

        return super.onKeyDown(keyCode, event);
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
            mTV_title_content.setText("资料录入");
        }

        icon_pusher = (ImageView) findViewById(R.id.icon_pusher);
        ll_amount = (LinearLayout) findViewById(R.id.ll_amount);
        tv_Amount = (TextView) findViewById(R.id.tv_amount);
        tv_pusher_taskName = (TextView) findViewById(R.id.tv_pusher_taskName);
        mIV_pusher_type_flag = (ImageView)findViewById(R.id.iv_flag);
        //tv_pusher_type_content = (TextView) findViewById(R.id.tv_pusher_taskType_content);
        tv_task_examine = (TextView) findViewById(R.id.tv_task_examine);
        tv_deadline_time = (TextView) findViewById(R.id.tv_deadline_time);
        tv_task_tel = (TextView) findViewById(R.id.tv_task_tel);
        ll_texts_area = (LinearLayout)findViewById(R.id.ll_texts_area);
        ll_images_area = (LinearLayout)findViewById(R.id.ll_images_area);
        ll_multiple_images_area = (LinearLayout)findViewById(R.id.ll_multiple_images_area);
        mBtn_submit = (Button)findViewById(R.id.btn_submit);
        mBtn_submit.setOnClickListener(this);
       // layout_back.setOnClickListener(this);
        mScrollView = (ScrollView)findViewById(R.id.scrollview_view);
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
                    context, new RQTaskDatumModel(userId, taskId),
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

        tv_pusher_taskName.setText(taskBean.taskTitle);

        if(taskBean.taskType==1){
//签约
            mIV_pusher_type_flag.setImageResource(R.drawable.team_qianyue);

        }else if(taskBean.taskType==2){
            //分享
            mIV_pusher_type_flag.setImageResource(R.drawable.team_share);
        }else if(taskBean.taskType==3){
            // 下载
            mIV_pusher_type_flag.setImageResource(R.drawable.team_down);
        }else{
            mIV_pusher_type_flag.setImageResource(R.drawable.team_qianyue);
        }

//        //简介
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
        //截止日期
        tv_deadline_time.setText(context.getResources().getString(R.string.task_detail_dealtime_format, TimeUtils.StringPattern(taskBean.endTime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd")));
        //tel
        if(TextUtils.isEmpty(taskBean.hotLine)){
            tv_task_tel.setVisibility(View.GONE);
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
        mScrollView.fullScroll(ScrollView.FOCUS_UP);
        ll_texts_area.removeAllViews();
        ll_images_area.removeAllViews();
        ll_multiple_images_area.removeAllViews();
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
            mTextsListView.setAdapter(new TaskDatumTemplateTextsTeamAdapter(context, controlDataList, 0, userId, taskId, texts_tag, team_type, texts_team.get()));
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
            TaskDatumTemplateImagesTeamAdapter mImagesAdapter= new TaskDatumTemplateImagesTeamAdapter(context,controlDataList,0,userId,taskId,images_tag, team_type, texts_team.get());
            mImagesAdapter.setTaskDatumTemplePIcManager(mTaskDatumTemplePIcManager);
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
            TaskDatumTemplateMultipleImagesTeamAdapter multipleImagesTeamAdapter = new TaskDatumTemplateMultipleImagesTeamAdapter(context,controlDataList,0,userId,taskId,multiple_images,team_type,multiple_images_team.get());
            multipleImagesTeamAdapter.setTaskDatumTemplePIcManager(mTaskDatumTemplePIcManager);
            mMultiple_images_gridview.setAdapter(multipleImagesTeamAdapter);
            ll_multiple_images_area.addView(Multiple_images_view, mLayoutParams);
            View line_view= new View(context);
            line_view.setBackgroundColor(context.getResources().getColor(R.color.line_bg_color));
            line_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    2));
            ll_multiple_images_area.addView(line_view);
        }
    }

    /**
     * 提交模板信息你
     */
    private void submitTaskDatumInfo(){
        //数据值的判断
        TaskTempleDBBean bean = new TaskTempleDBBean();
        bean.setTASK_ID(userId);
        bean.setUSER_ID(taskId);
        boolean isCheck =  mTaskTempleDBManager.checkoutTaskTemplateValueIsEmpty(bean);
        if(!isCheck) {
            ArrayList<RQTaskDatumSubmitModel.ValueInfo> data = new ArrayList<RQTaskDatumSubmitModel.ValueInfo>();
            ArrayList<TaskTempleDBBean> dataDB = mTaskTempleDBManager.getTaskTempleByUserIdAndTaskId(userId, taskId);
            int isize = dataDB == null ? 0 : dataDB.size();
            for (int i = 0; i < isize; i++) {
                TaskTempleDBBean bean1 = dataDB.get(i);
                RQTaskDatumSubmitModel.ValueInfo bean2 = (new RQTaskDatumSubmitModel()).new ValueInfo();
                bean2.controlValue = bean1.getTEAM_CONTENT_VALUE();
                bean2.controlName = bean1.getTEAM_CONTENT_KEY();
                data.add(bean2);
            }
            if (data != null && data.size() > 0) {
                showProgressDialog();
                ApiUtil.Request(new RQBaseModel<RQTaskDatumSubmitModel, RSTaskDatumnSubmitModel>(
                        context, new RQTaskDatumSubmitModel(userId, taskId, ctId, data),
                        new RSTaskDatumnSubmitModel(), ApiNames.提交任务模板接口.getValue(),
                        RequestType.POST, rqHandler_SubmitTaskDatumn));
            } else {
                ToastUtil.show(context, "请填写模板信息");
            }
        }else {
            ToastUtil.show(context, "请填写模板信息");
        }
    }

    /**
     * 提交模板信息返
     * @param t
     */
    private void showSubmitTaskDatumInfo(RSTaskDatumnSubmitModel t){
        if(t.code.equals("200")) {
            mTaskTempleDBManager.delTaskTempleTable();
            hideProgressDialog();
            SubmitSuccessDialog ssd = new SubmitSuccessDialog(context,
                    "审核时间" + mRSTaskDatum.data.task.auditCycle + "天，情耐心等待");
            ssd.addListener(new ExitDialogListener() {

                @Override
                public void clickCommit() {
                    //再次提交资料
                    getTaskDatumnTemplement();
                }

                @Override
                public void clickCancel() {
                    //确定，回到任务的审核列表
//                    Intent intent = new Intent(TaskDatumSubmitActiviyt.this,
//                            MyTaskMaterialActivity.class);
//                    intent.putExtra("topage", ToMainPage.审核中.getValue());
//                    intent.putExtra("TASK_ID", taskId);
//                    intent.putExtra("TASK_NAME", taskName);
//                    intent.putExtra("ctId",ctId);
//                    startActivity(intent);
                    finish();
                }
            });
            ssd.show();
            ssd.setCancelable(false);
        }else {
            ToastUtil.show(context,t.msg);
        }
    }
    /**
     * 返回操作
     */
    private void showCancleTaskDatum(){
        TaskTempleDBBean bean = new TaskTempleDBBean();
        bean.setUSER_ID(this.userId);
        bean.setTASK_ID(this.taskId);
        boolean isResult =   mTaskTempleDBManager.checkoutTaskTemplateValue(bean);
        if(isResult){
            //
        final  CancleDialog cancleDialog = new CancleDialog(context,"您还有未提交的资料，确定离开吗？");
            cancleDialog.addListener(new CancleDialog.CancleDialogListener() {
                @Override
                public void clickOk() {
                //确定离开
                    mTaskTempleDBManager.delTaskTempleTable();
                    TaskDatumSubmitActiviyt.this.finish();
                }

                @Override
                public void clickCancle() {
                    //取消

                }
            });
            cancleDialog.show();
            cancleDialog.setCancelable(false);
        }else{
            TaskDatumSubmitActiviyt.this.finish();
        }

    }


//=======================================图片的上传处理======================================
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PhotoManager.CAMERA_WITH_DATA || requestCode == PhotoManager.PHOTO_PICKED_WITH_DATA
            || requestCode == PhotoManager.SELECT_PICKED_LOCALTION) {
        // 默认回调是拍摄小票
        mTaskDatumTemplePIcManager.showOnActivityResult(mAct, requestCode, resultCode, data);
    }
}

    //根据返回的bean信息更新指定view信息
    public synchronized void  setViewInfoByTeam(int status ,uploadPicBean mUploadPicBean){
        View controlView =null;
        if(mUploadPicBean!=null){
            String strTag = mUploadPicBean.getTag()+"_"+mUploadPicBean.getTeam_num()+"_"+mUploadPicBean.getTeam_position()+"_img";
            if(mUploadPicBean.getTag().equals(images_tag)){
                //图片组
                controlView = (View)ll_images_area.findViewWithTag(strTag).getParent().getParent().getParent();
            }else if(mUploadPicBean.getTag().equals(multiple_images)){
            //多组图片
                controlView = (View)ll_multiple_images_area.findViewWithTag(strTag).getParent().getParent();
            }
            if(controlView !=null){
                LinearLayout mLL_image = ViewHolderUtil.get(controlView, R.id.linear_img);
                LoadingView mLoadingImage = ViewHolderUtil.get(controlView, R.id.item_img_iv);
                TextView mTV_image_reset = ViewHolderUtil.get(controlView,R.id.item_reset_tv);
                TextView mTV_image_status = ViewHolderUtil.get(controlView, R.id.item_status_tv);
                mLoadingImage.setClickable(false);
                switch(status){
                    case UploadService.TASK_STATE_START:
                        //开始上传
                        mLoadingImage.setImageBitmap(mUploadPicBean.getIcon());
                        mTV_image_status.setText(R.string.upload_task_status_uploading);
                        mTV_image_reset.setVisibility(View.INVISIBLE);
                        mTV_image_status.setVisibility(View.VISIBLE);
                        break;
                    case UploadService.TASK_STATE_UPLOADING:
                        //上传中
                        mTV_image_status.setText(R.string.upload_task_status_uploading);
                        mTV_image_reset.setVisibility(View.INVISIBLE);
                        mTV_image_status.setVisibility(View.VISIBLE);
                        break;
                    case UploadService.TASK_STATE_COMPLETE:
                        //上传成功
                        mTV_image_status.setText(R.string.upload_task_status_complete);
                        mLoadingImage.setLoadingVisibility(View.INVISIBLE);
                        mTV_image_reset.setVisibility(View.INVISIBLE);
                        mTV_image_status.setVisibility(View.VISIBLE);
                        break;
                    case UploadService.TASK_STATE_WAITING:
                        //等候上传
                        mTV_image_status.setText(R.string.upload_task_status_waiting);
                        mLoadingImage.setLoadingVisibility(View.VISIBLE);
                        mTV_image_reset.setVisibility(View.INVISIBLE);
                        mTV_image_status.setVisibility(View.VISIBLE);
                        break;
                    case UploadService.TASK_STATE_FAIL:
                        //上传失败
                        mTV_image_status.setText(R.string.upload_task_status_fail);
                        mTV_image_status.setTextColor(Color.RED);
                        mLoadingImage.setLoadingVisibility(View.INVISIBLE);
                        mTV_image_reset.setVisibility(View.VISIBLE);
                        mTV_image_status.setVisibility(View.VISIBLE);
                        mLoadingImage.setClickable(true);
                        break;
                    case  UploadService.TASK_STATE_STOP:
                        //上传已停止
                        mTV_image_status.setText(R.string.upload_task_status_stop);
                        mTV_image_status.setTextColor(Color.RED);
                        mLoadingImage.setLoadingVisibility(View.INVISIBLE);
                        mTV_image_reset.setVisibility(View.VISIBLE);
                        mTV_image_status.setVisibility(View.VISIBLE);
                        break;
                }
                if(status==UploadService.TASK_STATE_COMPLETE && mUploadPicBean!=null){
                    //上传成功,图片信息存入数据库，方便提交时信息整哈
                        TaskTempleDBBean bean = new  TaskTempleDBBean();
                    bean.setTASK_ID(mUploadPicBean.getTask_id());
                    bean.setUSER_ID(mUploadPicBean.getUser_id());
                    bean.setTAG(mUploadPicBean.getTag());
                    bean.setTEAM_TYPE(mUploadPicBean.getTeam_type());
                    bean.setTEAM_NUM(mUploadPicBean.getTeam_num());
                    bean.setTEAM_NUM_INDEX(mUploadPicBean.getTeam_position());
                    bean.setTEAM_CONTENT_VALUE(mUploadPicBean.getNetwork_path());
                    bean.setTEAM_CONTENT_KEY(mUploadPicBean.getControlKey());
                    bean.setTEAM_CONTENT_TYPE("2");
                    mTaskTempleDBManager.updateOrAddTaskTemplate(bean);
                }
            }
        }
    }
    @Override
    public void setUploadPicProgress(long fileLength, long curLength, String path,int status, Object objData) {
        setViewInfoByTeam(status, (uploadPicBean) objData);
    }

}
