package com.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.util.Utils;
import com.user.model.download.DownLoadUtils;
import com.user.service.CustomerServiceDialog;

/**
 * 关于我们界面
 * 
 * @author llp
 * 
 */
public class AboutMeActivity extends BaseActivity implements OnClickListener {
	private Context context;
	private TextView tv_version;// 版本号控件
	private TextView tv_help;// 帮助
	private RelativeLayout tv_version_update;// 版本更新
	private TextView tv_customer_service_center;// 客服中心

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_me);
		super.init();
		initControl();
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		context = this;
		tv_version = (TextView) findViewById(R.id.tv_version);
		tv_version.setText("V" + Utils.getVersion(context));
		tv_help = (TextView) findViewById(R.id.tv_help);
		tv_help.setOnClickListener(this);
		tv_version_update = (RelativeLayout) findViewById(R.id.tv_version_update);
		tv_version_update.setOnClickListener(this);
		tv_customer_service_center = (TextView) findViewById(R.id.tv_customer_service_center);
		tv_customer_service_center.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_help:// 帮助中心
			Intent intent = new Intent(context,HelpCenterActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_version_update:// 版本更新
			DownLoadUtils.checkoutAppVersion(this, true);
			break;
		case R.id.tv_customer_service_center:// 客服中心
			CustomerServiceDialog dialog = new CustomerServiceDialog(context);
			dialog.show();
			break;

		default:
			break;
		}
	}
}
