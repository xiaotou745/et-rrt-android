package com.task.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGetTaskDetailInfo;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQReceiveTask;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.PartnerList;
import com.renrentui.resultmodel.RSGetTaskDetailInfoNew;
import com.renrentui.resultmodel.RSReceiveTask;
import com.renrentui.resultmodel.TaskDeatailInfoNew;
import com.renrentui.resultmodel.TaskSpecBeanInfo;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.TimeUtils;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.renrentui.view.MyListView;
import com.task.adapter.TaskExplainAdapter;
import com.task.adapter.TaskFlowPathAdapter;
import com.task.adapter.TaskFriendGridAdapter;
import com.task.adapter.TaskLinksAdapter;


import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;

/**
 * 任务详细信息界面
 * 
 * @author llp
 * 
 */
public class TaskDetailInfoNewActivity extends BaseActivity implements
		OnClickListener, INodata {
public static final String TAG = TaskDetailInfoNewActivity.class.getSimpleName();

	private ImageView icon_pusher;// 任务发布商logo
	private LinearLayout ll_amount;//任务单价
	private TextView tv_Amount;// 任务单价

	private TextView tv_pusher_taskName;// 任务名称
	private ImageView mIV_pusher_type_flag;//任务类型

	private TextView tv_task_examine;//审核
	private TextView tv_deadline_time;// 截止日期
	private TextView tv_forecast_time;//预计用时
	private TextView tv_task_tel;//咨询电话
//	流程
	private LinearLayout ll_task_description;//流程
	private MyListView lv_task_flowpath;//流程list
	private View mLine_task_explain_top;
	private ListView lv_task_explain;//补充说明list
	private View mLine_detail_link_top;
	private ListView lv_task_detail_link;//详情连接list

	//参与人
	private TextView mTV_task_friend_flag;
	private GridView mTaskFriendGridView;//参与人集合

	//操作按钮
	private Button btn_receive_task;// 领取任务按钮

//adapter
	private TaskExplainAdapter  mTaskExplainAdapter;
	private TaskFlowPathAdapter mTaskFlowPathAdapter;
	private TaskLinksAdapter mTaskLinkAdapter;
	private TaskFriendGridAdapter mTaskFriendGridAdapter;//参与人

	private RSGetTaskDetailInfoNew rsGetTaskDetailInfo;
	private ArrayList<TaskSpecBeanInfo> mFlowpathData=new ArrayList<TaskSpecBeanInfo>();
	private ArrayList<TaskSpecBeanInfo> mExplainData=new ArrayList<TaskSpecBeanInfo>();
	private ArrayList<TaskSpecBeanInfo > mLinksData=new ArrayList<TaskSpecBeanInfo>();

//	基础数据
	private String userId;// 用户id
	private String taskId;// 任务id
	private boolean isShareTask =false;//是否是分享型任务
	private String str_shareContent ="";//分享的内容
	private String str_taskName;//任务名称
	private String str_ctId = "";//地推关系id
	private int i_isHad = 1;//1:已领取  0：未领取
	private String str_scanTip;//扫码说明
	private String str_reminder ;//温馨提示
	private String str_taskStatus="";//状态信息


//	任务详情Handler
	private RQHandler<RSGetTaskDetailInfoNew> rqHandler_getTaskDetailInfo = new RQHandler<>(
			new IRqHandlerMsg<RSGetTaskDetailInfoNew>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
				}

				@Override
				public void onNetworknotvalide() {
					TaskDetailInfoNewActivity.this.onNodata(
							ResultMsgType.NetworkNotValide, null, null, null);
				}

				@Override
				public void onSuccess(RSGetTaskDetailInfoNew t) {
					rsGetTaskDetailInfo = t;
					initData(rsGetTaskDetailInfo.data);
					hideProgressDialog();
				}

				@Override
				public void onSericeErr(RSGetTaskDetailInfoNew t) {
					TaskDetailInfoNewActivity.this.onNodata(
							ResultMsgType.ServiceErr, "刷新", "数据加载失败！",
							TaskDetailInfoNewActivity.this);
				}

				@Override
				public void onSericeExp() {
					TaskDetailInfoNewActivity.this.onNodata(
							ResultMsgType.ServiceExp, "刷新", "数据加载失败！",
							TaskDetailInfoNewActivity.this);
				}
			});

