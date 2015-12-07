package com.user.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBase;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQFindPwd;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQSendCode;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.MD5;
import com.renrentui.util.ToastUtil;

/**
 * 找回密码界面
 * 
 * @author llp
 * 
 */
public class FindPwdActivity extends BaseActivity implements OnClickListener {

	private Context context;
	private EditText et_phone;// 用户手机号
	private Button btn_get_code;// 获取验证码按钮
	private EditText et_code;// 手机验证码
	private EditText et_password;// 新密码
	private EditText et_password2;// 再次输入新密码
	private Button btn_submit;// 提交按钮
	private int time = 60;
	private Timer timer;

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
					btn_get_code.setEnabled(false);
				}

				@Override
				public void onSericeErr(RSBase t) {
					ToastUtil.show(context, t.msg);
				}

				@Override
				public void onSericeExp() {

				}
			});

	private RQHandler<RSBase> rqHandler_FindUserPwd = new RQHandler<RSBase>(
			new IRqHandlerMsg<RSBase>() {

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
				public void onSuccess(RSBase t) {
					hideProgressDialog();
					ToastUtil.show(context, "密码修改成功 ");
					finish();
				}

				@Override
				public void onSericeErr(RSBase t) {
					// TODO Auto-generated method stub
					hideProgressDialog();
					ToastUtil.show(context, t);
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password);
		super.init();
		initControl();
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		context = this;
		et_phone = (EditText) findViewById(R.id.et_phone);
		btn_get_code = (Button) findViewById(R.id.btn_get_code);
		btn_get_code.setOnClickListener(this);
		et_code = (EditText) findViewById(R.id.et_code);
		et_password = (EditText) findViewById(R.id.et_password);
		et_password2 = (EditText) findViewById(R.id.et_password2);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_code:
			String phone = et_phone.getText().toString();
			if (phone != null && !phone.equals("") && phone.length() == 11) {
				ApiUtil.Request(new RQBaseModel<RQBase, RSBase>(context,
						new RQSendCode(phone, 3), new RSBase(),
						ApiNames.获取手机验证码.getValue(), RequestType.POST,
						rqHandler_getPhoneCode));
			} else {
				ToastUtil.show(context, "请输入正确的手机号码!");
			}
			break;
		case R.id.btn_submit:
			String code = et_code.getText().toString();
			String password = et_password.getText().toString().trim();
			String phone1 = et_phone.getText().toString();
			String password2 = et_password2.getText().toString().trim();
			if (phone1.isEmpty() || phone1.length() != 11
					|| !phone1.substring(0, 1).equals("1")) {
				ToastUtil.show(context, "请输入正确的手机号");
				return;
			}
			if (code.isEmpty() || code.length() != 6) {
				ToastUtil.show(context, "请输入有效的验证码");
				return;
			}
			if (password.isEmpty() || password.length() < 6) {
				ToastUtil.show(context, "请输入6到12位的密码");
				return;
			}

			if (password2.isEmpty() || !password2.equals(password)) {
				ToastUtil.show(context, "密码确认错误");
				return;
			}
			showProgressDialog();
			ApiUtil.Request(new RQBaseModel<RQBase, RSBase>(context,
					new RQFindPwd(context, phone1, MD5.GetMD5Code(password), code),
					new RSBase(), ApiNames.忘记密码.getValue(), RequestType.POST,
					rqHandler_FindUserPwd));//504239
			break;
		}
	}

	private class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					time--;
					if (time >= 0) {
						btn_get_code.setText("("+time + ")重新发送");
					} else {
						btn_get_code.setText("获取验证码");

						btn_get_code.setEnabled(true);
						timer.cancel();

					}

				}
			});

		}
	}

}
