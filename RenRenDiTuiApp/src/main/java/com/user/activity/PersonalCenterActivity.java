package com.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import base.BaseActivity;

import com.renrentui.app.MyApplication;
import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQGetMyMessage;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQUserId;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.MyInCome;
import com.renrentui.resultmodel.RSGetMyMessage;
import com.renrentui.resultmodel.RSMyInCome;
import com.renrentui.tools.ExitApplication;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ImageLoadManager;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.task.activity.MyMaterialTaskTeamActivity;
import com.task.activity.MyTaskFramentNewActivity;
import com.task.activity.NoGoingTaskActicity;
import com.user.service.CustomerServiceDialog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 个人中心界面
 * 
 * @author llp
 * 
 */
public class PersonalCenterActivity extends BaseActivity implements
		OnClickListener {


	//	bottom
	private LinearLayout mTab_01;
	private LinearLayout mTab_02;
	private LinearLayout mTab_03;
	private ImageView mTab_image_03;
	private TextView mTab_text_03;

//	个人信息

	private RelativeLayout rl_to_user_info;// 用户信息
	private ImageView iv_user_icon;// 用户头像
	private TextView tv_user_phone;// 用户账号
	private TextView tv_user_name;// 用户名称

	private TextView tv_my_money;// 我的余额
	private Button tv_my_withdrawals;//提现

	private RelativeLayout mRL_my_money_details;//资金明细
	private RelativeLayout mRL_my_friends;//我的合伙人
	private RelativeLayout rl_datum_task;// 资料审核详情
	private RelativeLayout rl_help;// 帮助中心
	private RelativeLayout rl_more;// 更多

	private RelativeLayout rl_customer_service_center;// 客服支持
	private TextView mTV_customer_service_tel;//客服电话
	private MyInCome mMyInComeData;

	private boolean isQuit = false;


//	获取
	private RQHandler<RSMyInCome> rqHandler_myincome = new RQHandler<RSMyInCome>(
			new IRqHandlerMsg<RSMyInCome>() {

				@Override
				public void onBefore() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onNetworknotvalide() {
				}

				@SuppressWarnings("deprecation")
				@Override
				public void onSuccess(RSMyInCome t) {
					mMyInComeData = t.data;
					tv_user_name.setText(t.data.clienterName);
					tv_user_phone.setText(t.data.phoneNo);
					if (Util.IsNotNUll(t.data.fullHeadImage)) {
						ImageLoadManager.getLoaderInstace().disPlayNormalImg(
								t.data.fullHeadImage, iv_user_icon,
								R.drawable.icon_user_default);
					} else {
						iv_user_icon.setImageResource(R.drawable.icon_user_default);
					}
					tv_my_money.setText(t.data.getBalance());
				}

				@Override
				public void onSericeErr(RSMyInCome t) {
					tv_my_money.setText("0.00");
					ToastUtil.show(context, t.msg);
				}

				@Override
				public void onSericeExp() {
				}
			});
	private RQHandler<RSGetMyMessage> rqHandler_getMyMessage = new RQHandler<>(
			new IRqHandlerMsg<RSGetMyMessage>() {

				@Override
				public void onBefore() {
				}

				@Override
				public void onNetworknotvalide() {
					//网络无效
				}

				@Override
				public void onSuccess(RSGetMyMessage t) {
					if( t.getData()>0){
						MyApplication.isMessage = true;
						mTab_image_03.setImageResource(R.drawable.bg_main_menu_tab3_2_image_layout);
					}else{
						//无信息
						MyApplication.isMessage = false;
						mTab_image_03.setImageResource(R.drawable.bg_main_menu_tab3_1_image_layout);
					}
					if(MyApplication.isMessage){
						mIV_title_right.setImageResource(R.drawable.icon_message_center_sel);
					}else{
						mIV_title_right.setImageResource(R.drawable.icon_message_center_nor);
					}
				}

				@Override
				public void onSericeErr(RSGetMyMessage t) {

				}

				@Override
				public void onSericeExp() {

				}
			});
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_center);
		super.init();
		initControl();
		getInitData();
	}

	@Override
	protected void onStart() {
		super.onStart();
		getInitData();
	}

	@Override
	protected  void onResume(){
		super.onResume();
		getMyMessageInfo();

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
	 * 获取未读信息
	 */
	private void getMyMessageInfo(){
		ApiUtil.Request(new RQBaseModel<RQGetMyMessage, RSGetMyMessage>(
				context, new RQGetMyMessage(strUserId),
				new RSGetMyMessage(), ApiNames.获取未读信息数量.getValue(),
				RequestType.POST, rqHandler_getMyMessage));
	}

	/**
	 * 初始化控件
	 */
	private void initControl() {
		if(mTV_title_content!=null){
			mTV_title_content.setText("个人中心");
		}
		if(mIV_title_right!=null){
			mIV_title_right.setVisibility(View.VISIBLE);
			mIV_title_right.setOnClickListener(this);
			if(MyApplication.isMessage){
				mIV_title_right.setImageResource(R.drawable.icon_message_center_sel);
			}else{
				mIV_title_right.setImageResource(R.drawable.icon_message_center_nor);
			}
		}

		//		menu
		mTab_01 = (LinearLayout)findViewById(R.id.tab_01);
		mTab_01.setOnClickListener(this);

		mTab_02 = (LinearLayout)findViewById(R.id.tab_02);
		mTab_02.setOnClickListener(this);
		mTab_03 = (LinearLayout)findViewById(R.id.tab_03);
		mTab_03.setOnClickListener(this);
		mTab_03.setSelected(true);
		mTab_image_03 = (ImageView)findViewById(R.id.tab_image_03);
		mTab_image_03.setImageResource(R.drawable.bg_main_menu_tab3_1_image_layout);
		mTab_text_03 = (TextView)findViewById(R.id.tab_text_03);

		rl_to_user_info = (RelativeLayout) findViewById(R.id.rl_to_user_info);
		rl_to_user_info.setOnClickListener(this);
		iv_user_icon = (ImageView) findViewById(R.id.iv_user_icon);
		//iv_user_icon.setOnClickListener(this);
		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		tv_user_phone = (TextView) findViewById(R.id.tv_user_phone);

		tv_my_money = (TextView) findViewById(R.id.tv_my_money_new);
		tv_my_withdrawals = (Button)findViewById(R.id.tv_withdrawals);
		tv_my_withdrawals.setOnClickListener(this);

		 mRL_my_money_details = (RelativeLayout) findViewById(R.id.rl_money_detail);
		mRL_my_money_details.setOnClickListener(this);
		 mRL_my_friends = (RelativeLayout) findViewById(R.id.rl_friend_details);
		mRL_my_friends.setOnClickListener(this);
		rl_datum_task = (RelativeLayout) findViewById(R.id.rl_datum_task);
		rl_datum_task.setOnClickListener(this);
		 rl_help = (RelativeLayout) findViewById(R.id.rl_help);
		rl_help.setOnClickListener(this);
		 rl_more = (RelativeLayout) findViewById(R.id.rl_more);
		rl_more.setOnClickListener(this);

		 rl_customer_service_center = (RelativeLayout) findViewById(R.id.rl_customer_service_center);
		rl_customer_service_center.setOnClickListener(this);
		 mTV_customer_service_tel = (TextView) findViewById(R.id.tv_customer_service_center);

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		Bundle mBundle1 = new Bundle();
		boolean isFinish = false;
		switch (v.getId()) {
			case R.id.rl_to_user_info:// 点击用户头像，进入个人资料
				intent = new Intent(this, PersonalDataActivity.class);
				break;
			case R.id.tv_withdrawals:// 提现
				if(mMyInComeData==null || Double.parseDouble(mMyInComeData.getBalance())<10){
					intent = null;
					ToastUtil.show(context,"余额不足10元,暂时不能提现");
				}else {
					intent = new Intent(this, WithdrawalsActivity.class);
					intent.putExtra("VO", mMyInComeData);
				}
				break;
			case R.id.rl_money_detail:// 资金明细
				intent = new Intent(this, MyCapitalFramentActivity.class);
				break;
			case R.id.rl_friend_details:// 我的合伙人
				intent = new Intent(this, MyFriendsActivity.class);
				break;
			case R.id.rl_datum_task:
				//资料审核详情
				intent = new Intent(context, MyMaterialTaskTeamActivity.class);
				break;
			case R.id.rl_help:// 帮助中心
				intent = new Intent(context, HelpCenterActivity.class);
				break;
			case R.id.rl_more:// 点击更多按钮
				intent = new Intent(this, MoreActivity.class);
				break;
			case R.id.rl_customer_service_center:// 客服支持
				CustomerServiceDialog dialog = new CustomerServiceDialog(context);
				dialog.show();
				dialog.setCancelable(false);
				break;
			case R.id.iv_title_right:
				//消息
				intent = new Intent(context,MyMessageActivity.class);
				break;
			case R.id.tab_01:
				//可接任务
				intent = new Intent(context, NoGoingTaskActicity.class);
				isFinish = true;
				break;
			case R.id.tab_02:
				//我的任务
				if(isLogin()){
					intent = new Intent(context,MyTaskFramentNewActivity.class);
					isFinish = true;
				}else{
					intent = new Intent(context, LoginActivity.class);
				}
				break;
			case R.id.tab_03:
				break;
		default:
			break;
		}
		if (intent != null) {
			startActivity(intent);
			if (isFinish) {
				finish();
			}
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		getInitData();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 点击两次退出应用程序处理逻辑
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (!isQuit ) {
				isQuit = true;
				Toast.makeText(context, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						isQuit = false;
					}
				};
				new Timer().schedule(task, 2000);
			} else {
				ExitApplication.getInstance().exit();
				PersonalCenterActivity.this.finish();
				System.exit(0);
			}
		}
		return true;
	}

}