//	领取任务
	private RQHandler<RSReceiveTask> rqHandler_receiveTask = new RQHandler<>(
			new IRqHandlerMsg<RSReceiveTask>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
				}

				@Override
				public void onNetworknotvalide() {
					hideProgressDialog();
					ToastUtil.show(context,context.getResources().getString(
									R.string.networknotconnect));
				}

				@Override
				public void onSuccess(RSReceiveTask t) {
					hideProgressDialog();
					str_ctId = t.data;
					i_isHad =1;
					if(isShareTask){
					//分享 ，下载
						btn_receive_task.setText("继续任务");
					}else{
						//签约
						btn_receive_task.setText("分享二维码");
					}
					ToastUtil.show(context,"领取成功");
				}

				@Override
				public void onSericeErr(RSReceiveTask t) {
					hideProgressDialog();
					ToastUtil.show(context, t.msg);
				}

				@Override
				public void onSericeExp() {
					hideProgressDialog();
				}
			});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail_new_layout);
		super.init();
		Intent intent = getIntent();
		userId = Utils.getUserDTO(context).data.userId;
		taskId = intent.getStringExtra("TaskId");
		str_taskName = intent.getStringExtra("TaskName");
		str_ctId = intent.getStringExtra("ctId");
		initControl();
		getInitData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Utils.getUserDTO(context) != null){
			userId = Utils.getUserDTO(context).data.userId;
		}
		else {
			userId = "0";
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		//getInitData();
	}

	/**
	 * 获取详情数据
	 */
	private void getInitData() {
		showProgressDialog();
		ApiUtil.Request(new RQBaseModel<RQGetTaskDetailInfo, RSGetTaskDetailInfoNew>(
				context, new RQGetTaskDetailInfo(userId, taskId),
				new RSGetTaskDetailInfoNew(), ApiNames.获取任务详细信息.getValue(),
				RequestType.POST, rqHandler_getTaskDetailInfo));
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		if(mIV_title_left!=null){
			mIV_title_left.setVisibility(View.VISIBLE);
			mIV_title_left.setOnClickListener(this);
		}
		if(mTV_title_content!=null){
			mTV_title_content.setText("任务详情");
		}

		icon_pusher = (ImageView) findViewById(R.id.icon_pusher);
		ll_amount =(LinearLayout)findViewById(R.id.ll_amount);
		tv_Amount = (TextView)findViewById(R.id.tv_amount);

		tv_pusher_taskName = (TextView) findViewById(R.id.tv_pusher_taskName);
		mIV_pusher_type_flag = (ImageView)findViewById(R.id.iv_flag);

		tv_task_examine = (TextView)findViewById(R.id.tv_task_examine);
		tv_deadline_time =(TextView) findViewById(R.id.tv_deadline_time);
		tv_forecast_time = (TextView )findViewById(R.id.tv_task_forecast_time);

		tv_task_tel = (TextView)findViewById(R.id.tv_task_tel);

		ll_task_description = (LinearLayout) findViewById(R.id.ll_task_description);
		lv_task_flowpath = (MyListView)findViewById(R.id.lv_task_flowpath);
		mLine_task_explain_top = findViewById(R.id.line_task_explain_top);
		lv_task_explain = (ListView)findViewById(R.id.lv_task_explain);
		mLine_detail_link_top = findViewById(R.id.line_detail_link_top);
		lv_task_detail_link = (ListView)findViewById(R.id.lv_task_detail_link);

		mTV_task_friend_flag = (TextView)findViewById(R.id.tv_task_friend_flag);
		mTaskFriendGridView = (GridView)findViewById(R.id.gv_task_friend);

		btn_receive_task = (Button) findViewById(R.id.btn_task_get);
		btn_receive_task.setOnClickListener(this);

		mTaskExplainAdapter = new TaskExplainAdapter(context,mExplainData);
		mTaskFlowPathAdapter = new TaskFlowPathAdapter(context,mFlowpathData);
		mTaskLinkAdapter = new TaskLinksAdapter(context,mLinksData);

		lv_task_flowpath.setAdapter(mTaskFlowPathAdapter);
		lv_task_explain.setAdapter(mTaskExplainAdapter);
		lv_task_detail_link.setAdapter(mTaskLinkAdapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_task_get:
				submitTaskDetail(rsGetTaskDetailInfo.data.task.taskType,i_isHad);
				break;
			case R.id.tv_task_tel:
				//电话
				Utils.callPhone(context,rsGetTaskDetailInfo.data.task.hotLine.trim());
				break;
			case R.id.iv_title_left:
				finish();
				break;
		}
	}

	/**
	 * 初始化页面数据
	 *

	 */
	@SuppressWarnings("deprecation")
	private void initData(TaskDeatailInfoNew taskBean) {
		//头像
		if (Util.IsNotNUll(taskBean.task.logo )&& Utils.checkUrl(taskBean.task.logo)) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(taskBean.task.logo,
					icon_pusher, R.drawable.pusher_logo);
		} else {
			icon_pusher.setImageResource(R.drawable.pusher_logo);
		}
		tv_Amount.setText(taskBean.task.getAmount());

		tv_pusher_taskName.setText(taskBean.task.taskTitle);
		i_isHad = taskBean.task.isHad;
		if(taskBean.task.isHad==1){
			str_ctId = String.valueOf(taskBean.task.ctId);
		}else{
			str_ctId = "0";
		}

		//mtv_title_content.setText(taskBean.task.taskTitle);

		str_scanTip = taskBean.task.scanTip;
		str_reminder = taskBean.task.reminder;
		str_taskStatus = taskBean.task.status;

