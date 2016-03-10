package com.renrentui.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import base.BaseActivity;

import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQUserId;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSUserInfo;
import com.renrentui.tools.FileUtils;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.activity.NoGoingTaskActicity;
import com.task.activity.PersonalAddInfoActivity;
import com.umeng.analytics.MobclickAgent;

import org.w3c.dom.Text;

/**
 * 欢迎页
 * 
 * @author llp
 * 
 */
public class WelcomeActivity extends BaseActivity {
	private static final int TAG_HANDLER_START_ACTIVITY = 1;
	public Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		//goOtherActivity();
		FileUtils.createDirectory(FileUtils.getSaveFilePath());
		//GetCity.getCity(context).getCNBylocation();
//		setStartHomeActivity();
	}
	private void setStartHomeActivity() {
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				int iWhate = msg.what;
				if (iWhate == TAG_HANDLER_START_ACTIVITY) {
					this.removeMessages(TAG_HANDLER_START_ACTIVITY);
					Intent mIntent = new Intent();
					mIntent.setClass(WelcomeActivity.this,NoGoingTaskActicity.class);
					//mIntent.setClass(WelcomeActivity.this,PersonalAddInfoActivity.class);

					startActivity(mIntent);
					finish();
				} else {
					this.removeMessages(TAG_HANDLER_START_ACTIVITY);
					finish();
				}
			}
		};
		mHandler.sendEmptyMessageDelayed(TAG_HANDLER_START_ACTIVITY, 2 * 1000);
	}

	/**
	 * 快速跳转
	 */
	private void setStartHomeActivityQuick(){
		Intent mIntent = new Intent();
		mIntent.setClass(WelcomeActivity.this,NoGoingTaskActicity.class);
		//mIntent.setClass(WelcomeActivity.this,PersonalAddInfoActivity.class);

		startActivity(mIntent);
		finish();
	}

	/**
	 * 欢迎页动画效果
	 */
//	private void goOtherActivity() {
//		ImageView logoImage = (ImageView) this.findViewById(R.id.imv_splash);
//		AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
//		alphaAnimation.setDuration(2000);
//		logoImage.startAnimation(alphaAnimation);
//		alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
//
//			@Override
//			public void onAnimationStart(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animation animation) {
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animation animation) {
//				Intent intent;
//				intent = new Intent(context, NoGoingTaskActicity.class);
//				startActivity(intent);
//				WelcomeActivity.this.finish();
//			}
//		});
//	}

	@Override
	protected void onResume() {
		super.onResume();
		if(strUserId.equals("0")){
			//用户没有登录
			setStartHomeActivity();
		}else{
			getUserInfo();
		}
	}

	/**
	 * 获取用户信息
	 */
	private void getUserInfo(){
		ApiUtil.Request(new RQBaseModel<RQUserId, RSUserInfo>(context,
				new RQUserId(strUserId),
				new RSUserInfo(), ApiNames.获取用户信息.getValue(), RequestType.POST,
				rqHandler_userinfo));
	}
	private RQHandler<RSUserInfo> rqHandler_userinfo = new RQHandler<RSUserInfo>(
			new IRqHandlerMsg<RSUserInfo>() {

				@Override
				public void onBefore() {
				}

				@Override
				public void onNetworknotvalide() {
					setStartHomeActivityQuick();
				}

				@SuppressWarnings("deprecation")
				@Override
				public void onSuccess(RSUserInfo t) {
					String strUserName = t.data.clienterName;//用户名
					String strDirthDay =t.data.birthDay;//出生日期
					String strSex = t.data.sex;//性别
					if(!TextUtils.isEmpty(strDirthDay) && !TextUtils.isEmpty(strDirthDay)){
						//比较齐全
						setStartHomeActivityQuick();
					}else{
						//信息不全
						Intent mIntent = new Intent();
						mIntent.setClass(WelcomeActivity.this,PersonalAddInfoActivity.class);
						mIntent.putExtra("UserName", strUserName);
						mIntent.putExtra("sex", strSex);
						mIntent.putExtra("birthDay",strDirthDay);
						startActivity(mIntent);
						finish();
					}

				}

				@Override
				public void onSericeErr(RSUserInfo t) {
					setStartHomeActivityQuick();
				}

				@Override
				public void onSericeExp() {
					setStartHomeActivityQuick();
				}
			});



}
