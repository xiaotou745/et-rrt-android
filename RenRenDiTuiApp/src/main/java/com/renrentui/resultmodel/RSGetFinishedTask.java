package com.renrentui.resultmodel;

public class RSGetFinishedTask extends RSBase {
	public GetFinishedTask data;

	public RSGetFinishedTask() {
		super();
	}

	public RSGetFinishedTask(String Code, String Msg, GetFinishedTask data) {
		super(Code, Msg);
		this.data = data;
	}

	@Override
	public String toString() {
		return "RSGetFinishedTask[data=" + data + "]";
	}

}
