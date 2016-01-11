package com.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import base.BaseActivity;

import com.renrentui.app.MyApplication;
import com.renrentui.app.R;
import com.renrentui.tools.ExitApplication;
import com.renrentui.tools.FileUtils;
import com.renrentui.util.DataClearManager;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.activity.NoGoingTaskActicity;
import com.user.model.download.DownLoadUtils;
import com.user.service.QuitDialog;
import com.user.service.QuitDialog.ExitDialogListener;

public class MoreActivity extends BaseActivity implements OnClickListener {

	private TextView tv_version;// 版本号控件
	private RelativeLayout rl_version_update;// 版本更新
	private RelativeLayout rl_cache;// 清除缓存
	private RelativeLayout rl_updatePassword;// 更改密码
	private Button btn_sign_out;// 退出按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		super.init();
		initControl();
	}

	private void initControl() {

		if(mIV_title_left!=null){
			mIV_title_left.setVisibility(View.VISIBLE);
			mIV_title_left.setOnClickListener(this);
		}
		if(mTV_title_content!=null){
			mTV_title_content.setText("更多");
		}


		tv_version = (TextView) findViewById(R.id.tv_version);
		tv_version.setText("V " + Utils.getVersion(context));
		rl_version_update = (RelativeLayout) findViewById(R.id.rl_version_update);
		rl_version_update.setOnClickListener(this);
		rl_cache = (RelativeLayout) findViewById(R.id.rl_cache);
		rl_cache.setOnClickListener(this);
		rl_updatePassword = (RelativeLayout) findViewById(R.id.rl_updatePassword);
		rl_updatePassword.setOnClickListener(this);
		btn_sign_out = (Button) findViewById(R.id.btn_sign_out);
		btn_sign_out.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_title_left:
				finish();
				break;
		case R.id.rl_version_update:// 点击版本更新
			DownLoadUtils.checkoutAppVersion(this, true);
			break;
		case R.id.rl_cache:// 点击清楚缓存
			QuitDialog clearDialog = new QuitDialog(context, "确定要清理缓存吗？", "确定",
					"取消");
			clearDialog.addListener(new ExitDialogListener() {

				@Override
				public void clickCommit() {
					FileUtils.deleteDirectoty(FileUtils.getSaveFilePath());
//					DataClearManager.cleanApplicationData(context, FileUtils.getSaveFilePath(),"H:/Android/data/com.renrenditui.app/cache/uil-images","H:/DCIM/.thumbnails");
					ToastUtil.show(context, "缓存清理成功！");
				}

				@Override
				public void clickCancel() {

				}
			});
			clearDialog.show();
			clearDialog.setCancelable(false);
			break;
		case R.id.rl_updatePassword:// 点击修改密码
			Intent intent = new Intent(this, UpdatePwdActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_sign_out:// 退出登录
			QuitDialog dialog = new QuitDialog(context, "确定退出？", "确定", "取消");
			dialog.addListener(new ExitDialogListener() {

				@Override
				public void clickCommit() {
					Utils.quitUser(context);
					Intent intent = new Intent(context,
							NoGoingTaskActicity.class);
					startActivity(intent);

					finish();
				}

				@Override
				public void clickCancel() {

				}
			});
			dialog.show();
			dialog.setCancelable(false);
			break;
		default:
			break;
		}
	}

}
