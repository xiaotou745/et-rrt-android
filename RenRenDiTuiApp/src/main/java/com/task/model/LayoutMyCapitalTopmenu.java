package com.task.model;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.renrentui.app.R;
import com.renrentui.util.ToMyTaskPage;

/**
 * 资金列表
 * @author llp
 *
 */
public class LayoutMyCapitalTopmenu {
	public RadioButton btn_capital_income;//收入
	public RadioButton btn_captial_expend;//支出
	public View v_capital_income;
	public View v_captial_expend;

	public LayoutMyCapitalTopmenu(Context context) {
		Activity view = (Activity) context;
		btn_capital_income = (RadioButton) view.findViewById(R.id.btn_capital_1);
		btn_captial_expend = (RadioButton) view.findViewById(R.id.btn_capital_2);
		v_capital_income = view.findViewById(R.id.v_capital_1);
		v_captial_expend = view.findViewById(R.id.v_capital_2);
	}
	public LayoutMyCapitalTopmenu(View view) {
		btn_capital_income = (RadioButton) view.findViewById(R.id.btn_capital_1);
		btn_captial_expend = (RadioButton) view.findViewById(R.id.btn_capital_2);
		v_capital_income = view.findViewById(R.id.v_capital_1);
		v_captial_expend = view.findViewById(R.id.v_capital_2);
	}

	public void setOnClickListener(OnClickListener onClickListener) {
		this.btn_capital_income.setOnClickListener(onClickListener);
		this.btn_captial_expend.setOnClickListener(onClickListener);
	}
	
	/**
	 * 选中状态设定
	 * @param page
	 */
	public void selected(int page){
		hideView();
		if (page==ToMyTaskPage.进行中.getValue()) {
			this.btn_capital_income.setChecked(true);
			v_capital_income.setVisibility(View.VISIBLE);
		}
		if (page==ToMyTaskPage.已过期.getValue()) {
			this.btn_captial_expend.setChecked(true);
			v_captial_expend.setVisibility(View.VISIBLE);
		}

	}
	
	/**
	 * 隐藏所有view横线
	 */
	public void hideView(){
		v_capital_income.setVisibility(View.INVISIBLE);
		v_captial_expend.setVisibility(View.INVISIBLE);
	}
}
