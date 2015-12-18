package com.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import base.BaseActivity;

import com.renrentui.app.R;
import com.renrentui.interfaces.IBack;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQUserId;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSMyInCome;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.activity.MyTaskFramentActivity;
import com.task.activity.MyTaskMaterialActivity;
import com.task.activity.NoGoingTaskActicity;
import com.user.service.CustomerServiceDialog;

/**
 * 个人中心界面
 * 
 * @author llp
 * 
 */
public class PersonalCenterActivity extends BaseActivity implements
		OnClickListener {

	private Context context;
	private ImageView iv_user_icon;// 用户头像
	private RelativeLayout rl_to_user_info;// 用户信息
	private RelativeLayout rl_withdrawals;// 提现
	private RelativeLayout rl_help;// 帮助中心
	private RelativeLayout rl_finished_task;// 已完成任务
	private RelativeLayout rl_datum_task;// 已完成任务
	private RelativeLayout rl_message_task;// 已完成任务

	private TextView tv_to_more;// 更多按钮
	private TextView tv_user_phone;// 用户账号
	private TextView tv_user_name;// 用户名称
	private TextView tv_my_money;// 我的余额
	private TextView tv_my_new_money;//我的新余额
	private TextView tv_can_withdrawals_money;// 用户可提现金额
	private TextView tv_withdrawals_money;// 用户提现中金额
	private RelativeLayout rl_customer_service_center;// 客服支持

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

				@SuppressWarnings("deprecation")
				@Override
				public void onSuccess(RSMyInCome t) {
					tv_user_name.setText(t.data.userName);
					tv_user_phone.setText(t.data.phoneNo);
					if (Util.IsNotNUll(t.data.fullHeadImage)) {
						ImageLoadManager.getLoaderInstace().disPlayNormalImg(
								t.data.fullHeadImage, iv_user_icon,
								R.drawable.icon);
					} else {
						iv_user_icon.setImageResource(R.drawable.icon);
					}
					tv_my_money.setText(t.data.getBalance());
					tv_my_new_money.setText(t.data.getBalance());
					tv_can_withdrawals_money.setText(t.data.getWithdraw());
					tv_withdrawals_money.setText(t.data.getWithdrawing());
				}

				@Override
				public void onSericeErr(RSMyInCome t) {
					// TODO Auto-generated method stub
					tv_my_money.setText("0.00");
					tv_my_new_money.setText("0.00");
					tv_can_withdrawals_money.setText("0.00");
					tv_withdrawals_money.setText("0.00");
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
		setContentView(R.layout.activity_personal_center);
		super.init();
		super.onBack(new IBack() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, NoGoingTaskActicity.class);
				startActivity(intent);
				finish();
			}
		});
		initControl();
		getInitData();
	}

	/**
	 * 初始化数据
	 */
	public void getInitData() {
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
		tv_to_more = (TextView) findViewById(R.id.tv_to_more);
		tv_to_more.setOnClickListener(this);
		rl_to_user_info = (RelativeLayout) findViewById(R.id.rl_to_user_info);
		rl_to_user_info.setOnClickListener(this);
		iv_user_icon = (ImageView) findViewById(R.id.iv_user_icon);
		// iv_user_icon.setOnClickListener(this);
		rl_withdrawals = (RelativeLayout) findViewById(R.id.rl_withdrawals);
		rl_withdrawals.setOnClickListener(this);
		rl_help = (RelativeLayout) findViewById(R.id.rl_help);
		rl_help.setOnClickListener(this);
		rl_finished_task = (RelativeLayout) findViewById(R.id.rl_finished_task);
		rl_finished_task.setOnClickListener(this);
		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		tv_my_money = (TextView) findViewById(R.id.tv_my_money);
		tv_user_phone = (TextView) findViewById(R.id.tv_user_phone);
		tv_can_withdrawals_money = (TextView) findViewById(R.id.tv_can_withdrawals_money);
		tv_withdrawals_money = (TextView) findViewById(R.id.tv_withdrawals_money);
		rl_customer_service_center = (RelativeLayout) findViewById(R.id.rl_customer_service_center);
		rl_customer_service_center.setOnClickListener(this);
		rl_datum_task = (RelativeLayout)findViewById(R.id.rl_datum_task);
		rl_datum_task.setOnClickListener(this);
		rl_message_task = (RelativeLayout)findViewById(R.id.rl_message_task);
		rl_message_task.setOnClickListener(this);

		tv_my_new_money = (TextView)findViewById(R.id.tv_my_money_new);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.tv_to_more:// 点击更多按钮
			intent = new Intent(this, MoreActivity.class);
			break;
		case R.id.rl_withdrawals:// 提现
			intent = new Intent(this, WithdrawalsActivity.class);
			break;
		case R.id.rl_to_user_info:// 点击用户头像，进入个人资料
			intent = new Intent(this, PersonalDataActivity.class);
			break;
		case R.id.rl_finished_task:// 已完成任务
			intent = new Intent(this, MyTaskFramentActivity.class);
			break;
		case R.id.rl_customer_service_center:// 客服支持
			CustomerServiceDialog dialog = new CustomerServiceDialog(context);
			dialog.show();
			dialog.setCancelable(false);
			break;
		case R.id.rl_help:// 帮助中心
			intent = new Intent(context, HelpCenterActivity.class);
			break;
			case R.id.rl_datum_task:
				intent = new Intent(context, MyTaskMaterialActivity.class);
				break;
			case R.id.rl_message_task:
				intent = new Intent(context,MyMessageActivity.class);
				break;
		default:
			break;
		}
		if (intent != null)
			startActivity(intent);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		getInitData();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(context, NoGoingTaskActicity.class);
			startActivity(intent);
			finish();
		}
		return true;
	}

}
