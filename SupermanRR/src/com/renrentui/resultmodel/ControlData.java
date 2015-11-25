package com.renrentui.resultmodel;

public class ControlData {
	/**
	 * 控件item对应的Value
	 */
	public String Value;
	/**
	 * 控件item对应的Text
	 */
	public String Text;

	public ControlData() {
		super();
	}

	public ControlData(String value, String text) {
		super();
		Value = value;
		Text = text;
	}

	@Override
	public String toString() {
		return "ControlData[Value=" + Value + ",Text=" + Text + "]";
	}

}
