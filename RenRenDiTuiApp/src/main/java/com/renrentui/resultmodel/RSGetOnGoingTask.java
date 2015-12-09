package com.renrentui.resultmodel;

public class RSGetOnGoingTask extends RSBase {
	public GetOnGoingTask data;

	public RSGetOnGoingTask() {
		super();
	}

	public RSGetOnGoingTask(String Code, String Msg, GetOnGoingTask data) {
		super(Code, Msg);
		this.data = data;
	}

	@Override
	public String toString() {
		return "RSGetOnGoingTask[data=" + data + "]";
	}
}
