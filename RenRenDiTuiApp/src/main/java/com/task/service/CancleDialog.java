package com.task.service;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.renrentui.app.R;

/**
 * 取消操作
 * 
 * @author llp
 * 
 */
public class CancleDialog extends AlertDialog implements OnClickListener {

	private Button mBtn_ok;
	private Button mBtn_cancle;
	private CancleDialogListener dialogListener;
	private TextView mTitleTv;
	private String mTitle;

	public void addListener(CancleDialogListener mDialogListener) {
		this.dialogListener = mDialogListener;
	}

	public interface CancleDialogListener {
		void clickOk();

		void clickCancle();
	}

	public CancleDialog(Context context, String title) {
		super(context);
		this.mTitle = title;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cancle_dialog_layout);
		mTitleTv = (TextView) findViewById(R.id.tv_cancle_content_dialog);
		mBtn_ok = (Button) findViewById(R.id.stand_on_bt);
		mBtn_ok.setOnClickListener(this);
		mBtn_cancle = (Button) findViewById(R.id.leave_out_bt);
		mBtn_cancle.setOnClickListener(this);
		mTitleTv.setText(mTitle);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.stand_on_bt:
			dismiss();
			if (dialogListener != null) {
				dialogListener.clickOk();
			}
			break;
		case R.id.leave_out_bt:
			if (dialogListener != null) {
				dialogListener.clickCancle();
			}
			dismiss();
			break;
		default:
			break;
		}
	}
}
