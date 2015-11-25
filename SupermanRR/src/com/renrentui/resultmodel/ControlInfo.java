package com.renrentui.resultmodel;

import java.io.Serializable;
import java.util.List;

public class ControlInfo implements Serializable {
	/**
	 * 控件类型（共7类，SelectOption为下拉列表框；RadioBox为单项选择控件；CheckBox为复选框；Upload为上传图片；
	 * TextBox为文本输入框；DateTimePicker为日期选择框；CitySelector为城市选择控件
	 */
	public String controlType;
	/**
	 * 控件左边的描述
	 */
	public String title;
	/**
	 * 控件对应的名称，提交合同时候使用
	 */
	public String name;
	/**
	 * 默认值
	 */
	public String defaultValue;
	/**
	 * 控件值
	 */
	public String controlData;
	/**
	 * 控件排序标识
	 */
	public String orderNum;
	/**
	 * 已经提交合同显示提交的值,没有的话显示空字符串
	 */
	public String hadValue;
	/**
	 * 已经提交合同显示提交的值,没有的话显示空字符串(相对路径)
	 */
	public String controlValue;

	public ControlInfo() {
		super();
	}

	public ControlInfo(String controlType, String title, String name,
			String defaultValue, String controlData, String orderNum,
			String hadValue, String controlValue) {
		super();
		this.controlType = controlType;
		this.title = title;
		this.name = name;
		this.defaultValue = defaultValue;
		this.controlData = controlData;
		this.orderNum = orderNum;
		this.hadValue = hadValue;
		this.controlValue = controlValue;
	}

	@Override
	public String toString() {
		return "ControlInfo[controlType=" + controlType + ",title=" + title
				+ ",name=" + name + ",defaultValue=" + defaultValue
				+ ",controlData=" + controlData + ",orderNum=" + orderNum
				+ ",hadValue=" + hadValue + ",controlValue=" + controlValue
				+ "]";
	}
}
