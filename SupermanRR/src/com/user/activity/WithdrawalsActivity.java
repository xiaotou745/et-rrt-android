package com.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQUserId;
import com.renrentui.requestmodel.RQWithdraw;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.resultmodel.RSMyInCome;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.user.service.WithdrawalsDialog;
import com.user.service.WithdrawalsDialog.ExitDialogListener;

public class WithdrawalsActivity extends BaseActivity implements
		OnClickListener {

	private Context context;
	private Button btn_withdrawals;// 提现按钮
	private EditText et_zhifubao_name;// 支付宝开户姓名
	private EditText et_zhifubao;// 支付宝账号
	private EditText et_money;// 余额（元）
	private TextView tv_can_withdrawals_money;// 可用金额
	private TextView tv_accumulated_wealth;// 累积财富

	private RQHandler<RSBase> rqHandler_withdraw = new RQHandler<RSBase>(
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
					WithdrawalsDialog wd = new WithdrawalsDialog(context);
					wd.addListener(new ExitDialogListener() {

						@Override
						public void clickCommit() {
							finish();
						}
					});
					wd.show();
					wd.setCancelable(false);
				}

				@Override
				public void onSericeErr(RSBase t) {
					// TODO Auto-generated method stub
					ToastUtil.show(context, t.msg);
				}

				@Override
				public void onSericeExp() {
					// TODO Auto-generated method stub
				}
			});

	private RQHandler<RSMyInCome> rqHandler_myincome = new RQHandler<RSMyInCome>(
			new IRqHandlerMsg<RSMyInCome>() {

				@Override
				public void onBefore() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onNetworknotvalide() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onSuccess(RSMyInCome t) {
					tv_can_withdrawals_money.setText(t.data.getBalance());
					tv_accumulated_wealth.setText(t.data.getTotalAmount());
				}

				@Override
				public void onSericeErr(RSMyInCome t) {
					// TODO Auto-generated method stub
					ToastUtil.show(context, t.msg);
				}

				@Override
				public void onSericeExp() {
					// TODO Auto-generated method stub
				}
			});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals);
		super.init();
		initControl();
		getData();
	}

	/**
	 * 获取用户金额数据
	 */
	private void getData() {
		ApiUtil.Request(new RQBaseModel<RQUserId, RSMyInCome>(context,
				new RQUserId(Utils.getUserDTO(context).data.userId),
				new RSMyInCome(), ApiNames.获取用户信息.getValue(), RequestType.POST,
				rqHandler_myincome));
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		context = this;
		btn_withdrawals = (Button) findViewById(R.id.btn_withdrawals);
		btn_withdrawals.setOnClickListener(this);
		et_zhifubao_name = (EditText) findViewById(R.id.et_zhifubao_name);
		et_zhifubao = (EditText) findViewById(R.id.et_zhifubao);
		et_money = (EditText) findViewById(R.id.et_money);
		tv_can_withdrawals_money = (TextView) findViewById(R.id.tv_can_withdrawals_money);
		tv_accumulated_wealth = (TextView) findViewById(R.id.tv_accumulated_wealth);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_withdrawals:
			final String amount = et_money.getText().toString().trim();
			final String accountInfo = et_zhifubao.getText().toString().trim();
			final String trueName = et_zhifubao_name.getText().toString()
					.trim();
			if (!Util.IsNotNUll(amount)) {
				ToastUtil.show(context, "提现金额不能为空");
				return;
			}
			if (!Util.IsNotNUll(accountInfo)) {
				ToastUtil.show(context, "支付宝账号不能为空");
				return;
			}
			if (!Util.IsNotNUll(trueName)) {
				ToastUtil.show(context, "支付宝开户姓名不能为空");
				return;
			}
			if (Float.parseFloat(amount) < 10) {
				ToastUtil.show(context, "提现金额必须大于等于10元");
			} else {
				// AlertDialog.Builder builder = new Builder(context);
				// builder.setMessage("支付宝账号：" + accountInfo + "\n支付宝开户名："
				// + trueName + "\n转账金额：" + amount);
				// builder.setTitle("确认资料");
				// builder.setPositiveButton("确认",
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog,
				// int which) {
				ApiUtil.Request(new RQBaseModel<RQWithdraw, RSBase>(context,
						new RQWithdraw(Utils.getUserDTO(context).data.userId,
								amount, accountInfo, trueName), new RSBase(),
						ApiNames.申请提现.getValue(), RequestType.POST,
						rqHandler_withdraw));
				// }
				// });
				// builder.setNegativeButton("取消",
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog,
				// int which) {
				// dialog.dismiss();
				// }
				// });
				// builder.create().show();
				//
			}
			break;

		default:
			break;
		}
	}

}
