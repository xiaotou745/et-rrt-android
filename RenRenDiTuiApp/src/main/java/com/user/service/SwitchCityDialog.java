package com.user.service;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.renrentui.app.R;

/**
 * Created by Administrator on 2016/2/22 0022.
 * 切换城市对话框
 */
public class SwitchCityDialog extends AlertDialog implements View.OnClickListener {
    private Button mCancelTv;
    private Button mCommitTv;
    private SwitchDialogListener dialogListener;
    private TextView mTitleTv;
    private String mTitle;


//    public SwitchCityDialog(Context context) {
//        super(context);
//    }
//
//    public SwitchCityDialog(Context context, int theme) {
//        super(context, theme);
//    }
//
//    public SwitchCityDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }
    public SwitchCityDialog(Context context,String title) {
        super(context);
        this.mTitle = title;
    }

    public void addListener(SwitchDialogListener mDialogListener){
        this.dialogListener = mDialogListener;
    }

    public interface SwitchDialogListener{
        void clickCancel();
        void clickCommit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_switch_city_layout);
        mTitleTv = (TextView) findViewById(R.id.dialog_mesg_tv);
        mCancelTv = (Button) findViewById(R.id.stand_on_bt);
        mCancelTv.setOnClickListener(this);
        mCommitTv = (Button) findViewById(R.id.leave_out_bt);
        mCommitTv.setOnClickListener(this);
        mTitleTv.setText(mTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stand_on_bt:
                dismiss();
                if(dialogListener != null){
                    dialogListener.clickCancel();
                }
                break;
            case R.id.leave_out_bt:
                if(dialogListener != null){
                    dialogListener.clickCommit();
                }
                dismiss();
                break;
            default:
                break;
        }
    }
}
