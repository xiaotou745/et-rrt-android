package com.user.service;

import com.renrentui.app.R;

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
public class QuitDialog extends AlertDialog implements OnClickListener {

	private Button mCancelTv;
	private Button mCommitTv;
	private ExitDialogListener dialogListener;
	private TextView mTitleTv;
	private String mTitle;
	private String mCommitStr;
	private String mCancelStr;

	public void addListener(ExitDialogListener mDialogListener){
		this.dialogListener = mDialogListener;
	}
	
	public interface ExitDialogListener{
		void clickCancel();
		void clickCommit();
	}
	
	public QuitDialog(Context context,String title,String commitStr,String cancelStr) {
		super(context);
		this.mTitle = title;
		this.mCommitStr = commitStr;
		this.mCancelStr = cancelStr;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quit_dialog_layout);
		mTitleTv = (TextView) findViewById(R.id.dialog_mesg_tv);
		mCancelTv = (Button) findViewById(R.id.stand_on_bt);
		mCancelTv.setOnClickListener(this);
		mCommitTv = (Button) findViewById(R.id.leave_out_bt);
		mCommitTv.setOnClickListener(this);
		mTitleTv.setText(mTitle);
		mCommitTv.setText(mCommitStr);
		mCancelTv.setText(mCancelStr);
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
