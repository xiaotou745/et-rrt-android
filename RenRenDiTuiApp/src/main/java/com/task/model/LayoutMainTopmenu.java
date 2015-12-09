package com.task.model;

import com.renrentui.app.R;
import com.renrentui.util.ToMainPage;

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
public class LayoutMainTopmenu {
	public RadioButton btn_task_nogoing;
	public RadioButton btn_task_ongoing;
	public RadioButton btn_task_finished;
	public View v_task_nogoing;
	public View v_task_ongoing;
	public View v_task_finished;
	
	public void setShenhezhong(int shenhezhong) {
		btn_task_ongoing.setText("审核中("+shenhezhong+")");
	}
//	public void setLingqushu(int lingqushu) {
//		btn_task_nogoing.setText("已领取("+lingqushu+")");
//	}
public void setYtongguo(int tongguo) {
	btn_task_nogoing.setText("已通过("+tongguo+")");
}
	public void setWeitongguo(int weitongguo) {
		btn_task_finished.setText("未通过("+weitongguo+")");
	}
	public LayoutMainTopmenu(Context context) {
		Activity view = (Activity) context;
		btn_task_nogoing = (RadioButton) view.findViewById(R.id.btn_task_nogoing);
		btn_task_ongoing = (RadioButton) view.findViewById(R.id.btn_task_ongoing);
		btn_task_finished = (RadioButton) view.findViewById(R.id.btn_task_finished);
		v_task_nogoing = view.findViewById(R.id.v_task_nogoing);
		v_task_ongoing = view.findViewById(R.id.v_task_ongoing);
		v_task_finished = view.findViewById(R.id.v_task_finished);
	}
	public LayoutMainTopmenu(View view) {
		btn_task_nogoing = (RadioButton) view.findViewById(R.id.btn_task_nogoing);
		btn_task_ongoing = (RadioButton) view.findViewById(R.id.btn_task_ongoing);
		btn_task_finished = (RadioButton) view.findViewById(R.id.btn_task_finished);
		v_task_nogoing = view.findViewById(R.id.v_task_nogoing);
		v_task_ongoing = view.findViewById(R.id.v_task_ongoing);
		v_task_finished = view.findViewById(R.id.v_task_finished);
	}
	
	public void setOnClickListener(OnClickListener onClickListener) {
		this.btn_task_nogoing.setOnClickListener(onClickListener);
		this.btn_task_ongoing.setOnClickListener(onClickListener);
		this.btn_task_finished.setOnClickListener(onClickListener);
	}
	
	/**
	 * 选中状态设定
	 * @param page
	 */
	public void selected(int page){
		hideView();
		if (page==ToMainPage.审核中.getValue()) {
			this.btn_task_ongoing.setChecked(true);
			v_task_ongoing.setVisibility(View.VISIBLE);
		}
		if (page==ToMainPage.已通过.getValue()) {
			this.btn_task_nogoing.setChecked(true);
			v_task_nogoing.setVisibility(View.VISIBLE);
		}

		if (page==ToMainPage.未通过.getValue()) {
			this.btn_task_finished.setChecked(true);
			v_task_finished.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 隐藏所有view横线
	 */
	public void hideView(){
		v_task_nogoing.setVisibility(View.INVISIBLE);
		v_task_ongoing.setVisibility(View.INVISIBLE);
		v_task_finished.setVisibility(View.INVISIBLE);
	}
}
