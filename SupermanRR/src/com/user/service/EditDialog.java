package com.user.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.renrentui.app.R;

public class EditDialog extends AlertDialog implements OnClickListener {

	private Button mCancelTv;
	private Button mCommitTv;
	private ExitDialogListener dialogListener;
	private EditText et_dialog_mesg;
	private String editContent;
	private int inputType;

	public void addListener(ExitDialogListener mDialogListener) {
		this.dialogListener = mDialogListener;
	}

	public interface ExitDialogListener {
		void clickCancel();

		void clickCommit(String tv);
	}

	public EditDialog(Context context, String editContent) {
		super(context);
		this.editContent = editContent;
	}

	public EditDialog(Context context, String editContent, int inputType) {
		super(context);
		this.editContent = editContent;
		this.inputType = inputType;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_dialog_layout);
		et_dialog_mesg = (EditText) findViewById(R.id.et_dialog_mesg);
		mCancelTv = (Button) findViewById(R.id.stand_on_bt);
		mCancelTv.setOnClickListener(this);
		mCommitTv = (Button) findViewById(R.id.leave_out_bt);
		mCommitTv.setOnClickListener(this);
		et_dialog_mesg.setText(editContent);
		if (inputType != 0)
			et_dialog_mesg.setInputType(inputType);
		et_dialog_mesg.setSelection(editContent.length());
		et_dialog_mesg.setEnabled(true);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.stand_on_bt:
			dismiss();
			if (dialogListener != null) {
				dialogListener.clickCancel();
			}
			break;
		case R.id.leave_out_bt:
			if (dialogListener != null) {
				dialogListener.clickCommit(et_dialog_mesg.getText().toString());
			}
			dismiss();
			break;
		default:
			break;
		}
	}
}
