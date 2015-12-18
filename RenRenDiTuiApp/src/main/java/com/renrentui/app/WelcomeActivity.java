package com.renrentui.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import base.BaseActivity;

import com.renrentui.tools.FileUtils;
import com.renrentui.util.GetCity;
import com.task.activity.NoGoingTaskActicity;

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
		setStartHomeActivity();
	}
	private void setStartHomeActivity() {
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				int iWhate = msg.what;
				if (iWhate == TAG_HANDLER_START_ACTIVITY) {
					this.removeMessages(TAG_HANDLER_START_ACTIVITY);
					Intent mIntent = new Intent();
					mIntent.setClass(WelcomeActivity.this, NoGoingTaskActicity.class);
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

}
