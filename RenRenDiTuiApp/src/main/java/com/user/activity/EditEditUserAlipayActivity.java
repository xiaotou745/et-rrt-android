package com.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBase;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQBindAlipayModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQSendCode;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.MyInCome;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.resultmodel.RSBindAlipayModel;
import com.renrentui.tools.ExitApplication;
import com.renrentui.tools.Util;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.renrentui.util.Utils;
import com.user.service.BindAlipayDialog;
import com.user.service.QuitDialog;


import java.util.Timer;
import java.util.TimerTask;

import base.BaseActivity;

/**
 * 支付宝页面编辑
 */
public class EditEditUserAlipayActivity extends BaseActivity  implements View.OnClickListener,BindAlipayDialog.DialogInterfaceClick,QuitDialog.ExitDialogListener {

    private int time = 60;
    private Timer timer;
private TextView mTV_phone;
    private EditText mET_code;
    private EditText mET_num;
    private EditText mET_name;
    private Button mBtn_bing;
    private Button mBtn_code;
    private String strUserPhone;//电话
    private String strUserName;//姓名
    private String strAlipayNum;//账号
    private String strAlipayName;//名字
    private BindAlipayDialog mBindAlipayDialog;
    private   QuitDialog mQuitDialog;

    /**
     * 绑定接口
     */
    private RQHandler<RSBindAlipayModel> rqHandler_bindAlipay = new RQHandler<RSBindAlipayModel>(
            new IRqHandlerMsg<RSBindAlipayModel>() {

                @Override
                public void onBefore() {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onNetworknotvalide() {
                }

                @Override
                public void onSuccess(RSBindAlipayModel t) {
                    hideProgressDialog();
                    if("200".equals(t.code)){
                        Intent intent = new Intent(EditEditUserAlipayActivity.this,WithdrawalsActivity.class);
                        ToastUtil.show(context, "支付宝绑定成功!");
                        mMyInComeData.setAccountNo(strAlipayNum);
                        mMyInComeData.setAccountType("2");
                        mMyInComeData.setTrueName(strAlipayName);
                        intent.putExtra("VO",mMyInComeData);
                        startActivity(intent);
                        finish();
                    }else{
                        ToastUtil.show(context, "支付宝绑定失败!");
                    }
                }

                @Override
                public void onSericeErr(RSBindAlipayModel t) {
                    hideProgressDialog();
                }

                @Override
                public void onSericeExp() {
                    hideProgressDialog();
                }
            });

    private RQHandler<RSBase> rqHandler_getPhoneCode = new RQHandler<RSBase>(
            new IRqHandlerMsg<RSBase>() {

                @Override
                public void onBefore() {
                }

                @Override
                public void onNetworknotvalide() {
                }

                @Override
                public void onSuccess(RSBase t) {
                    time = 60;
                    timer = new Timer();
                    // 设置自动播放时间
                    timer.schedule(new MyTimerTask(), 100,1000);
                    mBtn_code.setEnabled(false);
                }

                @Override
                public void onSericeErr(RSBase t) {
                    ToastUtil.show(context, t.msg);
                }

                @Override
                public void onSericeExp() {
                }
            });

    private MyInCome mMyInComeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_edit_user_alipay);
        super.init();
        mMyInComeData= (MyInCome)getIntent().getSerializableExtra("VO");
        if(mMyInComeData!=null){
            strUserPhone = mMyInComeData.phoneNo;
            strUserName = mMyInComeData.clienterName;
        }
        initControlView();
    }
    private void initControlView(){
        if(mIV_title_left!=null){
            mIV_title_left.setVisibility(View.VISIBLE);
            mIV_title_left.setImageResource(R.drawable.back);
            mIV_title_left.setOnClickListener(this);
        }
        if(mTV_title_content!=null){
            mTV_title_content.setText("绑定支付宝");
        }
        mTV_phone = (TextView)findViewById(R.id.tv_alipay_phone_value);
        if(!TextUtils.isEmpty(strUserPhone) && strUserPhone.length()>=4){
            StringBuffer sb = new StringBuffer();
            sb.append(strUserPhone.substring(0, 3));
            sb.append("****").append(strUserPhone.substring(strUserPhone.length() - 4));
            mTV_phone.setText(sb.toString());
        }

        mET_code = (EditText)findViewById(R.id.et_alipay_code_value);
        mET_num =  (EditText)findViewById(R.id.et_alipay_num_value);
        mET_name =  (EditText)findViewById(R.id.et_alipay_name_value);
        mBtn_code =  (Button)findViewById(R.id.btn_get_code);
        mBtn_code.setOnClickListener(this);
        mBtn_bing = (Button)findViewById(R.id.bind_btn);
        mBtn_bing.setOnClickListener(this);
    }

    /**
     * 支付宝绑定
     */
    private void submitAlipayBind(){
        String strCode= mET_code.getText().toString().trim();
        String strNum = mET_num.getText().toString().trim();
        String strName = mET_name.getText().toString().trim();
        if(TextUtils.isEmpty(strCode)){
            ToastUtil.show(context, "请填写验证码!");
            return;
        }
        if(TextUtils.isEmpty(strName)){
            ToastUtil.show(context, "请输入支付宝姓名!");
            return;
        }
        if(TextUtils.isEmpty(strNum)){
            ToastUtil.show(context,"请输入支付宝账号!");
            return;
        }else if(strNum.length()>30){
            ToastUtil.show(context," 请输入正确支付宝信息!");
            return;
        }else if(!Util.isMobileNO(strNum) && !Util.isEmail(strNum)){
            ToastUtil.show(context,"请输入正确的邮箱地址或手机号!");
            return;
        }
        if(mBindAlipayDialog!=null){
            mBindAlipayDialog =null;
        }
     mBindAlipayDialog = new BindAlipayDialog(context,this,strNum,strName);
        mBindAlipayDialog.show();

    }

    /**
     * 获取手机验证码
     */
    private void getCode(){
        ApiUtil.Request(new RQBaseModel<RQBase, RSBase>(context,
                new RQSendCode(strUserPhone, 4), new RSBase(),
                ApiNames.获取手机验证码.getValue(), RequestType.POST,
                rqHandler_getPhoneCode));

    }


    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    time--;
                    if (time >= 0) {
                        mBtn_code.setText("("+time + ")重新发送");
                    } else {
                        mBtn_code.setText("获取验证码");
                        mBtn_code.setEnabled(true);
                        timer.cancel();
                    }
                }
            });

        }
    }

    /**
     * 离开页面
     */
    public void exitActivity(){
        if(mQuitDialog!=null){
            mQuitDialog = null;
        }
        mQuitDialog   = new QuitDialog(context,"绑定尚未完成,确定退出吗","确定","取消");
        mQuitDialog.addListener(this);
        mQuitDialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_left:
                exitActivity();
                break;
            case R.id.btn_get_code:
                getCode();
                break;
            case R.id.bind_btn:
                submitAlipayBind();
                break;
        }
    }
    @Override
    protected void onStop() {
    super.onPause();
        if(mBindAlipayDialog!=null && mBindAlipayDialog.isShowing()){
            mBindAlipayDialog.dismiss();
            mBindAlipayDialog=null;
        }
        if(mQuitDialog!=null && mQuitDialog.isShowing()){
            mQuitDialog.dismiss();
            mQuitDialog = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击两次退出应用程序处理逻辑
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            exitActivity();
        }
        return true;
    }

    @Override
    public void dialogClickOk() {
        showProgressDialog();
        String strCode= mET_code.getText().toString().trim();
        String strNum = mET_num.getText().toString().trim();
        String strName = mET_name.getText().toString().trim();
        strAlipayNum = strNum;
        strAlipayName = strName;
        ApiUtil.Request(new RQBaseModel<RQBindAlipayModel, RSBindAlipayModel>(context,
                new RQBindAlipayModel(Utils.getUserDTO(context).data.userId,strUserPhone,strNum,strName,strCode),
                new RSBindAlipayModel(), ApiNames.绑定支付宝.getValue(), RequestType.POST,
                rqHandler_bindAlipay));
    }

    //
    @Override
    public void clickCancel() {
        mQuitDialog.dismiss();
    }

    @Override
    public void clickCommit() {
        mQuitDialog.dismiss();
        finish();
    }
}
