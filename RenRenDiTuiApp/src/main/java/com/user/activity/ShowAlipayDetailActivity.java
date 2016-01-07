package com.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.MyInCome;

import base.BaseActivity;

/**
 * Created by Administrator on 2016/1/6 0006.
 * 提现账号详情
 */
public class ShowAlipayDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTV_key2;
    private TextView mTV_key4;
    private Button mBtn_click;
    private String str_user_name;
    private String str_user_phone;
    private String str_alipay_num;
    private String str_alipay_name;
    private MyInCome mMyInComeData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay_details_layout);
        super.init();

        if(mTV_title_content!=null){
            mTV_title_content.setText("支付宝账户");
        }
        if(mIV_title_left!=null){
            mIV_title_left.setVisibility(View.VISIBLE);
            mIV_title_left.setOnClickListener(this);
        }
        mMyInComeData= (MyInCome)getIntent().getSerializableExtra("VO");
        if(mMyInComeData!=null){
            str_user_phone = mMyInComeData.phoneNo;
            str_user_name = mMyInComeData.clienterName;
            str_alipay_name = mMyInComeData.getTrueName();
            str_alipay_num = mMyInComeData.getAccountNo();
        }
//        str_user_name = this.getIntent().getStringExtra("STR_USER_NAME");
//        str_user_phone = this.getIntent().getStringExtra("STR_USER_PHONE");
//        str_alipay_num = this.getIntent().getStringExtra("STR_ALIPAY_NUM");
//        str_alipay_name = this.getIntent().getStringExtra("STR_ALIPAY_NAME");


        mTV_key2 = (TextView)findViewById(R.id.tv_key_02);
        mTV_key2.setText(str_alipay_num);
        mTV_key4 = (TextView)findViewById(R.id.tv_key_04);
        mTV_key4.setText(str_alipay_name);
        mBtn_click = (Button)findViewById(R.id.btn_click_01);
        mBtn_click.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.btn_click_01:
                //更改
                Intent mIntent = new Intent();
                mIntent.setClass(ShowAlipayDetailActivity.this,EditEditUserAlipayActivity.class);
//                mIntent.putExtra("STR_USER_NAME", str_user_name);
//                mIntent.putExtra("STR_USER_PHONE", str_user_phone);
                mIntent.putExtra("VO",mMyInComeData);
                startActivity(mIntent);
                finish();
                break;
        }
    }
}
