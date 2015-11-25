package com.user.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.renrentui.app.R;

/**
 * 退出dialog
 * 
 * @author llp
 * 
 */
public class CustomerServiceDialog extends AlertDialog implements
		OnClickListener {

	private Context context;
	private Button mCancelTv;
	private Button mCommitTv;
	private String customerphone;

	public CustomerServiceDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_service_dialog_layout);
		customerphone = ((TextView) findViewById(R.id.customer_service_phone))
				.getText().toString();
		mCancelTv = (Button) findViewById(R.id.stand_on_bt);
		mCancelTv.setOnClickListener(this);
		mCommitTv = (Button) findViewById(R.id.leave_out_bt);
		mCommitTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.stand_on_bt:
			dismiss();
			break;
		case R.id.leave_out_bt:
			Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
					Uri.parse("tel:" + customerphone));
			context.startActivity(phoneIntent);
			dismiss();
			break;
		default:
			break;
		}
	}
}
