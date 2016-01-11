package com.user.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBase;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQRegisterUser;
import com.renrentui.requestmodel.RQSendCode;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.resultmodel.RSUser;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.MD5;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.activity.NoGoingTaskActicity;

/**
 * 用户注册页
 * 
 * @author llp
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {

	private int time = 60;
	private Timer timer;
	private EditText etPhone;// 用户手机号码
	private EditText etCode;// 手机获取验证码
	private EditText etPasssword;// 用户密码
	private EditText etPassword2;// 再次输入密码
	private EditText etRefereePhone;// 推荐人手机号
	private Button btnSubmit;// 确定按钮
	private Button btnGetCode;// 获取验证码按钮

	private RQHandler<RSBase> rqHandler_getPhoneCode = new RQHandler<RSBase>(
			new IRqHandlerMsg<RSBase>() {

				@Override
				public void onBefore() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onNetworknotvalide() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onSuccess(RSBase t) {
					time = 60;
					timer = new Timer();
					// 设置自动播放时间
					timer.schedule(new MyTimerTask(), 1000, 1000);
					btnGetCode.setEnabled(false);
				}

				@Override
				public void onSericeErr(RSBase t) {
					// TODO Auto-generated method stub
					ToastUtil.show(context, t.msg);
					// Log.i("llp", t.Msg);
				}

				@Override
				public void onSericeExp() {
					// TODO Auto-generated method stub
				}
			});

	private RQHandler<RSUser> rqHandler_registerUser = new RQHandler<RSUser>(
			new IRqHandlerMsg<RSUser>() {

				@Override
				public void onBefore() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onNetworknotvalide() {
					// TODO Auto-generated method stub
					hideProgressDialog();
				}

				@Override
				public void onSuccess(RSUser t) {
					hideProgressDialog();
					Utils.setUserDTO(context, t);
					Intent intent = new Intent(context, NoGoingTaskActicity.class);
					startActivity(intent);
					finish();
				}

				@Override
				public void onSericeErr(RSUser t) {
					// TODO Auto-generated method stub
					hideProgressDialog();
					ToastUtil.show(context, t.msg);
					// Log.i("llp", t.Msg);
				}

				@Override
				public void onSericeExp() {
					// TODO Auto-generated method stub
					hideProgressDialog();
				}
			});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		super.init();
		initControl();
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {

		if(mIV_title_left!=null){
			mIV_title_left.setVisibility(View.VISIBLE);
			mIV_title_left.setImageResource(R.drawable.back);
			mIV_title_left.setOnClickListener(this);
		}
		if(mTV_title_content!=null){
			mTV_title_content.setText(context.getResources().getString(R.string.register));
		}

		etPhone = (EditText) findViewById(R.id.et_phone);
		etCode = (EditText) findViewById(R.id.et_code);
		etPasssword = (EditText) findViewById(R.id.et_password);
		etPassword2 = (EditText) findViewById(R.id.et_password2);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(this);
		etRefereePhone= (EditText)findViewById(R.id.et_phone_referee);
		btnGetCode = (Button) findViewById(R.id.btn_get_code);
		btnGetCode.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_title_left:
				//返回
				finish();
				break;
		case R.id.btn_submit:
			submitUserInfo();
			break;
		case R.id.btn_get_code:
			String phone1 = etPhone.getText().toString();
			if (phone1 != null && !phone1.equals("") && phone1.length() == 11
					&& phone1.substring(0, 1).equals("1")) {
				ApiUtil.Request(new RQBaseModel<RQBase, RSBase>(context,
						new RQSendCode(phone1, 1), new RSBase(),
						ApiNames.获取手机验证码.getValue(), RequestType.POST,
						rqHandler_getPhoneCode));
			} else {
				ToastUtil.show(context, "请输入正确的手机号码!");
			}
			break;
		}
	}

	/**
	 * 检查要注册的用户信息填完整，若已经完整，提交到服务器进行保存，若不完整直接返回
	 */
	private void submitUserInfo() {
		String phone = etPhone.getText().toString();
		String password = etPasssword.getText().toString().trim();
		String password2 = etPassword2.getText().toString().trim();
		String code = etCode.getText().toString();
		String recommendPhone = etRefereePhone.getText().toString().trim();
		if (phone.length() != 11 || !phone.substring(0, 1).equals("1")) {
			ToastUtil.show(context, "请输入正确的手机号");
			return;
		}
		if (code.length() != 6) {
			ToastUtil.show(context, "请输入您收到的验证码");
			return;
		}
		if (password.length() < 6) {
			ToastUtil.show(context, "请输入6到12位的密码");
			return;
		}
		if (!password2.equals(password)) {
			ToastUtil.show(context, "两次密码不一致");
			return;
		}
		if(!TextUtils.isEmpty(recommendPhone)){
			if(!Util.isMobileNO(recommendPhone)){
				ToastUtil.show(context, "请输入正确的推荐人手机号");
				return ;
			}
		}
		showProgressDialog();
		ApiUtil.Request(new RQBaseModel<RQRegisterUser, RSUser>(
				context,
				new RQRegisterUser(phone, MD5.GetMD5Code(password), code,"",recommendPhone),
				new RSUser(), ApiNames.用户注册.getValue(), RequestType.POST,
				rqHandler_registerUser));
	}

	private class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					time--;
					if (time >= 0) {
						btnGetCode.setText("("+time + ")重新发送");
					} else {
						btnGetCode.setText("获取验证码");
						btnGetCode.setEnabled(true);
						timer.cancel();

					}

				}
			});

		}
	}
}