//		SpannableStringBuilder style = null;
//		switch (taskBean.task.taskType){
//			case 1:
//				//签约
//				style =  UIHelper.setStyleColorByColor(context, taskBean.task.taskTypeName.toString(), taskBean.task.taskGeneralInfo.toString(), R.color.white, R.color.tv_bg_color_1);
//				break;
//			case 2:
//				//分享
//				style =  UIHelper.setStyleColorByColor(context,taskBean.task.taskTypeName.toString(),taskBean.task.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_2);
//				break;
//			case 3:
//				//下载
//				style =  UIHelper.setStyleColorByColor(context,taskBean.task.taskTypeName.toString(),taskBean.task.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_3);
//				break;
//			default:
//				style =  UIHelper.setStyleColorByColor(context,taskBean.task.taskTypeName.toString(),taskBean.task.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_1);
//				break;
//		}
//		tv_pusher_type_content.setText(style);

		//审核
		tv_task_examine.setText(context.getResources().getString(R.string.task_detail_examine_format,taskBean.task.auditCycle));
		//截止日期
		tv_deadline_time.setText(context.getResources().getString(R.string.task_detail_dealtime_format, TimeUtils.StringPattern(taskBean.task.endTime,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd")));
		//预计时间
		tv_forecast_time.setText(context.getResources().getString(R.string.task_detail_forecasttime_format,taskBean.task.estimatedTime));

		//tel
		if(TextUtils.isEmpty(taskBean.task.hotLine)){
			tv_task_tel.setVisibility(View.GONE);
		}else {
			tv_task_tel.setVisibility(View.VISIBLE);
			tv_task_tel.setText(Html.fromHtml(context.getResources().getString(R.string.task_detail_tel_format, taskBean.task.hotLine)));
			tv_task_tel.setOnClickListener(this);
		}
		if(mFlowpathData!=null){
			mFlowpathData.clear();
		}
		if(mExplainData!=null){
			mExplainData.clear();
		}
		if(mLinksData!=null){
			mLinksData.clear();
		}
//		if("1".equals(taskBean.task.status)){
//		//任务审核通过，可以提交
//			btn_receive_task.setVisibility(View.VISIBLE);
//		}else{
//			//非合法任务信息
//			btn_receive_task.setVisibility(View.GONE);
//			String strUnMessage= "";
//			if("3".equals(taskBean.task.status)){
//				strUnMessage = "此任务已过期!";
//			}else{
//				strUnMessage = "此任务已被终止!";
//			}
//			ToastUtil.show(context,strUnMessage);
//		}
		//解析数据
		int isize = taskBean.taskSetps==null ?0:taskBean.taskSetps.size();
		for(int i=0;i<isize;i++){
			TaskSpecBeanInfo bean =  taskBean.taskSetps.get(i);
			int index = bean.getSortNo()-1;
			if(bean.getSetpType()==1){
				//步骤
				mFlowpathData.add(index,bean);
			}else if(bean.getSetpType()==2){
				//说明
				mExplainData.add(index,bean);
			}else if(bean.getSetpType()==3){
			   //细则
				mLinksData.add(index,bean);
			}
		}
		if(mExplainData==null ||mExplainData.size()==0){
			mLine_task_explain_top.setVisibility(View.GONE);
		}else{
			mLine_task_explain_top.setVisibility(View.VISIBLE);
		}
		if(mLinksData==null ||mLinksData.size()==0){
			mLine_detail_link_top.setVisibility(View.GONE);
		}else{
			mLine_detail_link_top.setVisibility(View.VISIBLE);
		}
		//适配数据
		mTaskFlowPathAdapter.setTaskData(mFlowpathData);
		mTaskExplainAdapter.setTaskData(mExplainData);
		mTaskLinkAdapter.setTaskData(mLinksData);

//		判断是否包含流程描述信息
		if(mFlowpathData.size()==0 &&mExplainData.size()==0 && mLinksData.size()==0){
			//无流程描述性信息
			ll_task_description.setVisibility(View.GONE);
		}else{
			btn_receive_task.setVisibility(View.VISIBLE);
			ll_task_description.setVisibility(View.VISIBLE);
		}

		if(i_isHad==0){
			//未领取
			if(taskBean.task.taskType==1){
				//签约
				btn_receive_task.setText("领取任务");
				isShareTask = false;
			}else if(taskBean.task.taskType==2){
				//分享
				btn_receive_task.setText("领取任务");
				isShareTask = true;
			}else  if(taskBean.task.taskType==3){
				//下载
				btn_receive_task.setText("领取任务");
				isShareTask = true;
			}
		}else if(taskBean.task.isHad==1){
			//已领取
			if(taskBean.task.taskType==1){
				//签约
				btn_receive_task.setText("继续任务");
				isShareTask = false;
			}else if(taskBean.task.taskType==2){
				//分享
				btn_receive_task.setText("分享二维码");
				isShareTask = true;
			}else  if(taskBean.task.taskType==3){
				//下载
				btn_receive_task.setText("分享二维码");
				isShareTask = true;
			}
		}
		str_shareContent = taskBean.task.downUrl;

		//解析任务参与人
		List<PartnerList>  mPartnerList = taskBean.partnerList;
		if(mPartnerList==null || mPartnerList.size()==0){
			 mTV_task_friend_flag.setText("还没有地推参与该任务~");
			mTaskFriendGridView.setVisibility(View.GONE);
		}else{
			//数据正常
			mTV_task_friend_flag.setText(context.getResources().getString(R.string.my_tasknew_friend_flag,mPartnerList.size()));
			mTaskFriendGridView.setVisibility(View.VISIBLE);
			mTaskFriendGridAdapter = new TaskFriendGridAdapter(context,0);
			mTaskFriendGridView.setAdapter(mTaskFriendGridAdapter);
			mTaskFriendGridAdapter.setData(mPartnerList);
			mTaskFriendGridAdapter.setTaskDetailInfoNewData(rsGetTaskDetailInfo.data);
		}
	}

	/**
	 * 领取任务
	 * @param type
	 * @param isHad
	 */
private  void submitTaskDetail(int type,int isHad){
	if(isHad==0){
		//为领取
		//单纯领取任务
		submitTask_1();
	}else{
		//已领取
		if(type==1){
			//签约
			//领取型
			Intent mIntent = new Intent();
			mIntent.setClass(context, MyTaskMaterialActivity.class);
			mIntent.putExtra("TASK_ID", taskId);
			mIntent.putExtra("topage",ToMainPage.审核中.getValue());
			mIntent.putExtra("TASK_NAME", str_taskName);
			mIntent.putExtra("isShowSubmitBtn",true);
			mIntent.putExtra("ctId",str_ctId);
			mIntent.putExtra("taskStatus",str_taskStatus);
			startActivity(mIntent);
			finish();
		}else{
			//下载 分享
			Intent mIntent = new Intent();
			mIntent.setClass(context,ShareViewActivity.class);
			mIntent.putExtra("TASK_ID", taskId);
			mIntent.putExtra("SHARE_CONTENT", str_shareContent);
			mIntent.putExtra("ctId",str_ctId);
			mIntent.putExtra("scanTip",str_scanTip);
			mIntent.putExtra("reminder",str_reminder);
			mIntent.putExtra("taskStatus",str_taskStatus);
			startActivity(mIntent);
			finish();
		}
	}


}

	/**
	 * 单纯的领取任务
	 */
	private void submitTask_1(){
			showProgressDialog();
		ApiUtil.Request(new RQBaseModel<RQReceiveTask, RSReceiveTask>(
				context, new RQReceiveTask(
				Utils.getUserDTO(context).data.userId, taskId),
				new RSReceiveTask(), ApiNames.领取任务.getValue(),
				RequestType.POST, rqHandler_receiveTask));
	}

//	/**
//	 * 领取并分享任务
//	 */
//	private void submitTask_2(){
//		showProgressDialog();
//		ApiUtil.Request(new RQBaseModel<RQReceiveTask, RSReceiveTask>(
//				context, new RQReceiveTask(
//				Utils.getUserDTO(context).data.userId, taskId),
//				new RSReceiveTask(), ApiNames.领取任务.getValue(),
//				RequestType.POST, rqHandler_receiveTask));
//	}
//
//	/**
//	 * 再次提交新资料
//	 */
//	private void submitTask_3(){
//		goToTaskMaterialActivity();
//	}
//	/**
//	 * 再次分享二维码
//	 */
//	private void submitTask_4(){
//			//分享型
//			Intent mIntent = new Intent();
//			mIntent.setClass(context,ShareViewActivity.class);
//		mIntent.putExtra("TASK_ID", taskId);
//			mIntent.putExtra("SHARE_CONTENT", str_shareContent);
//			mIntent.putExtra("ctId",str_ctId);
//		mIntent.putExtra("scanTip",str_scanTip);
//		mIntent.putExtra("reminder",str_reminder);
//		mIntent.putExtra("taskStatus",str_taskStatus);
//			startActivity(mIntent);
//			finish();
//
//	}
//资料提交页面
private  void goToTaskMaterialActivity(){
	Intent mIntent = new Intent();
	mIntent.setClass(context, MyTaskMaterialActivity.class);
	mIntent.putExtra("TASK_ID", taskId);
	mIntent.putExtra("topage",ToMainPage.审核中.getValue());
	mIntent.putExtra("TASK_NAME",str_taskName);
	mIntent.putExtra("isShowSubmitBtn",true);
	mIntent.putExtra("ctId",str_ctId);
	mIntent.putExtra("taskStatus",str_taskStatus);
	startActivity(mIntent);
	finish();
}
	@Override
	public void onNoData() {
		getInitData();
	}

}
