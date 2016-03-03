package com.task.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import com.renrentui.app.MyApplication;
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
import com.share.ShareUtils;
import com.share.bean.ShareBean;
import com.task.adapter.TaskExplainAdapter;
import com.task.adapter.TaskFlowPathAdapter;
import com.task.adapter.TaskFriendGridAdapter;
import com.task.adapter.TaskLinksAdapter;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.user.activity.LoginActivity;


import org.w3c.dom.Text;

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

	private View mContentView;//内容展示部分
	private ImageView icon_pusher;// 任务发布商logo
	private LinearLayout ll_amount;//任务单价
	private TextView tv_Amount;// 任务单价

	private TextView tv_pusher_taskName;// 任务名称
	private TextView mIV_pusher_type_flag;//任务类型

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


	//咨询电话
	private View mLine_task_detail_tel_top;
	private  LinearLayout mLL_task_tel;
	//参与人
	private View mLine_task_detail_friend_top;
	private LinearLayout mLL_detail_friends;
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
	private boolean isShareTask =false;//是否是分享型任务(1:签约  2和3 是分享型)
	private String str_shareContent ="";//分享的内容
	private String str_taskName;//任务名称
	private String str_ctId = "";//地推关系id
	private int i_isHad = 1;//1:已领取  0：未领取
	private String str_scanTip;//扫码说明
	private String str_reminder ;//温馨提示
	private String str_taskStatus="";//状态信息
	ShareUtils mShareUtils;
	private  String strShare_title;
	private String strShare_content;


