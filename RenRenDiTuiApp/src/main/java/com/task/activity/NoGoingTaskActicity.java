package com.task.activity;

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
import com.renrentui.requestmodel.RQGetMyMessage;
import com.renrentui.requestmodel.RQGetNoGoingTask;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.requestmodel.ResultMsgType;
import com.renrentui.resultmodel.CityFirstLetterRegionModel;
import com.renrentui.resultmodel.CityRegionModel;
import com.renrentui.resultmodel.NoGoingTaskInfo;
import com.renrentui.resultmodel.RSGetCityModel;
import com.renrentui.resultmodel.RSGetMyMessage;
import com.renrentui.resultmodel.RSGetNoGoingTask;
import com.renrentui.tools.Constants;
import com.renrentui.tools.ExitApplication;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.GetCity;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.adapter.GetNoGoingAdapter;
import com.task.adapter.TaskSorAdapter;
import com.user.activity.LoginActivity;
import com.user.activity.PersonalCenterActivity;
import com.user.model.download.DownLoadUtils;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
	private static final String ACTION_UPDATE_APP_UMENG_FILTER = "com.task.activity.NoGoingTaskActicity.Umeng";

	private static int  isLocation = -1;//是否定位成功(-1: 没有启动定位  0：定位成功   1：定位失败)
	private static int  isGetCity = -1;//是否获取城市成功(-1: 没有获取成功  0：获取城市成功   1：获取城市失败)


//	bottom
	private LinearLayout mTab_01;
	private LinearLayout mTab_02;
	private LinearLayout mTab_03;
	private ImageView mTab_image_03;
	private TextView mTab_text_03;

	private ListView lv_no_going_task;// 待领取任务列表
	private GetNoGoingAdapter getNoGoingAdapter;// 待领取任务适配器
	private PullToRefreshView pulltorefresh_nogoing_taskList;// 上拉刷新，下拉加载
	private List<NoGoingTaskInfo> noGoingTaskInfos;// 待领取任务信息集合
	private String nextId = "";
	private int pageindex = 1;
	private boolean isQuit = false; // 退出标识
	//定位
	public MyLocationListenner myListener = new MyLocationListenner();
	public CityDBManager mCityDBManager =null;//城市数据库
	public ArrayList<CityDBBean> cityAllData= new ArrayList<CityDBBean>();
	public Handler mGetDataHander;
	public static final int TAG_WHATE_STOP = 1002;
	public static final int TAG_WHATE_CONNECTION = 1001;


	private PopupWindow mMenuPopupWindow;
	private int is_selection = 1;
	private TaskSorAdapter msortAdapter;
	private ListView menuListView;

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
				}

				@Override
				public void onNetworknotvalide() {
					//网络无效
				}

				@Override
				public void onSuccess(RSGetCityModel t) {
					if("200".equals(t.code)){
						isGetCity = 0;
						//获取成功
						initCityInfo(t);
					}else{
						isGetCity = 1;
					}
				}

				@Override
				public void onSericeErr(RSGetCityModel t) {
					//服务器返回错误
					isGetCity = 1;
				}

				@Override
				public void onSericeExp() {
					//服务器返回为空
					isGetCity = 1;
					ToastUtil.show(context, "onSericeExp");
				}
			});

//	获取未读消息数量
private RQHandler<RSGetMyMessage> rqHandler_getMyMessage = new RQHandler<>(
		new IRqHandlerMsg<RSGetMyMessage>() {

			@Override
			public void onBefore() {
			}

			@Override
			public void onNetworknotvalide() {
				//网络无效
			}

			@Override
			public void onSuccess(RSGetMyMessage t) {
				if(t.getData()>0){
					MyApplication.isMessage = true;
					mTab_image_03.setImageResource(R.drawable.bg_main_menu_tab3_2_image_layout);
				}else{
					//无信息
					MyApplication.isMessage = false;
					mTab_image_03.setImageResource(R.drawable.bg_main_menu_tab3_1_image_layout);
				}
			}

			@Override
			public void onSericeErr(RSGetMyMessage t) {
			}

			@Override
			public void onSericeExp() {

			}
		});
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ExitApplication.getInstance().homeExit();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nogoing_task);
		super.init();
		initControl();
		setPopupView();
		checkoutGPSStatus();
		initLocation();
		initHandler();
		lv_no_going_task.setOverScrollMode(View.OVER_SCROLL_NEVER);
		noGoingTaskInfos = new ArrayList<NoGoingTaskInfo>();
		getNoGoingAdapter = new GetNoGoingAdapter(context, noGoingTaskInfos);
		lv_no_going_task.setAdapter(getNoGoingAdapter);
		mCityDBManager  = new CityDBManager(context);
		getCityData();
