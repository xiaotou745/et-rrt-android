package com.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.renrentui.resultmodel.MyInCome;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.resultmodel.RSMyInCome;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.user.service.WithdrawalsDialog;
import com.user.service.WithdrawalsDialog.ExitDialogListener;

import org.w3c.dom.Text;

/**
 * 提现页面
 */
public class WithdrawalsActivity extends BaseActivity implements
		OnClickListener {
	public static  final  int STR_REQUESET_FLAG_ADD = 1005;


	private TextView tv_can_withdrawals_money;// 我的余额
	private TextView tv_accumulated_wealth;// 已提现
	private TextView mTV_account_type;//提现账户类型
	private TextView mTV_account_num;//提现账户
	private EditText et_money;// 余额（元）

	private Button btn_withdrawals;// 提现按钮
	private String strAccountTrueName;//提现的真是姓名
	private String strAccountTrueNum;//提现的真是账户
	private boolean isBindAccount = true;//是否绑定了提现账户
	private String strUserPhone;
	private String strUserName;


	private MyInCome mMyInComeData;

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



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals);
		super.init();
		initControl();
		getData();
		initViewData();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		getData();
		initViewData();
	}

	/**
	 * 获取传递数据
	 */
private  void getData(){
	mMyInComeData = (MyInCome)this.getIntent().getSerializableExtra("VO");

}
//	/**
//	 * 获取用户金额数据
//	 */
//	private void getData() {
//		ApiUtil.Request(new RQBaseModel<RQUserId, RSMyInCome>(context,
//				new RQUserId(Utils.getUserDTO(context).data.userId),
//				new RSMyInCome(), ApiNames.获取用户信息.getValue(), RequestType.POST,
//				rqHandler_myincome));
//	}

	private void initViewData(){
		if(mMyInComeData!=null){
			strUserName = mMyInComeData.clienterName;
			strUserPhone = mMyInComeData.phoneNo;
			strAccountTrueName = mMyInComeData.getTrueName();
			tv_can_withdrawals_money.setText(mMyInComeData.getWithdraw());
			tv_accumulated_wealth.setText(mMyInComeData.getHadWithdraw());
			if("-1".equals(mMyInComeData.getAccountType())){
				mTV_account_type.setText("支付宝");
				mTV_account_num.setText("未设置");
				mTV_account_num.setTextColor(context.getResources().getColor(R.color.tv_order_color_3));
				isBindAccount = false;
				return;
			}else if("1".equals(mMyInComeData.getAccountType())){
				mTV_account_type.setText("网银");
			}else if("2".equals(mMyInComeData.getAccountType())){
				mTV_account_type.setText("支付宝");
			}else if("3".equals(mMyInComeData.getAccountType())){
				mTV_account_type.setText("微信");
			}else if("4".equals(mMyInComeData.getAccountType())){
				mTV_account_type.setText("财付通");
			}else if("5".equals(mMyInComeData.getAccountType())){
				mTV_account_type.setText("百度钱包");
			}
			isBindAccount = true;
			mTV_account_num.setTextColor(context.getResources().getColor(R.color.tv_order_color_2));
			strAccountTrueNum = mMyInComeData.getAccountNo();
			mTV_account_num.setText(getUserBankInfo(mMyInComeData.getAccountType(), mMyInComeData.getAccountNo()));

		}

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
			mTV_title_content.setText("提现");
		}
		btn_withdrawals = (Button) findViewById(R.id.btn_withdrawals);
		btn_withdrawals.setOnClickListener(this);
		et_money = (EditText) findViewById(R.id.et_money);
		tv_can_withdrawals_money = (TextView) findViewById(R.id.tv_can_withdrawals_money);
		tv_accumulated_wealth = (TextView) findViewById(R.id.tv_accumulated_wealth);
		mTV_account_type = (TextView)findViewById(R.id.tv_account_type);
		mTV_account_num = (TextView)findViewById(R.id.tv_account_num);
		mTV_account_num.setOnClickListener(this);
	}

	/**
	 * 数据整理
	 */
	private String  getUserBankInfo(String type, String content) {
		if(content==null  ||content.length()<=0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		if ("1".equals(type)) {
			// 银行
			if(content.length()<=4){
				return content;
			}
			sb.append(content.substring(0,4) +"****"+content.substring(content.length()-4));
		} else if ("2".equals(type)) {
			// 支付宝
			int iBankNo = content.lastIndexOf("@");
			if (iBankNo > 0) {
				// 邮箱
				String strStart = content.substring(iBankNo);
				int i = strStart.length() >= 3 ? 3 : strStart.length();
				sb.append(content.substring(0, i));
				sb.append("****").append(content.substring(i));
			} else {
				// 手机号
				if(content.length()<=3){
					return content;
				}
				sb.append(content.substring(0, 3));
				sb.append("****").append(content.substring(content.length() - 3));
			}
		}
		return sb.toString();
	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if(resultCode==RESULT_OK){
//			strAccountTrueNum = data.getStringExtra("STR_ALIPAY_NUM");
//			strAccountTrueName = data.getStringExtra("STR_ALIPAY_NAME");
//			strUserName = data.getStringExtra("STR_USER_NAME");
//			strUserPhone = data.getStringExtra("STR_USER_PHONE");
//			switch (requestCode){
//				case STR_REQUESET_FLAG_ADD:
//					//add
//					if(!TextUtils.isEmpty(strAccountTrueName) && !TextUtils.isEmpty(strAccountTrueNum)){
//						this.isBindAccount =true;
//						mTV_account_num.setText(getUserBankInfo("2",strAccountTrueNum));
//					}else{
//						this.isBindAccount = false;
//					}
//					break;
//
//			}
//		}
//
//	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_title_left:
				finish();
				break;
			case R.id.tv_account_num:
				Intent intent = new Intent();
				if(isBindAccount) {
					//查看账号信息
					intent.setClass(WithdrawalsActivity.this, ShowAlipayDetailActivity.class);
//					intent.putExtra("STR_USER_NAME", strUserName);
//					intent.putExtra("STR_USER_PHONE", strUserPhone);
					intent.putExtra("VO",mMyInComeData);
					startActivity(intent);
				} else {
					//绑定账号
					intent.setClass(WithdrawalsActivity.this, EditEditUserAlipayActivity.class);
//					intent.putExtra("STR_USER_NAME", strUserName);
//					intent.putExtra("STR_USER_PHONE", strUserPhone);
					intent.putExtra("VO",mMyInComeData);
					startActivity(intent);
				}

				break;
		case R.id.btn_withdrawals:
			String amount = et_money.getText().toString().trim();
			if(!isBindAccount){
				ToastUtil.show(context, "请绑定提现账号");
				return;
			}
			if (!Util.IsNotNUll(amount)) {
				ToastUtil.show(context, "提现金额不能为空");
				et_money.setSelection(0);
				return;
			}
			if(Integer.parseInt(amount.substring(0,1))<1){
				ToastUtil.show(context, "请输入正确提现金额!");
				et_money.setSelection(0,et_money.getText().toString().trim().length());
				return;
			}
			if (!Utils.isPositiveInteger(amount)) {
				ToastUtil.show(context, "提现金额应为10的倍数");
				et_money.setSelection(0, et_money.getText().toString().trim().length());
			} else if(Integer.parseInt(amount)%10!=0){
				ToastUtil.show(context, "提现金额应为10的倍数");
				et_money.setSelection(0,et_money.getText().toString().trim().length());
			} else if(Integer.parseInt(amount)<10){
				ToastUtil.show(context, "提现金额不能小于10元");
				et_money.setSelection(0, et_money.getText().toString().trim().length());
			} else if(Integer.parseInt(amount)>1000){
				ToastUtil.show(context, "单笔提现金额不能超过1000元");
				et_money.setSelection(0,et_money.getText().toString().trim().length());
		}else{
				ApiUtil.Request(new RQBaseModel<RQWithdraw, RSBase>(context,
						new RQWithdraw(Utils.getUserDTO(context).data.userId,
								amount, strAccountTrueNum, strAccountTrueName), new RSBase(),
						ApiNames.申请提现.getValue(), RequestType.POST,
						rqHandler_withdraw));
			}
			break;

		default:
			break;
		}
	}

}
