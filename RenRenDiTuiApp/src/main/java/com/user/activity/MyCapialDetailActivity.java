package com.user.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.renrentui.app.R;
import com.renrentui.resultmodel.RSMyCaptailModel;

import base.BaseActivity;

/**
 * 资金明细
 */
public class MyCapialDetailActivity extends BaseActivity implements View.OnClickListener {

    private RSMyCaptailModel.DataList mDataList;
    private TextView mTV_money_flag;
    private TextView mTV_money;
    private TextView mTV_type;
    private TextView mTV_content;
    private TextView mTV_time;
    private TextView mTV_trader_num;//交易号
    private boolean isAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_capial_detail);
        super.init();
        mDataList = (RSMyCaptailModel.DataList)this.getIntent().getSerializableExtra("VO");
        isAdd = this.getIntent().getIntExtra("isAdd",0)==0?true:false;
        initView();
        initViewValue();

    }
    private void initView(){
        if(mIV_title_left!=null){
            mIV_title_left.setVisibility(View.VISIBLE);
            mIV_title_left.setOnClickListener(this);
        }
        if(mTV_title_content!=null){
            mTV_title_content.setText("账单详情");
        }
        mTV_money_flag = (TextView)findViewById(R.id.bill_money_flag);
        mTV_money = (TextView)findViewById(R.id.bill_money);
        mTV_type = (TextView)findViewById(R.id.bill_type);
        mTV_content = (TextView)findViewById(R.id.bill_detail);
        mTV_time = (TextView)findViewById(R.id.bill_create_time);
        mTV_trader_num = (TextView)findViewById(R.id.bill_trade_num);
    }
    private void initViewValue(){
        if(mDataList!=null){

            mTV_money.setText(mDataList.getAmount());
            mTV_type.setText(mDataList.getRecordTypeName());
            if("1".equals(mDataList.getRecordType())){
                mTV_content.setText("成功完成-"+mDataList.getRemark());
            }else{
                mTV_content.setText(mDataList.getRemark());
            }
           // mTV_content.setText(mDataList.getRemark());
            mTV_time.setText(mDataList.getOperateTime());
            if(isAdd){
                mTV_money_flag.setVisibility(View.VISIBLE);
                mTV_money_flag.setText("+");
            }else{
                mTV_money_flag.setText("-");
            }
            mTV_trader_num.setText(mDataList.getRelationNo());
        }else{
            mTV_money.setText("");
            mTV_type.setText("");
            mTV_content.setText("");
            mTV_time.setText("");
            mTV_money_flag.setText("");
        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_left:
                finish();
                break;
        }

    }
}
