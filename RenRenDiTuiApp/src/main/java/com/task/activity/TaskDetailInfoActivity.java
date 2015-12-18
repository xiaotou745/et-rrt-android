package com.task.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGetTaskDetailInfo;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQReceiveTask;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.resultmodel.RSGetTaskDetailInfo;
import com.renrentui.resultmodel.RSReceiveTask;
import com.renrentui.resultmodel.TaskDetailInfo;
import com.renrentui.resultmodel.TaskSpecBeanInfo;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.TimeUtils;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.UIHelper;
import com.renrentui.util.Utils;
import com.renrentui.view.MyListView;
import com.task.adapter.TaskExplainAdapter;
import com.task.adapter.TaskFlowPathAdapter;
import com.task.adapter.TaskLinksAdapter;
import com.task.service.SuccessDialog;
import com.task.service.SuccessDialog.ExitDialogListener;

/**
 * 任务详细信息界面
 * 
 * @author llp
 * 
 */
public class TaskDetailInfoActivity extends BaseActivity implements
		OnClickListener, INodata {
public static final String TAG = TaskDetailInfoActivity.class.getSimpleName();
	private Context context;

	private ImageView icon_pusher;// 任务发布商logo
	private LinearLayout ll_amount;//任务单价
	private TextView tv_Amount;// 任务单价

	private TextView tv_pusher_taskName;// 任务名称
	private TextView tv_pusher_type_content;//任务类型及内容
	private TextView tv_task_examine;//审核
	private TextView tv_deadline_time;// 截止日期
	private TextView tv_task_tel;//咨询电话
//	流程
	private LinearLayout ll_task_description;//流程
	private MyListView lv_task_flowpath;//流程list
	private View mLine_task_explain_top;
	private ListView lv_task_explain;//补充说明list
	private View mLine_detail_link_top;
	private ListView lv_task_detail_link;//详情连接list

	//操作按钮
	private Button btn_receive_task;// 领取任务按钮

//adapter
	private TaskExplainAdapter  mTaskExplainAdapter;
	private TaskFlowPathAdapter mTaskFlowPathAdapter;
	private TaskLinksAdapter mTaskLinkAdapter;

	private RSGetTaskDetailInfo rsGetTaskDetailInfo;
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
	private String str_scanTip;//扫码说明
	private String str_reminder ;//温馨提示
	private String str_taskStatus="";//状态信息


//	任务详情Handler
	private RQHandler<RSGetTaskDetailInfo> rqHandler_getTaskDetailInfo = new RQHandler<>(
			new IRqHandlerMsg<RSGetTaskDetailInfo>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
				}

				@Override
				public void onNetworknotvalide() {
					TaskDetailInfoActivity.this.onNodata(
							ResultMsgType.NetworkNotValide, null, null, null);
				}

				@Override
				public void onSuccess(RSGetTaskDetailInfo t) {
					Log.e(TAG,t.toString());
					rsGetTaskDetailInfo = t;
					initData(rsGetTaskDetailInfo.data);
					hideProgressDialog();
				}

				@Override
				public void onSericeErr(RSGetTaskDetailInfo t) {
					TaskDetailInfoActivity.this.onNodata(
							ResultMsgType.ServiceErr, "刷新", "数据加载失败！",
							TaskDetailInfoActivity.this);
				}

				@Override
				public void onSericeExp() {
					TaskDetailInfoActivity.this.onNodata(
							ResultMsgType.ServiceExp, "刷新", "数据加载失败！",
							TaskDetailInfoActivity.this);
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
					ToastUtil.show(context,"任务领取成功");
					str_ctId = t.data;
					if(isShareTask){
					//分享型
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
					}else{
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
					}
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
		setContentView(R.layout.activity_task_detail);
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
		ApiUtil.Request(new RQBaseModel<RQGetTaskDetailInfo, RSGetTaskDetailInfo>(
				context, new RQGetTaskDetailInfo(userId, taskId),
				new RSGetTaskDetailInfo(), ApiNames.获取任务详细信息.getValue(),
				RequestType.POST, rqHandler_getTaskDetailInfo));
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		context = this;
		icon_pusher = (ImageView) findViewById(R.id.icon_pusher);
		ll_amount =(LinearLayout)findViewById(R.id.ll_amount);
		tv_Amount = (TextView)findViewById(R.id.tv_amount);
		tv_pusher_taskName = (TextView) findViewById(R.id.tv_pusher_taskName);
		tv_pusher_type_content = (TextView)findViewById(R.id.tv_pusher_taskType_content);
		tv_task_examine = (TextView)findViewById(R.id.tv_task_examine);
		tv_deadline_time =(TextView) findViewById(R.id.tv_deadline_time);
		tv_task_tel = (TextView)findViewById(R.id.tv_task_tel);
		ll_task_description = (LinearLayout) findViewById(R.id.ll_task_description);
		lv_task_flowpath = (MyListView)findViewById(R.id.lv_task_flowpath);
		mLine_task_explain_top = findViewById(R.id.line_task_explain_top);
		lv_task_explain = (ListView)findViewById(R.id.lv_task_explain);
		mLine_detail_link_top = findViewById(R.id.line_detail_link_top);
		lv_task_detail_link = (ListView)findViewById(R.id.lv_task_detail_link);

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
				submitTaskDetail(rsGetTaskDetailInfo.data.task.taskType,rsGetTaskDetailInfo.data.task.isHad);
				break;
			case R.id.tv_task_tel:
				//电话
				Utils.callPhone(context,rsGetTaskDetailInfo.data.task.hotLine.trim());
				break;
		}
	}

	/**
	 * 初始化页面数据
	 *

	 */
	@SuppressWarnings("deprecation")
	private void initData(TaskDetailInfo taskBean) {
		//头像
		if (Util.IsNotNUll(taskBean.task.logo )&& Utils.checkUrl(taskBean.task.logo)) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(taskBean.task.logo,
					icon_pusher, R.drawable.pusher_logo);
		} else {
			icon_pusher.setImageResource(R.drawable.pusher_logo);
		}
		tv_Amount.setText(taskBean.task.getAmount());

		tv_pusher_taskName.setText(taskBean.task.taskTitle);
		if(taskBean.task.isHad==1){
			str_ctId = String.valueOf(taskBean.task.ctId);
		}

		//mtv_title_content.setText(taskBean.task.taskTitle);

		str_scanTip = taskBean.task.scanTip;
		str_reminder = taskBean.task.reminder;
		str_taskStatus = taskBean.task.status;

		SpannableStringBuilder style = null;
		switch (taskBean.task.taskType){
			case 1:
				//签约
				style =  UIHelper.setStyleColorByColor(context, taskBean.task.taskTypeName.toString(), taskBean.task.taskGeneralInfo.toString(), R.color.white, R.color.tv_bg_color_1);
				break;
			case 2:
				//分享
				style =  UIHelper.setStyleColorByColor(context,taskBean.task.taskTypeName.toString(),taskBean.task.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_2);
				break;
			case 3:
				//下载
				style =  UIHelper.setStyleColorByColor(context,taskBean.task.taskTypeName.toString(),taskBean.task.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_3);
				break;
			default:
				style =  UIHelper.setStyleColorByColor(context,taskBean.task.taskTypeName.toString(),taskBean.task.taskGeneralInfo.toString(),R.color.white,R.color.tv_bg_color_1);
				break;
		}
		tv_pusher_type_content.setText(style);

		//审核
		tv_task_examine.setText(context.getResources().getString(R.string.task_detail_examine_format,taskBean.task.auditCycle));
		//截止日期
		tv_deadline_time.setText(context.getResources().getString(R.string.task_detail_dealtime_format, TimeUtils.StringPattern(taskBean.task.endTime,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd")));
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

		if(taskBean.task.isHad==0){
			//未领取
			if(taskBean.task.taskType==1){
				//签约
				btn_receive_task.setText("立即参与");
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
		}else if(taskBean.task.isHad==1){
			//已领取
			if(taskBean.task.taskType==1){
				//签约
				btn_receive_task.setText("继续任务");
				isShareTask = false;
			}else if(taskBean.task.taskType==2){
				//分享
				btn_receive_task.setText("继续分享");
				isShareTask = true;
			}else  if(taskBean.task.taskType==3){
				//下载
				btn_receive_task.setText("继续分享");
				isShareTask = true;
			}
		}
		str_shareContent = taskBean.task.downUrl;
	}

	/**
	 * 领取任务
	 * @param type
	 * @param isHad
	 */
private  void submitTaskDetail(int type,int isHad){
	if(isHad==0){
		//未领情
		if(type==1){
			//单纯领取任务
			submitTask_1();
		}else if(type==2){
			//分享二维码
			submitTask_2();
		}else if(type==3){
			//分享二维码
			submitTask_2();
		}
	}else{
		//已领取
		if(type==1){
			//单纯继续领取任务
			submitTask_3();
		}else if(type==2){
			//继续领取任务并分享二维码
			submitTask_4();
		}else if(type==3){
			//继续领取任务并分享二维码
			submitTask_4();
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

	/**
	 * 领取并分享任务
	 */
	private void submitTask_2(){
		showProgressDialog();
		ApiUtil.Request(new RQBaseModel<RQReceiveTask, RSReceiveTask>(
				context, new RQReceiveTask(
				Utils.getUserDTO(context).data.userId, taskId),
				new RSReceiveTask(), ApiNames.领取任务.getValue(),
				RequestType.POST, rqHandler_receiveTask));
	}

	/**
	 * 再次提交新资料
	 */
	private void submitTask_3(){
		goToTaskMaterialActivity();
	}
	/**
	 * 再次分享二维码
	 */
	private void submitTask_4(){
			//分享型
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
