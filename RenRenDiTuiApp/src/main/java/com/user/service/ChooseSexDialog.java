package com.user.service;

import com.renrentui.app.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseSexDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context context;
	private LinearLayout layout_woman;
	private LinearLayout layout_man;
	private ImageView iv_choose_woman;
	private ImageView iv_choose_man;
	private TextView tv_sex_man;
	private TextView tv_sex_woman;
	private ExitDialogListener dialogListener;
	private String editContent;

	public void addListener(ExitDialogListener mDialogListener) {
		this.dialogListener = mDialogListener;
	}

	public interface ExitDialogListener {
		void clickCancel();

		void clickCommit(String tv);
	}

	public ChooseSexDialog(Context context, String editContent) {
		super(context);
		this.editContent = editContent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_sex_dialog_layout);
		layout_woman = (LinearLayout) findViewById(R.id.layout_woman);
		layout_man = (LinearLayout) findViewById(R.id.layout_man);
		tv_sex_man = (TextView) findViewById(R.id.tv_sex_man);
		tv_sex_woman = (TextView) findViewById(R.id.tv_sex_woman);
		iv_choose_woman = (ImageView) findViewById(R.id.iv_choose_woman);
		iv_choose_man = (ImageView) findViewById(R.id.iv_choose_man);
		if (editContent.equals("男"))
			iv_choose_man.setVisibility(View.VISIBLE);
		else if (editContent.equals("女"))
			iv_choose_woman.setVisibility(View.VISIBLE);
		layout_woman.setOnClickListener(this);
		layout_man.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_woman:
			if (dialogListener != null) {
				dialogListener.clickCommit(tv_sex_woman.getText().toString());
			}
			dismiss();
			break;

		case R.id.layout_man:
			if (dialogListener != null) {
				dialogListener.clickCommit(tv_sex_man.getText().toString());
			}
			dismiss();
			break;

		default:
			break;
		}
	}

}
