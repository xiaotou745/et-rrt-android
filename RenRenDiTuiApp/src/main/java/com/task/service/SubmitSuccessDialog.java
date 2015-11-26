package com.task.service;

import com.renrentui.app.R;
import com.renrentui.util.ToastUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 退出dialog
 * 
 * @author llp
 * 
 */
public class SubmitSuccessDialog extends AlertDialog implements OnClickListener {

	private Button mCancelTv;
	private Button mCommitTv;
	private ExitDialogListener dialogListener;
	private TextView tv_content;
	private String content;

	public void addListener(ExitDialogListener mDialogListener){
		this.dialogListener = mDialogListener;
	}
	
	public interface ExitDialogListener{
		void clickCancel();
		void clickCommit();
	}
	
	public SubmitSuccessDialog(Context context,String content) {
		super(context);
		this.content = content;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_dialog_layout);
		tv_content = (TextView) findViewById(R.id.tv_content);
		mCancelTv = (Button) findViewById(R.id.stand_on_bt);
		mCancelTv.setOnClickListener(this);
		mCommitTv = (Button) findViewById(R.id.leave_out_bt);
		mCommitTv.setOnClickListener(this);
		tv_content.setText(content);
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
