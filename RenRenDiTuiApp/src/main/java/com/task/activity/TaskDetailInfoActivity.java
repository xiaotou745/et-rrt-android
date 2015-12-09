package com.task.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
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
	private ListView lv_task_explain;//补充说明list
	private ListView lv_task_detail_link;//详情连接list

	//操作按钮
	private Button btn_receive_task;// 领取任务按钮

//adapter
	private TaskExplainAdapter  mTaskExplainAdapter;
	private TaskFlowPathAdapter mTaskFlowPathAdapter;
	private TaskLinksAdapter mTaskLinkAdapter;

	private RSGetTaskDetailInfo rsGetTaskDetailInfo;
	private ArrayList<String> mFlowpathData=new ArrayList<String>();
	private ArrayList<String> mExplainData=new ArrayList<String>();
	private ArrayList<String > mLinksData=new ArrayList<String>();

//	基础数据
	private String userId;// 用户id
	private String taskId;// 任务id
	private boolean isShareTask =false;//是否是分享型任务
	private String str_shareContent ="";//分享的内容
	private String str_taskName;//任务名称

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
					ToastUtil.show(
							context,
							context.getResources().getString(
									R.string.networknotconnect));
				}

				@Override
				public void onSuccess(RSReceiveTask t) {
					hideProgressDialog();
//					SuccessDialog dialog = new SuccessDialog(context, "请在"
//							+ rsGetTaskDetailInfo.data.taskCycle
//							+ "小时内完成\n提示：完成之后不要忘记提交审核哦");
//					dialog.addListener(new ExitDialogListener() {
//
//						@Override
//						public void clickCommit() {
//							ll_receive_task.setVisibility(View.GONE);
//							ll_through_task.setVisibility(View.GONE);
//							ll_giveup_task.setVisibility(View.VISIBLE);
//							tv_states.setText("已领取");
//							isList = 5;
//							getInitData();
//						}
//
//						@Override
//						public void clickCancel() {
//							Intent intent = new Intent(context,
//									MyTaskMaterialActivity.class);
//							context.startActivity(intent);
//							finish();
//						}
//					});
//					dialog.show();
//					dialog.setCancelable(false);
					ToastUtil.show(context,"任务领取成功");
					if(isShareTask){
					//分享型
						Intent mIntent = new Intent();
						mIntent.setClass(context,ShareViewActivity.class);
						mIntent.putExtra("TASK_ID", taskId);
						mIntent.putExtra("SHARE_CONTENT", str_shareContent);
						startActivity(mIntent);
						finish();
					}else{
						//领取型
						Intent mIntent = new Intent();
						mIntent.setClass(context, MyTaskMaterialActivity.class);
						mIntent.putExtra("TASK_ID", taskId);
						mIntent.putExtra("topage",ToMainPage.审核中.getValue());
						mIntent.putExtra("TASK_NAME", str_taskName);

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

//	private RQHandler<RSBase> rqHandler_giveupTask = new RQHandler<>(
//			new IRqHandlerMsg<RSBase>() {
//
//				@Override
//				public void onBefore() {
//					hideProgressDialog();
//				}
//
//				@Override
//				public void onNetworknotvalide() {
//					hideProgressDialog();
//					ToastUtil.show(
//							context,
//							context.getResources().getString(
//									R.string.networknotconnect));
//				}
//
//				@Override
//				public void onSuccess(RSBase t) {
//					hideProgressDialog();
//					tv_time.setText("");
//					tv_time_show.setText("");
//					ToastUtil.show(context, "放弃任务成功");
//					ll_receive_task.setVisibility(View.VISIBLE);
//					ll_giveup_task.setVisibility(View.GONE);
//					ll_through_task.setVisibility(View.GONE);
//					tv_states.setText("待领取");
//					isList = 0;
//					hideProgressDialog();
//					getInitData();
//				}
//
//				@Override
//				public void onSericeErr(RSBase t) {
//					hideProgressDialog();
//					ToastUtil.show(context, t.msg);
//				}
//
//				@Override
//				public void onSericeExp() {
//					hideProgressDialog();
//				}
//			});


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);
		super.init();
		initControl();
		Intent intent = getIntent();
		taskId = intent.getStringExtra("TaskId");
		str_taskName = intent.getStringExtra("TaskName");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Utils.getUserDTO(context) != null){
			userId = Utils.getUserDTO(context).data.userId;}
		else {
			userId = "0";
		}
		getInitData();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		getInitData();
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
		lv_task_explain = (ListView)findViewById(R.id.lv_task_explain);
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
//		switch (v.getId()) {
//		case R.id.btn_look_shenhe:// 查看审核
//			intent = new Intent(context, SubmitDataActivity.class);
//			intent.putExtra("auditStatus", 2);
//			intent.putExtra("taskId", rsGetTaskDetailInfo.data.id);
//			intent.putExtra("myReceivedTaskId",
//					rsGetTaskDetailInfo.data.orderId);
//			context.startActivity(intent);
//			break;
//		case R.id.btn_submit_data:// 提交资料
//			intent = new Intent(context, SubmitDataActivity.class);
//			intent.putExtra("taskId", taskId);
//			intent.putExtra("myReceivedTaskId", orderId);
//			context.startActivity(intent);
//			break;
//		case R.id.btn_giveup_task:// 放弃任务
//			showProgressDialog();
//			ApiUtil.Request(new RQBaseModel<RQGiveupTask, RSBase>(context,
//					new RQGiveupTask(Utils.getUserDTO(context).data.userId,
//							orderId, ""), new RSBase(), ApiNames.放弃任务
//							.getValue(), RequestType.POST, rqHandler_giveupTask));
//			break;
//		case R.id.btn_receive_task_again:// 再次领取
//		case R.id.btn_receive_task:// 点击领取任务时
//			if (userId.equals("0")) {
//				intent = new Intent(context, LoginActivity.class);
//				context.startActivity(intent);
//				break;
//			}
//			showProgressDialog();
//			ApiUtil.Request(new RQBaseModel<RQReceiveTask, RSReceiveTask>(
//					context, new RQReceiveTask(
//							Utils.getUserDTO(context).data.userId, taskId),
//					new RSReceiveTask(), ApiNames.领取任务.getValue(),
//					RequestType.POST, rqHandler_receiveTask));
//			break;
//		default:
//			break;
//		}
	}

	/**
	 * 初始化页面数据
	 *

	 */
	@SuppressWarnings("deprecation")
	private void initData(TaskDetailInfo taskBean) {
		//头像
		if (Util.IsNotNUll(taskBean.task.logo )) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(taskBean.task.logo,
					icon_pusher, R.drawable.pusher_logo);
		} else {
			icon_pusher.setImageResource(R.drawable.pusher_logo);
		}
		tv_Amount.setText(taskBean.task.getAmount());

		tv_pusher_taskName.setText(taskBean.task.taskTitle);
	//mtv_title_content.setText(taskBean.task.taskTitle);
		//简介
		String strType =  " "+taskBean.task.taskTypeName.toString()+" ";
		String strTypeContent =strType +" "+taskBean.task.taskGeneralInfo.toString();
		int fstart = strTypeContent.indexOf(taskBean.task.taskTypeName.toString());
		int fend = fstart + taskBean.task.taskTypeName.toString().length();
		int bstart = 0;
		int bend = strType.length();
		SpannableStringBuilder style = new SpannableStringBuilder(strTypeContent);
		style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.white)),fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		style.setSpan(new BackgroundColorSpan(context.getResources().getColor(R.color.tv_bg_color_1)), bstart, bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tv_pusher_type_content.setText(style);



		//审核
		tv_task_examine.setText(context.getResources().getString(R.string.task_detail_examine_format,taskBean.task.auditCycle));
		//截止日期
		tv_deadline_time.setText(context.getResources().getString(R.string.task_detail_dealtime_format, TimeUtils.StringPattern(taskBean.task.endTime,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd")));
		//tel
		tv_task_tel.setText(Html.fromHtml(context.getResources().getString(R.string.task_detail_tel_format,taskBean.task.hotLine)));
		tv_task_tel.setOnClickListener(this);

		//解析数据
		int isize = taskBean.taskSetps==null ?0:taskBean.taskSetps.size();
		for(int i=0;i<isize;i++){
			TaskSpecBeanInfo bean =  taskBean.taskSetps.get(i);
			int index = bean.getSortNo()-1;
			if(bean.getSetpType()==1){
				//步骤
				mFlowpathData.add(index,bean.getContent());
			}else if(bean.getSetpType()==2){
				//说明
				mExplainData.add(index,bean.getContent());
			}else if(bean.getSetpType()==3){
			   //细则
				mLinksData.add(index,bean.getContent());
			}
		}
		//测试数据
		for(int i=0;i<10;i++){
			mFlowpathData.add(mFlowpathData.get(0));
			mExplainData.add(mExplainData.get(0));
			mLinksData.add(mLinksData.get(0));
		}
		mTaskFlowPathAdapter.setTaskData(mFlowpathData);
		mTaskExplainAdapter.setTaskData(mExplainData);
		mTaskLinkAdapter.setTaskData(mLinksData);

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
				btn_receive_task.setText("立即参与");
				isShareTask = false;
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
				btn_receive_task.setText("继续任务");
				isShareTask = false;
			}
		}
		str_shareContent = "www.baidu.com";
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
			//单纯领取任务
			submitTask_1();
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
			//单纯继续领取任务
			submitTask_3();
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
	startActivity(mIntent);
	finish();
}
	@Override
	public void onNoData() {
		getInitData();
	}

}
