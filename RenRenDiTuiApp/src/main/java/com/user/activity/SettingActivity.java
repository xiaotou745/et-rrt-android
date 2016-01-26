package com.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.util.DataClearManager;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.user.service.QuitDialog;
import com.user.service.QuitDialog.ExitDialogListener;

/**
 * 设置界面
 * @author llp
 *
 */
public class SettingActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout ll_update_password;//修改密码
	private RelativeLayout ll_clear_cache;//清楚缓存
	private Button btn_sign_out;//退出登录

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		super.init();
		initControl();
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
			mTV_title_content.setText("系统设置");
		}
		ll_update_password = (RelativeLayout) findViewById(R.id.ll_update_password);
		ll_clear_cache = (RelativeLayout) findViewById(R.id.ll_clear_cache);
		btn_sign_out = (Button) findViewById(R.id.btn_sign_out);
		ll_update_password.setOnClickListener(this);
		ll_clear_cache.setOnClickListener(this);
		btn_sign_out.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.ll_update_password://修改密码
			intent = new Intent(this, UpdatePwdActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_clear_cache://清除缓存
			QuitDialog clearDialog = new QuitDialog(SettingActivity.this, "确定要清理缓存吗？",
					"确定", "取消");
			clearDialog.addListener(new ExitDialogListener() {

				@Override
				public void clickCommit() {
					////////////////////////////////////////////////////////////
					DataClearManager.cleanApplicationData(context, Environment.getExternalStorageDirectory().getAbsolutePath(),"/Android/data/"+ context.getPackageName()+"/cache");
					ToastUtil.show(context, "缓存清理成功！");
				}

				@Override
				public void clickCancel() {
					
				}
			});
			clearDialog.show();
			clearDialog.setCanceledOnTouchOutside(true); 
			break;
		case R.id.btn_sign_out://退出登录
			QuitDialog dialog = new QuitDialog(SettingActivity.this, "确定退出？",
					"确定", "取消");
			dialog.addListener(new ExitDialogListener() {

				@Override
				public void clickCommit() {
					Utils.quitUser(context);
					Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
				}

				@Override
				public void clickCancel() {
					
				}
			});
			dialog.show();
			dialog.setCanceledOnTouchOutside(true); 
			break;
			case R.id.iv_title_left:
				finish();
				break;
		default:
			break;
		}
	}
}