//		升级提示信息
		DownLoadUtils.checkoutAppVersion(this, true, ACTION_UPDATE_APP_UMENG_FILTER,false);
	}

	@Override
	public void onResume() {
		super.onResume();
		if(isLogin()){
			//登录情况下获取未读信息
			getMyMessageInfo();
		}
		showProgressDialog();
		//用户信息
		if(!TextUtils.isEmpty(MyApplication.getmCurrentLocation().code) && !TextUtils.isEmpty(MyApplication.getmLocalLocation().code)){
			//地址信息完整，可以获取数据
			mTV_title_left.setText(MyApplication.getmCurrentLocation().name);
			getInitData();
		}else{
			//地址信息不完整.

		}
	}
	@Override
	public void onPause() {
		super.onPause();
		if(myListener!=null){
			mMyApplication.getmLocClient().stop();
			mMyApplication.getmLocClient().unRegisterLocationListener(myListener);
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(myListener!=null){
			mMyApplication.getmLocClient().unRegisterLocationListener(myListener);
		}
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
//		title
		if(mTV_title_left!=null){
			mTV_title_left.setText(GetCity.cityName);
			mTV_title_left.setOnClickListener(this);
			mTV_title_left.setVisibility(View.VISIBLE);
		}
		if(mTV_title_content!=null){
			mTV_title_content.setText("可接任务");
		}
		if(mTV_title_right!=null){
			mTV_title_right.setOnClickListener(this);
			mTV_title_right.setVisibility(View.VISIBLE);
			mTV_title_right.setText("排序");
			Drawable mDrawable = context.getResources().getDrawable(R.drawable.icon_order_sort);
			mDrawable.setBounds(0,0,mDrawable.getMinimumWidth(),mDrawable.getMinimumHeight());
			mTV_title_right.setCompoundDrawables(null, null, mDrawable, null);
			mTV_title_right.setCompoundDrawablePadding((int) context.getResources().getDimension(R.dimen._10dp));
			mTV_title_right.setOnClickListener(this);
		}

//		menu
		mTab_01 = (LinearLayout)findViewById(R.id.tab_01);
		mTab_01.setSelected(true);
		mTab_01.setOnClickListener(this);
		mTab_02 = (LinearLayout)findViewById(R.id.tab_02);
		mTab_02.setOnClickListener(this);
		mTab_03 = (LinearLayout)findViewById(R.id.tab_03);
		mTab_03.setOnClickListener(this);
		mTab_image_03 = (ImageView)findViewById(R.id.tab_image_03);
		mTab_image_03.setImageResource(R.drawable.bg_main_menu_tab3_1_image_layout);
		mTab_text_03 = (TextView)findViewById(R.id.tab_text_03);


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
		setCityInfo();
		ApiUtil.Request(new RQBaseModel<RQGetNoGoingTask, RSGetNoGoingTask>(
				context, new RQGetNoGoingTask(strUserId, "0", MyApplication.getmCurrentLocation().code,is_selection),
				new RSGetNoGoingTask(), ApiNames.获取所有可领取任务.getValue(),
				RequestType.POST, rqHandler_getNoGoingTask));
		pageindex = 1;
	}
	public  void setCityInfo(){
		String strLocationCode = "";//定位城市
		if(!TextUtils.isEmpty(MyApplication.getmLocalLocation().name)){
			strLocationCode =mCityDBManager.getCityCodeByName(MyApplication.getmLocalLocation().name);
		}
		String strCurrentCode = "";//当前城市
		if(!TextUtils.isEmpty(MyApplication.getmCurrentLocation().name)){
			strCurrentCode =mCityDBManager.getCityCodeByName(MyApplication.getmCurrentLocation().name);
		}
		if(TextUtils.isEmpty(MyApplication.getmCurrentLocation().name) || TextUtils.isEmpty(MyApplication.getmCurrentLocation().code)){
			MyApplication.setmCurrentLocation(MyApplication.getmLocalLocation());
		}
		//完善定位城市
		if(!TextUtils.isEmpty(strLocationCode) ){
			CityRegionModel beanCity = new CityRegionModel();
			beanCity.code=strLocationCode;
			beanCity.name=MyApplication.getmLocalLocation().name;
			MyApplication.setmLocalLocation(beanCity);
		}
		if(TextUtils.isEmpty(strCurrentCode)){
			//使用默认城市
			CityRegionModel beanCity = new CityRegionModel();
			beanCity.code="110100";
			beanCity.name="北京市";
			MyApplication.setmCurrentLocation(beanCity);
		}else{
			//个性城市
			MyApplication.getmCurrentLocation().code=strCurrentCode;
		}
		mTV_title_left.setText(MyApplication.getmCurrentLocation().name);
	}

	/**
	 * 获取未读信息
	 */
	private void getMyMessageInfo(){
		ApiUtil.Request(new RQBaseModel<RQGetMyMessage, RSGetMyMessage>(
				context, new RQGetMyMessage(strUserId),
				new RSGetMyMessage(), ApiNames.获取未读信息数量.getValue(),
				RequestType.POST, rqHandler_getMyMessage));
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
		ApiUtil.Request(new RQBaseModel<RQGetNoGoingTask, RSGetNoGoingTask>(
				context, new RQGetNoGoingTask(strUserId, nextId,MyApplication.getmCurrentLocation().code,is_selection),
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
		boolean isFinish = false;
		switch (v.getId()) {
			case R.id.tv_title_left:
				//切换城市
				intent = new Intent(context, ShowCityActivity.class);
				break;
			case R.id.tv_title_right:
				//排序
				intent = null;
				showAndDismissPopupWindow();
				break;
			case R.id.tab_01:
				//可接任务
				intent = null;
				break;
			case R.id.tab_02:
				//我的任务
				if(isLogin()){
					//intent = new Intent(context, MyTaskFramentActivity.class);
					intent = new Intent(context,MyTaskFramentNewActivity.class);
					isFinish = true;
				}else{
					intent = new Intent(context, LoginActivity.class);
				}
				break;
			case R.id.tab_03:
				//个人中心
				if(isLogin()){
					intent = new Intent(context, PersonalCenterActivity.class);
					isFinish = true;
				}else{
					intent = new Intent(context, LoginActivity.class);
				}
				break;

		}
		if(intent!=null){
			startActivity(intent);
			if(isFinish){
				finish();
			}
		}
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
//	定位
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
			MyApplication.setmLocalLocation(localLocation);
			MyApplication.setmCurrentLocation(localLocation);
			isLocation = 0;
			mGetDataHander.sendEmptyMessageAtTime(TAG_WHATE_CONNECTION,2*1000);
		}else{
			//定位失败
			isLocation = 1;
			mMyApplication.getmLocClient().stop();
			mGetDataHander.sendEmptyMessageAtTime(TAG_WHATE_CONNECTION, 2 * 1000);
			ToastUtil.show(context, "定位失败!" );
		}
	}
}
//	======================操作数据信息==================
	private void initHandler(){
		mGetDataHander = new Handler(){
			public void handleMessage(Message msg) {
				switch (msg.what){
					case  TAG_WHATE_CONNECTION:
						if(isGetCity!=-1 && isGetCity!=-1){
							//停止等待，获取任务信息
							mGetDataHander.removeMessages(TAG_WHATE_CONNECTION);
							mGetDataHander.sendEmptyMessageDelayed(TAG_WHATE_STOP, 1 * 1000);
							//获取任务数据
							getInitData();
						} else{
							mGetDataHander.sendEmptyMessageDelayed(TAG_WHATE_CONNECTION,1*1000);
						}
					break;
					case TAG_WHATE_STOP:
						mGetDataHander.removeMessages(TAG_WHATE_STOP);
						break;
				}
			}
	};
}
	//=========================windonw=======================
	/**
	 * 设置订单菜单
	 */
	private void setPopupView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.task_order_sort_layout, null);
		menuListView = (ListView) view.findViewById(R.id.lv_task_sort);
		 msortAdapter = new TaskSorAdapter(context);
		menuListView.setAdapter(msortAdapter);
		menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mMenuPopupWindow.dismiss();
				is_selection = position+1;
				mMenuPopupWindow.dismiss();
			}
		});
		menuListView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		if (mMenuPopupWindow == null) {
			mMenuPopupWindow = new PopupWindow(context);
		}
		mMenuPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_popupwindow_shape));
		mMenuPopupWindow.setWidth(menuListView.getMeasuredWidth());
		//mMenuPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
		mMenuPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		mMenuPopupWindow.setContentView(view);
		mMenuPopupWindow.setOutsideTouchable(true);
		mMenuPopupWindow.setFocusable(true);
		mMenuPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

			@Override
			public void onDismiss() {
				getInitData();
			}
		});
		mMenuPopupWindow.update();
	}
	/**
	 * 设置订单菜单
	 */
	private void setUpdataPopupView() {
		msortAdapter.setIs_selection(is_selection);
		mMenuPopupWindow.update();
	}

	/**
	 * 显示or隐藏
	 */
	private void showAndDismissPopupWindow() {
		if (mMenuPopupWindow != null && mMenuPopupWindow.isShowing()) {
			mMenuPopupWindow.dismiss();
		} else if (mMenuPopupWindow != null) {
			setUpdataPopupView();
			mMenuPopupWindow.showAsDropDown(mTV_title_right,0,20);

		}
	}

}
