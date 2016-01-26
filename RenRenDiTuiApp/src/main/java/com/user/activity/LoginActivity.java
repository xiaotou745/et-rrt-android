package com.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.interfaces.IBack;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQLogin;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSUser;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.MD5;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.activity.NoGoingTaskActicity;

/**
 * 登录页面
 * 
 * @author llp
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText et_phone;// 手机号码
	private EditText et_password;// 用户密码
	private TextView btn_register;// 注册按钮
	private TextView tv_forgot_password;// 忘记密码
	private Button btn_login;// 登录按钮
	private String SSID="";
	private String operSystemModel="";//手机具体型号
	private String phoneType="";//手机类型


	private RQHandler<RSUser> rqHandler_userLogin = new RQHandler<RSUser>(
			new IRqHandlerMsg<RSUser>() {

				@Override
				public void onBefore() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onNetworknotvalide() {
					// TODO Auto-generated method stub
					hideProgressDialog();
					ToastUtil.show(context, "网络异常");
				}

				@Override
				public void onSuccess(RSUser t) {
					Utils.setUserDTO(context, t);
//					Intent intent = new Intent(context,
//							NoGoingTaskActicity.class);
//					startActivity(intent);
					finish();
				}

				@Override
				public void onSericeErr(RSUser t) {
					// TODO Auto-generated method stub
					hideProgressDialog();
					ToastUtil.show(context, t);
				}

				@Override
				public void onSericeExp() {
					// TODO Auto-generated method stub
					hideProgressDialog();
					ToastUtil.show(context, "网络异常");
				}
			});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		super.init();
		SSID = Utils.getMobileDevieceId(context);
		operSystemModel = Utils.getModelSysVersion(context);
		phoneType = Utils.getModel(context);
		initControl();
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_password = (EditText) findViewById(R.id.et_password);
		if(mIV_title_left!=null){
			mIV_title_left.setVisibility(View.VISIBLE);
			mIV_title_left.setImageResource(R.drawable.back);
			mIV_title_left.setOnClickListener(this);
		}
		if(mTV_title_content!=null){
			mTV_title_content.setText(context.getResources().getString(R.string.login));
		}
		if(mTV_title_right!=null){
			mTV_title_right.setVisibility(View.VISIBLE);
			mTV_title_right.setText(context.getResources().getString(R.string.register));
			mTV_title_right.setOnClickListener(this);
		}
		tv_forgot_password = (TextView) findViewById(R.id.tv_forgot_password);
		tv_forgot_password.setOnClickListener(this);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.tv_title_right:// 点击注册按钮时
			intent = new Intent();
			intent.setClass(context, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_login:// 点击登录按钮时
			checkUserInfo();
			break;
		case R.id.tv_forgot_password:// 点击忘记密码
			intent = new Intent();
			intent.setClass(context, FindPwdActivity.class);
			startActivity(intent);
			break;
			case R.id.iv_title_left:
				//返回
//				Intent mIntent = new Intent(context, NoGoingTaskActicity.class);
//				startActivity(mIntent);
				finish();
				break;
		}
	}

	/**
	 * 检查用户名和密码是否为空 用户名为空提示 用户名不能为空 密码为空提示 密码不能为空 若都不为空 向服务器请求进行登录
	 */
	private void checkUserInfo() {
		String phone = et_phone.getText().toString();
		String password = et_password.getText().toString().trim();
		if (phone == null || phone.equals("")) {
			ToastUtil.show(context, "用户名不能为空！");
			return;
		}
		if (phone.length() != 11 || !phone.substring(0, 1).equals("1")) {
			ToastUtil.show(context, "请输入正确的手机号");
			return;
		}
		if (password != null && !password.trim().equals("")) {
			showProgressDialog();
			ApiUtil.Request(new RQBaseModel<RQLogin, RSUser>(context,
					new RQLogin(context, phone, MD5.GetMD5Code(password),SSID,operSystemModel,phoneType),
					new RSUser(), ApiNames.用户登录.getValue(), RequestType.POST,
					rqHandler_userLogin));
		} else {
			ToastUtil.show(context, "密码不能为空！");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(context, NoGoingTaskActicity.class);
			startActivity(intent);
			this.finish();
		}
		return true;
	}
}
