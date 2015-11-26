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
 * 提现成功dialog
 * 
 * @author llp
 * 
 */
public class WithdrawalsDialog extends AlertDialog implements OnClickListener {

	private Button btn_confirm;
	private ExitDialogListener dialogListener;

	public void addListener(ExitDialogListener mDialogListener){
		this.dialogListener = mDialogListener;
	}
	
	public interface ExitDialogListener{
		void clickCommit();
	}
	
	public WithdrawalsDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.withdrawals_dialog_layout);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
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
