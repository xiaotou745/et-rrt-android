package com.task.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.renrentui.app.R;
import com.renrentui.controls.PullToRefreshView;
import com.renrentui.controls.PullToRefreshView.OnFooterRefreshListener;
import com.renrentui.controls.PullToRefreshView.OnHeaderRefreshListener;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGetNoGoingTask;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.NoGoingTaskInfo;
import com.renrentui.resultmodel.RSGetNoGoingTask;
import com.renrentui.tools.ExitApplication;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.GetCity;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.service.GetNoGoingAdapter;
import com.user.activity.LoginActivity;
import com.user.activity.PersonalCenterActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import base.BaseActivity;

/**
 * App 首页
 * 
 * @author llp
 * 
 */
public class NoGoingTaskActicity extends BaseActivity implements
		OnHeaderRefreshListener, OnFooterRefreshListener, INodata,
		OnClickListener {

	private Context context;
	private TextView tv_user_address;// 用户当前地址
	private TextView tv_to_login;// 登录按钮
	private ListView lv_no_going_task;// 待领取任务列表
	private ImageView iv_to_personal_center;// 个人中心按钮
	private ImageView iv_to_my_task;// 我的任务
	private GetNoGoingAdapter getNoGoingAdapter;// 待领取任务适配器
	private PullToRefreshView pulltorefresh_nogoing_taskList;// 上拉刷新，下拉加载
	private List<NoGoingTaskInfo> noGoingTaskInfos;// 待领取任务信息集合
	private String nextId = "";
	private int pageindex = 1;
	private String userId;
	private boolean isQuit = false; // 退出标识

	private RQHandler<RSGetNoGoingTask> rqHandler_getNoGoingTask = new RQHandler<>(
			new IRqHandlerMsg<RSGetNoGoingTask>() {

				@Override
				public void onBefore() {
					// TODO Auto-generated method stub
					hideProgressDialog();
					pulltorefresh_nogoing_taskList.onHeaderRefreshComplete();
					pulltorefresh_nogoing_taskList.onFooterRefreshComplete();
				}

				@Override
				public void onNetworknotvalide() {
					// TODO Auto-generated method stub
					hideProgressDialog();
					pulltorefresh_nogoing_taskList.setVisibility(View.GONE);
					NoGoingTaskActicity.this.onNodata(
							ResultMsgType.NetworkNotValide, null, null, null);
				}

				@Override
				public void onSuccess(RSGetNoGoingTask t) {
					hideProgressDialog();
					NoGoingTaskActicity.this.hideLayoutNoda();
					pulltorefresh_nogoing_taskList.setVisibility(View.VISIBLE);
					if (pageindex == 1) {
						if (t.data.count == 0) {
							pulltorefresh_nogoing_taskList
									.setVisibility(View.GONE);
							NoGoingTaskActicity.this.onNodata(
									ResultMsgType.Success, "刷新", "暂无待领取任务！",
									NoGoingTaskActicity.this);
						} else {
							noGoingTaskInfos.clear();
							nextId = t.data.nextId;
							noGoingTaskInfos.addAll(t.data.content);
							getNoGoingAdapter.notifyDataSetChanged();
						}
					} else {
						if (t.data.count == 0) {
							ToastUtil.show(context, "暂无更多数据");
						} else {
							nextId = t.data.nextId;
							noGoingTaskInfos.addAll(t.data.content);
							getNoGoingAdapter.notifyDataSetChanged();
						}
					}
				}

				@Override
				public void onSericeErr(RSGetNoGoingTask t) {
					hideProgressDialog();
					pulltorefresh_nogoing_taskList.setVisibility(View.GONE);
					NoGoingTaskActicity.this.onNodata(ResultMsgType.ServiceErr,
							"刷新", "数据加载失败！", null);
				}

				@Override
				public void onSericeExp() {
					hideProgressDialog();
					pulltorefresh_nogoing_taskList.setVisibility(View.GONE);
					NoGoingTaskActicity.this.onNodata(ResultMsgType.ServiceExp,
							"刷新", "数据加载失败！", null);
				}
			});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ExitApplication.getInstance().homeExit();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nogoing_task);
		super.init();
		initControl();
		lv_no_going_task.setOverScrollMode(View.OVER_SCROLL_NEVER);
		noGoingTaskInfos = new ArrayList<NoGoingTaskInfo>();
		getNoGoingAdapter = new GetNoGoingAdapter(context, noGoingTaskInfos);
		lv_no_going_task.setAdapter(getNoGoingAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (Utils.getUserDTO(context) != null)
			userId = Utils.getUserDTO(context).data.userId;
		else
			userId = "0";
		
		if (!userId.equals("0")) {
			tv_to_login.setVisibility(View.GONE);
			iv_to_personal_center.setVisibility(View.VISIBLE);
			iv_to_my_task.setVisibility(View.VISIBLE);
		} else {
			iv_to_personal_center.setVisibility(View.GONE);
			tv_to_login.setVisibility(View.VISIBLE);
			iv_to_my_task.setVisibility(View.GONE);

		}
		getInitData();
		if(Util.IsNotNUll(tv_user_address.getText().toString())){
			tv_user_address.setText("北京市");
		}
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		context = this;
		tv_user_address = (TextView) findViewById(R.id.tv_user_address);
		tv_user_address.setText(GetCity.cityName);
		iv_to_personal_center = (ImageView) findViewById(R.id.iv_to_personal_center);
		iv_to_personal_center.setOnClickListener(this);
		iv_to_my_task = (ImageView) findViewById(R.id.iv_to_my_task);
		iv_to_my_task.setOnClickListener(this);
		tv_to_login = (TextView) findViewById(R.id.tv_to_login);
		tv_to_login.setOnClickListener(this);
		pulltorefresh_nogoing_taskList = (PullToRefreshView) findViewById(R.id.pulltorefresh_nogoing_taskList);
		pulltorefresh_nogoing_taskList.setOnHeaderRefreshListener(this);
		pulltorefresh_nogoing_taskList.setOnFooterRefreshListener(this);
		lv_no_going_task = (ListView) findViewById(R.id.lv_no_going_task);
	}

	/**
	 * 获取数据
	 */
	public void getInitData() {
		showProgressDialog();
		ApiUtil.Request(new RQBaseModel<RQGetNoGoingTask, RSGetNoGoingTask>(
				context, new RQGetNoGoingTask(userId, 0.0f, 0.0f, "0"),
				new RSGetNoGoingTask(), ApiNames.获取所有未领取任务.getValue(),
				RequestType.POST, rqHandler_getNoGoingTask));
		pageindex = 1;
	}

	@Override
	public void onNoData() {
		// TODO Auto-generated method stub
		showProgressDialog();
		getInitData();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		getMoreData();
	}

	/**
	 * 获取更多数据
	 */
	private void getMoreData() {
		ApiUtil.Request(new RQBaseModel<RQGetNoGoingTask, RSGetNoGoingTask>(
				context, new RQGetNoGoingTask(userId, 0.0f, 0.0f, nextId),
				new RSGetNoGoingTask(), ApiNames.获取所有未领取任务.getValue(),
				RequestType.POST, rqHandler_getNoGoingTask));
		pageindex++;
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		getInitData();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.tv_to_login:// 点击登录按钮
			intent = new Intent(context, LoginActivity.class);
			break;
		case R.id.iv_to_personal_center:// 点击个人中心按钮
			intent = new Intent(context, PersonalCenterActivity.class);
			break;
		case R.id.iv_to_my_task:// 点击书签进入我的任务
			intent = new Intent(context, MyTaskMainActivity.class);
			break;
		}
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 点击两次退出应用程序处理逻辑
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (isQuit == false) {
				isQuit = true;
				Toast.makeText(context, "再按一次返回键退出程序", 1).show();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						isQuit = false;
					}
				};
				new Timer().schedule(task, 2000);
			} else {
				ExitApplication.getInstance().exit();
				NoGoingTaskActicity.this.finish();
				System.exit(0);
			}
		}
		return true;
	}

}
