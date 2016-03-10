package com.task.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.renrentui.app.R;
import com.renrentui.interfaces.IRqHandlerMsg;
import com.renrentui.requestmodel.RQBaseModel;
import com.renrentui.requestmodel.RQHandler;
import com.renrentui.requestmodel.RQUpdateUserinfo;
import com.renrentui.requestmodel.RequestType;
import com.renrentui.resultmodel.RSBase;
import com.renrentui.util.ApiNames;
import com.renrentui.util.ApiUtil;
import com.renrentui.util.ToastUtil;
import com.user.activity.PersonalCenterActivity;
import com.user.service.PersonalDialog;
import com.wheelUtils.TimePopwindow;

import base.BaseActivity;

/**
 * Created by Administrator on 2016/3/7 0007.
 * 个人信息不全页面
 */
public class PersonalAddInfoActivity extends BaseActivity implements PersonalDialog.SwitchDialogListener {
    private EditText mET_name;
    private EditText mET_age;
    private Button mBtn_save;
    private String strUserName="";
    private String strSex;
    private String strBirthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personage_add);
        super.init();
        if(mTV_title_content!=null){
            mTV_title_content.setText("补全个人信息");
        }


        strUserName = this.getIntent().getStringExtra("UserName");
        strSex = this.getIntent().getStringExtra("sex");
        strBirthDay = this.getIntent().getStringExtra("birthDay");

        mET_age = (EditText)findViewById(R.id.et_personal_age);
        mET_name = (EditText)findViewById(R.id.et_personal_name);
        mET_name.setText(strUserName);
        if(!TextUtils.isEmpty(strUserName)){
            mET_name.setSelection(strUserName.length());
        }
        mET_age.setText(strBirthDay);
        mBtn_save = (Button)findViewById(R.id.btn_save);
        mET_age.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final TimePopwindow popWindow = new TimePopwindow(PersonalAddInfoActivity.this);
                popWindow.setmSelectTimeMsgObj(new TimePopwindow.SelectTimeMsgListener() {

                    @Override
                    public void selectTimeMsg(String strTime) {
                        mET_age.setText(strTime);
                        popWindow.dismiss();
                    }
                });
                popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                    }
                });
                closeWindow();
                popWindow.showAtLocation(mET_age, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.4f);
            }
        });
        mBtn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });
    }
    // 隐藏键盘
    private void closeWindow() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mET_age.getWindowToken(), 0); // 强制隐藏键盘
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }

    /**
     * 设置用户信息
     */
    private void saveUserInfo(){
        String str_1=mET_name.getText().toString().trim();
        String str_2 = mET_age.getText().toString().trim();

        if(TextUtils.isEmpty(str_1) || str_1.length()>10){
            ToastUtil.show(context,"请填写正确用户名");
            return;
        }
        if(TextUtils.isEmpty(str_2)){
            ToastUtil.show(context,"请填写出生日期");
            return;
        }
        ApiUtil.Request(new RQBaseModel<RQUpdateUserinfo, RSBase>(context,
                new RQUpdateUserinfo(strUserId,
                        str_1, strSex, "", "", str_2),
                new RSBase(), ApiNames.修改用户信息.getValue(), RequestType.POST,
                rqHandler_updateUserInfo));
    }
    private void startHomeActivity(){
        Intent mIntent = new Intent();
        mIntent.setClass(PersonalAddInfoActivity.this,NoGoingTaskActicity.class);
        startActivity(mIntent);
        finish();
    }
    private void startPersonalActivity(){
        Intent mIntent = new Intent();
        mIntent.setClass(PersonalAddInfoActivity.this,PersonalCenterActivity.class);
        startActivity(mIntent);
        finish();
    }

    private RQHandler<RSBase> rqHandler_updateUserInfo = new RQHandler<RSBase>(
            new IRqHandlerMsg<RSBase>() {

                @Override
                public void onBefore() {
                }

                @Override
                public void onNetworknotvalide() {
                    ToastUtil.show(context, "网络错误");
                    startHomeActivity();
                }

                @Override
                public void onSuccess(RSBase t) {
                    showDialog();
                }

                @Override
                public void onSericeErr(RSBase t) {
                        ToastUtil.show(context, t.msg);
                    startHomeActivity();
                }

                @Override
                public void onSericeExp() {
                    startHomeActivity();
                }
            });

    /**
     * 设置信息成功。显示对话框
     */
    private void showDialog(){
        PersonalDialog mPresonalDialog= new PersonalDialog(context);
        mPresonalDialog.addListener(this);
        mPresonalDialog.setCancelable(false);
        mPresonalDialog.show();

    }


    @Override
    public void clickLeft() {
        //去做任务
        startHomeActivity();
    }

    @Override
    public void clickRight() {
        //个人信息
        startPersonalActivity();

    }
}

