package com.task.activity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.renrentui.app.MyApplication;
import com.renrentui.app.R;
import com.renrentui.controls.PullToRefreshView;
import com.renrentui.controls.PullToRefreshView.OnFooterRefreshListener;
import com.renrentui.controls.PullToRefreshView.OnHeaderRefreshListener;
import com.renrentui.db.Bean.CityDBBean;
import com.renrentui.db.CityDBManager;
import com.renrentui.interfaces.INodata;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGetCityMode;
import com.renrentui.requestmodel.RQGetNoGoingTask;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.CityFirstLetterRegionModel;
import com.renrentui.resultmodel.CityRegionModel;
import com.renrentui.resultmodel.NoGoingTaskInfo;
import com.renrentui.resultmodel.RSGetCityModel;
import com.renrentui.resultmodel.RSGetNoGoingTask;
import com.renrentui.tools.Constants;
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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
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
	//定位
	public MyLocationListenner myListener = new MyLocationListenner();
	public CityDBManager mCityDBManager =null;//城市数据库
	public ArrayList<CityDBBean> cityAllData= new ArrayList<CityDBBean>();

	private RQHandler<RSGetNoGoingTask> rqHandler_getNoGoingTask = new RQHandler<>(
			new IRqHandlerMsg<RSGetNoGoingTask>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
					pulltorefresh_nogoing_taskList.onHeaderRefreshComplete();
					pulltorefresh_nogoing_taskList.onFooterRefreshComplete();
				}

				@Override
				public void onNetworknotvalide() {
					//网络无效
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
					//服务器返回错误
					hideProgressDialog();
					pulltorefresh_nogoing_taskList.setVisibility(View.GONE);
					NoGoingTaskActicity.this.onNodata(ResultMsgType.ServiceErr,
							"刷新", "数据加载失败！", null);
				}

				@Override
				public void onSericeExp() {
					//服务器返回为空
					hideProgressDialog();
					pulltorefresh_nogoing_taskList.setVisibility(View.GONE);
					NoGoingTaskActicity.this.onNodata(ResultMsgType.ServiceExp,
							"刷新", "数据加载失败！", null);
				}
			});
	//请求城市
	private RQHandler<RSGetCityModel> rqHandler_getCity = new RQHandler<>(
			new IRqHandlerMsg<RSGetCityModel>() {

				@Override
				public void onBefore() {
					hideProgressDialog();
				}

				@Override
				public void onNetworknotvalide() {
					//网络无效
				}

				@Override
				public void onSuccess(RSGetCityModel t) {
					if("200".equals(t.code)){
						//获取成功
						initCityInfo(t);
					}else{
						ToastUtil.show(context,t.msg);
					}
				}

				@Override
				public void onSericeErr(RSGetCityModel t) {
					//服务器返回错误
					//hideProgressDialog();
				}

				@Override
				public void onSericeExp() {
					//服务器返回为空
					//hideProgressDialog();
				}
			});
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ExitApplication.getInstance().homeExit();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nogoing_task);
		super.init();
		initControl();
		checkoutGPSStatus();
		initLocation();
		lv_no_going_task.setOverScrollMode(View.OVER_SCROLL_NEVER);
		noGoingTaskInfos = new ArrayList<NoGoingTaskInfo>();
		getNoGoingAdapter = new GetNoGoingAdapter(context, noGoingTaskInfos);
		lv_no_going_task.setAdapter(getNoGoingAdapter);
		mCityDBManager  = new CityDBManager(context);
		getCityData();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (Utils.getUserDTO(context) != null){
			userId = Utils.getUserDTO(context).data.userId;
		}else {
			userId = "0";
		}
		
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
		tv_user_address = (TextView) findViewById(R.id.tv_user_address);
		tv_user_address.setText(GetCity.cityName);
		tv_user_address.setOnClickListener(this);
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
	public void getCityData() {
		String strVersionr = mCityDBManager.getCiytVersion();
		ApiUtil.Request(new RQBaseModel<RQGetCityMode, RSGetCityModel>(
				context, new RQGetCityMode(strVersionr),
				new RSGetCityModel(), ApiNames.获取城市信息列表.getValue(),
				RequestType.POST, rqHandler_getCity));
	}

	/**
	 * 获取数据
	 */
	public void getInitData() {
		showProgressDialog();
		ApiUtil.Request(new RQBaseModel<RQGetNoGoingTask, RSGetNoGoingTask>(
				context, new RQGetNoGoingTask(userId, "0", "210200"),
				new RSGetNoGoingTask(), ApiNames.获取所有可领取任务.getValue(),
				RequestType.POST, rqHandler_getNoGoingTask));
		pageindex = 1;
	}

	@Override
	public void onNoData() {
		getInitData();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		getMoreData();
	}

	/**
	 * 获取更多数据
	 */
	private void getMoreData() {
//		ApiUtil.Request(new RQBaseModel<RQGetNoGoingTask, RSGetNoGoingTask>(
//				context, new RQGetNoGoingTask(userId, 0.0f, 0.0f, nextId),
//				new RSGetNoGoingTask(), ApiNames.获取所有未领取任务.getValue(),
//				RequestType.POST, rqHandler_getNoGoingTask));
//		pageindex++;

		ApiUtil.Request(new RQBaseModel<RQGetNoGoingTask, RSGetNoGoingTask>(
				context, new RQGetNoGoingTask(userId, nextId,"110100"),
				new RSGetNoGoingTask(), ApiNames.获取所有可领取任务.getValue(),
				RequestType.POST, rqHandler_getNoGoingTask));
		pageindex++;
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		getInitData();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		if(v.getId()==R.id.tv_user_address){
			//展示城市信息
			intent =new Intent(context,ShowCityActivity.class);

		}else if(TextUtils.isEmpty(userId) ||"0".equals(userId)){
			intent = new Intent(context, LoginActivity.class);
		}else {
			switch (v.getId()) {
				case R.id.tv_to_login:// 点击登录按钮
					intent = new Intent(context, LoginActivity.class);
					break;
				case R.id.iv_to_personal_center:// 点击个人中心按钮
					intent = new Intent(context, PersonalCenterActivity.class);
					break;
				case R.id.iv_to_my_task:// 点击书签进入我的任务
					intent = new Intent(context, MyTaskFramentActivity.class);
					break;
			}
		}
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 点击两次退出应用程序处理逻辑
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (!isQuit ) {
				isQuit = true;
				Toast.makeText(context, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 手动开启gps
		if (Constants.OPEN_GPS_SERVER_RESULT_CODE == requestCode) {
			mMyApplication.getmLocClient().start();
		}
	}
//	========================城市信息==============
	public void initCityInfo(RSGetCityModel t){
		if(t!=null && t.data!=null ){
			String strVersion = t.data.version;
			//热门城市
			ArrayList<CityRegionModel> hotCityList = t.data.hotRegionModel;
			int iHSize = hotCityList==null ?0: hotCityList.size();
			for(int i=0;i<iHSize;i++){
				CityRegionModel hBean = hotCityList.get(i);
				CityDBBean beanDB = new CityDBBean();
				beanDB.setCITY_NAME(hBean.name);
				beanDB.setCITY_FIRST_LETTER("2");
				beanDB.setCITY_TYPE("2");
				beanDB.setVERSION(strVersion);
				beanDB.setCITY_CODE(hBean.code);
				cityAllData.add(beanDB);
			}
			//
			//列表
			ArrayList<CityFirstLetterRegionModel> mFirsetLetterCityList = t.data.firstLetterRegionModel;
			int isize = mFirsetLetterCityList==null ?0: mFirsetLetterCityList.size();
			for(int i=0;i<isize;i++){
				CityFirstLetterRegionModel mLetterData = mFirsetLetterCityList.get(i);
				String str_letter = mLetterData.firstLetter;
				ArrayList<CityRegionModel> mCityData= mLetterData.regionModel;
				int icSize = mCityData==null?0:mCityData.size();
				for(int j=0;j<icSize;j++){
					CityRegionModel cBean = mCityData.get(j);
					CityDBBean beanDB = new CityDBBean();
					beanDB.setCITY_NAME(cBean.name);
					beanDB.setCITY_FIRST_LETTER(str_letter);
					beanDB.setCITY_TYPE("3");
					beanDB.setVERSION(strVersion);
					beanDB.setCITY_CODE(cBean.code);
					cityAllData.add(beanDB);
				}
			}
			//存入数据库
			mCityDBManager.delCityList();
			mCityDBManager.addCityList(cityAllData,strVersion);
		}
	}
//==============================定位信息===================================
	/**
	 * 检车是否开启gps
	 */
	private void checkoutGPSStatus() {
		if (!Utils.isOpenGPS(context)) {
			try {
				// 强制开启gps
				Utils.openGPS(context);
			} catch (Exception e) {
				// 手动开启gps
				openGPSByHand();
			}
		}
	}
	/**
	 * 手动开启gps
	 */
	private void openGPSByHand() {
		new AlertDialog.Builder(context).setTitle("提示")// 设置标题
				.setMessage("请开启GPS服务")// 设置提示消息
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 设置确定的按键
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivityForResult(intent,Constants.OPEN_GPS_SERVER_RESULT_CODE);

					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {// 设置取消按键
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).setCancelable(false)// 设置按返回键是否响应返回，这是是不响应
				.show();// 显示
	}
	/**
	 * 初始化定位
	 */
	public void initLocation() {
		try {
			mMyApplication.getmLocClient().registerLocationListener(myListener);
				LocationClientOption option = new LocationClientOption();
				option.setOpenGps(true);// 开启gps
				option.setCoorType("bd09ll");// 返回百度经纬度坐标
				option.setIsNeedAddress(true);
				option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
				option.setScanSpan(1000);
				mMyApplication.getmLocClient().setLocOption(option);
			mMyApplication.getmLocClient().start();
		}catch (Exception e){

		}finally {

		}
	}
public class MyLocationListenner implements BDLocationListener {

	@Override
	public void onReceiveLocation(BDLocation location) {
		if(location!=null && !TextUtils.isEmpty(location.getCity())){
			//定位成功
			mMyApplication.getmLocClient().stop();
			CityRegionModel localLocation = new CityRegionModel();
			String StrName = location.getCity();
			String strCode = mCityDBManager.getCityCodeByName(StrName);
			localLocation.code = strCode;
			localLocation.name = StrName;
			//MyApplication.setmLocalLocation(localLocation);
			ToastUtil.show(context,"<<<<<<<>>>>>>>>"+StrName);
		}else{
			//定位失败
			mMyApplication.getmLocClient().stop();
			ToastUtil.show(context,"<<<<<<<---fail--->>>>>>>>");
		}
	}
}
}