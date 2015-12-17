package com.task.model;

import com.renrentui.app.R;
import com.renrentui.util.ToMainPage;
import com.renrentui.util.ToMyTaskPage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

/**
 * 主界面  上按钮类  点击切换界面内容
 * @author llp
 *
 */
public class LayoutMyTaskTopmenu {
	public RadioButton btn_task_through;
	public RadioButton btn_task_invalid;
	//public RadioButton btn_task_cancelled;
	public View v_task_through;
	public View v_task_invalid;
	//public View v_task_cancelled;

	public LayoutMyTaskTopmenu(Context context) {
		Activity view = (Activity) context;
		btn_task_through = (RadioButton) view.findViewById(R.id.btn_task_through);
		btn_task_invalid = (RadioButton) view.findViewById(R.id.btn_task_invalid);
		//btn_task_cancelled = (RadioButton) view.findViewById(R.id.btn_task_cancelled);
		v_task_through = view.findViewById(R.id.v_task_through);
		v_task_invalid = view.findViewById(R.id.v_task_invalid);
		//v_task_cancelled = view.findViewById(R.id.v_task_cancelled);
	}
	public LayoutMyTaskTopmenu(View view) {
		btn_task_through = (RadioButton) view.findViewById(R.id.btn_task_through);
		btn_task_invalid = (RadioButton) view.findViewById(R.id.btn_task_invalid);
		//btn_task_cancelled = (RadioButton) view.findViewById(R.id.btn_task_cancelled);
		v_task_through = view.findViewById(R.id.v_task_through);
		v_task_invalid = view.findViewById(R.id.v_task_invalid);
		//v_task_cancelled = view.findViewById(R.id.v_task_cancelled);
	}
	public void setThroughNum(String num1) {
		btn_task_through.setText("进行中("+num1+")");
	}
	public void setInvalid(String num2) {
		btn_task_invalid.setText("已过期("+num2+")");
	}
	public void setOnClickListener(OnClickListener onClickListener) {
		this.btn_task_through.setOnClickListener(onClickListener);
		this.btn_task_invalid.setOnClickListener(onClickListener);
		//this.btn_task_cancelled.setOnClickListener(onClickListener);
	}
	
	/**
	 * 选中状态设定
	 * @param page
	 */
	public void selected(int page){
		hideView();
		if (page==ToMyTaskPage.进行中.getValue()) {
			this.btn_task_through.setChecked(true);
			v_task_through.setVisibility(View.VISIBLE);
		}
		if (page==ToMyTaskPage.已过期.getValue()) {
			this.btn_task_invalid.setChecked(true);
			v_task_invalid.setVisibility(View.VISIBLE);
		}
//		if (page==ToMyTaskPage.已取消.getValue()) {
//			this.btn_task_cancelled.setChecked(true);
//			v_task_cancelled.setVisibility(View.VISIBLE);
//		}
	}
	
	/**
	 * 隐藏所有view横线
	 */
	public void hideView(){
		v_task_through.setVisibility(View.INVISIBLE);
		v_task_invalid.setVisibility(View.INVISIBLE);
		//v_task_cancelled.setVisibility(View.INVISIBLE);
	}
}
