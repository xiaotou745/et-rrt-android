package com.user.activity;

import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQUpdatePwd;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.MD5;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import base.BaseActivity;

/**
 * 修改密码界面
 * @author llp
 */
public class UpdatePwdActivity extends BaseActivity implements OnClickListener {

	private EditText et_old_password;// 旧密码
	private EditText et_password;// 新密码
	private EditText et_password2;// 再次输入新密码
	private Button btn_submit;// 提交按钮

	private String oldP;
	private String newP;

	private RQHandler<RSBase> rqHandler_UpdateUserPwd = new RQHandler<RSBase>(
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
					ToastUtil.show(context, "密码修改成功");
					Utils.quitUser(context);
					Intent intent = new Intent(context,
							LoginActivity.class);
					startActivity(intent);
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_password);
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
			mTV_title_content.setText("修改密码");
		}
		et_old_password = (EditText) findViewById(R.id.et_old_password);
		et_password = (EditText) findViewById(R.id.et_password);
		et_password2 = (EditText) findViewById(R.id.et_password2);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_title_left:
				finish();
				break;
		case R.id.btn_submit:
			oldP = et_old_password.getText().toString().trim();
			newP = et_password.getText().toString().trim();
			String newP2 = et_password2.getText().toString().trim();
			if (oldP.isEmpty() || oldP.length() < 6) {
				ToastUtil.show(context, "请输入正确的原密码");
				return;
			}
			if (newP.isEmpty() || newP.length() < 6) {
				ToastUtil.show(context, "请输入6到12位的新密码 ");
				return;
			}
			if (newP2.isEmpty() || !newP.equals(newP2)) {
				ToastUtil.show(context, "确认密码错误");
				return;
			}
			if(oldP.equals(newP)){
				ToastUtil.show(context, "新旧密码不能相同");
				return;
			}
			showProgressDialog();
			ApiUtil.Request(new RQBaseModel<RQUpdatePwd, RSBase>(context,
					new RQUpdatePwd(Utils.getUserDTO(context).data.userId,  MD5.GetMD5Code(oldP),
							 MD5.GetMD5Code(newP)), new RSBase(), ApiNames.修改密码.getValue(),
					RequestType.POST, rqHandler_UpdateUserPwd));
			break;
		default:
			break;
		}
	}

}
