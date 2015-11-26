package com.renrentui.resultmodel;

public class RSReceiveTask extends RSBase {
	public String data;

	public RSReceiveTask() {
		super();
	}

	public RSReceiveTask(String Code, String Msg, String data) {
		super(Code, Msg);
		this.data = data;
	}

	@Override
	public String toString() {
		return "RSReceiveTask[data=" + data + "]";
	}

}
