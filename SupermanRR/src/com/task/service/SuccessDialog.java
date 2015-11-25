package com.task.service;

import com.renrentui.app.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 领取成功dialog
 * 
 * @author llp
 * 
 */
public class SuccessDialog extends AlertDialog implements OnClickListener {

	private Button mCancelTv;
	private Button mCommitTv;
	private ExitDialogListener dialogListener;
	private TextView mTitleTv;
	private String mTitle;

	public void addListener(ExitDialogListener mDialogListener) {
		this.dialogListener = mDialogListener;
	}

	public interface ExitDialogListener {
		void clickCancel();

		void clickCommit();
	}

	public SuccessDialog(Context context, String title) {
		super(context);
		this.mTitle = title;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.success_dialog_layout);
		mTitleTv = (TextView) findViewById(R.id.tv_success_content_dialog);
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
			if (dialogListener != null) {
				dialogListener.clickCommit();
			}
			break;
		case R.id.leave_out_bt:
			if (dialogListener != null) {
				dialogListener.clickCancel();
			}
			dismiss();
			break;
		default:
			break;
		}
	}
}
