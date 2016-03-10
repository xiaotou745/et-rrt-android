package com.user.service;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.renrentui.app.R;

/**
 * Created by Administrator on 2016/3/9 0009.
 * 补全人信息对话框
 */
public class PersonalDialog  extends AlertDialog implements View.OnClickListener {
    private Button mBtn_left;
    private Button mBtn_right;
    private SwitchDialogListener dialogListener;
    public PersonalDialog(Context context) {
        super(context);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_left:
                dismiss();
                if(dialogListener != null){
                    dialogListener.clickLeft();
                }
                break;
            case R.id.btn_right:
                if(dialogListener != null){
                    dialogListener.clickRight();
                }
                dismiss();
                break;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_dialog_layout);
        mBtn_left = (Button) findViewById(R.id.btn_left);
        mBtn_left.setOnClickListener(this);
        mBtn_right = (Button) findViewById(R.id.btn_right);
        mBtn_right.setOnClickListener(this);
    }

    public void addListener(SwitchDialogListener mDialogListener){
        this.dialogListener = mDialogListener;
    }
    public interface SwitchDialogListener{
        void clickLeft();
        void clickRight();
    }

}