//	任务详情Handler
	private RQHandler<RSGetTaskDetailInfoNew> rqHandler_getTaskDetailInfo = new RQHandler<>(
			new IRqHandlerMsg<RSGetTaskDetailInfoNew>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
				}

				@Override
				public void onNetworknotvalide() {
					mContentView.setVisibility(View.GONE);
					btn_receive_task.setVisibility(View.GONE);
					TaskDetailInfoNewActivity.this.onNodata(
							ResultMsgType.NetworkNotValide, R.drawable.icon_not_task, 0,"", null);
				}

				@Override
				public void onSuccess(RSGetTaskDetailInfoNew t) {
					mContentView.setVisibility(View.VISIBLE);
					TaskDetailInfoNewActivity.this.hideLayoutNoda();
					rsGetTaskDetailInfo = t;
					initData(rsGetTaskDetailInfo.data);
					hideProgressDialog();
				}

				@Override
				public void onSericeErr(RSGetTaskDetailInfoNew t) {
					mContentView.setVisibility(View.GONE);
					btn_receive_task.setVisibility(View.GONE);
					TaskDetailInfoNewActivity.this.onNodata(
							ResultMsgType.ServiceErr, R.drawable.icon_not_task, R.string.every_no_data_error,"",
							TaskDetailInfoNewActivity.this);
				}

				@Override
				public void onSericeExp() {
					mContentView.setVisibility(View.GONE);
					btn_receive_task.setVisibility(View.GONE);
					TaskDetailInfoNewActivity.this.onNodata(
							ResultMsgType.ServiceErr, R.drawable.icon_not_task, R.string.every_no_data_error,"",
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
						btn_receive_task.setText("分享二维码");
					}else{
						//签约
						btn_receive_task.setText("继续任务");
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
		taskId = intent.getStringExtra("TaskId");
		str_taskName = intent.getStringExtra("TaskName");
		str_ctId = intent.getStringExtra("ctId");
		initControl();
		//getInitData();
	}
	@Override
	protected void onStart() {
		super.onStart();
		if (Utils.getUserDTO(context) != null && Utils.getUserDTO(context).data!=null){
			userId = Utils.getUserDTO(context).data.userId;
		}
		else {
			userId = "0";
		}
		getInitData();
	}
	@Override
	protected void onResume() {
		super.onResume();
//		if (Utils.getUserDTO(context) != null){
//			userId = Utils.getUserDTO(context).data.userId;
//		}
//		else {
//			userId = "0";
//		}
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
		if(mTV_title_right!=null){
			mTV_title_right.setVisibility(View.INVISIBLE);
			mTV_title_right.setOnClickListener(this);
			mTV_title_right.setText("分享");
		}
		mContentView = findViewById(R.id.layout_data);
		icon_pusher = (ImageView) findViewById(R.id.icon_pusher);
		ll_amount =(LinearLayout)findViewById(R.id.ll_amount);
		tv_Amount = (TextView)findViewById(R.id.tv_amount);

		tv_pusher_taskName = (TextView) findViewById(R.id.tv_pusher_taskName);
		mIV_pusher_type_flag = (TextView)findViewById(R.id.iv_flag);

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



		btn_receive_task = (Button) findViewById(R.id.btn_task_get);
		btn_receive_task.setOnClickListener(this);

		mTaskExplainAdapter = new TaskExplainAdapter(context,mExplainData);
		mTaskFlowPathAdapter = new TaskFlowPathAdapter(context,mFlowpathData);
		mTaskLinkAdapter = new TaskLinksAdapter(context,mLinksData);

		lv_task_flowpath.setAdapter(mTaskFlowPathAdapter);
		lv_task_explain.setAdapter(mTaskExplainAdapter);
		lv_task_detail_link.setAdapter(mTaskLinkAdapter);

		mLine_task_detail_tel_top = findViewById(R.id.line_detail_tel_top);
		mLL_task_tel = (LinearLayout)findViewById(R.id.ll_detal_tel);

		mTV_task_friend_flag = (TextView)findViewById(R.id.tv_task_friend_flag);
		mTaskFriendGridView = (GridView)findViewById(R.id.gv_task_friend);
		mLine_task_detail_friend_top = findViewById(R.id.line_detail_friend_top);
		mLL_detail_friends = (LinearLayout)findViewById(R.id.ll_detail_friend);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_task_get:
				if(isLogin()){
					submitTaskDetail(rsGetTaskDetailInfo.data.task.taskType,i_isHad);
				}else{
					Intent intent = new Intent(context,LoginActivity.class);
					intent.putExtra("gotomain",false);
                    context.startActivity(intent);
				}
				break;
			case R.id.tv_task_tel:
				//电话
				Utils.callPhone(context,rsGetTaskDetailInfo.data.task.hotLine.trim());
				break;
			case R.id.iv_title_left:
				finish();
				break;
			case R.id.tv_title_right:
				//分享
				if(!TextUtils.isEmpty(rsGetTaskDetailInfo.data.task.taskTitle)){
					showShareDisplay();
				}
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
		mTV_title_right.setVisibility(View.VISIBLE);
		if (Util.IsNotNUll(taskBean.task.logo )&& Utils.checkUrl(taskBean.task.logo)) {
			ImageLoadManager.getLoaderInstace().disPlayNormalImg(taskBean.task.logo,
					icon_pusher, R.drawable.pusher_logo);
		} else {
			icon_pusher.setImageResource(R.drawable.pusher_logo);
		}
		tv_Amount.setText(taskBean.task.getAmount());

		tv_pusher_taskName.setText(taskBean.task.taskTitle);
		strShare_title = taskBean.task.taskTitle+"-"+taskBean.task.getAmount()+"元/次";
		strShare_content="最靠谱的资源共享平台，用最少的时间，赚取最多的财富";
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

//		if(taskBean.task.taskType==1){
////签约
//			mIV_pusher_type_flag.setImageResource(R.drawable.team_qianyue);
//
//		}else if(taskBean.task.taskType==2){
//			//分享
//			mIV_pusher_type_flag.setImageResource(R.drawable.team_share);
//		}else if(taskBean.task.taskType==3){
//			// 下载
//			mIV_pusher_type_flag.setImageResource(R.drawable.team_down);
//		}else{
//			mIV_pusher_type_flag.setImageResource(R.drawable.team_qianyue);
//		}
		mIV_pusher_type_flag.setText(taskBean.task.tagName);
		if(!TextUtils.isEmpty(taskBean.task.tagColorCode)){
			mIV_pusher_type_flag.setBackgroundColor(Color.parseColor(taskBean.task.tagColorCode));
		}
		//审核
		tv_task_examine.setText(context.getResources().getString(R.string.task_detail_examine_format,taskBean.task.auditCycle));
		//截止日期
		tv_deadline_time.setText(context.getResources().getString(R.string.task_detail_dealtime_format, TimeUtils.StringPattern(taskBean.task.endTime,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd")));
		//预计时间
		tv_forecast_time.setText(context.getResources().getString(R.string.task_detail_forecasttime_format,taskBean.task.estimatedTime));

		//tel
		if(TextUtils.isEmpty(taskBean.task.hotLine)){
			tv_task_tel.setVisibility(View.GONE);
			mLine_task_detail_tel_top.setVisibility(View.GONE);
			mLL_task_tel.setVisibility(View.GONE);
		}else {
			tv_task_tel.setVisibility(View.VISIBLE);
			mLine_task_detail_tel_top.setVisibility(View.VISIBLE);
			mLL_task_tel.setVisibility(View.VISIBLE);
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
		//流程步骤
		if(mFlowpathData==null || mFlowpathData.size()==0){
			lv_task_flowpath.setVisibility(View.GONE);
		}else{
			lv_task_flowpath.setVisibility(View.VISIBLE);
		}
		//补充说明
		if(mExplainData==null ||mExplainData.size()==0){
			mLine_task_explain_top.setVisibility(View.GONE);
			lv_task_explain.setVisibility(View.GONE);
		}else{
			mLine_task_explain_top.setVisibility(View.VISIBLE);
			lv_task_explain.setVisibility(View.VISIBLE);
		}
		//连接
		if(mLinksData==null ||mLinksData.size()==0){
			mLine_detail_link_top.setVisibility(View.GONE);
			lv_task_detail_link.setVisibility(View.GONE);
		}else{
			mLine_detail_link_top.setVisibility(View.VISIBLE);
			lv_task_detail_link.setVisibility(View.VISIBLE);
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
			ll_task_description.setVisibility(View.VISIBLE);
		}
//		if("1".equals(str_taskStatus)){
			btn_receive_task.setVisibility(View.VISIBLE);
//		}else{
//			btn_receive_task.setVisibility(View.GONE);
//		}


if("1".equals(str_taskStatus)) {
	//任务非终止
	if (i_isHad == 0) {
		//未领取
		if (taskBean.task.taskType == 1) {
			//签约
			btn_receive_task.setText("立即领取");
			isShareTask = false;
		} else if (taskBean.task.taskType == 2) {
			//分享
			btn_receive_task.setText("立即领取");
			isShareTask = true;
		} else if (taskBean.task.taskType == 3) {
			//下载
			btn_receive_task.setText("立即领取");
			isShareTask = true;
		}
	} else if (taskBean.task.isHad == 1) {
		//已领取
		if (taskBean.task.taskType == 1) {
			//签约
			btn_receive_task.setText("继续任务");
			isShareTask = false;
		} else if (taskBean.task.taskType == 2) {
			//分享
			btn_receive_task.setText("分享二维码");
			isShareTask = true;
		} else if (taskBean.task.taskType == 3) {
			//下载
			btn_receive_task.setText("分享二维码");
			isShareTask = true;
		}
	}
}else{
//终止
	if (i_isHad == 0) {
		//未领取
		if (taskBean.task.taskType == 1) {
			//签约
			btn_receive_task.setText("立即领取");
			isShareTask = false;
		} else if (taskBean.task.taskType == 2) {
			//分享
			btn_receive_task.setText("立即领取");
			isShareTask = true;
		} else if (taskBean.task.taskType == 3) {
			//下载
			btn_receive_task.setText("立即领取");
			isShareTask = true;
		}
	} else if (taskBean.task.isHad == 1) {
		//已领取
		if (taskBean.task.taskType == 1) {
			//签约
			btn_receive_task.setText("历史资料");
			isShareTask = false;
		} else if (taskBean.task.taskType == 2) {
			//分享
			btn_receive_task.setText("分享二维码");
			isShareTask = true;
		} else if (taskBean.task.taskType == 3) {
			//下载
			btn_receive_task.setText("分享二维码");
			isShareTask = true;
		}
	}
}
		str_shareContent = taskBean.task.downUrl;

		//解析任务参与人
		List<PartnerList>  mPartnerList = taskBean.partnerList;
		mLine_task_detail_friend_top.setVisibility(View.VISIBLE);
		mTaskFriendGridView.setVisibility(View.GONE);
		mLL_detail_friends.setVisibility(View.VISIBLE);
		if(mPartnerList==null || mPartnerList.size()==0){
			mTV_task_friend_flag.setText("还没有地推员参与该任务~");
			mTV_task_friend_flag.setTextColor(context.getResources().getColor(R.color.tv_order_color_3));
			mTaskFriendGridView.setVisibility(View.GONE);
		}else{
			//数据正常
			mTV_task_friend_flag.setText(context.getResources().getString(R.string.my_tasknew_friend_flag, taskBean.getPartnerTotal()));
			Drawable rightDrawable = context.getResources().getDrawable(R.drawable.go);
			mTV_task_friend_flag.setCompoundDrawablesWithIntrinsicBounds(null,null,rightDrawable,null);
			mTaskFriendGridView.setVisibility(View.GONE);
			mTV_task_friend_flag.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent mIntent = new Intent(context, TaskMyFriendListViewActivity.class);
					mIntent.putExtra("TASK_ID", taskId);
					context.startActivity(mIntent);
				}
			});
//			mTaskFriendGridAdapter = new TaskFriendGridAdapter(context,0);
//			mTaskFriendGridAdapter.setTaskFriendTaskId(taskId);
//			mTaskFriendGridView.setAdapter(mTaskFriendGridAdapter);
//			mTaskFriendGridAdapter.setData(mPartnerList);
//			mTaskFriendGridAdapter.setTaskDetailInfoNewData(rsGetTaskDetailInfo.data);
		}
	}

	/**
	 * 领取任务
	 * @param type
	 * @param isHad
	 */
private  void submitTaskDetail(int type,int isHad){
	if(isHad==0){
		//未领取
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
				Utils.getUserDTO(context).data.userId, taskId, MyApplication.getCurrentCity().code),
				new RSReceiveTask(), ApiNames.领取任务.getValue(),
				RequestType.POST, rqHandler_receiveTask));
	}
	@Override
	public void onNoData() {
		getInitData();
	}
	/**
	 * 详细页分享
	 */
	public void showShareDisplay(){
		ShareBean mShareBean = new ShareBean();
		mShareBean.setStrTitle(strShare_title);
		mShareBean.setStrText(strShare_content);
		mShareBean.setStrTargetUrl("http://m.renrentui.me");
		mShareBean.setUmImage(new UMImage(context, "http://m.renrentui.me/img/144_qs.png"));
		mShareUtils = new ShareUtils(context,TaskDetailInfoNewActivity.this,mShareBean);
		SHARE_MEDIA[] arrs =new SHARE_MEDIA[5];
		arrs[0] = SHARE_MEDIA.WEIXIN;
		arrs[1] =SHARE_MEDIA.WEIXIN_CIRCLE;
		arrs[2] = SHARE_MEDIA.QQ;
		arrs[3] = SHARE_MEDIA.QZONE;
		arrs[4] = SHARE_MEDIA.SINA;
		mShareUtils.showDefaultShareBoard(arrs, true);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mShareUtils.UMShareActivityResult(requestCode,resultCode,data);
	}
}
