package com.user.service;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.renrentui.app.R;

/**
 * Created by Administrator on 2016/1/6 0006.
 * 提现账户绑定提示窗口
 */
public class BindAlipayDialog extends AlertDialog implements android.view.View.OnClickListener{

    private TextView mTV_num;
    private TextView mTV_name;
    private TextView mTV_notice;
    private Button mBtn_ok;
    private Button mBtn_cancle;
    private Context context;
    private String strNum;
    private String strName;

    private  DialogInterfaceClick mDialogInterfaceClick;
    public BindAlipayDialog(Context context) {
        super(context);
        this.context = context;
        }

    public BindAlipayDialog(Context context, DialogInterfaceClick mDialogInterfaceClick,String strNum,String strName) {
        super(context);
        this.context = context;
        this.strNum = strNum;
        this.strName = strName;
        this.mDialogInterfaceClick = mDialogInterfaceClick;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_alipay_dialog_layout);
        mTV_num =(TextView) findViewById(R.id.tv_key_02);
        mTV_name =(TextView) findViewById(R.id.tv_key_04);
        mTV_notice = (TextView)findViewById(R.id.tv_alipay_dialog_notice);
        mBtn_ok = (Button)findViewById(R.id.leave_out_bt);
        mBtn_ok.setOnClickListener(this);
        mBtn_cancle = (Button)findViewById(R.id.stand_on_bt);
        mBtn_cancle.setOnClickListener(this);
        mTV_num.setText(strNum);
        mTV_name.setText(strName);
        mTV_notice.setText(Html.fromHtml(context.getResources().getString(R.string.bind_alipay_notice)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.leave_out_bt:
                //确定
                dismiss();
                mDialogInterfaceClick.dialogClickOk();
                break;
            case R.id.stand_on_bt:
                //取消
                dismiss();
                break;
        }

    }


    public interface  DialogInterfaceClick{
        public void dialogClickOk();
    }
}
